package bsuir.diplom.mercury;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import bsuir.diplom.mercury.entities.Staff;
import bsuir.diplom.mercury.entities.User;
import bsuir.diplom.mercury.entities.enums.Role;

@SuppressLint("ShowToast")
public class LoginActivity extends AppCompatActivity {
    private TextInputLayout phoneInput;
    private PinView pinView;

    private String sentCode;
    private String phoneNumber;

    private final DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneInput = findViewById(R.id.login_phone_input);
        pinView = findViewById(R.id.pin_view);

        Button sendCodeButton = findViewById(R.id.login_send_code);
        Button loginButton = findViewById(R.id.login_button);
        Button toRegistrationButton = findViewById(R.id.to_registration_button);

        Staff.insertStaffData();
        User.insertUserDriversAccounts();

        sendCodeButton.setOnClickListener(view -> {
            phoneNumber = Objects.requireNonNull(phoneInput.getEditText()).getText().toString();
            validatePhoneNumber();
        });
        loginButton.setOnClickListener(view -> {
            String code = pinView.getEditableText().toString();
            verifySignInCode(code);
        });
        toRegistrationButton.setOnClickListener(view -> startActivity(new Intent(this, RegistrationActivity.class)));
    }

    private void validatePhoneNumber() {

        usersReference.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    sendVerificationCode();
                    phoneInput.setError(null);
                } else {
                    phoneInput.setError("Аккаунта с таким номером не существует");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("LoginActivity", "An error due getting user by phone number from database");
            }
        });
    }

    private void sendVerificationCode() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(this, "Код был отправлен", Toast.LENGTH_SHORT);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pinView.setText(code);
                verifySignInCode(code);
            }
            Log.d("VerificationActivity -> onVerificationCompleted", "verification completed successfully");
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Log.e("RegistrationActivity", "Invalid credentials were sent");
            } else if (e instanceof FirebaseTooManyRequestsException) {
                Log.e("RegistrationActivity", "The SMS quota for the project has been exceeded");
            }
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            sentCode = s;
        }
    };

    private void verifySignInCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sentCode, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("RegistrationActivity", "signInWithCredential:success");
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        usersReference.child(Objects.requireNonNull(firebaseUser.getPhoneNumber())).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                if (Role.DRIVER.equals(user.getRole())) {
                                    startActivity(new Intent(LoginActivity.this, DriverMainActivity.class));
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
                                }
                                finishAffinity();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        Log.e("RegistrationActivity", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Log.e("RegistrationActivity", "The verification code entered was invalid");
                        }
                    }
                });
    }
}
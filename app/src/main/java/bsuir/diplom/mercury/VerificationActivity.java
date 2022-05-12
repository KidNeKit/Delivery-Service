package bsuir.diplom.mercury;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import bsuir.diplom.mercury.entities.User;

public class VerificationActivity extends AppCompatActivity {

    private PinView pinView;

    private String phoneNumber;
    private String surname;
    private String name;
    private String sentCode;

    private final DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        pinView = findViewById(R.id.pin_view);
        Button loginButton = findViewById(R.id.login_button);

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        surname = getIntent().getStringExtra("surname");
        name = getIntent().getStringExtra("name");

        sendVerificationCode();

        loginButton.setOnClickListener(view -> {
            String enteredCode = pinView.getEditableText().toString();
            if (!enteredCode.isEmpty()) {
                verifySignInCode(enteredCode);
            }
        });
    }

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

                        User user = new User(phoneNumber, surname, name);
                        usersReference.child(phoneNumber).setValue(user);

                        startActivity(new Intent(this, MainPageActivity.class));
                    } else {
                        Log.e("RegistrationActivity", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Log.e("RegistrationActivity", "The verification code entered was invalid");
                        }
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
}
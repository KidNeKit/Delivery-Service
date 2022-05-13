package bsuir.diplom.mercury;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

import bsuir.diplom.mercury.entities.User;
import bsuir.diplom.mercury.utils.Utils;

public class RegistrationActivity extends AppCompatActivity {
    private TextInputLayout nameInput;
    private TextInputLayout surnameInput;
    private TextInputLayout phoneInput;
    private CountryCodePicker countryCodePicker;

    private String fullNumber;
    private String surname;
    private String name;

    private final DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameInput = findViewById(R.id.reg_name_input);
        surnameInput = findViewById(R.id.reg_surname_input);
        phoneInput = findViewById(R.id.reg_phone_input);
        countryCodePicker = findViewById(R.id.reg_country_code);
        Button registrationButton = findViewById(R.id.registration_button);
        Button toLoginButton = findViewById(R.id.go_to_login_activity_button);

        toLoginButton.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });

        registrationButton.setOnClickListener(view -> {
            if (isAllFieldsValid()) {
                User user = new User(fullNumber, surname, name);
                usersReference.child(fullNumber).setValue(user).addOnCompleteListener(task -> Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT));
            }
        });
    }

    private boolean isAllFieldsValid() {
        name = Objects.requireNonNull(nameInput.getEditText()).getText().toString();
        surname = Objects.requireNonNull(surnameInput.getEditText()).getText().toString();
        String phoneNumber = Objects.requireNonNull(phoneInput.getEditText()).getText().toString();
        fullNumber = countryCodePicker.getSelectedCountryCodeWithPlus() + phoneNumber;

        if (Utils.isEmptyString(name)) {
            nameInput.setError("Имя не может быть пустым");
        } else nameInput.setError(null);
        if (Utils.isEmptyString(surname)) {
            surnameInput.setError("Фамилия не может быть пустой");
        } else surnameInput.setError(null);
        if (Utils.isEmptyString(phoneNumber)) {
            phoneInput.setError("Номер телефона не может быть пустым");
        } else phoneInput.setError(null);

        return nameInput.getError() == null
                && surnameInput.getError() == null
                && phoneInput.getError() == null;
    }

    /*private void validateUniquePhoneNumber() {
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(fullNumber)) {
                    phoneInput.setError("Такой номер телефона уже зарегистрирован");
                } else phoneInput.setError(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("RegistrationActivity", "Cannot check is phone number unique");
            }
        });
    }*/
}
package bsuir.diplom.mercury;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

import bsuir.diplom.mercury.utils.Utils;

public class RegistrationActivity extends AppCompatActivity {
    private TextInputLayout nameInput;
    private TextInputLayout surnameInput;
    private TextInputLayout phoneInput;
    private CountryCodePicker countryCodePicker;

    private String fullNumber;
    private String surname;
    private String name;

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

        toLoginButton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

        registrationButton.setOnClickListener(view -> {
            if (isAllFieldsValid()) {
                startActivity(getIntentWithRegistrationExtras());
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

    private Intent getIntentWithRegistrationExtras() {
        Intent intent = new Intent(this, VerificationActivity.class);
        intent.putExtra("phoneNumber", fullNumber);
        intent.putExtra("surname", surname);
        intent.putExtra("name", name);
        return intent;
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
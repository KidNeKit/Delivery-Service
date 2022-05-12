package bsuir.diplom.mercury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*usernameInput = findViewById(R.id.login_username_input);
        passwordInput = findViewById(R.id.login_password_input);
        Button loginButton = findViewById(R.id.login_button);*/
        Button toRegistrationButton = findViewById(R.id.to_registration_button);

        toRegistrationButton.setOnClickListener(view -> startActivity(new Intent(this, RegistrationActivity.class)));
    }
}
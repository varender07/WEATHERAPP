package com.example.weatherapp;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    private EditText emailEdit, passwordEdit;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailEdit = findViewById(R.id.registeremail2);
        passwordEdit = findViewById(R.id.password4);
        registerButton = findViewById(R.id.registerButton2);

        // Set up register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailEdit.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEdit.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    passwordEdit.setError("Password must be at least 6 characters");
                    return;
                }

                // Register user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(register.this, task -> {
                            if (task.isSuccessful()) {
                                // Registration success
                                Toast.makeText(register.this, "Registration successful.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(register.this, login.class));
                                finish();
                            } else {
                                // If registration fails, display a message to the user.
                                Toast.makeText(register.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    Button button2, registerButton;
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button2 = findViewById(R.id.button2);
        emailEditText = findViewById(R.id.registeremail);
        registerButton = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        passwordEditText = findViewById(R.id.textView4);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");
                    return;
                }

                // Authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(login.this, "Authentication successful.",
                                        Toast.LENGTH_SHORT).show();
                                // Navigate to the main activity
                                startActivity(new Intent(login.this, MainActivity.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Set up register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the register activity
                startActivity(new Intent(login.this, register.class));
            }
        });
    }
}

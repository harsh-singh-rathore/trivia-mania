package com.example.triviamania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_login extends AppCompatActivity {

        EditText etLoginEmail;
        EditText etLoginPassword;
        TextView tvRegisterHere;
        Button btnLogin;

        FirebaseAuth mAuth;

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            etLoginEmail = findViewById(R.id.etEmail);
            etLoginPassword = findViewById(R.id.etPass);
            tvRegisterHere = findViewById(R.id.tvRegisterhere);
            btnLogin = findViewById(R.id.btnLogin);

            mAuth = FirebaseAuth.getInstance();


            btnLogin.setOnClickListener(view -> {
                loginUser();
            });
            tvRegisterHere.setOnClickListener(view ->{
                startActivity(new Intent(activity_login.this, activity_signup.class));
            });
        }

        private void loginUser(){
            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();

            if (TextUtils.isEmpty(email)){
                etLoginEmail.setError("Email cannot be empty");
                etLoginEmail.requestFocus();
            }else if (TextUtils.isEmpty(password)){
                etLoginPassword.setError("Password cannot be empty");
                etLoginPassword.requestFocus();
            }else{
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity_login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity_login.this, MainActivity.class));
                        }else{
                            Toast.makeText(activity_login.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }
package com.example.triviamania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class activity_signup extends AppCompatActivity {

    private Button signup;
    private FirebaseAuth mAuth;
    EditText etRegEmail;
    EditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = (Button) findViewById(R.id.signup_butt);
        tvLoginHere = (TextView) findViewById(R.id.login_here);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(view -> {
            createUser();
        });

        tvLoginHere.setOnClickListener(view ->
        {
            startActivity(new Intent(activity_signup.this, activity_login.class));

        });
        private void createUser(){
            String email = etRegEmail.getText().toString();
            String password = etRegPassword.getText().toString();

            if (TextUtils.isEmpty(email)){
                etRegEmail.setError("Email cannot be empty");
                etRegEmail.requestFocus();
            }else if (TextUtils.isEmpty(password)){
                etRegPassword.setError("Password cannot be empty");
                etRegPassword.requestFocus();
            }else{
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


}
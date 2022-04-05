package com.example.triviamania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class activity_signup extends AppCompatActivity {

        EditText etRegEmail;
        EditText etRegPassword;
        TextView tvLoginHere;
        Button btnRegister;
        EditText etName;
        EditText etConfPass;
        EditText phone;

        FirebaseAuth mAuth;
        FirebaseDatabase rootNode;
        DatabaseReference reference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            etRegEmail = findViewById(R.id.etRegEmail);
            etRegPassword = findViewById(R.id.etRegPass);
            tvLoginHere = findViewById(R.id.tvLoginHere);
            btnRegister = findViewById(R.id.btnRegister);
            etConfPass = findViewById(R.id.etConfRegPass);
            etName = findViewById(R.id.etRegName);
            phone = findViewById(R.id.phone);

            mAuth = FirebaseAuth.getInstance();

            btnRegister.setOnClickListener(view -> {
                String email = etRegEmail.getText().toString();
                String pass = etRegPassword.getText().toString();
                String score = "0";
                String confPass = etConfPass.getText().toString();
                String name = etName.getText().toString();
                String Phone = phone.getText().toString();
                if(pass.equals(confPass)){
                    // Write a message to the database
                    rootNode = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    reference = rootNode.getReference("users");


                    UserScoreClass user = new UserScoreClass(name, email, score);


                    createUser(user, Phone);
                } else {
                    Toast.makeText(getApplicationContext(), "Password Don't match", Toast.LENGTH_LONG).show();
                }


            });

            tvLoginHere.setOnClickListener(view ->{
                startActivity(new Intent(activity_signup.this, activity_login.class));
            });
        }

        private void createUser(UserScoreClass userScore, String Phone){
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
                            Toast.makeText(activity_signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            reference.child(Phone).setValue(userScore);
                            startActivity(new Intent(activity_signup.this, activity_login.class));
                        }else{
                            Toast.makeText(activity_signup.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }
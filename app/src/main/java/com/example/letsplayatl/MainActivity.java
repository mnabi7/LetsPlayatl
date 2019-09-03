package com.example.letsplayatl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireBaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        tvSignIn = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.signupbut);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()) {
                    emailId.setError("Please enter valid email.");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter valid password.");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields Are Empty.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    Task<AuthResult> result = mFireBaseAuth.createUserWithEmailAndPassword(email,pwd);
                    result.addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Registration Failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Registration Successful",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error Occurred...",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }

}

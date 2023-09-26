package com.dan.naari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLoginBtn =  findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);

        progressBar = findViewById(R.id.progressBar2);



        mLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;

            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required");
                return;

            }
            if(password.length() < 6) {
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);



            if (email.equals("a@gmail.com") && password.equals("password")) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

        });

        mCreateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Registration.class)));
    }
}


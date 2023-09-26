package com.dan.naari;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class Registration extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterbtn;
    TextView mCreateText;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterbtn = findViewById(R.id.registerbtn);
        mCreateText = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);

        mRegisterbtn.setOnClickListener(v -> {
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

        });
        mCreateText.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Login.class)));
    }
}
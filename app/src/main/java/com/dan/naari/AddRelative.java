package com.dan.naari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class AddRelative extends AppCompatActivity {
    //private static final int REQUEST_CALL = 1;
    DatabaseHelper myDB;
    Button btnAdd, btnView;
    EditText editText, editText2;
    ArrayList<Contacts> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relative);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        myDB = new DatabaseHelper(this);

        btnAdd.setOnClickListener(v -> {
            String name = editText.getText().toString();
            String phoneNo = editText2.getText().toString();
            if (editText.length() != 0) {
                AddData(name);
                AddData(phoneNo);
                editText.setText("");

                saveContactsDetails(name, phoneNo);
            } else {
                Toast.makeText(AddRelative.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
            }
        });
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(AddRelative.this, ViewListContents.class);
            startActivity(intent);
        });
    }

    private void saveContactsDetails(String name, String phoneNo) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Contacts", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        contacts.add(new Contacts(name,phoneNo));
        String json = gson.toJson(contacts);
        editor.putString("contactsData", json);
        editor.apply();
    }

    public void AddData(String newEntry) {
        boolean insertData = myDB.addData(newEntry);

        if (insertData) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}
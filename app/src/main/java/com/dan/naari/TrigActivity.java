package com.dan.naari;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrigActivity extends AppCompatActivity {

    Bundle bundle;
    String phoneNos = "";
    String message;
    ArrayList<String> contacts = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    int count = 0;
    int key, keyEvent;
    String[] permissionsStr = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trig);
        bundle = getIntent().getExtras();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Contacts", MODE_PRIVATE);
        phoneNos = sharedPreferences.getString("contactsData", "");
        try {
            JSONArray ja_data = new JSONArray(phoneNos);
            int length = ja_data.length();
            for (int i = 0; i < length; i++) {
                JSONObject jObj = ja_data.getJSONObject(i);
                contacts.add(jObj.getString("phoneNo"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        permissionsStr = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION};

        if (!hasPermissions(TrigActivity.this, permissionsStr)) {
            ActivityCompat.requestPermissions(this, permissionsStr, 1);
        }
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            key = keyCode;
            keyEvent = KeyEvent.KEYCODE_VOLUME_UP;
            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                    if (key == keyEvent) {
                        count++;
                    } else {
                        count = 0;
                    }
                }

                public void onFinish() {
                    if (count == 2) {
                        Toast.makeText(TrigActivity.this, "2 sec over", Toast.LENGTH_SHORT).show();
                        sendLocationMessage();
                    }
                    count = 0;
                }
            }.start();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void sendLocationMessage() {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            message = "Please help me!!! My current location is: http://maps.google.com/maps?z=12&t=m&q=loc:" + location.getLatitude() + "+" + location.getLongitude();

                            for (String contact : contacts) {
                                sendSMS(contact, message);
                                Toast.makeText(this, "Location sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                                    100);
                        }
                    });

    }

    private void sendSMS(String phoneNo, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }
}

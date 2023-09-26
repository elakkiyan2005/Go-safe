package com.dan.naari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    int upButtonCount = 0;
    int downButtonCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addRelative(View v){
        Intent i = new Intent(getApplicationContext(), AddRelative.class);
        startActivity(i);
    }
    public void helplineNumbers(View v){
        Intent i = new Intent(getApplicationContext(), helplineCall.class);
        startActivity(i);
    }
    public void triggers(View v){
        Intent i = new Intent(getApplicationContext(), TrigActivity.class);
        startActivity(i);
    }
    public void HowTo(View v){
        Intent i = new Intent(getApplicationContext(), HowToSwipe.class);
        startActivity(i);
    }
    public void LogOut(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        // Start the login activity
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;
        action = event.getAction();
        keycode = event.getKeyCode();
        switch (keycode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(KeyEvent.ACTION_UP == action) {
                    upButtonCount++;
                    String S1 = String.valueOf(upButtonCount);
                    Log.d("upButton", S1);
                }
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(KeyEvent.ACTION_DOWN == action) {
                    downButtonCount++;
                    String S2 = String.valueOf(downButtonCount);
                    Log.d("downButton", S2);
                }
        }
        return super.dispatchKeyEvent(event);
    }
}
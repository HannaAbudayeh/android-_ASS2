package com.example.ass2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    EditText name;
    EditText password;
    Button signup;
    Button back;
    TextView errorTextView;
    private static String PREFS_NAME = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SharedPreferences save = getSharedPreferences(PREFS_NAME, 0);
        name = findViewById(R.id.name);
        signup = findViewById(R.id.sign);
        errorTextView = findViewById(R.id.fin);
        back = findViewById(R.id.back);
        password = findViewById(R.id.pass);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = name.getText().toString();
                String pass = password.getText().toString();

                if (user.isEmpty()) {
                    errorTextView.setText("Write Name");
                    return;
                }
                if (pass.isEmpty()) {
                    errorTextView.setText("Write Password");
                    return;
                }
                if (pass.length() >= 8) {
                    SharedPreferences.Editor editor = save.edit();
                    editor.putString("username", user);
                    editor.putString("password", pass);
                    editor.apply();
                    Log.d("CreateAcount", "Account created - Username: " + user + ", Password: " + pass);
                    Intent intent = new Intent(signup.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    errorTextView.setText("Password must be 8 Char");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
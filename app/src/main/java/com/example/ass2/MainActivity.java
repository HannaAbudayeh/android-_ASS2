package com.example.ass2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText password;
    TextView errorTextView;
    Button sign;
    Button Login;
    CheckBox Remember;
    private static String PREFS_NAME = "prefs";
    private static String PREF_REMEMBER = "PREF_REMEMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences save = getSharedPreferences(PREFS_NAME, 0);
        name = findViewById(R.id.name);
        password = findViewById(R.id.priority);
        errorTextView = findViewById(R.id.fin);
        sign = findViewById(R.id.sign);
        Login = findViewById(R.id.togg);
        Remember = findViewById(R.id.checkBox2);

        boolean RememberM = save.getBoolean(PREF_REMEMBER, false);
        Remember.setChecked(RememberM);
        if (RememberM) {
            String namesaved = save.getString("username", "username");
            String passsaved = save.getString("password", "password");
            name.setText(namesaved);
            password.setText(passsaved);
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = save.edit();
                editor.putBoolean(PREF_REMEMBER, Remember.isChecked());
                editor.apply();

                String usernamelogin = name.getText().toString();
                String passlogin = password.getText().toString();

                String namesaved = save.getString("username", "username");
                String passsaved = save.getString("password", "password");

                if (usernamelogin.equals(namesaved) && passlogin.equals(passsaved)) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                } else if (usernamelogin.equals("")) {
                    errorTextView.setText("Name Empty");
                } else if (passlogin.equals("")) {
                    errorTextView.setText("Password Empty");
                } else if (!passlogin.equals(passsaved)) {
                    errorTextView.setText("Incorrect Password");
                } else if (!usernamelogin.equals(namesaved)) {
                    errorTextView.setText("Incorrect Name");
                }

            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });
    }
}
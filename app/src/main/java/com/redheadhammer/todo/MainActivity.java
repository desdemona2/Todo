package com.redheadhammer.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    TextInputEditText username, password;
    CheckBox autologin;
    Button submit, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPref = getSharedPreferences("autologin", MODE_PRIVATE);

        /* launch register page if 1st run of the app */
        if (sharedPref.getBoolean("isFirstRun", true)) {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        }

        /* auto-login if set in past */
        if (sharedPref.getString("login", null) != null) {
            Intent intent = new Intent(MainActivity.this, TodoList.class);
            startActivity(intent);
        }

        /* setting view objects */
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        autologin = findViewById(R.id.autologin);
        submit = findViewById(R.id.submit);
        register = findViewById(R.id.register);

        /* get values from login screen */
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());

        register.setOnClickListener(this::onClick);

    }

    private void onClick(View view) {
        Toast.makeText(MainActivity.this, R.string.pending, Toast.LENGTH_SHORT).show();
    }
}
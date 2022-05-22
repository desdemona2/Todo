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
        } else if (sharedPref.getBoolean("login", false)) {
            /* auto-login if set true in past */
            Intent intent = new Intent(MainActivity.this, TodoList.class);
            startActivity(intent);
        }
        /* attach views with their objects */
        setObjects();

        submit.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());

        // check if input user-pass is correct
        if (check(user, pass)) {
            // move user to the actual notes page
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

            if (autologin.isChecked()) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("login", true);
                editor.apply();
            }

            Intent intent = new Intent(MainActivity.this, TodoList.class);
            startActivity(intent);
        } else {
            username.setError("incorrect username or password");

            Toast.makeText(this, R.string.wrong_user, Toast.LENGTH_SHORT).show();
        }
    }

    private void setObjects(){
        /* setting view objects */
        username = this.findViewById(R.id.username);
        password = this.findViewById(R.id.password);
        autologin = this.findViewById(R.id.autologin);
        submit = this.findViewById(R.id.submit);
        register = this.findViewById(R.id.register);
    }

    private boolean check(String user, String pass){
        String real_user = sharedPref.getString("userName", "");
        String real_pass = sharedPref.getString("password", "");
        return real_pass.equals(pass) && real_user.equals(user);
    }
}
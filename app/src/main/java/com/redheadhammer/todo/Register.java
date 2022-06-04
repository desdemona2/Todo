package com.redheadhammer.todo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText userName, password, passRepeat;
    Button register;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.activity_register);

        /* getShared preferences for autoLogin and saving username and password */
        sharedPref = getSharedPreferences("autologin", MODE_PRIVATE);

        /* setting view objects from activity_register */
        userName = this.findViewById(R.id.username);
        password = this.findViewById(R.id.password);
        passRepeat = this.findViewById(R.id.passwordRepeat);
        register = this.findViewById(R.id.register);

        /* onClick listener for register button */
        register.setOnClickListener(this::onClick);

    }

    public void onClick(View view) {
        /* get username, password value from view objects */
        final String user = String.valueOf(userName.getText());
        final String pass = String.valueOf(password.getText());
        final String passR = String.valueOf(passRepeat.getText());

        /* Dialog builder for confirming account creating */
        if (pass.equals(passR)) alert(user, pass);
        else {
            /* on password field mismatch setError on */
            passRepeat.setError("Mismatch in passwords");
            passRepeat.setText("");
        }
    }

    private void alert(String user, String pass) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Confirm").setMessage(R.string.dialog_register);

        alert.setNegativeButton("No", (dialogInterface, i) -> {
            userName.setText("");
            password.setText("");
            passRepeat.setText("");
            dialogInterface.cancel();
        });

        alert.setPositiveButton("Yes", (dialogInterface, i) -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("userName", user);
            editor.putString("password", pass);
            editor.putBoolean("isFirstRun", false);
            editor.apply();
            /* go back to login activity after creating username password */
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        });

        alert.show();
    }
}
package com.redheadhammer.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TodoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
    }

    @Override
    public void onBackPressed() {
        // will clear the back stack so back press will close the application
        finishAffinity();
    }
}
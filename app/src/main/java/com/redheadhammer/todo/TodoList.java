package com.redheadhammer.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoList extends AppCompatActivity {

    Button addNew;
    RecyclerView recycler;
    ArrayList<String[]> itemList;
    CustomRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Todo List");
        setContentView(R.layout.todo_list);

        addNew = this.findViewById(R.id.addTask);
        recycler = this.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        itemList = FileHelper.readData(this);

        adapter = new CustomRecyclerAdapter(itemList);
        recycler.setAdapter(adapter);

        addNew.setOnClickListener(this::addNote);
        addNew.setOnLongClickListener(this::disableAutoLogin);
    }

    public boolean disableAutoLogin(View view) {
        SharedPreferences autoLogin = getSharedPreferences("autologin", MODE_PRIVATE);
        SharedPreferences.Editor editor = autoLogin.edit();
        editor.putBoolean("login", false);
        editor.apply();
        Toast.makeText(this, "Autologin is disabled ", Toast.LENGTH_LONG).show();
        return true;
    }

    private void addNote(View view){
        CustomDialogFragment frag = new CustomDialogFragment(this, itemList, adapter);
        frag.showNow(getSupportFragmentManager(), "AddNewNote");
    }

    @Override
    public void onBackPressed() {
        // will clear the back stack so back press will close the application
        finishAffinity();
    }
}
package com.redheadhammer.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TodoList extends AppCompatActivity {

    Button addNew;
    RecyclerView recycler;
    ArrayList<String[]> itemList;
    CustomRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);

        addNew = this.findViewById(R.id.addTask);
        recycler = this.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        itemList = FileHelper.readData(this);

        adapter = new CustomRecyclerAdapter(itemList);
        recycler.setAdapter(adapter);

        addNew.setOnClickListener(this::addNote);
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
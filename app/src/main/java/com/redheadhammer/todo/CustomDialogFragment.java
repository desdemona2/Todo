package com.redheadhammer.todo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class CustomDialogFragment extends DialogFragment {

    Context context;
    EditText title, detail;
    Button button;
    ArrayList<String[]> itemList;
    String[] items;
    CustomRecyclerAdapter adapter;

    public CustomDialogFragment(Context context, ArrayList<String[]> itemList,
                                CustomRecyclerAdapter adapter) {
        this.itemList = itemList;
        this.context = context;
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        title = view.findViewById(R.id.title);
        detail = view.findViewById(R.id.detail);
        button = view.findViewById(R.id.button);
        items = new String[2];

        button.setOnClickListener( view1 -> {
            Log.d("ADD_NOTE", "Add note button is pressed");
            String head = String.valueOf(title.getText());
            String info = String.valueOf(detail.getText());
            if (!head.isEmpty()) {
                items[0] = head;
                items[1] = info;
                Log.d("ADD_NOTE", "item1 " + head);
                Log.d("ADD_NOTE", "item2 " + info);
                itemList.add(items);
                int size = itemList.size();
                FileHelper.writeData(itemList, context);
                adapter.notifyItemInserted(size-1);
                this.dismiss();
            }
        });

        return view;
    }
}

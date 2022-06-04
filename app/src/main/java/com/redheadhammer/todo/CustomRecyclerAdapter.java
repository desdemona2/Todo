package com.redheadhammer.todo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {

    private final ArrayList<String[]> values;

    public CustomRecyclerAdapter(ArrayList<String[]> values) {
        this.values = values;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,
                parent, false);
        return new CustomViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(values.get(position)[0]);
        holder.detail.setText(values.get(position)[1]);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title, detail;
        ConstraintLayout constraint;
        private CustomRecyclerAdapter customRecyclerAdapter;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.detail);
            constraint = itemView.findViewById(R.id.constraint);

            constraint.setOnClickListener(this::deleteEntry);
        }

        public void deleteEntry(View view) {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setTitle("Delete").setMessage("Are you sure you want to delete this note");
            alert.setPositiveButton("yes", (dialogInterface, i) -> {
                customRecyclerAdapter.values.remove(getAdapterPosition());
                customRecyclerAdapter.notifyItemRemoved(getAdapterPosition());
                dialogInterface.cancel();
                FileHelper.writeData(customRecyclerAdapter.values, view.getContext());
            });
            alert.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
            alert.show();
        }

        public CustomViewHolder linkAdapter(CustomRecyclerAdapter customRecyclerAdapter) {
            this.customRecyclerAdapter = customRecyclerAdapter;
            return this;
        }
    }
}

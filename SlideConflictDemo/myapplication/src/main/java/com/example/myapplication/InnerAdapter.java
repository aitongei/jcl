package com.example.myapplication;

/**
 * @author jcl
 */
// InnerAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> innerItems;

    public InnerAdapter(Context context, ArrayList<String> innerItems) {
        this.context = context;
        this.innerItems = innerItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inner_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String innerItem = innerItems.get(position);
        holder.innerText.setText(innerItem);
    }

    @Override
    public int getItemCount() {
        return innerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView innerText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            innerText = itemView.findViewById(R.id.innerText);
        }
    }
}
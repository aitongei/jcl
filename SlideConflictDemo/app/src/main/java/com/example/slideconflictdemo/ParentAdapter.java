package com.example.slideconflictdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jcl
 */
public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {

    private final List<List<String>> datas ;

    public ParentAdapter(List<List<String>> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChildAdapter childAdapter = new ChildAdapter();
        holder.childRecyclerView.setAdapter(childAdapter);
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView childRecyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
        }
    }
}

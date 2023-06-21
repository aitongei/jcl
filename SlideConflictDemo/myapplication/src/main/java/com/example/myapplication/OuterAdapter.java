package com.example.myapplication;

/**
 * @author jcl
 */
// OuterAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OuterAdapter extends RecyclerView.Adapter<OuterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OuterItem> outerItems;
    private RecyclerView currentInnerRecyclerView;


    public OuterAdapter(Context context, ArrayList<OuterItem> outerItems) {
        this.context = context;
        this.outerItems = outerItems;
    }

    public RecyclerView getCurrentInnerRecyclerView() {
        return currentInnerRecyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.outer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OuterItem outerItem = outerItems.get(position);
        holder.outerText.setText(outerItem.getOuterText());

        InnerAdapter innerAdapter = new InnerAdapter(context, outerItem.getInnerItems());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        holder.innerRecyclerView.setLayoutManager(layoutManager);
        holder.innerRecyclerView.setAdapter(innerAdapter);

        currentInnerRecyclerView = holder.innerRecyclerView;

    }

    @Override
    public int getItemCount() {
        return outerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView outerText;
        RecyclerView innerRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            outerText = itemView.findViewById(R.id.outerText);
            innerRecyclerView = itemView.findViewById(R.id.innerRecyclerView);
        }
    }
}

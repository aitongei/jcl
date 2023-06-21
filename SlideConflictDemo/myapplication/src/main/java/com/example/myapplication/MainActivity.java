package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// MainActivity.java

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private OuterRecyclerView outerRecyclerView;
    private OuterAdapter outerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outerRecyclerView = findViewById(R.id.outerRecyclerView);

        // 创建外层RecyclerView的布局管理器和适配器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        outerRecyclerView.setLayoutManager(layoutManager);
        outerAdapter = new OuterAdapter(this, getOuterItems());
        outerRecyclerView.setOuterAdapter(outerAdapter);
        outerRecyclerView.setAdapter(outerAdapter);
    }

    // 模拟外层RecyclerView的数据源
    private ArrayList<OuterItem> getOuterItems() {
        ArrayList<OuterItem> outerItems = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            OuterItem outerItem = new OuterItem("Outer Item " + i);
            outerItem.setInnerItems(getInnerItems());
            outerItems.add(outerItem);
        }

        return outerItems;
    }

    // 模拟内层RecyclerView的数据源
    private ArrayList<String> getInnerItems() {
        ArrayList<String> innerItems = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            innerItems.add("Inner Item " + i);
        }

        return innerItems;
    }
}
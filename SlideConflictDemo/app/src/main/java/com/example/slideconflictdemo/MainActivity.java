package com.example.slideconflictdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ParentRecyclerView parentRecyclerView;
    private ParentAdapter parentAdapter;
    List<List<String>> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentRecyclerView = findViewById(R.id.parentRecyclerView);
        initData();
        parentAdapter = new ParentAdapter(datas);
        parentRecyclerView.setAdapter(parentAdapter);
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            List<String > data = new ArrayList<>();
            data.add("数据"+1);
            data.add("数据"+2);
            data.add("数据"+3);
            datas.add(data);
        }
    }
}

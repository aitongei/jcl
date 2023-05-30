package com.example.magicindicatordemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends Fragment {

    List<String > list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two,container,false);


        initDatas();
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(new MyListViewAdapter(list,getContext()));
        listView.addHeaderView(inflater.inflate(R.layout.header_image,container,false));
        return view;
    }

    private void initDatas() {
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
    }
}

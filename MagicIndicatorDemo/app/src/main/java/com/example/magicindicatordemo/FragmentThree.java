package com.example.magicindicatordemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;

import java.util.ArrayList;
import java.util.List;

public class FragmentThree extends Fragment {

    private XRefreshView xRefreshView;
    private RecyclerView mRecyclerView;
    private List<String> datas = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        initData();

        xRefreshView = view.findViewById(R.id.custom_view);
        mRecyclerView = view.findViewById(R.id.id_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0){
                    return 2;
                }
                return 1;
            }
        });
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(12));
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(datas,this));
        ListView listView = new ListView(getContext());


        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                // 下拉刷新时被调用
                refreshData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                // 上拉加载更多时被调用
                loadMoreData();
            }

        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initData() {
        datas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            datas.add("位置是" + i);
        }
    }

    private void refreshData() {
        // 执行数据刷新操作
        // ...

        // 完成后，调用stopRefresh结束刷新
        xRefreshView.stopRefresh();
    }

    private void loadMoreData() {
        // 执行数据加载操作
        // ...

        // 完成后，调用stopLoadMore结束加载更多
        xRefreshView.stopLoadMore();
    }
}
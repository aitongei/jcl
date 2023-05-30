package com.example.magicindicatordemo;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

//为recycleView设置间距
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        if(parent.getLayoutManager() instanceof GridLayoutManager || parent.getLayoutManager() instanceof StaggeredGridLayoutManager){
            if(parent.getChildAdapterPosition(view) < 2){
                outRect.top=space;
            }
        }else{
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }
}

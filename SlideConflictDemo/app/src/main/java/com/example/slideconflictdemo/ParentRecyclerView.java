package com.example.slideconflictdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author jcl
 */
public class ParentRecyclerView extends RecyclerView {
    public ParentRecyclerView(Context context) {
        super(context);
    }

    public ParentRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        View targetView = findChildViewUnder(e.getX(), e.getY());
        if (targetView instanceof RecyclerView && e.getAction() == MotionEvent.ACTION_MOVE) {
            return false;
        }
        return super.onInterceptTouchEvent(e);
    }
}

package com.example.myapplication;

/**
 * @author jcl
 */
// OuterRecyclerView.java

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class OuterRecyclerView extends RecyclerView {
    private float mStartX;
    private float mStartY;

    private OuterAdapter outerAdapter;

    public void setOuterAdapter(OuterAdapter outerAdapter) {
        this.outerAdapter = outerAdapter;
    }

    public OuterRecyclerView(Context context) {
        super(context);
    }

    public OuterRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OuterRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean isChildScrollable() {
        RecyclerView innerRecyclerView = outerAdapter.getCurrentInnerRecyclerView();
        if (innerRecyclerView != null) {
            // 如果子RecyclerView不能继续向下滑动，返回false
            return innerRecyclerView.canScrollVertically(1) && innerRecyclerView.canScrollVertically(-1);
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        float deltaY = e.getY() - mStartY;

        RecyclerView innerRecyclerView = outerAdapter.getCurrentInnerRecyclerView();
        if (innerRecyclerView == null) {
            return super.onInterceptTouchEvent(e);
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = e.getX();
                mStartY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 子View是否滑动到顶部
                boolean isChildScrollToTop = !innerRecyclerView.canScrollVertically(-1);
                // 子View是否滑动到底部
                boolean isChildScrollToBottom = !innerRecyclerView.canScrollVertically(1);

                if (isChildScrollToTop && deltaY > 0) {
                    // 如果子View滑动到了顶部并且手势仍在向下滑动
                    return true;
                } else if (isChildScrollToBottom && deltaY < 0) {
                    // 如果子View滑动到了底部并且手势仍在向上滑动
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(e);
    }

}
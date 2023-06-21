package com.example.draggable;

/**
 * @author jcl
 */

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DraggableBoundaryView extends RelativeLayout {
    private int lastX;
    private int lastY;
    // 获取子View，这里假设子View的id是child_btn
    View childButton ;

    public DraggableBoundaryView(Context context) {
        super(context);
    }

    public DraggableBoundaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableBoundaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 内部方法，判断触摸事件是否落在子View上
    private boolean interceptTouchEventOnChildView(MotionEvent event, View childView) {
        if (childView == null) {
            return false;
        }
        Rect rect = new Rect();
        childView.getHitRect(rect);
        return rect.contains((int) event.getX(), (int) event.getY());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        // 如果触摸事件在子View上，让子View处理触摸事件
//        if (interceptTouchEventOnChildView(event, childButton)) {
//            return childButton.dispatchTouchEvent(event);
//        }
        // 如果触摸事件在子View上，让子View处理触摸事件
        if (interceptTouchEventOnChildView(event, childButton)) {
            childButton.dispatchTouchEvent(event);
            return true;
        }

        // 获取当前触摸点的坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                // 记录触摸点坐标
                lastX = x;
                lastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // 计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;

                // 通过layout()方法移动view
                int left = getLeft() + offsetX;
                int top = getTop() + offsetY;
                int right = getRight() + offsetX;
                int bottom = getBottom() + offsetY;

                // 超出屏幕边界处理
                ViewGroup parent = (ViewGroup) getParent();
                Rect rect = new Rect();
                parent.getDrawingRect(rect);

                if (left < rect.left) {
                    right = rect.left + right - left;
                    left = rect.left;
                }

                if (right > rect.right) {
                    left = rect.right - (right - left);
                    right = rect.right;
                }

                if (top < rect.top) {
                    bottom = rect.top + bottom - top;
                    top = rect.top;
                }

                if (bottom > rect.bottom) {
                    top = rect.bottom - (bottom - top);
                    bottom = rect.bottom;
                }

                layout(left, top, right, bottom);

                // 重新设置初始坐标
                lastX = x;
                lastY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 直接修改类级变量 childButton 的值
        childButton = findViewById(R.id.button);

        childButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理点击事件
                Toast.makeText(getContext(), "点击了按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

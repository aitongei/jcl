package com.example.test2;

import android.content.Context;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

        // 如果点击事件已经被处理，则不再触发 TextView 的 onClick 方法
        if (result) {
            return true;
        }

        // 获取点击位置
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= getTotalPaddingLeft();
        y -= getTotalPaddingTop();

        x += getScrollX();
        y += getScrollY();

        Layout layout = getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        // 获取点击位置的 Span
        Spannable spannable = (Spannable) getText();
        ClickableSpan[] link = spannable.getSpans(off, off, ClickableSpan.class);

        // 如果有 ClickableSpan，则不再触发 TextView 的 onClick 方法
        if (link.length != 0) {
            return true;
        }

        return result;
    }
}

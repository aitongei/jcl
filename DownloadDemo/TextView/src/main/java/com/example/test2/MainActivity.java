package com.example.test2;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        String text = "Hello, World!";
        SpannableString spannableString = new SpannableString(text);

        // 设置第1-5个字符的颜色为红色
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置第7-13个字符的大小为2倍
        spannableString.setSpan(new RelativeSizeSpan(2f), 6, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置第7-13个字符的下划线和点击事件
        spannableString.setSpan(new UnderlineSpan(), 6, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, "Clicked on 'World!'", Toast.LENGTH_SHORT).show();
            }
        }, 6, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 使用自定义的MovementMethod
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView.setText(spannableString);

        // 设置TextView本身的点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked on TextView", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class CustomLinkMovementMethod extends LinkMovementMethod {
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                int x = (int) event.getX();
//                int y = (int) event.getY();
//
//                x -= widget.getTotalPaddingLeft();
//                y -= widget.getTotalPaddingTop();
//
//                x += widget.getScrollX();
//                y += widget.getScrollY();
//
//                Layout layout = widget.getLayout();
//                int line = layout.getLineForVertical(y);
//                int off = layout.getOffsetForHorizontal(line, x);
//
//                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
//
//                if (link.length != 0) {
//                    link[0].onClick(widget);
//                    return true;
//                }
//            }
//            return super.onTouchEvent(widget, buffer, event);
            return true;
        }
    }
}

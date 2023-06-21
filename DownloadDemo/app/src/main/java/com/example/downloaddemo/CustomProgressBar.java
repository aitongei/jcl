package com.example.downloaddemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author jcl
 */
// 这个自定义view有个bug，该进度条在最开始没有走完最左边的半圆时，高度超过背景的半圆了，该怎么修改?可以使用clip裁剪等方式实现进度条的走动
public class CustomProgressBar extends View {
    private Paint mBackgroundPaint, mProgressPaint, mTextPaint;
    private RectF mRectF, mProgressRectF;
    private float mProgress = 0;
    private String mText = "下载";

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.GRAY);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(Color.BLUE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(40);

        mRectF = new RectF();
        mProgressRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        mRectF.set(0, 0, width, height);
        mProgressRectF.set(0, 0, (mProgress / 100) * width, height);

        canvas.drawRoundRect(mRectF, height / 2, height / 2, mBackgroundPaint);
        canvas.drawRoundRect(mProgressRectF, height / 2, height / 2, mProgressPaint);

        float textWidth = mTextPaint.measureText(mText);
        float x = width / 2 - textWidth / 2;
        float y = (height / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2);
        canvas.drawText(mText, x, y, mTextPaint);
    }

    public void setProgress(int progress) {
        mProgress = Math.min(progress, 100);
        mText = mProgress == 100 ? "下载完成" : "暂停";   //只是文字显示为”暂停“，实际上是下载中
        invalidate();
    }

    public String getText(){
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
        invalidate();
    }
}

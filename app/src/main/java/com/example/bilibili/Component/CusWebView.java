package com.example.bilibili.Component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * 通过绘制实现 圆角，适用所有view
 */

public class CusWebView extends WebView
{
    private String TAG = "CusWebView";
    private Paint paint1;
    private Paint paint2;
    private float m_radius = 10;
    private int width = 100;
    private int height = 100;
    private int x;
    private int y;

    public CusWebView(Context context)
    {
        super(context);
        init(context);
    }

    public CusWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public CusWebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setAntiAlias(true);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    public void setRadius(int w, int h, float radius)
    {
        m_radius = radius;
        width = w;
        height = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //判断 避免 width，height值为0,否则Bitmap.createBitmap()报错
        if (getMeasuredWidth() != 0)
        {
            width = getMeasuredWidth();
        }
        if (getMeasuredHeight() != 0)
        {
            height = getMeasuredHeight();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        Log.i(TAG, "--draw()-width =" + width);
        Log.i(TAG, "--draw()-height =" + height);
        x = this.getScrollX();
        y = this.getScrollY();
        Bitmap bitmap = Bitmap.createBitmap(x + width, y + height,
                Bitmap.Config.RGB_565);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        drawLeftUp(canvas2);
        drawRightUp(canvas2);
        drawLeftDown(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        bitmap.recycle();
    }

    private void drawLeftUp(Canvas canvas)
    {
        Path path = new Path();
        path.moveTo(x, m_radius);
        path.lineTo(x, y);
        path.lineTo(m_radius, y);
        path.arcTo(new RectF(x, y, x + m_radius * 2, y + m_radius * 2), -90,
                -90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawLeftDown(Canvas canvas)
    {
        Path path = new Path();
        path.moveTo(x, y + height - m_radius);
        path.lineTo(x, y + height);
        path.lineTo(x + m_radius, y + height);
        path.arcTo(new RectF(x, y + height - m_radius * 2, x + m_radius * 2, y
                + height), 90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawRightDown(Canvas canvas)
    {
        Path path = new Path();
        path.moveTo(x + width - m_radius, y + height);
        path.lineTo(x + width, y + height);
        path.lineTo(x + width, y + height - m_radius);
        path.arcTo(new RectF(x + width - m_radius * 2, y + height - m_radius
                * 2, x + width, y + height), 0, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawRightUp(Canvas canvas)
    {
        Path path = new Path();
        path.moveTo(x + width, y + m_radius);
        path.lineTo(x + width, y);
        path.lineTo(x + width - m_radius, y);
        path.arcTo(new RectF(x + width - m_radius * 2, y, x + width, y
                + m_radius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }
}


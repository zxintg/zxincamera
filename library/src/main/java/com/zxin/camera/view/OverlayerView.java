package com.zxin.camera.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

/**
 * 希望有一天可以开源出来  org.zhx
 *
 * @author zhx
 * @version 1.0, 2015-11-15 下午7:11:49
 */
public class OverlayerView extends android.support.v7.widget.AppCompatImageView {
    private Paint mLinePaint;
    private Rect mCenterRect = null;
    private Paint paint;

    public OverlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        //Point p = new Point(SystemInfoUtil.getScreenWidth(), SystemInfoUtil.getScreenHeight()-SystemInfoUtil.getStatusBarHeight());
    }

    private void initPaint() {
        // 绘制中间透明区域矩形边界的Paint
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLUE);
        mLinePaint.setStyle(Style.STROKE);
        mLinePaint.setStrokeWidth(5f);
        mLinePaint.setAlpha(0);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCenterRect == null)
            return;

        paint.setColor(Color.WHITE);
        paint.setAlpha(150);

        canvas.drawRect(mCenterRect.left - 2, mCenterRect.bottom, mCenterRect.left + 50, mCenterRect.bottom + 2, paint);// 左下 底部
        canvas.drawRect(mCenterRect.left - 2, mCenterRect.bottom - 50, mCenterRect.left, mCenterRect.bottom, paint);// 左下 左侧
        canvas.drawRect(mCenterRect.right - 50, mCenterRect.bottom, mCenterRect.right + 2, mCenterRect.bottom + 2, paint);// 右下 右侧
        canvas.drawRect(mCenterRect.right, mCenterRect.bottom - 50, mCenterRect.right + 2, mCenterRect.bottom, paint);// 右下 底部
        canvas.drawRect(mCenterRect.left - 2, mCenterRect.top - 2, mCenterRect.left + 50, mCenterRect.top, paint);// 左上 顶部
        canvas.drawRect(mCenterRect.left - 2, mCenterRect.top, mCenterRect.left, mCenterRect.top + 50, paint);// 左上 侧边
        canvas.drawRect(mCenterRect.right - 50, mCenterRect.top - 2, mCenterRect.right + 2, mCenterRect.top, paint);// 右上 顶部
        canvas.drawRect(mCenterRect.right, mCenterRect.top,mCenterRect.right + 2, mCenterRect.top + 50, paint);// 右上 右侧
        // 绘制目标透明区域
        canvas.drawRect(mCenterRect, mLinePaint);
        super.onDraw(canvas);
    }

    public void setmCenterRect(Rect mCenterRect) {
        this.mCenterRect = mCenterRect;
    }

}


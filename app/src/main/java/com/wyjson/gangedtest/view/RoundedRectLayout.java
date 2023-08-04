package com.wyjson.gangedtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.wyjson.gangedtest.R;

/**
 * https://www.cnblogs.com/longjunhao/p/9056128.html
 */
public class RoundedRectLayout extends FrameLayout {
    private Path mClip;
    private float mRadius;
    private float mRadiusMarginTop;
    private float mRadiusMarginLeft;
    private float mRadiusMarginRight;
    private float mRadiusMarginBottom;

    public RoundedRectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundedRectLayout(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundedRectLayout, 0, 0);
            mRadius = a.getDimensionPixelSize(R.styleable.RoundedRectLayout_radius, 0);
            mRadiusMarginTop = a.getDimensionPixelSize(R.styleable.RoundedRectLayout_radius_marginTop, 0);
            mRadiusMarginLeft = a.getDimensionPixelSize(R.styleable.RoundedRectLayout_radius_marginLeft, 0);
            mRadiusMarginRight = a.getDimensionPixelSize(R.styleable.RoundedRectLayout_radius_marginRight, 0);
            mRadiusMarginBottom = a.getDimensionPixelSize(R.styleable.RoundedRectLayout_radius_marginBottom, 0);
            a.recycle();
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mRadius > 0) {
            mClip = new Path();
            RectF rectRound = new RectF(mRadiusMarginLeft, mRadiusMarginTop, w - mRadiusMarginRight, h - mRadiusMarginBottom);
            mClip.addRoundRect(rectRound, mRadius, mRadius, Direction.CW);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int saveCount = canvas.save();
        if (mRadius > 0) {
            canvas.clipPath(mClip);
        }
        super.dispatchDraw(canvas);
        canvas.restoreToCount(saveCount);
    }
}

package com.wyjson.gangedtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class SlideScrollView extends HorizontalScrollView {

    public SlideScrollView(Context context) {
        super(context);
        initView();
    }

    public SlideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SlideScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public SlideScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);

        setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && getScrollX() != 0) {
                if (onListener != null)
                    onListener.onBack();
            }
            return false;
        });
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (onListener != null)
            onListener.onScrollChanged(this, x, y, oldX, oldY);
        if (x == 0) {
            setScroll(false);
            if (onListener != null)
                onListener.onComplete();
        }
    }

    @Override
    public void fling(int velocityX) {
        super.fling(velocityX / 1000);
    }

    private onListener onListener;

    public void setOnListener(onListener onListener) {
        this.onListener = onListener;
    }

    public interface onListener {
        void onBack();

        void onComplete();

        void onScrollChanged(SlideScrollView scrollView, int x, int y, int oldX, int oldY);
    }

    private boolean isScroll = true;

    public void setScroll(boolean scroll) {
        this.isScroll = scroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onTouchEvent(ev);
        } else {
            return true;
        }
    }

}

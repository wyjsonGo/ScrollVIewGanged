package com.wyjson.gangedtest;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ScrollView Ganged
 * <p>
 * Created by Wyjson on 2019/8/7.
 */
public class MainActivity extends AppCompatActivity implements ScrollViewListener {

    private ObservableScrollView sv1, sv2;

    private int distance;

    private TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        //this 150dip is @+id/v_scroll width
        distance = getResources().getDimensionPixelOffset(R.dimen.v_scroll_width);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        tvRight = findViewById(R.id.tv_right);

        sv1 = findViewById(R.id.sv_1);
        sv1.setScrollViewListener(this);
        sv2 = findViewById(R.id.sv_2);
        sv2.setScrollViewListener(this);
        sv2.post(new Runnable() {
            @Override
            public void run() {
                sv2.scrollBy(distance, 0);
            }
        });
        sv2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && x != 0) {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(x, distance);
                    valueAnimator.setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            sv2.scrollTo((int) animation.getAnimatedValue(), 0);
                        }
                    });
                    valueAnimator.start();
                }
                return false;
            }
        });
    }

    int x = 0;

    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView == sv2) {
            this.x = x;
            sv1.scrollTo(distance - x, y);
            if (x == 0) {
                tvRight.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_tv_right_blue_bg));
                Toast.makeText(this, "Complete!", Toast.LENGTH_SHORT).show();
            } else if (x == distance) {
                tvRight.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_tv_right_white_bg));
            } else {
                tvRight.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_tv_right_blue_bg));
            }

        }
    }
}
package com.wyjson.gangedtest.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.wyjson.gangedtest.R;
import com.wyjson.gangedtest.databinding.ActivityMainBinding;
import com.wyjson.gangedtest.view.SlideScrollView;

/**
 * ScrollView Ganged
 * <p>
 * Created by Wyjson on 2019/8/7.
 */
public class MainActivity extends FragmentActivity {

    private ActivityMainBinding vb;
    private int distance;
    private int distance2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());

        setSV1();
        setSV3();
    }


    private int dp2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public void onResetClick(View view) {
        setSV1();
        setSV3();
    }

    private void setSV1() {
        //this 150dip is @+id/v_scroll width
        distance = getResources().getDimensionPixelOffset(R.dimen.v_scroll_width);
        vb.sv1.setScroll(true);
        vb.sv1.post(() -> vb.sv1.scrollBy(distance, 0));
        vb.sv1.setOnListener(new SlideScrollView.onListener() {
            @Override
            public void onBack() {
                vb.sv1.post(() -> vb.sv1.smoothScrollBy(distance, 0));
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Complete!", Toast.LENGTH_SHORT).show();
                vb.tvRight.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.shape_tv_right_blue_bg));
            }

            @Override
            public void onScrollChanged(SlideScrollView scrollView, int x, int y, int oldX, int oldY) {
                vb.svFace.scrollTo(distance - x, y);
                if (x == distance) {
                    vb.tvRight.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.shape_tv_right_white_bg));
                } else {
                    vb.tvRight.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.shape_tv_right_blue_bg));
                }
            }
        });
    }

    private void setSV3() {
        distance2 = dp2px(this, 190);
        vb.ivBlock.setImageResource(R.drawable.ic_block_arrow);
        ((AnimationDrawable) vb.vAnim.getBackground()).start();

        vb.sv2.setScroll(true);
        vb.sv2.post(() -> vb.sv2.scrollBy(distance2, 0));
        vb.sv2.setOnListener(new SlideScrollView.onListener() {

            @Override
            public void onBack() {
                vb.ivBlock.setImageResource(R.drawable.ic_block_arrow);
                vb.sv2.post(() -> vb.sv2.smoothScrollBy(distance2, 0));
            }

            @Override
            public void onComplete() {
                vb.ivBlock.setImageResource(R.drawable.ic_block_tick);
                ((AnimationDrawable) vb.vAnim.getBackground()).stop();
            }

            @Override
            public void onScrollChanged(SlideScrollView scrollView, int x, int y, int oldX, int oldY) {

            }
        });
    }

}
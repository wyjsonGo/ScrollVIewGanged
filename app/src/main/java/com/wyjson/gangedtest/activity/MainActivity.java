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

        initSV();

        initSV3();
    }


    private int dp2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public void onResetClick(View view) {
        vb.sv2.setScroll(true);
        vb.sv2.post(() -> vb.sv2.scrollBy(distance, 0));
//      ------
        vb.sv3.setScroll(true);
        vb.ivBlock.setImageResource(R.drawable.ic_block_arrow);
        vb.sv3.post(() -> vb.sv3.scrollBy(distance2, 0));
    }

    private void initSV() {
        //this 150dip is @+id/v_scroll width
        distance = getResources().getDimensionPixelOffset(R.dimen.v_scroll_width);
        
        vb.sv2.setOnListener(new SlideScrollView.onListener() {
            @Override
            public void onBack() {
                vb.sv2.post(() -> vb.sv2.smoothScrollBy(distance, 0));
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Complete!", Toast.LENGTH_SHORT).show();
                vb.tvRight.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.shape_tv_right_blue_bg));
            }

            @Override
            public void onScrollChanged(SlideScrollView scrollView, int x, int y, int oldX, int oldY) {
                vb.sv1.scrollTo(distance - x, y);
                if (x == distance) {
                    vb.tvRight.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.shape_tv_right_white_bg));
                } else {
                    vb.tvRight.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.shape_tv_right_blue_bg));
                }
            }
        });
        vb.sv2.post(() -> vb.sv2.scrollBy(distance, 0));
    }

    private void initSV3() {
        distance2 = dp2px(this, 190);
        vb.sv3.setScroll(true);
        vb.ivBlock.setImageResource(R.drawable.ic_block_arrow);
        ((AnimationDrawable) vb.vAnim.getBackground()).start();
        vb.sv3.post(() -> vb.sv3.scrollBy(distance2, 0));
        vb.sv3.setOnListener(new SlideScrollView.onListener() {

            @Override
            public void onBack() {
                vb.ivBlock.setImageResource(R.drawable.ic_block_arrow);
                vb.sv3.post(() -> vb.sv3.smoothScrollBy(distance2, 0));
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
package test.better.com.leak.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import test.better.com.leak.R;

/**
 * Created by zhaoyu1 on 2017/2/16.
 */

public class AnimDiffActivity extends AppCompatActivity {

    private final String TAG = "anim";

    View iv;
    View btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_diff);

        iv = findViewById(R.id.image);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, String.format("scrollX:%s, left:%s, x:%s, y:%s, translateX:%s", iv.getScrollX(), iv.getLeft(), iv.getX(), iv.getY(), iv.getTranslationX()));

                // === 1. scrollTo ===
                //iv.scrollTo(100, (int) iv.getY());      // 100 表示实际区域距离内容区域100个像素（右）， 即：内容区域，左移动100个像素

// === 2. 使用动画 ===
//                Animation anim = new TranslateAnimation(iv.getX(), 100, iv.getY(), iv.getY());
//                anim.setFillAfter(true);
//                iv.startAnimation(anim);

                // === 2. LayoutParams ===
//                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
//                //lp.width += 100;
//                lp.leftMargin += 100;
//                iv.requestLayout();  // 或者 iv.setLayoutParams(lp);

                // 3.setX与setTranslationX
                iv.animate().translationX(-50).start();

                Log.e(TAG, String.format("scrollX:%s, left:%s, x:%s, y:%s, translateX:%s", iv.getScrollX(), iv.getLeft(), iv.getX(), iv.getY(), iv.getTranslationX()));
            }
        });
    }

}

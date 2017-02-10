package test.better.com.leak.leak;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;

import test.better.com.leak.R;

/**
 * 内部类的静态转化
 * Created by zhaoyu1 on 2017/2/9.
 */
public class LeakActivity extends AppCompatActivity {
    private final String TAG = "better";

    private String str = "LeakTest";
    private Handler handler = new Handler();

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            Log.e(TAG, str);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_1);
        handler.postDelayed(new MyRunnable(), 30000);       // 内存泄露
    }

    // ==============================================================
    //  解决方法
    // ==============================================================

    public String getStr() {
        return str;
    }

    private static class MyRunnable2 implements Runnable {
        // 不使用非静态内部类，通过弱引用，来引用外层Activity对象
        WeakReference<LeakActivity> weakReference;

        public MyRunnable2(LeakActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            if (weakReference.get() != null) {
                Log.e(TAG, weakReference.get().getStr());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

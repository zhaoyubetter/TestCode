package test.better.com.leak.leak;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Handler 造成的内存泄露：
 * 1. mHander 为匿名内部类，这样就持有外部类 activity的引用；
 * 2. 另外消息队列是在Looper线程中，不断轮询处理消息，当 activity退出后，消息队列中，还有未处理的消息时，
 *      将造成 Message持有 mHanderl 的实例，也就是间接持有了Activity的引用了；
 * Created by zhaoyu1 on 2017/2/10.
 */
public class LeakActivity3 extends AppCompatActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_leak_1);

        mHander2 = new MyHandler(this);
    }


    // ==============================================================
    //  解决方法：
    // 1.Handler内部改成静态内部类,通过弱引用来持有外部类activity的实例
    // 2.在Activity onDestroy，移除Handler中的所有消息 removeCallbacksAndMessages
    // ==============================================================

    private MyHandler mHander2;

    private static class MyHandler extends Handler {

        private WeakReference<Activity> weakReference = null;

        public MyHandler(Activity activity) {
            weakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = weakReference.get();
            if(activity != null) {
                // 业务处理
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHander2.removeCallbacksAndMessages(null);
    }
}

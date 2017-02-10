package test.better.com.leak.leak;

import android.content.Context;

/**
 * 单例模式可能造成的内存泄露：
 * 1. 如果传入进来的是 context 是 Activity，因为单例是 静态类型的，静态类型的生命周期，跟着应用走，这样就造成了内存泄露了
 * Created by zhaoyu1 on 2017/2/10.
 */
public final class SingleTon3 {
    private Context context;
    private static SingleTon3 sInstace;

    private SingleTon3(Context context) {
        this.context = context;

        // ==============================================================
        //  解决方法
        // ==============================================================
        this.context = context.getApplicationContext();     // 通过这样的方式来解决，获取应用的上下文
    }

    public static SingleTon3 getInstace(Context context) {
        if(sInstace == null) {
            synchronized (SingleTon3.class) {
                if (sInstace == null) {
                    sInstace = new SingleTon3(context);
                }
            }
        }
        return sInstace;
    }
}

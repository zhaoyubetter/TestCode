package test.better.com.leak;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhaoyu1 on 2017/2/15.
 */
public class Apps extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getApp() {
        return sContext;
    }
}

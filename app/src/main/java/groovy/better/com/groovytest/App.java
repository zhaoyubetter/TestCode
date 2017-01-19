package groovy.better.com.groovytest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by zhaoyu1 on 2016/11/21.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // activity 生命周期监控方案
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.e("better", activity.getClass().getName() + " onCreate");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.e("better", activity.getClass().getName() + " onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.e("better", activity.getClass().getName() + " onActivityDestroyed");
            }
        });

        isMainProcess();

        getLauncherActivity();
    }

    boolean isMainProcess() {
        boolean result = false;
        String mainProcessName = getApplicationContext().getPackageName();
        if (mainProcessName.equals(getProcessName(this, android.os.Process.myPid()))) {
            result = true;
        }
        return result;
    }

    public String getProcessName(Context ctx, int pid) {
        String result = null;
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (!runningAppProcesses.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                if (info.pid == pid) {
                    result = info.processName;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 获取启动Activity类名称
     *
     * @return
     */
    public String getLauncherActivity() {
        String mainProcessName = getApplicationContext().getPackageName();
        // 需要获取已安装应用权限
        Intent intent = getPackageManager().getLaunchIntentForPackage(mainProcessName);
        return intent.getComponent().getClassName();
    }
}

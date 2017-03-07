package better.com.commomlib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by zhaoyu1 on 2017/2/15.
 */
public final class RunningTaskInfoUtils {

    private static final String TAG = "Tasks";

    public static void analyseTaskInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = am.getRunningTasks(10);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfoList) {
            Log.e(TAG, "id: " + runningTaskInfo.id);
            Log.e(TAG, "description: " + runningTaskInfo.description);
            Log.e(TAG, "number of activities: " + runningTaskInfo.numActivities);
            Log.e(TAG, "topActivity: " + runningTaskInfo.topActivity);
            Log.e(TAG, "baseActivity: " + runningTaskInfo.baseActivity.toString());
        }
    }
}

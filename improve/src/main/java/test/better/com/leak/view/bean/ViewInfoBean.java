package test.better.com.leak.view.bean;

import android.app.Activity;

/**
 * Created by zhaoyu1 on 2017/2/16.
 */

public class ViewInfoBean {
    public String title;
    public Class<? extends Activity> clazz;

    public ViewInfoBean(String title, Class<? extends Activity> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return title;
    }
}

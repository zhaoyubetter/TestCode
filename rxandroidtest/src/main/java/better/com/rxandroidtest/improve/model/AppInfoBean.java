package better.com.rxandroidtest.improve.model;

import android.graphics.drawable.Drawable;

/**
 * Created by zhaoyu1 on 2017/2/7.
 */
public class AppInfoBean {

    public static final int SYSTEM_APP = 1; // 系统程序
    public static final int THIRD_APP = 2; // 第三方应用程序
    public static final int SDCARD_APP = 3; // 安装在SDCard的应用程序
    public static final int OTHER_APP = 4;       // 其他

    public static final int TITLE_TYPE = 5;     // 标题类型


    public Drawable iconDrawable;
    public String appName;
    public String pgName;
    public int appType;

    public int count;       // 标题类型下的App个数
}

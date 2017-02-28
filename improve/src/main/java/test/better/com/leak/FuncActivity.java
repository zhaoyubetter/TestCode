package test.better.com.leak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * 功能Activity
 * Created by zhaoyu1 on 2017/2/28.
 */
public class FuncActivity extends AppCompatActivity {

    public static final String PARAM_CLAZZ = "class";
    public static final String PARAM_TITLE = "title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);
        setTitle(getIntent().getStringExtra(PARAM_TITLE));
        if (savedInstanceState == null) {
            addFragment();
        }
    }

    private void addFragment() {
        Intent intent = getIntent();
        if (null != intent) {
            String className = intent.getStringExtra(PARAM_CLAZZ);
            if (!TextUtils.isEmpty(className)) {
                try {
                    Class<?> clazz = Class.forName(className);
                    Object instance = clazz.newInstance();
                    if (instance instanceof Fragment) {
                        Fragment fragment = (Fragment) instance;
                        Bundle extras = getIntent().getExtras();
                        if (null != extras) {
                            fragment.setArguments(extras);
                        }
                        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
                    } else {
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
            }
        }
    }

    /**
     * 跳转到一个activity内
     */
    public static void toActivity(Activity activity, Class<? extends Fragment> clazz, Bundle extras) {
        if (null != activity) {
            Intent intent = new Intent(activity, FuncActivity.class);
            intent.putExtra(PARAM_CLAZZ, clazz.getName());
            if (null != extras) {
                intent.putExtras(extras);
            }
            activity.startActivity(intent);
        }
    }
}

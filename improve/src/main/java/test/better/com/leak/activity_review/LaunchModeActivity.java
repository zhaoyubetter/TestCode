package test.better.com.leak.activity_review;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import better.com.commomlib.utils.RunningTaskInfoUtils;
import test.better.com.leak.Apps;
import test.better.com.leak.R;

/**
 * Created by zhaoyu1 on 2017/2/15.
 */

public class LaunchModeActivity extends AppCompatActivity {

    private TextView launchMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        setTitle("启动模式");
        launchMode = (TextView) findViewById(R.id.launchMode);

        RunningTaskInfoUtils.analyseTaskInfo(getApplicationContext());

        launchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Apps.getApp(), LaunchModeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Apps.getApp().startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(getApplicationContext(), "你好", Toast.LENGTH_LONG).show();
    }
}

package test.better.com.leak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.better.com.leak.activity_review.LaunchModeActivity;
import test.better.com.leak.activity_review.LifeCycleActivity;
import test.better.com.leak.custom_view.CustomViewMainActivity;
import test.better.com.leak.leak.LeakActivity;
import test.better.com.leak.optimize.ImageCompressActivity;
import test.better.com.leak.test.Instrumentation1Activity;
import test.better.com.leak.view.ViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.leak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LeakActivity.class);
                startActivity(intent);
            }
        });

        // 压缩图片
        findViewById(R.id.zoomImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ImageCompressActivity.class);
                startActivity(intent);
            }
        });

        // Activity 生命周期
        findViewById(R.id.activity_life_cycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LifeCycleActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.activity_launch_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Apps.getApp(), LaunchModeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.view_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Apps.getApp(), ViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Apps.getApp(), CustomViewMainActivity.class);
                startActivity(intent);
            }
        });

        // 测试
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Apps.getApp(), Instrumentation1Activity.class);
                startActivity(intent);
            }
        });
    }
}

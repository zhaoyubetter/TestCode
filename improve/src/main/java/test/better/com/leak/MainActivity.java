package test.better.com.leak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.better.com.leak.leak.LeakActivity;
import test.better.com.leak.optimize.ImageCompressActivity;

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
    }
}

package groovy.better.com.groovytest;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhaoyu1 on 2016/11/24.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TestFragment fragment = new TestFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.id_fragment, fragment).commitAllowingStateLoss();

        Test2Fragment fragment2 = new Test2Fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.id_fragment, fragment2).commitAllowingStateLoss();


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.id_tv_test_fragment);
                Rect rect = new Rect();
                textView.getGlobalVisibleRect(rect);
                Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_LONG).show();

                int i = 0;
                int j = 10 / i;
            }
        });
    }
}

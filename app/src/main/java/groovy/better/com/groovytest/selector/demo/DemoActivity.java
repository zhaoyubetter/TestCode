package groovy.better.com.groovytest.selector.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import groovy.better.com.groovytest.R;
import groovy.better.com.groovytest.selector.MyLayoutInflater;


/**
 * Created by hufeng on 2016/8/22
 */
public class DemoActivity extends AppCompatActivity {
    private static final String TAG = DemoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new MyLayoutInflater(getLayoutInflater(), this).inflate(R.layout.activity_test_selector, null, false);
        setContentView(view);
        View id = findViewById(R.id.test);
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) v).setText("放");
            }
        });

    }
}

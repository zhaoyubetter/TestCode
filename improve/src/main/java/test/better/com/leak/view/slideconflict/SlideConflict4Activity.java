package test.better.com.leak.view.slideconflict;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import test.better.com.leak.R;
import test.better.com.leak.ui.InnerListView;

/**
 * 方向一致时，的内部解决方法
 */
public class SlideConflict4Activity extends AppCompatActivity {

    private InnerListView list;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_conflict4);
        list = (InnerListView) findViewById(R.id.list);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_test_data_1)));
        list.setScrollView(mScrollView);
    }


}

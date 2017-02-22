package test.better.com.leak.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.better.com.leak.view.bean.ViewInfoBean;
import test.better.com.leak.view.slideconflict.SlideConflict1Activity;
import test.better.com.leak.view.slideconflict.SlideConflict2Activity;
import test.better.com.leak.view.slideconflict.SlideConflict3Activity;
import test.better.com.leak.view.slideconflict.SlideConflict4Activity;

/**
 * Created by zhaoyu1 on 2017/2/16.
 */
public class ViewActivity extends AppCompatActivity {

    ListView listView;

    List<ViewInfoBean> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);

        mDatas = new ArrayList<>();
        mDatas.add(new ViewInfoBean("3种动画", AnimDiffActivity.class));
        mDatas.add(new ViewInfoBean("滑动冲突1-方向不一致(外)", SlideConflict1Activity.class));
        mDatas.add(new ViewInfoBean("滑动冲突1-方向不一致(内)", SlideConflict2Activity.class));
        mDatas.add(new ViewInfoBean("滑动冲突2-方向一致(外)", SlideConflict3Activity.class));  // SlideConflict4Activity
        mDatas.add(new ViewInfoBean("滑动冲突2-方向一致(内)", SlideConflict4Activity.class));

        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDatas));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ViewInfoBean viewInfoBean = mDatas.get(position);
                Intent intent = new Intent(getApplicationContext(), viewInfoBean.clazz);
                startActivity(intent);
            }
        });
    }
}

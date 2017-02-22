package test.better.com.leak.view.slideconflict;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import test.better.com.leak.R;
import test.better.com.leak.ui.ConflictScrollView;

public class SlideConflict3Activity extends AppCompatActivity {

    ListView list;
    private ConflictScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_conflict3);
        list = (ListView) findViewById(R.id.list);
        scrollView = (ConflictScrollView) findViewById(R.id.scrollView);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_test_data_1)));

        scrollView.setListView(list);
    }
}

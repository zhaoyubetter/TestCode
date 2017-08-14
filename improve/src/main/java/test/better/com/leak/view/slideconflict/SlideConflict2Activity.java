package test.better.com.leak.view.slideconflict;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import test.better.com.leak.R;

public class SlideConflict2Activity extends AppCompatActivity {

	ListView list1;
	ListView list2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("内外滑动方向一致-内部拦截1");
		setContentView(R.layout.activity_slide_conflict_2);

		list1 = (ListView) findViewById(R.id.list1);
		list2 = (ListView) findViewById(R.id.list2);


		list1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_test_data_1)));
		list2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_test_data_2)));
	}
}

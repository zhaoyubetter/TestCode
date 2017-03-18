package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.better.com.leak.FuncActivity;
import test.better.com.leak.R;

public class CustomViewMainActivity extends AppCompatActivity {

	private ListView list;
	private List<FuncItem> mDatas = new ArrayList<>(10);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_custom_view);
		buildDatas();
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDatas));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final FuncItem funcItem = mDatas.get(position);
				try {
					Class<? extends Fragment> clazz = (Class<? extends Fragment>) Class.forName(funcItem.clazzName);
					Bundle args = new Bundle();
					args.putString("title", funcItem.name);
					FuncActivity.toActivity(CustomViewMainActivity.this, clazz, args);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void buildDatas() {
		mDatas.add(new FuncItem("DivideTextView", DivideTextViewFragment.class.getName()));
		mDatas.add(new FuncItem("CenterGridLayout", CenterGridLayoutFragment.class.getName()));
		mDatas.add(new FuncItem("BubbleView", BubbleViewFragment.class.getName()));
		mDatas.add(new FuncItem("FlowLayout", FlowLayoutFragment.class.getName()));
	}
}

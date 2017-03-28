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
import test.better.com.leak.custom_view.path.PathAnim1Fragment;

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
		mDatas.add(new FuncItem("SelectFlowLayout(多选)", SelectFlowLayoutFragment.class.getName()));
		mDatas.add(new FuncItem("stateTextView(自定义状态)", StateTextViewFragment.class.getName()));
		mDatas.add(new FuncItem("ClearEditText", ClearEditTextFragment.class.getName()));
		mDatas.add(new FuncItem("三阶贝塞尔 例子", BallViewFragment.class.getName()));
		mDatas.add(new FuncItem("三阶贝塞尔（优化） 例子", BallView2Fragment.class.getName()));
		mDatas.add(new FuncItem("三阶贝塞尔（水滴） 例子", WaterBallViewFragment.class.getName()));
		mDatas.add(new FuncItem("Path Anim", PathAnim1Fragment.class.getName()));
	}
}

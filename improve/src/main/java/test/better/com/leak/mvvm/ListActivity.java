package test.better.com.leak.mvvm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.better.com.leak.BR;
import test.better.com.leak.R;

public class ListActivity extends AppCompatActivity {

	private ListView lv;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			MyBaseAdapter<Food> adapter = new MyBaseAdapter<>(ListActivity.this,
					R.layout.item_mvvm_list, foods, BR.food);
			lv.setAdapter(adapter);
		}
	};
	private List<Food> foods;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		lv = (ListView) findViewById(R.id.lv);
		initData();
	}

	private void initData() {
		parseJson(null);
	}

	private void parseJson(String jsonStr) {
		String imgUrl = "https://img13.360buyimg.com/n1/jfs/t15421/39/463245065/344461/81397eea/5a2e4969N3128fb18.jpg";
		foods = new ArrayList<>();
		try {
			for (int i = 0; i < 10; i++) {
				String description = "描述文字描述文字描述文字描述文字描述文字";
				String img = imgUrl;
				String keywords = "【关键词】 " + "京东";
				String summary = description;
				foods.add(new Food(description, img, keywords, summary));
			}
			mHandler.sendEmptyMessage(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

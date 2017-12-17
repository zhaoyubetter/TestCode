package test.better.com.leak.mvvm.one;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import test.better.com.leak.R;
import test.better.com.leak.databinding.ActivityRecyclerDataBindBinding;

public class RecyclerDataBindActivity extends AppCompatActivity {

	ActivityRecyclerDataBindBinding mDatabind;

	public class Presenter {
		public void onClickAddItem(View view) {

		}

		public void onClickRemoveItem(View view) {

		}

		// 与原来的方法必须一致，方法引用绑定
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mDatabind.tv.setText(s);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDatabind = DataBindingUtil.setContentView(this, R.layout.activity_recycler_data_bind);
		mDatabind.recycler.setLayoutManager(new LinearLayoutManager(this));
		mDatabind.setPresenter(new Presenter());	// 设置Presenter
	}
}

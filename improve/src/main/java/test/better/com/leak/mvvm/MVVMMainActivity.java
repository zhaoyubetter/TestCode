package test.better.com.leak.mvvm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import test.better.com.leak.R;
import test.better.com.leak.databinding.ActivityMvvmmainBinding;
import test.better.com.leak.mvvm.one.RecyclerDataBindActivity;
import test.better.com.leak.mvvm.two.BindindAdapterDemoActiivty;

/**
 * 参考：http://blog.csdn.net/u012702547/article/details/52077515
 */
public class MVVMMainActivity extends AppCompatActivity implements View.OnClickListener {


	public class Presenter {
		/**
		 * xml 回传参数
		 *
		 * @param user
		 */
		public void onClickListenerBinding(UserEntity user) {
			Toast.makeText(MVVMMainActivity.this, "" + user.getNickname(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActivityMvvmmainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvmmain);
		final UserEntity user = new UserEntity();
		user.setAge(34);
		user.setUsername("zhangsan");
		user.setNickname("张三");
		user.icon = "http://img2.cache.netease.com/auto/2016/7/28/201607282215432cd8a.jpg";
		viewDataBinding.setUser(user);
		viewDataBinding.setPresenter(new Presenter());
		viewDataBinding.viewStub.getViewStub().inflate();

		findViewById(R.id.list).setOnClickListener(this);
		findViewById(R.id.recycler).setOnClickListener(this);
		findViewById(R.id.databindingAdapter).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.list:
				startActivity(new Intent(this, ListActivity.class));
				break;
			case R.id.recycler:
				startActivity(new Intent(this, RecyclerDataBindActivity.class));
				break;
			case R.id.databindingAdapter:
				startActivity(new Intent(this, BindindAdapterDemoActiivty.class));
				break;
		}
	}
}

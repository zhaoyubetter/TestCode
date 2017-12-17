package test.better.com.leak.mvvm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.better.com.leak.R;
import test.better.com.leak.databinding.ActivityMvvmmainBinding;

/**
 * 参考：http://blog.csdn.net/u012702547/article/details/52077515
 */
public class MVVMMainActivity extends AppCompatActivity implements View.OnClickListener {

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

		findViewById(R.id.list).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.list:
				startActivity(new Intent(this, ListActivity.class));
				break;
		}
	}
}

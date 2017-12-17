package test.better.com.leak.mvvm.one;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.better.com.leak.R;
import test.better.com.leak.databinding.ActivityRecyclerDataBindBinding;

public class RecyclerDataBindActivity extends AppCompatActivity {

	ActivityRecyclerDataBindBinding mDatabind;
	EmployeeAdapter adapter;

	public class Presenter {
		public void onClickAddItem(View view) {
			adapter.add(new Employee("c", "cc", false));
		}

		public void onClickRemoveItem(View view) {
			adapter.remove();
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
		mDatabind.setPresenter(new Presenter());    // 设置Presenter


		adapter = new EmployeeAdapter(getApplicationContext(),
				new EmployeeAdapter.OnItemClickListener() {
					@Override
					public void onItemClick(Employee employee) {
						Toast.makeText(getApplicationContext(), employee.getFirstName(), Toast.LENGTH_SHORT).show();
					}
				});
		mDatabind.recycler.setAdapter(adapter);

		List<Employee> data = new ArrayList<>();
		data.add(new Employee("better", "zhao", false));
		data.add(new Employee("better1", "zhao1", true));
		data.add(new Employee("better2", "zhao2", false));
		data.add(new Employee("better3", "zhao3", false));
		adapter.addAll(data);


	}
}

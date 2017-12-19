package test.better.com.leak.mvvm.four;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import test.better.com.leak.R;
import test.better.com.leak.databinding.ActivityLambdaBinding;
import test.better.com.leak.mvvm.one.Employee;

public class LambdaActivity extends AppCompatActivity {


	public class Presenter {
		public void onEmployeeClick(Employee ee) {
			Toast.makeText(LambdaActivity.this, ee.getFirstName(), Toast.LENGTH_SHORT).show();

		}

		public void onEmployeeOtherClick(Employee ee, Context context) {
			Toast.makeText(LambdaActivity.this, ee.getLastName(), Toast.LENGTH_SHORT).show();
		}

		public void onFocusChange(Employee ee) {
			Toast.makeText(LambdaActivity.this, "onFocusChange", Toast.LENGTH_SHORT).show();
		}
	}

	ActivityLambdaBinding bind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bind = DataBindingUtil.setContentView(this, R.layout.activity_lambda);
		bind.setEmployee(new Employee("better", "zhaoyu", false));
		bind.setPresenter(new Presenter());
	}
}

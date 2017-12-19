package test.better.com.leak.mvvm.two;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.better.com.leak.R;
import test.better.com.leak.databinding.ActivityBindindAdapterDemoActiivtyBinding;
import test.better.com.leak.mvvm.one.Employee;

public class BindindAdapterDemoActiivty extends AppCompatActivity {

	ActivityBindindAdapterDemoActiivtyBinding databinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		databinding = DataBindingUtil.setContentView(this, R.layout.activity_bindind_adapter_demo_actiivty);
		Employee e = new Employee("a", "b", false);
		e.icon = "https://img4.mukewang.com/545847490001582602200220-80-80.jpg";
		databinding.setEmployee(e);
	}
}

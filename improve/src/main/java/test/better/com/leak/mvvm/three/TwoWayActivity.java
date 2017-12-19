package test.better.com.leak.mvvm.three;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import test.better.com.leak.R;
import test.better.com.leak.databinding.ActivityTwoWayBinding;

/**
 * 双向绑定
 */
public class TwoWayActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityTwoWayBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_two_way);
		FormModel mode = new FormModel("better", "123");
		mode.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
			@Override
			public void onPropertyChanged(Observable observable, int i) {
				Toast.makeText(TwoWayActivity.this, "" + observable, Toast.LENGTH_SHORT).show();
			}
		});
		bind.setModel(mode);
	}
}

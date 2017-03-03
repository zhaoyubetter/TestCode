package test.better.com.leak.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import better.com.commomlib.utils.ViewUtils;
import better.com.commomlib.view.annotation.ViewInject;
import test.better.com.leak.R;

public class Instrumentation1Activity extends AppCompatActivity {

	@ViewInject(R.id.et)
	EditText et;
	@ViewInject(R.id.textView)
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instrumentation1);
		ViewUtils.inject(this);
	}

	public void sayHello(View v){
		EditText editText = (EditText) findViewById(R.id.et);
		textView.setText("Hello, " + editText.getText().toString() + "!");
	}
}

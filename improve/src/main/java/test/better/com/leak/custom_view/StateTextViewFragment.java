package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import better.com.commomlib.utils.ViewUtils;
import better.com.commomlib.view.annotation.ViewInject;
import test.better.com.leak.R;
import ui.better.StateTextView;

/**
 * Created by zhaoyu on 2017/3/19.
 */

public class StateTextViewFragment extends Fragment implements View.OnClickListener {

	@ViewInject(R.id.textview)
	StateTextView textview;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_state_textview, container, false);
		ViewUtils.inject(this, view);
		view.findViewById(R.id.btn_1).setOnClickListener(this);
		view.findViewById(R.id.btn_2).setOnClickListener(this);
		view.findViewById(R.id.btn_3).setOnClickListener(this);
		view.findViewById(R.id.btn_4).setOnClickListener(this);
		view.findViewById(R.id.btn_5).setOnClickListener(this);
		view.findViewById(R.id.btn_clear).setOnClickListener(this);
		return view;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_1:
				textview.setStateEnabled(StateTextView.STATE_FLAG1, true);
				break;
			case R.id.btn_2:
				textview.setStateEnabled(StateTextView.STATE_FLAG2, true);
				break;
			case R.id.btn_3:
				textview.setStateEnabled(StateTextView.STATE_FLAG3, true);
				break;
			case R.id.btn_4:
				textview.setStateEnabled(StateTextView.STATE_FLAG4, true);
				break;
			case R.id.btn_5:
				textview.setStateEnabled(StateTextView.STATE_FLAG5, true);
				break;
			case R.id.btn_clear:
				textview.clearStatus();
				break;

		}
	}
}

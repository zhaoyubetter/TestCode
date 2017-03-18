package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.better.com.leak.R;
import ui.better.FlowLayout;

/**
 * Created by zhaoyu on 2017/3/13.
 */

public class FlowLayoutFragment extends Fragment {

	private FlowLayout flowLayout;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_flow_layout, container, false);
		flowLayout = (FlowLayout) view.findViewById(R.id.flowLayout);

		flowLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				TextView tv = new TextView(getContext());
				tv.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				tv.setText("大招");
				flowLayout.addView(tv);
			}
		}, 3000);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}

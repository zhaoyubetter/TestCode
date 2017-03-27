package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.better.com.leak.R;

/**
 * Created by zhaoyu on 2017/3/23.
 */

public class WaterBallViewFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_water_ball, container, false);
		final WaterBallView ball = (WaterBallView) view.findViewById(R.id.ballView);
		ball.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ball.startAnimation();
			}
		});
		return view;
	}
}

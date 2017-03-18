package test.better.com.leak.custom_view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import test.better.com.leak.R;
import ui.better.FlowLayout;

/**
 * Created by zhaoyu on 2017/3/13.
 */

public class FlowLayoutFragment extends Fragment {

	private FlowLayout flowLayout;
	private SeekLayout seekLayout_ver;
	private SeekLayout seekLayout_hor;
	private RadioLayout radioGravity;

	private View add;
	private View del;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_flow_layout, container, false);
		flowLayout = (FlowLayout) view.findViewById(R.id.flowLayout);
		seekLayout_ver = (SeekLayout) view.findViewById(R.id.seekLayout_ver);
		seekLayout_hor = (SeekLayout) view.findViewById(R.id.seekLayout_hor);
		radioGravity = (RadioLayout) view.findViewById(R.id.radioGravity);

		add = view.findViewById(R.id.add);
		del = view.findViewById(R.id.del);

		seekLayout_ver.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress) {
				flowLayout.setVerticalPadding(progress);
			}
		});

		seekLayout_hor.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress) {
				flowLayout.setHorziontalPadding(progress);
			}
		});

		radioGravity.setOnCheckedListener(new RadioLayout.OnCheckedListener() {
			@Override
			public void onChecked(View v, int position, boolean isChecked) {
				if(isChecked) {
					flowLayout.setGravity(position);
				}
			}
		});


		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final TextView tv = new TextView(getContext());
				tv.setText(flowLayout.getChildCount() + "");
				tv.setBackgroundColor(Color.RED);
				tv.setLayoutParams(new ViewGroup.MarginLayoutParams(10 + new Random().nextInt(300), 68));
				flowLayout.addView(tv);
			}
		});

		del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flowLayout.removeViewAt(flowLayout.getChildCount() - 1);
			}
		});

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}

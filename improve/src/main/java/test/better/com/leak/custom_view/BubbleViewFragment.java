package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import better.com.commomlib.utils.ViewUtils;
import better.com.commomlib.view.annotation.ViewInject;
import test.better.com.leak.R;
import ui.better.bubbler.BubbleTextView;

/**
 * Created by zhaoyu on 2017/3/13.
 */

public class BubbleViewFragment extends Fragment {

	private BubbleTextView textview;

	@ViewInject(R.id.arrowDirectionRadio)
	private RadioLayout arrowDirectionRadio;

	@ViewInject(R.id.seekLayout_offset)
	private SeekLayout seekLayout_offset;

	@ViewInject(R.id.seekLayout_arrowHeight)
	private SeekLayout seekLayout_arrowHeight;

	@ViewInject(R.id.seekLayout_arrowWidth)
	private SeekLayout seekLayout_arrowWidth;

	@ViewInject(R.id.arrow_center)
	private CheckBox arrow_center;

	@ViewInject(R.id.seekLayout_radius)
	private SeekLayout seekLayout_radius;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bubble_view, container, false);
		textview = (BubbleTextView) view.findViewById(R.id.textview);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		arrowDirectionRadio.setOnCheckedListener(new RadioLayout.OnCheckedListener() {
			@Override
			public void onChecked(View v, int position, boolean isChecked) {
				textview.setArrowDirection(position);
			}
		});

		seekLayout_offset.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress) {
				textview.setArrowOffset(progress);
			}
		});

		seekLayout_arrowHeight.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress) {
				textview.setArrowHeight(progress);
			}
		});

		seekLayout_arrowWidth.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress) {
				textview.setArrowWidth(progress);
			}
		});

		arrow_center.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				textview.setArrowCenter(isChecked);
			}
		});

		seekLayout_radius.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress) {
				textview.setRadius(progress);
			}
		});
	}
}

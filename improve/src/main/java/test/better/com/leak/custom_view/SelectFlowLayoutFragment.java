package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import test.better.com.leak.R;
import ui.better.SelectFlowLayout;
import ui.better.callback.OnSingleSelectListener;

/**
 * 流布局测试
 * Created by zhaoyu on 2017/3/13.
 */
public class SelectFlowLayoutFragment extends Fragment {

	private SelectFlowLayout flowLayout;
	private SeekLayout seekLayout_ver;
	private SeekLayout seekLayout_hor;
	private RadioLayout radioGravity;
	private RadioLayout radioSelect;

	private View add;
	private View del;
	private View container_multi;
	private Spinner spinner;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_select_flow_layout, container, false);
		flowLayout = (SelectFlowLayout) view.findViewById(R.id.flowLayout);
		seekLayout_ver = (SeekLayout) view.findViewById(R.id.seekLayout_ver);
		seekLayout_hor = (SeekLayout) view.findViewById(R.id.seekLayout_hor);
		radioGravity = (RadioLayout) view.findViewById(R.id.radioGravity);
		radioSelect = (RadioLayout) view.findViewById(R.id.radioSelect);
		container_multi = view.findViewById(R.id.container_multi);
		spinner = (Spinner) view.findViewById(R.id.spinner1);

		add = view.findViewById(R.id.add);
		del = view.findViewById(R.id.del);

		radioSelect.setOnCheckedListener(new RadioLayout.OnCheckedListener() {
			@Override
			public void onChecked(View v, int position, boolean isChecked) {
				if (isChecked) {
					flowLayout.setSelectMode(position);
				}
				container_multi.setVisibility(position == 1 && isChecked ? View.VISIBLE : View.GONE);
				if (container_multi.getVisibility() == View.VISIBLE) {
					spinner.setSelection(2);
					flowLayout.setSelectMaxSize(3);
				}
			}
		});

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				flowLayout.setSelectMaxSize(position + 1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});


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
				if (isChecked) {
					flowLayout.setGravity(position);
				}
			}
		});


		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flowLayout.addView(getLabel(flowLayout.getChildCount() + ""));
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

	private TextView getLabel(String text) {
		TextView textView = new TextView(getContext());
		textView.setText(text);
		textView.setGravity(Gravity.CENTER);
		textView.setPadding(24, 8, 24, 8);
		return textView;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		flowLayout.setOnSingleSelectListener(new OnSingleSelectListener() {
			@Override
			public void onSelected(View v, int newPos, int oldPos) {
				Toast.makeText(getContext(), "选择了：" + newPos, Toast.LENGTH_SHORT).show();
			}
		});
	}
}

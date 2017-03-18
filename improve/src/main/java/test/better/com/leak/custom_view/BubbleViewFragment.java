package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.better.com.leak.R;
import ui.better.bubbler.BubbleTextView;

/**
 * Created by zhaoyu on 2017/3/13.
 */

public class BubbleViewFragment extends Fragment {

	private BubbleTextView textview;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_bubble_view, container, false);
		textview = (BubbleTextView) view.findViewById(R.id.textview);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}

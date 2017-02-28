package test.better.com.leak.custom_view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.better.com.leak.R;

/**
 * Created by zhaoyu1 on 2017/2/28.
 */
public class DivideTextViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_divider_textview, container, false);
        return view;
    }
}

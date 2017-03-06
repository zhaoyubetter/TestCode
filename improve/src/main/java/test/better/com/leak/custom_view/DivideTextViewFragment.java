package test.better.com.leak.custom_view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.ArrayList;

import test.better.com.leak.R;
import ui.better.DivideTextView;

/**
 * Created by zhaoyu1 on 2017/2/28.
 */
public class DivideTextViewFragment extends Fragment {

    private SeekLayout seekLayout;
    private CheckLayout checkLayout;
    private DivideTextView textview;
    private SeekLayout seekLayout_divider;
    private SeekLayout seekLayout_padding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_divider_textview, container, false);
        seekLayout = (SeekLayout) view.findViewById(R.id.seekLayout);
        textview = (DivideTextView) view.findViewById(R.id.textview);
        checkLayout = (CheckLayout) view.findViewById(R.id.checkLayout);
        seekLayout_divider = (SeekLayout) view.findViewById(R.id.seekLayout_divider);
        seekLayout_padding = (SeekLayout) view.findViewById(R.id.seekLayout_padding);

        seekLayout.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress) {
                textview.setTextSize(progress);
            }
        });

        checkLayout.setOnCheckedListener(new CheckLayout.OnCheckedListener() {
            @Override
            public void onChecked(View v, ArrayList<Integer> items, int position) {
                int mode = 0;
                int[] modes = {0x2, 0x04, 0x08, 0x10};
                for (Integer index : items) {
                    mode += modes[index];
                }
                textview.setDivideGravity(mode);
            }
        });

        seekLayout_divider.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress) {
                textview.setDivideSize(progress);
            }
        });

        seekLayout_padding.setOnSeekProgressChangeListener(new SeekLayout.OnSeekProgressChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress) {
                textview.setDividePadding(progress);
            }
        });

        return view;
    }
}

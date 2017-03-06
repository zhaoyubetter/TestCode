package test.better.com.leak.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import test.better.com.leak.R;
import ui.better.CenterGridLayout;

/**
 * Created by zhaoyu1 on 2017/3/1.
 */
public class CenterGridLayoutFragment extends Fragment {

    private CenterGridLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_center_textview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = (CenterGridLayout) view.findViewById(R.id.centerLayout);

        final int[] colorItems = getResources().getIntArray(R.array.color_items);

        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView childView = new TextView(getActivity());
                childView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                int childCount = layout.getChildCount();
                String text = "Text";
                if (0 == childCount) {
                    text = "The first text";
                }
                childView.setText(text + childCount);
                childView.setPadding(50, 20, 50, 20);
                childView.setBackgroundColor(colorItems[new Random().nextInt(colorItems.length)]);
                layout.addView(childView);
            }
        });
        view.findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int childCount = layout.getChildCount();
                if (0 < childCount) {
                    layout.removeViewAt(childCount - 1);
                }
            }
        });
    }
}

package test.better.com.leak.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;


import java.util.ArrayList;

import test.better.com.leak.R;

/**
 * Created by cz on 16/3/7.
 */
public class CheckLayout extends LinearLayout {
    private final ArrayList<Integer> checkItems;
    private OnCheckedListener listener;

    public CheckLayout(Context context) {
        this(context, null);
    }

    public CheckLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkItems = new ArrayList<Integer>();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckLayout);
        setItems(a.getTextArray(R.styleable.CheckLayout_cl_items));
        a.recycle();
    }

    /**
     * 设置复选 条目
     *
     * @param items
     */
    public void setItems(CharSequence[] items) {
        if (null != items) {
            removeAllViews();
            checkItems.clear();
            int length = items.length;
            for (int i = 0; i < length; i++) {
                CharSequence item = items[i];
                AppCompatCheckBox appCompatCheckBox = new AppCompatCheckBox(getContext());
                appCompatCheckBox.setText(item);
                final int index = i;
                appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            checkItems.add(index);
                        } else {
                            checkItems.remove(Integer.valueOf(index));
                        }
                        if (null != listener) {
                            listener.onChecked(buttonView, checkItems, index);
                        }
                    }
                });
                addView(appCompatCheckBox);
            }
        }
    }

    public void setOnCheckedListener(OnCheckedListener listener) {
        this.listener = listener;
    }

    public interface OnCheckedListener {
        void onChecked(View v, ArrayList<Integer> items, int position);
    }
}
package test.better.com.leak.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import test.better.com.leak.R;

/**
 * Created by cz on 16/3/7.
 */
public class RadioLayout extends RadioGroup {
	private OnCheckedListener listener;

	public RadioLayout(Context context) {
		super(context);
	}

	public RadioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadioLayout);
		setItems(a.getTextArray(R.styleable.RadioLayout_rl_items));
		a.recycle();
		setOrientation(HORIZONTAL);
	}

	/**
	 * 设置单选
	 *
	 * @param items
	 */
	public void setItems(CharSequence[] items) {
		if (null != items) {
			removeAllViews();
			int length = items.length;
			for (int i = 0; i < length; i++) {
				CharSequence item = items[i];
				AppCompatRadioButton appCompatCheckBox = new AppCompatRadioButton(getContext());
				appCompatCheckBox.setText(item);
				final int index = i;
				appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (null != listener && isChecked) {
							listener.onChecked(buttonView, index, isChecked);
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
		void onChecked(View v, int position, boolean isChecked);
	}
}
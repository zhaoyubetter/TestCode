package ui.better;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import ui.better.callback.OnMultiSelectListener;
import ui.better.callback.OnRectangleSelectListener;
import ui.better.callback.OnSingleSelectListener;
import ui.better.callback.Selectable;

/**
 * 可单选多选的FlowLayout
 * Created by zhaoyu on 2017/3/19.
 */
public class SelectFlowLayout extends FlowLayout implements Selectable {

	private final SelectHelper mSelectHelper;
	private Drawable mItemSelector;

	public SelectFlowLayout(Context context) {
		this(context, null);
	}

	public SelectFlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SelectFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mSelectHelper = new SelectHelper(this);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectFlowLayout);
		setSelectModeInner(a.getInt(R.styleable.SelectFlowLayout_fl_select_mode, SelectHelper.SINGLE));
		setItemSelector(a.getDrawable(R.styleable.SelectFlowLayout_fl_item_selector));
		setSelectMaxSize(a.getInt(R.styleable.SelectFlowLayout_fl_max_selected_size, 1));
		a.recycle();
	}

	public void setSelectMaxSize(int maxSize) {
		mSelectHelper.setMaxSelectSize(maxSize);
	}

	public void setItemSelector(Drawable selector) {
		this.mItemSelector = selector;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			setBackgroundDrawableCompat(childView);
		}
	}

	@Override
	public void addView(View child, int index, LayoutParams params) {
		super.addView(child, index, params);
		setViewListener(child);
		setBackgroundDrawableCompat(child);
	}

	private void setBackgroundDrawableCompat(View childView) {
		if (null != mItemSelector) {
			Drawable newDrawable = mItemSelector.getConstantState().newDrawable();
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				childView.setBackgroundDrawable(newDrawable);
			} else {
				childView.setBackground(newDrawable);
			}
		}
	}

	/**
	 * 设置view选中事件
	 *
	 * @param view
	 */
	private void setViewListener(final View view) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSelectHelper.setSelectedIndex(v, indexOfChild(v));
				invalidate();
			}
		});
	}

	private void setSelectModeInner(int mode) {
		this.setSelectMode(mode);
	}

	/**
	 * 设置选择模式
	 *
	 * @param mode
	 */
	public void setSelectMode(@SelectHelper.SelectMode int mode) {
		mSelectHelper.setSelectMode(mode);
		invalidate();
	}

	@Override
	public void setItemSelect(boolean select) {
		int childcount = getChildCount();
		for (int i = 0; i < childcount; i++) {
			setItemSelect(i, select);
		}
	}

	@Override
	public void setItemSelect(int index, boolean select) {
		View childView = getChildAt(index);
		if (childView != null) {
			childView.setSelected(select);
		}
	}

	@Override
	public void setOnSingleSelectListener(OnSingleSelectListener listener) {
		mSelectHelper.setOnSingleSelectListener(listener);
	}

	@Override
	public void setOnMultiSelectListener(OnMultiSelectListener listener) {
		mSelectHelper.setOnMultiSelectListener(listener);
	}

	@Override
	public void setOnRectangleSelectListener(OnRectangleSelectListener listener) {
		mSelectHelper.setOnRectangleSelectListener(listener);
	}

	@Override
	public int getSingleSelectedIndex() {
		return mSelectHelper.getSingleSelectedIndex();
	}

	@Override
	public List<Integer> getMultiSelectedIndex() {
		return mSelectHelper.getMultiSelectedIndex();
	}

	@Override
	public int getRectangleStartIndex() {
		return mSelectHelper.getRectangleStartIndex();
	}

	@Override
	public int getRectangleEndIndex() {
		return mSelectHelper.getRectangleEndIndex();
	}
}

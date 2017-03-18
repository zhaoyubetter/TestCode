package ui.better;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 类似 Swing 的流式布局
 * Created by zhaoyu on 2017/3/17.
 */
public class FlowLayout extends ViewGroup {

	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	public static final int BOTTOM = 3;

	@IntDef(value = {LEFT, CENTER, RIGHT, BOTTOM})
	public @interface Gravity {

	}

	/**
	 * 对其方式
	 */
	private int mGravity = BOTTOM;

	private int mVerticalPadding = 10;
	private int mHorziontalPadding = 5;

	private LayoutTransition layoutTransition;

	/**
	 * 所有（每行）要显示的View
	 */
	private SparseArray<List<View>> mLineViews = new SparseArray<>();
	private SparseArray<List<FlowInfomation>> mFlowsInfomation = new SparseArray<>();
	private int mUsedHeight = 0;

	public FlowLayout(Context context) {
		this(context, null);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initTransition();
	}

	/**
	 * setLayoutTransition
	 */
	private void initTransition() {
		layoutTransition = new LayoutTransition();
		//view出现时 view自身的动画效果
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "alpha", 0f, 1F).setDuration(layoutTransition.getDuration(LayoutTransition.APPEARING));
		layoutTransition.setAnimator(LayoutTransition.APPEARING, animator1);

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(null, "alpha", 1F, 0f).setDuration(layoutTransition.getDuration(LayoutTransition.DISAPPEARING));
		layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, animator2);
		//view 动画改变时，布局中的每个子view动画的时间间隔
		layoutTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
		layoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);

		PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
		PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
		PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 1);
		PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);
		final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(this, pvhLeft, pvhTop, pvhRight, pvhBottom).
				setDuration(layoutTransition.getDuration(LayoutTransition.CHANGE_APPEARING));
		layoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);

		final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(this, pvhLeft, pvhTop, pvhRight, pvhBottom).
				setDuration(layoutTransition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
		layoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);

		setLayoutTransition(layoutTransition);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mLineViews.clear();
		mFlowsInfomation.clear();

		// 当前宽
		int widthSpec = MeasureSpec.getSize(widthMeasureSpec);

		// 最终宽高
		int width = 0;
		int height = 0;

		int lineWidth = 0;
		int lineHeight = 0;

		int rowCount = 0;
		int colCount = 0;
		int offsetLeft = 0;
		int wrapHeight = 0;

		int childCount = getChildCount();

		List<View> currentRowView = new ArrayList<>();
		List<FlowInfomation> currentRowFlowInfo = new ArrayList<>();

		for (int i = 0; i < childCount; i++) {
			View v = getChildAt(i);
			if (v.getVisibility() != View.GONE) {
				FlowInfomation flowInfo = new FlowInfomation();

				measureChild(v, widthMeasureSpec, heightMeasureSpec);
				MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
				int childWidth = lp.leftMargin + v.getMeasuredWidth() + lp.rightMargin + mHorziontalPadding;
				int childHeight = lp.topMargin + v.getMeasuredHeight() + lp.bottomMargin + mVerticalPadding;

				if (lineWidth + childWidth + getPaddingLeft() + getPaddingRight() > widthSpec
						&& (lineWidth + childWidth + getPaddingLeft() + getPaddingRight() - mHorziontalPadding) > widthSpec) {    // 换行
					width = Math.max(lineWidth - mHorziontalPadding, width);    // 宽,去掉最后一列的  mHorziontalPadding
					height += lineHeight;    // 换行时增加高度
					// 换行时的还原操作
					lineWidth = childWidth;

					// 当前显示的所有view
					mLineViews.put(rowCount, currentRowView);
					mFlowsInfomation.put(rowCount, currentRowFlowInfo);
					currentRowView = new ArrayList<>();
					currentRowFlowInfo = new ArrayList<>();
					currentRowView.add(v);
					currentRowFlowInfo.add(flowInfo);
					offsetLeft = childWidth;
					wrapHeight += lineHeight;
					rowCount++;
				} else {
					lineWidth += childWidth;
					lineHeight = Math.max(lineHeight, childHeight);

					currentRowView.add(v);
					currentRowFlowInfo.add(flowInfo);
					offsetLeft += childWidth;
					colCount++;
				}

				// 布局信息
				flowInfo.column = colCount;
				flowInfo.row = rowCount;
				flowInfo.rect.left = getPaddingLeft() + offsetLeft - childWidth;
				flowInfo.rect.right = flowInfo.rect.left + v.getMeasuredWidth();
				flowInfo.rect.top = getPaddingTop() + wrapHeight;
				flowInfo.rect.bottom = flowInfo.rect.top + v.getMeasuredHeight();
			}
		}

		// 最后一行操作
		// 因为是在每次换行时，高度增加， 所有循环结束的时候，高度需要增加
		width = Math.max(lineWidth, width);
		height += lineHeight;
		if (rowCount > 1) {    // 如果大于1行，去掉最后一行的 mVerticalPadding
			height -= mVerticalPadding;
		}

		// 最后一行处理
		if (currentRowView.size() > 0 && currentRowFlowInfo.size() > 0) {
			mLineViews.put(rowCount, currentRowView);
			mFlowsInfomation.put(rowCount, currentRowFlowInfo);
		}

		mUsedHeight = height;

		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height + getPaddingBottom() + getPaddingTop(), heightMeasureSpec));
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int linecount = mLineViews.size();
		int spaceWidth = getWidth() - getPaddingRight() - getPaddingLeft();
		int spaceHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		int usedLineWidth = 0;
		int left = 0;
		int top = 0;

		for (int i = 0; i < linecount; i++) {
			List<View> views = mLineViews.get(i);
			List<FlowInfomation> flowInfomations = mFlowsInfomation.get(i);
			usedLineWidth = getLineUsedWidth(flowInfomations);

			for (int j = 0; j < views.size(); j++) {
				final View view = views.get(j);
				final FlowInfomation item = flowInfomations.get(j);
				switch (mGravity) {
					case LEFT:
						view.layout(item.rect.left, item.rect.top, item.rect.right, item.rect.bottom);
						break;
					case CENTER:
						left = (spaceWidth - usedLineWidth) / 2 + item.rect.left;
						view.layout(left, item.rect.top, left + item.rect.width(), item.rect.bottom);
						break;
					case RIGHT:
						left = spaceWidth - usedLineWidth + item.rect.left;
						view.layout(left, item.rect.top, left + item.rect.width(), item.rect.bottom);
						break;
					case BOTTOM:
						top = spaceHeight - mUsedHeight + item.rect.top;
						view.layout(item.rect.left, top, item.rect.right, top + item.rect.height());
						break;
				}
			}
		}
	}

	private int getLineUsedWidth(List<FlowInfomation> infos) {
		int lineWidth = 0;
		for (FlowInfomation i : infos) {
			lineWidth += (i.rect.width());
		}

		lineWidth += (mHorziontalPadding * infos.size() - 1);    // 最后一列，不计算 mHorziontalPadding
		return lineWidth;
	}

	public void setGravity(@Gravity int mGravity) {
		this.mGravity = mGravity;
		invalidate();
	}

	public void setVerticalPadding(int mVerticalPadding) {
		this.mVerticalPadding = mVerticalPadding;
		invalidate();
	}

	public void setHorziontalPadding(int mHorziontalPadding) {
		this.mHorziontalPadding = mHorziontalPadding;
		invalidate();
	}

	class FlowInfomation {
		public int row;
		public int column;
		public final Rect rect;

		public FlowInfomation() {
			rect = new Rect();
		}
	}
}



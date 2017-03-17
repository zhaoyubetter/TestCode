package ui.better.bubbler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import ui.better.R;

/**
 * https://github.com/lguipeng
 */
public class BubbleTextView extends TextView {

	/**
	 * 背景Drawable
	 */
	private BubbleDrawable mBubbleDrawable;

	/**
	 * 箭头宽带
	 */
	private float mArrowWidth;

	/**
	 * 箭头高度
	 */
	private float mArrowHeight;

	/**
	 * 圆弧角度
	 */
	private float mRadius;

	/**
	 * 箭头偏移量
	 */
	private float mArrowOffset;

	/**
	 * 气泡背景色
	 */
	private int mBubbleColor;

	/**
	 * 箭头方向
	 */
	private int mArrowDirection;

	/**
	 * 箭头是否居中
	 */
	private boolean mArrowCenter;


	public BubbleTextView(Context context) {
		this(context, null);
	}

	public BubbleTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.textViewStyle);
	}

	public BubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BubbleTextView);
		try {
			setArrowWidth(a.getDimension(R.styleable.BubbleTextView_bt_arrow_width, BubbleDrawable.Builder.DEFAULT_ARROW_WIDTH));
			setArrowHeight(a.getDimension(R.styleable.BubbleTextView_bt_arrow_height, BubbleDrawable.Builder.DEFAULT_ARROW_WIDTH));
			setArrowOffset(a.getDimension(R.styleable.BubbleTextView_bt_arrow_offset, BubbleDrawable.Builder.DEFAULT_ARROW_OFFSET));
			setArrowCenter(a.getBoolean(R.styleable.BubbleTextView_bt_arrow_center, false));
			setArrowDirection(a.getInt(R.styleable.BubbleTextView_bt_arrow_direction, BubbleDrawable.LEFT));
			setRadius(a.getDimension(R.styleable.BubbleTextView_bt_radius, BubbleDrawable.Builder.DEFAULT_RADIUS));
			setBubbleColor(a.getColor(R.styleable.BubbleTextView_bt_bubble_color, BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR));

			setInnerPadding();
		} finally {
			if (null != a) {
				a.recycle();
			}
		}
	}

	/**
	 * 箭头会有填充
	 */
	private void setInnerPadding() {
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		switch (mArrowDirection) {
			case BubbleDrawable.LEFT:
				paddingLeft += mArrowWidth;
				break;
			case BubbleDrawable.TOP:
				paddingTop += mArrowHeight;
				break;
			case BubbleDrawable.RIGHT:
				paddingRight += mArrowWidth;
				break;
			case BubbleDrawable.BOTTOM:
				paddingBottom += mArrowHeight;
				break;
		}

		setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w > 0 && h > 0) {
			reset(w, h);
		}
	}

	private void reset(int w, int h) {
		mBubbleDrawable = new BubbleDrawable.Builder(new RectF(0, 0, w, h))
				.arrowCenter(mArrowCenter)
				.arrowDirection(mArrowDirection)
				.arrowHeight(mArrowHeight)
				.arrowWidth(mArrowWidth)
				.arrowOffset(mArrowOffset)
				.bubbleColor(mBubbleColor)
				.radius(mRadius).build();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mBubbleDrawable != null) {
			mBubbleDrawable.draw(canvas);
		}
		super.onDraw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		reset(getWidth(), getHeight());
	}

	public void setArrowWidth(float mArrowWidth) {
		this.mArrowWidth = mArrowWidth;
		invalidate();
	}

	public void setArrowHeight(float mArrowHeight) {
		this.mArrowHeight = mArrowHeight;
		invalidate();
	}

	public void setRadius(float mRadius) {
		this.mRadius = mRadius;
		invalidate();
	}

	public void setArrowOffset(float mArrowOffset) {
		this.mArrowOffset = mArrowOffset;
		invalidate();
	}

	public void setBubbleColor(int mBubbleColor) {
		this.mBubbleColor = mBubbleColor;
		invalidate();
	}

	public void setArrowDirection(int mArrowDirection) {
		this.mArrowDirection = mArrowDirection;
		invalidate();
	}

	public void setArrowCenter(boolean mArrowCenter) {
		this.mArrowCenter = mArrowCenter;
		invalidate();
	}
}

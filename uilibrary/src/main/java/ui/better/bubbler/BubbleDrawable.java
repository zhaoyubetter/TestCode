package ui.better.bubbler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 9 patch
 * @see <a>https://github.com/lguipeng</a>
 */
public class BubbleDrawable extends Drawable {

	/**
	 * 大小
	 */
	private RectF mRect;

	/**
	 * 路径
	 */
	private Path mPath = new Path();

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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

	private BubbleDrawable(Builder builder) {
		this.mRect = builder.mRectF;
		this.mRadius = builder.mRadius;
		this.mArrowWidth = builder.mArrowWidth;
		this.mArrowHeight = builder.mArrowHeight;
		this.mArrowOffset = builder.mArrowOffset;
		this.mBubbleColor = builder.mBubbleColor;
		this.mArrowDirection = builder.mArrowDirection;
		this.mArrowCenter = builder.mArrowCenter;
	}

	@IntDef({LEFT, TOP, RIGHT, BOTTOM})
	@Retention(RetentionPolicy.SOURCE)
	public @interface ArrowDirection {

	}

	public static final int LEFT = 0;
	public static final int TOP = 1;
	public static final int RIGHT = 2;
	public static final int BOTTOM = 3;

	@Override
	public void draw(Canvas canvas) {
		mPaint.setColor(mBubbleColor);
		mPaint.setStyle(Paint.Style.FILL);
		setUpPath(mArrowDirection, mPath);
		canvas.drawPath(mPath, mPaint);
	}

	private void setUpPath(int mArrowDirection, Path mPath) {
		switch (mArrowDirection) {
			case LEFT:
				setUpLeftPath(mRect, mPath);
				break;
			case TOP:
				setUpTopPath(mRect, mPath);
				break;
			case RIGHT:
				setUpRightPath(mRect, mPath);
				break;
			case BOTTOM:
				setUpBottomPath(mRect, mPath);
				break;
		}
	}

	private void setUpBottomPath(RectF rect, Path path) {
		if (mArrowCenter) {
			mArrowOffset = (mRect.width() - mArrowHeight) / 2;    // rotate so is height
		}

		path.moveTo(rect.left + mArrowOffset, rect.top);
		// 右上角
		path.lineTo(rect.right - mRadius, rect.top);
		path.arcTo(new RectF(rect.right - mRadius, rect.top, rect.right, rect.top + mRadius), 270, 90);

		// 右下角
		path.lineTo(rect.right, rect.bottom - mRadius - mArrowHeight);
		path.arcTo(new RectF(rect.right - mRadius, rect.bottom - mRadius - mArrowHeight, rect.right, rect.bottom - mArrowHeight), 0, 90);

		// 角
		path.lineTo(rect.right - mArrowOffset, rect.bottom - mArrowHeight);
		path.lineTo(rect.right - mArrowOffset - mArrowWidth / 2, rect.bottom);
		path.lineTo(rect.right - mArrowOffset - mArrowWidth, rect.bottom - mArrowHeight);

		// 左下
		path.lineTo(rect.left + mRadius, rect.bottom - mArrowHeight);
		path.arcTo(new RectF(rect.left, rect.bottom - mRadius - mArrowHeight, rect.left + mRadius, rect.bottom - mArrowHeight), 90, 90);

		// 左上
		path.lineTo(rect.left, rect.top + mRadius);
		path.arcTo(new RectF(rect.left, rect.top, rect.left + mRadius, mRect.top + mRadius), 180, 90);
		path.close();
	}

	/**
	 * 右
	 */
	private void setUpRightPath(RectF rect, Path path) {
		if (mArrowCenter) {
			mArrowOffset = (rect.bottom - rect.top - mArrowHeight) / 2;
		}

		// 右上角
		path.moveTo(rect.left + mRadius, rect.top);
		path.lineTo(rect.width() - mRadius - mArrowWidth, rect.top);
		path.arcTo(new RectF(rect.right - mRadius - mArrowWidth, rect.top, rect.right - mArrowWidth, rect.top + mRadius), 270, 90);

		// 箭头
		path.lineTo(rect.right - mArrowWidth, mArrowOffset);
		path.lineTo(rect.right, mArrowOffset + mArrowHeight / 2);
		path.lineTo(rect.right - mArrowWidth, mArrowHeight + mArrowOffset);

		// 右下角
		path.lineTo(rect.right - mArrowWidth, rect.bottom - mRadius);
		path.arcTo(new RectF(rect.right - mArrowWidth - mRadius, rect.bottom - mRadius, rect.width() - mArrowWidth, rect.bottom), 0, 90);

		// 左下角
		path.lineTo(rect.left + mRadius, rect.bottom);
		path.arcTo(new RectF(rect.left, rect.bottom - mRadius, rect.left + mArrowWidth + mRadius, rect.bottom), 90, 90);

		mPath.lineTo(rect.left, rect.top + mRadius);
		path.arcTo(new RectF(rect.left, rect.top, rect.left + mRadius, rect.top + mRadius), 180, 90);
		path.close();
	}

	/**
	 * 上
	 */
	private void setUpTopPath(RectF rect, Path path) {
		if (mArrowCenter) {
			mArrowOffset = (mRect.width() - mArrowHeight) / 2;    // rotate so is height
		}

		// 角
		path.moveTo(rect.left + mArrowOffset, rect.top + mArrowHeight);
		path.lineTo(rect.left + mArrowOffset, rect.top + mArrowHeight);
		path.lineTo(rect.left + mArrowOffset + mArrowWidth / 2, rect.top);
		path.lineTo(rect.left + mArrowOffset + mArrowWidth, rect.top + mArrowHeight);

		// 右上角
		path.lineTo(rect.right - mRadius, rect.top + mArrowHeight);
		path.arcTo(new RectF(rect.right - mRadius, rect.top + mArrowHeight, rect.right, rect.top + mArrowHeight + mRadius), 270, 90);

		// 右下角
		path.lineTo(rect.right, rect.bottom - mRadius);
		path.arcTo(new RectF(rect.right - mRadius, rect.bottom - mRadius, rect.right, rect.bottom), 0, 90);

		// 左下
		path.lineTo(rect.left + mRadius, rect.bottom);
		path.arcTo(new RectF(rect.left, rect.bottom - mRadius, rect.left + mRadius, rect.bottom), 90, 90);

		// 左上
		path.lineTo(rect.left, rect.top + mArrowHeight + mRadius);
		path.arcTo(new RectF(rect.left, rect.top + mArrowHeight, rect.left + mRadius, mRect.top + mArrowHeight + mRadius), 180, 90);
		path.close();
	}

	/**
	 * 左
	 *
	 * @param rect
	 * @param path
	 */
	private void setUpLeftPath(RectF rect, Path path) {
		if (mArrowCenter) {
			mArrowOffset = (rect.bottom - rect.top - mArrowHeight) / 2;
		}

		// 右上角(需要加上箭头宽)
		path.moveTo(rect.left + mRadius, rect.top);
		path.lineTo(rect.width() - mRadius, rect.top);
		path.arcTo(new RectF(rect.right - mRadius, rect.top, rect.right, rect.top + mRadius), 270, 90);

		// 右下角
		path.lineTo(rect.right, rect.bottom - mRadius);
		path.arcTo(new RectF(rect.right - mRadius, rect.bottom - mRadius, rect.width(), rect.bottom), 0, 90);

		// 左下角
		path.lineTo(rect.left + mArrowWidth + mRadius, rect.bottom);
		path.arcTo(new RectF(rect.left + mArrowWidth, rect.bottom - mRadius, rect.left + mArrowWidth + mRadius, rect.bottom), 90, 90);

		path.lineTo(rect.left + mArrowWidth, mArrowHeight + mArrowOffset);
		path.lineTo(rect.left, mArrowOffset + mArrowHeight / 2);
		path.lineTo(rect.left + mArrowWidth, mArrowOffset);

		path.arcTo(new RectF(rect.left + mArrowWidth, rect.top, rect.left + mArrowWidth + mRadius, rect.top + mRadius), 180, 90);
		path.close();
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {
		mPaint.setColorFilter(colorFilter);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	/**
	 * 建造者模式
	 */
	public static class Builder {
		/**
		 * 箭头默认宽度
		 */
		public static float DEFAULT_ARROW_WIDTH = 25;
		/**
		 * 箭头默认高度
		 */
		public static float DEFAULT_ARROW_HEIGHT = 25;

		/**
		 * 默认圆角半径
		 */
		public static float DEFAULT_RADIUS = 20;
		/**
		 * 默认箭头偏移量
		 */
		public static float DEFAULT_ARROW_OFFSET = 50;
		/**
		 * 气泡默认背景颜色
		 */
		public static int DEFAULT_BUBBLE_COLOR = Color.RED;

		private RectF mRectF;
		private float mArrowWidth = DEFAULT_ARROW_WIDTH;
		private float mArrowHeight = DEFAULT_ARROW_HEIGHT;
		private float mRadius = DEFAULT_RADIUS;
		private float mArrowOffset = DEFAULT_ARROW_OFFSET;

		private int mBubbleColor = DEFAULT_BUBBLE_COLOR;
		private boolean mArrowCenter = true;
		private int mArrowDirection = BOTTOM;

		public Builder(RectF rect) {
			this.mRectF = rect;
		}

		public Builder rect(RectF rect) {
			this.mRectF = rect;
			return this;
		}

		public Builder arrowWidth(float width) {
			this.mArrowWidth = width;
			return this;
		}

		public Builder arrowHeight(float height) {
			this.mArrowHeight = height;
			return this;
		}

		public Builder radius(float radius) {
			this.mRadius = radius;
			return this;
		}

		public Builder arrowOffset(int arrowOffset) {
			this.mArrowOffset = arrowOffset;
			return this;
		}

		public Builder bubbleColor(@ColorInt int color) {
			this.mBubbleColor = color;
			return this;
		}

		public Builder arrowCenter(boolean isCenter) {
			this.mArrowCenter = isCenter;
			return this;
		}

		public Builder arrowDirection(@ArrowDirection int arrowDirection) {
			this.mArrowDirection = arrowDirection;
			return this;
		}

		public BubbleDrawable build() {
			return new BubbleDrawable(this);
		}
	}
}

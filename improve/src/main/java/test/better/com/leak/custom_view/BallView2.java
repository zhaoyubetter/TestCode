package test.better.com.leak.custom_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by zhaoyu on 2017/3/23.
 */

public class BallView2 extends View {

	private Paint mPaint;
	private Path mPath;

	private int radius = 50;

	private float mDegree;

	public BallView2(Context context) {
		this(context, null);
	}

	public BallView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BallView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPath = new Path();

		initPoint();
		updatePath(0, 0);
	}

	HorizontalLine mTopLine;
	HorizontalLine mBottomLine;
	VerticalLine mLeftLine;
	VerticalLine mRightLine;

	private void initPoint() {
		mTopLine = new HorizontalLine(0, -radius);        // 上面的3个点 (P11, P0, P1)
		mBottomLine = new HorizontalLine(0, radius);        // 下面的3个点（P7,	P6,	P5）
		mLeftLine = new VerticalLine(-radius, 0);        // 左边3个点	  (p10,p9,p8)
		mRightLine = new VerticalLine(radius, 0);        // 右边3个点   (p2,p3,p4)
	}

	private static float points2Degrees(float x1, float y1, float x2, float y2) {
		double angle = Math.atan2(y2 - y1, x2 - x1);
		return (float) Math.toDegrees(angle);
	}


	// 实现圆
	private void updatePath(float x, float y) {
		float distance = distance(mPrevX, mPrevY, x, y);
		float longRadius = radius + distance;
		float shortRadius = radius - distance * 0.1f;
		mDegree = points2Degrees(mPrevX, mPrevY, x, y);

		// 修改相关参数，缩放
		mRightLine.setX(longRadius);
		mLeftLine.setX(-shortRadius);
		mTopLine.setY(-shortRadius);
		mBottomLine.setY(shortRadius);

		mPath.reset();
		mPath.moveTo(mTopLine.middle.x, mTopLine.middle.y);
		mPath.cubicTo(mTopLine.right.x, mTopLine.right.y,
				mRightLine.top.x, mRightLine.top.y,
				mRightLine.middle.x, mRightLine.middle.y);
		mPath.cubicTo(mRightLine.bottom.x, mRightLine.bottom.y,
				mBottomLine.right.x, mBottomLine.right.y,
				mBottomLine.middle.x, mBottomLine.middle.y);
		mPath.cubicTo(mBottomLine.left.x, mBottomLine.left.y,
				mLeftLine.bottom.x, mLeftLine.bottom.y,
				mLeftLine.middle.x, mLeftLine.middle.y);
		mPath.cubicTo(mLeftLine.top.x, mLeftLine.top.y,
				mTopLine.left.x, mTopLine.left.y,
				mTopLine.middle.x, mTopLine.middle.y);

		invalidate();
	}

	private float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.translate(getWidth() / 2, getHeight() / 2);
		canvas.rotate(mDegree);
		canvas.drawPath(mPath, mPaint);
		canvas.restore();
	}

	private float mPrevX;
	private float mPrevY;
	private boolean mIsDown;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mPrevX = event.getX();
				mPrevY = event.getY();

				int centerX = getWidth() / 2;
				int centerY = getHeight() / 2;

				//点击位置x坐标与圆心的x坐标的距离
				float distanceX = Math.abs(centerX - mPrevX);
				//点击位置y坐标与圆心的y坐标的距离
				float distanceY = Math.abs(centerY - mPrevY);
				//点击位置与圆心的直线距离
				int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
				if (distanceZ > radius) {
					mIsDown = false;
				} else {
					mIsDown = true;
					return true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (mIsDown) {
					updatePath(event.getX(), event.getY());
					return true;
				}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (mIsDown) {
					float endX = event.getX();
					float endY = event.getY();
					mIsDown = false;
					back(endX, endY);        // 还原
					return true;
				}

				break;
		}

		return super.onTouchEvent(event);
	}

	private void back(final float x, final float y) {
		final float diffX = x - mPrevX;
		final float diffY = y - mPrevY;

		ValueAnimator animator = new ValueAnimator();
		animator.setDuration(200);
		animator.setFloatValues(0, 1);
		animator.setInterpolator(new AccelerateInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float fract = animation.getAnimatedFraction();
				updatePath(x - diffX * fract, y - diffY * fract);
			}
		});
		animator.start();
	}

	// 魔力数字
	private float c = 0.551915024494f;

	class HorizontalLine {
		public PointF left = new PointF(); //P7 P11
		public PointF middle = new PointF(); //P0 P6
		public PointF right = new PointF(); //P1 P5

		public HorizontalLine(float x, float y) {
			left.x = -radius * c;
			left.y = y;
			middle.x = x;
			middle.y = y;
			right.x = radius * c;
			right.y = y;
		}

		public void setY(float y) {
			left.y = y;
			middle.y = y;
			right.y = y;
		}

	}

	class VerticalLine {
		public PointF top = new PointF(); //P2 P10
		public PointF middle = new PointF(); //P3 P9
		public PointF bottom = new PointF(); //P4 P8


		public VerticalLine(float x, float y) {
			top.x = x;
			top.y = -radius * c;
			middle.x = x;
			middle.y = y;
			bottom.x = x;
			bottom.y = radius * c;
		}


		public void setX(float x) {
			top.x = x;
			middle.x = x;
			bottom.x = x;
		}
	}
}

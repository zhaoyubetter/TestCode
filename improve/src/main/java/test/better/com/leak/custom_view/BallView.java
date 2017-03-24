package test.better.com.leak.custom_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by zhaoyu on 2017/3/23.
 */

public class BallView extends View {

	private final float sMagicNumber = 0.55228475f;

	private Paint mPaint;
	private Path mPath;
	private int radius = 200;
	private float mPrevX, mPrevY;
	private boolean mIsDown;
	private float mDegrees;

	public BallView(Context context) {
		this(context, null);
	}

	public BallView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPath = new Path();
		updatePath(0, 0);
	}

	/**
	 * 2点间的距离
	 *
	 * @return
	 */
	public static float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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
		mDegrees = points2Degrees(mPrevX, mPrevY, x, y);

		mPath.reset();
		mPath.moveTo(0, -radius);
		mPath.cubicTo(radius * sMagicNumber, -radius
				, longRadius, -radius * sMagicNumber
				, longRadius, 0);
		//mPath.lineTo(0, 0);

		mPath.moveTo(0, radius);
		mPath.cubicTo(radius * sMagicNumber, radius
				, longRadius, radius * sMagicNumber
				, longRadius, 0);
		//mPath.lineTo(0, 0);

		// 左上1/4圆
		mPath.moveTo(0, -radius);
		mPath.cubicTo(-radius * sMagicNumber, -radius
				, -shortRadius, -radius * sMagicNumber
				, -shortRadius, 0);
		//mPath.lineTo(0, 0);

		// 左下1/4圆
		mPath.moveTo(0, radius);
		mPath.cubicTo(-radius * sMagicNumber, radius
				, -shortRadius, radius * sMagicNumber
				, -shortRadius, 0);
		//mPath.lineTo(0, 0);
		invalidate();


		/*
		mPath.reset();
		// 4条三阶曲线来表示圆
		// 右上1/4圆
		mPath.lineTo(0, -radius);
		mPath.cubicTo(radius * sMagicNumber, -radius
				, radius, -radius * sMagicNumber
				, radius, 0);
		mPath.lineTo(0, 0);

		// 右下1/4圆
		mPath.lineTo(0, radius);
		mPath.cubicTo(radius * sMagicNumber, radius
				, radius, radius * sMagicNumber
				, radius, 0);
		mPath.lineTo(0, 0);

		// 左上1/4圆
		mPath.lineTo(0, -radius);
		mPath.cubicTo(-radius * sMagicNumber, -radius
				, -radius, -radius * sMagicNumber
				, -radius, 0);
		mPath.lineTo(0, 0);

		// 左下1/4圆
		mPath.lineTo(0, radius);
		mPath.cubicTo(-radius * sMagicNumber, radius
				, -radius, radius * sMagicNumber
				, -radius, 0);
		mPath.lineTo(0, 0);
		*/
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				// 判断是否点击在圆内
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
				if(distanceZ > radius) {
					mIsDown = false;
				} else {
					mIsDown = true;
					return true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (!mIsDown) break;
				updatePath(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (!mIsDown) break;
				float endX = event.getX();
				float endY = event.getY();
				mIsDown = false;
				back(endX, endY);        // 还原
				return  true;
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

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.translate(getWidth() / 2, getHeight() / 2);
		canvas.rotate(mDegrees);
		canvas.drawPath(mPath, mPaint);
		canvas.restore();
	}
}

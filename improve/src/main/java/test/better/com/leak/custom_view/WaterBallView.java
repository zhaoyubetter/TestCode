package test.better.com.leak.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;


/**
 * Created by zhaoyu on 2017/3/24.
 */

public class WaterBallView extends View {

	// 魔力数字
	private float c = 0.551915024494f;

	private Paint mPaint;
	private Path mPath;
	private float mRadius = 50;
	/**
	 * 最大移动距离
	 */
	private float mMaxMoveDistance;

	/**
	 * 时间控制
	 */
	private float mInterpolatedTime = 0.0f;

	HorizontalLine mTopLine;
	HorizontalLine mBottomLine;
	VerticalLine mLeftLine;
	VerticalLine mRightLine;

	public WaterBallView(Context context) {
		this(context, null);
	}

	public WaterBallView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WaterBallView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPath = new Path();

		mTopLine = new HorizontalLine(0, -mRadius);        // 上面的3个点 (P11, P0, P1)
		mBottomLine = new HorizontalLine(0, mRadius);        // 下面的3个点（P7,	P6,	P5）
		mLeftLine = new VerticalLine(-mRadius, 0);        // 左边3个点	  (p10,p9,p8)
		mRightLine = new VerticalLine(mRadius, 0);        // 右边3个点   (p2,p3,p4)
	}

	private void updatePath() {
		mPath.reset();

		// 修改相关参数，缩放
		if (mInterpolatedTime >= 0 && mInterpolatedTime <= 0.2f) {
			model1(mInterpolatedTime);
		} else if (mInterpolatedTime > 0.2f && mInterpolatedTime <= 0.5) {
			model2(mInterpolatedTime);
		} else if (mInterpolatedTime > 0.5 && mInterpolatedTime <= 0.8) {
			model3(mInterpolatedTime);
		} else if (mInterpolatedTime > 0.8f && mInterpolatedTime <= 0.9) {
			model4(mInterpolatedTime);
		} else if (mInterpolatedTime > 0.9f && mInterpolatedTime <= 1.0f) {
			model5(mInterpolatedTime);
		}

		// 上下 波动
		mTopLine.setY(-mRadius + (int) (Math.sin(mInterpolatedTime * Math.PI) * (1 / 6f * mRadius)));
		mBottomLine.setY(mRadius - ((int) (Math.sin(mInterpolatedTime * Math.PI) * (1 / 6f * mRadius))));

		// 总偏移
		float offset = mMaxMoveDistance * (mInterpolatedTime);
		offset = offset > 0 ? offset : 0;
		mLeftLine.adjustAllX(offset);
		mRightLine.adjustAllX(offset);
		mTopLine.adjustAllX(offset);
		mBottomLine.adjustAllX(offset);

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
	}

	/**
	 * 还原初始位置
	 */
	private void model0() {
		mTopLine = new HorizontalLine(0, -mRadius);        // 上面的3个点 (P11, P0, P1)
		mBottomLine = new HorizontalLine(0, mRadius);      // 下面的3个点（P7,	P6,	P5）
		mLeftLine = new VerticalLine(-mRadius, 0);        // 左边3个点	  (p10,p9,p8)
		mRightLine = new VerticalLine(mRadius, 0);        // 右边3个点   (p2,p3,p4)
	}

	// 右拉伸状态
	private void model1(float time) {
		model0();
		mRightLine.setX(mRadius + mRadius * time * 5);	   // 右边3个点统一右移一点
	}

	// 左右拉伸
	private void model2(float time) {
		model1(0.2f);	 // 右移到最大
		time = (time - 0.2f) * (10f / 3);
		float shrinkTopBottom = c * 0.45f;

		// 上下 右移
		mTopLine.adjustAllX(mRadius / 2 * time);
		mBottomLine.adjustAllX(mRadius / 2 * time);

		// 左右 垂直压缩
		mRightLine.adjustY(shrinkTopBottom * time);
		mLeftLine.adjustY(shrinkTopBottom * time);
	}

	// 左拉伸
	private void model3(float time) {
		model2(0.5f);

		time = (time - 0.5f) * (10f / 3);
		float shrinkTopBottom = c * 0.45f;

		// 上下 右移
		mTopLine.adjustAllX(mRadius / 2 * time);
		mBottomLine.adjustAllX(mRadius / 2 * time);

		// 左右 还原
		mLeftLine.adjustY(-shrinkTopBottom * time);
		mRightLine.adjustY(-shrinkTopBottom * time);

		// 左拉伸
		mLeftLine.adjustAllX(mRadius / 2 * time);
	}

	private void model4(float time) {
		model3(0.8f);
		time = (time - 0.8f) * 10;

		// 左继续
		mLeftLine.adjustAllX(mRadius / 2 * time);
	}

	// 还原
	private void model5(float time) {
		model4(0.9f);
		time = time - 0.9f;
		mLeftLine.adjustAllX((float) (Math.sin(Math.PI * time * 10f) * (2 / 10f * mRadius)));
	}


	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mMaxMoveDistance = getWidth() - mRadius * 4;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.translate(mRadius * 2, mRadius * 2);
		updatePath();
		canvas.drawPath(mPath, mPaint);
		canvas.restore();
	}

	private class MoveAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			mInterpolatedTime = interpolatedTime;
			invalidate();
		}
	}

	public void startAnimation() {
		mPath.reset();
		mInterpolatedTime = 0;
		MoveAnimation move = new MoveAnimation();
		move.setDuration(3000);
		move.setInterpolator(new AccelerateDecelerateInterpolator());
		//move.setRepeatCount(Animation.INFINITE);
		//move.setRepeatMode(Animation.REVERSE);
		startAnimation(move);
	}

	class HorizontalLine {
		public PointF left = new PointF(); //P7 P11
		public PointF middle = new PointF(); //P0 P6
		public PointF right = new PointF(); //P1 P5

		public HorizontalLine(float x, float y) {
			left.x = -mRadius * c;
			left.y = y;
			middle.x = x;
			middle.y = y;
			right.x = mRadius * c;
			right.y = y;
		}

		public void setY(float y) {
			left.y = y;
			middle.y = y;
			right.y = y;
		}

		public void adjustAllX(float offset) {
			middle.x += offset;
			left.x += offset;
			right.x += offset;
		}
	}

	class VerticalLine {
		public PointF top = new PointF(); //P2 P10
		public PointF middle = new PointF(); //P3 P9
		public PointF bottom = new PointF(); //P4 P8

		public VerticalLine(float x, float y) {
			top.x = x;
			top.y = -mRadius * c;
			middle.x = x;
			middle.y = y;
			bottom.x = x;
			bottom.y = mRadius * c;
		}

		public void setX(float x) {
			top.x = x;
			middle.x = x;
			bottom.x = x;
		}

		public void adjustY(float offset) {
			top.y -= offset;
			bottom.y += offset;
		}

		public void adjustAllX(float offset) {
			middle.x += offset;
			top.x += offset;
			bottom.x += offset;
		}
	}
}

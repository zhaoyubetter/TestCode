package test.better.com.leak.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 左右滑动view
 */
public class CustomHorizontalView extends ViewGroup {

	private static final String TAG = "CustomHorizontalView";

	private GestureDetector mDetector;

	private Scroller mScroller;

	private boolean mIsFling;

	private int mCurIndex;

	public CustomHorizontalView(Context context) {
		this(context, null);
	}

	public CustomHorizontalView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mScroller = new Scroller(getContext());
		mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				CustomHorizontalView.this.scrollBy((int) distanceX, 0);
				Log.e(TAG, "" + distanceX + "， scrollX: " + getScrollX());
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				mIsFling = true;
				if (velocityX > 0 && mCurIndex > 0) {
					moveToDest(mCurIndex - 1);
				} else if (velocityX < 0 && mCurIndex < getChildCount() - 1) {
					moveToDest(mCurIndex + 1);
				} else {
					// 其他情况
					moveToDest();
				}
				return false;
			}
		});
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		// 布局(全部全屏)
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			child.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
		}
	}


	/**
	 * action_up
	 */
	private void moveToDest() {
		// 目标view索引下标
		int desIndex = (getScrollX() + getWidth() / 2) / getWidth();
		if (desIndex > getChildCount() - 1) {
			desIndex = getChildCount() - 1;
		}
		if (desIndex < 0) {
			desIndex = 0;
		}
		moveToDest(desIndex);
	}

	/**
	 * 移动到指定的下标
	 *
	 * @param curIndex
	 */
	private void moveToDest(int curIndex) {
		mCurIndex = curIndex;
		int distance = curIndex * getWidth() - getScrollX();
		Log.e(TAG, String.format("scrollX: %s, distance: %s", getScrollX(), distance));
		// 启动弹性滑动
		mScroller.startScroll(getScrollX(), getScrollY(), distance, 0);
		invalidate();
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), 0);
			invalidate();
		}
	}

	private float lastX;
	private float lastY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (!mIsFling) {
					moveToDest();
				}
				mIsFling = false;
				break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean result = false;
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = ev.getX();
				lastY = ev.getY();
				result = false;
				// 让Detector收到DOWN事件，如果不设置，则表示ViewGroup将没有down这个事件，
				// 这个时候，滑动的时候，会发生错乱；
				// 根据事件分发原则，只有在 onInterceptTouchEvent返回true时，onTouchEvent才执行；
				// 返回false的时候，down被子view消耗了，这个时候，当前 容器 onTouchEvent没有收到down事件；
				mDetector.onTouchEvent(ev);

				// 滚动没有完成的时候
				if(!mScroller.isFinished()) {
					mScroller.abortAnimation();
					return true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int distanceX = (int) Math.abs(ev.getX() - lastX);
				int distanceY = (int) Math.abs(ev.getY() - lastY);
				int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
				if (distanceX > distanceY && distanceX > slop) {
					result = true;         // 消耗事件，交由onTouchEvent处理
				} else {
					result = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				result = false;
		}

		return result;
	}
}

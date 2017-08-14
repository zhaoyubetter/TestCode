package test.better.com.leak.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 嵌套滑动冲突
 * Created by zhaoyu1 on 2017/2/22.
 */
public class ConflictScrollView extends ScrollView {

	private ListView mListView;

	public ConflictScrollView(Context context) {
		this(context, null);
	}

	public ConflictScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ConflictScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setListView(ListView list) {
		this.mListView = list;
	}

	private float mLastY;
	private boolean isReDispatch;
	private boolean isDrag = false;

    /*
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                intercept = super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                // 第一个条目完成可见时，并且向下滑动时，才拦截事件
                if (mListView.getFirstVisiblePosition() == 0 &&
                        mListView.getChildAt(0).getTop() >= mListView.getPaddingTop() &&
                        y > mDownY) {
                    intercept = true;
                    break;
                }

                // 最后一个条目完成可见时，并且向上滑动，拦截事件
                if (mListView.getLastVisiblePosition() == mListView.getCount() - 1) {
                    final View lastVisibleChild = mListView.getChildAt(mListView.getLastVisiblePosition() - mListView.getFirstVisiblePosition());
                    if (lastVisibleChild != null && y < mDownY) {
                        // lastVisibleChild 不完全显示的 bottom 大于 完全时的 bottom
                        intercept = lastVisibleChild.getBottom() + mListView.getPaddingBottom() <= mListView.getHeight();
                        Log.e("better", String.format("lastChild Bottom: %s, listView Height: %s, listView paddingBtm :%s", lastVisibleChild.getBottom(), mListView.getHeight(), mListView.getPaddingBottom()));
                    }
                }
                break;
        }

        // Log.e("better", intercept + "" + " , top: " + mListView.getChildAt(0).getTop() + ", listView height: " + mListView.getHeight());

        return intercept;
    }*/

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercept = false;
		float y = ev.getY();

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = y;
				intercept = super.onInterceptTouchEvent(ev);
				break;
			case MotionEvent.ACTION_MOVE:
				// 第一个条目完成可见时，并且向下滑动时，才拦截事件
				float dy = y - mLastY;
				if (Math.abs(dy) > ViewConfiguration.getTouchSlop()) {
					isDrag = true;
					if (!ViewCompat.canScrollVertically(mListView, -1) && dy > 0) {
						intercept = true;
					}
					if (!ViewCompat.canScrollVertically(mListView, 1) && dy < 0) {
						intercept = true;
					}
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				isDrag = false;
		}

		return intercept;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				float dy = y - mLastY;
				Log.e("better", "onTouchEvent: " + dy);
				if(!isDrag && Math.abs(dy) > ViewConfiguration.getTouchSlop()) {
					isDrag = true;
				}
				if (isDrag) {
					if (dy > 0 && !ViewCompat.canScrollVertically(this, -1) && ViewCompat.canScrollVertically(mListView, -1)) {
						ev.setAction(MotionEvent.ACTION_DOWN);
						dispatchTouchEvent(ev);
						isReDispatch = false;
						Log.e("better", "redispatch --》 onTouchEvent");
					}
					if (dy < 0 && !ViewCompat.canScrollVertically(this, 1) && ViewCompat.canScrollVertically(mListView, 1)) {
						ev.setAction(MotionEvent.ACTION_DOWN);
						dispatchTouchEvent(ev);
						isReDispatch = false;
						Log.e("better", "redispatch --》 onTouchEvent");
					}
				}

				mLastY = y;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				isDrag = false;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				float dy = y - mLastY;
				if (Math.abs(dy) > ViewConfiguration.getTouchSlop()) {
					// 内层下拉到头了 并且 外层还能下拉时，重发事件
					if (!isReDispatch && !ViewCompat.canScrollVertically(mListView, -1) && dy > 0 && ViewCompat.canScrollVertically(this, -1)) {
						isReDispatch = true;
						Log.e("better", "下拉到头了，外层还可以下拉，重发事件");
						ev.setAction(MotionEvent.ACTION_CANCEL);
						MotionEvent ev2 = MotionEvent.obtain(ev);
						ev2.setAction(MotionEvent.ACTION_DOWN);
						dispatchTouchEvent(ev);
						return dispatchTouchEvent(ev2);
					}

					if (!isReDispatch && !ViewCompat.canScrollVertically(mListView, 1) && dy < 0 && ViewCompat.canScrollVertically(this, 1)) {
						isReDispatch = true;
						Log.e("better", "上拉  到头了，外层还可以上拉，重发事件");
						ev.setAction(MotionEvent.ACTION_CANCEL);
						MotionEvent ev2 = MotionEvent.obtain(ev);
						ev2.setAction(MotionEvent.ACTION_DOWN);
						dispatchTouchEvent(ev);
						return dispatchTouchEvent(ev2);
					}
				}

				break;

			case MotionEvent.ACTION_UP:
				isReDispatch = false;
		}


		return super.dispatchTouchEvent(ev);
	}
}

package test.better.com.leak.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * @see test.better.com.leak.view.slideconflict.SlideConflict4Activity
 */
public class InnerListView extends ListView {

    private ScrollView mScrollView;

    public void setScrollView(ScrollView view) {
        this.mScrollView = view;
    }

    public InnerListView(Context context) {
        this(context, null);
    }

    public InnerListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mDownY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mScrollView.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                // 向下滑动
                if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= getPaddingTop() &&
                        y > mDownY) {
                    mScrollView.requestDisallowInterceptTouchEvent(false);
                    break;
                }

                if (getLastVisiblePosition() == getCount() - 1) {
                    final View lastVisibleChild = getChildAt(getLastVisiblePosition() - getFirstVisiblePosition());
                    if (lastVisibleChild != null && y < mDownY) {
                        if (lastVisibleChild.getBottom() + getPaddingBottom() <= getHeight()) {
                            mScrollView.requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
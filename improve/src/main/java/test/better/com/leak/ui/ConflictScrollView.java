package test.better.com.leak.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

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

    private float mDownY;

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
    }
}

package test.better.com.leak.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by zhaoyu on 2017/2/21.
 */

public class MyListView extends ListView {
	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	int lastX, lastY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				// 要求父不要阻止拦截事件
				getParent().requestDisallowInterceptTouchEvent(true);
				lastX = (int) ev.getX();
				lastY = (int) ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				int distanceX = (int) Math.abs(ev.getX() - lastX);
				int distanceY = (int) Math.abs(ev.getY() - lastY);
				int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
				// 父要事件了
				if (distanceX > distanceY && distanceX > slop) {
					getParent().requestDisallowInterceptTouchEvent(false);
				}
				break;
		}

		lastX = (int) ev.getX();
		lastY = (int) ev.getY();

		return super.dispatchTouchEvent(ev);
	}
}

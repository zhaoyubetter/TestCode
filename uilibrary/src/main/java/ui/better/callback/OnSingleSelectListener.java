package ui.better.callback;

import android.view.View;

/**
 * 单选回调接口
 */
public interface OnSingleSelectListener {
	void onSelected(View v, int newPos, int oldPos);
}

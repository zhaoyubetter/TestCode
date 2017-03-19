package ui.better;

import android.support.annotation.IntDef;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ui.better.callback.OnMultiSelectListener;
import ui.better.callback.OnRectangleSelectListener;
import ui.better.callback.OnSingleSelectListener;
import ui.better.callback.Selectable;

/**
 * 选择帮助类
 * 选中回调与数据实现相分离
 * 参考：https://github.com/momodae/widget
 */
public final class SelectHelper {
	public static final int SINGLE = 0;
	public static final int MULTI = 1;
	public static final int RECTANGLE = 2;

	/**
	 * 操作接口
	 */
	public final Selectable selectable;

	private int singleSelectedIndex = -1;// 单选位置
	private List<Integer> multiItems;    // 多选集合
	private int start, end;        // 范围选择的起终点
	private int selectMode;        // 选择模式
	private int maxSelectSize = Integer.MAX_VALUE;        // 多选时，最大选择的个数

	@IntDef(value = {SINGLE, MULTI, RECTANGLE})
	public @interface SelectMode {

	}

	// 回调接口
	private OnSingleSelectListener onSingleSelectListener;
	private OnMultiSelectListener onMultiSelectListener;
	private OnRectangleSelectListener onRectangleSelectListener;

	public SelectHelper(Selectable selectable) {
		this.multiItems = new ArrayList<>();
		this.selectable = selectable;
	}

	public int getSelectMode() {
		return this.selectMode;
	}

	public void setMaxSelectSize(int size) {
		if (size > 0 && this.selectMode == MULTI) {
			this.maxSelectSize = size;
		} else {
			this.maxSelectSize = Integer.MAX_VALUE;
		}
	}

	public void setSelectMode(@SelectMode int mode) {
		this.selectMode = mode;
		switch (selectMode) {
			case MULTI:
				singleSelectedIndex = start = end = -1;
				break;
			case RECTANGLE:
				singleSelectedIndex = -1;
				multiItems.clear();
				maxSelectSize = Integer.MAX_VALUE;
				break;
			case SINGLE:
			default:
				start = end = -1;
				multiItems.clear();
				maxSelectSize = Integer.MAX_VALUE;
				break;
		}

		selectable.setItemSelect(false);
	}

	/**
	 * 设置单选选中位置
	 *
	 * @param index
	 */
	public void setSingleSelectedIndex(int index) {
		if (singleSelectedIndex != index) {
			// 设置当前选中状态
			if (-1 != index) {
				selectable.setItemSelect(index, true);
			}
			// 设置上一次非选中状态
			if (-1 != singleSelectedIndex) {
				selectable.setItemSelect(singleSelectedIndex, false);
			}
			// 记录选中的位置
			singleSelectedIndex = index;
		}
	}

	/**
	 * 设置单选位置，并回调
	 *
	 * @param v
	 * @param index
	 */
	public void setSingleSelectedIndexCallback(View v, int index) {
		setSingleSelectedIndex(index);
		if (null != onSingleSelectListener) {
			onSingleSelectListener.onSelected(v, index, singleSelectedIndex);
		}
	}

	/**
	 * 设置选中位置
	 *
	 * @param v
	 * @param index
	 */
	public void setSelectedIndex(View v, int index) {
		switch (selectMode) {
			case MULTI:
				if (multiItems.contains(index)) {
					multiItems.remove(new Integer(index));
					selectable.setItemSelect(index, false);
				} else {
					if (multiItems.size() < maxSelectSize) {
						multiItems.add(index);
						selectable.setItemSelect(index, true);
					}
				}

				// 回调分离
				if (null != onMultiSelectListener) {
					onMultiSelectListener.onMultiSelected(v, multiItems);
				}
				break;
			case RECTANGLE:
				if (-1 != start && -1 != end) {
					// 清空所有选项
					selectable.setItemSelect(false);
					start = end = -1;
				} else if (-1 == start) {
					start = index;
					selectable.setItemSelect(index, true);
				} else if (-1 == end) {
					end = index;
					// 范围选中
					for (int i = Math.min(start, end); i <= Math.max(start, end); i++) {
						selectable.setItemSelect(i, true);
					}

					// end 选择时，才回调
					if (null != onRectangleSelectListener) {
						onRectangleSelectListener.onRectangleSelected(start, end);
					}
				}
				break;
			case SINGLE:        // 单选
			default:
				setSingleSelectedIndexCallback(v, index);
				break;
		}
	}

	public int getSingleSelectedIndex() {
		return this.singleSelectedIndex;
	}

	public List<Integer> getMultiSelectedIndex() {
		return this.multiItems;
	}

	public int getRectangleStartIndex() {
		return start;
	}

	public int getRectangleEndIndex() {
		return end;
	}

	// 设置监听回调
	public void setOnSingleSelectListener(OnSingleSelectListener onSingleSelectListener) {
		this.onSingleSelectListener = onSingleSelectListener;
	}

	public void setOnMultiSelectListener(OnMultiSelectListener onMultiSelectListener) {
		this.onMultiSelectListener = onMultiSelectListener;
	}

	public void setOnRectangleSelectListener(OnRectangleSelectListener onRectangleSelectListener) {
		this.onRectangleSelectListener = onRectangleSelectListener;
	}


}

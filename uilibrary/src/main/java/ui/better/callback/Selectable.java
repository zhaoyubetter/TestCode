package ui.better.callback;

import java.util.List;

/**
 * 可选中的
 */
public interface Selectable {
	/**
	 * 设置选中状态
	 *
	 * @param select
	 */
	void setItemSelect(boolean select);

	/**
	 * 设置选中状态
	 * @param index
	 * @param select
	 */
	void setItemSelect(int index, boolean select);

	void setOnSingleSelectListener(OnSingleSelectListener listener);

	void setOnMultiSelectListener(OnMultiSelectListener listener);

	void setOnRectangleSelectListener(OnRectangleSelectListener listener);

	int getSingleSelectedIndex();

	List<Integer> getMultiSelectedIndex();

	/**
	 * 范围选择起点位置
	 *
	 * @return
	 */
	int getRectangleStartIndex();

	/**
	 * 范围选择终点位置
	 *
	 * @return
	 */
	int getRectangleEndIndex();


}

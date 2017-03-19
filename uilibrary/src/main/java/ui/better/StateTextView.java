package ui.better;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 多状态textview
 */
public class StateTextView extends TextView {

	private static final String TAG = "StateTextView";

	// 定义5个状态
	private static final int[] STATE_1 = {R.attr.state1};
	private static final int[] STATE_2 = {R.attr.state2};
	private static final int[] STATE_3 = {R.attr.state3};
	private static final int[] STATE_4 = {R.attr.state4};
	private static final int[] STATE_5 = {R.attr.state5};

	public static final int STATE_NONE = 0x00;
	public static final int STATE_FLAG1 = 0x01;
	public static final int STATE_FLAG2 = 0x02;
	public static final int STATE_FLAG3 = 0x03;
	public static final int STATE_FLAG4 = 0x04;
	public static final int STATE_FLAG5 = 0x05;

	public static final int[][] STATUS = {STATE_1, STATE_2, STATE_3, STATE_4, STATE_5};
	private final List<Integer> STATE_LIST;
	private int state;

	@IntDef({STATE_NONE, STATE_FLAG1, STATE_FLAG2, STATE_FLAG3, STATE_FLAG4, STATE_FLAG5})
	public @interface State {

	}

	public StateTextView(Context context) {
		this(context, null);
	}

	public StateTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		STATE_LIST = new ArrayList<>();
		for (int i = 0; i < STATUS.length; i++) {
			STATE_LIST.add(STATUS[i][0]);
		}
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateTextView);
		setStateEnabledInner(a.getInt(R.styleable.StateTextView_stateEnabled, STATE_NONE), true);
		a.recycle();
	}

	public void setStateEnabled(@State int flag, boolean enabled) {
		setStateEnabledInner(flag, enabled);
	}

	public void clearStatus() {
		state = STATE_NONE;
		refreshDrawableState();
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (STATE_NONE != state) {
			// 加入自定义的状态
			mergeDrawableStates(drawableState, STATUS[state - 1]);
		}
		return drawableState;
	}

	private void setStateEnabledInner(int flag, boolean enabled) {
		state = enabled ? flag : STATE_NONE;
		if (isShown()) {
			// 更新drawable状态
			refreshDrawableState();
		}
	}


}

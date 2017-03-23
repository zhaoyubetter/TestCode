package ui.better;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 带删除图标的ClearEditText
 * Created by zhaoyu on 2017/3/23.
 */
public class ClearEditText extends EditText implements TextWatcher {

	private Drawable mClearIconRight;

	public ClearEditText(Context context) {
		this(context, null);
	}

	public ClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
		setClearIconRight(a.getDrawable(R.styleable.ClearEditText_ct_drawableRight));
		a.recycle();
	}

	public void setClearIconRight(Drawable drawable) {
		if (drawable != null) {
			mClearIconRight = drawable;
			mClearIconRight.setBounds(0, 0, mClearIconRight.getIntrinsicWidth(), mClearIconRight.getIntrinsicHeight());
			addTextChangedListener(this);
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// if MOTION UP and range on the clearDrawable, then clear the EditText
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if (getCompoundDrawables()[2] != null) {
				Rect clearRect = new Rect(getWidth() - getTotalPaddingRight(), getPaddingTop(),
						getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
				if (clearRect.contains((int) event.getX(), (int) event.getY())) {
					setText("");
				}
			}
		}

		return super.onTouchEvent(event);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		setClearIconVisible();
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	private void setClearIconVisible() {
		Drawable right = length() > 0 && isFocused() ? mClearIconRight : null ;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
	}

	@Override
	public void afterTextChanged(Editable s) {
		setClearIconVisible();
	}
}

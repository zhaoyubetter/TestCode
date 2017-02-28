package ui.better;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 四周可画分割线的TextView
 * <p>
 * Created by zhaoyu1 on 2017/2/28.
 */
public class DivideTextView extends TextView {

    public static final int NONE = 0x01;     // 0000 0001
    public static final int LEFT = 0x02;      // 0000 0010
    public static final int TOP = 0x04;     // 0000 0100
    public static final int RIGHT = 0x08;     // 0000 1000
    public static final int BOTTOM = 0x10;  // 0000 1010

    @IntDef({NONE, TOP, LEFT, RIGHT, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DivideGravity {
    }

    /**
     * 分割线位置
     */
    private int mDivideGravity;

    private Drawable mDivideDrawable;

    private int mDivideSize;

    private int mDividePadding;

    public DivideTextView(Context context) {
        this(context, null);
    }

    public DivideTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DivideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        // 初始化属性
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DivideTextView, defStyleAttr, 0);
        try {
            setDivideDrawable(a.getDrawable(R.styleable.DivideTextView_dt_drawable));
            setDivideGravityInner(a.getInt(R.styleable.DivideTextView_dt_divideGravity, NONE));
            setDivideSize((int) a.getDimension(R.styleable.DivideTextView_dt_dividerSize, 0f));
            setDividePadding((int) a.getDimension(R.styleable.DivideTextView_dt_dividerPadding, 0f));
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 判断响应的位为1
        drawDivide(canvas, mDivideGravity == (mDivideGravity | LEFT),
                mDivideGravity == (mDivideGravity | TOP),
                mDivideGravity == (mDivideGravity | RIGHT),
                mDivideGravity == (mDivideGravity | BOTTOM));
    }

    private void drawDivide(Canvas canvas, boolean drawLeft, boolean drawTop, boolean drawRight, boolean drawBottom) {
        if (null == mDivideDrawable) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        if (drawLeft) {
            mDivideDrawable.setBounds(0, mDividePadding, mDivideSize, height - mDividePadding);
            mDivideDrawable.draw(canvas);
        }
        if (drawTop) {
            mDivideDrawable.setBounds(mDividePadding, 0, width - mDividePadding, mDivideSize);
            mDivideDrawable.draw(canvas);
        }
        if (drawRight) {
            mDivideDrawable.setBounds(width - mDivideSize, mDividePadding, width + mDivideSize, height - mDividePadding);
            mDivideDrawable.draw(canvas);
        }
        if (drawBottom) {
            mDivideDrawable.setBounds(mDividePadding, height - mDivideSize, width - mDividePadding, height + mDivideSize);
            mDivideDrawable.draw(canvas);
        }
    }

    public void setDividePadding(int mDividePadding) {
        this.mDividePadding = mDividePadding;
    }

    public void setDivideGravity(@DivideGravity int divideGravity) {
        setDivideGravityInner(divideGravity);
    }

    public void setDivideGravityInner(int mDivideGravity) {
        this.mDivideGravity = mDivideGravity;
    }

    public void setDivideDrawable(Drawable mDivideDrawable) {
        this.mDivideDrawable = mDivideDrawable;
    }

    public void setDivideSize(int mDivideSize) {
        this.mDivideSize = mDivideSize;
    }
}

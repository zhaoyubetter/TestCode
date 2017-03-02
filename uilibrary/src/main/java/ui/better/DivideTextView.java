package ui.better;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 四周可画分割线的TextView
 * <p>
 * Created by zhaoyu1 on 2017/2/28.
 */
public class DivideTextView extends TextView {

    private static final boolean DEBUG = false;

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
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public DivideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        // 初始化属性
        // final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DivideTextView, defStyleAttr, R.style.Def_Style_DividerTextView);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DivideTextView, R.attr.Def_Style_Attr_DividerTextView, 0);
        try {
            setDivideDrawable(a.getDrawable(R.styleable.DivideTextView_dt_drawable));
            setDivideGravityInner(a.getInt(R.styleable.DivideTextView_dt_divideGravity, NONE));
            setDivideSize((int) a.getDimension(R.styleable.DivideTextView_dt_dividerSize, 0f));
            setDividePadding((int) a.getDimension(R.styleable.DivideTextView_dt_dividerPadding, 0f));

            // 测试代码：
            if (DEBUG) {
                int padding = (int) a.getDimension(R.styleable.DivideTextView_dt_dividerPadding, 0f);
                int size = (int) a.getDimension(R.styleable.DivideTextView_dt_dividerSize, 0f);

                Log.e("better", "dividerPadding: " + padding);
                Log.e("better", "dividerSize: " + size);
                Log.e("better", "Drawable: " + a.getColor(R.styleable.DivideTextView_dt_drawable, 0));
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }

        /* 第二种获取自定义属性的方式：
        final int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            if (i == R.styleable.DivideTextView_dt_drawable) {
            } else if (i == R.styleable.DivideTextView_dt_divideGravity) {
                setDivideDrawable(a.getDrawable(i));
            } else if (i == R.styleable.DivideTextView_dt_dividerSize) {
                setDivideGravityInner(a.getInt(i, NONE));
            } else if (i == R.styleable.DivideTextView_dt_dividerPadding) {
                setDividePadding((int) a.getDimension(i, 0f));
            }
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 判断响应的位是否为1
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
            mDivideDrawable.setBounds(width - mDivideSize, mDividePadding, width, height - mDividePadding);
            mDivideDrawable.draw(canvas);
        }
        if (drawBottom) {
            mDivideDrawable.setBounds(mDividePadding, height - mDivideSize, width - mDividePadding, height);
            mDivideDrawable.draw(canvas);
        }
    }

    public void setDividePadding(int mDividePadding) {
        this.mDividePadding = mDividePadding;
        invalidate();
    }

    public void setDivideGravity(@DivideGravity int divideGravity) {
        setDivideGravityInner(divideGravity);
    }

    public void setDivideGravityInner(int mDivideGravity) {
        this.mDivideGravity = mDivideGravity;
        invalidate();
    }

    public void setDivideDrawable(Drawable mDivideDrawable) {
        this.mDivideDrawable = mDivideDrawable;
        invalidate();
    }

    public void setDivideSize(int mDivideSize) {
        this.mDivideSize = mDivideSize;
        invalidate();
    }
}

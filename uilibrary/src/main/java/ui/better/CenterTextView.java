package ui.better;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 用于CompoundDrawable与文字同时居中,以及针对CompoundDrawable的尺寸限定
 * 不能做其他用途
 * Created by zhaoyu1 on 2017/3/1.
 */
public class CenterTextView extends DivideTextView {

    //drawableMode Drawable的 设定drawable展示位置模式
    public static final int DRAWABLE_LEFT = 0x00;
    public static final int DRAWABLE_TOP = 0x01;
    public static final int DRAWABLE_RIGHT = 0x02;
    public static final int DRAWABLE_BOTTOM = 0x03;
    public static final int DRAWABLE_ALL = 0x04;

    //sizeMode Drawable尺寸大小控制
    public static final int NONE = 0x00;
    public static final int LEFT = 0x01;
    public static final int TOP = 0x02;
    public static final int RIGHT = 0x04;
    public static final int BOTTOM = 0x08;
    public static final int ALL = 0xf;

    private int mSizeMode;

    private int mDrawableMode;

    private int mDrawableWidth;
    private int mDrawableHeight;

    private CharSequence mText;
    private TextPaint mTextPaint;
    private Drawable[] mCompoundDrawables;

    public CenterTextView(Context context) {
        this(context, null);
    }

    public CenterTextView(Context context, AttributeSet attrs) {
        this(context, attrs, com.android.internal.R.attr.textViewStyle);
    }

    public CenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.CenterTextView);
            setDrawableWidth((int) a.getDimension(R.styleable.CenterTextView_cv_drawableWidth, -1));
            setDrawableHeight((int) a.getDimension(R.styleable.CenterTextView_cv_drawableHeight, -1));
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final TextPaint paint = getPaint();
        final CharSequence text = this.mText;

        // 文字宽高
        int textWidth = 0, textHeight = 0;
        if (!TextUtils.isEmpty(text)) {
            textWidth = Math.round(paint.measureText(text.toString()) + 0.5f);
            Rect textRect = new Rect();
            paint.getTextBounds(text.toString(), 0, text.length(), textRect);
            textHeight = textRect.height();
        }

        // 初始化控件宽
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        Drawable[] compoundDrawables = this.mCompoundDrawables;
        if (compoundDrawables != null && (null != compoundDrawables[0] || null != compoundDrawables[2])) {
            width += getCompoundDrawablePadding();
            if (null != compoundDrawables[0]) {
                width += compoundDrawables[0].getIntrinsicWidth();
            }
            if (null != compoundDrawables[2]) {
                width += compoundDrawables[2].getIntrinsicWidth();
            }
        }

        // 高
        int height = getPaddingBottom() + getPaddingTop() + textHeight;
        if (compoundDrawables != null && (null != compoundDrawables[1] || null != compoundDrawables[3])) {
            height += getCompoundDrawablePadding();
            if (null != compoundDrawables[1]) {
                height += compoundDrawables[1].getIntrinsicHeight();
            }
            if (null != compoundDrawables[3]) {
                height += compoundDrawables[3].getIntrinsicHeight();
            }
        }

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable[] drawables = this.mCompoundDrawables;
        int drawablePadding = getCompoundDrawablePadding();
        if (DRAWABLE_ALL != mDrawableMode) {
            draw(0, drawables, mDrawableMode == DRAWABLE_LEFT || mDrawableMode == DRAWABLE_ALL, canvas, drawablePadding,
                    getDrawableWidth(drawables[0], mSizeMode == (mSizeMode | LEFT)),
                    getDrawableHeight(drawables[0], mSizeMode == (mSizeMode | LEFT)));
        }
    }

    /**
     * 绘制指定方向drawable对象
     *
     * @param index
     * @param drawables
     * @param mode
     * @param drawablePadding
     */
    private void draw(int index, Drawable[] drawables, boolean mode, Canvas canvas, int drawablePadding, int drawableWidth, int drawableHeight) {
        if (drawables == null || null == drawables[index]) return;
        int width = getWidth();
        int height = getHeight();
        float textWidth = 0, textHeight = 0;
        //计算出字体宽高,如果文字无,那么设置图片居中
        if (!TextUtils.isEmpty(mText)) {
            String text = this.mText.toString();
            TextPaint paint = getPaint();
            textWidth = paint.measureText(text);//文字宽
            Rect textRect = new Rect();
            paint.getTextBounds(text, 0, text.length(), textRect);
            textHeight = textRect.height();//文字高
        }
        Drawable drawable = drawables[index];
        //绘制drawable
        drawCompoundDrawable(index, mode, canvas, drawablePadding, drawableWidth, drawableHeight, drawable, width, height, textWidth, textHeight);
    }

    /**
     * 绘制drawable对象
     *
     * @param index
     * @param mode
     * @param canvas
     * @param drawablePadding
     * @param drawableWidth
     * @param drawableHeight
     * @param drawable
     * @param width
     * @param height
     * @param textWidth
     * @param textHeight
     */
    private void drawCompoundDrawable(int index, boolean mode, Canvas canvas, int drawablePadding, int drawableWidth, int drawableHeight, Drawable drawable, int width, int height, float textWidth, float textHeight) {
        //绘drawable对象
        int centerX = (int) ((width - drawableWidth - drawablePadding - textWidth) / 2);
        int centerY = (int) ((height - drawableHeight - drawablePadding - textHeight) / 2);
        //如果应用居中模式,则设置居中
        if (mode) {
            switch (index) {
                case 0:     // 左
                    centerY = (height - drawableHeight) / 2;
                    drawable.setBounds(centerX, centerY, centerX + drawableWidth, centerY + drawableHeight);//左边
                    break;
                case 1:     // 上
                    centerX = (width - drawableWidth) / 2;
                    drawable.setBounds(centerX, centerY, centerX + drawableWidth, centerY + drawableHeight);//上边
                    break;
                case 2:
                    centerY = (height - drawableHeight) / 2;
                    drawable.setBounds(centerX + drawablePadding + (int) textWidth, centerY, centerX + drawablePadding + (int) textWidth + drawableWidth, centerY + drawableHeight);//右边
                    break;
                case 3:
                    centerX = (width - drawableWidth) / 2;
                    drawable.setBounds(centerX, centerY + drawablePadding + (int) textHeight, centerX + drawableWidth, centerY + drawablePadding + (int) textHeight + drawableHeight);//下边
                    break;
            }
        } else {
            //应用常规模式
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            switch (index) {
                case 0:
                    centerY = (height - drawableHeight) / 2;
                    drawable.setBounds(paddingLeft, centerY, paddingLeft + drawableWidth, centerY + drawableHeight);//左边
                    break;
                case 1:
                    centerX = (width - drawableWidth) / 2;
                    drawable.setBounds(centerX, paddingTop, centerX + drawableWidth, paddingTop + drawableHeight);//上边
                    break;
                case 2:
                    centerY = (height - drawableHeight) / 2;
                    drawable.setBounds(width - paddingRight - drawableWidth, centerY, width - paddingRight, centerY + drawableHeight);//右边
                    break;
                case 3:
                    centerX = (width - drawableWidth) / 2;
                    drawable.setBounds(centerX, height - paddingBottom - drawableHeight, centerX + drawableWidth, height - paddingBottom);//底边
                    break;
            }
        }
        drawable.draw(canvas);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(null, type);
        if (!TextUtils.isEmpty(text)) {
            this.mText = text;
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        if (mCompoundDrawables == null) {
            mCompoundDrawables = new Drawable[4];
        }

        mCompoundDrawables[0] = left;
        mCompoundDrawables[1] = top;
        mCompoundDrawables[2] = right;
        mCompoundDrawables[3] = bottom;
        invalidate();
    }

    /**
     * 根据模式获得drawable对象的宽度
     *
     * @param drawable
     * @param isMode
     * @return
     */
    private int getDrawableWidth(Drawable drawable, boolean isMode) {
        if (null == drawable) return -1;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        return isMode ? mDrawableWidth : intrinsicWidth;
    }

    private int getDrawableHeight(Drawable drawable, boolean isMode) {
        if (null == drawable) return -1;
        int intrinsicHeight = drawable.getIntrinsicHeight();
        return isMode ? mDrawableHeight : intrinsicHeight;
    }


    @NonNull
    @Override
    public Drawable[] getCompoundDrawables() {
        return mCompoundDrawables;
    }

    @Override
    public CharSequence getText() {
        return this.mText;
    }

    public void setDrawableHeight(int mDrawableHeight) {
        this.mDrawableHeight = mDrawableHeight;
        invalidate();
    }

    public void setDrawableWidth(int mDrawableWidth) {
        this.mDrawableWidth = mDrawableWidth;
        invalidate();
    }
}

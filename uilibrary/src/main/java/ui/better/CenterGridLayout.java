package ui.better;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 条目居中的Grid控件，注意：条目不能复用
 * width: match_parent
 * height:wrap_content
 */
public class CenterGridLayout extends ViewGroup {

    public static final int AUTO_HEIGHT = -1;
    public static final int ITEM_WIDTH = 0x00;          //  宽度为主
    public static final int HORIZONTAL_PADDING = 0x01;  // 横向边框为主

    private int mColCount;
    private int mItemWidth;
    private int mItemHeight;
    private int mItemHorizontalPadding;
    private int mItemVerticalPadding;


    /**
     * 条目宽度模式
     */
    private int mItemSizeMode = ITEM_WIDTH;

    public CenterGridLayout(Context context) {
        this(context, null);
    }

    public CenterGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.CenterGridLayout);
            setColCount(a.getInt(R.styleable.CenterGridLayout_cl_colCount, 0));
            setItemWidth((int) a.getDimension(R.styleable.CenterGridLayout_cl_itemWidth, 0));
            setItemHeight(a.getLayoutDimension(R.styleable.CenterGridLayout_cl_itemHeight, AUTO_HEIGHT));
            setItemHorizontalPadding((int) a.getDimension(R.styleable.CenterGridLayout_cl_horizontalPadding, 0));
            setItemVerticalPadding((int) a.getDimension(R.styleable.CenterGridLayout_cl_verticalPadding, 0));
            setItemSizeMode(a.getInt(R.styleable.CenterGridLayout_cl_itemSizeMode, ITEM_WIDTH));
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    /**
     * 这里认为该控件默认的 width 是精确的
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int childCount = getChildCount();

        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();

        int childWidth = 0;
        int childHeight = mItemHeight;

        int col = mColCount;

        if (col > 0) {
            if (mItemWidth > 0) {
                switch (mItemSizeMode) {
                    case ITEM_WIDTH:        // 宽度优先
                        if (mItemWidth * col > width) {      // 超过父了，要缩小 mItemWidth
                            mItemWidth = (width - (mItemHorizontalPadding * (col + 1))) / col;
                        }
                        mItemHorizontalPadding = (width - (col * mItemWidth)) / (col + 1);
                        break;
                    case HORIZONTAL_PADDING:// padding优先
                        mItemWidth = (width - mItemHorizontalPadding * (col + 1)) / col;
                        break;
                }
                childWidth = mItemWidth;
            } else {
                childWidth = (width - mItemHorizontalPadding * (col + 1)) / col;
            }
        } else {
            childWidth = mItemWidth;
            switch (mItemSizeMode) {
                case ITEM_WIDTH:     // 宽度优先
                    col = width / childWidth;
                    if (childCount > 0 && childCount < col) {    // 特殊情况判断
                        col = childCount;
                    }
                    mItemHorizontalPadding = (width - (col * mItemWidth)) / (col + 1);
                    break;
                case HORIZONTAL_PADDING:
                    col = width / (childWidth + mItemHorizontalPadding);
                    if ((col * childWidth) + (col + 1) * mItemHorizontalPadding > width) {
                        col--;
                    }
                    mItemWidth = (width - mItemHorizontalPadding * (col + 1)) / col;
                    childWidth = mItemWidth;
                    break;
            }
        }

        mColCount = col;

        // 多少行
        int rowCount = childCount % col == 0 ? childCount / col : childCount / col + 1;
        int cellWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int cellHeightSpec = heightMeasureSpec;

        // cell 自适应高度
        if (AUTO_HEIGHT != mItemHeight) {
            childHeight = mItemHeight;
            cellHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        }

        for (int i = 0; i < childCount; i++) {
            final View view = getChildAt(i);
            if (view.getVisibility() != View.GONE) {
                view.measure(cellWidthSpec, cellHeightSpec);
                if (mItemHeight < 0) {
                    mItemHeight = view.getMeasuredHeight();
                    childHeight = mItemHeight;
                }
            }
        }

        // 总高度
        int totalHeight = 0;
        if (childCount > 0) {
            totalHeight = getPaddingBottom() + getPaddingTop() + (childHeight * rowCount) + (rowCount + 1) * mItemVerticalPadding;
        }

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(totalHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            int left = getPaddingLeft() + mItemHorizontalPadding;
            int top = getPaddingTop() + mItemVerticalPadding;
            int index = 0;
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                int itemWidth = child.getMeasuredWidth();
                int itemHeight = child.getMeasuredHeight();
                child.layout(left, top, left + itemWidth, top + itemHeight);

                if (index >= (mColCount - 1)) {     // 换行
                    index = 0;
                    left = getPaddingLeft() + mItemHorizontalPadding;
                    top += (itemHeight + mItemVerticalPadding);    // 换行
                } else {
                    index++;
                    left += (itemWidth + mItemHorizontalPadding);  // 换列
                }
            }
        }
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        child.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void setColCount(int mRowCount) {
        this.mColCount = mRowCount;
        requestLayout();
    }

    public void setItemWidth(int mItemWidth) {
        this.mItemWidth = mItemWidth;
        requestLayout();
    }

    public void setItemHeight(int mItemHeight) {
        this.mItemHeight = mItemHeight;
        requestLayout();
    }

    public void setItemHorizontalPadding(int mItemHorizontalPadding) {
        this.mItemHorizontalPadding = mItemHorizontalPadding;
        requestLayout();
    }

    public void setItemVerticalPadding(int mItemVerticalPadding) {
        this.mItemVerticalPadding = mItemVerticalPadding;
        requestLayout();
    }

    public void setItemSizeMode(int mItemSizeMode) {
        this.mItemSizeMode = mItemSizeMode;
        requestLayout();
    }
}

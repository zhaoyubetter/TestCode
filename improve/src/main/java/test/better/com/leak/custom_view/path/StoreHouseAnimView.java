package test.better.com.leak.custom_view.path;

import android.content.Context;
import android.util.AttributeSet;

public class StoreHouseAnimView extends PathAnimView {

    public StoreHouseAnimView(Context context) {
        this(context, null);
    }

    public StoreHouseAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoreHouseAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * GET SET FUNC
     **/
    public long getPathMaxLength() {
        return ((StoreHouseAnimHelper) mPathAnimHelper).getPathMaxLength();
    }

    /**
     * 设置残影最大长度
     *
     * @param pathMaxLength
     * @return
     */
    public StoreHouseAnimView setPathMaxLength(long pathMaxLength) {
        ((StoreHouseAnimHelper) mPathAnimHelper).setPathMaxLength(pathMaxLength);
        return this;
    }

    @Override
    protected PathAnimHelper getInitAnimHeper() {
        return new StoreHouseAnimHelper(this, mSourcePath, mAnimPath);
    }
}
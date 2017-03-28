package test.better.com.leak.custom_view.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 参考：http://blog.csdn.net/zxt0601/article/details/53040506
 * 一个做路径动画的View
 * 利用源Path绘制“底”
 * 利用动画Path 绘制 填充动画
 * 通过mPathAnimHelper 对SourcePath做动画：
 * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画
 */
public class PathAnimView extends View {

	protected Path mSourcePath;//需要做动画的源Path
	protected Path mAnimPath;//用于绘制动画的Path
	protected Paint mPaint;
	protected int mColorBg = Color.GRAY;//背景色
	protected int mColorFg = Color.WHITE;//前景色 填充色

	protected PathAnimHelper mPathAnimHelper;

	protected int mPaddingLeft, mPaddingTop;

	public PathAnimView(Context context) {
		this(context, null);
	}

	public PathAnimView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	protected void init() {
		//Paint
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);

		//动画路径只要初始化即可
		mAnimPath = new Path();
		//初始化动画帮助类
		initAnimHelper();
	}

	public PathAnimView setFgColor(int color) {
		this.mColorFg = color;
		return this;
	}

	public PathAnimView setBgColor(int color) {
		this.mColorBg = color;
		return this;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mPaddingLeft = getPaddingLeft();
		mPaddingTop = getPaddingTop();
	}

	public void setPathAnimHelper(PathAnimHelper helper) {
		this.mPathAnimHelper = helper;
	}

	/**
	 * 这个方法可能会经常用到，用于设置源Path
	 *
	 * @param sourcePath
	 * @return
	 */
	public PathAnimView setSourcePath(Path sourcePath) {
		mSourcePath = sourcePath;
		initAnimHelper();
		return this;
	}


	/**
	 * 初始化动画帮助类
	 */
	protected void initAnimHelper() {
		mPathAnimHelper = getInitAnimHeper();
	}

	/**
	 * 子类可通过重写这个方法，返回自定义的AnimHelper
	 *
	 * @return
	 */
	protected PathAnimHelper getInitAnimHeper() {
		return new PathAnimHelper(this, mSourcePath, mAnimPath);
	}

	/**
	 * draw FUNC
	 **/
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();

		//平移
		canvas.translate(mPaddingLeft, mPaddingTop);

		//先绘制底，
		mPaint.setColor(mColorBg);
		canvas.drawPath(mSourcePath, mPaint);

		//再绘制前景，mAnimPath不断变化，不断重绘View的话，就会有动画效果。
		mPaint.setColor(mColorFg);
		canvas.drawPath(mAnimPath, mPaint);

		canvas.restore();
	}

	/**
	 * 设置动画 循环
	 */
	public PathAnimView setAnimInfinite(boolean infinite) {
		mPathAnimHelper.setInfinite(infinite);
		return this;
	}

	/**
	 * 设置动画 总时长
	 */
	public PathAnimView setAnimTime(long animTime) {
		mPathAnimHelper.setAnimTime(animTime);
		return this;
	}

	/**
	 * 执行循环动画
	 */
	public void startAnim() {
		mPathAnimHelper.startAnim();
	}

	/**
	 * 停止动画
	 */
	public void stopAnim() {
		mPathAnimHelper.stopAnim();
	}

	/**
	 * 清除并停止动画
	 */
	public void clearAnim() {
		stopAnim();
		mAnimPath.reset();
		mAnimPath.lineTo(0, 0);
		invalidate();
	}

	public Path getSourcePath() {
		return mSourcePath;
	}

	public Path getAnimPath() {
		return mAnimPath;
	}
}

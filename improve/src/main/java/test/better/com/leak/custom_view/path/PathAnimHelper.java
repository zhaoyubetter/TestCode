package test.better.com.leak.custom_view.path;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.content.ContentValues.TAG;

/**
 * PathAnimView的Path动画的工具类
 */
public class PathAnimHelper {

	protected static final long mDefaultAnimTime = 1500;//默认动画总时间

	protected View mView;//执行动画的View
	protected Path mSourcePath;//源Path
	protected Path mAnimPath;//用于绘制动画的Path
	protected long mAnimTime;//动画一共的时间
	protected boolean mIsInfinite;//是否无限循环

	protected ValueAnimator mAnimator;//动画对象

	public PathAnimHelper(View animView, Path sourcePath, Path animPath) {
		this(animView, sourcePath, animPath, mDefaultAnimTime, true);
	}

	public PathAnimHelper(View animView, Path sourcePath, Path animPath, long animTime, boolean isInfinite) {
		if (animView == null || sourcePath == null || animPath == null) {
			Log.e(TAG, "PathAnimHelper init error: view 、sourcePath、animPath can not be null");
			return;
		}
		mView = animView;
		mSourcePath = sourcePath;
		mAnimPath = animPath;
		mAnimTime = animTime;
		mIsInfinite = isInfinite;
	}

	public void stopAnim() {
		if (null != mAnimator && mAnimator.isRunning()) {
			mAnimator.end();
		}
	}

	public void startAnim() {
		startAnim(mView, mSourcePath, mAnimPath, mAnimTime, mIsInfinite);
	}

	/**
	 * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画
	 * 自定义动画的总时间
	 * 和是否循环
	 *
	 * @param view           需要做动画的自定义View
	 * @param sourcePath     源Path
	 * @param animPath       自定义View用这个Path做动画
	 * @param totalDuaration 动画一共的时间
	 * @param isInfinite     是否无限循环
	 */
	protected void startAnim(View view, Path sourcePath, Path animPath, long totalDuaration, boolean isInfinite) {
		if (view == null || sourcePath == null || animPath == null) {
			return;
		}
		PathMeasure pathMeasure = new PathMeasure();
		//先重置一下需要显示动画的path
		animPath.reset();
		animPath.lineTo(0, 0);
		pathMeasure.setPath(sourcePath, false);
		//这里仅仅是为了 计算一下每一段的duration
		int count = 0;
		while (pathMeasure.getLength() != 0) {
			pathMeasure.nextContour();    //跳转到下一个段
			count++;
		}
		//经过上面这段计算duration代码的折腾 需要重新初始化pathMeasure
		pathMeasure.setPath(sourcePath, false);
		loopAnim(view, sourcePath, animPath, totalDuaration, pathMeasure, totalDuaration / count, isInfinite);
	}

	/**
	 * 循环取出每一段path ，并执行动画
	 *
	 * @param animPath    自定义View用这个Path做动画
	 * @param pathMeasure 用于测量的PathMeasure
	 */
	protected void loopAnim(final View view, final Path sourcePath, final Path animPath,
							final long totalDuaration, final PathMeasure pathMeasure,
							final long duration, final boolean isInfinite) {
		stopAnim();

		mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
		mAnimator.setInterpolator(new LinearInterpolator());
		mAnimator.setDuration(duration);
		mAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//增加一个callback 便于子类重写搞事情
				onPathAnimCallback(view, sourcePath, animPath, pathMeasure, animation);
				//通知View刷新自己
				view.invalidate();
			}
		});

		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationRepeat(Animator animation) {
				//每段path走完后，要补一下 某些情况会出现 animPath不满的情况
				pathMeasure.getSegment(0, pathMeasure.getLength(), animPath, true);

				//绘制完一条Path之后，再绘制下一条
				pathMeasure.nextContour();
				//长度为0 说明一次循环结束
				if (pathMeasure.getLength() == 0) {
					if (isInfinite) {//如果需要循环动画
						animPath.reset();
						animPath.lineTo(0, 0);
						pathMeasure.setPath(sourcePath, false);
					} else {//不需要就停止（因为repeat是无限 需要手动停止）
						animation.end();
					}
				}
			}
		});
		mAnimator.start();
	}

	/**
	 * 用于子类继承搞事情，对animPath进行再次操作的函数
	 *
	 * @param view
	 * @param sourcePath
	 * @param animPath
	 * @param pathMeasure
	 */
	public void onPathAnimCallback(View view, Path sourcePath, Path animPath, PathMeasure pathMeasure, ValueAnimator animation) {
		float value = (float) animation.getAnimatedValue();
		// 不断获取轮廓，来实现动画
		pathMeasure.getSegment(0, pathMeasure.getLength() * value, animPath, true);
	}

	public void setAnimTime(long animTime) {
		this.mAnimTime = animTime;
	}

	public void setInfinite(boolean infinite) {
		this.mIsInfinite = infinite;
	}
}

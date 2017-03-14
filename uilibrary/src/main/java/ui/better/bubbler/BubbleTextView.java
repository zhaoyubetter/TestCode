package ui.better.bubbler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * https://github.com/lguipeng
 */
public class BubbleTextView extends TextView {

	private Paint mPaint;
	private Path mPath;
	// 画笔描边默认宽度
	private static final float DEFAULT_STROKE_WIDTH = 10;

	// 默认圆角半径
	private static final float DEFAULT_RADIUS = 50;


	public BubbleTextView(Context context) {
		this(context, null);
	}

	public BubbleTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.textViewStyle);
	}

	public BubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
		mPath = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawRect(canvas);
	}

	private void drawRect(Canvas canvas) {

		RectF rectF = null;
		// 圆弧起点
		mPath.moveTo(100 + DEFAULT_RADIUS, 100);
		mPath.lineTo(500 - DEFAULT_RADIUS, 100);

		// 右上角圆角 (从 rectF 的中间开始画, path 为路径所以会连起来)
		rectF = new RectF(500 - DEFAULT_RADIUS, 100, 500, (100 + DEFAULT_RADIUS));
		mPath.arcTo(rectF, 270, 90);

		// 右下圆角
		mPath.lineTo(500, 300 - DEFAULT_RADIUS);
		rectF = new RectF(500 - DEFAULT_RADIUS, 300 - DEFAULT_RADIUS, 500, 300);
		mPath.arcTo(rectF, 0, 90);

		// 左下圆角
		mPath.lineTo(100 + DEFAULT_RADIUS, 300);
		rectF = new RectF(100, 300 - DEFAULT_RADIUS, 100 + DEFAULT_RADIUS, 300);
		mPath.arcTo(rectF, 90, 90);


		// 小三角形,
		mPath.lineTo(100, 100 + DEFAULT_RADIUS + 50);
		mPath.lineTo(100 - 50, 100 + DEFAULT_RADIUS + 25);
		mPath.lineTo(100, 100 + DEFAULT_RADIUS);

		// 左上圆角
		rectF = new RectF(100, 100, 100 + DEFAULT_RADIUS, 100 + DEFAULT_RADIUS);
		mPath.arcTo(rectF, 180, 90);

		mPath.close();

		canvas.drawPath(mPath, mPaint);
	}
}

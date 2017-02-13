package test.better.com.leak.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

/**
 * Created by zhaoyu on 2017/2/10.
 */

public final class ImageUtils {

	/**
	 * 压缩图片
	 *
	 * @param targetWidth 目标宽
	 * @return
	 */
	public static Bitmap compressBitmap(Context context, @DrawableRes int resId, int targetWidth,
										int targetHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeResource(context.getResources(), resId, options);        // 分析资源信息

		int sourceWidth = options.outWidth;            // 原始图片宽高
		int sourceHeight = options.outHeight;

		int sampleSize = calculateInSampleSize(sourceWidth, sourceHeight, targetWidth, targetHeight);

		options.inSampleSize = sampleSize;
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(context.getResources(), resId, options);
	}


	/**
	 * 计算压缩比例
	 *
	 * @param sWidth  原始宽高
	 * @param sHeight
	 * @param tWidth  目标宽高
	 * @param tHeight
	 * @return
	 */
	private static int calculateInSampleSize(int sWidth, int sHeight, int tWidth, int tHeight) {
		int sampleSize = 1;
		if (sWidth > tWidth || sHeight > tHeight) {
			int ratioW = Math.round(sWidth * 1.0f / tWidth);
			int ratioH = Math.round(sHeight * 1.0f / tHeight);

			sampleSize = ratioW > ratioH ? ratioH : ratioW;        // 返回较小者
		}
		return sampleSize;
	}

}

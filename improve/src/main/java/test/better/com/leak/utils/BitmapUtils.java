package test.better.com.leak.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * 参考：http://blog.csdn.net/u010687392/article/details/50721437
 * Created by zhaoyu on 2017/2/10.
 */

public final class BitmapUtils {
    /*
     BitmapFactory.Options 说明：
        inBitmap——在解析Bitmap时重用该Bitmap，不过必须等大的Bitmap而且inMutable须为true
        inMutable——配置Bitmap是否可以更改，比如：在Bitmap上隔几个像素加一条线段
        inJustDecodeBounds——为true仅返回Bitmap的宽高等属性
        inSampleSize——须>=1,表示Bitmap的压缩比例，如：inSampleSize=4，将返回一个是原始图的1/16大小的Bitmap
        inPreferredConfig——Bitmap.Config.ARGB_8888等
        inDither——是否抖动，默认为false
        inPremultiplied——默认为true，一般不改变它的值
        inDensity——Bitmap的像素密度
        inTargetDensity——Bitmap最终的像素密度
        inScreenDensity——当前屏幕的像素密度
        inScaled——是否支持缩放，默认为true，当设置了这个，Bitmap将会以inTargetDensity的值进行缩放
        inPurgeable——当存储Pixel的内存空间在系统内存不足时是否可以被回收
        inInputShareable——inPurgeable为true情况下才生效，是否可以共享一个InputStream
        inPreferQualityOverSpeed——为true则优先保证Bitmap质量其次是解码速度
        outWidth——返回的Bitmap的宽
        outHeight——返回的Bitmap的高
        inTempStorage——解码时的临时空间，建议16*1024
    */

    /*
     优化策略：
        1、BitmapConfig的配置
        2、使用decodeFile、decodeResource、decodeStream进行解析Bitmap时，配置inDensity和inTargetDensity，两者应该相等,值可以等于屏幕像素密度*0.75f
        3、使用inJustDecodeBounds预判断Bitmap的大小及使用inSampleSize进行压缩
        4、对Density>240的设备进行Bitmap的适配（缩放Density）
        5、2.3版本inNativeAlloc的使用
        6、4.4以下版本inPurgeable、inInputShareable的使用
        7、Bitmap的回收
     */

    private static final int DEFAULT_DENSITY = 240;
    private static final float SCALE_FACTOR = 0.75f;
    private static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.RGB_565;


    public final static Bitmap compressBitmap(Context context, InputStream is, int maxWidth, int maxHeight) {
        checkParam(context);
        checkParam(is);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, opt);
        int height = opt.outHeight;
        int width = opt.outWidth;
        int sampleSize = calculateInSampleSize(width, height, maxWidth, maxHeight);
        BitmapFactory.Options options = getBitmapOptions(context);
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeStream(is, null, options);
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
    private final static int calculateInSampleSize(int sWidth, int sHeight, int tWidth, int tHeight) {
        int sampleSize = 1;
        if (sWidth > tWidth || sHeight > tHeight) {
            int ratioW = Math.round(sWidth * 1.0f / tWidth);
            int ratioH = Math.round(sHeight * 1.0f / tHeight);

            sampleSize = ratioW > ratioH ? ratioH : ratioW;        // 返回较小者
        }
        if (sampleSize % 2 != 0) {
            sampleSize -= 1;
        }
        return sampleSize <= 1 ? 1 : sampleSize;
    }

    /**
     * 根据当前设备配置，获取BitmapFactory.Options
     *
     * @param context
     * @return BitmapFactory.Options
     */
    private static BitmapFactory.Options getBitmapOptions(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inPreferredConfig = DEFAULT_BITMAP_CONFIG;      // RGB 565
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            Field field = null;
            try {
                field = BitmapFactory.Options.class.getDeclaredField("inNativeAlloc");
                field.setAccessible(true);
                field.setBoolean(options, true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int displayDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        float displayDensity = context.getResources().getDisplayMetrics().density;
        if (displayDensityDpi > DEFAULT_DENSITY && displayDensity > 1.5f) {
            int density = (int) (displayDensityDpi * SCALE_FACTOR);     // 缩放密度
            options.inDensity = density;
            options.inTargetDensity = density;
        }
        return options;
    }

    /**
     * decodeBitmap:对Bitmap不压缩，但是会根据屏幕的密度合适的进行缩放压缩
     *
     * @return
     */
    public static Bitmap decodeBitmap(Context context, int resId) {
        checkParam(context);
        return BitmapFactory.decodeResource(context.getResources(), resId, getBitmapOptions(context));
    }

    /**
     * decodeBitmap:对Bitmap不压缩，但是会根据屏幕的密度合适的进行缩放压缩
     *
     * @param context
     * @param pathName
     * @return
     */
    public static Bitmap decodeBitmap(Context context, String pathName) {
        checkParam(context);
        return BitmapFactory.decodeFile(pathName, getBitmapOptions(context));
    }

    /**
     * decodeBitmap:对Bitmap不压缩，但是会根据屏幕的密度合适的进行缩放压缩
     *
     * @return
     */
    public static Bitmap decodeBitmap(Context context, InputStream is) {
        checkParam(context);
        checkParam(is);
        return BitmapFactory.decodeStream(is, null, getBitmapOptions(context));
    }

    /**
     * compressBimtap:对Bitmap进行超过最大宽高的压缩，同时也会根据屏幕的密度合适的进行缩放压缩。
     *
     * @param context
     * @param resId
     * @param maxWidth  最大宽
     * @param maxHeight
     * @return
     */
    public static Bitmap compressBitmap(Context context, int resId, int maxWidth, int maxHeight) {
        checkParam(context);
        final TypedValue value = new TypedValue();
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(resId, value);
            return compressBitmap(context, is, maxWidth, maxHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Bitmap compressBitmap(Context context, String pathName, int maxWidth, int maxHeight) {
        checkParam(context);
        InputStream is = null;
        try {
            is = new FileInputStream(pathName);
            return compressBitmap(context, is, maxWidth, maxHeight);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static <T> void checkParam(T param) {
        if (param == null)
            throw new NullPointerException();
    }
}

package groovy.better.com.groovytest.selector.inflater;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import groovy.better.com.groovytest.selector.ViewParams;
import groovy.better.com.groovytest.selector.shape.IShapeCreator;


/**
 * Created by hufeng7 on 2017/1/6.<br/>
 * <b>所有子类必须提供空构造函数</b>
 */
public interface IBackgroundInflater {
    /**
     * 解析属性
     *
     * @param array <b>子类不需要调用{@link TypedArray#recycle()}</b>
     * @param vp    将解析的属性值存储于该参数中。<br/>
     *              <b>此时{@link ViewParams#width}与{@link ViewParams#height}没有实际值</b>
     */
    void onParser(TypedArray array, ViewParams vp, View view);

    /**
     * 绘制自己需要的背景样式
     *
     * @param vp 绘制过程中需要的参数从该类中获取
     */
    void onDraw(Canvas canvas, Paint paint, ViewParams vp, View view);

    /**
     * 返回跟子类相关的ViewParams，各Params中定义子类需要的属性——类似于LayoutParams。
     */
    ViewParams generateViewParams();

    /**
     * 获取将要绘制的shape
     */
    IShapeCreator generateShapeCreator();
}

package groovy.better.com.groovytest.selector.inflater;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import groovy.better.com.groovytest.R;
import groovy.better.com.groovytest.selector.ViewParams;
import groovy.better.com.groovytest.selector.shape.CircleShapeCreator;
import groovy.better.com.groovytest.selector.shape.IShapeCreator;


/**
 * 圆形背景。
 */

public class CircleBackgrounInflater implements IBackgroundInflater {

    public CircleBackgrounInflater() {
    }

    @Override
    public void onParser(TypedArray array, ViewParams vp, View view) {
        SubViewParams svp = (SubViewParams) vp;
        svp.radiusStyle = array.getInt(R.styleable.test_radiusStyleMy, 1);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, final ViewParams vp, View view) {
        Path path = ((CircleShapeCreator) generateShapeCreator()).createPath(vp, new CircleShapeCreator.CircleShapeCreatorCallback() {
            @Override
            public int getRadiusStyle() {
                SubViewParams svp = (SubViewParams) vp;
                return svp.radiusStyle;
            }
        }, view);
        canvas.clipPath(path);
        canvas.drawPath(path, paint);
    }

    @Override
    public IShapeCreator generateShapeCreator() {
        return new CircleShapeCreator();
    }

    @Override
    public ViewParams generateViewParams() {
        return new SubViewParams();
    }

    private class SubViewParams extends ViewParams {
        int radiusStyle;//1表示以宽高的大者为直径，2表示以短者为直径
    }
}

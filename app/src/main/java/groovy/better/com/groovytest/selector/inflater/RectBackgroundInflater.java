package groovy.better.com.groovytest.selector.inflater;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;

import groovy.better.com.groovytest.R;
import groovy.better.com.groovytest.selector.ViewParams;
import groovy.better.com.groovytest.selector.shape.IShapeCreator;
import groovy.better.com.groovytest.selector.shape.RectShapeCreator;


/**
 * 矩形背景。包括圆角矩形，直角矩形
 */
public class RectBackgroundInflater implements IBackgroundInflater {

    public RectBackgroundInflater() {
    }

    @Override
    public void onParser(TypedArray array, ViewParams vp1, View view) {
        SubViewParams vp = (SubViewParams) vp1;
        float cornerRadius = array.getDimensionPixelSize(R.styleable.test_cornerRadiusMy, -1);
        float topLeftRadius = array.getDimensionPixelSize(R.styleable.test_topLeftRadiusMy, 0);
        float topRightRadius = array.getDimensionPixelSize(R.styleable.test_topRightRadiusMy, 0);
        float bottomLeftRadius = array.getDimensionPixelSize(R.styleable.test_bottomLeftRadiusMy, 0);
        float bottomRightRadius = array.getDimensionPixelSize(R.styleable.test_bottomRightRadiusMy, 0);
        vp.tl = getRadius(cornerRadius, topLeftRadius);
        vp.tr = getRadius(cornerRadius, topRightRadius);
        vp.bl = getRadius(cornerRadius, bottomLeftRadius);
        vp.br = getRadius(cornerRadius, bottomRightRadius);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint,final ViewParams vp, View view) {
        Path p = ((RectShapeCreator) generateShapeCreator()).createPath(vp, new RectShapeCreator.RectShapeCreatorCallback() {
            @Override
            public void getRoundRect(PointF[] pointFs) {
                SubViewParams svp = (SubViewParams) vp;
                pointFs[0] = svp.tl;
                pointFs[1] = svp.tr;
                pointFs[2] = svp.bl;
                pointFs[3] = svp.br;
            }
        }, view);
        canvas.clipPath(p);
        canvas.drawPath(p, paint);
    }

    @Override
    public ViewParams generateViewParams() {
        return new SubViewParams();
    }

    @Override
    public IShapeCreator generateShapeCreator() {
        return new RectShapeCreator();
    }

    private PointF getRadius(float cornerRadius, float r) {
        PointF p = new PointF();
        if (cornerRadius == -1) {
            p.x = r;
            p.y = r;
        } else {
            p.x = cornerRadius;
            p.y = cornerRadius;
        }
        return p;
    }

    private class SubViewParams extends ViewParams {
        PointF tl, tr, bl, br;
    }
}

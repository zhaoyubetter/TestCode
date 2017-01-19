package groovy.better.com.groovytest.selector.shape;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;

import groovy.better.com.groovytest.selector.ViewParams;


/**
 * Created by hufeng7 on 2017/1/9
 */

public class RectShapeCreator implements IShapeCreator<RectShapeCreator.RectShapeCreatorCallback> {
    @Override
    public Path createPath(ViewParams vp, RectShapeCreatorCallback callback, View view) {
        PointF[] ps = new PointF[4];
        callback.getRoundRect(ps);
        PointF tl = ps[0];
        PointF tr = ps[1];
        PointF bl = ps[2];
        PointF br = ps[3];
        //左上
        RectF tlr = new RectF(0, 0, tl.x * 2, tl.y * 2);
        //右上
        RectF trr = new RectF(vp.width - tr.x * 2, 0, vp.width, tl.y * 2);
        //右下
        RectF brr = new RectF(vp.width - br.x * 2, vp.height - br.y * 2, vp.width, vp.height);
        //左下
        RectF blr = new RectF(0, vp.height - bl.y * 2, bl.x * 2, vp.height);

        Path p = new Path();
        p.moveTo(tlr.left, tlr.centerY());
        p.arcTo(tlr, 180, 90);

        p.lineTo(trr.centerX(), trr.top);
        p.arcTo(trr, 270, 90);

        p.lineTo(brr.right, brr.centerY());
        p.arcTo(brr, 0, 90);

        p.lineTo(blr.centerX(), blr.bottom);
        p.arcTo(blr, 90, 90);
        p.lineTo(tlr.left, tlr.centerY());
        return p;
    }

    public interface RectShapeCreatorCallback extends IShapeCreator.ShapeCreatorCallback {
        /**
         * 获取矩形四个角度的圆弧半径
         *
         * @param pointFs 大小为4，从0-3为左上，右上，左下，右下四个角度的圆弧半径
         */
        void getRoundRect(PointF[] pointFs);
    }
}

package groovy.better.com.groovytest.selector.shape;

import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;

import groovy.better.com.groovytest.selector.ViewParams;


/**
 * Created by hufeng7 on 2017/1/9
 */

public class CircleShapeCreator implements IShapeCreator<CircleShapeCreator.CircleShapeCreatorCallback> {

    @Override
    public Path createPath(ViewParams vp, CircleShapeCreatorCallback callback, View view) {
        Path path = new Path();
        int radius;
        if (callback.getRadiusStyle() == 1) {
            int max = Math.max(vp.width, vp.height);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = max;
            params.height = max;
            view.setLayoutParams(params);
            radius = max / 2;
        } else {
            int min = Math.min(vp.width, vp.height);
            radius = min / 2;
        }
        path.addCircle(vp.width / 2, vp.height / 2, radius - vp.strokeWidth / 2, Path.Direction.CCW);
        return path;
    }

    public interface CircleShapeCreatorCallback extends IShapeCreator.ShapeCreatorCallback {
        int getRadiusStyle();
    }
}

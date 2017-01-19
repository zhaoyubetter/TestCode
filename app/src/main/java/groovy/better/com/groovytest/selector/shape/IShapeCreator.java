package groovy.better.com.groovytest.selector.shape;

import android.graphics.Path;
import android.view.View;

import groovy.better.com.groovytest.selector.ViewParams;


/**
 * Created by hufeng7 on 2017/1/9
 * 背景图形样式——只负责创建样式，不负责填充
 */
public interface IShapeCreator<T extends IShapeCreator.ShapeCreatorCallback> {
    /**
     * 创建path
     *
     * @param callback：Creator的回调，{@link ShapeCreatorCallback}为空接口
     * @param vp：通用
     */
    Path createPath(ViewParams vp, T callback, View view);

    interface ShapeCreatorCallback {

    }
}

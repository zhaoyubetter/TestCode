package groovy.better.com.groovytest.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import groovy.better.com.groovytest.R;
import groovy.better.com.groovytest.selector.inflater.IBackgroundInflater;


/**
 * Created by hufeng7 on 2017/1/5
 */

public class MyLayoutInflater extends LayoutInflater {
    private LayoutInflater inflater;
    private static final String TAG = MyLayoutInflater.class.getSimpleName();

    public MyLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        inflater = original;
        setFactory2(new MyF(original));//这里设置的f2，不能直接使用setFactory()。如果使用了在AppCompatActivity中是没有效果的。
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        LayoutInflater result = new MyLayoutInflater(inflater, newContext);
        result.setFactory2(new MyF(inflater));
        return result;
    }

    private class MyF implements Factory2 {
        private LayoutInflater inflater;
        private final String[] sClassPrefixList = {
                "android.widget.",
                "android.webkit.",
                "android.app."
        };

        private MyF(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View view = null;
            try {
                if (-1 == name.indexOf('.')) {//TextView等系统控件
                    view = onCreateView(name, attrs);
                } else {//自定义控件，不需要写包名
                    view = createView(name, null, attrs);
                }
                //获取自定义属性，并进行操作
                final View v = view;
                TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.test);
                String string = array.getString(R.styleable.test_backgroundParser);
                if (!TextUtils.isEmpty(string)) {
                    final IBackgroundInflater parser = BackgroundParserFactory.getParser(string);
                    if (parser == null)
                        throw new RuntimeException(String.format("Unable to instance %s", name));
                    ViewParams vp = parser.generateViewParams();
                    //有自定义属性
                    vp.color = array.getColor(R.styleable.test_colorMy, Color.WHITE);
                    vp.style = array.getInt(R.styleable.test_styleMy, 1);//1是stroke
                    vp.strokeWidth = array.getDimensionPixelSize(R.styleable.test_strokeWidthMy, 4);
                    parser.onParser(array, vp, v);
                    v.setTag(R.id.login_activity, vp);
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            showBg(v, parser);
                        }
                    });
                    v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            Log.e(TAG, "xxx---mine");
                            showBg(v, parser);
                        }
                    });
                }
                array.recycle();
            } catch (ClassNotFoundException ignored) {

            }
            return view;
        }

        /**
         * 拼凑成完整的类名，并通过反射完成。
         */
        private View onCreateView(String name, AttributeSet attrs) {
            for (String prefix : sClassPrefixList) {
                try {
                    View view = createView(name, prefix, attrs);
                    if (view != null) {
                        return view;
                    }
                } catch (Exception e) {

                }
            }
            return null;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return onCreateView(name, context, attrs);
        }
    }

    private void showBg(final View v, final IBackgroundInflater parser) {
        final ViewParams vp = (ViewParams) v.getTag(R.id.login_activity);
        vp.width = v.getWidth();
        vp.height = v.getHeight();
        ShapeDrawable sd = new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setColor(vp.color);
                paint.setAntiAlias(true);
                paint.setStrokeWidth(vp.strokeWidth);
                switch (vp.style) {
                    case 1:
                        paint.setStyle(Paint.Style.STROKE);
                        break;
                    default:
                        paint.setStyle(Paint.Style.FILL);
                        break;
                }
                parser.onDraw(canvas, paint, vp, v);
            }
        });
        v.setBackgroundDrawable(sd);
    }
}
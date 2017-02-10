package test.better.com.leak.leak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.better.com.leak.R;

/**
 * 非静态内部类，会默认持有外部类的引用，而且使用了该非静态内部类创建了一个静态示例，
 * 静态实例的生命周期跟应用一样长，这样就造成了 该静态实例，将会持续持有Activity的引用，导入activity不能正常回收了
 * Created by zhaoyu1 on 2017/2/10.
 */
public class LeakActivity2 extends AppCompatActivity {

    private static InnerClass sInnerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_1);

        if (sInnerClass == null) {
            sInnerClass = new InnerClass();
        }
    }

    class InnerClass {

    }

    // ==============================================================
    //  解决方法：
    // 1.内部改成静态内部类，或外部类
    // 2.如果需要，可将外部类改成单例模式形式
    // ==============================================================

}

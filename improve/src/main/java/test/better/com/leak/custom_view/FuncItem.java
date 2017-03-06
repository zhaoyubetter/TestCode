package test.better.com.leak.custom_view;

/**
 * Created by zhaoyu1 on 2017/2/28.
 */
public class FuncItem {
    public String name;
    public String clazzName;

    public FuncItem(String name, String clazzName) {
        this.name = name;
        this.clazzName = clazzName;
    }

    @Override
    public String toString() {
        return name;
    }
}

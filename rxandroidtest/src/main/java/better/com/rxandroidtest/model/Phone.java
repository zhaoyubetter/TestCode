package better.com.rxandroidtest.model;

/**
 * Created by zhaoyu1 on 2017/1/17.
 */
public class Phone {
    public float price;
    public String name;

    public Phone(float price, String name) {
        this.price = price;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}

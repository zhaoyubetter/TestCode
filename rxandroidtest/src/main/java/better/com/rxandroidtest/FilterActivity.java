package better.com.rxandroidtest;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import better.com.rxandroidtest.model.Phone;
import better.com.rxandroidtest.model.User;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoyu1 on 2017/1/18.
 */
public class FilterActivity extends AppCompatActivity {

    static final String TAG = "Rx_better";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        //filter();
        //take();
        //takeUntil();
        //takeUntil2();
        // skip();
        debounce();
    }

    /**
     * 过滤掉了由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，
     * 那么它将发射最后的那个
     */
    private void debounce() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                    SystemClock.sleep(20);
                }
                subscriber.onCompleted();
            }
        }).debounce(400, TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "事件是：" + integer);
            }
        });
    }


    /**
     * 跳过前5个， 类似有：SkipLast
     */
    private void skip() {
        // 跳过前5个
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).skip(5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "" + integer);
            }
        });
    }

    private void takeUntil2() {
        Observable.just(1, 2, 3, 4, 5).takeUntil(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer >= 3;  // 判断 util条件，直到 >= 3，输出 1,2,3
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "" + integer);
            }
        });
    }

    /**
     * 订阅并开始发射原始Observable，同时监视我们提供的第二个Observable。
     * 如果第二个Observable发射了一项数据或者发射了一个终止通知，
     * takeUntil()返回的Observable会停止发射原始Observable并终止
     */
    private void takeUntil() {
        Observable<Long> observableA = Observable.interval(300, TimeUnit.MILLISECONDS);
        Observable<Long> observableB = Observable.interval(1000, TimeUnit.MILLISECONDS);

        observableA.takeUntil(observableB).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG, "" + aLong);
            }
        });
    }

    private void take() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.userName = "better [" + (i + 1) + "]";
            list.add(user);
        }
        // 获取前2个
        Observable.from(list).take(2).subscribe(new Action1<User>() {
            @Override
            public void call(User user) {
                Log.e(TAG, user.userName);
            }
        });
    }

    void filter() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.userName = "better";

        User user2 = new User();
        user2.userName = "zhaoyu";

        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(5088, "IPhone 7"));
        phones.add(new Phone(3022, "MOTO Z"));

        List<Phone> phones2 = new ArrayList<>();
        phones2.add(new Phone(512, "meizu"));
        phones2.add(new Phone(1999, "xiaomi"));

        user.phoneList = phones;
        user2.phoneList = phones2;
        Collections.addAll(users, user, user2);

        // 过滤
        Observable.from(users).filter(new Func1<User, Boolean>() {
            @Override
            public Boolean call(User user) {
                boolean result = false;
                for (Phone phone : user.phoneList) {
                    result = phone.price > 500;
                }
                return result;
            }
        }).subscribe(new Action1<User>() {
            @Override
            public void call(User user) {
                Log.e(TAG, user.userName);
            }
        });
    }
}

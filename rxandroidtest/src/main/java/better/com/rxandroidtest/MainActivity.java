package better.com.rxandroidtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import better.com.rxandroidtest.model.Phone;
import better.com.rxandroidtest.model.User;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Rx_better";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.rx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // rxTest1();
                // rxTest2();
                // rxTest3FlatMap();
                // rxTestSwitchMap();
                // rxTestScan();
                testSource();
            }
        });

        findViewById(R.id.group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                startActivity(intent);
            }
        });
    }

    // 对一个序列的数据应用一个函数，并将这个函数的结果发射出去作为下个数据应用合格函数时的第一个参数使用。
    private void rxTestScan() {
        Observable.just(1, 2, 3, 4, 5).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, integer.toString());
            }
        });
    }

    private void rxTestSwitchMap() {
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

        // 在不同的线程操作，之前的事件将取消，最后一个事件执行
        // switch()和flatMap()很像，除了一点:当源Observable发射一个新的数据项时，
        // 如果旧数据项订阅还未完成，就取消旧订阅数据和停止监视那个数据项产生的Observable,开始监视新的数据项.
        Observable.from(users).switchMap(new Func1<User, Observable<Phone>>() {
            @Override
            public Observable<Phone> call(User user) {
                return Observable.from(user.phoneList).subscribeOn(Schedulers.newThread()); // 新线程生成obserable
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Phone>() {
            @Override
            public void call(Phone phone) {
                Log.e(TAG, phone.toString());
            }
        });
    }


    //
    private void rxTest3FlatMap() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.userName = "better";

        User user2 = new User();
        user2.userName = "zhaoyu";

        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(5088, "IPhone 7"));
        phones.add(new Phone(3022, "MOTO Z"));
        user.phoneList = phones;
        user2.phoneList = phones;
        Collections.addAll(users, user, user2);

        // 从 user 集合中，取出 phone
        Observable.from(users).flatMap(new Func1<User, Observable<Phone>>() {
            @Override
            public Observable<Phone> call(User user) {
                // 转成了 Observable 对象，区别于 map
                return Observable.from(user.phoneList);
            }
        }).subscribe(new Action1<Phone>() {
            @Override
            public void call(Phone phone) {
                Log.e(TAG, phone.toString());
            }
        });
    }

    // 基本使用
    private void rxTest1() {
        // 1. 观察者 Subscriber
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "出错");
            }

            @Override
            public void onNext(Integer s) {
                Log.e(TAG, "Item: " + s);
            }
        };

        // 2.被观察者
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
        observable.subscribe(observer);
    }

    private void rxTest2() {
        // 转换类操作符 map 一对一转化
        Observable.just(1, 2, 3, 4, 5).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return String.valueOf(integer);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "Item: " + s);
            }
        });
    }

    private void testSource() {

        /*
        final Observable<Integer> observableA = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        });

        final Observable<String> observableB = observableA.map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "This is " + String.valueOf(integer);
            }
        });

        Subscriber subscriberOne = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String o) {
                Log.e(TAG, o);
            }
        };

        observableB.subscribe(subscriberOne);
*/
        Subscriber a = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String o) {
                Log.e(TAG, "" + Thread.currentThread().getName());
                Log.e(TAG, "onNext" + o);
            }
        };

        // 线程调度的观察
        final Observable obserbable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.e(TAG, "" + Thread.currentThread().getName());
                subscriber.onNext("Hello RxJava!!");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        obserbable.subscribe(a);

    }
}

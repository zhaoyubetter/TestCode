package better.com.rxandroidtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import better.com.rxandroidtest.model.User;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by zhaoyu1 on 2017/1/18.
 */
public class GroupActivity extends AppCompatActivity {

    static final String TAG = "Rx_better";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // merge();
        //startWith();
        //contact();
        // zip();
        // combineLatest();
        join();
    }

    // join 操作
    private void join() {
        final List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.userName = "better[" + i + "]";
            users.add(user);
        }

        final Observable<User> userSequeue = Observable.interval(1, TimeUnit.SECONDS).map(new Func1<Long, User>() {
            @Override
            public User call(Long aLong) {
                return users.get(aLong.intValue());
            }
        }).take(users.size());

        final Observable<Long> numSequeue = Observable.interval(1, TimeUnit.SECONDS);

        // 基于时间窗口将两个Observables发射的数据结合在一起。
        userSequeue.join(numSequeue, new Func1<User, Observable<Long>>() {
            @Override
            public Observable<Long> call(User user) {
                // 控制源 Observable: userSequeue 发射出来的数据的有效期
                return Observable.timer(1, TimeUnit.SECONDS);
            }
        }, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                // 控制目标 Observable: numSequeue 发射出来的数据的有效期
                return Observable.timer(1, TimeUnit.SECONDS);
            }
        }, new Func2<User, Long, String>() {
            @Override
            public String call(User user, Long aLong) {
                return aLong + "-->" + user.userName;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, s);
            }
        });
    }

    // 用于将两个Observale最近发射的数据已经Func2函数的规则进展组合
    private void combineLatest() {
        final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        final Observable<String> lettersSequene = Observable.interval(300, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                return letters[aLong.intValue()];
            }
        }).take(letters.length);

        final Observable<Long> numberSequeue = Observable.interval(300, TimeUnit.MILLISECONDS).take(letters.length);

        Observable.combineLatest(lettersSequene, numberSequeue, new Func2<String, Long, String>() {
            @Override
            public String call(String s, Long aLong) {
                return s + aLong;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, s);
            }
        });
    }

    // 用来合并两个Observable发射的数据项，根据Func2函数生成一个新的值并发射出去。
    // 当其中一个Observable发送数据结束或者出现异常后，另一个Observable也将停在发射数据
    private void zip() {
        final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        final Observable<String> lettersSequene = Observable.interval(300, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                return letters[aLong.intValue()];
            }
        }).take(letters.length);

        final Observable<Long> numberSequeue = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

        Observable.zip(lettersSequene, numberSequeue, new Func2<String, Long, String>() {
            @Override
            public String call(String s, Long aLong) {
                return s + aLong;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, s);
            }
        });
    }

    // 事件有序
    private void contact() {
        final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};

        final Observable<String> letterSequene = Observable.interval(300, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long position) {     // position : 0,1,2...
                return letters[position.intValue()];
            }
        }).take(letters.length);

        final Observable<Long> numberSequene = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

        // 合并
        Observable.concat(letterSequene, numberSequene).subscribe(new Action1<Serializable>() {
            @Override
            public void call(Serializable serializable) {
                Log.e(TAG, serializable.toString());
            }
        });
    }

    // 用于在源Observable发射的数据前插入数据
    private void startWith() {
        final Observable<Long> just = Observable.just(10000L, 20000L, 30000L);
        Observable.interval(200, TimeUnit.MILLISECONDS).take(5).startWith(just).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.e(TAG, "startWith: " + aLong);
            }
        });
    }

    // 合并2个事件序列,合并后的事件序列无序 ( 合并后发射的数据是无序的）
    private void merge() {
        final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};

        final Observable<String> letterSequene = Observable.interval(300, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long position) {     // position : 0,1,2...
                return letters[position.intValue()];
            }
        }).take(letters.length);

        final Observable<Long> numberSequene = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

        // 合并
        Observable.merge(letterSequene, numberSequene).subscribe(new Action1<Serializable>() {
            @Override
            public void call(Serializable serializable) {
                Log.e(TAG, serializable.toString());
            }
        });
    }
}

package better.basenet.base.request;

/**
 * Created by zhaoyu1 on 2017/3/6.
 */
public interface IRequestCallBack<T> {
    void onSuccess(T t);

    void onFailure(Throwable e);
}

package better.basenet.okhttp;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import better.basenet.base.request.AbsRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp 类
 * Created by zhaoyu1 on 2017/3/7.
 */
public class OkHttpRequest extends AbsRequest {

    private static final OkHttpClient sOkHttpClient;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("media/type");

    private Call mCall;

    static {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);
        sOkHttpClient = builder.build();
    }

    protected OkHttpRequest(Builder builder) {
        super(builder);
    }

    private void realRequest(Request.Builder tBuilder) {
        // 判断此次请求，超时时间是否不同，如果不同，创建 Client
        OkHttpClient tClient = sOkHttpClient;
        if (mTimeOut > 1000 && mTimeOut != DEFAULT_TIME_OUT) {
            final OkHttpClient.Builder builder = sOkHttpClient.newBuilder().connectTimeout(mTimeOut, TimeUnit.MILLISECONDS).readTimeout(mTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(mTimeOut, TimeUnit.MILLISECONDS);
            tClient = builder.build();
        } else {
            tClient = sOkHttpClient;
        }

        // 设置Header
        if (mHeader != null && mHeader.size() > 0) {
            for (Map.Entry<String, String> entry : mHeader.entrySet()) {
                tBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        final Request request = tBuilder.build();       // 创建request
        // 走异步
        mCall = tClient.newCall(request);
        mCall.enqueue(new Callback() {
            boolean isSuccess = false;                       // 是否成功
            Map<String, String> headerMap = null;            // 响应头
            String returnBody = null;                        // 响应体

            @Override
            public void onFailure(Call call, IOException e) {
                if (null != mCallBack) {
                    mCallBack.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != mCallBack) {
                    headerMap = getResponseHeaders(response);
                    if (response.isSuccessful()) {
                        isSuccess = true;
                        returnBody = response.body().string();    // 字符串响应体
                        mCallBack.onSuccess(returnBody);
                    } else {
                        isSuccess = false;
                        returnBody = response.code() + " " + response.message();
                        mCallBack.onFailure(new Exception(returnBody));
                    }
                }
            }
        });
    }

    @Override
    protected void get() {
        Request.Builder tBuilder = new Request.Builder();
        tBuilder.get().url(generateUrl(mUrl, mParams)).tag(mTag);
        realRequest(tBuilder);
    }

    @Override
    protected void post() {
        Request.Builder tBuilder = new Request.Builder();
        tBuilder.url(mUrl).tag(mTag).post(getRequestBody());
        realRequest(tBuilder);
    }

    @Override
    public void cancel() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }

    /**
     * post 请求体, 必须有一个请求体，否则报异常
     *
     * @return
     */
    private RequestBody getRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (null != mParams && mParams.size() > 0) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 封装响应 header
     *
     * @param response
     * @return
     */
    private HashMap getResponseHeaders(Response response) {
        HashMap headerMap = null;
        if (null != response.headers() && response.headers().size() > 0) {
            headerMap = new HashMap<>();
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                headerMap.put(responseHeaders.name(i), responseHeaders.value(i));
            }
        }
        return headerMap;
    }

    public static class Builder extends AbsRequest.Builder {
        @Override
        public AbsRequest build() {
            return new OkHttpRequest(this);
        }
    }
}

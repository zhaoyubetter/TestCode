package better.basenet.base.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 网络请求封装接口,抽象类
 *
 * @author zhaoyu
 * @author hufeng
 * @version 1.0
 * @since 2017/3/6.
 */
public abstract class AbsRequest implements IRequest {

    public static final String CHAR_SET = "UTF-8";

    /**
     * 默认请求超时时间 10s
     */
    public static final long DEFAULT_TIME_OUT = 10 * 1000;

    /**
     * url 地址
     */
    protected String mUrl;

    /**
     * 参数
     */
    protected Map<String, String> mParams;

    /**
     * 请求头信息
     */
    protected Map<String, String> mHeader;

    /**
     * 本地请求超时时间
     */
    protected long mTimeOut;

    /**
     * 请求标记
     */
    protected Object mTag;

    /**
     * 回调
     */
    protected IRequestCallBack mCallBack;

    /**
     * 请求方式
     */
    protected int mReqType;

    protected AbsRequest(Builder builder) {
        this.mUrl = builder.mUrl;
        this.mCallBack = builder.mCallBack;
        this.mTag = builder.mTag;
        this.mTimeOut = builder.mTimeOut;
        this.mReqType = builder.mReqType;
        this.mParams = builder.mParams;
        this.mHeader = builder.mHeader;
    }

    @Override
    public final void request() {
        switch (mReqType) {
            case RequestType.GET:
                get();
                break;
            case RequestType.POST:
                post();
                break;
        }
    }

    /**
     * 执行get方式
     */
    protected abstract void get();

    /**
     * 执行post方式
     */
    protected abstract void post();


    /**
     * 生成请求的url地址
     *
     * @param url
     * @param params
     * @return
     */
    protected String generateUrl(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.size() > 0) {      // GET 请求，拼接url
            if (sb.charAt(sb.length() - 1) != '?') {            // get 请求 有 ?
                sb.append("?");
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    sb.append(URLEncoder.encode(entry.getKey(), CHAR_SET)).append("=").append(URLEncoder.encode(entry.getValue(), CHAR_SET)).append("&");
                } catch (UnsupportedEncodingException e) {
                    // NOT_HAPPEND
                }
            }
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    public static abstract class Builder {

        /**
         * url 地址
         */
        private String mUrl;

        /**
         * 参数
         */
        private Map<String, String> mParams;

        /**
         * 请求头信息
         */
        private Map<String, String> mHeader;

        /**
         * 本地请求超时时间
         */
        private long mTimeOut;

        /**
         * 请求标记
         */
        private Object mTag;

        /**
         * 回调
         */
        private IRequestCallBack mCallBack;

        /**
         * 请求方式
         */
        private int mReqType;

        public Builder() {
        }

        public Builder url(String url) {
            this.mUrl = url;
            return this;
        }

        public Builder body(Map<String, String> params) {
            this.mParams = params;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.mHeader = headers;
            return this;
        }

        public Builder timeout(long time) {
            this.mTimeOut = time;
            return this;
        }

        public Builder tag(Object tag) {
            this.mTag = tag;
            return this;
        }

        public Builder callback(IRequestCallBack callBack) {
            this.mCallBack = callBack;
            return this;
        }

        /**
         * @param reqType {@link IRequest.RequestType}中常量
         * @return
         */
        public Builder type(int reqType) {
            this.mReqType = reqType;
            return this;
        }

        public abstract AbsRequest build();
    }
}

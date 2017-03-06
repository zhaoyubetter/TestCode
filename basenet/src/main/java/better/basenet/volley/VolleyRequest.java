package better.basenet.volley;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import better.basenet.base.request.AbsRequest;

/**
 * Volley请求封装类
 *
 * @author zhaoyu
 * @author hufeng
 * @version 1.0
 * @since 2017/3/6.
 */
public class VolleyRequest extends AbsRequest {

    private static RequestQueue requestQueue;

    private VolleyRequest(Builder builder) {
        super(builder);
    }

    @Override
    protected void get() {
        realRequest(Request.Method.GET);
    }

    @Override
    protected void post() {
        realRequest(Request.Method.POST);
    }

    private void realRequest(final int reqType) {
        int tReqType = Request.Method.GET;
        String tUrl = mUrl;
        switch (tReqType) {
            case Request.Method.GET:
                tReqType = Request.Method.GET;
                tUrl = generateUrl(mUrl, mParams);
                break;
            case Request.Method.POST:
                tReqType = Request.Method.POST;
                break;
        }

        StringRequest stringRequest = new StringRequest(tReqType, tUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mCallBack.onFailure(error.getCause());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> superHeader = super.getHeaders();
                if (mHeader != null && mHeader.size() > 0) {
                    superHeader = mHeader;
                }
                return superHeader;
            }

            // 设置Body参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> tParams = super.getParams();
                if (mParams != null && mParams.size() > 0 && reqType == Request.Method.POST) {
                    tParams = mParams;
                }
                return tParams;
            }
        };

        // 设置此次请求超时时间
        if (mTimeOut > 1000) {
            stringRequest.setRetryPolicy(new DefaultRetryPolicy((int) mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        stringRequest.setTag(mTag);
        requestQueue.add(stringRequest);
    }

    public static class Builder extends AbsRequest.Builder {

        private Context mCtx;
        private VolleyRequest sRequest;

        public Builder(Context ctx) {
            this.mCtx = ctx;
        }

        @Override
        public AbsRequest build() {
            if (sRequest == null) {
                synchronized (VolleyRequest.class) {
                    if (sRequest == null) {
                        sRequest = new VolleyRequest(this);
                        requestQueue = Volley.newRequestQueue(mCtx);
                    }
                }
            }
            return sRequest;
        }
    }
}

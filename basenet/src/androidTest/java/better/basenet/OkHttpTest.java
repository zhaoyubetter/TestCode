package better.basenet;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import better.basenet.base.request.AbsRequest;
import better.basenet.base.request.IRequest;
import better.basenet.base.request.IRequestCallBack;
import better.basenet.okhttp.OkHttpRequest;
import better.basenet.volley.VolleyRequest;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class OkHttpTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("better.basenet.test", appContext.getPackageName());
    }

    @Test
    public void testBase1() {
        final AbsRequest.Builder builder = new OkHttpRequest.Builder().url("https://www.baidu.com/");
        builder.callback(new IRequestCallBack() {
            @Override
            public void onSuccess(Object o) {
                Log.e("okHttp success", o.toString());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("okHttp failure", e.toString());
            }
        });
        builder.build().request();

        SystemClock.sleep(500);
    }

    @Test
    public void testPost() {
        Map<String, String> headers = new HashMap<>();
        headers.put("head1", "value1");
        headers.put("head2", "value2");

        Map<String, String> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "value2");

        new OkHttpRequest.Builder().url("https://www.baidu.com/").body(params).headers(headers).type(IRequest.RequestType.POST).callback(new IRequestCallBack() {
            @Override
            public void onSuccess(Object o) {
                Log.e("okHttp success", o.toString());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("okHttp failure", e.toString());
            }
        }).build().request();
    }

    @Test
    public void testTimeOut() {
        new OkHttpRequest.Builder().url("http://www.dodod.com/").callback(new IRequestCallBack() {
            @Override
            public void onSuccess(Object o) {
                Log.e("okHttp success", o.toString());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("okHttp failure", e.toString());
            }
        }).build().request();

        SystemClock.sleep(5000);
    }
}

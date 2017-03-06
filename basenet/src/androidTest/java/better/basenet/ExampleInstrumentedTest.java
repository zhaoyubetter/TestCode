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

import better.basenet.base.request.IRequest;
import better.basenet.base.request.IRequestCallBack;
import better.basenet.volley.VolleyRequest;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("better.basenet.test", appContext.getPackageName());
    }

    @Test
    public void testVolleyBase1() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        new VolleyRequest.Builder(appContext).url("https://www.baidu.com/").callback(new IRequestCallBack() {
            @Override
            public void onSuccess(Object o) {
                Log.e("volley", o.toString());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("volley", e.toString());
            }
        }).build().request();
        SystemClock.sleep(500);
    }

    @Test
    public void testVolley2() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Map<String, String> headers = new HashMap<>();
        headers.put("head1", "value1");
        headers.put("head2", "value2");

        Map<String, String> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key1", "value2");

        new VolleyRequest.Builder(appContext).url("http://jdme.jd.com/jmeMobile/getRandomToken").
                headders(headers).body(params).type(VolleyRequest.RequestType.GET).
                callback(new IRequestCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        Log.e("volley", o.toString());
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e("volley", e.toString());
                    }
                }).build().request();

        SystemClock.sleep(500);
    }
}

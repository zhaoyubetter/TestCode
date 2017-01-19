package groovy.better.com.groovytest;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testMy() {
        int pos = -1;
        int expectedModCount;
        int lastPosition = -1;

        lastPosition = ++pos;

        System.out.println("lastPos: " + lastPosition);
        System.out.println("pos: " + pos);

    }
}
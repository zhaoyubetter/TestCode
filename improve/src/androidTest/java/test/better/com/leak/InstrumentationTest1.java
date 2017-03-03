package test.better.com.leak;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import test.better.com.leak.test.Instrumentation1Activity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by zhaoyu on 2017/2/24.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstrumentationTest1 {
	private static final String STRING_TO_BE_TYPED = "Peter";

	@Rule
	public ActivityTestRule<Instrumentation1Activity> mActivityRule = new ActivityTestRule<>(
			Instrumentation1Activity.class);

	@Test
	public void sayHello(){
		onView(withId(R.id.et)).perform(typeText(STRING_TO_BE_TYPED)); //line 1
		closeSoftKeyboard();

		onView(withText("Say hello!")).perform(click()); //line 2

		String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
		onView(withId(R.id.textView)).check(matches(withText(expectedText))); //line 3

	}
}

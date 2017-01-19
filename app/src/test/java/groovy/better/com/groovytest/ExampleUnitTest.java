package groovy.better.com.groovytest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public void testArrayList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(10 + i);
        }

        ListIterator<Integer> iterator = list.listIterator();
        iterator.hasPrevious();
    }
}
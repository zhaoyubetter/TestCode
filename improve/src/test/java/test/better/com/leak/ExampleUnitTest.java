package test.better.com.leak;

import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void testMock() {
		LinkedList mockedList = mock(LinkedList.class);
		// 此时调用get方法，会返回null，因为还没有对方法调用的返回值做模拟
		System.out.println(mockedList.get(0));

		when(mockedList.get(0)).thenReturn("first");
		System.out.println(mockedList.get(0));

		// 模拟获取第二个元素时，抛出RuntimeException
//		when(mockedList.get(1)).thenThrow(new RuntimeException());
//// 此时将会抛出RuntimeException
//		System.out.println(mockedList.get(1));

		// anyInt()匹配任何int参数，这意味着参数为任意值，其返回值均是element
		when(mockedList.get(anyInt())).thenReturn("element");
// 此时打印是element
		System.out.println(mockedList.get(999));
	}
}
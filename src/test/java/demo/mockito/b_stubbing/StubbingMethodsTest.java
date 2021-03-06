package demo.mockito.b_stubbing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import demo.mockito.PasswordEncoder;

public class StubbingMethodsTest {

	private PasswordEncoder passwordEncoder;

	@Before
	public void beforeMethod() {
		passwordEncoder = mock(PasswordEncoder.class);
	}

	@Test
	public void thenReturn() {
		//doReturn(new StringBuilder()).when(passwordEncoder).encode("1");
		when(passwordEncoder.encode("1")).thenReturn("a");

		assertEquals("a", passwordEncoder.encode("1"));
	}

	@Test
	public void thenReturnConsecutive() {
		when(passwordEncoder.encode("1")).thenReturn("a", "b");

		assertEquals("a", passwordEncoder.encode("1"));
		assertEquals("b", passwordEncoder.encode("1"));
		assertEquals("b", passwordEncoder.encode("1"));
	}

	@Test
	public void thenAnswerGetArgument() {
		when(passwordEncoder.encode("1")).thenAnswer(invocation -> invocation.getArgument(0) + "!");

		assertEquals("1!", passwordEncoder.encode("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void thenAnswerThrowException() {
		when(passwordEncoder.encode("1")).thenAnswer(invocation -> {
			throw new IllegalArgumentException();
		});

		passwordEncoder.encode("1");
	}

	@Test(expected = Exception.class)
	public void thenCallRealMethodFail() {
		when(passwordEncoder.encode("1")).thenCallRealMethod();
	}

	@Test
	public void thenCallRealMethod() {
		Date mock = mock(Date.class);
		doCallRealMethod().when(mock).setTime(42);
		when(mock.getTime()).thenCallRealMethod();

		mock.setTime(42);

		assertEquals(42, mock.getTime());
	}

	@Test(expected = IllegalArgumentException.class)
	public void thenThrowExceptionByInstance() {
		when(passwordEncoder.encode("1")).thenThrow(new IllegalArgumentException());

		passwordEncoder.encode("1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void thenThrowExceptionByClass() {
		when(passwordEncoder.encode("1")).thenThrow(IllegalArgumentException.class);

		passwordEncoder.encode("1");
	}
}

package demo.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ArgumentCaptorTest {

	@Test
	public void testSingleCall() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

		passwordEncoder.encode("password");

		ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
		verify(passwordEncoder).encode(passwordCaptor.capture());

		assertEquals("password", passwordCaptor.getValue());
	}

	@Test
	public void testMultipleCalls() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

		passwordEncoder.encode("password1");
		passwordEncoder.encode("password2");
		passwordEncoder.encode("password3");

		ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
		verify(passwordEncoder, times(3)).encode(passwordCaptor.capture());

		assertEquals(Arrays.asList("password1", "password2", "password3"), passwordCaptor.getAllValues());
	}
}

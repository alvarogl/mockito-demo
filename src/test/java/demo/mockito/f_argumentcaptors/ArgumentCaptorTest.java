package demo.mockito.f_argumentcaptors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import demo.mockito.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class ArgumentCaptorTest {

	@Captor
	private ArgumentCaptor<String> stringCaptor;

	@Test
	public void testSingleCall() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

		passwordEncoder.encode("password");

		ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
		verify(passwordEncoder).encode(passwordCaptor.capture());

		assertEquals("password", passwordCaptor.getValue());
	}

	@Test
	public void testSingleCallWithAnnotation() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

		passwordEncoder.encode("password");

		verify(passwordEncoder).encode(stringCaptor.capture());

		assertEquals("password", stringCaptor.getValue());
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

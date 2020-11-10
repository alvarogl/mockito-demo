package demo.mockito;

import static org.mockito.Mockito.mock;

import org.junit.Test;

public class PasswordEncoderMockDemoTest {

	@Test
	public void simpleMock() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	}
}

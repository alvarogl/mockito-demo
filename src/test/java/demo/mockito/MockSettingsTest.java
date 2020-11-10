package demo.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.exceptions.verification.SmartNullPointerException;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.listeners.InvocationListener;
import org.mockito.listeners.MethodInvocationReport;

public class MockSettingsTest {

	@Test(expected = SmartNullPointerException.class)
	public void returnsSmartNulls() {
		UserRepository userRepository = mock(UserRepository.class, RETURNS_SMART_NULLS);
		User user = userRepository.findById(null);

		// user is SmartNull but would be null with RETURNS_DEFAULTS answer
		assertNotNull(user);

		// will fail with SmartNullPointerException and nice stacktrace
		String passwordHash = user.getPasswordHash();
	}

	@Test
	public void returnsMocks() {
		UserRepository userRepository = mock(UserRepository.class, RETURNS_MOCKS);
		User user = userRepository.findById(null);

		// mocked method returned a mock
		assertNotNull(user);
		assertEquals("", user.getPasswordHash());
		assertFalse(user.isEnabled());
	}

	@Test(expected = WantedButNotInvoked.class)
	public void mockName() {
		PasswordEncoder robustPasswordEncoder = mock(PasswordEncoder.class, "robustPasswordEncoder");
		PasswordEncoder weakPasswordEncoder = mock(PasswordEncoder.class, "weakPasswordEncoder");

		verify(robustPasswordEncoder).encode(anyString());
	}

	@Test(expected = WantedButNotInvoked.class)
	public void mockNoName() {
		PasswordEncoder robustPasswordEncoder = mock(PasswordEncoder.class);
		PasswordEncoder weakPasswordEncoder = mock(PasswordEncoder.class);

		verify(robustPasswordEncoder).encode(anyString());
	}

	@Test
	public void extraInterfaces() {
		PasswordEncoder mock = mock(PasswordEncoder.class, withSettings().extraInterfaces(List.class, Map.class));

		assertTrue(mock instanceof List);
		assertTrue(mock instanceof Map);
	}

	@Test
	public void invocationListeners() {
		InvocationListener invocationListener = new InvocationListener() {
			@Override
			public void reportInvocation(MethodInvocationReport report) {
				if (report.threwException()) {
					Throwable throwable = report.getThrowable();
					// do something with throwable
					throwable.printStackTrace();
				} else {
					Object returnedValue = report.getReturnedValue();
					// do something with returnedValue
					System.out.println(returnedValue);
				}
			}
		};

		PasswordEncoder passwordEncoder =
				mock(PasswordEncoder.class, withSettings().invocationListeners(invocationListener));

		passwordEncoder.encode("1");
	}

	@Test
	public void verboseLogging() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class, withSettings().verboseLogging());

		// listeners are called upon encode() invocation
		when(passwordEncoder.encode("1")).thenReturn("encoded1");

		// even this won't help avoid calling a listener
		//doReturn("encoded1").when(passwordEncoder).encode("1");

		passwordEncoder.encode("1");
		passwordEncoder.encode("2");
	}

	@Test
	public void spiedInstance() {
		UserService userService = new UserService(mock(UserRepository.class), mock(PasswordEncoder.class));

		UserService userServiceMock =
				mock(UserService.class, withSettings().spiedInstance(userService).name("coolService"));
	}
}

package demo.mockito.g_mockitosettings;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;

import demo.mockito.PasswordEncoder;
import demo.mockito.UserRepository;

public class BadTasteTest {

	@Test
	public void clearInvocationsTest() {
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
		UserRepository userRepository = mock(UserRepository.class);

		// use mocks
		passwordEncoder.encode(null);
		userRepository.findById(null);

		// clear
		clearInvocations(passwordEncoder, userRepository);

		// succeeds because invocations were cleared
		verifyZeroInteractions(passwordEncoder, userRepository);
	}
}

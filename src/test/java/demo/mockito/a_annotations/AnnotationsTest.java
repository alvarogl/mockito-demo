package demo.mockito.a_annotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.internal.util.MockUtil.isMock;
import static org.mockito.internal.util.MockUtil.isSpy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import demo.mockito.PasswordEncoder;
import demo.mockito.PasswordEncoderImpl;
import demo.mockito.UserRepository;
import demo.mockito.UserService;

//@RunWith(MockitoJUnitRunner.class)
public class AnnotationsTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Spy
	private PasswordEncoderImpl passwordEncoderImpl;

	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test1() {
		String output = passwordEncoder.encode("1");
		verify(passwordEncoder).encode(anyString());
		Assert.assertNull(output);
	}

	@Test
	public void test2() {
		verifyZeroInteractions(passwordEncoder, userRepository);
		assertFalse(isMock(userService));
		assertFalse(isSpy(userService));
		assertTrue(isMock(userService.getPasswordEncoder()));
		assertTrue(isMock(userService.getUserRepository()));
	}

	@Test
	public void test3() {
		final String output = passwordEncoderImpl.encode("test");
		assertTrue(isSpy(passwordEncoderImpl));
		verify(passwordEncoderImpl).encode(anyString());
		Assert.assertEquals("tset", output);
	}
}

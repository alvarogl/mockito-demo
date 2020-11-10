package demo.mockito;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.internal.util.MockUtil.isMock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

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
	}

	@Test
	public void test3() {
		final String output = passwordEncoderImpl.encode("test");
		verify(passwordEncoderImpl).encode(anyString());
		Assert.assertEquals("tset", output);
	}
}

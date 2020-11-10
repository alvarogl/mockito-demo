package demo.mockito.e_doanswer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import demo.mockito.PasswordEncoder;
import demo.mockito.UserRepository;
import demo.mockito.UserService;

@RunWith(MockitoJUnitRunner.class)
public class DoAnswerTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@Test
	public void thenAnswerCallRealMethod() {
		Date mock = mock(Date.class);
		doAnswer(InvocationOnMock::callRealMethod).when(mock).setTime(42);
		doAnswer(InvocationOnMock::callRealMethod).when(mock).getTime();

		mock.setTime(42);

		assertEquals(42, mock.getTime());
	}

	@Test
	public void doCallRealMethodTest() {
		Date mock = mock(Date.class);
		when(mock.getTime()).thenCallRealMethod();
		doCallRealMethod().when(mock).setTime(42);

		mock.setTime(42);

		assertEquals(42, mock.getTime());
	}

	@Test
	public void doAnswerSimple() {
		when(passwordEncoder.encode("example1")).thenAnswer(invocation -> invocation.getArgument(0) + "!");

		doAnswer(invocation -> invocation.getArgument(0) + "!").when(passwordEncoder).encode("example2");

		final String response1 = passwordEncoder.encode("example1");
		final String response2 = passwordEncoder.encode("example2");

		Assert.assertEquals("example1!", response1);
		Assert.assertEquals("example2!", response2);
	}

	@Test
	public void doAnswerComplex() {
		UserService service = new UserService(userRepository, passwordEncoder);
		doAnswer(invocation -> {
			final String root = invocation.getArgument(0);
			Consumer<Map<String, String>> consumer = invocation.getArgument(1);
			consumer.accept(Collections.singletonMap("ID", root + (Math.random() * 100)));
			consumer.accept(Collections.singletonMap("ID", root + (Math.random() * 100)));
			return null;
		}).when(userRepository).findUsersWithSameRoot(anyString(), any(Consumer.class));

		final String root = "1ABC";
		final List<String> users = service.listSameRootUsers(root);

		Assert.assertEquals(2, users.size());
		Assert.assertTrue(users.get(0).startsWith(root));
		System.out.println(users.get(0));
		Assert.assertTrue(users.get(1).startsWith(root));
		System.out.println(users.get(1));
	}
}

package demo.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileFilter;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;

public class ArgumentMatchersTest {

	private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

	@Test
	public void simpleDemo() {
		when(passwordEncoder.encode(anyString())).thenReturn("exact");

		assertEquals("exact", passwordEncoder.encode("1"));
		assertEquals("exact", passwordEncoder.encode("abc"));
	}

	@Test(expected = InvalidUseOfMatchersException.class)
	public void allArgumentsByMatchersWithInvalidUseOfMatchers() {
		abstract class AClass {
			public abstract boolean call(String s, int i);
		}

		AClass mock = mock(AClass.class);

		// illegal use example
		when(mock.call("a", anyInt())).thenReturn(true);
	}

	@Test
	public void allArgumentsByMatchersWithValidUseOfMatchers() {
		abstract class AClass {
			public abstract boolean call(String s, int i);
		}

		AClass mock = mock(AClass.class);

		when(mock.call(eq("a"), anyInt())).thenReturn(true);
	}

	@Test
	public void customMatcher() {
		FileFilter fileFilter = mock(FileFilter.class);
		ArgumentMatcher<File> hasLuck = file -> file.getName().endsWith("luck");
		when(fileFilter.accept(argThat(hasLuck))).thenReturn(true);

		assertFalse(fileFilter.accept(new File("/deserve")));
		assertTrue(fileFilter.accept(new File("/deserve/luck")));
	}

	@Test
	public void combinedMatchers() {
		when(passwordEncoder.encode(or(eq("1"), contains("a")))).thenReturn("ok");

		assertEquals("ok", passwordEncoder.encode("1"));
		assertEquals("ok", passwordEncoder.encode("123abc"));
		assertNull(passwordEncoder.encode("123"));
	}
}

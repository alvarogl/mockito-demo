package demo.mockito.d_exceptions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Test;

import demo.mockito.MyDictionary;

public class ExceptionsTest {

	@Test(expected = NullPointerException.class)
	public void whenConfigNonVoidReturnMethodToThrowEx_thenExIsThrown() {
		MyDictionary dictMock = mock(MyDictionary.class);
		when(dictMock.getMeaning(anyString())).thenThrow(NullPointerException.class);

		dictMock.getMeaning("word");
	}

	@Test(expected = IllegalStateException.class)
	public void whenConfigVoidReturnMethodToThrowEx_thenExIsThrown() {
		MyDictionary dictMock = mock(MyDictionary.class);
		doThrow(IllegalStateException.class).when(dictMock).add(anyString(), anyString());

		dictMock.add("word", "meaning");
	}

	@Test(expected = NullPointerException.class)
	public void whenConfigNonVoidReturnMethodToThrowExWithNewExObj_thenExIsThrown() {
		MyDictionary dictMock = mock(MyDictionary.class);
		when(dictMock.getMeaning(anyString())).thenThrow(new NullPointerException("Error occurred"));

		dictMock.getMeaning("word");
	}

	@Test(expected = IllegalStateException.class)
	public void whenConfigVoidReturnMethodToThrowExWithNewExObj_thenExIsThrown() {
		MyDictionary dictMock = mock(MyDictionary.class);
		doThrow(new IllegalStateException("Error occurred")).when(dictMock).add(anyString(), anyString());

		dictMock.add("word", "meaning");
	}

	@Test(expected = NullPointerException.class)
	public void givenSpy_whenConfigNonVoidReturnMethodToThrowEx_thenExIsThrown() {
		MyDictionary dictSpy = spy(MyDictionary.class);
		when(dictSpy.getMeaning(anyString())).thenThrow(NullPointerException.class);

		dictSpy.getMeaning("word");
	}
}

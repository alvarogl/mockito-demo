package demo.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import java.text.DecimalFormat;

import org.junit.Test;

public class DecimalFormatSpyTest {

	@Test
	public void test() {
		DecimalFormat decimalFormat = spy(new DecimalFormat());
		assertEquals("42", decimalFormat.format(42L));
	}
}

package demo.mockito.c_argumentmatchers;

import static org.mockito.Mockito.after;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;
import static org.mockito.internal.verification.VerificationModeFactory.atMost;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.AbstractList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTest {

	@Mock
	private MyList mockedList;

	@Test
	public void verifySimpleInvocation() {
		mockedList.size();
		verify(mockedList).size();
	}

	@Test
	public void verifyNumberOfInteractions() {
		mockedList.size();
		verify(mockedList, times(1)).size();
	}

	@Test
	public void verifyNoInteractionsWithWholeMock() {
		verifyZeroInteractions(mockedList);
	}

	@Test
	public void verifyNoInteractionsWithSpecificMethod() {
		verify(mockedList, times(0)).size();
		// or
		verify(mockedList, never()).size();
	}

	@Test
	public void verifyNoUnexpectedIterations() {
		mockedList.size();
		mockedList.clear();
		verify(mockedList).size();
		verify(mockedList).clear();
		verifyNoMoreInteractions(mockedList);
	}

	@Test
	public void verifyOrderOfInteractions() {
		mockedList.size();
		mockedList.add("a parameter");
		mockedList.clear();

		InOrder inOrder = inOrder(mockedList);
		inOrder.verify(mockedList).size();
		inOrder.verify(mockedList).add("a parameter");
		inOrder.verify(mockedList).clear();
	}

	@Test
	public void verifyAtLeastInvocations() {
		mockedList.clear();
		mockedList.clear();
		mockedList.clear();

		verify(mockedList, atLeast(1)).clear();
		verify(mockedList, atMost(10)).clear();
	}

	@Test
	public void verifyTimeout() {
		useMyListInOtherThread();
		verify(mockedList, timeout(500)).add("a");
	}

	@Test
	public void verifyAfter() {
		useMyListInOtherThread();
		verify(mockedList, after(500)).add("a");
	}

	private void useMyListInOtherThread() {
		new Thread(() -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			mockedList.add("a");
		}).start();
	}

	private static class MyList extends AbstractList<String> {

		@Override
		public String get(final int index) {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}
	}
}

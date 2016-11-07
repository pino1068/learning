package refactoring.onebyone;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class ReplaceInheritanceWithDelegationOriginal {
	@SuppressWarnings("serial")
	class MyStack {
		private Vector v = new Vector();
		
		public void push(Object element) {
			v.insertElementAt(element, 0);
		}

		public Object pop() {
			Object result = v.firstElement();
			v.removeElementAt(0);
			return result;
		}

		public int size() {
			return v.size();
		}

		public boolean isEmpty() {
			return v.isEmpty();
		}
	}
	
	@Test
	public void scenario(){
		MyStack s = new MyStack();
		s.push("ciao");
		
		assertEquals(1, s.size());
		assertEquals(false, s.isEmpty());
		assertEquals("ciao", s.pop());
		assertEquals(true, s.isEmpty());
	}
}

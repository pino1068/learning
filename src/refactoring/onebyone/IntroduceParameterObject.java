package refactoring.onebyone;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class IntroduceParameterObject {

	/* before
	  class Entry {
		  
	    Entry (double value, Date chargeDate) {
	        _value = value;
	        _chargeDate = chargeDate;
	    }
	    
	    Date getDate(){
	        return _chargeDate;
	    }
	    
	    double getValue(){
	        return _value;
	    }
	    
	    private Date _chargeDate;
	    private double _value;
	  }
	  
	  public class Account{
	    double getFlowBetween (Date start, Date end) {
	        double result = 0;
	        Enumeration e = _entries.elements();
	        while (e.hasMoreElements()) {
	            Entry each = (Entry) e.nextElement();
	            if (each.getDate().equals(start) ||
	                each.getDate().equals(end) ||
	                 (each.getDate().after(start) && each.getDate().before(end)))
	            {
	            }
	        }
	        return result;
	    }
	    
	    private Vector _entries = new Vector();
	  }
	 */
	
	class Entry {

		private Date _chargeDate;
		private double _value;

		Entry(double value, Date chargeDate) {
			_value = value;
			_chargeDate = chargeDate;
		}

		double getValue() {
			return _value;
		}

		public boolean within(Interval i) {
			return i.includes(_chargeDate);
		}
	}
	  
	public class Account {
		private List<Entry> _entries = new ArrayList<Entry>();

		double getFlowBetween(Interval i) {
	        double result = 0;
			for (Entry e : _entries)
				if (e.within(i))
					result += e.getValue();
	        return result;
		}

		public void add(Date date, int value) {
			_entries.add(new Entry(value, date));
		}
	}

	class Interval {

		private Date start;
		private Date end;

		public Interval(Date start, Date end) {
			this.start = start;
			this.end = end;
		}

		public boolean includes(Date date) {
			return date.equals(start) || date.equals(end)
					|| (date.after(start) && date.before(end));
		}

	}

	@Test
	public void testName() throws Exception {
		IntroduceParameterObject me = new IntroduceParameterObject();
		Account account = me.new Account();
		Date today = new Date();
		account.add(today, 5);
		account.add(today, 15);
		
		double total = account.getFlowBetween( me.new Interval(today, today));
		
		assertEquals( "20.0", ""+total);
	}
	
	public static void main(String[] args) {
		IntroduceParameterObject me = new IntroduceParameterObject();
		Account account = me.new Account();
		Date today = new Date();
		account.add(today, 5);
		account.add(today, 15);
//		System.out.println(account.getFlowBetween(new Date(), new Date()));
		double total = account.getFlowBetween( me.new Interval(today, today));
		System.out.println("total is: "+total);
	}

}
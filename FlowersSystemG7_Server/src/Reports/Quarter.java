package Reports;

import java.util.Calendar;
import java.util.Date;

/**
 * This class stores the quarter details
 */
public class Quarter {
	private int quarter;
	private int year;

	public Quarter(int quarter, int year) {
		this.quarter = quarter;
		this.year = year;
	}
	
	public Quarter() { }

	public int getQuarter() {
		return quarter;
	}

	public int getYear() {
		return year;
	}

	/**
	 * Get the last quarter 
	 * @return last quarter
	 */
	public static Quarter getLastQuarter() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		
		int presentQuarter = (month / 3) + 1;

		int calculatedLastQuarter = presentQuarter - 1;
		
		int lastQuarterYear = calculatedLastQuarter > 0 ? year : year - 1;
		int lastQuarter = (calculatedLastQuarter > 0) ? calculatedLastQuarter : 4;

		return new Quarter(lastQuarter, lastQuarterYear);
	}
	
	public static Date getFirstDayOfQuarter() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3 * 3);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    return cal.getTime();
	}

	public static Date getLastDayOfQuarter() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3 * 3 + 2);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	    return cal.getTime();
	}

}

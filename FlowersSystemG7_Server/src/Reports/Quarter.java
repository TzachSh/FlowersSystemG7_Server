package Reports;

import java.util.Calendar;

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

}

package Reports;

import java.util.ArrayList;

import Branches.OrderReport;
import Logic.DbQuery;

/**
 * This class extends report generation for concert report
 */
public class OrderReportGeneration extends ReportGeneration {
	
	public OrderReportGeneration(DbQuery db, int year, int quarter) {
		super(db, year, quarter);
	}

	@Override
	public int getIndexOfBranchInArray() {
		return 0;
	}

	@Override
	public String getQueryReport() {
		return " SELECT  o.brId, pt.description as 'Product Category', o.oId as 'Order Id',o.creationDate as 'Creation Date', " + 
				"						pio.pId as 'product id',st.status,  " + 
				"						IF(EXISTS(SELECT * FROM catalogproduct cp WHERE cp.pId = p.pId), " + 
				"							(SELECT cp.productName FROM catalogproduct cp WHERE cp.pId = p.pId), " + 
				"							'Custom Product') as 'Product Name', " + 
				"						p.price as 'Price',op.paymentMethod,d.delId as 'Delivery Number',d.Address,d.phone,d.receiver " + 
				"						FROM `order` o  INNER JOIN orderpayment op ON op.oId=o.oId " + 
				"									    INNER JOIN productinorder pio ON o.oId=pio.oId " + 
				"										INNER JOIN product p ON  p.pId = pio.pId " + 
				"						                INNER JOIN producttype pt ON pt.typeId=p.typeId " + 
				"                                        Inner JOIN `status` st ON st.stId=o.stId " + 
				"									    LEFT OUTER JOIN delivery d ON d.oId=o.oId " + 
				"						WHERE year(o.creationDate)=? AND quarter(o.creationDate)=? " + 
				"						ORDER BY pt.description ASC";
	}
	
	/**
	 * Read and get the report for specified branch id
	 * @param branchId Branch id to create for it the report
	 * @return Collection of Order report
	 * @throws Exception Exception when failed on reading the report
	 */
	public ArrayList<OrderReport> getReport(int branchId) throws Exception
	{
		ArrayList<String[]> csvData = getReportInString(branchId);
		
		// convert each column in string array to order report entity
		ArrayList<OrderReport> report = new ArrayList<>();
		
		for (String[] row : csvData)
		{
			String productCategory = row[0];
			int	orderId = Integer.valueOf(row[1]);
			String creationDate = row[2];
			int productId = Integer.valueOf(row[3]);
			String status = row[4];
			String productName = row[5];
			double price = Double.valueOf(row[6]);
			String paymentMethod = row[7];
			
			int deliveryNumber = 0;
			String address = "";
			String phone = "";
			String receiver = "";
			
			try
			{
				deliveryNumber = Integer.valueOf(row[8]);
			}
			catch (Exception e) { deliveryNumber = -1; }
			address = row[9];
			
			phone = row[10];
			
			receiver = row[11];
			
			OrderReport orderReport = new OrderReport(productCategory, orderId, creationDate, productId, productName, price, paymentMethod, deliveryNumber, address, phone, receiver,status);
			report.add(orderReport);
		}

		return report;
	}

	@Override
	public String toString() {
		return "OrderReport";
	}
}

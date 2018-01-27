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
	public String getQueryReport() {
		return " SELECT pt.description as 'Product Category', o.oId as 'Order Id',o.creationDate as 'Creation Date', " + 
				"						pio.pId as 'product id',st.status,  " + 
				"						IF(EXISTS(SELECT * FROM catalogproduct cp WHERE cp.pId = p.pId), " + 
				"							(SELECT cp.productName FROM catalogproduct cp WHERE cp.pId = p.pId), " + 
				"							'Custom Product') as 'Product Name', " + 
				"						cast(o.Total as decimal(8,2)) as 'Price',op.paymentMethod,d.delId as 'Delivery Number',d.Address,d.phone,d.receiver " + 
				"						FROM `order` o  INNER JOIN orderpayment op ON op.oId=o.oId " + 
				"									    INNER JOIN productinorder pio ON o.oId=pio.oId " + 
				"										INNER JOIN product p ON  p.pId = pio.pId " + 
				"						                INNER JOIN producttype pt ON pt.typeId=p.typeId " + 
				"                                       INNER JOIN `status` st ON st.stId=o.stId " + 
				"									    LEFT OUTER JOIN delivery d ON d.oId=o.oId " + 
				"						WHERE o.brId = ? AND year(o.creationDate)=? AND quarter(o.creationDate)=? " + 
				"						ORDER BY pt.description ASC";
	}
	
	@Override
	public Object createObject(String[] row) {
		String productCategory = row[0];
		int	orderId = Integer.valueOf(row[1]);
		String creationDate = row[2];
		int productId = Integer.valueOf(row[3]);
		String status = row[4];
		String productName = row[5];
		String price = row[6]+" $";
		String paymentMethod = row[7];
		
		int deliveryNumber = 0;

		try
		{
			deliveryNumber = Integer.valueOf(row[8]);
		}
		catch (Exception e) 
		{ 
			deliveryNumber = -1;
		}
		
		String address = row[9];
		String phone = row[10];
		String receiver = row[11];
		
		return new OrderReport(productCategory, orderId, creationDate, productId, productName, price, paymentMethod, deliveryNumber, address, phone, receiver,status);
	}

	@Override
	public String toString() {
		return "OrderReport";
	}


}

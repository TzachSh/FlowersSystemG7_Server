package Orders;

import java.io.Serializable;
import java.sql.Date;
/**
 * Entity
 * payment details for order
 */
public class OrderPayment implements Serializable {
	/**payment id*/
	private int id;
	/** order number*/
	private int orderId;
	/** payment method*/
	private PaymentMethod paymentMethod;
	/** total payed */
	private double amount;
	/** date of the payment*/
	private Date paymentDate;
	/**
	 * @return date of the payment
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}
	/**
	 * @param paymentDate update payment date
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	/**
	 * @return payment id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id payment id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return get order number
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId order number
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * 
	 * @return payment option
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	/**
	 * 
	 * @param paymentMethod payment option
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	/**
	 * 
	 * @return total payed
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * 
	 * @param amount update total payed
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * Create Payment for the order
	 * @param id payment id
	 * @param orderId order number
	 * @param paymentMethod payment options
	 * @param amount total amount in payment
	 * @param paymentDate payment date
	 */
	public OrderPayment(int id, int orderId, PaymentMethod paymentMethod, double amount, Date paymentDate) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	/**
	 * Create Payment for the order
	 * @param paymentMethod payment options
	 * @param amount amount
	 * @param paymentDate payment date
	 */
	public OrderPayment(PaymentMethod paymentMethod, double amount, Date paymentDate) {
		super();
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	/**
	 * Create Payment for the order
	 * @param paymentMethod payment option
	 * @param amount amount
	 */
	public OrderPayment(PaymentMethod paymentMethod, double amount) {
		super();
		this.paymentMethod = paymentMethod;
		this.amount = amount;
	}
}

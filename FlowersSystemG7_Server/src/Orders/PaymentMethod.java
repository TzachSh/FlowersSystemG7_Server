package Orders;

import java.io.Serializable;
/**
 * Enumeration
 * Payment options
 */
public enum PaymentMethod implements Serializable {
	Cash,
	CreditCard,
	BalancePayment
}

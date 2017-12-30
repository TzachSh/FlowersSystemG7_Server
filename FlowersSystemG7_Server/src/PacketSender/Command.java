package PacketSender;

import java.io.Serializable;

/**
 * Uses for hands all the commands that will use for client requests and server handling
 *
 */
public enum Command implements Serializable
{
	getCatalogProducts,
	updateCatalogProduct,
	getFlowers
}

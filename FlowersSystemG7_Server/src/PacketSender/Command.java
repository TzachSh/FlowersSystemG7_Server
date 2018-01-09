package PacketSender;

import java.io.Serializable;


/**
 * Uses for hands all the commands that will use for client requests and server handling
 *
 */
public enum Command implements Serializable
{
	getCatalogProducts,
	updateProduct,
	updateCatalogProduct,
	deleteFlowersInProduct,
	updateFlowersInProduct,
	getFlowersInProducts,
	getFlowers,
	getProductTypes,
	insertCatalogProduct,
	insertFlowersInProduct,
	updateCatalogImage,
	getCatalogImage,
	getBranches,
	getBranchSales,
	addUsers,
	getUsers,
	getUsersByUserName,
	getMemberShip,
	getMemberShipKey,
	addCustomers,
	getCustomersKeyByuId,
	addAccounts,
	getAccounts,
	getAccountStatus,
	getAccountbycID,
	getUserByuId,
	updateUserByuId,
	updateCustomerByuId,
	updateAccountsBycId,
	updateAccounts,
	updateCustomer,
	updateUser,
	getColors,
	addFlower,
	getDiscountsByBranch,
	getAccountBycId,
	addReply,
	updateComplain,
	addComplainRefund,
	getComplains,
	addComplain
}
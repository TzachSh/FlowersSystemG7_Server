package PacketSender;

/**
 * Interface that clients can use for implements their work when the result was arrived from the server
 * and also when waiting for result
 *
 */
public interface IResultHandler
{
	/**
	 * Method that called when data arrived from the server
	 * 
	 * @param p the result Packet from the server
	 */
	void onReceivingResult(Packet p);
	
	
	/**
	 * Method that called when waiting for data from the server
	 * 
	 */
	void onWaitingForResult();
}

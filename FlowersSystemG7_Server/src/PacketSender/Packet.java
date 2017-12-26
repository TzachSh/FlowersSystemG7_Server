package PacketSender;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Class uses for sends requests to the server with parameters as optional
 * And also for receive the expected result
 *
 */
public class Packet implements Serializable
{
	/**
	 * private attributes
	 */
	private static final long serialVersionUID = 1L;
	private Command msgKey;
	private String exceptionMessage;
	private ArrayList<Object> paramList = new ArrayList<Object>();
	
	private boolean resultSuccess = true;
	
	/**
	 * Constructor
	 * 
	 * @param msgKey The key for the specific request
	 */
	public Packet(Command msgKey)
	{
		this.msgKey = msgKey;
	}
	
	/**
	 * Check if there is a result for the request from the server
	 * 
	 * @return if result has been received
	 */
	public boolean hasResultFromServer()
	{
		return paramList.size() > 0;
	}
	
	/**
	 * Add parameter for sending to server for client uses
	 * 
	 * @param param the parameter to add
	 */
	public void addParameter(Object param)
	{
		paramList.add(param);
	}
	
	/**
	 * Convert the result list to specific type for client uses
	 * @param <T> The excepted type to convert
	 * 
	 * @return the result list on type excepted
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> convertedResultList()
	{
		ArrayList<T> genericList = new ArrayList<>();
		ArrayList<Object> objList = getParameterList();
		for (Object obj : objList)
			genericList.add((T)obj);
		
		return genericList;
	}
	
	
	/**
	 * Getter for msgKey attribute for server uses
	 * 
	 * @return message key that sent to server
	 */
	public Command getmsgKey()
	{
		return msgKey;
	}
	
	/**
	 * Getter for paramList attribute for client uses
	 * 
	 * @return collection of parameters that sent to server
	 */
	public ArrayList<Object> getParameterList()
	{
		return new ArrayList<Object>(paramList);
	}
	
	/**
	 * Setter for paramList attribute for server uses
	 * 
	 * @param paramList the result and parameters collection that generated from the server or the client
	 */
	public void setParameterList(ArrayList<Object> paramList)
	{
		this.paramList = paramList;
	}
	
	/**
	 * Get the result state from the server
	 * 
	 */
	public boolean getResultState()
	{
		return resultSuccess;
	}
	
	/**
	 * Set an exception that server throw for the request
	 * @param e Exception instance
	 */
	public void setExceptionMessage(Exception e)
	{
		this.exceptionMessage = e.getMessage();
		resultSuccess = false;
	}
	
	/**
	 * Get an exception if received from the server
	 * @return Exception instance
	 */
	public String getExceptionMessage()
	{
		return exceptionMessage;
	}
}

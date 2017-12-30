package PacketSender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
	private String exceptionMessage;
	private HashMap<Command, ArrayList<Object>> cmdParam = new HashMap<Command, ArrayList<Object>>();
	private boolean resultSuccess = true;
	
	/**
	 * Add command for sending to server for client uses
	 * 
	 * @param cmd the command to add
	 */

	public Packet() {};
	
	/**
	 * Add command for sending to server for client uses
	 * 
	 * @param cmd the command to add
	 */
	public void addCommand(Command cmd)
	{
		cmdParam.put(cmd, new ArrayList<Object>());
	}
	
	/**
	 * get all commands registered to the packet
	 */
	public ArrayList<Command> getCommands()
	{
		ArrayList<Command> commands = new ArrayList<>();
		
		for (Command cmd : cmdParam.keySet())
		{
			commands.add(cmd);
		}
		
		return commands;
	}
	
	/**
	 * Convert the result list to specific type for client uses
	 * @param <T> The excepted type to convert
	 * 
	 * @return the result list on type excepted
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> convertedResultListForCommand(Command cmd)
	{
		ArrayList<T> genericList = new ArrayList<>();
		ArrayList<Object> objList = getParameterForCommand(cmd);
		for (Object obj : objList)
			genericList.add((T)obj);
		
		return genericList;
	}
	
	/**
	 * Set parameters list for specific command
	 * 
	 * @param cmd the command to add parameters to it
	 * @param paramList the parameters to add to the command
	 */
	public void setParametersForCommand(Command cmd, ArrayList<Object> paramList)
	{
		cmdParam.put(cmd, paramList);
	}
	
	/**
	 * Getter for paramList attribute for client uses
	 * 
	 * @param cmd the command to get the parameters for
	 * @return collection of parameters that sent to server
	 */
	public ArrayList<Object> getParameterForCommand(Command cmd)
	{
		return cmdParam.get(cmd);
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
	public void setExceptionMessage(String e)
	{
		this.exceptionMessage = e;
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

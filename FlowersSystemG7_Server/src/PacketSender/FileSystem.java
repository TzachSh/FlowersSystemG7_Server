package PacketSender;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;

import javafx.scene.image.Image;
/**
 * Image handling class
 */
public class FileSystem implements Serializable {

	/**
	 * default library
	 */
	private final static String dir = "Uploads";
	
	/**
	 * local path where img is saved
	 */
	private String localPath = "";
	/**
	 * extension of the image
	 */
	private String extenstion = "";
	/**
	 * product codes
	 */
	private int productId = -1;
	/**
	 * image size
	 */
	private int size = 0;
	/**
	 * image in byte array
	 */
	public byte[] mybytearray;
	/**
	 * if file is empty
	 */
	private boolean fileIsEmpty = false;
	/**
	 * if file is not exists
	 */
	private boolean fileIsNotExists = false;
	
	/**
	 * init the array
	 * @param size size of new array
	 */
	public void initArray(int size) {
		mybytearray = new byte[size];
	}
	/**
	 * Constructor default
	 */
	public FileSystem() 
	{
		this("");
	}
	/**
	 * Constructor 
	 * @param localPath local path to save the images
	 */
	public FileSystem(String localPath) 
	{
		loadImageFromLocal(localPath);
	}
	/**
	 * send image to the server to save
	 * @throws IOException  if failed
	 */
	public void saveImageOnServer() throws IOException
	{
		File theDir = new File(dir);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		        theDir.mkdir();
		}
		
		// there is no product id linked to the image
		if (productId == -1)
			return;
		
		String serverPath = getServerPath();
		// save image to default local
		try (FileOutputStream fos = new FileOutputStream(serverPath)) {
			   fos.write(getMybytearray());
			   fos.close();
			}
	}
	/**
	 * get server path where images is saved
	 * @return path on server
	 */
	public String getServerPath()
	{
		return String.format("%s/p%d.%s", dir, productId, extenstion);
	}
	/**
	 * load image from local path
	 * @param localPath path from where to load
	 */
	public void loadImageFromLocal(String localPath)
	{
		File newFile = null;
		try
		{
			if (localPath.isEmpty())
			{
				fileIsEmpty = true;
				throw new FileNotFoundException();
			}
			
		    newFile = new File(localPath);
			if (!newFile.exists())
			{
				fileIsNotExists = true;
				throw new FileNotFoundException();
			}
		}
		catch (FileNotFoundException e)
		{
			newFile = new File(Paths.get(dir, "blank.png").toString());
		}
		finally
		{
			try
			{
				// blank image is not exists
				if (newFile == null || newFile != null && !newFile.exists())
					throw new FileNotFoundException();
			
				setExtenstion(newFile.getName().split("\\.")[1]);
				byte [] mybytearray  = new byte [(int)newFile.length()];
				FileInputStream fis = new FileInputStream(newFile);
				BufferedInputStream bis = new BufferedInputStream(fis);			  
	      
				initArray(mybytearray.length);
				setSize(mybytearray.length);
	      
				bis.read(getMybytearray(),0,mybytearray.length);
				bis.close();
				
				this.localPath = localPath;
			}
			catch (IOException e) { }
		}
	}
	/**
	 * get image extension
	 * @return extension
	 */
	public String getExtenstion()
	{
		return extenstion; 
	}
	
	/**
	 * update image extension
	 * @param extenstion extension
	 */
	public void setExtenstion(String extenstion)
	{
		this.extenstion = extenstion;
	}
	/**
	 * @return local path
	 */
	public String getLocalFilePath() {
		return localPath;
	}

	public void setLocalFilePath(String localPath) {
		this.localPath = localPath;
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}

	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	public void setMybytearray(byte[] mybytearray) {

		for (int i = 0; i < mybytearray.length; i++)
			this.mybytearray[i] = mybytearray[i];
	}
	
	public boolean getIfFileIsNotExists()
	{
		return fileIsNotExists;
	}
	
	public boolean getIfFileIsEmpty()
	{
		return fileIsEmpty;
	}
	
	public Image getImageInstance() throws IOException
	{
		if (fileIsNotExists)
			throw new FileNotFoundException("Image For this Product has been Removed, Please update the Image.");
		
		if (fileIsEmpty)
			throw new FileNotFoundException("There is no Image Linked to this Product on the Server, Please Update the Image for Re-Linking");
		
		Image img = new Image(new ByteArrayInputStream(getMybytearray()));
		return img;
	}
	
	public Image getImage() 
	{
		Image img = new Image(new ByteArrayInputStream(getMybytearray()));
		return img;
	}
}

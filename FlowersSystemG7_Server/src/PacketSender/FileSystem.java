package PacketSender;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javafx.scene.image.Image;

public class FileSystem implements Serializable {

	private final static String dir = "Uploads";
	
	private String serverPath = "";
	private String localPath = "";
	private String extenstion = "";
	private int productId = -1;
	private int size = 0;
	public byte[] mybytearray;
	private boolean fileIsEmpty = false;
	private boolean fileIsNotExists = false;
	
	
	public void initArray(int size) {
		mybytearray = new byte[size];
	}

	public FileSystem() 
	{
		this("");
	}
	
	public FileSystem(String localPath) 
	{
		loadImageFromLocal(localPath);
	}
	
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
		
		// save image to default local
		try (FileOutputStream fos = new FileOutputStream(serverPath)) {
			   fos.write(getMybytearray());
			   fos.close();
			}
	}
	
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
			newFile = new File(dir + "/blank.png");
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
				this.serverPath = String.format("%s/p%d.%s", dir, productId, extenstion);
			}
			catch (IOException e) { }
		}
	}
	
	public String getExtenstion()
	{
		return extenstion; 
	}
	
	public void setExtenstion(String extenstion)
	{
		this.extenstion = extenstion;
	}

	public String getLocalFilePath() {
		return localPath;
	}

	public void setLocalFilePath(String localPath) {
		this.localPath = localPath;
	}
	
	public String getServerFilePath() {
		return serverPath;
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

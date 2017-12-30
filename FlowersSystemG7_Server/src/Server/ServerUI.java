package Server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerUI extends Application{
	private static final int DEFAULT_PORT = 5555;
	@FXML
	private TextField txtPort;
	@FXML
	private Button btnSubmit;
	@FXML
	private TextArea txtLog;
	@FXML
	private TextField txtDb;
	@FXML
	private TextField txtUser;
	@FXML
	private PasswordField txtPass;
	private SystemServer sc=null;
	int port = 0; // Port to listen on
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * if button pressed check
	 * the function check if server already listen to port 
	 * if yes then stop to listen
	 *  otherwise start listen and update button text
	 * */
	public void onSubmitClicked(ActionEvent event)
	{
		String time=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());//get datetime for log print
		if(sc==null || !sc.isListening())//check if not initiate listening or is not listen
		{
			try {
				port = Integer.parseInt(txtPort.getText()); // Get port from command line
				sc = new SystemServer(port,txtLog);
			} catch (Throwable t) {//if  port is wrong or listening already
				txtLog.setText(time+"---"+"ERROR - Could not listen for clients from this port!\n\r"+txtLog.getText()+"\n\r");
				return;
			}
		}
		try 
		{
			if(sc.changeListening(txtDb.getText(), txtUser.getText(), txtPass.getText()))//check if switch listening is complete
			{
				if(btnSubmit.getText().equals("Start service")) {//if it was listening
					txtLog.setText(time+"---Server has started listening on port:"+port+"\n\r"+txtLog.getText());//write to log
					btnSubmit.setText("Stop service");//update button
					
				}
				else//if it starts to listen
				{
					txtLog.setText(time+"---Server has finished listening on port:"+port+"\n\r"+txtLog.getText());
					btnSubmit.setText("Start service");///update button
				}
			}
			else//if failed
			{
				txtLog.setText(time+"---ERROR - operation failed!\n\r"+txtLog.getText());
				
			}
		}
		catch (Exception e) {//if it was exception in switch listen to on/off
			txtLog.setText(e.getMessage()+"\n\r"+txtLog.getText());
		}
	}
	/***
	 * clear log text area
	 */
	public void onClearClicked(ActionEvent event)
	{
		txtLog.clear();
	}
	
	
	/**
	 * initialize the window of the server
	 * 
	 * */
	@Override
	public void start(Stage arg0) throws Exception {
		
		String title = "Server";
		String srcFXML = "/Server/App.fxml";
		String srcCSS = "/Server/application.css";
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(srcFXML));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(srcCSS).toExternalForm());
			arg0.setTitle(title);
			arg0.setScene(scene);
			arg0.show();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
		}
		
		arg0.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
			}
		});
	}
}

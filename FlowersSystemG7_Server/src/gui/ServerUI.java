package gui;

import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;

import Server.SystemServer;
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
	private PasswordField txtPass;
	private SystemServer sc=null;
	int port = 0; // Port to listen on
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * if button pressed check if server already listen to port 
	 * if yes then stop to listen
	 *  otherwise start listen and update button text
	 * */
	public void onSubmitClicked(ActionEvent event)
	{
		String dataBase= new String();
		String password = new String();
		if(sc==null || !sc.isListening())//check if listen
		{
			try {
				port = Integer.parseInt(txtPort.getText()); // Get port from command line
			} catch (Throwable t) {
				port = DEFAULT_PORT; // Set port to 5555
			}
			if(txtDb.getText().isEmpty())
				dataBase="test";
			else
				dataBase=txtDb.getText();
			if(txtPass.getText().isEmpty())
				password="root";
			else
				password=txtPass.getText();

			sc = new SystemServer(port);
			btnSubmit.setText("Stop service");//update button
			try {
				sc.listen(); // Start listening for connections
				txtLog.setText(txtLog.getText()+"\r\n"+"Server has started listening on port:"+port);//write to log
			} catch (Exception ex) {
				System.out.println("ERROR - Could not listen for clients!");//write to log
				txtLog.setText(txtLog.getText()+"ERROR - Could not listen for clients!");
			}	
		}
		else
		{
			btnSubmit.setText("Start service");///update button
			connectionClose();
		}
	}
	/**
	 * closing connection and write to log
	 * */
	private void connectionClose()
	{
		if(sc.isListening())
			try {
				sc.stopListening();
				sc.close();
				txtLog.setText(txtLog.getText()+"\r\n"+"Server has finished listening on port:"+port);
//				System.out.println("Connection closed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				txtLog.setText(txtLog.getText()+"\r\n"+e.getMessage());
	//			e.printStackTrace();
			}
	}
	
	/**
	 * initialize the window of the server
	 * 
	 * */
	@Override
	public void start(Stage arg0) throws Exception {
		
		String title = "Server";
		String srcFXML = "/gui/App.fxml";
		String srcCSS = "/gui/application.css";
		
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

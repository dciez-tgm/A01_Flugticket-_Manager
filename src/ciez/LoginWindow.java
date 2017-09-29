package ciez;

import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import java.sql.*;
import java.util.Properties;

public class LoginWindow {

	protected Shell shlLogin;
	private Text textHost;
	private Text textUser;
	private Text textPassword;
	private Text textPort;
	private Text txtOutput;
	
	private static Connection conn = null;
	
	// Host name
	private static String dbHost;
	
	// Port ... standard: 3306
	private static String dbPort;
	
	// Database name: flightdata
	private static String database = "flightdata";
	
	// Database user
	private static String dbUser;
	
	// Database password
	private static String dbPw;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginWindow window = new LoginWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlLogin.open();
		shlLogin.layout();
		while (!shlLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlLogin = new Shell();
		shlLogin.setSize(450, 350);
		shlLogin.setText("Login");
		shlLogin.setLayout(null);
		
		
		Label lblDbConnection = new Label(shlLogin, SWT.CENTER);
		lblDbConnection.setBounds(110, 60, 210, 32);
		lblDbConnection.setFont(SWTResourceManager.getFont("System", 20, SWT.NORMAL));
		lblDbConnection.setText("DB - Connection");
		
		Label lblHost = new Label(shlLogin, SWT.NONE);
		lblHost.setFont(SWTResourceManager.getFont("System", 14, SWT.NORMAL));
		lblHost.setBounds(79, 136, 70, 20);
		lblHost.setText("Hostname");
		
		Label lblUser = new Label(shlLogin, SWT.NONE);
		lblUser.setText("Username");
		lblUser.setFont(SWTResourceManager.getFont("System", 14, SWT.NORMAL));
		lblUser.setBounds(79, 172, 70, 20);
		
		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setText("Password");
		lblPassword.setFont(SWTResourceManager.getFont("System", 14, SWT.NORMAL));
		lblPassword.setBounds(79, 210, 70, 20);
		
		Label lblPort = new Label(shlLogin, SWT.NONE);
		lblPort.setFont(SWTResourceManager.getFont("Courier", 5, SWT.NORMAL));
		lblPort.setAlignment(SWT.CENTER);
		lblPort.setBounds(347, 113, 30, 15);
		lblPort.setText("Port");
		
		textHost = new Text(shlLogin, SWT.BORDER);
		textHost.setBounds(165, 134, 176, 21);
		
		textPort = new Text(shlLogin, SWT.BORDER);
		textPort.setBounds(347, 134, 36, 21);
		
		textUser = new Text(shlLogin, SWT.BORDER);
		textUser.setBounds(165, 171, 176, 21);
		
		textPassword = new Text(shlLogin, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(165, 208, 176, 21);
				
		txtOutput = new Text(shlLogin, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
		txtOutput.setFont(SWTResourceManager.getFont("Courier", 10, SWT.NORMAL));
		txtOutput.setBounds(73, 243, 202, 58);
		txtOutput.setEnabled(false);
		txtOutput.setText("");
		
		Button btnLogin = new Button(shlLogin, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Assigning the inputs to the variables
				dbHost = textHost.getText();
				dbPort = textPort.getText();
				dbUser = textUser.getText();
				dbPw = textPassword.getText();
				
				Properties properties = new Properties();
				
				// Checking if all the text fields meet the requirements
				if(dbHost == "" || dbPort == "" || dbUser == "" || dbPort == ""){
					txtOutput.setText("At least one text-field is empty!");
				}else{
					boolean valid = false;
					do{
						// Checking if dbPort is a valid number
						try {
							int temp = Integer.parseInt(dbPort);
						} catch(NumberFormatException e1) {
							txtOutput.setText("Port must be a number!");
							return;
						}
						
						// Connecting to database
						try {
							connect(dbHost, dbPort, database, dbUser, dbPw);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						valid = true;
					}while(valid == false);
					
					// Opening MainWindow after all the requirements are met
					txtOutput.setText("Connection successfully established!");
					try {
						MainWindow window = new MainWindow(conn);
						window.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}					
				}
			}
		});
		btnLogin.setFont(SWTResourceManager.getFont("System", 9, SWT.NORMAL));
		btnLogin.setBounds(281, 243, 60, 22);
		btnLogin.setText("Login");

	}
	
	
	public void connect(String host, String port, String db, String user, String pw) throws Exception{

	      if(conn != null) return;

	      try {
	          Class.forName("com.mysql.jdbc.Driver");
	      } catch (ClassNotFoundException e) {
	          throw new Exception("No database");
	      }

	      conn = DriverManager.getConnection(
	        "jdbc:mysql://" + host +":"+port+"/"+db+"?autoReconnect=true&useSSL=false", user, pw); 
	  }
	
}

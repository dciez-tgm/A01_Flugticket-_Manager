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

public class LoginWindow {

	protected Shell shlLogin;
	private Text textServer;
	private Text textUser;
	private Text textPassword;

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
		
		Label lblServer = new Label(shlLogin, SWT.NONE);
		lblServer.setFont(SWTResourceManager.getFont("System", 14, SWT.NORMAL));
		lblServer.setBounds(79, 136, 70, 20);
		lblServer.setText("Server");
		
		Label lblUser = new Label(shlLogin, SWT.NONE);
		lblUser.setText("Username");
		lblUser.setFont(SWTResourceManager.getFont("System", 14, SWT.NORMAL));
		lblUser.setBounds(79, 172, 70, 20);
		
		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setText("Password");
		lblPassword.setFont(SWTResourceManager.getFont("System", 14, SWT.NORMAL));
		lblPassword.setBounds(79, 210, 70, 20);
		
		textServer = new Text(shlLogin, SWT.BORDER);
		textServer.setBounds(165, 134, 176, 21);
		
		textUser = new Text(shlLogin, SWT.BORDER);
		textUser.setBounds(165, 171, 176, 21);
		
		textPassword = new Text(shlLogin, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(165, 208, 176, 21);
		
		Button btnLogin = new Button(shlLogin, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnLogin.setFont(SWTResourceManager.getFont("System", 9, SWT.NORMAL));
		btnLogin.setBounds(281, 243, 60, 22);
		btnLogin.setText("Login");

	}
}

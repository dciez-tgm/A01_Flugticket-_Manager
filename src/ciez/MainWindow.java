package ciez;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import java.sql.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {

	private static Connection conn;
	
	protected Shell shlFlightPlanner;
	private Text txtOutput;
	private Text txtFirstName;
	private Text txtLastName;

	
	// Constructor; Creates a Connection Item
	public MainWindow(Connection c){
		this.conn = c;
	}
	
	// Creating different queries
	private String originCountryQuery = "SELECT name FROM countries";
	private String originAirportQuery = "";
	private String destinationCountryQuery = "";
	private String destinationAirportQuery = "";
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlFlightPlanner.open();
		shlFlightPlanner.layout();
		while (!shlFlightPlanner.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shlFlightPlanner = new Shell();
		shlFlightPlanner.setSize(750, 515);
		shlFlightPlanner.setText("Flight Planner\r\n");
		txtOutput = new Text(shlFlightPlanner, SWT.BORDER | SWT.MULTI); //SWT.MULTI for multiple line output
		txtOutput.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtOutput.setEditable(false);
		txtOutput.setEnabled(false);
		txtOutput.setText("Flight Information:" + "\n");
		txtOutput.setFont(SWTResourceManager.getFont("Courier", 10, SWT.NORMAL));
		txtOutput.setBounds(480, 160, 225, 280);
		//tOutput.append(""); to add text
		txtOutput.append("---   Origin    ---" + "\n");
		txtOutput.append("Country: " + "\n");
		txtOutput.append("Airport: " + "\n");
		txtOutput.append("--- Destination ---" + "\n");
		txtOutput.append("Country: " + "\n");
		txtOutput.append("Airport: " + "\n");
		txtOutput.setVisible(true);

		
		Label lblTickts = new Label(shlFlightPlanner, SWT.NONE);
		lblTickts.setFont(SWTResourceManager.getFont("System", 30, SWT.NORMAL));
		lblTickts.setBounds(25, 25, 300, 50);
		lblTickts.setText("Flight Booking");
		
		Label lblOrigin = new Label(shlFlightPlanner, SWT.NONE);
		lblOrigin.setFont(SWTResourceManager.getFont("System", 17, SWT.NORMAL));
		lblOrigin.setBounds(25, 120, 110, 35);
		lblOrigin.setText("Origin");
		
		Label lblDestination = new Label(shlFlightPlanner, SWT.NONE);
		lblDestination.setText("Destination");
		lblDestination.setFont(SWTResourceManager.getFont("System", 17, SWT.NORMAL));
		lblDestination.setBounds(238, 120, 160, 35);
		
		Label lblCountryOrigin = new Label(shlFlightPlanner, SWT.NONE);
		lblCountryOrigin.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblCountryOrigin.setBounds(25, 161, 110, 18);
		lblCountryOrigin.setText("Country");
		
		Label lblCountryDestination = new Label(shlFlightPlanner, SWT.NONE);
		lblCountryDestination.setText("Country");
		lblCountryDestination.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblCountryDestination.setBounds(238, 161, 110, 18);
		
		ComboViewer comboViewerOriginCountry = new ComboViewer(shlFlightPlanner, SWT.DROP_DOWN | SWT.BORDER);
		Combo comboOriginCountry = comboViewerOriginCountry.getCombo();
		comboOriginCountry.setBounds(25, 185, 200, 23);
		// Filling drop-down list with the country names
		//fillDropDown(comboOriginCountry, originCountryQuery, "name");
		
		ComboViewer comboViewerDestinationCountry = new ComboViewer(shlFlightPlanner, SWT.DROP_DOWN | SWT.BORDER);
		Combo comboDestinationCountry = comboViewerDestinationCountry.getCombo();
		comboDestinationCountry.setBounds(238, 185, 200, 23);
		
		Label lblAirportOrigin = new Label(shlFlightPlanner, SWT.NONE);
		lblAirportOrigin.setText("Airport");
		lblAirportOrigin.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblAirportOrigin.setBounds(25, 232, 110, 18);
		
		Label lblAirportDestination = new Label(shlFlightPlanner, SWT.NONE);
		lblAirportDestination.setText("Airport");
		lblAirportDestination.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblAirportDestination.setBounds(238, 232, 110, 18);
		
		ComboViewer comboViewerOriginAirport = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboOriginAirport = comboViewerOriginAirport.getCombo();
		comboOriginAirport.setBounds(25, 256, 200, 20);
		
		ComboViewer comboViewerDestinationAirport = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboDestinationAirport = comboViewerDestinationAirport.getCombo();
		comboDestinationAirport.setBounds(238, 256, 200, 23);
		
		Label lblPassenger = new Label(shlFlightPlanner, SWT.NONE);
		lblPassenger.setText("Passenger");
		lblPassenger.setFont(SWTResourceManager.getFont("System", 17, SWT.NORMAL));
		lblPassenger.setBounds(25, 297, 160, 35);
		
		Label lblFirstName = new Label(shlFlightPlanner, SWT.NONE);
		lblFirstName.setAlignment(SWT.RIGHT);
		lblFirstName.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblFirstName.setBounds(25, 350, 94, 20);
		lblFirstName.setText("First Name");
		
		Label lblLastName = new Label(shlFlightPlanner, SWT.NONE);
		lblLastName.setAlignment(SWT.RIGHT);
		lblLastName.setText("Last Name");
		lblLastName.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblLastName.setBounds(25, 376, 94, 20);
		
		txtFirstName = new Text(shlFlightPlanner, SWT.BORDER);
		txtFirstName.setBounds(125, 349, 100, 21);
		
		txtLastName = new Text(shlFlightPlanner, SWT.BORDER);
		txtLastName.setBounds(125, 376, 100, 21);
		
		Label lblAirline = new Label(shlFlightPlanner, SWT.NONE);
		lblAirline.setAlignment(SWT.CENTER);
		lblAirline.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblAirline.setBounds(238, 350, 100, 20);
		lblAirline.setText("Airline");
		
		ComboViewer comboViewerAirline = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboAirline = comboViewerAirline.getCombo();
		comboAirline.setBounds(238, 375, 100, 23);
		
		Label lblRow = new Label(shlFlightPlanner, SWT.NONE);
		lblRow.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblRow.setAlignment(SWT.RIGHT);
		lblRow.setBounds(344, 350, 45, 20);
		lblRow.setText("Row");
		
		Label lblSeat = new Label(shlFlightPlanner, SWT.NONE);
		lblSeat.setText("Seat");
		lblSeat.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblSeat.setAlignment(SWT.RIGHT);
		lblSeat.setBounds(344, 376, 45, 20);
		
		ComboViewer comboViewerRow = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboRow = comboViewerRow.getCombo();
		comboRow.setBounds(395, 347, 45, 23);
		
		ComboViewer comboViewerSeat = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboSeat = comboViewerSeat.getCombo();
		comboSeat.setBounds(395, 373, 45, 23);
		
		Button btnSubmit = new Button(shlFlightPlanner, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  
				
			}
		});
		btnSubmit.setFont(SWTResourceManager.getFont("System", 9, SWT.NORMAL));
		btnSubmit.setBounds(363, 416, 75, 25);
		btnSubmit.setText("Submit");
		// Passenger Arguments:
		// id(autoincrement), First Name, Last Name, Airline, Flight Number, Rownumber, Seatposition

	}
	
	
	/*
	 * Fills a drop down box with the 
	 * specified column-items from a particular query
	 * 
	 * @param list The drop-down list which will be filled
	 * @param query A String containing a query
	 * @param c A String containing the "result-column" 
	 */
	public static void fillDropDown(Combo list, String query, String c){
		String column = c;
		// Creating a Statement Object
		Statement st;
		try {
			// Creating a result set
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			// Iterating through result set
			while (rs.next()) {
				list.add(rs.getString(column));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}

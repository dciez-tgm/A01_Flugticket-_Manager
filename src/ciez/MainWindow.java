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
import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.widgets.List;

public class MainWindow {

	private static Connection conn;
	
	protected Shell shlFlightPlanner;
	private Text txtOutput;
	private Text txtFirstName;
	private Text txtLastName;
	

	private String originCountry;
	private String destinationCountry;
	private String originAirport;
	private String destinationAirport;
	private String originCountryCode;
	private String destinationCountryCode;
	private String originAirportCode;
	private String destinationAirportCode;
	private String[] flights;
	
	
	// Constructor; Creates a Connection Item
	public MainWindow(Connection c){
		this.conn = c;
	}
	
	
	private HashMap<String, String> countries;
	
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
		shlFlightPlanner.setSize(1000, 600);
		shlFlightPlanner.setText("Flight Planner\r\n");
		
		txtOutput = new Text(shlFlightPlanner, SWT.BORDER | SWT.MULTI); //SWT.MULTI for multiple line output
		txtOutput.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtOutput.setEditable(false);
		txtOutput.setEnabled(false);
		txtOutput.setText("Flight Information:");
		txtOutput.setFont(SWTResourceManager.getFont("Courier", 10, SWT.NORMAL));
		txtOutput.setBounds(523, 160, 451, 373);
		refreshOutput(txtOutput, "", "", "", "");
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
		
		ComboViewer comboViewerDestinationCountry = new ComboViewer(shlFlightPlanner, SWT.DROP_DOWN | SWT.BORDER);
		Combo comboDestinationCountry = comboViewerDestinationCountry.getCombo();
		comboDestinationCountry.setBounds(238, 185, 200, 23);
		
		// Filling drop-down lists with the country names
		fillCountries(comboOriginCountry);
		fillCountries(comboDestinationCountry);
		
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
		
		List flightlist = new List(shlFlightPlanner, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		flightlist.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		flightlist.setItems(new String[] {});
		flightlist.setBounds(25, 290, 492, 85);
		
		Label lblPassenger = new Label(shlFlightPlanner, SWT.NONE);
		lblPassenger.setText("Passenger");
		lblPassenger.setFont(SWTResourceManager.getFont("System", 17, SWT.NORMAL));
		lblPassenger.setBounds(25, 390, 160, 35);
		
		Label lblFirstName = new Label(shlFlightPlanner, SWT.NONE);
		lblFirstName.setAlignment(SWT.RIGHT);
		lblFirstName.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblFirstName.setBounds(25, 443, 94, 20);
		lblFirstName.setText("First Name");
		
		Label lblLastName = new Label(shlFlightPlanner, SWT.NONE);
		lblLastName.setAlignment(SWT.RIGHT);
		lblLastName.setText("Last Name");
		lblLastName.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblLastName.setBounds(25, 469, 94, 20);
		
		txtFirstName = new Text(shlFlightPlanner, SWT.BORDER);
		txtFirstName.setBounds(125, 442, 100, 21);
		
		txtLastName = new Text(shlFlightPlanner, SWT.BORDER);
		txtLastName.setBounds(125, 469, 100, 21);
		
		Label lblAirline = new Label(shlFlightPlanner, SWT.NONE);
		lblAirline.setAlignment(SWT.CENTER);
		lblAirline.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblAirline.setBounds(238, 443, 100, 20);
		lblAirline.setText("Airline");
		
		ComboViewer comboViewerAirline = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboAirline = comboViewerAirline.getCombo();
		comboAirline.setBounds(238, 468, 100, 23);
		
		// Filling drop-down list with the airline names
		fillAirlines(comboAirline);
		
		Label lblRow = new Label(shlFlightPlanner, SWT.NONE);
		lblRow.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblRow.setAlignment(SWT.RIGHT);
		lblRow.setBounds(344, 443, 45, 20);
		lblRow.setText("Row");
		
		Label lblSeat = new Label(shlFlightPlanner, SWT.NONE);
		lblSeat.setText("Seat");
		lblSeat.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblSeat.setAlignment(SWT.RIGHT);
		lblSeat.setBounds(344, 469, 45, 20);
		
		ComboViewer comboViewerRow = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboRow = comboViewerRow.getCombo();
		comboRow.setBounds(395, 440, 45, 23);
		
		ComboViewer comboViewerSeat = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboSeat = comboViewerSeat.getCombo();
		comboSeat.setBounds(395, 466, 45, 23);

		
		
		

		
		
		
		
		Button btnSubmit = new Button(shlFlightPlanner, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  
				
			}
		});
		btnSubmit.setFont(SWTResourceManager.getFont("System", 9, SWT.NORMAL));
		btnSubmit.setBounds(447, 508, 70, 25);
		btnSubmit.setText("Submit");
		
			
		Button btnLockCountires = new Button(shlFlightPlanner, SWT.NONE);
		btnLockCountires.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				// The "old" airports are being deleted in case a new airport is locked in 
				// The "country" input is received and afterwards the country-code
				comboOriginAirport.removeAll();
				originCountry = comboOriginCountry.getText();
				originCountryCode = getCountryCode(originCountry);
				// Filling the airport drop-down list
				fillAirports(comboOriginAirport, originCountryCode);
				
				// Same as above
				comboDestinationAirport.removeAll();
				destinationCountry = comboDestinationCountry.getText();
				destinationCountryCode = getCountryCode(destinationCountry);
				fillAirports(comboDestinationAirport, destinationCountryCode);
				
				// Adding selected countries to preview/output window
				refreshOutput(txtOutput, originCountry, destinationCountry, "", "");
			}
		});
		btnLockCountires.setFont(SWTResourceManager.getFont("System", 9, SWT.NORMAL));
		btnLockCountires.setBounds(447, 183, 70, 25);
		btnLockCountires.setText("Lock");
		
		
		Button btnLockAirports = new Button(shlFlightPlanner, SWT.NONE);
		btnLockAirports.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				// Get selected airport and city text
				originAirport = comboOriginAirport.getText();
				String[] tempOriginSplit = originAirport.split(",");
				originAirportCode = getAirportCode(tempOriginSplit[0]);
				
				destinationAirport = comboDestinationAirport.getText();
				String[] tempDestinationSplit = destinationAirport.split(",");
				destinationAirportCode = getAirportCode(tempDestinationSplit[0]);
				
				// Check if there are any available flights (if so, fill the list)
				checkFlights(flightlist, originAirportCode, destinationAirportCode);
				
				// Adding selected countries to preview/output window
				refreshOutput(txtOutput, originCountry, destinationCountry, originAirport, destinationAirport);
			}
		});
		btnLockAirports.setText("Lock");
		btnLockAirports.setFont(SWTResourceManager.getFont("System", 9, SWT.NORMAL));
		btnLockAirports.setBounds(447, 254, 70, 25);
		
		Button btnResetAll = new Button(shlFlightPlanner, SWT.NONE);
		btnResetAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comboOriginCountry.deselectAll();
				comboOriginAirport.removeAll();
				comboDestinationCountry.deselectAll();
				comboDestinationAirport.removeAll();
				
				refreshOutput(txtOutput, "", "", "", "");
			}
		});
		btnResetAll.setFont(SWTResourceManager.getFont("System", 9, SWT.NORMAL));
		btnResetAll.setBounds(357, 508, 81, 25);
		btnResetAll.setText("Reset All");
		// Passenger Arguments:
		// id(autoincrement), First Name, Last Name, Airline, Flight Number, Rownumber, Seatposition

		
	}
	
	
	/*
	 * Fills a drop down box with the 
	 * specified column-items
	 * 
	 * @param combo The drop-down list which will be filled
	 */
	public static void fillCountries(Combo combo){
		// Creating a Statement object
		Statement st;
		try {
			// Creating a result set
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT name FROM countries");
			
			// Iterating through result set
			while (rs.next()) {	
				combo.add(rs.getString("name"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Executes a query to receive the associated country-code to a certain country
	 * 
	 * @param country Specifies which country code is returned
	 */
	public static String getCountryCode(String country){
		// Creating a Statement object
		Statement st;
		try {
			// Creating a result set
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT code FROM countries WHERE name='" + country + "'");
			
			String code;
			// Iterating through result set
			while (rs.next()) {
				code = rs.getString("code");
				return code;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/*
	 * Fills a drop down box with the 
	 * specified column-items
	 * 
	 * @param combo The drop-down list which will be filled
	 * @param code The country code to pick the correct airport
	 */
	public static void fillAirports(Combo combo, String code){
		// Creating a Statement object
		Statement st;
		try {
			// Creating a result set
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT name, city FROM airports WHERE country='" + code + "'");
			
			
			String airport;
			String city;
			// Iterating through result set
			while (rs.next()) {
				airport = rs.getString("name");
				city = rs.getString("city");
				String result = airport + ", " + city;
				combo.add(result);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * This function returns the airport code
	 * 
	 * @param airport Specifies which airport code is returned
	 */
	public static String getAirportCode(String airport){
		// Creating a Statement object
		Statement st;
		try {
			// Creating a result set
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT airportcode FROM airports WHERE name='" + airport + "'");
			
			String code;
			// Iterating through result set
			while (rs.next()) {
				code = rs.getString("airportcode");
				return code;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/*
	 * This function fills the airport drop-down list
	 * 
	 * @param combo Specifies the drop-down list
	 */
	public static void fillAirlines(Combo combo){
		// Creating a Statement object
		Statement st;
		try {
			// Creating a result set
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT name FROM airlines");
			
			// Iterating through result set
			while (rs.next()) {	
				combo.add(rs.getString("name"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This function checks if there are any available flights between 
	 * the 2 given airports
	 * 
	 * @param list The list which will be filled
	 * @param air1 The first airport
	 * @param air2 The second airport
	 */
	public static void checkFlights(List list, String air1, String air2){
		// Creating Statement objects
		Statement st1;
		Statement st2;
		try {
			// Creating a result set
			st1 = conn.createStatement();
			st2 = conn.createStatement();
			ResultSet rs1 = st1.executeQuery("SELECT * FROM flights WHERE departure_airport='" + air1 + "' AND destination_airport='" + air2 + "'");
			// Optional: SELECT name FROM airports WHERE airportcode="AAA" OR airportcode="AAB";
			ResultSet rs2 = st2.executeQuery("SELECT name FROM airports WHERE airportcode IN ('" + air1 + "', '" + air2 + "')");
			
			
			String[] airports = {"", ""};
			int count = 0;
			// Iterating through the second result set
			while (rs2.next()) {	
				airports[count] = rs2.getString("name");
				count++;
			}
			
			
			String airline;
			String flightnr;
			// Iterating through the first result set
			while (rs1.next()) {	
				airline = rs1.getString("airline");
				flightnr = rs1.getString("flightnr");
				
				list.add(airline + " " + flightnr 
						+ " - Departure: " + airports[0] + " " + convertDate(rs1.getString("departure_time")) + " - "
						+ " - Destination: " + airports[1] + " " + convertDate(rs1.getString("destination_time")));
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This function returns a specific date-part
	 */
	public static String convertDate(String date){
		String[] split = date.split("-| |:");
		return split[2] + "." + split[1] + "." + split[0] + " " + split[3] + ":" + split[4];
	}
	
	/*
	 * This function "refreshes" the output window
	 * 
	 * @param t The textfield
	 * @param oC origin country
	 * @param dC destination country
	 * @param oA origin airport
	 * @param dA destination airport
	 */
	public static void refreshOutput(Text t, String oC, String dC, String oA, String dA){
		t.setText("_________________ FLIGHT INFORMATION _________________\n\n\n\n\n"
				+ "--- Origin\n\nCountry: " + oC 
				+ "\nAirport: " + oA 
				+ "\n\n\n--- Destination\n\nCountry: " + dC 
				+"\nAirport: " + dA 
				+ "\n\n\n--- Flight\n\nBooked for: "
				+ "\nNr.: " + "Airline:" 
				+ "\nDeparture: " 
				+ "\nDestiantion: " 
				+ "\nAirplane: ");

	}
	
	/*
	 * Checks if the string contains only letters
	 * 
	 * @param name The string which will be checked
	 */
	public static boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
	}
}

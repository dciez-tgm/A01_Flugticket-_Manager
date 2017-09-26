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

public class MainWindow {

	protected Shell shlFlightPlanner;
	private Text tOutput;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
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
	 */
	protected void createContents() {
		shlFlightPlanner = new Shell();
		shlFlightPlanner.setSize(750, 500);
		shlFlightPlanner.setText("Flight Planner\r\n");
		
		tOutput = new Text(shlFlightPlanner, SWT.BORDER | SWT.MULTI); //SWT.MULTI for multiple line output
		tOutput.setText("Flight Information:" + "\n");
		tOutput.setFont(SWTResourceManager.getFont("Courier", 10, SWT.NORMAL));
		tOutput.setEditable(false);
		tOutput.setBounds(475, 161, 224, 268);
		//tOutput.append(""); to add text
		tOutput.append("---   Origin    ---" + "\n");
		tOutput.append("Country: " + "\n");
		tOutput.append("Airport: " + "\n");
		tOutput.append("--- Destination ---" + "\n");
		tOutput.append("Country: " + "\n");
		tOutput.append("Airport: " + "\n");
		
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
		
		ComboViewer comboViewer = new ComboViewer(shlFlightPlanner, SWT.DROP_DOWN | SWT.BORDER);
		Combo comboOriginCountry = comboViewer.getCombo();
		comboOriginCountry.setBounds(25, 185, 200, 23);
		//comboOrigin.add(""); too add items to the dropdownlist
		/*
		for(int i=1; i<121; i++){
			comboOrigin.add("Item " + i);
		}
		*/
		
		ComboViewer comboViewer_1 = new ComboViewer(shlFlightPlanner, SWT.DROP_DOWN | SWT.BORDER);
		Combo comboDestinationCountry = comboViewer_1.getCombo();
		comboDestinationCountry.setBounds(238, 185, 200, 23);
		
		Label lblAirportOrigin = new Label(shlFlightPlanner, SWT.NONE);
		lblAirportOrigin.setText("Airport");
		lblAirportOrigin.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblAirportOrigin.setBounds(25, 232, 110, 18);
		
		Label lblAirportDestination = new Label(shlFlightPlanner, SWT.NONE);
		lblAirportDestination.setText("Airport");
		lblAirportDestination.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		lblAirportDestination.setBounds(238, 232, 110, 18);
		
		ComboViewer comboViewer_2 = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboOriginAirport = comboViewer_2.getCombo();
		comboOriginAirport.setBounds(25, 256, 200, 23);
		
		ComboViewer comboViewer_3 = new ComboViewer(shlFlightPlanner, SWT.NONE);
		Combo comboDestinationAirport = comboViewer_3.getCombo();
		comboDestinationAirport.setBounds(238, 256, 200, 23);
		
		Label lblPassenger = new Label(shlFlightPlanner, SWT.NONE);
		lblPassenger.setText("Passenger");
		lblPassenger.setFont(SWTResourceManager.getFont("System", 17, SWT.NORMAL));
		lblPassenger.setBounds(25, 297, 160, 35);
		// Passenger Arguments:
		// id(autoincrement), First Name, Last Name, Airline, Flight Number, Rownumber, Seatposition

	}
}

import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Rakshith Kunchum
 *
 */
public class Logger {
	private static Logger instance;
	private ArrayList<CustomerEntry> DinerLogs;
	public String header = "Diner\t" +"Arrived" + "Seating\t" +"Table-ID" +"Cook-ID\t" +"Food\t" + 
			"Leaving\t"+"BTime\t"+"FTime\t"+"CTime\t"+"STime";
	
	private Logger() {
		
	}
	
	/**
	 * @param numberOfDiners
	 */
	public void initialize(int numberOfDiners) {
		DinerLogs = new ArrayList<CustomerEntry>();
		for(int i=0; i<numberOfDiners; i++) {
			DinerLogs.add(new CustomerEntry());
		}
	}
	
	/**
	 * @return
	 */
	public static Logger getStaticInstance() {
		if(instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	/**
	 * @return
	 */
	public ArrayList<CustomerEntry> getOutputData() {
		return DinerLogs;
	}
}
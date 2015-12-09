/**
 * Emulator class to emulate the running of the restaurant.
 */

/**
 * @author Jhansi
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Emulator {
	
	private final int MAX_TIME=120;
	private Diners diners;
	private Cooks cooks;
	private Tables tables;
	private Timer timer;
	private OutputLogger outputLogger;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Input file name argument is missing. Please run in the following format : " +
					"java Emulator <InputFile name>");
			System.exit(0);
		}
		Emulator restaurant = parseInput(args[0]);
		restaurant.runSimulation();
		restaurant.printOutput();
	}

	/**
	 * @param string
	 */
	private static Emulator parseInput(String inputFilename) {
		Emulator restaurant = null;
		try{
			FileReader inputFile = new FileReader(inputFilename);
			BufferedReader inputReader = new BufferedReader(inputFile);
			restaurant = new Emulator(Integer.parseInt(inputReader.readLine().trim()),
					Integer.parseInt(inputReader.readLine().trim()), Integer.parseInt(inputReader.readLine().trim()));
			String line;
			int orderNumber = 0;
			while((line = inputReader.readLine()) != null){
				String orderLine[] = line.split("\\s+");
				int arrivalTimeStamp = Integer.parseInt(orderLine[0].trim());
				DinerOrder newDinerOrder = new DinerOrder(Integer.parseInt(orderLine[1].trim()), 
						Integer.parseInt(orderLine[2].trim()), Integer.parseInt(orderLine[3].trim()), Integer.parseInt(orderLine[4].trim()));
				restaurant.diners.addDiner(orderNumber, new Diner(arrivalTimeStamp,newDinerOrder,orderNumber));
				orderNumber++;
			}
			inputReader.close();
		}catch(Exception ex){
			System.out.println("Issue with opening/reading the input file.");
		}
		return restaurant;
	}

	private void runSimulation() {
		while(timer.getTime() <= MAX_TIME || Diners.getStaticInstance().getNumberOfCurrentDiners() > 0) {
			diners.startDinersArrivedNow();
			timer.increment(); 
			synchronized(timer) {
				timer.notifyAll();
			}
		}
	}
	
	/**
	 * return null
	 */
	private void printOutput() {
		String str;
		str = outputLogger.header;
		System.out.println(str);
		ArrayList<DinerEntry> entry = outputLogger.getOutputData();
		for(int i=0; i<entry.size(); i++) {
			str = 	i + "\t" + entry.get(i).toString();
			System.out.println(str);
		}
	}
	
	/**
	 * @param numberOfDiners
	 * @param numberOfCooks
	 * @param numberOfTables
	 */
	public Emulator(int numberOfDiners, int numberOfTables, int numberOfCooks) {
		timer = Timer.getStaticInstance();
		tables = Tables.getStaticInstance();
		tables.initialize(numberOfTables);
		diners = Diners.getStaticInstance();
		diners.initialize(numberOfDiners);
		cooks = Cooks.getStaticInstance();
		cooks.initialize(numberOfCooks);
		outputLogger = OutputLogger.getStaticInstance();
		outputLogger.initialize(numberOfDiners);
	}
}

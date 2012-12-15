/*
 * Driver class for the TSP problem
 * Takes input via clicks on StdDraw or from file 
 * type help in command line for options.
 * Also performs some drawing simulations and reports some statistics 
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class driver {

	public static void main(String[] args) throws FileNotFoundException{
		
		Scanner keyboard = new Scanner(System.in);
		String fileName = "";
		String option = "";
		
		if(args.length != 0){
			fileName = args[0];
		}else{
			System.out.print("1. Input from file \n2. Create nodes on gui");
			option = keyboard.next();
			if (option.equals("1")){
				fileName = keyboard.next();
				
				File file = new File(fileName);
				Scanner fileScanner = new Scanner(file);

				ArrayList<Point> points = new ArrayList<Point>();
				while(fileScanner.hasNextDouble()){
					
					double x = fileScanner.nextDouble();
					double y = fileScanner.nextDouble();
					Point p = new Point(x,y);
					points.add(p);
				}
				

				Point pts[] = new Point[points.size()];
				points.toArray(pts);

				TSP tour = new TSP(pts);

				tour.execute(true, 100);
				
				fileScanner.close();

			} 
			
			else if(option.equals("2")){
				StdDraw.getInputPoints();
			} else {
				System.out.print("incorrect option, exiting");
				System.exit(0);
			}
		}
	}
}
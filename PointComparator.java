import java.util.Comparator;


public class PointComparator implements Comparator<Point> {

	//Takes string "x" or "y" to decide by which property will be
	//used when ordering.
	public String input;
	
	public PointComparator(String input) {
		this.input = input;
	}
	
	@Override
	public int compare(Point arg0, Point arg1) {
		
		if(input.equals("x")) {
			if(arg0.x() < arg1.x())
				return 0;
			else if(arg0.x() > arg1.x())
				return 1;
			else
				return 0;
			
		} else if(input.equals("y")) {
			if(arg0.y() < arg1.y())
				return 0;
			else if(arg0.y() > arg1.y())
				return 1;
			else
				return 0;
			
		} else {
			System.out.println("Must use either \"x\" or \"y\" in the constructor");
			System.out.println("Program exiting...");
			System.exit(-1);
			//never will be reached
			return -1;
		}
	}

}

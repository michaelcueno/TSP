/*
 * Creates random numbers based on command line arguments
 * arg0 = File Output name
 * arg1 = number of points
 * arg2 (optional) = lower bound  :: Default = 0.0
 * arg3 (optional) = upper bound  :: Default = 1.0
 * arg4 (optional) = precision    :: Default = 3 (0.000)
 */
import java.util.Random;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class RandNumGen {

	public static void main(String[] args) throws IOException{
	
		/*-----GET COMMAND LINE ARGUMENTS----------*/
		String fileName = args[0];
		int numpoints = Integer.parseInt(args[1]);
		
		Random r = new Random();
		
		FileWriter fout = new FileWriter(fileName);
		BufferedWriter sout = new BufferedWriter(fout);
		
		for(int i = 0 ; i < numpoints ; i++){
			sout.write(String.valueOf(r.nextDouble()));
			sout.write(" ");
			sout.write(String.valueOf(r.nextDouble()));
			sout.write("\n");
		}
		sout.close();
		
		
	}
	
	
}

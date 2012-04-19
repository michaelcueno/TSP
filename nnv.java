/*************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer < input.txt
 *                java Generator N | java NearestNeighborVisualizer
 *  Dependencies: StdDraw.java PointSet.java TDTree.java Point.java
 *
 *  Read points from standard input and draw to standard draw.
 *  Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 *************************************************************************/
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class nnv {

    public static void main(String[] args) throws FileNotFoundException {

	ArrayList<Point> points = new ArrayList<Point>();

	File file = new File(args[0]);
	Scanner fileScanner = new Scanner(file);

	while(fileScanner.hasNextDouble()){
		double x = fileScanner.nextDouble();
		double y = fileScanner.nextDouble();
		Point p = new Point(x,y);
		points.add(p);
	}
	
	Point pts[] = new Point[points.size()];
	points.toArray(pts);

        TDTree tree = new TDTree(pts);

        StdDraw.setCanvasSize(800, 800);
        StdDraw.show(0);

	tree.draw();
	StdDraw.show(0);
        StdDraw.setPenRadius();
	tree.preOrderPrint();
        // StdDraw.setPenRadius(.007);
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point query = new Point(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            tree.draw();

            // draw in red the nearest neighbor according to the brute-force algorithm
            // StdDraw.setPenRadius(.03);
            // StdDraw.setPenColor(StdDraw.RED);
            tree.nearest(query).draw(StdDraw.RED);
            // StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor according to the kd-tree algorithm
            StdDraw.setPenColor(StdDraw.BLUE);
            // tdt.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }
}

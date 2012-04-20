/********************************************************
 * TRAVELING SALESMAN PROBLEM (TSP) 			*
 * Class: CS 202 (Bonus project)			*
 * Author: Michael Cueno				*
 * Function: 						*
 * 	Finds a Minimum Spanning Tree (MST) for a set	*
 * 	of points in 2-d space (cities for example)	*
 * 	and gives an optimized tour of the points	*
 * Input:						*
 * 	command line file 				*
 * 	Format = 2D points seperated by commas		*
 *							*
 ********************************************************/

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class TSP{



	public static void main(String[] args) throws FileNotFoundException{

		ArrayList<Point> points = new ArrayList<Point>();
		

		//Get points from arg
		File file = new File(args[0]);
		Scanner fileScanner = new Scanner(file);

		while(fileScanner.hasNextDouble()){
			double x = fileScanner.nextDouble();
			double y = fileScanner.nextDouble();
			Point p = new Point(x,y);
			points.add(p);
		}
		
		Point[] pts = new Point[points.size()];
		points.toArray(pts);

		System.out.println("pts: " + points);
		TDTree pointTree = new TDTree(pts);
		
		pointTree.preOrderPrint(pointTree.root);
		pointTree.draw();
		StdDraw.show(1000);
		StdDraw.circle(.1, .6, .007);
		System.out.println(pointTree.nearest(.1,.6));
		StdDraw.show(0);

		//Load points into TDTREE

		//Find MST of points
		
		//Find tour

	}
}

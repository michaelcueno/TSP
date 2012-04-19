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

import java.util.Scanner;
import java.io.File;


public class TSP{



	public static void main(String[] args){

		ArrayList<Point> points = new ArrayList<Point>();
		TDTree pointTree = new TDTree();

		//Get points from arg
		File file = new File(arg[0]);
		Scanner fileScanner = new Scanner(file);
		fileScanner.useDelimiter(",");

		while(fileScanner.hasNext()){
			double x = fileScanner.nextDouble();
			double y = fileScanner.nextDouble();
			Point p = new Point(x,y);
			points.add(p);
		}
		
		Point[] pts = points.toArray();

		TDTree.buildBalanced(pts);

		//Load points into TDTREE

		//Find MST of points
		
		//Find tour

	}
}

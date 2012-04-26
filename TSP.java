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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class TSP{

	TDTree<Point> tree;
	LinkedList<Point> MST;
	PriorityQueue<Point> q;
	int[] index;   //for quick indexing into priority queue

	public TSP(Point[] pts){

		tree = new TDTree(pts);
		MST = new LinkedList();
		q = new PriorityQueue();
		index = new int[pts.length] 
	}

	public void execute(){
	
		//pick arbitrary point
		//find nearest neighbor
		//add to MST
		//add to q
		//find nearest neighbors to points in MST
		//if next nearest 

	}




}




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

	TDTree tree;		// 2-d Tree containing points to construct tour from
	LinkedList<Edge> MST;	// Minimum spanning tree of points
	PriorityQueue<Point> q;	// priority queue used in single fragment algorithm (this algorithm)
	int numPoints;		// number of points in the problem instance
	Edge top;		// for referencing top element in q
	Edge real;		// for referencing newly created edge
	Point x;		// Arbitrary point 
	Point y;		// Typically x's nearest isolated neighbor


	/**
	 * Factory constructor for TSP instance
	 */
	public TSP(Point[] pts){
	
		tree = new TDTree(pts);
		MST = new MST();
		q = new PriorityQueue<Edge>();
		numPoints = tree.size; 	
		top = new Edge();
		real = new Edge();	
		x = new Point();
		y = new Point();
	}

	public void execute(){
	
		//pick arbitrary point
		x = tree.getMin();

		//find nearest neighbor
		y = tree.nearest(s);
		real = new Edge(0, x, y);

		//add to q
		q.add(real);

		//for n-1 nodes
		for(int i = 0; i < numPoints-1 ; i++){
			
			//loop until top node in q is real
			while( !mst.realityCheck(q.peek()) ){

				Edge top = p.peek();
				y = tree.nearestIsolated(top.getParent()); 

				// update edge which triggers a priority queue percolate up
				top.update( y );			
				// un-isolate y
			}
	
			top = q.peek(); 
			MST.add(top);

			// Once added to the MST, we no longer need the point in the 2DTree
			tree.delete(top.getParent());
			tree.delete(top.getChild());

			//find nearest neighbor to point just added to MST
			y = tree.nearestIsolated(top.getChild()); 
			real = new Edge( top.getChild, y );
			q.add(real);	

		}

	}

}




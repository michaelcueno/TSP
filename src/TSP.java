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
 *	
 *	DEPENDANCIES:						*
 *		-StdDraw.java
 *		-MST.java
 *		-Edge.java; EdgeComparator.java
 *		-Point.java
 *		-TDTree.java
 *		-TSP.java
 ********************************************************/

import java.util.Comparator;
import java.util.PriorityQueue;


public class TSP{

	TDTree tree;		// 2-d Tree containing points to construct tour from
	MST mst;	// Minimum spanning tree of points
	PriorityQueue<Edge> q;	// priority queue used in single fragment algorithm (this algorithm)
	int numPoints;		// number of points in the problem instance
	Edge top;		// for referencing top element in q
	Edge real;		// for referencing newly created edge
	Point x;		// Arbitrary point 
	Point y;		// Typically x's nearest isolated neighbor
	Comparator<Edge> comparator;

	/**
	 * Constructor for TSP instance
	 */
	public TSP(Point[] pts){
		
		comparator = new EdgeComparator();
		tree = new TDTree(pts);
		mst = new MST();
		numPoints = tree.size; 	
		q = new PriorityQueue<Edge>(numPoints, comparator);

		System.out.println("TSP constructed");
	}

	public void execute(){
		execute(false, 500);
	}
	
	public void execute(boolean draw){
		execute(draw, 500);
	}
	
	public void execute(boolean draw, int delay){
	
		if(draw){StdDraw.setCanvasSize(800, 800);}
		//pick arbitrary point
		x = tree.getMin();
		
		tree.deleteSlow(x);
		//find nearest neighbor
		y = tree.nearest(x);
		
		real = new Edge(x.distanceTo(y), x, y);
		
		//-----------Draw option----------//
		if(draw){ drawEdge(real, delay); }
		
		//add to q
		q.offer(real);

		//for n-1 nodes
		for(int i = 0; i < numPoints-1 ; i++){
			
			//loop until top node in q is real
			while( !mst.realityCheck(q.peek()) ){

				Edge top = q.poll();
				y = tree.nearest(top.getParent()); 

				
				// update edge which triggers a priority queue percolate up
				top.update( y );
				q.offer(top);
				// un-isolate y
			}
	
			top = q.peek(); 
			mst.add(top);

			if(draw){ draw(delay); }
			
			// Once added to the MST, we no longer need the point in the 2DTree
		
			tree.deleteSlow(top.getChild());  //TODO there is a bug in delete : last node not in correct subtree

			//find nearest neighbor to point just added to MST
			y = tree.nearest( top.getChild()); 
			if(y != null){
				real = new Edge( top.getChild(), y );
				if(draw){ drawEdge(real, delay); }
				q.offer(real);	
			}else{
				// No more nodes in tree so we are done!
			}
				
		}
		if(draw){StdDraw.clear();draw(0);}

	}

	public void draw(int delay){
		tree.drawPoints();
		mst.draw();
		StdDraw.show(delay);
	}
	
	public void drawTree(int delay){

		tree.draw();
		StdDraw.show(delay);

	}
	
	public void drawMST(int delay){

		mst.draw();
		StdDraw.show(delay);

	}
	
	public void drawX(Point x, int delay){
		StdDraw.clear();
		tree.drawPoints();
		mst.drawPoints();
		StdDraw.setPenColor(StdDraw.BLUE); 
		StdDraw.circle(x.x(), x.y(), .01);
		StdDraw.show(delay);
	}

	public void drawY(Point x, int delay){
		StdDraw.clear();
		tree.drawPoints();
		mst.drawPoints();
		StdDraw.setPenColor(StdDraw.GREEN); 
		StdDraw.circle(x.x(), x.y(), .01);
		StdDraw.show(delay);
	}

	public void drawEdge(Edge e, int delay){
		tree.drawPoints();
		mst.drawPoints();
		StdDraw.setPenColor(StdDraw.CYAN); 
		StdDraw.line(e.getParent().x(), e.getParent().y(), e.getChild().x(), e.getChild().y());
		StdDraw.show(delay);
	}
	

}




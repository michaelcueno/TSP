/*
 * Simple Graph class. Holds parameterized values of T in a 
 * HashMap. Uses a dependency list implementation. 
 * 		-Depends on Vertex.java
 */

import java.util.HashMap;
import java.util.Stack;

public class MST {

	public ArrayList<Edge> edges = new ArrayList<Edge>();
	public ArrayList<Point> points = new ArrayList<Point>();  // For checking if point is in MST

	public boolean add( Edge e ){

		edges.add(e);
		Point x = e.getParent();
		Point y = e.getChild();

		if(!points.contains(x)){
			points.add(x);
		}
		if(!points.contains(y)){
			points.add(y);
		}

	}

	/**
	 * Checks if the edge is real; An edge is not real iff the 
	 * edge's child point is already in the MST
	 */
	public boolean realityCheck( Edge e ){
	
		Point child = e.getChild();
		if( points.contains(child) ){
			return false;
		}else
			return true;

	}

}

/*
 * Simple Graph class. Holds parameterized values of T in a 
 * HashMap. Uses a dependency list implementation. 
 * 		-Depends on Vertex.java
 */
import java.util.ArrayList;

public class MST {

	public ArrayList<Edge> edges;
	public ArrayList<Point> points;  // For checking if point is in MST

	public MST(){
		edges = new ArrayList<Edge>();
		points = new ArrayList<Point>();
	}
	public void add( Edge e ){

		Edge E = new Edge(e.dist, e.getParent(), e.getChild());
		edges.add(E);
		Point x = e.getParent();
		Point y = e.getChild();

		
		//TODO Possible logical flaw here if duplicate points are used
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
			return false;  // link is not real
		}else
			return true;   // link is real

	}
	
	public void draw(){
		
		for(int i = 0; i < edges.size(); i++){
		
			Edge e = edges.get(i);
			
			/*---------Draw edge points --------------*/
			Point parent = e.getParent();
			StdDraw.circle(parent.x(), parent.y(), .005);
			Point child = e.getChild();
			StdDraw.circle(child.x(), child.y(), .005);
			
			/*--------Draw line representing edge-----*/
			StdDraw.line(parent.x(), parent.y(), child.x(), child.y());
			
		}
		
	}
	
	public void drawPoints(){
		
		for (int i = 0; i < points.size(); i++){
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.circle(points.get(i).x(), points.get(i).y(), .005);
		}
	}
	

}

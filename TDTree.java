/********************************************************
 *  Two Dimensional Tree for storing points for the 	*
 *  Traveling Salesman Problem 				*
 *  Not fully functional tree 				*
 *  	-no delete method				*
 *  	-not a rebalancing tree				*
 *							*
 ********************************************************/
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.Double;

public class TDTree {

	Node root;
	int size;
	int height;
	Double minX, minY, maxX, maxY;
	private final double LOWERBOUND = -2;
	private final double UPPERBOUND =  2;

	//Nested Node Class

	private static class Node{
		
		Point value;
		Node lt;
		Node rt;
		int depth;
		int height;
		int size;
		double minX, minY, maxX, maxY; 
		Node(Point value){
			this(value, null, null, 0, 0);
		}
		Node(Point value, Node lt, Node rt, int depth, int height){
			this.value = value;
			this.lt = lt;
			this.rt = rt;
			this.depth = depth;
			this.height = height;
			
		}
		public void setLt(Node t){
			if(t == null){
				this.lt = null;
			}else
			t.depth = this.depth + 1; 
			this.lt = t;
		}
		public void setRt(Node t){
			if(t == null){
				this.rt = null;
			}else
			t.depth = this.depth + 1;
			this.rt = t;
		}
		public String toString() {
			return "(" + this.value.x() + ", " + this.value.y() + ")";
		}
	}

  // Constructors ------------------//
  // Point array must be sorted previously (low to high)
  public TDTree(Point [] pts){
    this();
    root = buildBalanced(pts);
  }
  public TDTree() {
	  root = null;
	  size = 0;
	  height = -1;
	  maxX = maxY = minX = minY = null;
  }

  // Simple Statistic methods -----//
  public int size() {
    return size(root);
  }
  public int height() {
    return height;
  }
  public Double minX(){
    return minX;
  }
  public Double minY(){
    return minY;
  }
  public Double maxX(){
    return maxX;
  }
  public Double maxY(){
    return maxY;
  }
 
  /*
   * buildBalanced requires a sorted Point[] array to be passed in
   * if the array is not sorted, the method breaks
   * Sort once. Pass in one array double sized x sorted on left, y sorted on right
   * book keeping to build the tree
   */ 
  
  public Node buildBalanced(Point[] pts){

	  Point[] sorted = new Point[pts.length*2];
	  Arrays.sort(pts, new PointComparator("x"));
	  for(int i = 0 ; i < pts.length ; i++ ){
		  sorted[i] = pts[i];
	  }
	  Arrays.sort(pts, new PointComparator("y"));
	  for(int j = pts.length ; j < sorted.length ; j++ ){
		  sorted[j] = pts[j];
	  }
	  
	  return buildBalanced( sorted, root, 0 );
  }

  // It's just a matter of inserting in the correct order. 
  // always insert the median followed by the median of the left and right partition
  //  ------------x----------X-----------x---------- X dimension
  //  -----x-----------x-----------x----------x----- Y dimension
  public Node buildBalanced(Point[] pts, Node t, int depth){
  
	  if(pts.length == 0){
		  return null;
		  
	  }else if(pts.length == 1){
		  t = new Node(pts[0]);
		  t.depth = depth;
		  
	  }else if(depth%2 == 0){
		  Arrays.sort(pts, new PointComparator("x"));
		  t = new Node( pts[pts.length/2] );
		  t.depth = depth;
		  int size = pts.length;
		  int mid = size/2;
		  Point[] left = Arrays.copyOfRange(pts, 0, mid);
		  Point[] right = Arrays.copyOfRange(pts, mid+1, size);
		  t.lt = (buildBalanced(left, t.lt, depth+1));
		  t.rt = (buildBalanced(right, t.rt, depth+1));
		  
	  }else{
		  Arrays.sort(pts, new PointComparator("y"));
		  t = new Node( pts[pts.length/2] );
		  t.depth = depth;
		  int size = pts.length;
		  int mid = size/2;
		  Point[] left = Arrays.copyOfRange(pts, 0, mid);
		  Point[] right = Arrays.copyOfRange(pts, mid+1, size);
		  t.lt = (buildBalanced(left, t.lt, depth+1));
		  t.rt = (buildBalanced(right, t.rt, depth+1));
	
	  }
	  
	  t.size = size(t.lt) + size(t.rt) + 1;
	  t.height = Math.max(height(t.lt), height(t.rt)) + 1;
	  return t;
  }
  
  public boolean insert(Point pt) {
	if(contains(pt)){
		return false;
	}else
		updateStats(pt);
		if(root == null){
			height = 0;
		}
		root = insert(pt, root);
		size++;
		return true; 
  }

  public boolean insert(double x, double y) {
    return insert(new Point(x,y));
  }
  
  private Node insert(Point p, Node t){
	updateStats(p);
  	if(t == null){  // Base Case
  		t = new Node(p);
  		
  	}else if(t.depth%2 != 0){ // Split on Y coordinate
  		
  		if(p.compareY(t.value) < 0){ // insert to the left
  			t.setLt(insert(p, t.lt));
  			
  		}else if(p.compareY(t.value) > 0){ // insert to the right
  			t.setRt(insert(p, t.rt));
  			
  		}else{  //Tie breaker ( sort on x ) 
  			if(p.compareX(t.value) < 0){  // insert to the left
  	  			t.setLt(insert(p, t.lt));
  	  			
  	  		}else if(p.compareX(t.value) > 0){  // insert to the right
  	  			t.setRt(insert(p, t.rt));
  	  		}
  		}
  	}else if(t.depth%2 == 0){  // Split on X coordinate
  		if(p.compareX(t.value) < 0){  // insert to the left
  			t.setLt(insert(p, t.lt));
  			
  		}else if(p.compareX(t.value) > 0){  // insert to the right
  			t.setRt(insert(p, t.rt));
  			
  		}else{  // tie breaker (sort on y)
  			if(p.compareY(t.value) < 0){ // insert to the left
  	  			t.setLt(insert(p, t.lt));
  	  			
  	  		}else if(p.compareY(t.value) > 0){ // insert to the right
  	  			t.setRt(insert(p, t.rt));
  	  		}
  		}
  	}
	t.size = size(t.lt) + size(t.rt) + 1;
	t.height = Math.max(height(t.lt), height(t.rt)) + 1;
	
	return t;
  }

  public boolean contains(Point p){
    return contains(p, root);
  }
  
  public boolean contains(double x, double y) {
    return contains(new Point(x,y));
  }
  
  public boolean contains(Point p, Node t){
	if(t == null){
		return false;
	}
	else if(t.depth%2 != 0){
  		
  		if(p.compareY(t.value) < 0){ // insert to the left
  			return contains(p, t.lt);
  			
  		}else if(p.compareY(t.value) > 0){ // insert to the right
  			return contains(p, t.rt);
  			
  		}else{  //Tie breaker ( sort on x ) 
  			if(p.compareX(t.value) < 0){  // insert to the left
  	  			return contains(p, t.lt);
  	  			
  	  		}else if(p.compareX(t.value) > 0){  // insert to the right
  	  			return contains(p, t.rt);
  	  		}else{
				return true;
			}
  		}
  	}else{
  		if(p.compareX(t.value) < 0){  // insert to the left
  			return contains(p, t.lt);
  			
  		}else if(p.compareX(t.value) > 0){  // insert to the right
  			return contains(p, t.rt);
  			
  		}else{  // tie breaker (sort on y)
  			if(p.compareY(t.value) < 0){ // insert to the left
  	  			return contains(p, t.lt);
  	  			
  	  		}else if(p.compareY(t.value) > 0){ // insert to the right
  	  			return contains(p, t.rt);
  	  		}else{
				return true;
  			}
		}	
  	}
	}

  /*
   * Prints in pre-order. Indents based on depth in tree.
   */
   public void preOrderPrint(){
	   preOrderPrint(root);
   }

   public void preOrderPrint(Node t){
	  if(t == null){
		  return;
	  }
	  String alignment;
	  if(t.depth%2 == 0){
		  alignment = "vertical";
	  }else{
		  alignment = "horizontal";
	  }
	  for(int i = 0; i < t.depth; i++){ 
		  System.out.print("\t");
	  }
	  System.out.println(t.value + "  " + alignment + " cut" );
	  preOrderPrint(t.lt);
	  preOrderPrint(t.rt);
  }


  /**
  * Draw Method: Recursive Structure
  */
  public void draw(){
	draw(root, UPPERBOUND, UPPERBOUND, LOWERBOUND, LOWERBOUND); 
  }
  
  public void draw(Node t, double maxX, double maxY, double minX, double minY){
	if( t == null ) {return; }

//      Uncomment next line to toggle coordinate points (xx.xx, xx.xx) to be drawn
        StdDraw.text(t.value.x(), t.value.y(), t.value.toString());

	StdDraw.circle(t.value.x(), t.value.y(), .005);
	if( t.depth%2 == 0 ){
		StdDraw.line(t.value.x(), maxY, t.value.x(), minY); 
		draw(t.lt, t.value.x(), maxY, minX, minY);
		draw(t.rt, maxX, maxY, t.value.x(), minY);
	}else{ 
		StdDraw.line(maxX, t.value.y(), minX, t.value.y());
		draw(t.lt, maxX, t.value.y(), minX, minY);
		draw(t.rt, maxX, maxY, minX, t.value.y());
	}
  }	
	
  /**
   * Nearest Point method: returns the point with the least euclidean distance
   * to the point passed in (p)
   * Returns null if tree is empty
  */
  public Point nearest(Point p) {
     if(size == 0 || root == null){
    	 return null;
     }else 
    	 return nearest(p, root);
  }

  public Point nearest(double x, double y) {
     return nearest(new Point(x,y));
  }

  /**
   * Algorithm (Nearest Neighbor):
   * 	1: Recurse down the tree as if inserting point p
   * 	2: when leaf is found, compute distance and update as best
   * 	3: roll out of the recursion and at each node
   * 		decide if other subtree must be searched by finding 
   * 		distance to axis and if this is greater than best do not search
   * 		else
   * 		search the subtree using this algorithm
   * 	4: when root is reached, return p
   */
  public Point nearest(Point p, Node t){
	 
	  if(t== null){
		  return null;
	  }
	  Point candidate = t.value;
	  double currentDist = p.distanceTo(t.value);
	  
	  // Must be a more elegant way to do this, but all we are doing here
	  // is finding leaf node closest to p
	  int cmpX = p.compareX(t.value);
	  int cmpY = p.compareY(t.value);
	  if(t.depth%2 != 0){
		  if(cmpY < 0){ 
			 
				  candidate = nearest(p, t.lt);

		  }else if(cmpY > 0){ 
			  
				  candidate = nearest(p, t.rt);
			
		  }else{  //Tie breaker ( sort on x ) 
			  if(cmpX < 0){  
				  
				  candidate = nearest(p, t.lt);
	  			
			  }else if(cmpX > 0){  
				  
				  candidate = nearest(p, t.rt);
			  }else
				  return p; // nearest point is always closest to itself..
		  }
	  }else if(t.depth%2 == 0){
		 if(cmpX < 0){  
			  
			 candidate = nearest(p, t.lt);
				   
		  }else if(cmpX > 0){ 
			  
			  candidate = nearest(p, t.rt);
			
		  }else{  // tie breaker (sort on y)
			  if(cmpY < 0){ 
				  
				  candidate = nearest(p, t.lt);
	  			
			  }else if(cmpY > 0){
				  
				  candidate = nearest(p, t.rt);
			  }else
				  return p;
		  }
	  }
	  // End of finding leaf node
	  // If candidate is null, then t is current best (leaf node)
	  
	  if(candidate == null){
		  candidate = t.value;     
	  }
	  
	  if( currentDist < candidate.distanceTo(p)){
		  candidate = t.value;
	  }
	  Point tmpCandidate = candidate;
	  double bestDistSoFar = p.distanceTo(candidate);
	  
	  if(t.depth%2 == 0){
		  if(p.compareX(t.value) < 0){
			  if(p.distanceTo( new Point(t.value.x(), p.y())) < bestDistSoFar ){
				  candidate = nearest(p, t.rt);
			  }
		  }else
			  
			  if(p.distanceTo( new Point(t.value.x(), p.y())) < bestDistSoFar){
				  candidate = nearest(p, t.lt);
			  }
	  }else{
		  if(p.compareY(t.value) < 0){
			  if(p.distanceTo( new Point(p.x(), t.value.y())) < bestDistSoFar ){
				  candidate = nearest(p, t.rt);
			  }
		  }else
			  
			  if(p.distanceTo( new Point(p.x(), t.value.y())) < bestDistSoFar){
				  candidate = nearest(p, t.lt);
			  }
	  }
	
	  if( candidate == null){
		  candidate = tmpCandidate;
	  }
	  
	  if( p.distanceTo(tmpCandidate) < candidate.distanceTo(p)){
		  candidate = tmpCandidate;
	  }
	  
	  return candidate;
	  
	  
  }


  private int size(Node t){
	int size = (t == null)? -0: t.size;
	return size;
  }
  
  private int height(Node t){
	int height = (t == null)? -1: t.height;
	return height;
  }
  
  private void updateStats(Point pt) {
	  if(maxX == null){ maxX = pt.x();}
	  if(maxY == null){ maxY = pt.y();}
	  if(minX == null){ minX = pt.x();}
	  if(minY == null){ minY = pt.y();}
	  if(pt.x() > maxX){	
		  maxX = pt.x();
	  }else if(pt.x() < minX){
		  minX = pt.x();
	  }
	  if(pt.y() > maxY){
		  maxY = pt.y();
	  }else if( pt.y() < minY){
		  minY = pt.y();
	  }
  }
	
  private Node findMin( Node t, int dim ){ 
	if( t == null ){
		return null;
	}else if(t.depth%2 == dim){
		if( t.lt == null )
			return t;
		else
			return findMin( t.lt, dim );
	}else 
		if(t.rt == null && t.lt == null)
			return t;
		else
			return minimum(findMin( t.rt, dim), findMin( t.lt, dim), dim);
  }
  
  private Node minimum(Node a, Node b, int dim){
	if(a == null)
		return b;
	else if(b == null)
		return a;
	else if(dim == 0)
		if(a.value.compareX(b.value) < 0)
			return a;
		else
			return b;
	else
		if(a.value.compareY(b.value) < 0)
			return a;
		else
			return b;
	
	}
	
	/*
	 * loads all points in tree into an arrayList O(n)
	 */
	public ArrayList<Point> toArrayList(Node t, ArrayList<Point> pts){
		if(t == null){
			return pts;
		}else
			pts.add(t.value);
			pts = toArrayList( t.lt, pts);
			pts = toArrayList( t.rt, pts);
		
		return pts;
	}
}

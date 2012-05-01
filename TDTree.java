/*  version: 1.3
 * 			
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.Double;

public class TDTree {

  // TDTree data members here.
	Node root;
	int size;
	int height;
	Double minX, minY, maxX, maxY;
	private final double LOWERBOUND = -2;
	private final double UPPERBOUND =  2;


  // nested classes (if any) could go here

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


  /*************** PART OF LEVEL 3 FUNCTIONALITY ****/
  /**
  * insert takes care of rebuilding
  *
  *   Given trivial implementation just calls insert
  *   
  *      for each point, but does not control balance
  *      of tree.
  */
  public TDTree(Point [] pts){
    this();
    root = buildBalanced(pts, root, 0);
  }
  

	  
  
  /*************** LEVEL 1 FUNCTIONALITY ************/
  /**
  * 
  *   default constructor creates an empty tree.
  */
  public TDTree() {
	  root = null;
	  size = 0;
	  height = -1;
	  maxX = maxY = minX = minY = null;
  }
  /**
  * 
  * runtime requirement:  O(1)
  */
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
   * Put all points in tree into list 
   * I went with the nlog^2n time algorithm
   */
  public Node reBalance(Node t){
	  

	  ArrayList<Point> list = new ArrayList<Point>();
	  list = toArrayList(t, list);  
	  Point[] pts = new Point[size(t)];
	  pts = list.toArray(pts);
	  int depth = t.depth;
	  t = null;
	  t = buildBalanced(pts, t, depth );
	  return t;
  }

  public Node buildBalanced2(Point[] pts, Node t, int depth){

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
		  t.lt = (buildBalanced2(left, t.lt, depth+1));
		  t.rt = (buildBalanced2(right, t.rt, depth+1));
		  
	  }else{
		  Arrays.sort(pts, new PointComparator("y"));
		  t = new Node( pts[pts.length/2] );
		  t.depth = depth;
		  int size = pts.length;
		  int mid = size/2;
		  Point[] left = Arrays.copyOfRange(pts, 0, mid);
		  Point[] right = Arrays.copyOfRange(pts, mid+1, size);
		  t.lt = (buildBalanced2(left, t.lt, depth+1));
		  t.rt = (buildBalanced2(right, t.rt, depth+1));
	
	  }
	  
	  t.size = size(t.lt) + size(t.rt) + 1;
	  t.height = Math.max(height(t.lt), height(t.rt)) + 1;
	  return t;
  }
  
   
  public Node buildBalanced(Point[] pts, Node t, int depth){

	  if(pts.length == 0){
		  return null;
		  
	  }else if(pts.length == 1){
		  t = new Node(pts[0]);
		  t.depth = depth;
		  size++;
		  
	  }else if(depth%2 == 0){
		  Arrays.sort(pts, new PointComparator("x"));
		  t = new Node( pts[pts.length/2] );
		  size++;
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
		  size++;
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
  
  /**
  * TODO
  * returns false if point pt already in tree
  */
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
	/*
	* TODO - implement check for unbalanced-ness
	*/

  public boolean insert(double x, double y) {
    return insert(new Point(x,y));
  }
  
  private Node insert(Point p, Node t){
	updateStats(p);
  	if(t == null){
  		t = new Node(p);
  		
  	}else if(t.depth%2 != 0){
  		
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
  	}else if(t.depth%2 == 0){
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
  	if(isSizedBalanced(t) != true) {
		System.out.println("Size violation at node at"+t);
		System.out.println("Rebuilding tree");
		t = reBalance(t);
	}
	t.size = size(t.lt) + size(t.rt) + 1;
	t.height = Math.max(height(t.lt), height(t.rt)) + 1;
	
	return t;
  }
  /**
  * Recrusive contains method 
  */
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

  /******************** LEVEL 2 FUNCTIONALITY ******/

  /**
  * Delete
  Algorithm:
	1. find which subtree is larger, tl or tr				
	2. depending on above, find min of tr or max of tl
	3. copy this value to node passed into delete method
	4. delete copied node in subtree
	5. check for balance (might be unnecessary... )
  */
  public boolean delete(Point p) {
	if(contains(p)){
		root = delete(root, p);
		size--;
		return true;
	}else
		return false;
  }
  /**
  * for convenience
  */
  public boolean delete(double x, double y) {
    return delete(new Point(x,y));
  }
  
  public Node delete(Node t, Point p){

	if(t == null){    // point doesn't exist
		return t;
	}
	int xcmp = p.compareX(t.value);
	int ycmp = p.compareY(t.value);
	if(t.depth%2 == 0){
  		
  		if(xcmp < 0){ 
  			t.lt = delete(t.lt, p);
  			
  		}else if(xcmp > 0){ 
  			t.rt = delete(t.rt, p);
  			
  		}else{  // tie breaker
  			if(ycmp < 0){  
  	  			t.lt = delete(t.lt, p);
  	  			
  	  		}else if(ycmp > 0){  
  	  			t.rt = delete(t.rt, p);
  	  		}else if(t.rt != null){          // Point found and its got a right tree
				
				t.value = findMin( t.rt, t.depth%2 ).value;           // Set node to delete to smallest value in rt
				t.rt = delete( t.rt, t.value );     	 // recursivley delete smallest value in rt	
			}else if(t.lt != null){						// 
				
				t.value = findMin( t.lt, t.depth%2).value;
				t.lt = null;
				t.rt = delete( t.lt, t.value);   // Swap subtrees to maintain structure
			}else
				t = (t.lt != null)? t.lt : t.rt;     
  		}
  	}else{
  		if(ycmp < 0){  
  			t.lt = delete(t.lt, p);
  			
  		}else if(ycmp > 0){  
  			t.rt = delete(t.rt, p);
  			
  		}else{   // tie breaker
  			if(xcmp < 0){ 
  	  			t.lt = delete(t.lt, p);
  	  			
  	  		}else if(xcmp > 0){ 
  	  			t.rt = delete(t.rt, p);
  	  		}else if(t.rt != null){          // Point found and its got a right tree
				
				t.value = findMin( t.rt, t.depth%2 ).value;           // Set node to delete to smallest value in rt
				t.rt = delete( t.rt, t.value );     	 // recursivley delete smallest value in rt	
			}else if(t.lt != null){						// 
				
				t.value = findMin( t.lt, t.depth%2).value;
				t.lt = null;
				t.rt = delete( t.lt, t.value);   // Swap subtrees to maintain structure
			}else
				t = (t.lt != null)? t.lt : t.rt;     
  		}
  	}
//	if(t != null){
//		if(isSizedBalanced(t) != true) {
//			System.out.println("Size violation at node at"+t);
//			System.out.println("Rebuilding tree");
//			t = reBalance(t);
//		}
//	}
	return t;
	
  }


  /**
  * find if horizontal or vertical cut
  * find if lt or rt of parent node
  * update lowerbound and upper bound
  * draw
  */
  public void draw(){
	draw(root, UPPERBOUND, UPPERBOUND, LOWERBOUND, LOWERBOUND); 
  }
  
  public void draw(Node t, double maxX, double maxY, double minX, double minY){
	if( t == null ) {return; }
//  StdDraw.text(t.value.x(), t.value.y(), t.value.toString());
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
	

  /***************** PART OF LEVEL 3 FUNCTIONALITY *****/
  /** Note that incremental enforcement of the size-balanced
  *     property is part of LEVEL 3.
  *   It requires modification to insert and delete.
  *******************************************************/
  /**
  * TODO
  *  returns point in tree closest to point p (by 
  *     Euclidean distance).
  *  if tree empty, null is returned.
  */
  public Point nearest(Point p) {
     if(size == 0 || root == null){
    	 return null;
     }else 
    	 return nearest(p, root);
  }

  /**
  * for convenience
  */
  public Point nearest(double x, double y) {
     return nearest(new Point(x,y));
  }


  /**
   * Algorithm (Nearest Neighbor):
   * 	1  Recurse down the tree as if inserting point p
   * 	2  when leaf is found, compute distance and update as best
   * 	3  roll out of the recursion and at each node
   * 		decide if other subtree must be searched by finding 
   * 		distance to axis and if this is greater than best do not search
   * 		else
   * 		search the subtree using this algorithm
   * 	4  when root is reached, return p
   */

  public Point nearest(Point p, Node t){
	 
	  if( t == null ){
		  return null;
	  }
	  
	  Point candidate = t.value;
	  double currentDist = p.distanceTo(t.value);
	  
	  /*---- Find leaf node in tree ---------------------------------*/
	  int cmpX = p.compareX(t.value);
	  int cmpY = p.compareY(t.value);

	  if(t.depth%2 != 0){

		  if(cmpY < 0){ 
			 
				  candidate = nearest(p, t.lt);

		  }else if(cmpY > 0){ 
			  
				  candidate = nearest(p, t.rt);
			
		  }else{  /*------Tie breaker ( sort on x )--------*/ 

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
	  /*----- End of finding leaf node------------------------------*/

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

  /****************** BONUS METHODS ***************/
  /**
  * TODO
  *
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   populates Collection<Point> result with points in the
  *     rectangle.
  */
  public void rangeQuery(Point sw, Point ne,
                            Collection<Point> result) {
    	System.out.println("TDTree unimplemented method:  rangeQuery()");
  }

  /**
  *  for convenience.  Just calls above method
  */
  public void rangeQuery(double minX, double maxX, 
                            double minY, double maxY, 
                            Collection<Point> result) {

	Point sw = new Point(minX, minY);
	Point ne = new Point(maxX, maxY);

	rangeQuery(sw, ne, result);
  }


  /**
  * TODO
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   returns number of points within the rectangle (but NOT
  *     the points themselves).
  *   
  */
  public int rangeSize(Point se, Point ne) {
    	System.out.println("TDTree unimplemented method:  rangeSize()");
	return 0;

  }
  public int rangeSize(double minX, double maxX, 
                            double minY, double maxY){

	Point sw = new Point(minX, minY);
	Point ne = new Point(maxX, maxY);

	return rangeSize(sw, ne);
  }

  /*
   * Helper methods
   *  *size(Node t) : returns number of nodes in subtrees of t
   *  *updateStats(Point p) : checks p with maxX, maxY, minX, minY and updates 
   *  		if new extreme found
   *  *height(Node t) : returns height of node but resolves null pointers for use in 
   *		insert method
   */
  
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
	
  /**
   * Returns the node in the lower-left corner
   */
  public Point getMin(){
	  return findMin(root, 0).value;
  }

  /**
   * returns the mininum element in the tree
   * based on the dimension passed in as 
   * @param dim
   */
  private Node findMin( Node t, int dim ){        // if dim == 0, find smallest x : else y
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
	  private Node findMax( Node t, int dim ){        // if dim == 0, find smallest x : else y
	if( t == null ){
		return null;
	}else if(t.depth%2 == dim){
		if( t.rt == null )
			return t;
		else
			return findMax( t.rt, dim );
	}else 
		if(t.lt == null && t.rt == null)
			return t;
		else
			return maximum(findMax( t.rt, dim), findMax( t.lt, dim), dim);
  }
  
  private Node maximum(Node a, Node b, int dim){
	if(a == null)
		return b;
	else if(b == null)
		return a;
	else if(dim == 0)
		if(a.value.compareX(b.value) > 0)
			return a;
		else
			return b;
	else
		if(a.value.compareY(b.value) > 0)
			return a;
		else
			return b;
	
	}
	private boolean isSizedBalanced(Node t) {
		return Math.max(size(t.lt), size(t.rt)) <= 2*Math.min(size(t.lt), size(t.rt))+1;
	}
	
	/////////// TEST CASES////////////////
	public void testFindMax(){
		Node testX = findMax(root, 0);
		Node testY = findMax(root, 1);
		if(testX != null)
			System.out.println("Max x = " + testX.value);
		if(testY != null)
			System.out.println("Max y = " + testY.value);
			
		testX = findMax(root.rt, 0);
		testY = findMax(root.rt, 1);
		if(testX != null)
			System.out.println("root.rt Max x = " + testX.value);
		if(testY != null)
			System.out.println("root.rt Max y = " + testY.value);
			
		testX = findMax(root.lt.lt, 0);
		testY = findMax(root.lt.lt, 1);
		if(testX != null)
			System.out.println("root.lt.lt Max x = " + testX.value);
		if(testY != null)
			System.out.println("root.lt.lt Max y = " + testY.value);
			
		testX = findMax(root.rt.lt.rt, 0);
		testY = findMax(root.rt.lt.rt, 1);
		if(testX != null)
			System.out.println("root.rt.lt.rt Max x = " + testX.value);
		if(testY != null)
			System.out.println("root.rt.lt.rt Max y = " + testY.value);
			
			
	
	}
	/////////// TEST CASES////////////////
	public void testFindmin(){
		Node testX = findMin(root, 0);
		Node testY = findMin(root, 1);
		if(testX != null)
			System.out.println("min x = " + testX.value);
		if(testY != null)
			System.out.println("min y = " + testY.value);
			
		testX = findMin(root.rt, 0);
		testY = findMin(root.rt, 1);
		if(testX != null)
			System.out.println("root.rt min x = " + testX.value);
		if(testY != null)
			System.out.println("root.rt min y = " + testY.value);
			
		testX = findMin(root.lt.lt, 0);
		testY = findMin(root.lt.lt, 1);
		if(testX != null)
			System.out.println("root.lt.lt min x = " + testX.value);
		if(testY != null)
			System.out.println("root.lt.lt min y = " + testY.value);
			
		testX = findMin(root.rt.lt.rt, 0);
		testY = findMin(root.rt.lt.rt, 1);
		if(testX != null)
			System.out.println("root.rt.lt.rt min x = " + testX.value);
		if(testY != null)
			System.out.println("root.rt.lt.rt min y = " + testY.value);
			
			
	
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


import java.awt.*;

/**
* Immutable class for points in the cartesian plane
**/

public class Point implements Comparable {

  private double _x;
  private double _y;

  public Point(double x, double y){
	_x = x;
	_y = y;
  }

  public double x(){
    return _x;
  }
  public double y(){
    return _y;
  }

  public String toString() {
    return "(" + _x + ", " + _y + ")";
  }

  
  /**
  * returns squared distance to Point p
  * 
  */
  public double squaredDistanceTo(Point p) {
     double dx = _x - p._x;
     double dy = _y - p._y;

     return dx*dx + dy*dy;
  }

  /**
  * returns Euclidean distance to Point p
  */
  public double distanceTo(Point p) {
     return Math.sqrt(squaredDistanceTo(p));
  }

  public void draw(Color clr){
	StdDraw.setPenColor(clr);
	StdDraw.circle(_x, _y, 0.015);
  }
  public void draw(){
	draw(StdDraw.BLACK);
  }

  // You can add additional methods here...

  public static void drawRectangle(Point sw, Point ne) {
        StdDraw.line(sw._x, sw._y, sw._x, ne._y);
        StdDraw.line(sw._x, ne._y, ne._x, ne._y);
        StdDraw.line(ne._x, ne._y, ne._x, sw._y);
        StdDraw.line(ne._x, sw._y, sw._x, sw._y);
  }

  public boolean equals(Point p) {
        return (p==this) || (_x == p._x && _y == p._y);
  }

  public boolean inside(Point sw, Point ne) {
        return (sw._x <= _x && _x <= ne._x) &&
               (sw._y <= _y && _y <= ne._y);
  }

  public int compareX( Point p ){
	  int result = 0;
	  if(this.x() < p.x()){
		  result = -1;
	  }else if (this.x() > p.x()){
		  result = 1;
	  }
	  return result;
  }
  
  public int compareY( Point p ){
	  int result = 0;
	  if(this.y() < p.y()){
		  result = -1;
	  }else if (this.y() > p.y()){
		  result = 1;
	  }
	  return result;
  }  
@Override
public int compareTo(Object arg0) {
	int result = 0; 
	
	return 0;
}
		
}

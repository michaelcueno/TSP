
public class Edge{
	
	double dist;
	Point parent;
	Point child;

	public Edge( Point parent, Point child ){
		this( parent.distanceTo(child), parent, child );
	}

	public Edge(double dist, Point parent, Point child){;
		this.dist = dist;
		this.parent = parent;
		this.child = child;
	}

	public double getDist(){ return this.dist; }
 	
	public Point getParent(){ return parent; }

	public Point getChild(){ return child; }

	public void update( Point child){
		double newDist = parent.distanceTo(child);
		this.dist = newDist;
		this.child = child;
	}

	@Override
	public String toString(){
		return( parent +" -> "+ child + "dist: " + dist);
	}

	
}

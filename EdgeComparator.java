import java.util.Comparator;


public class EdgeComparator implements Comparator<Edge> {

	@Override
	public int compare( Edge arg0, Edge arg1 ){
		if( arg0.dist < arg1.dist ){
			return -1;
		}else if( arg0.dist > arg1.dist ){
			return 1;
		}else
			return 0; 
	}

}
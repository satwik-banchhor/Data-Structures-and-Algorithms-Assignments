
public class Edge implements EdgeInterface, Comparable<Edge>{
	public Point p1;
	public Point p2;
	public Point[] end_points;
	public LinkedList<Triangle> triangle_neighbours;//FIFO
	public float length;
	public boolean old = false;
	public Position<Edge> boundary_position;
		
	Edge(Point p1, Point p2) {
		//p1<p2 for an edge
		if (p1.compareTo(p2)<0) {
			this.p1=p1;
			this.p2=p2;
		}
		else {
			this.p1=p2;
			this.p2=p1;
		}
		this.end_points=new Point[2];
		this.end_points[0]=this.p1;
		this.end_points[1]=this.p2;
		this.triangle_neighbours = new LinkedList<Triangle>();
		this.length=(this.p1.x-this.p2.x)*(this.p1.x-this.p2.x) + 
					(this.p1.y-this.p2.y)*(this.p1.y-this.p2.y) + 
					(this.p1.z-this.p2.z)*(this.p1.z-this.p2.z);
	}
	@Override
	public PointInterface[] edgeEndPoints() {
		return end_points;
	}
	@Override
	public int compareTo(Edge e) {
		if (this.p1.compareTo(e.p1)>0) {
			return 1;
		}
		else if(this.p1.compareTo(e.p1)==0) {
			if (this.p2.compareTo(e.p2)>0) {
				return 1;
			}
			else if(this.p2.compareTo(e.p2)==0) {
				return 0;
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
	}
	
	public boolean is_boundary() {
		if (this.triangle_neighbours.length==1) {
			return true;
		}
		return false;
	}
	
	//DEBUG
	public String toString() {
		return "["+this.p1.toString()+","+this.p2.toString()+"]";
	}
}

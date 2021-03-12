
public class Triangle implements TriangleInterface, Comparable<Triangle> {
	public Point p1;
	public Point p2;
	public Point p3;
	public Edge e1;
	public Edge e2;
	public Edge e3;
	public Point[] points;
	public Edge[] edges;
	public int arrivalrank;
	public LinkedList<Triangle> neighbours;//FIFO
	public LinkedList<Triangle> extended_neighbours;//FIFO
	public boolean in_a_component = false;
	public boolean considered_as_ext_neigh = false;

	Triangle(Point p1, Point p2, Point p3, Edge e1, Edge e2, Edge e3) {
		this.points = new Point[3];
		this.points[0] = p1;
		this.points[1] = p2;
		this.points[2] = p3;
		this.edges = new Edge[3];
		this.edges[0] = e1;
		this.edges[1] = e2;
		this.edges[2] = e3;
		//Sorting triangle's points and edges
		for (int i=0; i<2; i++) {
			for (int j=0; j<2-i; j++) {
				if (this.points[j].compareTo(this.points[j+1])>0) {
					Point temp = this.points[j];
					this.points[j] = this.points[j+1];
					this.points[j+1]=temp;
				}
			}
		}
		for (int i=0; i<2; i++) {
			for (int j=0; j<2-i; j++) {
				if (this.edges[j].compareTo(this.edges[j+1])>0) {
					Edge temp = this.edges[j];
					this.edges[j] = this.edges[j+1];
					this.edges[j+1]=temp;
				}
			}
		}
		//for each triangle p1<p2<p3
		//for each triangle e1<e2<e3
		//Edge corresponding to p1p2:e1 p1p3:e2 p2p3:e3
		this.p1 = points[0];
		this.p2 = points[1];
		this.p3 = points[2];
		this.e1 = edges[0];
		this.e2 = edges[1];
		this.e3 = edges[2];
		this.neighbours = new LinkedList<Triangle>();
		this.extended_neighbours = new LinkedList<Triangle>();
	}
	@Override
	public PointInterface[] triangle_coord() {
		return this.points;
	}
	@Override
	public int compareTo(Triangle t) {
		if (this.p1.compareTo(t.p1)>0) {
			return 1;
		}
		else if (this.p1.compareTo(t.p1)==0) {
			if (this.p2.compareTo(t.p2)>0) {
				return 1;
			}
			else if (this.p2.compareTo(t.p2)==0) {
				if (this.p3.compareTo(t.p3)>0) {
					return 1;
				}
				else if (this.p3.compareTo(t.p3)==0) {
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
		else {
			return -1;
		}
	}
	
	//Catch: No two edges can have same neighbour triangles as then 
	//it would be this new triangle itself which we will make sure 
	//is not added when we call these functions
	public static Position<Triangle> least_arrival_rank(Position<Triangle> p1, Position<Triangle> p2, Position<Triangle> p3) {
		Triangle t1 = null;
		Triangle t2 = null;
		Triangle t3 = null;
		if (p1!=null) {
			t1 = p1.val;
		}
		if (p2!=null) {
			t2 = p2.val;
		}
		if (p3!=null) {
			t3 = p3.val;
		}

		if (t1==null && t2==null) {
			return p3;
		}
		if (t1==null && t3==null) {
			return p2;
		}
		if (t2==null && t3==null) {
			return p1;
		}
		if (t1==null) {
			return least_arrival_rank(p2, p3);
		}
		if (t2==null) {
			return least_arrival_rank(p1, p3);
		}
		if (t3==null) {
			return least_arrival_rank(p1, p2);
		}
		if (t1.arrivalrank<=t2.arrivalrank && t1.arrivalrank<=t3.arrivalrank) {
			return p1;
		}
		if (t2.arrivalrank<=t1.arrivalrank && t2.arrivalrank<=t3.arrivalrank) {
			return p2;
		}
		if (t3.arrivalrank<=t1.arrivalrank && t3.arrivalrank<=t2.arrivalrank) {
			return p3;
		}
		System.out.println("OMG");
		System.out.println(t1.arrivalrank);
		System.out.println(t2.arrivalrank);
		System.out.println(t3.arrivalrank);
		return null;
	}
	
	public static Position<Triangle> least_arrival_rank(Position<Triangle> p1, Position<Triangle> p2) {
		Triangle t1 = p1.val;
		Triangle t2 = p2.val;
		if (t1.arrivalrank<t2.arrivalrank) {
			return p1;
		}
		else {
			return p2;
		}
	}
	
	//DEBUG
		public String toString() {
			return "["+this.p1.toString()+","+this.p2.toString()+","+this.p3.toString()+"]";
		}
}

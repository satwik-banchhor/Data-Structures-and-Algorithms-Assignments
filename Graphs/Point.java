
public class Point implements PointInterface, Comparable<Point>{
	public float x;
	public float y;
	public float z;
	public float[] coordinates;
	public LinkedList<Point> point_neighbours;
	public LinkedList<Edge> edge_neighbours;
	public LinkedList<Triangle> triangle_neighbours;//FIFO
	public boolean old = false;
	public boolean taken_for_centroid = false;
	public boolean taken_for_component = false;
	Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.coordinates = new float[3];
		this.coordinates[0] = this.x;
		this.coordinates[1] = this.y;
		this.coordinates[2] = this.z;
		this.point_neighbours=new LinkedList<Point>();
		this.edge_neighbours=new LinkedList<Edge>();
		this.triangle_neighbours= new LinkedList<Triangle>();
	}
	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	@Override
	public float getZ() {
		return this.z;
	}

	@Override
	public float[] getXYZcoordinate() {		
		return this.coordinates;
	}
	@Override
	public int compareTo(Point p) {
		if (this.x>p.x) {
			return 1;
		}
		else if (this.x==p.x) {
			if (this.y>p.y) {
				return 1;
			}
			else if (this.y==p.y) {
				if (this.z>p.z) {
					return 1;
				}
				else if (this.z==p.z) {
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

	//DEBUG
	public String toString() {
		return "("+String.valueOf(this.x)+","+String.valueOf(this.y)+","+String.valueOf(this.z)+")";
		 
	}
}

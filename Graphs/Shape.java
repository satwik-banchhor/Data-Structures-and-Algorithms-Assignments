
enum type_mesh{
	proper,semiproper,improper
}

public class Shape implements ShapeInterface {
	public RBTree<Point> pointsRB;
	public RBTree<Edge> edgesRB;
	public RBTree<Triangle> trianglesRB;
	public LinkedList<Edge> boundariesLL;
	public LinkedList<Triangle> trianglesLL;
	public int triangle_number=1;
	public type_mesh mesh_type;
	
	Shape() {
		this.pointsRB = new RBTree<Point>();
		this.edgesRB = new RBTree<Edge>();
		this.trianglesRB = new RBTree<Triangle>();
		this.boundariesLL = new LinkedList<Edge>();
		this.trianglesLL = new LinkedList<Triangle>();
		this.mesh_type = type_mesh.semiproper;
	}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public boolean ADD_TRIANGLE(float [] triangle_coord){
		//TODO:If they are collinear return false
		float a = triangle_coord[0]-triangle_coord[3];//x1-x2
		float b = triangle_coord[1]-triangle_coord[4];//y1-y2
		float c = triangle_coord[2]-triangle_coord[5];//z1-z2
		float d = triangle_coord[6]-triangle_coord[3];//x3-x2
		float e = triangle_coord[7]-triangle_coord[4];//y3-y2
		float f = triangle_coord[8]-triangle_coord[5];//z3-z2
		float Area = (b*f-e*c)*(b*f-e*c)+(c*d-a*f)*(c*d-a*f)+(a*e-b*d)*(a*e-b*d);
		if (Area == 0) {
			return false;
		}
		//Else get original point and edge objects if they existed or create new
		//Update three RBs and trianlesLL
		Point p1 = new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point p2 = new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point p3 = new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		if (!pointsRB.insert(p1)) {
			p1=pointsRB.searchinsertpos(p1).key;
		}
		if (!pointsRB.insert(p2)) {
			p2=pointsRB.searchinsertpos(p2).key;
		}
		if (!pointsRB.insert(p3)) {
			p3=pointsRB.searchinsertpos(p3).key;
		}
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p1,p3);
		Edge e3 = new Edge(p2,p3);
		if (!edgesRB.insert(e1)) {
			e1=edgesRB.searchinsertpos(e1).key;
		}
		if (!edgesRB.insert(e2)) {
			e2=edgesRB.searchinsertpos(e2).key;
		}
		if (!edgesRB.insert(e3)) {
			e3=edgesRB.searchinsertpos(e3).key;
		}
		Triangle t = new Triangle(p1,p2,p3,e1,e2,e3);
		if (trianglesRB.search(t)!=null) {
			return false;
		}
		trianglesRB.insert(t);
		trianglesLL.add(t);
		//TODO:Update triangle_number and triangle's arrivalrank
		t.arrivalrank=triangle_number;
		triangle_number++;
		//Points and edges in order (increasing)
		p1=t.p1;
		p2=t.p2;
		p3=t.p3;
		e1=t.e1;
		e2=t.e2;
		e3=t.e3;
		
		//Catches:
		//(i)Update edge neighbours' neighbours first then point neighbours'
		//(ii)Add new triangle to edge and point's neighbours after updating
		//the triangle's neighbours
		
		//Updating already existing objects and new objects		
		//TODO:(1,2,3)Triangle's neighbours using all edge's triangles requires(merging   
		//	   3 linkedlists) and also all edge's triangles by adding the new triangle
		//	   also update all edges' triangle_neighbours
		LinkedList<Triangle> LL1 = e1.triangle_neighbours;
		LinkedList<Triangle> LL2 = e2.triangle_neighbours;
		LinkedList<Triangle> LL3 = e3.triangle_neighbours;
		Position<Triangle> iterpos1 = LL1.head;
		Position<Triangle> iterpos2 = LL2.head;
		Position<Triangle> iterpos3 = LL3.head;
		//CATCH:No two neighbours of two different edge can be same 
		//INV:We have merged LLi's before iterposi in t.neighbours 
		while (!(iterpos1==null && iterpos2==null && iterpos3==null)) {			
			Position<Triangle> minpos = Triangle.least_arrival_rank(iterpos1,iterpos2,iterpos3);
			minpos.val.neighbours.add(t);//1.Updating edge's neighbours' neghbours
			t.neighbours.add(minpos.val);//2.Updating triangle's neighbours
			if (iterpos1!=null && minpos.val.compareTo(iterpos1.val)==0) {
				iterpos1 =iterpos1.nextpos;
			}
			if (iterpos2!=null && minpos.val.compareTo(iterpos2.val)==0) {
				iterpos2 =iterpos2.nextpos;
			}
			if (iterpos3!=null && minpos.val.compareTo(iterpos3.val)==0) {
				iterpos3 =iterpos3.nextpos;
			}
		}
		//3.Updating edges' triangle neighbours
		e1.triangle_neighbours.add(t);
		e2.triangle_neighbours.add(t);
		e3.triangle_neighbours.add(t);
		
		//TODO:(4,5,6)Triangle's extended neighbours using all point's triangles 
		//	   (that do not have this triangle) as their last neighbour and
		//	   also all points' neighbours' extended neighboours () by adding the new 
		//	   triangle
		//	   also update all points' triangle neighbours
		LL1 = p1.triangle_neighbours;
		LL2 = p2.triangle_neighbours;
		LL3 = p3.triangle_neighbours;
		iterpos1 = LL1.head;
		iterpos2 = LL2.head;
		iterpos3 = LL3.head;
		//CATCH:No two only neighbours(which do not have t as neighbour) of 2 different 
		//	    point can be same 
		//INV:We have merged LLi's before iterposi in t.neighbours
		while (!(iterpos1==null && iterpos2==null && iterpos3==null)) {
			//1.Updating edge's neighbours' neghbours
//			while(iterpos1!=null && iterpos1.val.considered_as_ext_neigh) {//iterpos1.val.neighbours.length>0 && iterpos1.val.neighbours.end.val.compareTo(t)==0) {
//				iterpos1 = iterpos1.nextpos;
//			}
//			if (iterpos1!=null) {
//				iterpos1.val.considered_as_ext_neigh=true;
//			}
//			while(iterpos2!=null && iterpos2.val.considered_as_ext_neigh) {//iterpos2.val.neighbours.length>0 && iterpos2.val.neighbours.end.val.compareTo(t)==0) {
//				iterpos2 = iterpos2.nextpos;
//			}
//			if (iterpos2!=null) {
//				iterpos2.val.considered_as_ext_neigh=true;
//			}
//			while(iterpos3!=null && iterpos3.val.considered_as_ext_neigh) {//iterpos3.val.neighbours.length>0 && iterpos3.val.neighbours.end.val.compareTo(t)==0) {
//				iterpos3 = iterpos3.nextpos;
//			}
//			if (iterpos3!=null) {
//				iterpos3.val.considered_as_ext_neigh=true;
//			}
//			if (!(iterpos1==null && iterpos2==null && iterpos3==null)) {
				Position<Triangle> minpos = Triangle.least_arrival_rank(iterpos1,iterpos2,iterpos3);
				if (!minpos.val.considered_as_ext_neigh) {
					minpos.val.extended_neighbours.add(t);//1.Updating point's neighbours' extneighs
					minpos.val.considered_as_ext_neigh = true;
					t.extended_neighbours.add(minpos.val);//2.Updating triangle's ext. neighbours
				}
				if (iterpos1!=null && minpos.val.compareTo(iterpos1.val)==0) {
					iterpos1 =iterpos1.nextpos;
				}
				if (iterpos2!=null && minpos.val.compareTo(iterpos2.val)==0) {
					iterpos2 =iterpos2.nextpos;
				}
				if (iterpos3!=null && minpos.val.compareTo(iterpos3.val)==0) {
					iterpos3 =iterpos3.nextpos;
				}
//			}
		}
		LL1 = p1.triangle_neighbours;
		LL2 = p2.triangle_neighbours;
		LL3 = p3.triangle_neighbours;
		iterpos1 = LL1.head;
		iterpos2 = LL2.head;
		iterpos3 = LL3.head;
		while (iterpos1!=null) {
			iterpos1.val.considered_as_ext_neigh=false;
			iterpos1 = iterpos1.nextpos;			
		}
		while (iterpos2!=null) {
			iterpos2.val.considered_as_ext_neigh=false;
			iterpos2 = iterpos2.nextpos;			
		}
		while (iterpos3!=null) {
			iterpos3.val.considered_as_ext_neigh=false;
			iterpos3 = iterpos3.nextpos;
		}
		//3.Updating points' triangle neighbours
		p1.triangle_neighbours.add(t);
		p2.triangle_neighbours.add(t);
		p3.triangle_neighbours.add(t);
		
		//TODO:Point's neighbours and incident edges
		//	   Add new edges through this point and new points
		//	   Edge corresponding to p1p2:e1 p1p3:e2 p2p3:e3
		if (!e1.old) {//e1=p1p2
			p1.edge_neighbours.add(e1);			
			p2.edge_neighbours.add(e1);
			p1.point_neighbours.add(p2);
			p2.point_neighbours.add(p1);
		}
		if (!e2.old) {//e2=p1p3
			p1.edge_neighbours.add(e2);
			p3.edge_neighbours.add(e2);
			p1.point_neighbours.add(p3);
			p3.point_neighbours.add(p1);
		}		
		if (!e3.old) {//e3=p2p3
			p2.edge_neighbours.add(e3);
			p3.edge_neighbours.add(e3);
			p2.point_neighbours.add(p3);
			p3.point_neighbours.add(p2);
		}
		
		//TODO:Update boundariesLL by removing those edge-positions that have count=2
		//	   and adding new positions with value = edges that have count=1;
		//	   also update those edges' boundary_position (either null or new position)
		if (e1.triangle_neighbours.length==1) {
			boundariesLL.add(e1);
			e1.boundary_position = boundariesLL.end;
		}
		if (e1.triangle_neighbours.length==2) {
			boundariesLL.remove(e1.boundary_position);
			e1.boundary_position = null;
		}
		if (e2.triangle_neighbours.length==1) {
			boundariesLL.add(e2);
			e2.boundary_position = boundariesLL.end;
		}
		if (e2.triangle_neighbours.length==2) {
			boundariesLL.remove(e2.boundary_position);
			e2.boundary_position = null;
		}
		if (e3.triangle_neighbours.length==1) {
			boundariesLL.add(e3);
			e3.boundary_position = boundariesLL.end;
		}
		if (e3.triangle_neighbours.length==2) {
			boundariesLL.remove(e3.boundary_position);
			e3.boundary_position = null;
		}
		
		//TODO:Update mesh_type
		if (this.mesh_type!=type_mesh.improper) {
			if (e1.triangle_neighbours.length>2 || e2.triangle_neighbours.length>2 || e3.triangle_neighbours.length>2) {
				this.mesh_type = type_mesh.improper;
			}
			else if (boundariesLL.length!=0) {
				this.mesh_type = type_mesh.semiproper;
			}
			else {
				this.mesh_type = type_mesh.proper;
			}
		}		
		
		//TODO:Update all points' and edges' old field.
		p1.old = true;
		p2.old = true;
		p3.old = true;
		e1.old = true;
		e2.old = true;
		e3.old = true;
		return true;
	}
	
	public int TYPE_MESH() {
		if (this.mesh_type==type_mesh.proper) {
			return 1;
		}
		if (this.mesh_type==type_mesh.semiproper) {
			return 2;
		}
		else {// (this.mesh_type==type_mesh.improper) {
			return 3;
		}
	}
	
	public EdgeInterface [] BOUNDARY_EDGES(){
		if (this.boundariesLL.length==0) {
			return null;
		}
		Edge[] boundary_edges = new Edge[boundariesLL.length];
		Position<Edge> iterpos = boundariesLL.head;
		int i = 0;
		while (iterpos!=null) {
			boundary_edges[i] = iterpos.val;
			i++;
			iterpos = iterpos.nextpos;
		}
		return mergesort(boundary_edges);
	}
	
	//TODO:Implement mergesort
	public Edge[] mergesort(Edge[] l) {
		if (l.length==1 || l.length==0) {
			return l;
		}
		int length = l.length;
		int length1 = length/2;
		int length2 = length - length1;
		Edge[] l1 = new Edge[length1];
		Edge[] l2 = new Edge[length2];
		for (int i=0;i<length;i++) {
			if (i<length1) {
				l1[i] = l[i];
			}
			else {
				l2[i-length1] = l[i];
			}
		}
		l1 = mergesort(l1);
		l2 = mergesort(l2);
		l = merge(l1,l2);
		return l;
	}
	public Edge[] merge(Edge[] l1, Edge[] l2) {
		if (l1.length==0) {
			return l2;
		}
		if (l2.length==0) {
			return l1;
		}
		Edge[] l = new Edge[l1.length+l2.length];
		int i1 = 0;
		int i2 = 0;
		for (int i=0;i<l1.length+l2.length;i++) {
			if (i1==l1.length) {
				l[i]=l2[i2];
				i2++;
			}
			else if (i2==l2.length) {
				l[i]=l1[i1];
				i1++;
			}
			else {
				if (l1[i1].length<=l2[i2].length) {
					l[i]=l1[i1];
					i1++;
				}
				else {
					l[i]=l2[i2];
					i2++;
				}
			}
		}
 		return l;
	}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord) {
		Point p1 = new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point p2 = new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point p3 = new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p1,p3);
		Edge e3 = new Edge(p2,p3);
		Triangle t = new Triangle(p1,p2,p3,e1,e2,e3);
		RedBlackNode<Triangle> rb_node = trianglesRB.search(t);
		if (rb_node==null) {
			return null;//null1
		}
		t = rb_node.key;
		//Making Array
		Triangle[] triangles_required = new Triangle[t.neighbours.length];
		if (t.neighbours.length==0) {
			return null;//null2
		}
		Position<Triangle> iterpos = t.neighbours.head;
		int i = 0;
		while (iterpos!=null) {
			triangles_required[i] = iterpos.val;
			i++;//I'am forgetting this
			iterpos = iterpos.nextpos;
		}
		return triangles_required;
	}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
		Point p1 = new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point p2 = new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point p3 = new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p1,p3);
		Edge e3 = new Edge(p2,p3);
		Triangle t = new Triangle(p1,p2,p3,e1,e2,e3);
		RedBlackNode<Triangle> rb_node = trianglesRB.search(t);
		if (rb_node==null) {
			return null;//null1
		}
		t = rb_node.key;
		return t.edges;
	}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
		Point p1 = new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point p2 = new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point p3 = new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p1,p3);
		Edge e3 = new Edge(p2,p3);
		Triangle t = new Triangle(p1,p2,p3,e1,e2,e3);
		RedBlackNode<Triangle> rb_node = trianglesRB.search(t);
		if (rb_node==null) {
			return null;//null1
		}
		t = rb_node.key;
		return t.points;
	}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
		Point p1 = new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point p2 = new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point p3 = new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p1,p3);
		Edge e3 = new Edge(p2,p3);
		Triangle t = new Triangle(p1,p2,p3,e1,e2,e3);
		RedBlackNode<Triangle> rb_node = trianglesRB.search(t);
		if (rb_node==null) {
			return null;//null1
		}
		t = rb_node.key;
		//Making Array
		Triangle[] triangles_required = new Triangle[t.extended_neighbours.length];
		if (t.extended_neighbours.length==0) {
			return null;//null2
		}
		Position<Triangle> iterpos = t.extended_neighbours.head;
		int i = 0;
		while (iterpos!=null) {
			triangles_required[i] = iterpos.val;
			i++;//I'am forgetting this
			iterpos = iterpos.nextpos;
		}
		return triangles_required;
	}
	
	//INPUT [x,y,z]
	public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates) {
		Point p1 = new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		RedBlackNode<Point> rb_node = pointsRB.search(p1);
		if (rb_node==null) {
			return null;//null1
		}
		p1 = rb_node.key;
		//Making Array
		Triangle[] triangles_required = new Triangle[p1.triangle_neighbours.length];
		if (p1.triangle_neighbours.length==0) {
			return null;//null2
		}
		Position<Triangle> iterpos = p1.triangle_neighbours.head;
		int i = 0;
		while (iterpos!=null) {
			triangles_required[i] = iterpos.val;
			i++;//I'am forgetting this
			iterpos = iterpos.nextpos;
		}
		return triangles_required;
	}
	
	// INPUT [x,y,z]
	public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates) {
		Point p1 = new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		RedBlackNode<Point> rb_node = pointsRB.search(p1);
		if (rb_node==null) {
			return null;//null1
		}
		p1 = rb_node.key;
		//Making Array
		Point[] points_required = new Point[p1.point_neighbours.length];
		if (p1.point_neighbours.length==0) {
			return null;//null2
		}
		Position<Point> iterpos = p1.point_neighbours.head;
		int i = 0;
		while (iterpos!=null) {
			points_required[i] = iterpos.val;
			i++;//I'am forgetting this
			iterpos = iterpos.nextpos;
		}
		return points_required;
	}
	
	// INPUT[x,y,z]
	public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates) {
		Point p1 = new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		RedBlackNode<Point> rb_node = pointsRB.search(p1);
		if (rb_node==null) {
			return null;//null1
		}
		p1 = rb_node.key;
		//Making Array
		Edge[] edges_required = new Edge[p1.edge_neighbours.length];
		if (p1.edge_neighbours.length==0) {
			return null;//null2
		}
		Position<Edge> iterpos = p1.edge_neighbours.head;
		int i = 0;
		while (iterpos!=null) {
			edges_required[i] = iterpos.val;
			i++;//I'am forgetting this
			iterpos = iterpos.nextpos;
		}
		return edges_required;
	}
	
	// INPUT[x,y,z]
	public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates) {
		Point p1 = new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		RedBlackNode<Point> rb_node = pointsRB.search(p1);
		if (rb_node==null) {
			return null;//null1
		}
		p1 = rb_node.key;
		//Making Array
		Triangle[] triangles_required = new Triangle[p1.triangle_neighbours.length];
		if (p1.triangle_neighbours.length==0) {
			return null;//null2
		}
		Position<Triangle> iterpos = p1.triangle_neighbours.head;
		int i = 0;
		while (iterpos!=null) {
			triangles_required[i] = iterpos.val;
			i++;//I'am forgetting this
			iterpos = iterpos.nextpos;
		}
		return triangles_required;
	}
	
	// INPUT [x1,y1,z1,x2,y2,z2] // where (x1,y1,z1) refers to first end point of edge and  (x2,y2,z2) refers to the second.
	public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates) {
		Point p1 = new Point(edge_coordinates[0],edge_coordinates[1],edge_coordinates[2]);
		Point p2 = new Point(edge_coordinates[3],edge_coordinates[4],edge_coordinates[5]);
		Edge e1 = new Edge(p1,p2);
		RedBlackNode<Edge> rb_node = edgesRB.search(e1);
		if (rb_node==null) {
			return null;//null1
		}
		e1 = rb_node.key;
		//Making Array
		Triangle[] triangles_required = new Triangle[e1.triangle_neighbours.length];
		if (e1.triangle_neighbours.length==0) {
			return null;//null2
		}
		Position<Triangle> iterpos = e1.triangle_neighbours.head;
		int i = 0;
		while (iterpos!=null) {
			triangles_required[i] = iterpos.val;
			i++;//I'am forgetting this
			iterpos = iterpos.nextpos;
		}
		return triangles_required;		
	}
	
	//BASIC: bfs_allComp and bfs
	public void bfs_allComp() {
		Position<Triangle> iterpos = trianglesLL.head;
		while (iterpos!=null) {
			if (!iterpos.val.in_a_component) {//If a triangle is not in any component then do bfs
				this.bfs(iterpos.val);
			}
			iterpos=iterpos.nextpos;
		}

		iterpos = trianglesLL.head;
		while (iterpos!=null) {//Unmarking all
			iterpos.val.in_a_component = false;	
			iterpos=iterpos.nextpos;
		}
	}	
	public void bfs(Triangle t) {//Does BFS and marks all the triangles connected to t 
		LinkedList<Triangle> queue = new LinkedList<Triangle>();
		queue.add(t);
		t.in_a_component = true;
		while (queue.length!=0) {
			Triangle parent = queue.head.val;
			// parent.in_a_component = true;//Marking PointsWRONG
			queue.remove(queue.head);//Remove from queue
			Position<Triangle> iter_on_children = parent.neighbours.head;
			while (iter_on_children!=null) {//Add to queue
				if (!iter_on_children.val.in_a_component) {
					queue.add(iter_on_children.val);
					iter_on_children.val.in_a_component = true;
				}
				iter_on_children=iter_on_children.nextpos;
			}
		}
	}
	
	public int COUNT_CONNECTED_COMPONENTS() {
		return bfs_allComp_for_COUNT_CONNECTED_COMPONENTS();
	}
	//BFS for COUNT_CONNECTED_COMPONENTS
	public int bfs_allComp_for_COUNT_CONNECTED_COMPONENTS() {//MODIFICATION: Return type changed to int
		Position<Triangle> iterpos = trianglesLL.head;
		int component_count = 0;//ADDITION
		while (iterpos!=null) {
			if (!iterpos.val.in_a_component) {//If a triangle is not in any component then do bfs
				this.bfs_for_COUNT_CONNECTED_COMPONENTS(iterpos.val);
				component_count++;//ADDITION
			}
			iterpos=iterpos.nextpos;
		}
		iterpos = trianglesLL.head;
		while (iterpos!=null) {//Unmarking all
			iterpos.val.in_a_component = false;	
			iterpos=iterpos.nextpos;
		}
		return component_count;
	}
	public void bfs_for_COUNT_CONNECTED_COMPONENTS(Triangle t) {//Does BFS and marks all the triangles connected to t 
	LinkedList<Triangle> queue = new LinkedList<Triangle>();
	queue.add(t);
	t.in_a_component = true;
	while (queue.length!=0) {
		Triangle parent = queue.head.val;
		// parent.in_a_component = true;//Marking PointsWRONG
		queue.remove(queue.head);//Remove from queue
		Position<Triangle> iter_on_children = parent.neighbours.head;
		while (iter_on_children!=null) {//Add to queue
			if (!iter_on_children.val.in_a_component) {
				queue.add(iter_on_children.val);
				iter_on_children.val.in_a_component = true;				
			}
			iter_on_children=iter_on_children.nextpos;
		}
	}
}
	
	// INPUT // [xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 , xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3]
	//where xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 are 3 coordinates of first triangle and xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3
	//are coordinates of second triangle as given in specificaition.
	public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2) {
		Point p1 = new Point(triangle_coord_1[0],triangle_coord_1[1],triangle_coord_1[2]);
		Point p2 = new Point(triangle_coord_1[3],triangle_coord_1[4],triangle_coord_1[5]);
		Point p3 = new Point(triangle_coord_1[6],triangle_coord_1[7],triangle_coord_1[8]);
		Edge e1 = new Edge(p1,p2);
		Edge e2 = new Edge(p1,p3);
		Edge e3 = new Edge(p2,p3);
		Triangle t1 = new Triangle(p1,p2,p3,e1,e2,e3);
		RedBlackNode<Triangle> rb_node = trianglesRB.search(t1);
		if (rb_node==null) {
			return false;//false1
		}
		t1=rb_node.key;
		p1 = new Point(triangle_coord_2[0],triangle_coord_2[1],triangle_coord_2[2]);
		p2 = new Point(triangle_coord_2[3],triangle_coord_2[4],triangle_coord_2[5]);
		p3 = new Point(triangle_coord_2[6],triangle_coord_2[7],triangle_coord_2[8]);
		e1 = new Edge(p1,p2);
		e2 = new Edge(p1,p3);
		e3 = new Edge(p2,p3);
		Triangle t2 = new Triangle(p1,p2,p3,e1,e2,e3);
		rb_node = trianglesRB.search(t2);
		if (rb_node==null) {
			return false;//false2
		}
		t2=rb_node.key;
		return bfs_for_IS_CONNECTED(t1,t2);
	}
	//BFS for IS_CONNECTED
	//MODIFICATION:return type: boolean, Triangle t1,Triangle t
	public boolean bfs_for_IS_CONNECTED(Triangle t1,Triangle t2) {//Does BFS and marks all the triangles connected to t
		LinkedList<Triangle> queue = new LinkedList<Triangle>();
		LinkedList<Triangle> marked= new LinkedList<Triangle>();//ADDITION
		queue.add(t1);
		t1.in_a_component = true;
		marked.add(t1);
		while (queue.length!=0) {
			Triangle parent = queue.head.val;
			// parent.in_a_component = true;//Marking PointsWRONG
			queue.remove(queue.head);//Remove from queue
			Position<Triangle> iter_on_children = parent.neighbours.head;
			while (iter_on_children!=null && iter_on_children.val.compareTo(t2)!=0) {//Add to queue
				if (!iter_on_children.val.in_a_component) {
					queue.add(iter_on_children.val);
					iter_on_children.val.in_a_component = true;
					marked.add(iter_on_children.val);
				}
				iter_on_children=iter_on_children.nextpos;
			}
			//ADDITION starts:
			if (iter_on_children!=null && iter_on_children.val.compareTo(t2)==0) {
				Position<Triangle> iter_marked = marked.head;
				while (iter_marked!=null) {
					iter_marked.val.in_a_component=false;
					iter_marked = iter_marked.nextpos;
				}
				return true;
			}
			//ADDITION ends
		}
		Position<Triangle> iter_marked = marked.head;
		while (iter_marked!=null) {
			iter_marked.val.in_a_component=false;
			iter_marked = iter_marked.nextpos;
		}
		return false;//ADDITION
	}
	
	public int MAXIMUM_DIAMETER() {
		return bfs_allComp_for_MAXIMUM_DIAMETER();
	}
	//MODIFICATION: Return type int(maximum diameter of the graph)
	public int bfs_allComp_for_MAXIMUM_DIAMETER() {
		LinkedList<Triangle> largest_component = new LinkedList<Triangle>();//ADDITION
		Position<Triangle> iterpos = trianglesLL.head;
		while (iterpos!=null) {
			if (!iterpos.val.in_a_component) {//If a triangle is not in any component then do bfs
				LinkedList<Triangle> component = this.bfs_for_MAXIMUM_DIAMETER(iterpos.val);//MODIFICATION
				//ADDITION starts:
				if (component.length>largest_component.length) {
					largest_component = component;
				}
				//ADDITION ends
			}
			iterpos=iterpos.nextpos;
		}

		iterpos = trianglesLL.head;
		while (iterpos!=null) {//Unmarking all
			iterpos.val.in_a_component = false;	
			iterpos=iterpos.nextpos;
		}
		return bfs_allComp_for_diameter(largest_component);
	}
	//MODIFICATION: Return type LinkedList<Triangle> component
	public LinkedList<Triangle> bfs_for_MAXIMUM_DIAMETER(Triangle t) {//Does BFS and marks all the triangles connected to t
		LinkedList<Triangle> queue = new LinkedList<Triangle>();
		LinkedList<Triangle> component = new LinkedList<Triangle>();//ADDITION
		queue.add(t);
		t.in_a_component = true;
		while (queue.length!=0) {
			Triangle parent = queue.head.val;
			// parent.in_a_component = true;//Marking PointsWrong
			queue.remove(queue.head);//Remove from queue
			component.add(parent);//ADDITION
			Position<Triangle> iter_on_children = parent.neighbours.head;
			while (iter_on_children!=null) {//Add to queue
				if (!iter_on_children.val.in_a_component) {
					queue.add(iter_on_children.val);
					iter_on_children.val.in_a_component = true;
				}
				iter_on_children=iter_on_children.nextpos;
			}
		}
		return component;
	}
	//MODIFICATION: retrun type int diameter of the component 
	public int bfs_allComp_for_diameter(LinkedList<Triangle> component) {
		Position<Triangle> iterpos = component.head;//MODIFICATION
		int diameter = 0;
		while (iterpos!=null) {
			int eccentricity = this.bfs_for_diameter(iterpos.val, component);
			if 	(eccentricity>diameter) {
				diameter = eccentricity;
			}
			iterpos=iterpos.nextpos;
		}
		//No unmarking required here
		return diameter;//ADDITION
	}
	//MODIFICATION: return type int eccentricity of t
	public int bfs_for_diameter(Triangle t, LinkedList<Triangle> component) {//Does BFS and marks all the triangles connected to t 
		LinkedList<Triangle> level = new LinkedList<Triangle>();//MODIFICATION queue <=> level
		int eccentricity = 0;//ADDITION
		level.add(t);
		t.in_a_component = true;
		while (level.length!=0) {			
			//MODIFICATION starts
			LinkedList<Triangle> nextlevel = new LinkedList<Triangle>();
			Position<Triangle> iter_level = level.head;
			while (iter_level!=null) {
				Triangle parent = iter_level.val;
				// parent.in_a_component = true;//Marking PointsWRONG
				Position<Triangle> iter_on_children = parent.neighbours.head;
				while (iter_on_children!=null) {//Add to queue
					if (!iter_on_children.val.in_a_component) {
						nextlevel.add(iter_on_children.val);
						iter_on_children.val.in_a_component = true;
					}
					iter_on_children=iter_on_children.nextpos;
				}
				iter_level = iter_level.nextpos;
			}
			level = nextlevel;
			//MODIFICATION ends
			if (level.length!=0) {
				eccentricity++;
			}
		}
		//ADDITION starts:
		Position<Triangle> iterpos = component.head;
		while (iterpos!=null) {//Unmarking all
			iterpos.val.in_a_component = false;	
			iterpos=iterpos.nextpos;
		}
		return eccentricity;
		//ADDITION ends
	}

	public PointInterface [] CENTROID () {
		LinkedList<Point> Centroids = bfs_allComp_for_CENTROID();
		Point[] CentroidsArray = new Point[Centroids.length];
		int i = 0;
		Position<Point> iter_centroids = Centroids.head;
		while (iter_centroids!=null) {
			CentroidsArray[i] = iter_centroids.val;
			i++;
			iter_centroids = iter_centroids.nextpos;
		}
		return mergesort(CentroidsArray);
	}
	//MODIFICATION: Return type Sorted Centroids
	public LinkedList<Point> bfs_allComp_for_CENTROID() {
		Position<Triangle> iterpos = trianglesLL.head;
		LinkedList<Point> Centroids = new LinkedList<Point>();//ADDITION
		while (iterpos!=null) {
			if (!iterpos.val.in_a_component) {//If a triangle is not in any component then do bfs
				Point centroid = this.bfs_for_CENTROID(iterpos.val);//MODIFICATION
				Centroids.add(centroid);//ADDITION
			}
			iterpos=iterpos.nextpos;
		}	
		iterpos = trianglesLL.head;
		while (iterpos!=null) {//Unmarking all
			iterpos.val.in_a_component = false;	
			iterpos=iterpos.nextpos;
		}
		return Centroids;
	}
	//MODIFICATION: Return type Point component's centroid
	public Point bfs_for_CENTROID(Triangle t) {//Does BFS and marks all the triangles connected to t
		LinkedList<Triangle> queue = new LinkedList<Triangle>();
		LinkedList<Triangle> component = new LinkedList<Triangle>();//ADDITION
		int number_of_points_of_the_component = 0;//ADDITION
		LinkedList<Point> points_of_the_component = new LinkedList<Point>();
		Point centroid = new Point(0,0,0);//ADDITION
		queue.add(t);
		t.in_a_component = true;
		while (queue.length!=0) {
			Triangle parent = queue.head.val;
			// parent.in_a_component = true;//Marking PointsWRONG
			queue.remove(queue.head);//Remove from queue
			component.add(parent);//ADDITION
			//ADDITION satrts:
			for (int i=0;i<3;i++) {
				if (!parent.points[i].taken_for_centroid) {//not taken for centroid then take and call it taken
					centroid.x = centroid.x + parent.points[i].x;
					centroid.y = centroid.y + parent.points[i].y;
					centroid.z = centroid.z + parent.points[i].z;
					number_of_points_of_the_component++;
					parent.points[i].taken_for_centroid = true;
					points_of_the_component.add(parent.points[i]);					
				}
			}
			//ADDITION ends
			Position<Triangle> iter_on_children = parent.neighbours.head;
			while (iter_on_children!=null) {//Add to queue
				if (!iter_on_children.val.in_a_component) {
					queue.add(iter_on_children.val);
					iter_on_children.val.in_a_component = true;
				}
				iter_on_children=iter_on_children.nextpos;
			}			
		}
		//ADDITION starts: (Unmarking marked triangles is not required but we need to unmark points)
		Position<Point> iter_pointscomp = points_of_the_component.head;
		while (iter_pointscomp!=null) {
			iter_pointscomp.val.taken_for_centroid = false;
			iter_pointscomp = iter_pointscomp.nextpos;			
		}
		centroid.x = centroid.x/number_of_points_of_the_component;
		centroid.y = centroid.y/number_of_points_of_the_component;
		centroid.z = centroid.z/number_of_points_of_the_component;
		return centroid;
		//ADDITION ends
	}
	public Point[] mergesort(Point[] l) {
		if (l.length==1) {
			return l;
		}
		int length = l.length;
		int length1 = length/2;
		int length2 = length - length1;
		Point[] l1 = new Point[length1];
		Point[] l2 = new Point[length2];
		for (int i=0;i<length;i++) {
			if (i<length1) {
				l1[i] = l[i];
			}
			else {
				l2[i-length1] = l[i];
			}
		}
		l1 = mergesort(l1);
		l2 = mergesort(l2);
		l = merge(l1,l2);
		return l;
	}
	public Point[] merge(Point[] l1, Point[] l2) {
		if (l1.length==0) {
			return l2;
		}
		if (l2.length==0) {
			return l1;
		}
		Point[] l = new Point[l1.length+l2.length];
		int i1 = 0;
		int i2 = 0;
		for (int i=0;i<l1.length+l2.length;i++) {
			if (i1==l1.length) {
				l[i]=l2[i2];
				i2++;
			}
			else if (i2==l2.length) {
				l[i]=l1[i1];
				i1++;
			}
			else {
				if (l1[i1].compareTo(l2[i2])<0) {
					l[i]=l1[i1];
					i1++;
				}
				else {
					l[i]=l2[i2];
					i2++;
				}
			}
		}
 		return l;
	}
	
	// INPUT [x,y,z]
	public PointInterface CENTROID_OF_COMPONENT (float [] point_coordinates) {
		Point p1 = new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		RedBlackNode<Point> rb_node = pointsRB.search(p1);
		if (rb_node==null) {
			return null;//null1
		}
		p1 = rb_node.key;
		 return bfs_for_CENTROID_OF_COMPONENT(p1.triangle_neighbours.head.val);
	}
	//MODIFICATION: Return type Point component's centroid
	public Point bfs_for_CENTROID_OF_COMPONENT(Triangle t) {//Does BFS and marks all the triangles connected to t
		LinkedList<Triangle> queue = new LinkedList<Triangle>();
		LinkedList<Triangle> component = new LinkedList<Triangle>();//ADDITION
		int number_of_points_of_the_component = 0;//ADDITION
		LinkedList<Point> points_of_the_component = new LinkedList<Point>();//ADDITION
		Point centroid = new Point(0,0,0);//ADDITION
		queue.add(t);
		t.in_a_component = true;
		while (queue.length!=0) {
			Triangle parent = queue.head.val;
			// parent.in_a_component = true;//Marking PointsWRONG
			queue.remove(queue.head);//Remove from queue
			component.add(parent);//ADDITION
			//ADDITION satrts:
			for (int i=0;i<3;i++) {
				if (!parent.points[i].taken_for_centroid) {//not taken for centroid then take and call it taken
					centroid.x = centroid.x + parent.points[i].x;
					centroid.y = centroid.y + parent.points[i].y;
					centroid.z = centroid.z + parent.points[i].z;
					number_of_points_of_the_component++;
					parent.points[i].taken_for_centroid = true;
					points_of_the_component.add(parent.points[i]);
				}
			}
			//ADDITION ends
			Position<Triangle> iter_on_children = parent.neighbours.head;
			while (iter_on_children!=null) {//Add to queue
				if (!iter_on_children.val.in_a_component) {
					queue.add(iter_on_children.val);
					iter_on_children.val.in_a_component = true;	
				}
				iter_on_children=iter_on_children.nextpos;
			}			
		}
		//ADDITION starts: (Unmarking marked triangles and points)
		Position<Point> iter_pointscomp = points_of_the_component.head;
		while (iter_pointscomp!=null) {
			iter_pointscomp.val.taken_for_centroid = false;
			iter_pointscomp = iter_pointscomp.nextpos;
			
		}
		Position<Triangle> iter_comp = component.head;
		while (iter_comp!=null) {
			iter_comp.val.in_a_component = false;
			iter_comp = iter_comp.nextpos;
		}
		centroid.x = centroid.x/number_of_points_of_the_component;
		centroid.y = centroid.y/number_of_points_of_the_component;
		centroid.z = centroid.z/number_of_points_of_the_component;
		return centroid;
		//ADDITION ends
	}
	
	public 	PointInterface [] CLOSEST_COMPONENTS() {
		return get_closest_points(bfs_allComp_for_CLOSEST_COMPONENTS());
	}
	//MODIFICATION: Return type int(maximum diameter of the graph)
	public LinkedList<LinkedList<Point>> bfs_allComp_for_CLOSEST_COMPONENTS() { 
		LinkedList<LinkedList<Point>> all_component_points = new LinkedList<LinkedList<Point>>();//ADDITION
		Position<Triangle> iterpos = trianglesLL.head;
		while (iterpos!=null) {
			if (!iterpos.val.in_a_component) {//If a triangle is not in any component then do bfs
				LinkedList<Point> component_points = this.bfs_for_CLOSEST_COMPONENTS(iterpos.val);//MODIFICATION
				//ADDITION starts:
				all_component_points.add(component_points);
				}
				//ADDITION ends			
			iterpos=iterpos.nextpos;
		}

		iterpos = trianglesLL.head;
		while (iterpos!=null) {//Unmarking all
			iterpos.val.in_a_component = false;	
			iterpos=iterpos.nextpos;
		}
		return all_component_points;
	}	
	//MODIFICATION: Return type LinkedList<Triangle> component
	public LinkedList<Point> bfs_for_CLOSEST_COMPONENTS(Triangle t) {//Does BFS and marks all the triangles connected to t
		LinkedList<Triangle> queue = new LinkedList<Triangle>();
		LinkedList<Point> component_points = new LinkedList<Point>();//ADDITION
		queue.add(t);
		t.in_a_component = true;
		while (queue.length!=0) {
			Triangle parent = queue.head.val;
			// parent.in_a_component = true;//Marking PointsWRONG
			queue.remove(queue.head);//Remove from queue
			//ADDITION starts
			for (int i=0;i<3;i++) {
				if (!parent.points[i].taken_for_component) {
					component_points.add(parent.points[i]);
					parent.points[i].taken_for_component=true;
				}
			}
			//ADDITION ends
			Position<Triangle> iter_on_children = parent.neighbours.head;
			while (iter_on_children!=null) {//Add to queue
				if (!iter_on_children.val.in_a_component) {
					queue.add(iter_on_children.val);
					iter_on_children.val.in_a_component = true;	
				}
				iter_on_children=iter_on_children.nextpos;
			}
		}
		//ADDITION: Unmarking points
		Position<Point> iter_comp_points = component_points.head;
		while (iter_comp_points!=null) {
			iter_comp_points.val.taken_for_component=false;
			iter_comp_points = iter_comp_points.nextpos;
		}
		return component_points;
	}
	public Point[] get_closest_points(LinkedList<LinkedList<Point>> all_component_points) {
		if (all_component_points.length<2) {
			return null;
		}
		Point[] closest_points = new Point[2];
		closest_points[0] = all_component_points.head.val.head.val;
		closest_points[1] = all_component_points.head.nextpos.val.head.val;
		Position<LinkedList<Point>> iter_all_comp_points = all_component_points.head;
		while (iter_all_comp_points.nextpos!=null) {
			LinkedList<Point> component1_points = iter_all_comp_points.val;
			Position<LinkedList<Point>> iter_for_comp1 = iter_all_comp_points.nextpos;
			while (iter_for_comp1!=null) {
				LinkedList<Point> component2_points = iter_for_comp1.val;
				Position<Point> iter_comp1_points = component1_points.head;				
				while (iter_comp1_points!=null) {
					Position<Point> iter_comp2_points = component2_points.head;
					while (iter_comp2_points!=null) {
						Point p1 = iter_comp1_points.val;
						Point p2 = iter_comp2_points.val;
						if (dist(p1,p2)<dist(closest_points[0],closest_points[1])){
							closest_points[0] = p1;
							closest_points[1] = p2;
						}
						iter_comp2_points=iter_comp2_points.nextpos;
					}
					iter_comp1_points=iter_comp1_points.nextpos;
				}
				iter_for_comp1 = iter_for_comp1.nextpos;
			}
			iter_all_comp_points = iter_all_comp_points.nextpos;
		}
		
		
		return closest_points;
	}
	public float dist(Point p1, Point p2) {
		return (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y)+(p1.z-p2.z)*(p1.z-p2.z);
	}
	
}


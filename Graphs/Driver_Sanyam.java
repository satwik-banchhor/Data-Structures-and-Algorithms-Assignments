import java.util.ArrayList;

import java.util.Arrays; 

import java.io.*;
class Driver_Sanyam {


  public static void main(String[] args) {
	try{
		long start = System.currentTimeMillis();
       	    BufferedReader br = null;
            br = new BufferedReader(new FileReader(args[0]));
	    ShapeInterface shape_intr = new Shape();
            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
		//System.out.println("cmd is "+ Arrays.toString(cmd));	

                if (cmd.length == 0) {
                    System.err.println("Error parsing:1 ");
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

		ArrayList<Float> inp = new ArrayList<>();
		 int firstskip=0;
        //printarr(cmd);
		for (String val:cmd) {

			if(0==firstskip){
			firstskip++;
			continue;
			}
//            System.out.println("VALUE: "+ val);
			inp.add(Float.parseFloat(val.trim()));
			System.out.print(val + " ");
		}

        System.out.println("");
		
		//System.out.println("arguments " +Arrays.toString(input.toArray()));


		float[] input = new float[inp.size()]; 
		int k = 0;

		for (Float f : inp) {
		    input[k++] = f; 
		}
                switch (cmd[0]) {
			

		

                    case "ADD_TRIANGLE":
			System.out.println("Add TriangleInterface ");	
			
			System.out.println(shape_intr.ADD_TRIANGLE(input));
                        break;

                    case "TYPE_MESH":
			System.out.println("Checking mesh type");
			int mesh_type = shape_intr.TYPE_MESH();
			System.out.println("Mesh type " + mesh_type);
                        break;
                    case "COUNT_CONNECTED_COMPONENTS":
			System.out.println("Finding number of connected components");
			int count_connected = shape_intr.COUNT_CONNECTED_COMPONENTS();
			System.out.println("Number of connected components = "+ count_connected);
                        break;
                    case "BOUNDARY_EDGES":		
			System.out.println("Getting boundary edges");	

			 EdgeInterface [] boundary_edges= shape_intr.BOUNDARY_EDGES();
             printarr(boundary_edges);
             System.out.println("");
             break;
                    case "IS_CONNECTED":
			System.out.println("CHECKING IS_CONNECTED");	
			float [] triangle1 = new float[9]; 
			float [] triangle2 = new float[9]; 
			for (int i =0;i<9;i++)
			{
				triangle1[i]=input[i];
			}
			for (int i =9;i<18;i++)
			{
				triangle2[i-9]=input[i];
			}
				


			boolean is_con = shape_intr.IS_CONNECTED(triangle1, triangle2);		
			System.out.println("Is connected = " + is_con);
                        break;

                    case "NEIGHBORS_OF_POINT":
			System.out.println("FINDING NEIGHBORS_OF_POINT" );
			 PointInterface [] nbrs_of_point = shape_intr.NEIGHBORS_OF_POINT(input);
             printarr(nbrs_of_point);
             System.out.println("");
			break;

                    case "NEIGHBORS_OF_TRIANGLE":
			System.out.println("FINDING NEIGHBORS_OF_TRIANGLE" );
			TriangleInterface[] nbrs_of_triangles = shape_intr.NEIGHBORS_OF_TRIANGLE(input);
            printarr(nbrs_of_triangles);
            System.out.println("");
			break;

                    case "INCIDENT_TRIANGLES":
			System.out.println("FINDING INCIDENT_TRIANGLES " );
			TriangleInterface[] incident_triangles = shape_intr.INCIDENT_TRIANGLES(input);
            printarr(incident_triangles);
            System.out.println("");
			break;

                    case "VERTEX_NEIGHBOR_TRIANGLE":
			System.out.println("FINDING VERTEX_NEIGHBOR_TRIANGLE " );
			PointInterface[] vertex_neighbour = shape_intr.VERTEX_NEIGHBOR_TRIANGLE(input);
            printarr(vertex_neighbour);
            System.out.println("");
                       	 break;

                    case "EXTENDED_NEIGHBOR_TRIANGLE":
			System.out.println(" FINDING EXTENDED_NEIGHBOR_TRIANGLE " );

			TriangleInterface [] extended_neighbor_triangle = shape_intr.EXTENDED_NEIGHBOR_TRIANGLE(input);
            printarr(extended_neighbor_triangle);
            System.out.println("");
			break;

	          case "MAXIMUM_DIAMETER":
			System.out.println(" Finding diameter " );
			int diameter = shape_intr.MAXIMUM_DIAMETER();
            System.out.println(diameter);
            System.out.println("");

                        break;
                    case "EDGE_NEIGHBOR_TRIANGLE":
			System.out.println(" Finding   EDGE_NEIGHBOR_TRIANGLE ");
			 EdgeInterface [] edge_neighbors_of_triangle = shape_intr.EDGE_NEIGHBOR_TRIANGLE(input);
             printarr(edge_neighbors_of_triangle);
             System.out.println("");
                        break;

                   case "FACE_NEIGHBORS_OF_POINT":
			System.out.println(" Finding   FACE_NEIGHBORS_OF_POINT ");
			 TriangleInterface [] face_nbrs = shape_intr.FACE_NEIGHBORS_OF_POINT(input);
             printarr(face_nbrs);
             System.out.println("");
                        break;



                   case "EDGE_NEIGHBORS_OF_POINT":
			System.out.println(" Finding   EDGE_NEIGHBORS_OF_POINT ");
			 EdgeInterface [] edge_nbrs = shape_intr.EDGE_NEIGHBORS_OF_POINT(input);
             printarr(edge_nbrs);
             System.out.println("");
                        break;

                    case "TRIANGLE_NEIGHBOR_OF_EDGE":
			System.out.println(" Finding TRIANGLE_NEIGHBOR_OF_EDGE ");
			 TriangleInterface [] triangle_neighbors = shape_intr.TRIANGLE_NEIGHBOR_OF_EDGE(input);
             printarr(triangle_neighbors);
             System.out.println("");
			break;
		

	          case "CENTROID":
			System.out.println(" Finding Centroid " );
			PointInterface [] centroid_array = shape_intr.CENTROID();
            printarr(centroid_array);
            System.out.println("");

                        break;
                    case "CENTROID_OF_COMPONENT":
			System.out.println(" Finding CENTROID_OF_COMPONENT ");
			 PointInterface centroid_of_component = shape_intr.CENTROID_OF_COMPONENT(input);
             System.out.println(centroid_of_component);
             System.out.println("");
                        break;

                    case "CLOSEST_COMPONENTS":
			System.out.println(" Finding CLOSEST_COMPONENTS ");
			  PointInterface [] closest_vertices = shape_intr.CLOSEST_COMPONENTS();
              printarr(closest_vertices);
              System.out.println("");
			break;
		


	//	default :System.out.println(cmd[0] +" not found");	
	//		break;
			
                }

            }
            long end = System.currentTimeMillis();
            System.out.println(end-start);
	}
	catch(Exception e)
	{
		System.err.println("Error parsing: 2	 " +e);
        e.printStackTrace();
	}
	

}

public static void printarr(Object[] arr) {
    if(arr==null) {
        System.out.println(arr);
        return ;
    }
    for(int i=0;i<arr.length;i++)
        System.out.println(arr[i]);
}

}

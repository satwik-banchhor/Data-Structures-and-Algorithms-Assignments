import java.io.*;
import java.util.Iterator;
public class Assignment1 {
	static LinkedList<Hostel> allHostels = new LinkedList<Hostel>();
	static LinkedList<Department> allDepartments = new LinkedList<Department>();
	static LinkedList<Course> allCourses = new LinkedList<Course>();
	static LinkedList<Student> allStudents = new LinkedList<Student>();
	private static void getData(String studentrecord , String courserecord){
		File strec = new File(studentrecord);
		File corec = new File(courserecord);
		BufferedReader fileiter = null;
		try {
			fileiter = new BufferedReader(new FileReader(strec));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		  
		String line = "";		
		try {
			line = fileiter.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (line != null) {
			String[] studinfo = line.split(" ",4);
			studinfo[0]=studinfo[0].trim();
			studinfo[1]=studinfo[1].trim();
			studinfo[2]=studinfo[2].trim();
			studinfo[3]=studinfo[3].trim();
			allStudents.add(new Student(studinfo[0],studinfo[1],studinfo[2],studinfo[3]));
			Iterator<Position_<Hostel>> iterH = allHostels.positions();
			boolean foundH = false;
			while (iterH.hasNext() && foundH==false) {
				Position_<Hostel> xpos = iterH.next();
				Hostel x = xpos.value();
				if (x.entname.compareTo(studinfo[3])==0) {
					x.count = x.count + 1;
					foundH = true;
				}				
			}
			if (foundH==false) {
				allHostels.add(new Hostel(studinfo[3],1));
			}
			Iterator<Position_<Department>> iterD = allDepartments.positions();
			boolean foundD = false;
			while (iterD.hasNext() && foundD==false) {
				Position_<Department> xpos = iterD.next();
				Department x = xpos.value();
				if (x.entname.compareTo(studinfo[2])==0) {
					x.count = x.count + 1;
					foundD = true;
				}				
			}
			if (foundD==false) {
				allDepartments.add(new Department(studinfo[2],1));
			}
			try {
				line = fileiter.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		Iterator<Position_<Hostel>> iterH = allHostels.positions();
		while (iterH.hasNext()){
			Position_<Hostel> xpos = iterH.next();
			Hostel x = xpos.value();
			x.Students = new Student[x.count];
		}
		Iterator<Position_<Department>> iterD = allDepartments.positions();
		while (iterD.hasNext()){
			Position_<Department> xpos = iterD.next();
			Department x = xpos.value();
			x.Students = new Student[x.count];
		}
		
		BufferedReader fileiter1 = null;
		try {
			fileiter1 = new BufferedReader(new FileReader(strec));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		  
		String line1 = "";
		
		try {
			line1 = fileiter1.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (line1 != null) {
			String[] studinfo = line1.split(" ",4);
			studinfo[0]=studinfo[0].trim();
			studinfo[1]=studinfo[1].trim();
			studinfo[2]=studinfo[2].trim();
			studinfo[3]=studinfo[3].trim();
			Iterator<Position_<Hostel>> iteH = allHostels.positions();
			boolean foundH = false;
			while (iteH.hasNext() && foundH==false) {
				Position_<Hostel> xpos = iteH.next();
				Hostel x = xpos.value();
				if (x.entname.compareTo(studinfo[3])==0) {
					x.Students[x.hostelindex] = new Student(studinfo[0],studinfo[1],studinfo[2],studinfo[3]);
					x.hostelindex = x.hostelindex+1;
					foundH = true;
				}
			}
			Iterator<Position_<Department>> iteD = allDepartments.positions();
			boolean foundD = false;
			while (iteD.hasNext() && foundD==false) {
				Position_<Department> xpos = iteD.next();
				Department x = xpos.value();
				if (x.entname.compareTo(studinfo[2])==0) {
					x.Students[x.depindex] = new Student(studinfo[0],studinfo[1],studinfo[2],studinfo[3]);
					x.depindex=x.depindex+1;
					foundD = true;
				}
			}
			try {
				line1 = fileiter1.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedReader filereader = null;
		try {
			filereader = new BufferedReader(new FileReader(corec));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		  
		String lin = "";
		
		try {
			lin = filereader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (lin != null) {
			String[] studinfo = lin.split(" ",4);
			studinfo[0]=studinfo[0].trim();
			studinfo[1]=studinfo[1].trim();
			studinfo[2]=studinfo[2].trim();
			studinfo[3]=studinfo[3].trim();
			Iterator<Position_<Student>> iterS = allStudents.positions();
			boolean foundS = false;
			while (foundS==false && iterS.hasNext()) {
				Position_<Student> xpos= iterS.next();
				Student  x = xpos.value();
				if (x.entryNo_.compareTo(studinfo[0])==0){
					foundS = true;
					x.coursecount++;
				}
			}
			Iterator<Position_<Course>> iterC = allCourses.positions();
			boolean foundC = false;
			while (foundC==false && iterC.hasNext()) {
				Position_<Course> xpos = iterC.next();
				 Course  x = xpos.value();
				if (x.entcode.compareTo(studinfo[1])==0){
					foundC = true;
					x.count++;
				}
			}
			if (foundC == false) {
				allCourses.add(new Course(studinfo[3],studinfo[1],1));
			}			
			try {
				lin = filereader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		Iterator<Position_<Student>> iterS = allStudents.positions();
		while (iterS.hasNext()) {
			Position_<Student> xpos = iterS.next();
			Student x = xpos.value();
			x.courseList_=new CourseGrade[x.coursecount];
		}
		Iterator<Position_<Course>> iterC = allCourses.positions();
		while (iterC.hasNext()) {
			Position_<Course> xpos = iterC.next();
			Course x = xpos.value();
			x.Students = new Student[x.count];
		}		
		BufferedReader filescanner = null;
		try {
			filescanner = new BufferedReader(new FileReader(corec));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		  
		
		String linee = "";		
		try {
			linee = filescanner.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (linee != null) {
			String[] studinfo = linee.split(" " , 4);
			studinfo[0]=studinfo[0].trim();
			studinfo[1]=studinfo[1].trim();
			studinfo[2]=studinfo[2].trim();
			studinfo[3]=studinfo[3].trim();
			Iterator<Position_<Student>> iteS = allStudents.positions();
			boolean fouS = false;
			while (fouS==false && iteS.hasNext()) {
				Position_<Student> xpos = iteS.next();
				Student x = xpos.value();
				if(x.entryNo_.compareTo(studinfo[0])==0) {
					CourseGrade new_course_grade = new CourseGrade(studinfo[3],studinfo[1],studinfo[2]);
					x.courseList_[x.courseindex] = new_course_grade;
					x.courseindex=x.courseindex+1;
					x.cgpa_num = x.cgpa_num + 3.0*new_course_grade.grade().num_grade;
					if (studinfo[2].compareTo("I")==0) {
						x.cgpa_denom = x.cgpa_denom + 0.0;
					}
					else {
					x.cgpa_denom = x.cgpa_denom + 3.0 ;
					}
					if (studinfo[2].compareTo("E")==0 || studinfo[2].compareTo("F")==0 || studinfo[2].compareTo("I")==0) {
						x.completedCredits_=String.valueOf(Integer.valueOf(x.completedCredits_)+0);
					}
					else {
						x.completedCredits_=String.valueOf(Integer.valueOf(x.completedCredits_)+3);
					}
					if (Double.compare(x.cgpa_num, 0.0)!=0) {
						x.cgpa_=String.format("%.2f", x.cgpa_num/x.cgpa_denom);
					}					
					fouS = true;
					Iterator<Position_<Course>> iteC = allCourses.positions();
					boolean fouC = false;
					while (fouC == false && iteC.hasNext()) {
						Position_<Course> copos = iteC.next();
						Course co = copos.value();
						if (co.entcode.compareTo(studinfo[1])==0) {
							co.Students[co.courseindex] = x;
							co.courseindex = co.courseindex+1;
							fouC = true;
						}
					}
				}				
			}			
			try {
				linee = filescanner.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	private static void answerQuerries(String query) {
		File qry = new File(query);
		String[] output_array = null;
		int numberoflines=0;
		BufferedReader countiter = null;
		try {
			countiter = new BufferedReader(new FileReader(qry));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		  
		String linetocount = "";
		
		try {
			linetocount = countiter.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (linetocount != null) {
			numberoflines++;			
			try {
				linetocount = countiter.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		output_array = new String[numberoflines];
		int output_index=numberoflines-1;
		BufferedReader fileiter = null;
		try {
			fileiter = new BufferedReader(new FileReader(qry));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		  
		String line = "";
		
		try {
			line = fileiter.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (line != null) {
			String printstring = "";
			String[] qarray = line.split(" ");
			for (int i = 0; i<qarray.length;i++) {
				qarray[i]=qarray[i].trim();
			}
			if (qarray[0].compareTo("SHARE")==0) {
				boolean entfound = false;
				boolean student_found_in_ent = false;
				Iterator<Student_> iterS_find = null;
				Iterator<Student_> iterS_get_others = null;
				String[] Entrynoarray = null;
				Iterator<Position_<Hostel>> iteH = allHostels.positions();
				while (entfound==false && iteH.hasNext()) {
					Position_<Hostel> xpos = iteH.next();
					Hostel x = xpos.value();
					if (x.entname.compareTo(qarray[2])==0) {
						entfound = true;
						iterS_find = x.studentList();
						iterS_get_others = x.studentList();
						Entrynoarray = new String[x.count-1];
					}
				}				
				Iterator<Position_<Department>> iteD = allDepartments.positions();
				while (entfound==false && iteD.hasNext()) {
					Position_<Department> xpos = iteD.next();
					Department x = xpos.value();
					if (x.entname.compareTo(qarray[2])==0) {
						entfound = true;
						iterS_find = x.studentList();
						iterS_get_others = x.studentList();
						Entrynoarray = new String[x.count-1];
					}
				}
				Iterator<Position_<Course>> iteC = allCourses.positions();
				while (entfound==false && iteC.hasNext()) {
					Position_<Course> xpos = iteC.next();
					Course x = xpos.value();
					if (x.entcode.compareTo(qarray[2])==0) {
						entfound = true;
						iterS_find = x.studentList();
						iterS_get_others = x.studentList();
						Entrynoarray = new String[x.count-1];
					}
				}
				if (entfound) {
					while (iterS_find.hasNext() && student_found_in_ent == false) {
						Student_ compare_stud = iterS_find.next();
						if (compare_stud.entryNo().compareTo(qarray[1])==0){
							student_found_in_ent = true;
						}
					}
					if (student_found_in_ent) {
						for (int i = 0 ; i<Entrynoarray.length;i++) {
							Student_ stud = iterS_get_others.next();
							if (stud.entryNo().compareTo(qarray[1])==0){
								stud = iterS_get_others.next();
							}
							Entrynoarray[i] = stud.entryNo();
						}
						Qsort.qsort(Entrynoarray, 0,Entrynoarray.length-1);
						for (int i =0; i<Entrynoarray.length;i++) {
							printstring = printstring + Entrynoarray[i].trim()+" ";
						}
					}				
				}
				printstring.trim();				
			}
			else if (qarray[0].compareTo("COURSETITLE")==0) {
				Iterator<Position_<Course>> iterC = allCourses.positions();
				boolean foundcourse=false;
				while (iterC.hasNext() && foundcourse==false) {
					Position_<Course> xpos = iterC.next(); 
					Course x = xpos.value();
					if (x.entcode.compareTo(qarray[1])==0) {
						foundcourse = true;
						printstring = x.entname.trim();
					}
				}
			}
			else if (qarray[0].compareTo("INFO")==0) {
				Iterator<Position_<Student>> iterS = allStudents.positions();
				boolean foundstudent=false;
				while (foundstudent==false && iterS.hasNext()) {
					Position_<Student> xpos = iterS.next();
					Student x = xpos.value();
					if (x.entryNo().compareTo(qarray[1])==0 || x.name_.compareTo(qarray[1])==0) {
						foundstudent=true;
						String[] courses=new String[x.coursecount];
						Iterator<CourseGrade_> iterCouGrad = new CourseGradeIterator(x); 
						for (int i=0;i<courses.length;i++) {
							CourseGrade_ course = iterCouGrad.next();
							courses[i] = (course.coursenum()+" "+x.courseList_[i].grade_).trim();
						}
						Qsort.qsort(courses, 0, courses.length-1);
						String coursestring = "";
						for (int i=0;i<courses.length;i++) {
							coursestring = coursestring +courses[i]+" ";
						}
						printstring = x.entryNo_+" "+x.name_+" "+x.department_+" "+x.hostel_+" "+x.cgpa_+" ";
						printstring = printstring + coursestring.trim();
					}
				}
			}
			output_array[output_index] = printstring.trim();
			output_index=output_index-1;
			try {
				line = fileiter.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int i=0;i<numberoflines;i++) {
			System.out.println(output_array[i]);
		}
	}
	public static void main(String args[]) {
		String studentrecord = args[0];
		String courserecord = args[1];
		String query = args[2];
		Assignment1.getData(studentrecord,courserecord);
		Assignment1.answerQuerries(query);
	}
}
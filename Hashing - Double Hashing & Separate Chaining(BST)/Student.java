public class Student implements Student_{
	private String fname;
	private String lname;
	private String hostel;
	private String department;
	private String cgpa;
	Student(String f,String l,String h,String d,String c){
		fname = f;
		lname=l;
		hostel=h;
		department=d;
		cgpa=c;
	}
	public String fname() {
		return fname;
	}
	public String lname() {
		return lname;
	}
	public String hostel() {
		return hostel;
	}
	public String department() {
		return department;
	}
	public String cgpa() {
		return cgpa;
	}
	public String key() {
		return fname+lname;
	}
}

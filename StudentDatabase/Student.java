import java.util.Iterator;

public class Student implements Student_ , Iterable<CourseGrade_>{
	String name_;
	String entryNo_;
	String hostel_;
	String department_;
	String completedCredits_;
	String cgpa_;
	int coursecount=0;
	int courseindex=0;
	double cgpa_num;
	double cgpa_denom;
	CourseGrade[] courseList_;
	Student(String a, String b,String c, String d){
		entryNo_ = a;
		name_ = b;
		department_ = c;
		hostel_ = d;
		cgpa_num=0.0;
		cgpa_denom=0.0;	
		completedCredits_="0";
		cgpa_="0.00";
		courseList_=null;
	}
	public String name() {
		return name_;
	}
	public String entryNo() {
		return entryNo_;
	}
	public String hostel() {
		return hostel_;
	}
	public String department() {
		return department_;
	}
	public String completedCredits() {
		return completedCredits_;
	}
	public String cgpa() {
		return cgpa_;
	}
	public Iterator<CourseGrade_> courseList(){
		return this.iterator();
	}
	public Iterator<CourseGrade_> iterator() {
		return new CourseGradeIterator(this);
	}	
}
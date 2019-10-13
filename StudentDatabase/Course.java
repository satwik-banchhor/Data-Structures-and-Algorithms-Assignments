import java.util.Iterator;

public class Course  implements Entity_ , Iterable<Student_>{
	String entname;
	String entcode;
	int count;
	int courseindex=0;
	Student_[] Students;
	Course(String name,String code,int i){
		entname = name;
		entcode = code;
		count = i;
		Students = null;
	}
	public String name() {
		return entname;
	}
	public Iterator<Student_> studentList(){
		return this.iterator();
	}
	public Iterator<Student_> iterator() {
		StudentIterator iterobj = new StudentIterator();
		iterobj.SArray = Students;
		return iterobj;
	}	
}

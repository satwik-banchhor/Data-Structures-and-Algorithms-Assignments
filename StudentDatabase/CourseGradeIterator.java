import java.util.Iterator;

public class CourseGradeIterator  implements Iterator<CourseGrade_>{
	CourseGrade[] clist;
	int index;
	CourseGradeIterator(Student s){
		clist = s.courseList_;
		index = -1;
	}
	public boolean hasNext() {
		return (index+1<clist.length);
	}
	public CourseGrade_ next() {
		index++;
		return clist[index];
	}

}

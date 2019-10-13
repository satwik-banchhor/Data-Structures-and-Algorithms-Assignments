README

In this Assignment the following data structures and algorithms were implemented:-
	(i) Linked Lists
	(ii) Java Iterators were used to iterate the Linked Lists
	(iii) Quick Sort

I made the following .java files for Assignment1:-

1.Position_ : Interface : Taken as specified in Assignment 1 webpage:-
	1.1 Method Signatures:
		1.1.1 public T value(); --Return value at position
   		1.1.2 public Position_<T> after(); --Returns the position after this position in its list

2. Position : Class implementing Positoin_<T> interface :-
	2.1 Fields :-
		2.1.1 public T val; --The value of type T at this position
		2.1.2 public Position<T> nextpos;  --The position after this position
	2.2 Constructor :-
		Initializes the fields to null
	2.3 Methods :-
		2.3.1 public T value() --Return value at position --val
   		2.3.2 public Position_<T> after() --Returns nextpos (the next position in the list)

3.LinkedList_ : Interface :-
	3.1 Method Signatures:-
		3.1.1 public Position<T> add(T,e); --adds element e to the list and returns its position in the list
		3.1.2 public Iterator<Position_<T>>  positions(); --Returns an iterator for all positions in the list
		3.1.3 public int count(); --Returnsnumber of ekements in the list

4.LinkedList : Class implementing LinkedList<T> and Iterable<Position_<T>> :-
	4.1 Fields:-
		4.1.1 int length; --Length of the linked list 
		4.1.2 Position<T> head; --Head of the linked list
		4.1.3 Position<T> currentpos; --A position in the list
	4.2 Constructor :-
		Initializes the field head by creating a new object of type Position<T>.
		Sets current position to head.
	4.3 Methods :-
		4.3.1 public int count() --Returns count
		4.3.2 public Position_<T> add(T e) -- Runs a loop and takes current pos to the last non-null position of the linked list sets the next-position of the current position to a new Position with value e and changes the current position to the next position i.e. the one with value e and returns that position.
		4.3.4 public Iterator<Position_<T>> iterator() --Returns an iterator to iterate through the linked list by creating a new object of the type LListIterator that implements Iterator<Position_<T>>
		4.3.5 public Iterator<Position_<T>> positions() --Returns an iterator of the linked list by calling the iterator() function
	4.4 LListIterator class:-class LListIterator implements Iterator<Position_<T>>
			4.4.1 Fields :-
				4.4.1.1 Position<T> cursor -- current position while iterating
			4.4.2 Constructor -- Initializes cursor to head
			4.4.3 Methods :-
				4.4.3.1 hasNext() -- Returns (cursor.nextpos != null) i.e. True when cursor is somewhere in the middle of the linked list such that the next position is not null and False when the cursor is in the end of the linked list and the next position is null
				4.4.3.2 next() -- Shifts the cursor to the next position

5. Entity_ : Interface :-
	5.1 Method Signatures:-
		5.1.1 name() -- Returns the name of the entity
		5.1.2 studentList() -- Returns an iterator of type Iterator<Student_> for iterating through all students in this entity
		
6. Hostel : Class implementing Entity and Iterable<Student_> :-
	6.1 Fields :-
		6.1.1 entname -- name of the hostel (an entity)
		6.1.2 Students -- array of the type Student_[] containing all students in thsi hostel
		6.1.3 count -- number of students in the hostel
		6.1.4 hostelindex -- index upto which (not including) Students are filled in the Students array of the entity
	6.2 Constructor:-
		Takes two arguements String name and int i
		Sets entname to name and count to i
		Initializes the array Students as null
	6.3 Fields :-
		6.3.1 name() -- returns the name of the hostel entname
		6.3.2 studentList -- returns an iterator of the type Iterator<Student_> to iterate through the students of the hostel in the array Students by calling the function itertor with the reference of the current object of the class ('this')
		6.3.3 iterator() -- returns an object of the type StudetIterator that iterates through an array SArray which is set to the array Students. Therefore the method returns an iterator that iterates through the Students array of the hostel
				
7. Department : Class implementing Entity and Iterable<Student_> :-
	7.1 Fields :-
		7.1.1 entname -- name of the department (an entity)
		7.1.2 Students -- array of the type Student_[] containing all students in thsi department
		7.1.3 count -- number of students in the department
		7.1.4 depindex -- index upto which (not including) Students are filled in the Students array of the entity
	7.2 Constructor:-
		Takes two arguements String name and int i
		Sets entname to name and count to i
		Initializes the array Students as null
	7.3 Fields :-
		7.3.1 name() -- returns the name of the department entname
		7.3.2 studentList -- returns an iterator of the type Iterator<Student_> to iterate through the students of the department in the array Students by calling the function itertor with the reference of the current object of the class ('this')
		7.3.3 iterator() -- returns an object of the type StudetIterator that iterates through an array SArray which is set to the array Students. Therefore the method returns an iterator that iterates through the Students array of the department.
		
8. Course : Class implementing Entity and Iterable<Student_> :-
	8.1 Fields :-
		8.1.1 entname -- name of the course (an entity)
		8.1.2 Students -- array of the type Student_[] containing all students in thsi course
		8.1.3 count -- number of students in the course
		8.1.4 courseindex -- index upto which (not including) Students are filled in the Students array of the entity
	8.2 Constructor:-
		Takes two arguements String name and int i
		Sets entname to name and count to i
		Initializes the array Students as null
	8.3 Fields :-
		8.3.1 name() -- returns the name of the course entname
		8.3.2 studentList -- returns an iterator of the type Iterator<Student_> to iterate through the students of the course in the array Students by calling the function itertor with the reference of the current object of the class ('this')
		8.3.3 iterator() -- returns an object of the type StudetIterator that iterates through an array SArray which is set to the array Students. Therefore the method returns an iterator that iterates through the Students array of the course.
			
9. StudentIterator : Class that implements Iterator<Student_> :-
	9.1 Fields :-
		9.1.1 
		index : index of the current position at which we are in the array
		9.1.2 SArray : Array that the iterator is iterating through
	9.2 Constructor : 
		Initializes index to -1. I used -1 for initialization of index as I would want to refer to the first element of the array by a next() method called using an object of the StudentIterator type when I start iterating.
	9.3 Methods :-
		9.3.1 hasNext() -- returns true is index+1 <SArray.length and false otherwise i.e. it returns false when the index is equal to the index of the last element of the array and true when index is between its initial value -1 and SArray.length-2
		9.3.2 next() -- shifts the index to the next value and returns the Student in the array at that index.
		
10. CourseGrade_ : Interface used as specified

11. CourseGrade : Class implementing interface CourseGrade_ :-
	11.1 Fields :- 
		11.1.1 coursetitle_ -- title of the course
		11.1.2 coursenum_ -- course code
		11.1.3 grade_ -- grade of the current student in this course. Current student is the student in whose courselist_ array the object of the CourseGrade is present.
	11.2 Constructor
		Takes 3 string arguements title, num and grad and sets them to the 3 corresponding fields.
	11.3 Methods :-
		11.3.1 coursetitle() -- returns coursetitle_ 
		11.3.2 coursenum() -- returns coursenum__
		11.3.3 grade() -- returns an object of the type GradeInfo_ by creating a new object of the type GradeInfo and returning it using which we can find out the letter and number grade of the current student in the current course.
		
12. GradeInfo_ : Interface used as specified
	
13. GradeInfo : Class implementing interface GradeInfo_
	13.1 Fields :-
		13.1.1 letter_grade -- grade in letters (A Aminus etc)
		13.1.2 num_grade -- gradepoint (10 9 etc)
	13.2 Constructor :-
		Takes 1 String arguement grade
		Sets letter_grade to grade
		Sets num_grade to grade point using the gradepoint function defined in the interface GradeInfo_

14. CourseGradeIterator : class that implements Iterator<CourseGrade_> :- 
	14.1 Fields :-
		14.1.1 clist -- The CourseGrade[] array that the iterator iterates through
	14.2 Constructor :-
		Takes arguement Student s (whose CourseGrade array is to be iterated through)
		Sets clist to the courseList_ of the student taken as arguement
		Sets index to  -1
	14.3 Methods :-
		14.3.1 hasNext() -- returns true is index+1 <clist.length and false otherwise i.e. it returns false when the index is equal to the index of the last element of the array and true when index is between its initial value -1 and SArray.length-2
		14.3.2 next() -- shifts the index to the next value and returns the CourseGrade_ in the array at that index.
		
15. Student_ : Interface used as specified

16. Student : Class that implements Student_ and Iterable<CourseGrade_> :-
	16.1 Fields :-
		16.1.1 name_ -- name of the student
		16.1.2 entryNo_ -- entry number of the student
		16.1.3 hostel_ -- hostel of the student
		16.1.4 department_ -- department of the student
		16.1.5 completedCredits -- credits completed by the student 
		16.1.6 cgpa_ -- student's cgpa upto 2 decimal places
		16.1.7 coursecount -- number of courses the student is registered to
		16.1.8 courseindex -- index of the courseList_ upto which( current value of index is not included) CourseGrades of the student have been filled in the array courseList_ 
		16.1.9 cgpa_num -- Numerator used for calculating cgpa 
		16.1.10 cgpa_denom -- Denominator used to calculate cgpa
		16.1.11 courseList_ -- array containing CourseGrades of all the courses the student is registerd to
	16.2 Constructor --
		Takes 4 arguements a,b,c,d and sests entroNo_,name_,department_ and hostel_
		Initializes cgpa_num and cgpa_denom to 0.0, completedCredits_ to "0"
		Initializes cgpa_ to "0.00"
		Initializes courseList_ to null
	16.3 Methods :-
		16.3.1 name() -- returns name_
		16.3.2 entryNo() -- returns entryNo_
		16.3.3 hostel() -- returns hostel_
		16.3.4 department() -- returns department_
		16.3.5 completedCredits() -- returns completedCredits_
		16.3.6 cgpa() -- returns cgpa_
		16.3.7 courseList() -- returns an iterator that iterates through all the CpurseGrades of the student present in the array courseList_ by calling the method iterator() with the reference of the current student
		16.3.8 iterator() -- returns an iterator that iterates through all the CpurseGrades of the student present in the array courseList_ by creating a new object of the type CourseGradeIterator

17. Qsort : Class that has a static void method qsort which is used to sort an array of strings using the quick sort algorithm :-
	17.1 Methods :-
		Two static methods :--
		17.1.1 partition(String[] list, int i, int j) :-
			Partitions the string array "list" between the indices i and j (both inclusive) by taking the element at the ith index as the pivot using two pointer method
		17.1.2 qsort(String[] list, int i, int j) :--
			Applies the quick sort algorithm to sort the string array "list" between the indices i and j (both inclusive) by first partitioning the array[i..j] about the pivot array[i] and then quicksorting the two parts created as a consequence recursively.
			
18. Assignment1 : Class :-
	18.1 Fields :-
		18.1.1 static LinkedList<Hostel> allHostels -- linked list maintained to store all Hostel entities
		18.1.2 static LinkedList<Department> allDepartments -- linked list maintained to store all Department entities
		18.1.3 static LinkedList<Course> allCourses -- linked list maintained to store all Course entities
		18.1.4 static LinkedList<Student> allStudents -- linked list maintained to store all Students
	18.2  Methods :-	
		18.2.1 private static void getData(String studentrecord , String courserecord) :-

			The two string arguements are file names and they are converted to file handles strec (studentrecord) and corec (courserecord) respectively.

			A BufferedReader fileiter is created for the file strec.
			Using a while loop the file is read line by line for the firse time.
			For each line a new Student is added to the all Students linked list using the information read frim the line.
			And for the entities hostel and department of a given line first their respective linked lists are iterated to search whether they have been already made or not if yes then the sudent count for the respective entity is increased by 1 if not then a new entity with the name taken from the line and the initial student count 1 is created and added to the linked list.
			The above process is repeated for each line till we reach the end of the student record file and we exit the while loop.
			
			At this point we have filled the allStudents linked list with Student objects but with information of their name, entryno, hostel and department only.
			And we have also filled the allHostels and allDepartments linked lists with their respective entities with only the information of their name and the student counts for each entity.
			So we initialize the Students array of each entity of both the linked lists with new Student[respective_count].

			Now, to fill the Students array of the two linked lists we iterate through the student record file once again using the BufferedReader fileiter1 and another while loop. We read the file line by line once again and now for each line we get a student and search the allStudents array to get that students object.Then we search for the hostel and department mentioned in the line in their respective linked lists to get their objects. Then we add the student's object to the Students array of the hostel and department objects using the remembered index upto which Students array was filled and increasing this index by 1 . Therefore, we fill one Student each in the Students array of the entity whose name is in that line by searching for that entity in its respective linked lists.
			At the end of the file we exit the while loop.
			
			At this point we have gathered all the information from the student record file and filled all the data structures that we could using this information.
			
			Next we use the corec file handle to create a BufferedReader fo rht ecourses.txt file.
			
			Using the buffered reader we start reading the file line by line using another while loop. From each line we gather a student's entry number and his grade in one of his registered courses and that course's complete information.
			First we search in the allStudents linked list and get the object of the Student whose entry number us mentioned in the line. Then we increas the course count for this student's object by 1.
			Then search the allCourses linked list to know whether an object of the course mentioned in the line has been already created or not if yes then the student count of that course's object is increases by one if not then a new object is created for that course with the information gathered and the initial count set to 1 and it is added to the allCourses linked list.
			
			After we have read the course file once completely we exit the while loop.
			At this point we have the course count for each student in the allStudents linked list therefore we iterate through the linked list and cinitialize the courseList_ array for each student by an array of the size of their respective coursecounts.
			Next we also have the student count for each course therefore we iterate throught the allCourses linked list and initialize the Students array of each course using that course's student count.
			
			Now to completely fill allStuents and allCourses we create another BufferedReader "filescanner" for the courses file with handle corec and read the file line by line. For each line using the netry number mentioned we first fine the student's object by iterating in the allStudents linked list. We create a new CourseGrade object using the information mentioned in the line and add it to the remembered courseindex of the student's object and increase the remembered index for the student's object by 1.
			We also update the cgpa_num, cgpa_denom, completedCredits_ and cgpa_ fields of the student's object.
			Then using the information of the course mentioned we find that course's object in the allCourses linked list and add the Student's object that we had found earlier to that course's Students array at the remembered courseindex and increment it by 1.
			
			With the end of this while loop we have all the data structures completely filled i.e. we have gathered all the information from the students and courses files.
			
		18.2.2 private static void answerQuerries(String query) :-
			We first creat a File handle for the query file using the String taken as arguement which is the name of the file.
			Then we create a String[] array outputarray that would store the outputs to each queries in the query file as we have to give the outputs in opposite orderwe would need to store them in an array so as to remember the order.
			Now we create a buffered reader for the file corec "countiter" and read the file line by line just to count the number of lines and using this count we initialize the outputarray using the number of lines as its size.
			Now we will find the output of each query and fill it in the output array in opposite fashion i.e. the output to the first query will be filled in the last position of the output array. We do this by remembering the index for in which we have to fill the outputarray as output_index and decreasing it by 1 after filling each output in the outputarray.
			To find the output to each query we read the query file line by line once again using another BufferedReader fileiter and another while loop.
			For each line we firsh identify the type of theh query and then find its output.
			
			If the query is a SHARE query then we get an entry number of a student and an entity name. We iterate through allStudents and get the student's object using the entry number similarly we get the entity's object by iterating through all the 3 linked lists.We then check whether the student's object belongs to the Students array of the entity's object if not then we take the output printstring as an empty line, if yes then we collect the entry numbers of all the students in the entity's Students array in another array except the current student himself and then sort this array. Using the sorted entry number array we create the output string by iterating and concatenating the entry numbers with 1 space between each of them and no spaces in teh end.
			
			If the query is a COURSETITLE query then we iterate through allCourses ans find the object for the mentioned course and add to the output array its course title.
			
			If the query is an INFO query then we first find the object of the student using the name or the entry number of the student given in the query by iterating through the allStudents array. Then we create a courses array with size equal to the course count of the student's object. We add to the courses array strings of the form course_code+" "+Student's_grade_in_this_course for all the courses by iterating through the courses and then sort this array. NOTE: This sorted array will have the same order as that when we sort the courses with their codes as the codes are unique and each string in our courses array begins with a course code. We iterate through the courses array and create the required concatenated course string and make the printstring in the required format by concatenating the required student's fields and the course string at the end. We add this print string created to the output array.
			
			We iterate through the output array and print the outputs in the required order.
			
		18.2.3 public static void main(String args[])
			Takes as command line arguements three file names.
			Calls the getData method passing the first two command line arguements.
			Calls the answerQuerries method passing the third command line arguement.
			
			
			
			
			
			
			

package ProjectManagement;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import Trie.Trie;
import Trie.TrieNode;
import PriorityQueue.MaxHeap;
import PriorityQueue.Priority;

public class Scheduler_Driver extends Thread implements SchedulerInterface {
	public Trie<User> users;
	public Trie<Project> projects;
	public ArrayList<Project> projectsAL;
	public MaxHeap<Job> jobsMH;
	public Trie<Job> jobs;
	public ArrayList<Job> completedjobsAL;	
	public MaxHeap<Job> notcompletedjobsMH;
	public int job_number=1;
	public int projectnumber=1;
	public int global_time=0;
	//New Data-Structures:
	public ArrayList<User> OrderedUsers;
	public ArrayList<Job> PriorityOrderedJobs;
	
	Scheduler_Driver() {
		users=new Trie<User>();
		projects=new Trie<Project>();
		projectsAL=new ArrayList<Project>();
		jobsMH=new MaxHeap<Job>();
		jobs=new Trie<Job>();
		completedjobsAL=new ArrayList<Job>();
		notcompletedjobsMH=new MaxHeap<Job>();
		//New Data-Structures:
		OrderedUsers=new ArrayList<User>();//OrderedUsers_initialize
		PriorityOrderedJobs=new ArrayList<Job>();//PriorityOrderedJobs_initialize
	}

    public static void main(String[] args) throws IOException {
//

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }

    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
    	int index=0;
    	int size = OrderedUsers.size();
    	ArrayList<UserReport_> return_ArrayList = new ArrayList<UserReport_>();
    	while (index<top & index< size) {
    		return_ArrayList.add(OrderedUsers.get(index));
    		index++;
    	}
        return return_ArrayList;
    }

    @Override
    public void timed_flush(int waittime) {
		//a.Iterate through the PriorityOrderedJobs
		//b.Execute the jobs with waiting time more >= waittime. Compare waiting time with the current wait time
		//c.Increase global time. 
		//d.Decrease Project budget. 
		//e.Increase User's budgetconsumed
		//f.Set job's endtime 
		//g.Update the status of the job
		//h.Add them to the completedjobsAL
		//i.Add the jobs that are not being executed to a temporaryPriorityOrderedJobs ArrayList
		//j.Iterate through the heap add the not finished jobs to a temporary heap
		//k.Set notcompletedjobsMH to : Heapified temporary heap
		//l.Set PriorityOrderedJobs to temporaryPriorityOrderedJobs
		//m.Merge sort OrderedUsers
		//n.Update the consumption indices by iteratng through the OrderedUsers 	
    	int initialglobaltime = global_time;
    	ArrayList<Job> tempPOJ = new ArrayList<Job>();
    	ArrayList<Priority<Job>> tempheap = new ArrayList<Priority<Job>>();
    	ArrayList<Priority<Job>> heap = jobsMH.heap;
    	for (int i=0;i<PriorityOrderedJobs.size();i++) {//a
    		Job job = PriorityOrderedJobs.get(i);
        	if (waittime<=initialglobaltime-job.arrival_time & job.runtime<=job.project.budget) {//b
        		//b to h
        		global_time = global_time + job.runtime;//c
        		job.project.budget = job.project.budget - job.runtime;//d
        		job.user.budgetconsumed = job.user.budgetconsumed + job.runtime;//e
        		job.end_time = global_time;//f
        		job.status = Status.finished;//g
        		completedjobsAL.add(job);//h        		
        	}
        	else {
        		tempPOJ.add(job);//i
        	}
    	}
    	for (int i=0;i<heap.size();i++) {//j
    		Priority<Job> p = heap.get(i);
    		if (p.priority.status==Status.notfinished) {
    			tempheap.add(p);
    		}
    	}
    	//Heapify tempheap
    	heap = tempheap;//k
    	jobsMH.heap = heap;
    	int index = heap.size()/2;
    	while (index>=0) {
    		bubbledown(heap,index);
    		index--;
    	}
    	PriorityOrderedJobs = tempPOJ;//l
    	OrderedUsers = mergeSort(OrderedUsers);//m
    	for (int i=0;i<OrderedUsers.size();i++) {//n
    		OrderedUsers.get(i).consumptionindex=i;
    	}
    }
    
    private ArrayList<User> mergeSort(ArrayList<User> OrderedUser) {
    	if (OrderedUser.size()==1 || OrderedUser.size()==0) {
			return OrderedUser;
		}
		else {
			ArrayList<User> firsthalf = new ArrayList<User>();
			ArrayList<User> secondhalf = new ArrayList<User>();
			ArrayList<User> newfirsthalf = new ArrayList<User>();
			ArrayList<User> newsecondhalf = new ArrayList<User>();
			int size = OrderedUser.size();
			for (int i=0;i<size/2;i++) {
				firsthalf.add(OrderedUser.get(i));
			}
			for (int i=size/2;i<size;i++) {
				secondhalf.add(OrderedUser.get(i));
			}
			newfirsthalf = mergeSort(firsthalf);
			newsecondhalf = mergeSort(secondhalf);
			return Merge(newfirsthalf,newsecondhalf);
		}
	}

	private ArrayList<User> Merge(ArrayList<User> a1, ArrayList<User> a2) {
		int size = 0;
		ArrayList<User> return_ArrayList = new ArrayList<User>();
		int size1 = a1.size();
		int size2 = a2.size();
		int reqsize = size1 + size2;
		int j1=0;
		int j2=0;
		while (size<reqsize) {
			if (size1==j1) {
				for (int j2i=j2;j2i<size2;j2i++) {
					return_ArrayList.add(a2.get(j2i));
				}
				size = size + size2;
				j2 = size2;
				return return_ArrayList;
			}
			else if (size2==j2) {
				for (int j1i=j1;j1i<size1;j1i++) {
					return_ArrayList.add(a1.get(j1i));
				}
				size = size + size1;
				j1 = size1;
				return return_ArrayList;
			}
			else {
				if (a1.get(j1).budgetconsumed<a2.get(j2).budgetconsumed) {
					return_ArrayList.add(a1.get(j1));
					size++;
					j1++;
				}
				else {
					return_ArrayList.add(a2.get(j2));
					size++;
					j2++;
				}
			}
		}
		return return_ArrayList;
	}

	public void bubbledown(ArrayList<Priority<Job>> heap, int index) {
    	int lastindex = heap.size()-1;
    	boolean positionfound = false;
    	while (!positionfound) {
    		if (2*index>=lastindex) {
    			positionfound=true;
    		}
    		else if (2*index+1==lastindex) {
    			Priority<Job> leftchild = heap.get(2*index+1);
    			Priority<Job> node = heap.get(index);
    			if (node.compareTo(leftchild)<=0) {
    				heap.set(index, leftchild);
    				heap.set(2*index+1, node);
    				index = 2*index + 1;
    			}
    			positionfound=true;
    		}
    		else {
    			Priority<Job> leftchild = heap.get(2*index+1);
    			Priority<Job> node = heap.get(index);
    			Priority<Job> rightchild = heap.get(2*index+2);
    			if (node.compareTo(leftchild)>0 & node.compareTo(rightchild)>0) {
    				positionfound=true;
    			}
    			else if (leftchild.compareTo(rightchild)<0){
    				heap.set(index, rightchild);
    				heap.set(2*index+2, node);
    				index = 2*index+2;
    			}
    			else {
    				heap.set(index, leftchild);
    				heap.set(2*index+1, node);
    				index = 2*index + 1;
    			}
    		}
    	}
    }

    private ArrayList<JobReport_> handle_new_priority(String s) {
    	int cutoff_priority = Integer.parseInt(s);
    	int size = PriorityOrderedJobs.size();
    	int index = 0;
    	ArrayList<JobReport_> return_ArrayList = new ArrayList<JobReport_>();
    	while (index<size && PriorityOrderedJobs.get(index).priority<=cutoff_priority) {
    		return_ArrayList.add(PriorityOrderedJobs.get(index));
    		index++;
    	}
        return return_ArrayList;
    }

    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
    	String projectname = cmd[1];
    	String username = cmd[2];
    	int t1 = Integer.parseInt(cmd[3]);
    	int t2 = Integer.parseInt(cmd[4]);
    	ArrayList<JobReport_> return_ArrayList = new ArrayList<JobReport_>();
    	ArrayList<Job> searchjobs; 
    	TrieNode<User> usernode=users.search(username);
    	if (usernode==null) {
    		return return_ArrayList;
    	}
    	User user = usernode.getValue();
    	TrieNode<ArrayList<Job>> searchjobsnode = user.UserProjectTrie.search(projectname);
    	if (searchjobsnode == null) {
    		return return_ArrayList;
    	}
    	searchjobs = searchjobsnode.getValue();
    	if (searchjobs == null) {
    		return return_ArrayList;
    	}
    	else {
    		ArrayList<Job> notcompletedcommonjobs = new ArrayList<Job>();
    		ArrayList<Job> completedcommonjobs = new ArrayList<Job>();
    		int t1_index = binarysearch_t1pos(searchjobs,t1);
    		for (int i=t1_index;i<searchjobs.size() && searchjobs.get(i).arrival_time<=t2;i++) {
    			Job currentjob = searchjobs.get(i);
    			if (currentjob.status == Status.finished) {
    				completedcommonjobs.add(currentjob);
    			}
    			else {
    				notcompletedcommonjobs.add(currentjob);
    			}
    		}
    		completedcommonjobs = mergesort(completedcommonjobs);
    		return_ArrayList.addAll(completedcommonjobs);
    		return_ArrayList.addAll(notcompletedcommonjobs);
    		return return_ArrayList;
    	}
    }

    private int binarysearch_t1pos(ArrayList<Job> searchjobs, int t1) {
    	int size = searchjobs.size();
    	if (t1<=searchjobs.get(0).arrival_time()) {
    		return 0;
    	}
    	else if (searchjobs.get(size-1).arrival_time()<t1) {
    		return size;
    	}
    	else {
    		int start = 1;
    		int end = size-1;
    		boolean posfound = false;
    		int mid = (start + end)/2;
    		while (!posfound) {
    			if (start==end) {
    				return start;
    			}
    			if (searchjobs.get(mid-1).arrival_time()<t1 && t1<=searchjobs.get(mid).arrival_time()) {
    				return mid;
    			}
    			else if (searchjobs.get(mid-1).arrival_time()>=t1) {
    				end = mid - 1;
    				mid = (start+end)/2;
    			}
    			else {
    				start = mid + 1;
    				mid = (start+end)/2;
    			}
    		}
    	}
    	System.out.println("OUT BINARY SEARCH NOT POSSIBLE?????");
		return 0;
	}
    
	private ArrayList<Job> mergesort(ArrayList<Job> completedcommonjobs) {
		if (completedcommonjobs.size()==1) {
			return completedcommonjobs;
		}
		else {
			ArrayList<Job> firsthalf = new ArrayList<Job>();
			ArrayList<Job> secondhalf = new ArrayList<Job>();
			int size = completedcommonjobs.size();
			for (int i=0;i<size/2;i++) {
				firsthalf.add(completedcommonjobs.get(i));
			}
			for (int i=size/2;i<size;i++) {
				secondhalf.add(completedcommonjobs.get(i));
			}
			firsthalf = mergesort(firsthalf);
			secondhalf = mergesort(secondhalf);
			return merge(firsthalf,secondhalf);
		}		
	}
	
	
	private ArrayList<Job> merge(ArrayList<Job> a1, ArrayList<Job> a2) {
		int size = 0;
		ArrayList<Job> return_ArrayList = new ArrayList<Job>();
		int size1 = a1.size();
		int size2 = a2.size();
		int reqsize = size1 + size2;
		int j1=0;
		int j2=0;
		while (size<reqsize) {
			if (size1==j1) {
				for (int j2i=j2;j2i<size2;j2i++) {
					return_ArrayList.add(a2.get(j2i));
				}
				size = size + size2;
				j2 = size2;
				return return_ArrayList;
			}
			else if (size2==j2) {
				for (int j1i=j1;j1i<size1;j1i++) {
					return_ArrayList.add(a1.get(j1i));
				}
				size = size + size1;
				j1 = size1;
				return return_ArrayList;
			}
			else {
				if (a1.get(j1).end_time<a2.get(j2).end_time) {
					return_ArrayList.add(a1.get(j1));
					size++;
					j1++;
				}
				else {
					return_ArrayList.add(a2.get(j2));
					size++;
					j2++;
				}
			}
		}
		return return_ArrayList;
	}

	
	private ArrayList<JobReport_> handle_new_user(String[] cmd) {
		String username = cmd[1];
    	int t1 = Integer.parseInt(cmd[2]);
    	int t2 = Integer.parseInt(cmd[3]);
    	ArrayList<JobReport_> return_ArrayList = new ArrayList<JobReport_>();
    	TrieNode<User> usernode=users.search(username);
    	if (usernode==null) {
    		return return_ArrayList;
    	}
    	User user = usernode.getValue();
    	ArrayList<Job> searchjobs = user.CreationTimeOrderedJobs;
    	int t1_index = binarysearch_t1pos(searchjobs,t1);
    	for (int i=t1_index;i<searchjobs.size() && searchjobs.get(i).arrival_time<=t2;i++) {
    		return_ArrayList.add(searchjobs.get(i));
    	}
        return return_ArrayList;
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
    	String projectname = cmd[1];
    	int t1 = Integer.parseInt(cmd[2]);
    	int t2 = Integer.parseInt(cmd[3]);
    	TrieNode<Project> projectnode=projects.search(projectname);
    	Project project = projectnode.getValue();
    	ArrayList<Job> searchjobs = project.CreationTimeOrderedJobs;    	
    	int t1_index = binarysearch_t1pos(searchjobs,t1);
    	ArrayList<JobReport_> return_ArrayList = new ArrayList<JobReport_>();
    	for (int i=t1_index;i<searchjobs.size() && searchjobs.get(i).arrival_time<=t2;i++) {
    		return_ArrayList.add(searchjobs.get(i));
    	}
        return return_ArrayList;
    }

    public void schedule() {
            execute_a_job();
    }
    
    // Timed queries for the old queries. These are equivalent to their untimed parts.
    // Only they should not print anything so the real code is timed.
    public void timed_handle_user(String name){
//    	System.out.println("Creating user");
    	User newuser = new User(name);		//a.Create user object
    	users.insert(name, newuser);//b.Add to trie users
    	//Maintaining new Data-Structures
    	newuser.consumptionindex=OrderedUsers.size();//consumptionindex_update_i
    	OrderedUsers.add(newuser);//OrderedUsers_update_i
    }
   
    public void timed_handle_job(String[] cmd){
    	//System.out.println("Creating job");
    	String jobname=cmd[1];
    	String projectname=cmd[2];
    	String username=cmd[3];
    	int runningtime=Integer.valueOf(cmd[4]);
    	TrieNode<Project> projectnode=projects.search(projectname);
    	TrieNode<User> usernode=users.search(username); 		//a.Check :
    	if (projectnode==null) {						//i.Project name existence
    		//System.out.println("No such project exists. "+projectname);
    	}
    	else if (usernode==null) {						//ii.User name existence
    		//System.out.println("No such user exists: "+username);
    	}
    	else {   					//b.c.d.
    		Project project=projectnode.getValue();
    		User user=usernode.getValue();
    		Job newjob = new Job(jobname,project,user,runningtime,job_number);//b.Create job object
    		newjob.arrival_time=global_time;//New: Setting current time as the arrival time of the new job
    		job_number++;
    		jobs.insert(jobname, newjob);//c.Add to trie jobs
    		jobsMH.insert(newjob);//d.Add to heap jobsMH
    	}
    }
    public void timed_handle_project(String[] cmd){
//    	System.out.println("Creating project");
    	String projectname=cmd[1];
    	int priority=Integer.valueOf(cmd[2]);
    	int budget=Integer.valueOf(cmd[3]);
    	Project newproject=new Project(projectname,priority,budget,projectnumber);//a.Create a project object
    	projectnumber++;
    	projects.insert(projectname, newproject);//b.Add to the trie projects
    	projectsAL.add(newproject);//c.Add to the ArrayList projectsAL
    }
    public void timed_run_to_completion(){
    	Job maxpriorityjob=jobsMH.extractMax();//a.Extrace job
    	while (maxpriorityjob!=null) {
    		System.out.println("Running code");
        	System.out.println("Remaining jobs: "+(jobsMH.heap.size()+1));
        	boolean executable=maxpriorityjob.runtime<=maxpriorityjob.project.budget;
        	while (maxpriorityjob!=null & !executable) {//Till executable job is not found
        		//Non Executable Budget
        		System.out.println("Executing: "+maxpriorityjob.name+" from: "+maxpriorityjob.project.name);
        		System.out.println("Un-sufficient budget.");
    			maxpriorityjob.project.notcompletedjobsAL.add(maxpriorityjob);//1.Add to project’s notcompletedjobsAL of its project
    			maxpriorityjob=jobsMH.extractMax();//2.Extract another project and repeat
    			if (maxpriorityjob!=null) {
    				executable=maxpriorityjob.runtime<=maxpriorityjob.project.budget;
    			}
        	}
        	if (executable) {
	 			System.out.println("Executing: "+maxpriorityjob.name+" from: "+maxpriorityjob.project.name);
	   			maxpriorityjob.project.budget=maxpriorityjob.project.budget-maxpriorityjob.runtime;//i.Reduce project’s budget
	   			this.global_time=this.global_time+maxpriorityjob.runtime;//ii.Update Global time
	   			maxpriorityjob.end_time=this.global_time;//iii.Set job’s end_time
	    		completedjobsAL.add(maxpriorityjob);//iv.Add project to completedjobsAL
	    		maxpriorityjob.status=Status.finished;//v.Update the status of the job
	    		System.out.println("Project: "+maxpriorityjob.project.name+" budget remaining: "+maxpriorityjob.project.budget);
	    		System.out.println("System execution completed");
	    		maxpriorityjob=jobsMH.extractMax();//vi.Extract another project and repeat
    		}
    	}
    }

    public void run_to_completion() {
    	Job maxpriorityjob=jobsMH.extractMax();//a.Extrace job
    	while (maxpriorityjob!=null) {
    		System.out.println("Running code");
        	System.out.println("Remaining jobs: "+(jobsMH.heap.size()+1));
        	boolean executable=maxpriorityjob.runtime<=maxpriorityjob.project.budget;
        	while (maxpriorityjob!=null & !executable) {//Till executable job is not found
        		//Non Executable Budget
        		System.out.println("Executing: "+maxpriorityjob.name+" from: "+maxpriorityjob.project.name);
        		System.out.println("Un-sufficient budget.");
    			maxpriorityjob.project.notcompletedjobsAL.add(maxpriorityjob);//1.Add to project’s notcompletedjobsAL of its project
    			maxpriorityjob=jobsMH.extractMax();//2.Extract another project and repeat
    			if (maxpriorityjob!=null) {
    				executable=maxpriorityjob.runtime<=maxpriorityjob.project.budget;
    			}
        	}
        	if (executable) {
	 			System.out.println("Executing: "+maxpriorityjob.name+" from: "+maxpriorityjob.project.name);
	   			maxpriorityjob.project.budget=maxpriorityjob.project.budget-maxpriorityjob.runtime;//i.Reduce project’s budget
	   			this.global_time=this.global_time+maxpriorityjob.runtime;//ii.Update Global time
	   			maxpriorityjob.end_time=this.global_time;//iii.Set job’s end_time
	    		completedjobsAL.add(maxpriorityjob);//iv.Add project to completedjobsAL
	    		maxpriorityjob.status=Status.finished;//v.Update the status of the job
	    		System.out.println("Project: "+maxpriorityjob.project.name+" budget remaining: "+maxpriorityjob.project.budget);
	    		System.out.println("System execution completed");
	    		maxpriorityjob=jobsMH.extractMax();//vi.Extract another project and repeat
    		}
    	}
    }

    public void print_stats() {
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+completedjobsAL.size());
    	for (int i=0;i<completedjobsAL.size();i++) {
    		Job j=completedjobsAL.get(i);
    		String un=j.user.name;
    		String pn=j.project.name;
    		int t=j.runtime;
    		int et=j.end_time;
    		String jn=j.name;
    		System.out.println("Job{user='"+un+"', project='"+pn+"', jobstatus=COMPLETED, execution_time="+t+", end_time="+et+", name='"+jn+"'}");
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	int count=0;
    	int index = projectsAL.size()/2;
    	while (index>=0) {
    		bubbledownforprojects(projectsAL,index);
    		index--;
    	}
    	MaxHeap<Project> projectMH = new MaxHeap<Project>();
    	ArrayList<Priority<Project>> projectsheap = new ArrayList<Priority<Project>>(); 
    	for (int i=0;i<projectsAL.size();i++) {
    		projectsheap.add(new Priority<Project>(projectsAL.get(i),projectsAL.get(i).projectnumber));
    	}
    	projectMH.heap = projectsheap;
    	Project nextproject = projectMH.extractMax();
    	while (nextproject!=null) {
    		ArrayList<Job> printjobs = nextproject.notcompletedjobsAL;
    		count = count + printjobs.size();
    		for (int i=0;i< printjobs.size();i++) {
    			Job j = printjobs.get(i);
        		String un=j.user.name;
        		String pn=j.project.name;
        		int t=j.runtime;
        		String jn=j.name;
        		System.out.println("Job{user='"+un+"', project='"+pn+"', jobstatus=REQUESTED, execution_time="+t+", end_time=null, name='"+jn+"'}");
    		}
    		nextproject = projectMH.extractMax();
    	}
//    	for (int i=0;i<projectsAL.size();i++) {
//    		Project p=projectsAL.get(i);
//    		ArrayList<Job> unfin_jobs_of_p=p.notcompletedjobsAL;
//    		for (int j=0;j<unfin_jobs_of_p.size();j++) {
//    			Job uf_job=unfin_jobs_of_p.get(j);
//    			count++;
//    			notcompletedjobsMH.insert(uf_job);
//    		}
//    	}
//    	Job notcompjob=notcompletedjobsMH.extractMax();
//    	while (notcompjob!=null) {
//    		Job j=notcompjob;
//    		String un=j.user.name;
//    		String pn=j.project.name;
//    		int t=j.runtime;
//    		String jn=j.name;
//    		System.out.println("Job{user='"+un+"', project='"+pn+"', jobstatus=REQUESTED, execution_time="+t+", end_time=null, name='"+jn+"'}");
//    		notcompjob=notcompletedjobsMH.extractMax();
//    	}
    	System.out.println("Total unfinished jobs: "+count);
    	System.out.println("--------------STATS DONE---------------");

    }
    
    public void bubbledownforprojects(ArrayList<Project> heap, int index) {
    	int lastindex = heap.size()-1;
    	boolean positionfound = false;
    	while (!positionfound) {
    		if (2*index>=lastindex) {
    			positionfound=true;
    		}
    		else if (2*index+1==lastindex) {
    			Project leftchild = heap.get(2*index+1);
    			Project node = heap.get(index);
    			if (node.compareTo(leftchild)<=0) {
    				heap.set(index, leftchild);
    				heap.set(2*index+1, node);
    				index = 2*index + 1;
    			}
    			positionfound=true;
    		}
    		else {
    			Project leftchild = heap.get(2*index+1);
    			Project node = heap.get(index);
    			Project rightchild = heap.get(2*index+2);
    			if (node.compareTo(leftchild)>0 & node.compareTo(rightchild)>0) {
    				positionfound=true;
    			}
    			else if (leftchild.compareTo(rightchild)<0){
    				heap.set(index, rightchild);
    				heap.set(2*index+2, node);
    				index = 2*index+2;
    			}
    			else {
    				heap.set(index, leftchild);
    				heap.set(2*index+1, node);
    				index = 2*index + 1;
    			}
    		}
    	}
    }

    public void handle_add(String[] cmd) {
    	String projectname=cmd[1];
    	int budgetincrement=Integer.valueOf(cmd[2]);
    	TrieNode<Project> projectnode=projects.search(projectname);
    	if (projectnode==null) {//a.Check No such project exists trie projects
    		System.out.println("No such project exists. "+projectname);
    	}
    	else {//b.If project found in trie projects:
    		System.out.println("ADDING Budget");
    		Project project=projectnode.getValue();
    		project.budget=project.budget+budgetincrement;//i.Increase budget
    		int size=project.notcompletedjobsAL.size();
    		for (int i=0;i<size;i++) {//ii.Add uncompleted jobs of the project to the heap jobsMH
    			Job p=project.notcompletedjobsAL.get(i);
    			jobsMH.insert(p);
    		}
    		project.notcompletedjobsAL=new ArrayList<Job>();
    	}
    }

    public void handle_empty_line() {
       schedule();
    }

    public void handle_query(String key) {
    	System.out.println("Querying");
    	String jobname=key;
    	TrieNode<Job> jobnode=jobs.search(jobname);
    	if (jobnode==null) {		//a.Check NOSUCHJOB in the trie jobs
    		System.out.println(jobname+": NO SUCH JOB");
    	}
    	else {//b.If job found in trie jobs
    		Job job=jobnode.getValue();
    		if (job.status==Status.finished) {
    			System.out.println(jobname+": COMPLETED");//i.FINISHED
    		}
    		else {			
    			System.out.println(jobname+": NOT FINISHED");//ii.NOT FINISHED
    		}
    	} 	 
    }

    public void handle_user(String name) {
    	System.out.println("Creating user");
    	User newuser = new User(name);		//a.Create user object
    	users.insert(name, newuser);//b.Add to trie users
    	//Maintaining new Data-Structures
    	newuser.consumptionindex=OrderedUsers.size();//consumptionindex_update_i
    	OrderedUsers.add(newuser);//OrderedUsers_update_i
    }
    
    public void handle_job(String[] cmd) {
    	System.out.println("Creating job");
    	String jobname=cmd[1];
    	String projectname=cmd[2];
    	String username=cmd[3];
    	int runningtime=Integer.valueOf(cmd[4]);
    	TrieNode<Project> projectnode=projects.search(projectname);
    	TrieNode<User> usernode=users.search(username); 		//a.Check :
    	if (projectnode==null) {						//i.Project name existence
    		System.out.println("No such project exists. "+projectname);
    	}
    	else if (usernode==null) {						//ii.User name existence
    		System.out.println("No such user exists: "+username);
    	}
    	else {   					//b.c.d.
    		Project project=projectnode.getValue();
    		User user=usernode.getValue();
    		Job newjob = new Job(jobname,project,user,runningtime,job_number);//b.Create job object
    		newjob.arrival_time=global_time;//New: Setting current time as the arrival time of the new job
    		job_number++;
    		jobs.insert(jobname, newjob);//c.Add to trie jobs
    		jobsMH.insert(newjob);//d.Add to heap jobsMH
    		//Maintaining New Data-Structures
    		//PriorityOrderedJobs_update_i
    		int currentindex=PriorityOrderedJobs.size();
    		PriorityOrderedJobs.add(newjob);
    		boolean positionfound=false;
    		while (currentindex!=0 & !positionfound) {
    			if (PriorityOrderedJobs.get(currentindex-1).priority<newjob.priority) {
    				PriorityOrderedJobs.set(currentindex, PriorityOrderedJobs.get(currentindex-1));
    				PriorityOrderedJobs.set(currentindex-1, newjob);
    				currentindex--;
    			}
    			else{
    				positionfound=true;
    			}
    		}
    		//CreationTimeOrderedJobs:ArrayList<Job>_update_i
    		newjob.user.CreationTimeOrderedJobs.add(newjob);
    		newjob.project.CreationTimeOrderedJobs.add(newjob);   
    		//ProjectUserTrie_update_i 
    		//UserProjectTrie_update_i
    		if (projectname.length()<username.length()) {
    			TrieNode<ArrayList<Job>> searchnode = user.UserProjectTrie.search(projectname);
    			if (searchnode==null) {
    				user.UserProjectTrie.insert(projectname, new ArrayList<Job>());
    				project.ProjectUserTrie.insert(username, new ArrayList<Job>());
    			}
    			searchnode = user.UserProjectTrie.search(projectname);
    			searchnode.getValue().add(newjob);
    			searchnode = project.ProjectUserTrie.search(username);
    			searchnode.getValue().add(newjob);
    		}
    		else {
    			TrieNode<ArrayList<Job>> searchnode = project.ProjectUserTrie.search(username);
    			if (searchnode==null) {
    				user.UserProjectTrie.insert(projectname, new ArrayList<Job>());
    				project.ProjectUserTrie.insert(username, new ArrayList<Job>());
    			}
    			searchnode = user.UserProjectTrie.search(projectname);
    			searchnode.getValue().add(newjob);
    			searchnode = project.ProjectUserTrie.search(username);
    			searchnode.getValue().add(newjob);
    		}
    	}
    }

    public void handle_project(String[] cmd) {
    	System.out.println("Creating project");
    	String projectname=cmd[1];
    	int priority=Integer.valueOf(cmd[2]);
    	int budget=Integer.valueOf(cmd[3]);
    	Project newproject=new Project(projectname,priority,budget,projectnumber);//a.Create a project object
    	projectnumber++;
    	projects.insert(projectname, newproject);//b.Add to the trie projects
    	projectsAL.add(newproject);//c.Add to the ArrayList projectsAL
    }
    
    public void execute_a_job() {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: "+jobsMH.heap.size());
    	Job maxpriorityjob=jobsMH.extractMax();//a.Extrace job
    	boolean done=false;
    	while (maxpriorityjob!=null & !done) {
    		System.out.println("Executing: "+maxpriorityjob.name+" from: "+maxpriorityjob.project.name);
    		if (maxpriorityjob.runtime<=maxpriorityjob.project.budget) {//Executable Budget i.If budget allows then Execute
    			maxpriorityjob.project.budget=maxpriorityjob.project.budget-maxpriorityjob.runtime;//i.Reduce project’s budget
    			this.global_time=this.global_time+maxpriorityjob.runtime;//ii.Update Global time
    			maxpriorityjob.end_time=this.global_time;//iii.Set job’s end_time
    			completedjobsAL.add(maxpriorityjob);//iv.Add project to completedjobsAL
    			maxpriorityjob.status=Status.finished;//v.Update the status of the job
    			System.out.println("Project: "+maxpriorityjob.project.name+" budget remaining: "+maxpriorityjob.project.budget);
    			done=true;
    			//Maintaining New Data-Structures
    			maxpriorityjob.user.budgetconsumed=maxpriorityjob.user.budgetconsumed+maxpriorityjob.runtime;//budgetconsumed_update_i
    			//OrderedUsers_update_ii
    			User currentuser=maxpriorityjob.user;
    			int i = currentuser.consumptionindex;
    			boolean positionfound=false;
    			while (i!=0 & !positionfound) {
    				if (OrderedUsers.get(i-1).budgetconsumed<=currentuser.budgetconsumed) {
    					OrderedUsers.set(i, OrderedUsers.get(i-1));
    					OrderedUsers.get(i).consumptionindex=i;//consumptionindex_update_ii
    					OrderedUsers.set(i-1, currentuser);
    					currentuser.consumptionindex=i-1;
    					i--;
    				}
    				else {
    					positionfound=true;
    				}
    			}
    			//PriorityOrderedJobs_update_ii
    			PriorityOrderedJobs.remove(maxpriorityjob);
    		}
    		else {//Non Executable Budget
    			System.out.println("Un-sufficient budget.");
    			maxpriorityjob.project.notcompletedjobsAL.add(maxpriorityjob);//1.Add to project’s notcompletedjobsAL of its project
    			maxpriorityjob=jobsMH.extractMax();//2.Extract another project and repeat
    		}
    	}
    	System.out.println("Execution cycle completed");

    }
}

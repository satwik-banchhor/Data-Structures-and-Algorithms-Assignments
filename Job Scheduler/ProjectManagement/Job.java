package ProjectManagement;

import PriorityQueue.Priority;

enum Status{
	finished,notfinished
}
public class Job implements Comparable<Job>, JobReport_{
	public String name;
	public Project project;
	public User user;
	public int runtime;
	public int priority;
	public int job_number;
	public Status status;
	public int end_time;
	public int arrival_time;
	Job(String name, Project project,User user, int runtime,int job_number) {
		this.name = name;
		this.project=project;
		this.user=user;
		this.runtime=runtime;
		this.priority=project.priority;
		this.job_number=job_number;
		this.status=Status.notfinished;
	}
    @Override
    public int compareTo(Job job) {
    	if (this.priority<job.priority) {
    		return -1;
    	}
    	else if (this.priority==job.priority) {
    		if (this.job_number<job.job_number) {
    			return 1;
    		}
    		else {
    			return -1;
    		}
    	}
    	else {
    		return 1;
    	}
    }
	@Override
	public String user() {
		return user.name;
	}
	@Override
	public String project_name() {
		return project.name;
	}
	@Override
	public int budget() {   //Confusion on what budget actually is
		return this.runtime;
	}
	@Override
	public int arrival_time() {
		return arrival_time;
	}
	@Override
	public int completion_time() {
		return end_time;
	}
}
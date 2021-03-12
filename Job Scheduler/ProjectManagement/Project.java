package ProjectManagement;

import java.util.ArrayList;
import Trie.Trie;

public class Project implements Comparable<Project>{
	public String name;
	public int priority;
	public int budget;
	public ArrayList<Job> notcompletedjobsAL;
	//New Data-Structures
	public ArrayList<Job> CreationTimeOrderedJobs;
	public Trie<ArrayList<Job>> ProjectUserTrie;
	public int projectnumber;
	
	Project(String name, int priority,int budget,int projectnumber) {
		this.name=name;
		this.priority=priority;
		this.budget=budget;
		notcompletedjobsAL=new ArrayList<Job>();
		this.CreationTimeOrderedJobs=new ArrayList<Job>();//CreationTimeOrderedJobs_initialize
		this.ProjectUserTrie=new Trie<ArrayList<Job>>();//ProjectUserTrie_initialize	
	}

	public int compareTo(Project project) {
    	if (this.priority<project.priority) {
    		return -1;
    	}
    	else if (this.priority==project.priority) {
    		if (this.projectnumber<project.projectnumber) {
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
}

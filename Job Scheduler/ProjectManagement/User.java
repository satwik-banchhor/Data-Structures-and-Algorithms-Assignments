package ProjectManagement;

import java.util.ArrayList;
import Trie.Trie;

public class User implements Comparable<User>, UserReport_ {
	public String name;
	//New Data-Structures
	public ArrayList<Job> CreationTimeOrderedJobs;
	public Trie<ArrayList<Job>> UserProjectTrie;
	public int budgetconsumed;
	public int consumptionindex;
	
	User(String name) {
		this.name=name;
		this.budgetconsumed=0;//budgetconsumed_initialize
		this.consumptionindex=0;//consumptionindex_initialize
		this.CreationTimeOrderedJobs=new ArrayList<Job>();//CreationTimeOrderedJobs_initialize
		this.UserProjectTrie=new Trie<ArrayList<Job>>();//UserProjectTrie_initialize	
	}

    @Override
    public int compareTo(User user) {
        return this.name.compareTo(user.name);
    }

	@Override
	public String user() {
		return name;
	}

	@Override
	public int consumed() {
		return budgetconsumed;
	}
}

public class Qsort {
	public static int partition(String[] list, int i, int j) {
		String element = list[i];
		i=i+1;
		while (i<=j) {
			if (element.compareTo(list[i])>=0){
				String rem = list[i-1];
				list[i-1] = list[i];
				list[i] = rem;
				i++;				
			}
			else {
				String rem = list[j];
				list[j] = list[i];
				list[i] = rem;
				j = j-1;				
			}
		}
		return i;		
	}
	public static void qsort(String[] list,int i,int j) {
		if (i<j){
			int pos = partition(list , i , j);
			qsort(list , i ,pos-2);
			qsort(list , pos , j);
		}		
	}
}
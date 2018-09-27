import java.util.ArrayList;
import java.util.List;

public class Schedule {
	private ArrayList<Class> classList = new ArrayList<Class>();
	private ArrayList<Integer> startList = new ArrayList<Integer>();
	private ArrayList<Integer> endList = new ArrayList<Integer>();
	
	public List<Class> getCourseList() {
		return classList;
	}
	public List<Integer> getStartList() {
		return startList;
	}
	public List<Integer> getEndList() {
		return endList;
	}
	
	public int getSize() {
		return classList.size();
	}
	
	public void addCourse(String name, String days, int start, int end) {
		Class my_class = new Class(name, days, start, end);
		classList.add(my_class);
	}
	
	
	public void addCourse(Class myClass) {
		classList.add(myClass);
	}

	public boolean evaluate() {
		for(int i=0; i<classList.size(); i++) {
			startList.add(classList.get(i).getStart());
			endList.add(classList.get(i).getEnd());
		}
		//for(int i=0; i<classList.size(); i++) {
		//	System.out.println(startList.get(i)+"-"+endList.get(i));
		//}
		//System.out.println("Schedule Size: "+classList.size());
		for(int i=0; i<classList.size(); i++) {
			for(int j=i+1; j<classList.size(); j++) {
				if((startList.get(i) >= startList.get(j) && 
						(startList.get(i)) <= endList.get(j))) {
					//System.out.println("Conflict:"+classList.get(i).getName()+classList.get(i).getStart()+" "+classList.get(i).getEnd()
					//		+" x "+classList.get(j).getName()+": "+classList.get(j).getStart()+" "+classList.get(j).getEnd());
					return false;
				}
				else if((endList.get(i) >= startList.get(j)) &&
						endList.get(i) <= endList.get(j)) {
					//System.out.println("Conflict:"+classList.get(i).getName()
					//		+" x "+classList.get(j).getName());
					return false;
				}
			}
		}
		return true;
	}
}

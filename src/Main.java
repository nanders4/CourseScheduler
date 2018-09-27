import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		List<List<Class>> finalList = new ArrayList<List<Class>>();
		int courseNum = 4;
		int labNum = 3;
		List<Class> listOfCourses = new ArrayList<Class>();
		List<Class> listOfLabs = new ArrayList<Class>();
		List<Class> definiteList = new ArrayList<Class>();
		
		Scanner reader = new Scanner(System.in);
		
		//pre-enter courses
		
		//definite courses
		definiteList.add(new Class("Dynamics1", "m", 1315, 1615));
		definiteList.add(new Class("Database", "tr", 1315, 1430));
		definiteList.add(new Class("Algo1", "tr", 955, 1110));
		//definiteList.add(new Class("Algo2", "tr", 1120, 1235));
		//definiteList.add(new Class("Wisdom", "t", 1900, 2500));
		



		//courses
		listOfCourses.add(new Class("Algo2", "tr", 1120, 1235));
		listOfCourses.add(new Class("Algo1", "tr", 955, 1110));
		listOfCourses.add(new Class("Database", "tr", 1315, 1430));
		//listOfCourses.add(new Class("MobileRobotics", "tr", 1440, 1555));
		listOfCourses.add(new Class("Operations", "tr", 1120, 1235));
		listOfCourses.add(new Class("Poststructuralism", "tr", 1315, 1430));
		listOfCourses.add(new Class("Dynamics1", "m", 1315, 1615));
		listOfCourses.add(new Class("Dynamics2", "t", 1315, 1615));
		//listOfCourses.add(new Class("Prob1", "mwf", 930, 1020));
		//listOfCourses.add(new Class("Prob2", "mwf", 1030, 1120));
		listOfCourses.add(new Class("ShortStory", "mwf", 930, 1020));
		//listOfCourses.add(new Class("AsianHum", "tr", 1440, 1555));
		listOfCourses.add(new Class("Wisdom", "t", 1900, 2500));
		listOfCourses.add(new Class("Metaphysics", "w", 1315, 1600));

		//labs
		listOfLabs.add(new Lab("Algo1Lab", "m", 850, 1020, "Algo1"));
		listOfLabs.add(new Lab("Algo2Lab1", "m", 1315, 1445, "Algo2"));
		listOfLabs.add(new Lab("Algo2Lab2", "m", 1500, 1630, "Algo2"));
		listOfLabs.add(new Lab("DatabaseLab1", "w", 1315, 1445, "Database"));
		listOfLabs.add(new Lab("DatabaseLab2", "w", 1500, 1430, "Database"));
		

		//add courses to the course list
		System.out.println("Enter class as follows: OneWordName mwf 09:20-13:30");
		System.out.println("To make class definite, type \"def\" after the time: ...13:50 d\n");
		boolean loop = false; //false for just pre-enter
		while(loop) {
			System.out.println("Enter class: ");
			String line = reader.nextLine();
			boolean def = false;
			String[] substrings = line.split(" ");
			String rawName = substrings[0];
			String days = substrings[1];
			String temp1 = substrings[2].substring(0, 2);
			String temp2 = substrings[2].substring(3,5);
			String temp3 = substrings[2].substring(6, 8);
			String temp4 = substrings[2].substring(9, 11);
			if(substrings.length>3) {
				if(substrings[3].equals("def")) {
					def = true;
				}
			}
			int start = Integer.parseInt((temp1+temp2));
			int end = Integer.parseInt((temp3+temp4));
			
			Class tempClass = new Class(rawName, days, start, end);
			listOfCourses.add(tempClass);
			if(def) {
				definiteList.add(tempClass);
			}
			System.out.println("Another class? (y/n)");
			System.out.println("[type \"lab\" to enter a lab section]");
			String input = reader.nextLine().trim();
			if(input.equals("n")) {
				loop = false;
			}
		}
		
		//make course combinations
		List<List<Class>> possibleLists = new ArrayList<List<Class>>();
		List<List<Class>> possibleLabLists = new ArrayList<List<Class>>();
		List<List<Class>> tempPossibleLabLists = new ArrayList<List<Class>>();
		Collections.sort(listOfCourses);
		Collections.sort(listOfCourses);
		possibleLists = makeAllLists(listOfCourses, courseNum);
		tempPossibleLabLists = makeAllLabLists(listOfLabs, labNum);
		
		//remove doubled lab sections
		for(int i=0; i<tempPossibleLabLists.size(); i++) {
			if(checkDouble(tempPossibleLabLists.get(i))==false) {
				possibleLabLists.add(tempPossibleLabLists.get(i));
			}
		}
		//print all returned lists
		/*for(int a=0; a<possibleLists.size(); a++) {
			for(int b=0; b<possibleLists.get(a).size(); b++) {
				System.out.print(possibleLists.get(a).get(b).getName());
			}
			System.out.println("");
		}
		for(int a=0; a<possibleLabLists.size(); a++) {
			for(int b=0; b<possibleLabLists.get(a).size(); b++) {
				System.out.print(possibleLabLists.get(a).get(b).getName());
			}
			System.out.println("");
		}*/
		
		
		//for each possible lists:
		List<Class> coursesToAdd = new ArrayList<Class>();
		for (int j=0; j<possibleLists.size(); j++) {
			List<List<Class>> intermediateLabListList = new ArrayList<List<Class>>();
			coursesToAdd = possibleLists.get(j);
			
			Schedule monday = new Schedule();
			Schedule tuesday = new Schedule();
			Schedule wednesday = new Schedule();
			Schedule thursday = new Schedule();
			Schedule friday = new Schedule();
			
			//add courses to day schedules
			addToSchedules(coursesToAdd, monday, tuesday, wednesday, 
					thursday, friday);

			//evaluate schedules
			if(weekEvaluate(monday, tuesday, wednesday, thursday, friday)) {

				//account for labs
				boolean modified = false;
				for(int a=0; a<possibleLabLists.size(); a++) {
					modified = false;
					List<Class> tempList = new ArrayList<Class>();
					for(int b=0; b<possibleLabLists.get(a).size(); b++) {
						for(int c=0; c<coursesToAdd.size(); c++) {
							if(possibleLabLists.get(a).get(b).getParent()
									.equals(coursesToAdd.get(c).getName())) {
								tempList.add(possibleLabLists.get(a).get(b));
								modified = true;
							}
						}
					}
					if(modified) {
						for(int z=0; z<coursesToAdd.size(); z++) {
							tempList.add(coursesToAdd.get(z));
						}
						intermediateLabListList.add(tempList);
					}
				}


				//final schedule check				
				for(int x=0; x<intermediateLabListList.size(); x++) {
					Schedule monday1 = new Schedule();
					Schedule tuesday1 = new Schedule();
					Schedule wednesday1 = new Schedule();
					Schedule thursday1 = new Schedule();
					Schedule friday1 = new Schedule();
					
					addToSchedules(intermediateLabListList.get(x), monday1, tuesday1, wednesday1,
							thursday1, friday1);
					//System.out.println("i: "+x+ "monday1Size2: "+monday1.getSize());

					if(weekEvaluate(monday1, tuesday1, wednesday1, thursday1, friday1)) {
						finalList.add(intermediateLabListList.get(x));
					}
					else {
						//System.out.println("This labbed schedule doesn't work");
					}
				}

			}
			else{
				//System.out.println("This unlabbed schedule doesn't work");
			}
			
		}

		
		//sort finalList
		for(int i=0; i<finalList.size(); i++) {
			for(int j=0; j<finalList.get(i).size(); j++) {
				Collections.sort(finalList.get(i));
			}
		}
		
		/*Collections.sort(finalList, new Comparator<List<Class>>() {
			public int compare(List<Class> list1, List<Class> list2) {
				int size = 0;
				int ret;
				if(list1.size()>list2.size()) {
					size = list2.size();
				}
				else {
					size=list1.size();
				}
				
				int count1 =0;
				int count2 =0;
				for(int i=0; i<size; i++) {
					if(list1.get(i).getName().compareTo(list2.get(i).getName())<0) {
						count1 +=1;
					}
					else if(list2.get(i).getName().compareTo(list1.get(i).getName())<0) {
						count2 +=1;
					}
				}
				ret = count1-count2;
				return ret;
			}
		});*/
		
		
		//remove duplicates
		List<List<Class>> actualFinal = new ArrayList<List<Class>>();
		List<List<Class>> dupList = new ArrayList<List<Class>>();
		
		int size;
		boolean dupFound = false;
		for(int i=0; i<finalList.size(); i++) {
			dupFound = false;
			size = dupList.size();
			//System.out.print("dupListsize: "+size+". Checking "+i+"th element: ");
			for(int j=0; j<size; j++) {
				if(listsEqual(finalList.get(i), dupList.get(j))) {
					//System.out.println("dupFound!");
					dupFound = true;
					/*System.out.println("dupFound:");
					for(int k=0; k<finalList.get(i).size(); k++) {
						System.out.print(finalList.get(i).get(k).getName());
					}
					System.out.println("");
					for(int k=0; k<dupList.get(j).size(); k++) {
						System.out.print(dupList.get(j).get(k).getName());
					}
					System.out.println("");
					*/
				}
			}
			if(!dupFound) {
				dupList.add(finalList.get(i));
				actualFinal.add(finalList.get(i));
				/*System.out.println("   Added: ");
				for(int k=0; k<finalList.get(i).size(); k++) {
					System.out.print("   "+finalList.get(i).get(k).getName());
				}
				System.out.println("");
				*/
			}
		}
		
		//account for definite courses
		List<List<Class>> trueFinal = new ArrayList<List<Class>>();
		for(int i=0; i<actualFinal.size(); i++) {
			if(containsDef(actualFinal.get(i), definiteList)) {
				trueFinal.add(actualFinal.get(i));
			}
		}
		
		//print actualFinal
		System.out.println("Number of possible schedules: "+trueFinal.size());
		for(int i=0; i<trueFinal.size(); i++) {
			System.out.println("=========");
			for(int j=0; j<trueFinal.get(i).size(); j++) {
				System.out.println(trueFinal.get(i).get(j).getName());
			}
			System.out.println("------");
		}
		reader.close();
	}
	
	static List<List<Class>> makeAllLabLists(List<Class> listOfLabs, int labNum) {
		List<List<Class>> possibleLabLists = makeListRec(listOfLabs, labNum, 0, 0);
		return possibleLabLists;
	}
	static List<List<Class>> makeAllLists(List<Class> listOfCourses, int courseNum) {
		List<List<Class>> possibleLists = makeListRec(listOfCourses, courseNum, 0, 0);
		/*System.out.println("listOfCourses: ");
		for(int i=0; i<listOfCourses.size(); i++) {
			System.out.println(listOfCourses.get(i).getName());
		*/
		return possibleLists;
	}
	
	//recursion
	static List<List<Class>> makeListRec(List<Class> dataList, int sizeCap, int index, int start) {
		List<List<Class>> madeList = new ArrayList<List<Class>>();
		/*System.out.println("sizeCap: "+sizeCap);
		System.out.println("index: "+index);
		System.out.println("start: "+start);
		System.out.println("dataList.size()-sizCap+1: "+(dataList.size()-sizeCap+1));
		*/
		if (sizeCap==0) {
			//System.out.println("terminating condition of recursion");
			List<List<Class>> listList = new ArrayList<List<Class>>();
			List<Class> retList = new ArrayList<Class>();
			listList.add(retList);
			return listList;
		}
		else {
			//System.out.println("standard recursion entry");
			List<List<Class>> listList = new ArrayList<List<Class>>();
			for (int i=start; i<dataList.size()-sizeCap+1; i++) {
				//System.out.println("i: "+i);
				madeList = makeListRec(dataList, sizeCap-1, index+1, i+1);
				//System.out.println("Returned madeList");
				for (int j=0; j<madeList.size(); j++) {
					madeList.get(j).add(0, dataList.get(i));
					//System.out.println("Added: " +dataList.get(i).getName());
					listList.add(madeList.get(j));
				}
			}
			return listList;
		}
	}
	
	static void addToSchedules(List<Class> coursesToAdd, Schedule monday, 
			Schedule tuesday, Schedule wednesday, Schedule thursday, Schedule friday) {
		for(int i=0; i<coursesToAdd.size(); i++) {
			if (coursesToAdd.get(i).getDays() == "mwf") {
				monday.addCourse(coursesToAdd.get(i));
				wednesday.addCourse(coursesToAdd.get(i));
				friday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "tr") {
				tuesday.addCourse(coursesToAdd.get(i));
				thursday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "m") {
				monday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "t") {
				tuesday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "w") {
				wednesday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "r") {
				thursday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "f") {
				friday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "mw") {
				monday.addCourse(coursesToAdd.get(i));
				wednesday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "mt") {
				monday.addCourse(coursesToAdd.get(i));
				tuesday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "wr") {
				wednesday.addCourse(coursesToAdd.get(i));
				thursday.addCourse(coursesToAdd.get(i));
			}
			else if (coursesToAdd.get(i).getDays() == "wf") {
				wednesday.addCourse(coursesToAdd.get(i));
				friday.addCourse(coursesToAdd.get(i));
			}
			else {
				System.out.println("day not recognized");
			}
		}
	}
	
	
	static boolean weekEvaluate(Schedule monday, Schedule tuesday, Schedule wednesday,
			Schedule thursday, Schedule friday) {
		if((monday.evaluate() == true) && (tuesday.evaluate() == true) 
				&& (wednesday.evaluate() == true) && (thursday.evaluate() == true) 
				&& (friday.evaluate() == true)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	static boolean checkDouble(List<Class> list) {
		for(int i=0; i<list.size(); i++) {
			for(int j=i; j<list.size(); j++) {
				if(i!=j) {
					if(list.get(i).getParent().equals(list.get(j).getParent())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	static boolean listsEqual(List<Class> list1, List<Class> list2) {
		if(list1.size()!=list2.size()) {
			//System.out.println("size inequivelance");
			return false;
		}
		for(int i=0; i<list1.size(); i++) {
			if(!(list1.get(i).getName().equals(list2.get(i).getName()))) {
				return false;
			}
		}
		return true;
	}
	
	static boolean containsDef(List<Class> list, List<Class> defList) {
		List<Boolean> boolList = new ArrayList<Boolean>();
		for(int i=0; i<defList.size(); i++) {
			for(int j=0; j<list.size(); j++) {
				if(defList.get(i).getName().equals(list.get(j).getName())) {
					boolList.add(true);
				}
			}
		}
		if (boolList.size() == defList.size()) {
			return true;
		}
		else {
			return false;
		}
	}
}

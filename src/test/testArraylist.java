package test;

import java.util.ArrayList;
import java.util.ListIterator;

public class testArraylist {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> testlist;
		testlist = new ArrayList<String>();
		
		testlist.add("abc");
		testlist.add("adfadf");
		testlist.add("adfadfe");
		
		ListIterator<String> itr = testlist.listIterator();
		
		while(itr.hasNext())
		{
			String testSting; 
			ArrayList<String> testArraylist = new ArrayList<String>();
			
			String CurrentString = itr.next();
			
			testSting = CurrentString;
			
			
			System.out.println(testSting);
			
			testArraylist.add(CurrentString);
			
			System.out.println(testArraylist.size());
			
			
		}
		
		
		
		
		
		
		
		
		
	}

}

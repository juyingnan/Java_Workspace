package cn.edu.pku.ss.test.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Tools {	
	public static final Random RANDOM = new Random();
	
	public static double canculateCoverage(HashSet<Integer> setExpected, HashSet<Integer> setActual){
		
		double r = 0;
		
		if(setExpected.size()>0){			
			Iterator<Integer> it = setActual.iterator();
			while(it.hasNext()){
				if(setExpected.contains(it.next())){
					r++;
				}
			}
		
			r = r / setExpected.size();
		}
		
		return r;
		
	}
	
}

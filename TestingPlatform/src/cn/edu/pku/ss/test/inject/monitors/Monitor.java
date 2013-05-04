package cn.edu.pku.ss.test.inject.monitors;

import java.util.Random;

public abstract class Monitor<T> {

	private static Random seed = new Random();
	public static String PREFIX = "pathRecorder_";
	
	public static String getInject(){	
		int n = seed.nextInt();
		if(n<0) n *= -1;
		return PREFIX + n ;
	}
	
	public abstract T getResult();	
}

package cn.edu.pku.ss.test.java;

import java.util.ArrayList;

import org.junit.Test;

import cn.edu.pku.ss.test.util.Log;

public class SortTest {
	
	@Test
	public void test(){
		ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
		
		arr.add(new ArrayList<Integer>());
		arr.add(new ArrayList<Integer>());
		arr.add(new ArrayList<Integer>());
		
		arr.get(0).add(1);
		arr.get(0).add(2);
		arr.get(0).add(3);
		arr.get(0).add(4);		
		arr.get(0).add(5);
		
		arr.get(1).add(6);
		arr.get(1).add(7);
		arr.get(1).add(8);
		
		arr.get(2).add(9);
		
		ArrayList<ArrayList<Integer>> r = sort(arr);
		
		Log.println(arr);
		Log.println(r);
		
	}
	
	public ArrayList<ArrayList<Integer>> sort(ArrayList<ArrayList<Integer>> arr){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		int count =1;
		int[] size =  new int[arr.size()];
		int length = 0;
		for(int i=0;i<arr.size();i++){
			if(arr.get(i).size()>0){
				count*=arr.get(i).size();
				size[i] = (int)(Math.log(arr.get(i).size()) / Math.log(2))+1;
				Log.println(size[i]);
				length += size[i];
			}
		}
		
		int time = (int)Math.pow(2, length);
		
		Log.println(time);
		
		
		for(int i=0; i< time; i++){
			ArrayList<Integer> r = new ArrayList<Integer>();
			String s = toBin(i,length);
			Log.println(s);
			int pos =0;
			for (int j=0; j< size.length; j++){
				String sub = s.substring(pos, size[j] + pos);
				pos +=size[j];
				//Debug.println(sub);
				int index = Integer.parseInt(sub, 2);
				if(index<arr.get(j).size()){
					r.add(arr.get(j).get(index));
				}
				else{
					break;
				}			
				
			}
			
			if(r.size() == arr.size()){
				result.add(r);
			}

		}
		
		
		return result;
	}
	
	public static String toBin(int num, int length){
		String s = Integer.toString(num, 2);
		if(s.length()< length){
			String z = "";
			for(int i=s.length(); i< length;i++){
				z+="0";
			}
			s = z+s;
		}
		return s;
	}
}

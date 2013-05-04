package cn.edu.pku.ss.test.condition;

import java.util.ArrayList;

import cn.edu.pku.ss.test.condition.ConditionResult;
import cn.edu.pku.ss.test.condition.ConditionResultArray;
import cn.edu.pku.ss.test.util.Log;

public class ConditionTree extends ArrayList<ConditionTreeNode> {

	
	
	public ConditionResultArray generateDCPath() {
		ArrayList<ArrayList<ConditionResult>> arrs = new ArrayList<ArrayList<ConditionResult>>();
		for (int i = 0; i < this.size(); i++) {
			ArrayList<ConditionResult> arr = new ArrayList<ConditionResult>();			
			arr.add(new ConditionResult(this.get(i).getATrueTestCase(), this
					.get(i).getCount()));
			arr.add(new ConditionResult(this.get(i).getAFalseTestCase(), this
					.get(i).getCount()));
			arrs.add(arr);
		}
		
		arrs = ConditionTree.sort(arrs);
		//Debug.println(arrs);
		ConditionResultArray pathlist = ConditionTree.combine(arrs);
		//Debug.println(pathlist);
		return pathlist;

	}
	
	public ConditionResultArray generateCCPath(){
	
		ArrayList<ArrayList<ConditionResult>> arrs = new ArrayList<ArrayList<ConditionResult>>();
		for (int i = 0; i < this.size(); i++) {
			ArrayList<ConditionResult> arr = new ArrayList<ConditionResult>();			
			
			ConditionResult pt = new ConditionResult();
			ConditionResult pf = new ConditionResult();

			int count = this.get(i).getCount();
			for (int j = 0; j < count; j++) {
				pt.add(1);
				pf.add(0);
			}
			
			System.out.println("0000 pt : " + pt.toString());
			System.out.println("0000 pf : " + pf.toString());
			
			arr.add(pt);
			arr.add(pf);
			arrs.add(arr);
		}
		System.out.println("1111 arrs : " + arrs);
		arrs = ConditionTree.sort(arrs);
		System.out.println("2222 arrs : " + arrs);
		//Debug.println(arrs);
		ConditionResultArray pathlist = ConditionTree.combine(arrs);
		System.out.println("3333 arrs : " + arrs);
		//Debug.println(pathlist);
		return pathlist;		
	}
	
	public ConditionResultArray generateMCPath(){
		ArrayList<ArrayList<ConditionResult>> arrs = new ArrayList<ArrayList<ConditionResult>>();
		for (int i = 0; i < this.size(); i++) {			
			arrs.add(new ConditionResultArray(this.get(i).getAllShortCuicus(), this
					.get(i).getCount()));			
		}		
		
		arrs = ConditionTree.sort(arrs);
		//Debug.println(arrs);
		ConditionResultArray pathlist = ConditionTree.combine(arrs);
		//Debug.println(pathlist);
		return pathlist;	
	}

	
	public ConditionResultArray generateAllPath() {
		ConditionResultArray paths = new ConditionResultArray();
		// 将条件信息初始化
		ArrayList<Integer> condition = new ArrayList<Integer>();
		for (int i = 0; i < this.size(); i++) {
			int count = this.get(i).getCount();
			for (int j = 0; j < count; j++) {
				condition.add(0);
			}
		}
		 //0,1的全排列
		int count = (int) (Math.pow(2, condition.size())) - 1;


		int length = condition.size();
		for (int i = 0; i <= count; i++) {
			System.out.println("-------------------------");
			ConditionResult path = new ConditionResult();
			String strPath = Integer.toString(i, 2);
			int strPathlen = strPath.length();
			if (strPathlen < length) {
				StringBuffer s = new StringBuffer();
				for (int temp = strPathlen; temp < length; temp++) {
					s.append("0");
				}
				strPath = s + strPath;
			}
//			System.out.println("strPath:"+strPath);
			// int zero = condition.size() - strPath.length();
			for (int j = 0; j < length; j++) {
				if (j < strPath.length()) {
					path.add(strPath.charAt(j) - 48);
				} else {
					path.add(0);
				}
//				System.out.println("path (i="+i+"j="+j+"):"+path);
			}
			paths.add(path);
//			System.out.println("path (i="+i+"):"+path);
			// System.out.println(Integer.toString(i, 2));
		}
		System.out.println(paths);
		return paths;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< this.size(); i++){
			sb.append(this.get(i).toString() + "\r\n");
		}
		return sb.toString();
	}
	
	public static ArrayList<ArrayList<ConditionResult>> sort(ArrayList<ArrayList<ConditionResult>> arr){
		ArrayList<ArrayList<ConditionResult>> result = new ArrayList<ArrayList<ConditionResult>>();
		int count =1;
		int[] size =  new int[arr.size()];
		int length = 0;
		for(int i=0;i<arr.size();i++){
			if(arr.get(i).size()>0){
				count*=arr.get(i).size();
				size[i] = (int)(Math.log(arr.get(i).size()) / Math.log(2))+1;
				//Debug.println(size[i]);
				length += size[i];
			}
		}
		
		int time = (int)Math.pow(2, length);
		
		//Debug.println(time);
		
		
		for(int i=0; i< time; i++){
			ArrayList<ConditionResult> r = new ArrayList<ConditionResult>();
			String s = toBin(i,length);
			//Debug.println(s);
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
	
	public static ConditionResultArray combine(ArrayList<ArrayList<ConditionResult>> arrs){
		ConditionResultArray arr = new ConditionResultArray();
		for(int i=0; i< arrs.size(); i++){
			ConditionResult r = new ConditionResult();
			for(int j=0;j< arrs.get(i).size();j++){
				r.join(arrs.get(i).get(j));
			}
			arr.add(r);
		}		
		return arr;
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

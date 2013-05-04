package cn.edu.pku.ss.test.java.condition;

import java.util.ArrayList;
import cn.edu.pku.ss.test.condition.ConditionResult;




public class ConditionPath extends ArrayList<ConditionPathNode>{
	
	private ArrayList<Integer> conditionInfo;
	
	public void addPath(int conditionId, int subConditionId, boolean condition){
		
		ConditionPathNode node = new ConditionPathNode(conditionId, subConditionId);
		int index = this.search(node);
		if(index==-1){
			node.plusCount(condition);
			this.add(node);
		}else{
			this.get(index).plusCount(condition);
		}
	}
	
	public int search(ConditionPathNode node){
		for(int i=0; i< this.size(); i++){
			if(this.get(i).equals(node)){
				return i;
			}
		}
		return -1;
	}

	public void setConditionInfo(ArrayList<Integer> conditionInfo) {
		this.conditionInfo = conditionInfo;
	}

	public ArrayList<Integer> getConditionInfo() {
		return conditionInfo;
	}
	
	public ConditionResult getResult(){
		ConditionResult result = new ConditionResult();
		if(conditionInfo==null) {
			return null;
		}
		else{
			for(int i =0 ; i < conditionInfo.size(); i++){
					for(int j=0; j< conditionInfo.get(i); j++){
						ConditionPathNode node = new ConditionPathNode(i,j);
						int index = this.search(node);
						if(index>=0){
							result.add(this.get(index).getHistory().get(0) == true? 1:0);
						}
						else{
							result.add(-1);
						}
					}
			}
		}
		
		return result;
	}
	
//	public ArrayList<Integer> getResult(){
//		ArrayList<Integer> result = new ArrayList<Integer>();
//		if(conditionInfo==null) {
//			return null;
//		}
//		else{
//			for(int i =0 ; i < conditionInfo.size(); i++){
//					for(int j=0; j< conditionInfo.get(i); j++){
//						ConditionPathNode node = new ConditionPathNode(i,j);
//						int index = this.search(node);
//						if(index>=0){
//							result.add(this.get(index).getHistory());
//						}
//						else{
//							result.add(-1);
//						}
//					}
//			}
//		}
//		Debug.println("£ò£å£ã£ï£ò£ä" + this.get(0));
//		return result;
//	}
	
//
//	public String getStrResult(){
//		ArrayList<Integer> arr = getResult();
//		StringBuilder sb = new StringBuilder();
//		for(int i=0; i < arr.size();i++){
//			sb.append(arr.get(i));
//		}
//		return sb.toString();
//	}
//	
//	
//
//	
}

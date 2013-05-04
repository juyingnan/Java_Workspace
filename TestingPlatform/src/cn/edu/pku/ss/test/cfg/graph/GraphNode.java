package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;

import cn.edu.pku.ss.test.util.IDValuePairArray;

public class GraphNode {
	
	public static int UNACCESSABLE = -1;
	
	int Id = UNACCESSABLE;
	int firstId = UNACCESSABLE;
	int secondId = UNACCESSABLE;
	private IDValuePairArray<Integer,Boolean> others= new IDValuePairArray<Integer,Boolean>();
	

	boolean isEnd;
	boolean isLoop;
	private boolean isLoopEnd;
	private boolean isContinue;	
	private boolean isBreak;
	private int loopId = -1;
	
	private int lineNum;
	private int beginLine;
	private int endLine;
	
	public GraphNode(){
		
	}
	
	public GraphNode(int id){
		this.setId(id);
	}
	
	public void setNext(int next){
		if(next == this.getId()) return;
		if(firstId==UNACCESSABLE){
			firstId = next;
		}
		else if(secondId==UNACCESSABLE && firstId!= next){
			secondId = next;
		} else {
			if(firstId!=next && secondId!=next && !others.containsId(next)){
				addOther(next);
			}
		}
	}
	
	public boolean hasNext(){
		if(firstId==UNACCESSABLE && secondId==UNACCESSABLE){
			return false;
		} else return true;
	}
	
	public void setId(int id) {
		Id = id;
	}
	public int getId() {
		return Id;
	}
	public void setFirstId(int firstId) {
		this.firstId = firstId;
	}
	public int getFirstId() {
		return firstId;
	}
	public void setSecondId(int secondId) {
		this.secondId = secondId;
	}
	
	public int getSecondId() {
		return secondId;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}


	public boolean isEnd() {
		return isEnd;
	}


	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}


	public boolean isLoop() {
		return isLoop;
	}
	
	public boolean isLoopEnd() {
		return isLoopEnd;
	}

	public void setLoopEnd(boolean isLoopEnd) {
		this.isLoopEnd = isLoopEnd;
	}

	public int getLoopId() {
		return loopId;
	}

	public void setLoopId(int loopId) {
		this.loopId = loopId;
	}

	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}

	public boolean isContinue() {
		return isContinue;
	}
	
	public IDValuePairArray<Integer, Boolean> getOthers() {
		return others;
	}

	public void addOther(int id){
		others.add(id, true);
	}
	
	public void addOther(int id, boolean necessary){
		others.add(id, necessary);
	}
	
	public final static String HEADER = "id\tloopId\tfirst\tsecond\tisEnd\tisLoop\tisContinue\tisLoopEnd\tothers[necessary]";
	
	public String toString(){
		String str = this.getId() + "\t" + this.getLoopId() + "\t" + this.getFirstId()
		+ "\t" + this.getSecondId()+ "\t" + this.isEnd()
		+ "\t" + this.isLoop()+ "\t" + this.isContinue()+ "\t" + this.isLoopEnd() + "\t";
		for(int i=0; i< others.size(); i++){
			str+= others.get(i).getId() + "[" + others.get(i).getValue() +  "]\t"; 
		}
		return str.substring(0, str.length()-1);
	}

	public ArrayList<Integer> getFollows() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		if(firstId!= UNACCESSABLE){
			arr.add(firstId);
		} 
		if(secondId!=UNACCESSABLE){
			arr.add(secondId);
		}
		
		for(int i=0; i< others.size(); i++){
			arr.add(others.get(i).getId());
		}
		
		return arr;
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public boolean isBreak() {
		return isBreak;
	}
	
	public void setLineNum(int n){
		this.lineNum = n;
	}
	public int getLineNum(){
		return this.lineNum;
	}
	public void setBeginLine(int n){
		this.beginLine = n;
	}
	public int getBeginLine(){
		return this.beginLine;
	}
	public void setEndLine(int n){
		this.endLine = n;
	}
	public int getEndline(){
		return this.endLine;
	}


}

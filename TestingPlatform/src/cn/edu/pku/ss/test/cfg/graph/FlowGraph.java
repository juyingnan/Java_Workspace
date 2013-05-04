package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;

import cn.edu.pku.ss.test.util.Log;

public class FlowGraph extends ArrayList<GraphNode> {

	private GraphPathArray 	paths = new GraphPathArray();
	
	public GraphNode getById(int id){
		for(int i=0; i < this.size(); i++){
			if(this.get(i).getId() == id){
				return this.get(i);
			}
		}
		return null;
	}
	
	public GraphNode getLast2(){
		if(this.size()>=2)
			return getById(this.size()-2);
		else return null;
	}
	
	public GraphNode getLast(){
		if(this.size()>0){
			return get(this.size()-1);
		} else {
			return null;
		}		
	}
	
	public void setLastNext(int id){
		
		for(int i = this.size()-1; i>=0; i--){
			if(this.get(i).isEnd() || this.get(i).isContinue()){
				continue;
			} else {				
				this.get(i).setNext(id);
				//Debug.println(i + "**" + id);
				break;
			}
		}
	}
	

	public void add(int nodeId, int lineNum){
		GraphNode gn = new GraphNode();
		gn.setId(nodeId);
		gn.setLineNum(lineNum);
		this.add(gn);
	}
	
	public void add(int nodeId, int firstId, int lineNum){
		GraphNode gn = new GraphNode();
		gn.setId(nodeId);
		gn.setLineNum(lineNum);
		gn.setFirstId(firstId);
		this.add(gn);
	}
	
	public void add(int nodeId, boolean isLoop, int lineNum){
		GraphNode gn = new GraphNode();
		gn.setLoop(isLoop);
		gn.setId(nodeId);
		gn.setLineNum(lineNum);
		this.add(gn);
	}
	
	

	
	public GraphPathArray getPaths(){
		return paths;
	}

	private Graph4FindBasisSet getGraph(){
		Graph4FindBasisSet graph = new Graph4FindBasisSet();
		
		for(int i=0;i<this.size();i++){
			Node4FindBasisSet node = new Node4FindBasisSet();
			GraphNode temp;
			temp = this.get(i);
			node.id = temp.Id;
			node.firstNode = temp.firstId;
			node.secondNode = temp.secondId;
			node.visited = false;
			
			//set destination of the default Edge
			node.destOfDftEdg = temp.firstId;
			if(temp.firstId == -1){
				node.destOfDftEdg = temp.firstId;
			}
			
			if(temp.isEnd){
				node.destOfDftEdg = -99; //sink node
			}
			
			graph.add(node);
		}
		
		System.out.println(graph);
		
//		for(int i=0;i<this.size();i++){
		for(int i = this.size()-1; i >= 0;i--){
			GraphNode temp = this.get(i);
			if(temp.isLoop || temp.isLoopEnd()){
				Node4FindBasisSet node = graph.get(i);	
				int nextNodeID = graph.getByID(node.destOfDftEdg).id;
				while(true){
					Node4FindBasisSet nextNode = graph.getByID(nextNodeID);
					if(nextNode.destOfDftEdg == -99){
						break;
					}
					if(node.id == nextNode.id){
						node.destOfDftEdg = node.secondNode;
						break;
					}
					nextNodeID = nextNode.destOfDftEdg;
				}
				System.out.println("--"+graph);
			}
		}
		return graph;
	}

	public GraphPathArray generatPaths() {
		paths = new GraphPathArray();	
		
		Graph4FindBasisSet tempGraph = this.getGraph();
		String basisSet = tempGraph.findBasis(tempGraph.get(0));
		System.out.println("+++++++++++++++++++++++++:"+basisSet);
		
		
		for(int i=0; i < basisSet.split(",").length; i++){
			GraphPath graphPath = new GraphPath();
//			for (int j =0; j < basisSet.split(",")[i].length();j++){
			for (int j = basisSet.split(",")[i].length()-1; j >= 0; j--){
				int temp = Integer.parseInt(basisSet.split(",")[i].substring(j, j+1));
				graphPath.add(temp);
			}
			paths.add(graphPath);
		}
				
		Log.Debug(paths.size());
		return paths;	
	}
	
	@Override
	public String toString(){
		StringBuilder buff = new StringBuilder();
		for(int i = 0; i < this.size(); i++){
			buff.append(this.get(i).toString() + "\r\n");			
		}
		return buff.toString();
	}
	
	protected void visit(int pathId, int nodeId, boolean all){
		//Log.Debug("--");
		//Log.Debug(paths);
		GraphNode node = this.getById(nodeId);
		GraphPath path = paths.get(pathId);	
		//if(path.check(node.getId())){
			path.add(node.getId());
			if(node.isLoopEnd()){
				path.getLoopHistory().put(node.getLoopId(), node.getId());
			}
		//} else return;
		
		if(!node.isEnd()){
			if(node.getSecondId()!= GraphNode.UNACCESSABLE){				
				if(path.check(node.getSecondId())){
					GraphPath right = path.clone();					
					//Debug.println(right + "right" + right.getLoopEnds().toString());				
					paths.add(right);			
					visit(paths.size()-1, node.getSecondId(), all);
				}						
			}
			
			if(path.check(node.getFirstId())){
				//������һ���ڵ�
				visit(pathId, node.getFirstId(), all);
			}
		} 
		
	
	}
	
	protected void visit1(int pathId, int nodeId, boolean all){
		GraphNode node = this.getById(nodeId);
		GraphPath path = paths.get(pathId);		
		if(!node.isEnd()){
			//visitOthers(pathId, nodeId, all);
			path.add(node.getId());
			//���ֻ��һ����һ���ڵ�
			if(node.getSecondId()<0){
				if(node.isContinue()){					
					//Debug.println(node.getFirstId() + " : " + path.getLoops().pop() );
					int id = path.getLoops().pop();
					//����ѭ���ĵ�һ���ڵ�
					path.add(id);
					//�Ƴ�һ��ѭ��
					path.removeLoop(id);
					int next = this.getById(id).getSecondId();
					//������һ���ڵ�
					visit(pathId, next, all);
					
				} else {
					//���ȼ����һ���ڵ��ǲ��ǵ�ǰ·����ĳһ��ѭ���Ľ����ڵ�
					while(path.getLoopEnds().containsValue(node.getFirstId())){
						//Debug.println("xxx:" + gp + "===" + gp.getLoopEnds());
						
						//��ĳ��ѭ���Ľ�����
						int id = path.getLoops().pop();
						//����ѭ���ĵ�һ���ڵ�
						path.add(id);
						//�Ƴ�һ��ѭ��
						path.removeLoop(id);
					}
					//������һ���ڵ�
					visit(pathId, node.getFirstId(), all);
					//Debug.println(path.toString());
					//Debug.println(pathId + "\t" + node.getId() + "\t" + node.getFirstId());
				}
			}
			else{
				
				GraphPath right = path.clone();					
				//Debug.println(right + "right" + right.getLoopEnds().toString());				
				paths.add(right);			
				visit(paths.size()-1, node.getSecondId(), all);
				
				if(node.isLoop()){
					path.getLoopEnds().put(node.getId(), node.getSecondId());
					path.getLoops().push(node.getId());
				}
				
				visit(pathId, node.getFirstId(), all);
			}
		} else {
			//����Ƿ����û�����ѭ���Ľڵ�
			//���ȼ����һ���ڵ��ǲ��ǵ�ǰ·����ĳһ��ѭ���Ľ����ڵ�
			while(path.getLoopEnds().containsValue(nodeId)){
				//Debug.println("xxx:" + gp + "===" + gp.getLoopEnds());
				
				//��ĳ��ѭ���Ľ�����
				int id = path.getLoops().pop();
				//����ѭ���ĵ�һ���ڵ�
				path.add(id);
				//�Ƴ�һ��ѭ��
				path.removeLoop(id);
			}
			path.add(node.getId());
		}
		
		
	}

	protected void visitOthers1(int pathId, int nodeId, boolean all){
		GraphNode node = this.getById(nodeId);
		GraphPath path = paths.get(pathId);		
		
		for(int i=0; i< node.getOthers().size(); i++){
			boolean n = node.getOthers().get(i).getValue();
			if(!n && !all){
				continue;
			}
			if(path.isNecessary() && !n){
				path.setNecessary(false);
			}
			GraphPath o = path.clone();
			paths.add(o);			
			visit(paths.size()-1, node.getOthers().get(i).getId(), all);
		}
	}



	
}
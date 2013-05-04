package cn.edu.pku.ss.test.java.service;

import java.util.Iterator;
import java.util.Properties;

import org.jdom.Element;

import cn.edu.pku.ss.test.cfg.graph.FlowGraph;
import cn.edu.pku.ss.test.cfg.graph.GraphNode;
import cn.edu.pku.ss.test.cfg.graph.GraphPath;
import cn.edu.pku.ss.test.cfg.graph.GraphPathArray;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BPStumpWorker;
import cn.edu.pku.ss.test.java.bp.BPWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.Reporter;
import cn.edu.pku.ss.test.services.ITesting;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class JavaGetBasisPathSet implements ITesting {
	/**
	 * BP测试，以下三个参数是必的 <br>
	 * FileName=[TestClass.java]<br>
	 * TargetClass=[TestClass]<br>
	 * TargetMethod=[testMethod]<br>
	 * 
	 * */
	@Override
	public String testing(String code, String options) {

		BPStumpWorker w = new BPStumpWorker();
		TestData d = new TestData();
		Properties pro = PropertiesUtil.parse(options);
				
		String fileName = pro.getProperty(JobConst.FILENAME);
		d.setSourceCode(code, fileName);
		d.put(JobConst.TARGET_CLASS, pro.getProperty(JobConst.TARGET_CLASS));
		d.put(JobConst.TARGET_METHOD,pro.getProperty(JobConst.TARGET_METHOD));
		
		boolean b = w.run(d);

		if(b){
			
			//To generate the Flow Graph
			String rs = "<FlowGraph>\r\n";
			FlowGraph fg = (FlowGraph) d.get(JobConst.CONTROL_FLOW_GRAPH);
			Iterator<GraphNode> itGn = fg.iterator();
			while(itGn.hasNext()){
				GraphNode node = itGn.next();
				rs+=(("  <Node id=\""+node.getId()+"\" nodeSize=\"30\"/>\r\n"));
				if(node.getFirstId()!= -1)
					rs+=(("    <Edge fromID=\""+node.getId()+"\" toID=\""+node.getFirstId()+"\"/>\r\n"));
				if(node.getSecondId()!= -1)
					rs+=(("    <Edge fromID=\""+node.getId()+"\" toID=\""+node.getSecondId()+"\"/>\r\n"));
			}
			rs+="</FlowGraph>\r\n";
			
			rs+="$$$$$\n";
			
			//To generate the Basic Path Set
			rs += "<basicPathSet>\r\n";
			String[]temp = null;
			fg = (FlowGraph) d.get(JobConst.CONTROL_FLOW_GRAPH);
			GraphPathArray targetPaths = fg.generatPaths();	
			Iterator<GraphPath> itGp = targetPaths.iterator();
			while(itGp.hasNext()){
			GraphPath path = itGp.next();
				rs+=(("  <basicPath>\r\n"));
				temp = path.toString().substring(0,path.toString().indexOf(" {")).split(" ");
				for (int i = 0; i < temp.length; ++i)
					rs+=(("    <node id = \""+ temp[i] + "\"/>\r\n"));
				rs+=(("  </basicPath>\r\n"));
			}
			rs+="</basicPathSet>\r\n";
			return rs;
			
		} else {
			return d.getResult().getReport();
		}
		
	}
}

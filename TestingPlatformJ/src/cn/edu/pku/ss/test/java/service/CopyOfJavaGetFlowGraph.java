package cn.edu.pku.ss.test.java.service;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Properties;

import org.jdom.Element;
import org.junit.Test;

import cn.edu.pku.ss.test.cfg.graph.FlowGraph;
import cn.edu.pku.ss.test.cfg.graph.GraphNode;
import cn.edu.pku.ss.test.cfg.graph.GraphPath;
import cn.edu.pku.ss.test.cfg.graph.GraphPathArray;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BPStumpWorker;
import cn.edu.pku.ss.test.java.bp.BPVisitor;
import cn.edu.pku.ss.test.java.bp.BPVistor2;
import cn.edu.pku.ss.test.java.bp.BPWorker;
import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.Reporter;
import cn.edu.pku.ss.test.services.ITesting;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class CopyOfJavaGetFlowGraph implements ITesting
{
	/**
	 * BP测试，以下三个参数是必的 <br>
	 * FileName=[TestClass.java]<br>
	 * TargetClass=[TestClass]<br>
	 * TargetMethod=[testMethod]<br>
	 * 
	 * */
	@Override
	public String testing(String code, String options)
	{

		// Add line number```
		// begin
		TestData data = new TestData();
		Properties ipro = PropertiesUtil.parse(options);

		String ifileName = ipro.getProperty(JobConst.FILENAME);
		data.setSourceCode(code, ifileName);
		data.put(JobConst.TARGET_CLASS, ipro.getProperty(JobConst.TARGET_CLASS));
		data.put(JobConst.TARGET_METHOD, ipro.getProperty(JobConst.TARGET_METHOD));

		JavaParser p = new JavaParser();
		BPVistor2 v = new BPVistor2();

		p.run(data);
		v.run(data);

		FlowGraph ifg = (FlowGraph) data.get(JobConst.CONTROL_FLOW_GRAPH);
		Iterator<GraphNode> iitGn = ifg.iterator();
		// end

		BPStumpWorker w = new BPStumpWorker();
		TestData d = new TestData();
		Properties pro = PropertiesUtil.parse(options);

		String fileName = pro.getProperty(JobConst.FILENAME);
		d.setSourceCode(code, fileName);
		d.put(JobConst.TARGET_CLASS, pro.getProperty(JobConst.TARGET_CLASS));
		d.put(JobConst.TARGET_METHOD, pro.getProperty(JobConst.TARGET_METHOD));

		boolean b = w.run(d);

		if (b)
		{

			// To generate the Flow Graph
			String rs = "digraph G {\r\n";
			FlowGraph fg = (FlowGraph) d.get(JobConst.CONTROL_FLOW_GRAPH);
			System.out.println(ifg.size());
			for (int i = 0; i < ifg.size(); i++)
			{
				GraphNode temp = ifg.get(i);
				rs += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "[color=blue]" + "\r\n";
				if (temp.getFirstId() != -1)
				{
					rs += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->" + "\""
							+ ifg.getById(temp.getFirstId()).getBeginLine() + "-"
							+ ifg.getById(temp.getFirstId()).getEndline() + "\"" + "[color=blue]" + "\r\n";
				}
				if (temp.getSecondId() != -1)
				{
					rs += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->" + "\""
							+ ifg.getById(temp.getSecondId()).getBeginLine() + "-"
							+ ifg.getById(temp.getSecondId()).getEndline() + "\"" + "[color=blue]" + "\r\n";
				}
			}

			rs += "}";
			try
			{
				FileWriter dotFile = new FileWriter("C:/temp/test.dot");
				dotFile.write(rs);
				dotFile.close();
				java.lang.Runtime.getRuntime().exec("dot -Tpng -o c:/temp/test.png c:/temp/test.dot").waitFor();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			GraphPathArray basisPaths = fg.generatPaths();
			int basisSize = basisPaths.size();
			for (int a = 0; a < basisSize; a++)
			{
				GraphPath tempPath = basisPaths.get(a);
				String basisPathStr = "digraph G {\r\n";
				int z = 0;
				for (int i = 0; i < ifg.size(); i++)
				{
					GraphNode temp = ifg.get(i);
					int first = temp.getFirstId();
					int second = temp.getSecondId();
					boolean contain = false;
					for (int x = z; x < tempPath.size(); x++)
					{
						// while(z<tempPath.size()){
						if (temp.getId() == tempPath.get(z))
						{
							contain = true;
							z += 1;
							break;
						}
					}
					// if(tempPath.contains(temp.getId())){
					if (contain)
					{
						basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "[color=red]"
								+ "\r\n";
						if (first != -1)
						{
							// if(first == tempPath.get(tempPath.indexOf(i)+1)){
							if (first == tempPath.get(z))
							{
								basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->"
										+ "\"" + ifg.getById(temp.getFirstId()).getBeginLine() + "-"
										+ ifg.getById(temp.getFirstId()).getEndline() + "\"" + "[color=red]" + "\r\n";
							}
							else
							{
								basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->"
										+ "\"" + ifg.getById(temp.getFirstId()).getBeginLine() + "-"
										+ ifg.getById(temp.getFirstId()).getEndline() + "\"" + "[color=blue]" + "\r\n";
							}
						}
						if (second != -1)
						{
							// if(second ==
							// tempPath.get(tempPath.indexOf(i)+1)){
							if (second == tempPath.get(z))
							{
								basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->"
										+ "\"" + ifg.getById(temp.getSecondId()).getBeginLine() + "-"
										+ ifg.getById(temp.getSecondId()).getEndline() + "\"" + "[color=red]" + "\r\n";
							}
							else
							{
								basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->"
										+ "\"" + ifg.getById(temp.getSecondId()).getBeginLine() + "-"
										+ ifg.getById(temp.getSecondId()).getEndline() + "\"" + "[color=blue]" + "\r\n";
							}
						}
					}
					else
					{
						basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "[color=blue]"
								+ "\r\n";
						if (temp.getFirstId() != -1)
						{
							basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->" + "\""
									+ ifg.getById(temp.getFirstId()).getBeginLine() + "-"
									+ ifg.getById(temp.getFirstId()).getEndline() + "\"" + "[color=blue]" + "\r\n";
						}
						if (temp.getSecondId() != -1)
						{
							basisPathStr += "\"" + temp.getBeginLine() + "-" + temp.getEndline() + "\"" + "->" + "\""
									+ ifg.getById(temp.getSecondId()).getBeginLine() + "-"
									+ ifg.getById(temp.getSecondId()).getEndline() + "\"" + "[color=blue]" + "\r\n";
						}
					}
				}
				// for(int i=0;i<ifg.size();i++){
				// for(int c=0;c<tempPath.size();c++){
				// if(c == temp.getId()){
				// int first = temp.getFirstId();
				// int second = temp.getSecondId();
				// if(first!=-1 && first == c+1){
				// }
				// if(second!=-1 && second == c+1){
				// }
				// }
				// else{
				// if(temp.getFirstId()!=-1){
				// basisPathStr+="\""
				// +temp.getBeginLine()+"-"+temp.getEndline()+"\""+"->"+"\""+ifg.getById(temp.getFirstId()).getBeginLine()+"-"+ifg.getById(temp.getFirstId()).getEndline()+"\""+"[color=blue]"+"\r\n";
				// }
				// if(temp.getSecondId()!=-1){
				// basisPathStr+="\""+temp.getBeginLine()+"-"+temp.getEndline()+"\""+"->"+"\""+ifg.getById(temp.getSecondId()).getBeginLine()+"-"+ifg.getById(temp.getSecondId()).getEndline()+"\""+"[color=blue]"+"\r\n";
				// }
				// }
				// }
				// }

				basisPathStr += "}";
				try
				{
					String dotFileDir = "C:/temp/test" + (a + 1) + ".dot";
					FileWriter dotFile = new FileWriter(dotFileDir);
					dotFile.write(basisPathStr);
					dotFile.close();
					String imageDir = "c:/temp/test" + (a + 1) + ".png";
					String graphVizCmd = "dot -Tpng -o " + imageDir + " " + dotFileDir;
					java.lang.Runtime.getRuntime().exec(graphVizCmd).waitFor();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			rs = rs + "$$$$$" + basisSize;
			return rs;
		}
		else
		{
			return d.getResult().getReport();
		}

	}
}

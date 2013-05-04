package cn.edu.pku.ss.test.java.service;

import java.awt.List;
import java.util.ArrayList;
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

public class JavaGetFlowGraph implements ITesting
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
		data.put(JobConst.TARGET_METHOD,
				ipro.getProperty(JobConst.TARGET_METHOD));

		JavaParser p = new JavaParser();
		BPVistor2 v = new BPVistor2();

		p.run(data);
		v.run(data);

		FlowGraph ifg = (FlowGraph) data.get(JobConst.CONTROL_FLOW_GRAPH);
		Iterator<GraphNode> iitGn = ifg.iterator();
		// end

		BPStumpWorker w = new BPStumpWorker();
		// TestData d = new TestData();
		Properties pro = PropertiesUtil.parse(options);

		String fileName = pro.getProperty(JobConst.FILENAME);
		// d.setSourceCode(code, fileName);
		// d.put(JobConst.TARGET_CLASS, pro.getProperty(JobConst.TARGET_CLASS));
		// d.put(JobConst.TARGET_METHOD,
		// pro.getProperty(JobConst.TARGET_METHOD));

		// boolean b = w.run(d);

		if (true)
		{

			// To generate the Flow Graph
			String rs = "<FlowGraph>\r\n";

			int preNodeLineNum = 1;
			boolean Flag = true;

			// test
			ArrayList<Integer> A = new ArrayList<Integer>();
			ArrayList<Integer> B = new ArrayList<Integer>();
			A.add(-1);
			B.add(-1);
			boolean isDual = false;
			Iterator<GraphNode> iitGn2 = ifg.iterator();

			while (iitGn2.hasNext())
			{
				GraphNode inode = iitGn2.next();// changed by me```
				if (inode.getFirstId() != -1)
				{
					A.add(inode.getId());
					B.add(inode.getFirstId());
				}
				if (inode.getSecondId() != -1)
				{

					A.add(inode.getId());
					B.add(inode.getFirstId());
				}
			}

			while (iitGn.hasNext())
			{
				GraphNode inode = iitGn.next();// changed by me```

				Flag = false;
				rs += (("  <Node id=\"" + inode.getId() + "\" name=\""
						+ inode.getBeginLine() + " - " + inode.getEndline() + "\" nodeSize=\"30\" nodeColor=\"0x4682b4\"/>\r\n"));
				if (inode.getFirstId() != -1)
				{
					for (int i = 0; i < A.size(); i++)
					{
						if (A.get(i) == inode.getFirstId()
								&& B.get(i) == inode.getId())
						{
							isDual = true;
						}
					}
					if (isDual)
					{
						rs += (("    <Edge fromID=\"" + inode.getId()
								+ "\" toID=\"" + inode.getFirstId() + "\" color=\"0x4682b4\" edgeLabel=\"DUAL\"/>\r\n"));
					}
					else
					{
						rs += (("    <Edge fromID=\"" + inode.getId()
								+ "\" toID=\"" + inode.getFirstId() + "\" color=\"0x4682b4\"/>\r\n"));
					}
					isDual = false;
				}
				if (inode.getSecondId() != -1)
				{
					for (int i = 0; i < A.size(); i++)
					{
						if (A.get(i) == inode.getSecondId()
								&& B.get(i) == inode.getId())
						{
							isDual = true;
						}
					}
					if (isDual)
					{
						rs += (("    <Edge fromID=\"" + inode.getId()
								+ "\" toID=\"" + inode.getSecondId() + "\" color=\"0x4682b4\" edgeLabel=\"DUAL\"/>\r\n"));
					}
					else
					{
						rs += (("    <Edge fromID=\"" + inode.getId()
								+ "\" toID=\"" + inode.getSecondId() + "\" color=\"0x4682b4\"/>\r\n"));
					}
					isDual = false;
				}

			}

			// while (iitGn.hasNext())
			// {
			// GraphNode inode = iitGn.next();// changed by me```
			//
			// if (Flag)
			// {
			// Flag = false;
			// rs += (("  <Node id=\"" + inode.getId() + "\" name=\"" +
			// inode.getBeginLine() + " - "
			// + inode.getEndline() +
			// "\" nodeSize=\"10\" nodeColor=\"0x4682b4\"/>\r\n"));
			// if (inode.getFirstId() != -1)
			// {
			// rs += (("    <Edge fromID=\"" + inode.getId() + "\" toID=\"" +
			// inode.getFirstId() +
			// "\" color=\"0x4682b4\" edgeClass=\"rain\" flow=\"400\" />\r\n"));
			// }
			// if (inode.getSecondId() != -1)
			// {
			// rs += (("    <Edge fromID=\"" + inode.getId() + "\" toID=\"" +
			// inode.getSecondId() +
			// "\"  color=\"0xb682b4\" edgeClass=\"rain\" flow=\"1\" />\r\n"));
			// }
			// }
			// // preNodeLineNum = inode.getLineNum();
			// //
			// rs+=(("  <Node id=\""+node.getId()+"\" name=\""+preNodeLineNum+"-"+inode.getLineNum()+"\" nodeSize=\"30\" nodeColor=\"0x4682b4\"/>\r\n"));
			// else
			// {
			// rs += (("  <Node id=\"" + inode.getId() + "\" name=\"" +
			// inode.getBeginLine() + " - "
			// + inode.getEndline() +
			// "\" nodeSize=\"10\" nodeColor=\"0x4682b4\"/>\r\n"));
			// // preNodeLineNum = inode.getLineNum();
			// if (inode.getFirstId() != -1)
			// {
			// rs += (("    <Edge fromID=\"" + inode.getId() + "\" toID=\"" +
			// inode.getFirstId() +
			// "\"   color=\"0x4682b4\" edgeClass=\"rain\" flow=\"400\" />\r\n"));
			// }
			// if (inode.getSecondId() != -1)
			// {
			// rs += (("    <Edge fromID=\"" + inode.getId() + "\" toID=\"" +
			// inode.getSecondId() +
			// "\"  color=\"0xb682b4\" edgeClass=\"rain\" flow=\"1\"  />\r\n"));
			// }
			// }
			// }

			rs += "</FlowGraph>\r\n";

			rs += "$$$$$\n";

			// To generate the Basic Path Set
			rs += "<basicPathSet>\r\n";
			String[] temp = null;
			ifg = (FlowGraph) data.get(JobConst.CONTROL_FLOW_GRAPH);
			GraphPathArray targetPaths = ifg.generatPaths();
			Iterator<GraphPath> itGp = targetPaths.iterator();
			while (itGp.hasNext())
			{
				GraphPath path = itGp.next();
				rs += (("  <basicPath>\r\n"));
				temp = path.toString()
						.substring(0, path.toString().indexOf(" {")).split(" ");
				for (int i = 0; i < temp.length; ++i)
					rs += (("    <node id = \"" + temp[i] + "\"/>\r\n"));
				rs += (("  </basicPath>\r\n"));
			}
			rs += "</basicPathSet>\r\n";
			return rs;

		}
		else
		{
			return data.getResult().getReport();
		}

	}
}

package cn.edu.pku.ss.test.bp;

import java.util.ArrayList;
import java.util.HashSet;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import cn.edu.pku.ss.test.cfg.graph.FlowGraph;
import cn.edu.pku.ss.test.cfg.graph.GraphNode;
import cn.edu.pku.ss.test.cfg.graph.GraphPath;
import cn.edu.pku.ss.test.cfg.graph.GraphPathArray;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.Reporter;
import cn.edu.pku.ss.test.testcase.MethodParamArray;
import cn.edu.pku.ss.test.util.Tools;

public abstract class BPReporter extends Reporter {

	@Override
	public void calculateCoverage(TestData data) {
		MethodParamArray result = (MethodParamArray)data.get(JobConst.RESULT_TESTCASE);
		GraphPathArray  targetPath = (GraphPathArray)data.get(JobConst.RESULT_TARGET_PATH);
		GraphPathArray  actualPath = (GraphPathArray)data.get(JobConst.RESULT_ACTUAL_PATH);
		
		if(result==null || targetPath==null || actualPath==null){
			data.getResult().addReport("Parameter empty!");
			return;
		}
		
		double coverage = 0, coveredPath=0;
		
		for(int i=0;i< targetPath.size();i++){
			
			HashSet<Integer> set1 = targetPath.get(i).getHashSet(); 
			HashSet<Integer> set2 = actualPath.get(i).getHashSet(); 

			double c = Tools.canculateCoverage(set1, set2);
			coverage += c;
			if(c == 1)coveredPath++;			
		}
		
		coverage= coverage / targetPath.size();
		double coveragePath = coveredPath/targetPath.size();
		
		data.put(JobConst.RESULT_CONVERAGE, new Double(coverage));
		data.put(JobConst.RESULT_CONVERAGE_PATH, new Double(coveragePath));
		
	}

	@Override
	public void generateXmlReport(TestData data) {
		Element root = (Element)data.get(JobConst.XMLROOT);
				
		Element typeElement = new Element("Type");
		typeElement.setAttribute("name", "Basis Path Testing");
		//typeElement.setAttribute("coverageStrategyType", data.getStr(JobConst.CONDITION_TYPE));		
		root.addContent(typeElement);
				
		Element coverageElement = new Element("Coverage");
		Double d = (Double)data.get(JobConst.RESULT_CONVERAGE);
		//转换成百分比表示
		//NumberFormat nf = NumberFormat.getPercentInstance();
		coverageElement.setAttribute("Value", d*100+"%");
		root.addContent(coverageElement);
		
		FlowGraph fg = (FlowGraph) data.get(JobConst.CONTROL_FLOW_GRAPH);		
		Element fgElement = new Element("ControlFlowGraph");
		fgElement.addContent("\r\n" + GraphNode.HEADER + "\r\n" + fg.toString());
		root.addContent(fgElement);
		
		GraphPathArray targetPaths = (GraphPathArray)data.get(JobConst.RESULT_TARGET_PATH);
		GraphPathArray actualPaths = (GraphPathArray)data.get(JobConst.RESULT_ACTUAL_PATH);
		//Double coverage = (Double)data.get(JobConst.RESULT_CONVERAGE);
		MethodParamArray testCases = (MethodParamArray)data.get(JobConst.RESULT_TESTCASE);
		ArrayList<Object> returnValues =(ArrayList<Object>)data.get(JobConst.RESULT_RETURN_VALUES);
		for (int i = 0; i < actualPaths.size(); i++) {
			//ConditionResult targetPath = targetPaths.get(i);
			Element caseElement = new Element("TestCase");
			caseElement.setAttribute("method", data.getStr(JobConst.TARGET_METHOD));

			for (Object param : testCases.get(i)) {
				Element paramElement = new Element("Parameter");
				paramElement.setAttribute("type", param.getClass().getName());
				paramElement.setAttribute("value", param.toString());
				caseElement.addContent(paramElement);
			}

			if (returnValues != null) {
				Element retElement = new Element("ReturnValue");
				Object retVal = returnValues.get(i);
				if (retVal == null) {
					retElement.setAttribute("type", "void");
					retElement.setAttribute("value", "");
				} else {
					retElement.setAttribute("type", retVal.getClass().getName());
					retElement.setAttribute("value", retVal.toString());
				}
				caseElement.addContent(retElement);
			} 
			
			if (actualPaths != null) {
				Element pathElement = new Element("ExcutedPath");
				String path = actualPaths.get(i).toString();
				if (path != null) {
					pathElement.setAttribute("value", path);
				} else {
					pathElement.setAttribute("value", "");
				}
				caseElement.addContent(pathElement);
			}

			if (targetPaths != null) {
				Element pathElement = new Element("TargetPath");
				String path = targetPaths.get(i).toString();
				if (path != null) {
					pathElement.setAttribute("value", path);
				} else {
					pathElement.setAttribute("value", "");
				}
				caseElement.addContent(pathElement);
			}
			root.addContent(caseElement);
		}

		
		data.put(JobConst.XMLROOT, root);
		
	}
	
}

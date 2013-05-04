package cn.edu.pku.ss.test.condition;

import java.util.ArrayList;

import org.jdom.Element;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.Reporter;
import cn.edu.pku.ss.test.testcase.MethodParamArray;

public abstract class ConditionReporter extends Reporter {


	@Override
	public void calculateCoverage(TestData data) {
		MethodParamArray result = (MethodParamArray)data.get(JobConst.RESULT_TESTCASE);
		ConditionResultArray targetPath = (ConditionResultArray)data.get(JobConst.RESULT_TARGET_PATH);
		ConditionResultArray actualPath = (ConditionResultArray)data.get(JobConst.RESULT_ACTUAL_PATH);
		
		if(result==null || targetPath==null || actualPath==null){
			data.getResult().addReport("Parameter empty!");
			return;
		}
		
		double total = 0, covered = 0, coveredPath=0;
		
		for(int i=0;i< targetPath.size();i++){
			boolean c = true;
			for(int j=0;j< targetPath.get(i).size();j++){
				total++;
				if(targetPath.get(i).get(j) ==-1 || targetPath.get(i).get(j).equals(actualPath.get(i).get(j))){
					covered++;
				}else c =false;
				
					
			}
			if(c)coveredPath++;			
		}
		
		double coverage = covered / total;
		double coveragePath = coveredPath/targetPath.size();
		
		data.put(JobConst.RESULT_CONVERAGE, new Double(coverage));
		data.put(JobConst.RESULT_CONVERAGE_PATH, new Double(coveragePath));
		
	}

	

	@Override
	public void generateXmlReport(TestData data) {
		
		Element root = (Element)data.get(JobConst.XMLROOT);
		
		Element typeElement = new Element("Type");
		typeElement.setAttribute("name", "Condition");
		typeElement.setAttribute("coverageStrategyType", data.getStr(JobConst.CONDITION_TYPE));		
		root.addContent(typeElement);
				
		Element coverageElement = new Element("Coverage");
		Double d = (Double)data.get(JobConst.RESULT_CONVERAGE);
		//转换成百分比表示
		//NumberFormat nf = NumberFormat.getPercentInstance();
		coverageElement.setAttribute("Value", d*100+"%");
		root.addContent(coverageElement);
		
		
		ConditionResultArray targetPaths = (ConditionResultArray)data.get(JobConst.RESULT_TARGET_PATH);
		ConditionResultArray actualPaths = (ConditionResultArray)data.get(JobConst.RESULT_ACTUAL_PATH);
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
				Element pathElement = new Element("ActualExp");
				String path = actualPaths.get(i).toString();
				

				path = path.replace('1', 'T');
				path = path.replace('0', 'F');

				
				if (path != null) {
					pathElement.setAttribute("value", path);
				} else {
					pathElement.setAttribute("value", "");
				}
				caseElement.addContent(pathElement);
			}

			if (targetPaths != null) {
				Element pathElement = new Element("ExpectExp");
				String path = targetPaths.get(i).toString();
				

				
				path = path.replace("-1", "X");
				path = path.replace('1', 'T');
				path = path.replace('0', 'F');
				

				
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

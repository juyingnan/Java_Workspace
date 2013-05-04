package cn.edu.pku.ss.test.java.condition.jobs;

import java.util.ArrayList;

import cn.edu.pku.ss.test.condition.ConditionResult;
import cn.edu.pku.ss.test.condition.ConditionResultArray;
import cn.edu.pku.ss.test.condition.ConditionTree;
import cn.edu.pku.ss.test.condition.ConditionType;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.java.condition.ConditionPath;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.testcase.MethodParamArray;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.testcase.generators.GeneEvaluator;
import cn.edu.pku.ss.test.testcase.generators.GeneGenerator;

public class CodnitionTestCaseGenerator extends GeneGenerator {
	
	protected ConditionType type = ConditionType.DecisionCoverage;
	
	public CodnitionTestCaseGenerator() {
		this(new GeneEvaluator());		
	}
	
	public CodnitionTestCaseGenerator(GeneEvaluator evaluator) {
		super(evaluator);		
	}
	

	//TODO: 增加安全类型检测	
	@Override
	public boolean test(TestData data) {

//		System.out.println("----------------ConditionTestCaseGene TestData : " + data);
		this.executor.setMonitorType(MonitorType.Field);
		
		if(data.containsArg(JobConst.CONDITION_TYPE)){
			type = (ConditionType)data.get(JobConst.CONDITION_TYPE);
		}
		
		ConditionTree trees = (ConditionTree)data.get(JobConst.CONDITION_TREES);		
		if(trees == null) return false;
				
		ConditionResultArray targetPath = new ConditionResultArray();	
		
		switch(type){
		case DecisionCoverage:
			targetPath = trees.generateDCPath();
			break;
		case ConditionCoverage:
			targetPath = trees.generateCCPath();
			break;
		case MutipleCoverage:
			targetPath = trees.generateMCPath();
			break;
		case AllCoverage:
			targetPath = trees.generateAllPath();
			break;
		}
				
		MethodParamArray result = new MethodParamArray();
		ConditionResultArray actualPath = new ConditionResultArray();
		ArrayList<Object> returnValues = new ArrayList<Object>();
		
//		System.out.println("----------------ConditionTestCaseGene result before put : " + result);
		for (int i = 0; i < targetPath.size(); i++) {
//			System.out.println("----------------ConditionTestCaseGene result before add : " + result);
			result.add(geneFind(targetPath.get(i)));
//			System.out.println("----------------ConditionTestCaseGene result after add : " + result);
			ConditionPath path = (ConditionPath)((Monitor)executor.execute(result.get(i)).getMonitor()).getResult();			
			returnValues.add(executor.getExecutedResult().getReturn());
			actualPath.add(path.getResult());			
		}			
		
//		System.out.println("----------------ConditionTestCaseGene result after put : " + result);
		data.put(JobConst.RESULT_TARGET_PATH, targetPath);
		data.put(JobConst.RESULT_ACTUAL_PATH, actualPath);
		data.put(JobConst.RESULT_TESTCASE, result);
		data.put(JobConst.RESULT_RETURN_VALUES, returnValues);
		return true;
		
	}


	@Override
	public double evaluate(Object target, Object actual) {	
		
		ConditionResult actualPath = new ConditionResult();
		if(actual.getClass() == ConditionPath.class){
			actualPath = ((ConditionPath)actual).getResult();
		}
		ConditionResult targetPath = (ConditionResult)target;
		
		
		
		double similarity=0.0;
		int failedNumber=0,sucNumber=0;
		
		if(targetPath.size() != actualPath.size()){
			//Debug.println("The actual size does not match to the target size!");
			return 0;
		}
		
		for (int i = 0; i < targetPath.size(); i++) {
			if (targetPath.get(i) == -1
					|| targetPath.get(i).equals(actualPath.get(i))) {
				sucNumber++;
			} else
				failedNumber++;
		}
		
		similarity = (sucNumber-failedNumber)>0 ? sucNumber : 0;
	
		return similarity;
		
	}
	

	
}

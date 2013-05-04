package cn.edu.pku.ss.test.cpp.condition;

import java.util.ArrayList;

import cn.edu.pku.ss.test.condition.ConditionResult;
import cn.edu.pku.ss.test.condition.ConditionResultArray;
import cn.edu.pku.ss.test.condition.ConditionTree;
import cn.edu.pku.ss.test.condition.ConditionType;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.testcase.ExecuteResult;
import cn.edu.pku.ss.test.testcase.MethodParam;
import cn.edu.pku.ss.test.testcase.MethodParamArray;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.testcase.generators.GeneEvaluator;
import cn.edu.pku.ss.test.testcase.generators.GeneGenerator;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IDValuePairArray;

public class ConditionTestCaseGenerator extends GeneGenerator {

	protected ArrayList<Integer> conditionInfo = new ArrayList<Integer>();
	protected ConditionType conditionType = ConditionType.DecisionCoverage;

	public ConditionTestCaseGenerator() {
		super(new GeneEvaluator());
		
	}
	
	public ConditionTestCaseGenerator(GeneEvaluator evaluator) {
		super(evaluator);
		
	}

		
	@Override
	public boolean test(TestData data) {
		
		if(this.evaluator == null){
			return false;
		} 
		
		ConditionTree trees = (ConditionTree) data.get(JobConst.CONDITION_TREES);
		conditionType = (ConditionType) data.get(JobConst.CONDITION_TYPE);
		conditionInfo.clear();
		for(int i=0;i<trees.size(); i++){
			conditionInfo.add(trees.get(i).getCount());
		}
		
		ConditionResultArray targetPaths = null;
		
		switch(conditionType){
		case DecisionCoverage:
			 targetPaths = trees.generateDCPath();
			break;
		case ConditionCoverage:
			targetPaths = trees.generateCCPath();
			break;
		case MutipleCoverage:
			targetPaths = trees.generateMCPath();
			break;
		case AllCoverage:
			targetPaths = trees.generateAllPath();
			break;
		}		
		
		ConditionResultArray actualPaths = new ConditionResultArray();
		MethodParamArray teatCaseArr = new MethodParamArray();
		ArrayList<Object> returns = new ArrayList<Object>();
		this.executor.setMonitorType(MonitorType.Object);
		
		for(int i=0; i< targetPaths.size(); i++){
			MethodParam testCase = this.geneFind(targetPaths.get(i));
			teatCaseArr.add(testCase);	
			ExecuteResult r = this.executor.execute(testCase);
			actualPaths.add(parse(((Monitor)r.getMonitor()).getResult()));
			returns.add(r.getReturn());			
		}
		
		data.put(JobConst.RESULT_TARGET_PATH, targetPaths);
		data.put(JobConst.RESULT_ACTUAL_PATH, actualPaths);
		data.put(JobConst.RESULT_TESTCASE, teatCaseArr);
		data.put(JobConst.RESULT_RETURN_VALUES, returns);
		
		return true;
	}

	@Override
	public double evaluate(Object target, Object actual) {		
		
		ConditionResult targetPath = (ConditionResult)target;
		ConditionResult actualPath = parse(actual);
		
		
		double similarity=0.0;
		int failedNumber=0,sucNumber=0;
		
		//Debug.println(targetPath);
		//Debug.println(actualPath);
		
		if(targetPath.size() != actualPath.size()){
			Log.println("The actual size does not match to the target size!");
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
	
	@SuppressWarnings("unchecked")
	public ConditionResult parse(Object actual){
		
		IDValuePairArray<Integer,Boolean> list = (IDValuePairArray<Integer,Boolean>)actual;
		ConditionResult result = new ConditionResult();
		int index =0;
		for(int i=0; i< conditionInfo.size(); i++){
			for(int j=0; j< conditionInfo.get(i); j++){
				Boolean b = (Boolean)list.getValue(index + j);
				if(b==null){
					result.add(-1);
				}
				else{
					result.add(b==true? 1: 0);
				}				
			}
			index+=conditionInfo.get(i);
		}		
		
		return result;
		
	}

	
	
}

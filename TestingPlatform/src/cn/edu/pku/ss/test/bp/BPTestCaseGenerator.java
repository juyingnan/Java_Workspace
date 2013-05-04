package cn.edu.pku.ss.test.bp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import cn.edu.pku.ss.test.cfg.graph.FlowGraph;
import cn.edu.pku.ss.test.cfg.graph.GraphPath;
import cn.edu.pku.ss.test.cfg.graph.GraphPathArray;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.testcase.ExecuteResult;
import cn.edu.pku.ss.test.testcase.MethodParam;
import cn.edu.pku.ss.test.testcase.MethodParamArray;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.testcase.generators.GeneGenerator;
import cn.edu.pku.ss.test.util.Log;

public class BPTestCaseGenerator extends GeneGenerator  {
	
	@SuppressWarnings("unchecked")
	@Override
	public double evaluate(Object target, Object actual) {		
		
		ArrayList<Double> weights = new ArrayList<Double>(); // �������е�Ȩֵ����
		HashMap similaries = new HashMap(); // �����׵����ƶ�		
		HashSet<Integer> intersectionSet = new HashSet<Integer>(); // Ŀ��·����ʵ��·���Ľ���
		HashSet<Integer> unionSet = new HashSet<Integer>(); // Ŀ��·����ʵ��·���Ĳ���

		int order = 1; // һ��
		int currentSim;		
		
		// ��Ӧ�Ⱥ���
		GraphPath targetPath = (GraphPath) target;
		GraphPath actualPath = new GraphPath((ArrayList<Integer>)actual);
		
		// ����·���ĺϼ�
		for (int i = 0; i < targetPath.size(); i++) {
			unionSet.add(targetPath.get(i));
		}
		for (int i = 0; i < actualPath.size(); i++) {
			unionSet.add(actualPath.get(i));
		}
		// ����·���Ľ���
		Iterator<Integer> itr = unionSet.iterator();

		while (itr.hasNext()) {
			Integer element = itr.next();
			if (targetPath.contains(element) && actualPath.contains(element)) {
				intersectionSet.add(element);
			}
		}

		currentSim = intersectionSet.size() / unionSet.size();
		similaries.put(order, currentSim);// ����ǰ�ף�һ�ף������ƶȼ��뵽���ƶȼ���

		weights.add(1.1);// ��һ��Ȩ�����뵽Ȩ��������
		intersectionSet.clear();
		unionSet.clear();
		order++;

		ArrayList targetPathTemp = new ArrayList();// Ŀ��·���ڵ����ʱ��ſռ�
		ArrayList actualPathTemp = new ArrayList();// ʵ��·���ڵ����ʱ��ſռ�
		int size = targetPath.size() < actualPath.size() ? targetPath.size()
				: actualPath.size();
		while (order <= size) {
			String str = "";
			// ��order�׶β���
			for (int i = 0; i <= targetPath.size() - order; i++) {
				for (int j = 0; j < order + i; j++) {
					targetPathTemp.add(targetPath.get(j));
					unionSet.add(targetPath.get(j));
					//str += targetPath.get(j).toString();
				}
				//targetPathTemp.add(str);
				//unionSet.add(str);
				//str = "";
			}
			for (int i = 0; i <= actualPath.size() - order; i++) {
				for (int j = 0; j < order + i; j++) {
					//str += actualPath.get(j).toString();					
					targetPathTemp.add(actualPath.get(j));
					unionSet.add(actualPath.get(j));
				}
				//actualPathTemp.add(str);
				//unionSet.add(str);
				//str = "";
			}

			// ��order�׶ν���
			itr = unionSet.iterator();
			while (itr.hasNext()) {
				Integer element = itr.next();
				if (targetPathTemp.contains(element)
						&& actualPathTemp.contains(element)) {
					intersectionSet.add(element);
				}
			}
			currentSim = intersectionSet.size() / unionSet.size();
			similaries.put(order, currentSim);// ����ǰ�ף�order�ף������ƶȼ��뵽���ƶȼ���

			weights.add((double)targetPathTemp.size());// ����order�׵ļ�Ȩϵ��

			intersectionSet.clear();
			unionSet.clear();
			order++;
		}

		double f1, f2;
		double similary = 0.0;
		for (int i = 0; i < similaries.size(); i++) {
			f1 = Double.parseDouble(weights.get(i).toString());
			f2 = Double.parseDouble(similaries.get(i + 1).toString());
			similary += f1 * f2;
		}		
		//Debug.println(similary);		
		return similary;
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean test(TestData data) {
		FlowGraph gf = (FlowGraph) data.get(JobConst.CONTROL_FLOW_GRAPH);
		GraphPathArray targetPaths = gf.generatPaths();	
		
		GraphPathArray actualPaths = new GraphPathArray();
		MethodParamArray teatCaseArr = new MethodParamArray();
		ArrayList<Object> returns = new ArrayList<Object>();
		if(data.containsArg(JobConst.MONITOR_TYPE)) {
			this.executor.setMonitorType((MonitorType) data.get(JobConst.MONITOR_TYPE));
		} else {
			this.executor.setMonitorType(MonitorType.Object);
		}
		
		for(int i=0; i < targetPaths.size(); i++){
			MethodParam testCase = this.geneFind(targetPaths.get(i));
			teatCaseArr.add(testCase);
			ExecuteResult r = this.executor.execute(testCase);
			actualPaths.add((ArrayList<Integer>)((Monitor)r.getMonitor()).getResult());
			returns.add(r.getReturn());
		}
		
		data.put(JobConst.RESULT_TARGET_PATH, targetPaths);
		data.put(JobConst.RESULT_ACTUAL_PATH, actualPaths);
		data.put(JobConst.RESULT_TESTCASE, teatCaseArr);
		data.put(JobConst.RESULT_RETURN_VALUES, returns);
		
		return true;
	}

}

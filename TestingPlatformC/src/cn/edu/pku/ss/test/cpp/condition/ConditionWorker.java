package cn.edu.pku.ss.test.cpp.condition;

import cn.edu.pku.ss.test.condition.ConditionReporter;
import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.jobs.JNICompiler;
import cn.edu.pku.ss.test.cpp.jobs.visitors.CppParser;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.JobFlow;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class ConditionWorker extends ConditionStumpWorker {

	public ConditionWorker(){
		this.add(new JNICompiler());
		this.add(new ConditionTestCaseGenerator());
		this.add(new CppConditionReporter());
	}
	
}

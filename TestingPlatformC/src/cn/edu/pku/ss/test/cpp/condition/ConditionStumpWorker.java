package cn.edu.pku.ss.test.cpp.condition;

import cn.edu.pku.ss.test.condition.ConditionReporter;
import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.jobs.JNICompiler;
import cn.edu.pku.ss.test.cpp.jobs.visitors.CppParser;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.JobFlow;
import cn.edu.pku.ss.test.jobs.compilers.JavaCompiler;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class ConditionStumpWorker extends JobFlow {

	public ConditionStumpWorker(){
		this.add(new CppParser());
		this.add(new ConditionVisitor());		
	}
	
	@Override
	public boolean run(TestData data){
		if(!data.containsArg(Const.MONITOR_TEMPLATE)){
			String path = PropertiesUtil.getInstance().readValue(PropertiesUtil.TEMPLATE_PATH) + "ConditionMonitor.java";
			data.put(Const.MONITOR_TEMPLATE, IOUtil.read(path));
			//data.put(Const.MONITOR_TEMPLATE, IOUtil.read("templates\\ConditionMonitor.java"));
			data.put(Const.FUNC_VISIT_METHOD_SIGNATURE, "(IZ)Z");
		}
		if(!data.containsArg(JobConst.CLASS_PATH)){
			data.put(JobConst.CLASS_PATH, JavaCompiler.getClassPath());
		}
		return super.run(data);
	}
}

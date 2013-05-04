package cn.edu.pku.ss.test.jobs;


import java.util.ArrayList;

import cn.edu.pku.ss.test.data.TestData;

public class JobFlow extends ArrayList<IJob> implements IJob {
	
	//默认可中断式
	protected FlowType flowType = FlowType.Interruptted;
	

	public JobFlow(){
				
	}
	
	public FlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}

	
	public boolean run(TestData data){
		
//		System.out.println("__________________________________");
//		System.out.println("run data :　" + data);
//		System.out.println("__________________________________");
		
		if(data.containsArg(JobConst.JOB_FLOW_TYPE)){
			this.flowType = (FlowType) data.get(JobConst.JOB_FLOW_TYPE);
		}
		
		for(int i=0; i < this.size(); i++){
			try{
//				System.out.println("______________________________");
//				System.out.println("this.get(i).toString :　" + this.get(i).toString()); 
//				System.out.println("data : " + data.toString() + "data.getSourceCode : " + data.getSourceCode());
//				System.out.println("______________________________");
				boolean r = this.get(i).run(data);
				
				if(!r){
					data.getResult().addReport("Job " + i + " running err!");
					if(this.flowType == FlowType.Interruptted){
						data.getResult().addReport("Job Flow interrupted!");
						data.getResult().addReport("Error class: " + this.get(i).getClass());
						
						return false;
					}				
				}
				else{
					data.getResult().addReport("Job " + i + " running success!");
				}
			} catch(Exception ex){
				if(this.flowType == FlowType.Interruptted){
					data.getResult().addReport("Job Flow interrupted!");
					data.getResult().addReport("Error class: " + this.get(i).getClass());
					data.getResult().addReport("Error Message: " + ex.toString());
					return false;
				}				
			}
		}
		
		return true;
		
//		TestResult tr = new TestResult();
//		// 解析AST
//		Object ast = parser.parse(data.getSourceFile());
//		if (ast == null) {
//			tr.addReport("AST parse err! ");
//			return tr;
//		}
//
//		// 遍历AST完成插桩
//		// 遍历同时产生期望路径
//		if (!visitor.visitAST(ast, data)) {
//			tr.addReport("visitor err! ");
//			return tr;
//		}
//
//		// 生成临时文件夹
//		String output = Tools.getTempPath();
//		
//		// 存储这个目录
//		data.put(TARGET, output);
//

//
//		// 编译
//		if (!compiler.compile(data)) {
//			tr.addReport(compiler.getComiledReport());
//			return tr;
//		}
//
//		// 测试并返回结果
//		return tester.test(data);
		
	}
	
	
	public enum FlowType{
		/**
		 * 可中断式的
		 * */
		Interruptted,
		/**
		 * 不可中断式的，一直执行
		 * */
		Continuous
	}
	
	
}

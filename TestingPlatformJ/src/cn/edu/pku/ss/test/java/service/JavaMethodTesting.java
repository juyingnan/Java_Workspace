package cn.edu.pku.ss.test.java.service;

import cn.edu.pku.ss.test.data.TestData;

import cn.edu.pku.ss.test.java.Const;
import cn.edu.pku.ss.test.java.jobs.MethodWorker;
import cn.edu.pku.ss.test.java.util.MethodTable;
import cn.edu.pku.ss.test.services.ITesting;

public class JavaMethodTesting implements ITesting {
	
	/**
	 *  获取 Java的方法列表，只需要Code参数
	 * */
	@Override
	public String testing(String code, String options) {
		
		TestData d = new TestData();
		d.setSourceCode(code, "Testing.java");
		MethodWorker w  = new MethodWorker();
		boolean b = w.run(d);
		if(b){
			MethodTable mt = (MethodTable) d.get(Const.METHODTABLE);
			if(mt!=null){
				return mt.toLinerString();
			} else {
				return "EmptyMethod" + d.getResult().getReport();
			}
			
		} else {
			return d.getResult().getReport();
		}
	}
}
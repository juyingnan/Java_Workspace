package cn.edu.pku.ss.test.jobs.compilers;

import java.io.File;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.IOUtil;

public abstract class Compiler implements IJob{

	public final static String SRC = "src";
	public final static String BIN = "bin";
	
	protected String out_src = "";
	protected String out_bin = "";
		
	
	@Override
	public boolean run(TestData data) {
		
		//��ȡ��ʱ���Ŀ¼
		data.getStr(JobConst.TARGET_PATH_SRC);
		//��ȡ����Ŀ¼
		out_bin = data.getStr(JobConst.TARGET_PATH_BIN);
		
		if(out_src.length()==0 || out_bin.length()==0){
			initializePath();
		}
		
		//��飬����Ŀ¼
		IOUtil.mkdirs(out_src);
		IOUtil.mkdirs(out_bin);
		data.put(JobConst.TARGET_PATH_SRC, out_src);
		data.put(JobConst.TARGET_PATH_BIN, out_bin);
		
		return compile(data);
	}
	
	public String getSrc(){
		if(out_src.length()==0){
			initializePath();
		}
		return out_src;
	}
	
	

	public String getBin(){
		if(out_bin.length()==0){
			initializePath();
		}
		return out_bin;
	}
	
	protected void initializePath() {
		String temp = IOUtil.getTempPath();
		out_src = temp + SRC + File.separator ;
		out_bin = temp + BIN + File.separator ;
	}
	
	public abstract boolean compile(TestData data);
}

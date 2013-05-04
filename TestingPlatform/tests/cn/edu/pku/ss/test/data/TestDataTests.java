package cn.edu.pku.ss.test.data;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tests.common.Setting;


import java.io.File;
import java.util.ArrayList;

import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class TestDataTests{
	String path = Setting.BasePath + "Testing\\TestData\\";
	File file = new File(path + "target\\");
	TestData data = new TestData();
	
	@Before
	public void setUp() throws Exception {
		//清空目录
		IOUtil.recursiveRemoveDir(file);
		//创建目录
		if(!file.exists()){
			file.mkdirs();
		}
		
		data.setSourcePath(path + "src\\TestDataSource.java");
		data.addAttachedFile(path + "src\\AnotherCompile.java");
		data.addAttachedFile(path + "src\\Data\\CompileList.java");
		data.addAttachedFile(path + "src\\Data\\", false);
		data.addAttachedFile(path + "AnotherTestData.java");
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	
	
	@Test
	public void testCopy(){
		data.copyList(file.toString());
		
		String[] list = new String[]{"AnotherTestData.java", 
				"AnotherCompile.java",
				"Data\\CompileList.java",
				"Data\\CopyTest.txt"};
		
		//检查这几个文件是否存在		
		for(int i=0; i< list.length; i++){
			File checkfile = new File(file.toString() + "\\" +  list[i]);
			Log.println(checkfile.toString());
			Assert.assertEquals(checkfile.toString() + " err!" , true, checkfile.exists());
		
		}		
	}
	
	@Test
	public void testCompiledList(){
		ArrayList<String> compiles = data.getCompiledList();
		
		String[] list = new String[]{"AnotherTestData.java", 
				"AnotherCompile.java",
				"Data\\CompileList.java",
				"TestDataSource.java"};
		
		Log.println(compiles);
		
		//检查这几个文件是否存在		
		for(int i=0; i< list.length; i++){
			Assert.assertEquals(compiles.contains(list[i]), true);
			
		}		
	}
	
}
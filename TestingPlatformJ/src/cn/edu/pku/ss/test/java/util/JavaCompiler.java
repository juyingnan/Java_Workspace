package cn.edu.pku.ss.test.java.util;

import java.io.File;
import java.util.Random;

import cn.edu.pku.ss.test.util.IOUtil;

/**
 * Java的编译器，基于JDK 1.6
 * */
public class JavaCompiler {
	
	private String targetDir;
	private String classPath;
	private String warnFile;
	
	
	public static final String PREFIX = "jcctesting_";
	public static final Random RANDOM = new Random();
		

	/**
	 * 构造一个Java编译器，并初始化临时输入出目录
	 * */
	public JavaCompiler(){
		targetDir = System.getProperty("java.io.tmpdir");
		String dir = PREFIX + RANDOM.nextInt(99999999);
		targetDir +=  dir + File.separatorChar ;
		String basePath =  new File("").getAbsolutePath();
		classPath = ".;";
		classPath += basePath + ";" ;
		classPath += basePath + "\\bin\\;" ;
		classPath += basePath + "\\lib\\;" ;
		classPath += basePath + "\\libs\\;" ;		
		warnFile = targetDir + "warn.txt";
	}
	
	/**
	 * 检查输出目录是否存在，如果不存在就创建
	 * */
	public void initialize(){
		File file = new File(targetDir);		
		if(!file.exists()){
			try {				
				file.mkdir();
			} catch (SecurityException e) {				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 编译输入的源文件
	 * @return true 编译成功， false 编译失败，失败信息写入输出目录的warn.txt
	 * */
	public boolean compile(String sourcePath){
		initialize();
		
		String[] args;
		//可以没有classPath
		if(classPath == null){
			String[] str= { "-Xstdout", warnFile, "-d", targetDir, sourcePath };
			args = str;
		}
		else{
			String[] str= { "-cp", classPath ,"-Xstdout", warnFile, "-d", targetDir, sourcePath };
			args = str;
		}
		
		com.sun.tools.javac.Main.compile(args);

		
		File warn = new File(warnFile);
		if(warn.length()>0){
//			System.out.println(IOUtil.read(warnFile));
			return false;
		}
		else return true;
		
		
		
	}
	
	/**
	 * 编译输入的源文件，并指定一个输出目录
	 * @return true 编译成功， false 编译失败，失败信息写入输出目录的warn.txt
	 * */
	public void compile(String sourcePath, String targetDir){
		setTargetDir(targetDir);
		compile(sourcePath);
	}

	/**
	 * 设定输出目录
	 * */
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
		warnFile = targetDir + "warn.txt";
	}

	/**
	 * 获取输出目录
	 * */
	public String getTargetDir() {
		initialize();
		return targetDir;
	}

	/**
	 * 设定ClassPath目录
	 * */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * 获取ClassPath目录
	 * */
	public String getClassPath() {
		return classPath;
	}

}

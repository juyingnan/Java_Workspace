package cn.edu.pku.ss.test.java.util;

import java.io.File;
import java.util.Random;

import cn.edu.pku.ss.test.util.IOUtil;

/**
 * Java�ı�����������JDK 1.6
 * */
public class JavaCompiler {
	
	private String targetDir;
	private String classPath;
	private String warnFile;
	
	
	public static final String PREFIX = "jcctesting_";
	public static final Random RANDOM = new Random();
		

	/**
	 * ����һ��Java������������ʼ����ʱ�����Ŀ¼
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
	 * ������Ŀ¼�Ƿ���ڣ���������ھʹ���
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
	 * ���������Դ�ļ�
	 * @return true ����ɹ��� false ����ʧ�ܣ�ʧ����Ϣд�����Ŀ¼��warn.txt
	 * */
	public boolean compile(String sourcePath){
		initialize();
		
		String[] args;
		//����û��classPath
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
	 * ���������Դ�ļ�����ָ��һ�����Ŀ¼
	 * @return true ����ɹ��� false ����ʧ�ܣ�ʧ����Ϣд�����Ŀ¼��warn.txt
	 * */
	public void compile(String sourcePath, String targetDir){
		setTargetDir(targetDir);
		compile(sourcePath);
	}

	/**
	 * �趨���Ŀ¼
	 * */
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
		warnFile = targetDir + "warn.txt";
	}

	/**
	 * ��ȡ���Ŀ¼
	 * */
	public String getTargetDir() {
		initialize();
		return targetDir;
	}

	/**
	 * �趨ClassPathĿ¼
	 * */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * ��ȡClassPathĿ¼
	 * */
	public String getClassPath() {
		return classPath;
	}

}

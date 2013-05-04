package cn.edu.pku.ss.test.jobs.compilers;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class JavaCompiler extends Compiler{

	public final static String WARN = "tp_jcompiler_warn.txt";
	public final static String MAKE = "tp_jcompiler_make.txt";
	public static String staticBin;
	public static String staticClassPath;
	public static String staticSourceFileName;
	
	private String warnFile;

	@Override
	public boolean compile(TestData data) {
		System.out.println("...enter JavaCompiler comple(data)...");
		
		if(data==null) return false;
		//读取源码
		String source = data.getStr(JobConst.TARGET_SOURCE);
		
		
		
//		System.out.println("++++++++++++++++++++++");
//		System.out.println("souce code : " + source);
//		if(source.indexOf("();")!=-1){
//			String s = new String(source);
//			Pattern pattern = Pattern.compile("=\\s*\\w*\\(\\)\\;");
//			Matcher matcher = pattern.matcher(s);
////			System.out.println(matcher.replaceAll("= 5"));
//			source = matcher.replaceAll(" = 5;\\/\\/need mocked");
//		}
		String s = new String(source);
		Pattern pattern = Pattern.compile("valueReturned\\s*=\\s*\\w");
		Matcher matcher = pattern.matcher(s);
//		System.out.println(matcher.replaceAll(s +"\\/\\/need mocked"));
		source = matcher.replaceAll("valueReturned = 8;\\/\\/need mocked");
//		System.out.println("++++++++++++++++++++++");
		//输出源码
		
		data.copyList(getSrc());
		// 组合输入文件名, 写入临时输出目录
		IOUtil.write(getSrc() + data.getSourceFile().getName(), source);
		setStaticSourceFileName(data.getSourceFile().getName());
//		System.out.println("========++++++" + data.getSourceFile().getName());
		//输出信息
		warnFile = getSrc() + WARN;
		
		String classPath = data.getStr(JobConst.CLASS_PATH);
		if(classPath.isEmpty()){
			classPath = getClassPath();
		}
		//Debug.println(classPath);
		
		//输出make文件		
		StringBuilder sb = new StringBuilder();
		ArrayList<String> arr = data.getCompiledList();
		for(int i=0;i<arr.size();i++){
			sb.append(getSrc() + arr.get(i)+ JobConst.ENTER);
		}
		Log.println("1111"+getSrc());
		//Debug.println(classPath);
		IOUtil.write(getSrc() + MAKE, sb.toString());
		
		if(!compile(classPath, getBin(), "@"+getSrc() + MAKE, warnFile)){
			data.getResult().addReport(IOUtil.read(warnFile));
			//data.put(JobConst.COMPILED_REPORT, IOUtil.read(warnFile));
			return false;
		} else return true;
		
	}

	public static String getClassPath() {
		// 类库信息
		String classPath = System.getProperty("java.library.path");
		classPath += System.getProperty("java.class.path");
//		System.out.println("^^^^^^^^^ java classpath : " + System.getProperty("java.class.path"));
		String basePath = IOUtil.getBasePath();		
		
		if(PropertiesUtil.exist()){
			basePath = PropertiesUtil.getInstance().readValue(PropertiesUtil.CLASS_PATH);
		}
//		System.out.println("^^^^^^^^^basePath : " + basePath);
		
		File file = new File(basePath);
		if(!file.exists()){
			Log.println("Warn: ClassPath=" + basePath + " does not exist!");
		} else if(file.isDirectory()){
			classPath += basePath + ";";			
			classPath += IOUtil.listJarFiles(basePath, ";");
		} else if(file.getName().toLowerCase().endsWith(".jar")){
			classPath +=basePath+ ";";				
		} else {
			classPath += IOUtil.read(basePath).replace("\r\n", "").replace("\n", "");
		}

		return classPath;
	}

	public static boolean compile(String classPath, String bin, String src, String warn){
		setStaticClassPath(classPath);
		setStaticBin(bin);
//		setStaticSrc(src)
//		setStaticWarn(warn);
		//Debug.println("Debug: ClassPath=" + classPath);
//		System.out.println("((((((compile(String classPath, String bin, String src, String warn)");
//		System.out.println("classPath : " + classPath + ";bin : " + bin + ";src : " + src);
//		System.out.println("))))))))))))))))))");
		String[] args= { "-cp", classPath ,"-Xstdout", warn, 
				"-d", bin, src};
		
//		Log.println("Debug: -cp " + classPath + " -Xstdout " + warn + " -d " + bin + " " + src);
		//for(String s: args){
		//	Debug.println(s);
		//}
		
		File f = new File(bin);
		if(!f.exists()){
			try{
				System.out.println("************ !f.exists");
				f.mkdirs();
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}
		}
		
		//for(int i=0;i<args.length;i++){
		//	Debug.println(args[i]);
		//}
		
		//编译
		com.sun.tools.javac.Main.compile(args);

		
		//查看编译结果
//		System.out.println("~~~~~~~~~~~~JavaCompiler ：File warn path : " + warn);
		File result = new File(warn);
		if(result.length()>0){	
			return false;
		}
		else return true;
	}

	private static void setStaticBin(String bin) {
		// TODO Auto-generated method stub
		staticBin = bin;
	}

	private static void setStaticClassPath(String classPath) {
		// TODO Auto-generated method stub
		staticClassPath = classPath;
	}
	
	private void setStaticSourceFileName(String sourceFileName) {
		// TODO Auto-generated method stub
		staticSourceFileName = sourceFileName;
	}

	public static String getStaticBin(){
		return staticBin;
	}
	
	public static String getStaticClassPath(){
		return staticClassPath;
	}
	
	public static String getStaticSourceFileName(){
		return staticSourceFileName;
	}


/*
 * C:\Users\shootsoft>javac
用法：javac <选项> <源文件>
其中，可能的选项包括：
  -g                         生成所有调试信息
  -g:none                    不生成任何调试信息
  -g:{lines,vars,source}     只生成某些调试信息
  -nowarn                    不生成任何警告
  -verbose                   输出有关编译器正在执行的操作的消息
  -deprecation               输出使用已过时的 API 的源位置
  -classpath <路径>            指定查找用户类文件和注释处理程序的位置
  -cp <路径>                   指定查找用户类文件和注释处理程序的位置
  -sourcepath <路径>           指定查找输入源文件的位置
  -bootclasspath <路径>        覆盖引导类文件的位置
  -extdirs <目录>              覆盖安装的扩展目录的位置
  -endorseddirs <目录>         覆盖签名的标准路径的位置
  -proc:{none,only}          控制是否执行注释处理和/或编译。
  -processor <class1>[,<class2>,<class3>...]要运行的注释处理程序的名称；绕过默认
的搜索进程
  -processorpath <路径>        指定查找注释处理程序的位置
  -d <目录>                    指定存放生成的类文件的位置
  -s <目录>                    指定存放生成的源文件的位置
  -implicit:{none,class}     指定是否为隐式引用文件生成类文件
  -encoding <编码>             指定源文件使用的字符编码
  -source <版本>               提供与指定版本的源兼容性
  -target <版本>               生成特定 VM 版本的类文件
  -version                   版本信息
  -help                      输出标准选项的提要
  -Akey[=value]              传递给注释处理程序的选项
  -X                         输出非标准选项的提要
  -J<标志>                     直接将 <标志> 传递给运行时系统
 * 
 * */

}

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
		//��ȡԴ��
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
		//���Դ��
		
		data.copyList(getSrc());
		// ��������ļ���, д����ʱ���Ŀ¼
		IOUtil.write(getSrc() + data.getSourceFile().getName(), source);
		setStaticSourceFileName(data.getSourceFile().getName());
//		System.out.println("========++++++" + data.getSourceFile().getName());
		//�����Ϣ
		warnFile = getSrc() + WARN;
		
		String classPath = data.getStr(JobConst.CLASS_PATH);
		if(classPath.isEmpty()){
			classPath = getClassPath();
		}
		//Debug.println(classPath);
		
		//���make�ļ�		
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
		// �����Ϣ
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
		
		//����
		com.sun.tools.javac.Main.compile(args);

		
		//�鿴������
//		System.out.println("~~~~~~~~~~~~JavaCompiler ��File warn path : " + warn);
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
�÷���javac <ѡ��> <Դ�ļ�>
���У����ܵ�ѡ�������
  -g                         �������е�����Ϣ
  -g:none                    �������κε�����Ϣ
  -g:{lines,vars,source}     ֻ����ĳЩ������Ϣ
  -nowarn                    �������κξ���
  -verbose                   ����йر���������ִ�еĲ�������Ϣ
  -deprecation               ���ʹ���ѹ�ʱ�� API ��Դλ��
  -classpath <·��>            ָ�������û����ļ���ע�ʹ�������λ��
  -cp <·��>                   ָ�������û����ļ���ע�ʹ�������λ��
  -sourcepath <·��>           ָ����������Դ�ļ���λ��
  -bootclasspath <·��>        �����������ļ���λ��
  -extdirs <Ŀ¼>              ���ǰ�װ����չĿ¼��λ��
  -endorseddirs <Ŀ¼>         ����ǩ���ı�׼·����λ��
  -proc:{none,only}          �����Ƿ�ִ��ע�ʹ����/����롣
  -processor <class1>[,<class2>,<class3>...]Ҫ���е�ע�ʹ����������ƣ��ƹ�Ĭ��
����������
  -processorpath <·��>        ָ������ע�ʹ�������λ��
  -d <Ŀ¼>                    ָ��������ɵ����ļ���λ��
  -s <Ŀ¼>                    ָ��������ɵ�Դ�ļ���λ��
  -implicit:{none,class}     ָ���Ƿ�Ϊ��ʽ�����ļ��������ļ�
  -encoding <����>             ָ��Դ�ļ�ʹ�õ��ַ�����
  -source <�汾>               �ṩ��ָ���汾��Դ������
  -target <�汾>               �����ض� VM �汾�����ļ�
  -version                   �汾��Ϣ
  -help                      �����׼ѡ�����Ҫ
  -Akey[=value]              ���ݸ�ע�ʹ�������ѡ��
  -X                         ����Ǳ�׼ѡ�����Ҫ
  -J<��־>                     ֱ�ӽ� <��־> ���ݸ�����ʱϵͳ
 * 
 * */

}

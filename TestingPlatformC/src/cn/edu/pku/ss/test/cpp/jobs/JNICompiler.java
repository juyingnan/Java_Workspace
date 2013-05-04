package cn.edu.pku.ss.test.cpp.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.inject.CppEnvironment;
import cn.edu.pku.ss.test.cpp.inject.FunctionToMethod;
import cn.edu.pku.ss.test.cpp.jobs.visitors.CppParser;
import cn.edu.pku.ss.test.cpp.jobs.visitors.FunctionVisitor;
import cn.edu.pku.ss.test.cpp.jobs.visitors.SourceVisitor;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.compilers.Compiler;
import cn.edu.pku.ss.test.jobs.compilers.CppCompiler;
import cn.edu.pku.ss.test.jobs.compilers.JavaCompiler;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.PropertiesUtil;
import cn.edu.pku.ss.test.util.Tools;

public class JNICompiler extends Compiler {

	private TestData data = null;
	
	public boolean compileConnector(String template, CppEnvironment cppEnv, 
			String libName, ArrayList<CPPASTFunctionDefinition> funcArr, String stumpedCode){		
		
		//��һ������ ģ������һ��java�ļ�
		String code = template.replace("$Name$", cppEnv.getMonitorClassName());
		code = code.replace("$LibName$", libName);
		//��ȡԭ����������ת����Java���������Ҽ�¼ԭ������ԭ������
		ArrayList<String> funcDeclars = new ArrayList<String>();

		String methods = "";
		for(int i=0;i< funcArr.size();i++){
			//Java��Wrapper����ķ�������
			methods += FunctionToMethod.convert(funcArr.get(i)) + ";\r\n    ";
			//C++ lib����Դ���еķ�������
			FunctionVisitor sv = new FunctionVisitor();
			CPPASTFunctionDefinition cppDefi = funcArr.get(i);
			cppDefi.accept(sv);
			funcDeclars.add(sv.getSource());
			//Debug.println("xxx" + sv.getSource());
		}
		
		code = code.replace("$Methods$", methods);
		//д����ʱĿ¼
		String file = getSrc() + cppEnv.getMonitorClassName() + ".java";
		if(!IOUtil.write(file, code)){
			if(data!=null){
				data.getResult().addReport("Output wrapper failed!");
			}
		
			return false;
		}
		
		
		//�ڶ��� �������Java�ļ�		
		if(!JavaCompiler.compile(JavaCompiler.getClassPath(), getBin(), file,  getBin() + "warn.txt")){
			if(data!=null){
				data.getResult().addReport("Java wrapper compile failed!");
			}
			return false;
		}
		
		//String classFile = getBin() ;
		
		//������������javah ������Ӧ��ͷ�ļ�
		String[] args = new String[]{ "-classpath", getBin(),
				"-d", getSrc(), "-jni", cppEnv.getMonitorClassName()
		};
		com.sun.tools.javah.Main.main(args);
		
		
		//�������������ͷ�ļ�
		IOUtil.copyDir(PropertiesUtil.getInstance().readValue(PropertiesUtil.TEMPLATE_PATH) + Const.INCLUDE_PATH, getSrc());
				
		
		//������������templateName.h
		File header = new File(getSrc()+ cppEnv.getMonitorClassName() + ".h");
		if(!header.exists()){
			if(data!=null){
				data.getResult().addReport("Could not find the header file :" + header.toString());				
			}
			return false;
		}
		
		//����������ͷ�ļ�
		FunctionVisitor funcVisitorHeader = new FunctionVisitor();
		//׼����Source�ļ�
		String newSource = "#include \"" + cppEnv.getMonitorClassName() + ".h\"\r\n";
		newSource+=stumpedCode;

		CppParser.parse(header).accept(funcVisitorHeader);
		//�趨��Ӧ��Ŀ�꺯������
		ArrayList<CPPASTFunctionDeclarator> list = funcVisitorHeader.getFuncDeclarators();
		for(int i=0;i< list.size();i++){
			//�����µ�����
			FunctionVisitor funcNew = new FunctionVisitor(cppEnv);
			funcNew.getFuncTargets().put(funcArr.get(i), list.get(i));
			funcArr.get(i).accept(funcNew);
			String newFuncDeclar = funcNew.getSource();
			Log.println("Old: " + funcDeclars.get(i));
			Log.println("New: " + newFuncDeclar);
			Log.println(newSource.indexOf(funcDeclars.get(i)));
			newSource = newSource.replace( funcDeclars.get(i), newFuncDeclar);			
		}
		
	
		//���Դ��			
		String cpp = getSrc() + cppEnv.getMonitorClassName() + ".cpp";
		IOUtil.write(cpp, newSource);
		
		Log.println(cpp);
		Log.println(newSource);
		
		//����C++����
		String dll = getBin() + libName + ".dll";
		
		if(!CppCompiler.compileDll(dll + " " + cpp)){
			
			File dllFile = new File(dll);
			if(!dllFile.exists()){	
				if(data!=null){
					data.getResult().addReport("Cpp compile failed!");
				}
				return false;
			}		
		}		
		

		return true;
	}
	
	
	@Override
	public boolean compile(TestData data) {
		
		String template = data.getStr(Const.MONITOR_TEMPLATE);
		if(template.length()==0){
			template = IOUtil.read(PropertiesUtil.getInstance().readValue(PropertiesUtil.TEMPLATE_PATH) 
					+ Const.MONITOR_TEMPLATE_DEF);
			if(template==null || template.length()==0){
				data.getResult().addReport("Monitor template error!");
				return false;
			}
			data.put(Const.MONITOR_TEMPLATE, template);
		}
		//Cpp������صı����������͵���Ϣ
		CppEnvironment cppEnv = (CppEnvironment) data.get(Const.CPP_ENVIRONMENT);
		
		String libName = data.getStr(Const.LIB_NAME);
		if(libName.length()==0){
			libName = Const.LIB_PEFIX + Tools.RANDOM.nextInt(Integer.MAX_VALUE);
			 data.put(Const.LIB_NAME, libName);
		}
		
		String stumpedCode = data.getStr(Const.SOURCE_WITH_MONITOR);
		if(stumpedCode.length()==0){
			data.getResult().addReport("Stumped Code error!");
			return false;
		}
		
		CPPASTFunctionDefinition funcDefi = (CPPASTFunctionDefinition) data.get(Const.TARGET_FUNCTION);
		if(funcDefi!=null){
			ArrayList<CPPASTFunctionDefinition> funcArr = new ArrayList<CPPASTFunctionDefinition>();
			funcArr.add(funcDefi);	
			this.data = data;
			if(this.compileConnector(template, cppEnv, libName, funcArr, stumpedCode)){
				data.put(JobConst.TARGET_CLASS, cppEnv.getMonitorClassName());
				data.put(JobConst.TARGET_METHOD, funcDefi.getDeclarator().getName().toString());
				
				String libRuntimePath = data.getStr(Const.LIB_RUNTIME_PATH);
				if(libRuntimePath.length()==0){
					libRuntimePath = PropertiesUtil.getInstance().readValue(PropertiesUtil.TESTING_PATH);
					if(libRuntimePath.length()==0){
						libRuntimePath = Const.LIB_RUNTIME_PATH_DEF;
					}
					data.put(Const.LIB_RUNTIME_PATH, libRuntimePath);
				}
				Log.println("Src: " + getBin() + libName + ".dll");
				Log.println("Target: " + libRuntimePath + libName + ".dll");
				if(IOUtil.copy(getBin() + libName + ".dll", libRuntimePath + libName + ".dll")){
					return true;
				} else{
					data.getResult().addReport("Copy runtime lib failed!");
					return false;
				}
			}else {
				data.getResult().addReport("JNICompiler failed!");
				return false;
			}
		} else {
			data.getResult().addReport("Function list error!");
			return false;
		}
		
	}

	
//	E:\Programs\Java\workspace\JCC\src>javah
//	�÷���javah [ѡ��] <��>
//
//	���� [ѡ��] ������
//
//	        -help                 ����˰�����Ϣ���˳�
//	        -classpath <·��>     ����װ�����·��
//	        -bootclasspath <·��> ����װ���������·��
//	        -d <Ŀ¼>             ���Ŀ¼
//	        -o <�ļ�>             ����ļ���ֻ��ʹ�� -d �� -o �е�һ����
//	        -jni                  ���� JNI��ʽ��ͷ�ļ���Ĭ�ϣ�
//	        -version              ����汾��Ϣ
//	        -verbose              ������ϸ���
//	        -force                ʼ��д������ļ�
//
//	ʹ��ȫ�޶�����ָ�� <��>����
//	�磬java.lang.Object����
	
}

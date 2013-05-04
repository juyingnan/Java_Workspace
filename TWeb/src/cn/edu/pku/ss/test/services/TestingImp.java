package cn.edu.pku.ss.test.services;

import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import cn.edu.pku.ss.test.cpp.service.*;
import cn.edu.pku.ss.test.java.service.*;
import cn.edu.pku.ss.test.util.Log;


public class TestingImp implements ITesting {

	private static String[] types = new String[]{
		"CppAST","CppBPStump","CppBPTesting","CppConditionStump","CppConditionTesting",
		"JavaAST","JavaBPStump","JavaBPTesting","JavaConditionStump","JavaConditionTesting", 
		"FunctionTesting","MethodTesting"
		
	};
	
	private static String typesList = null;
	
	@Override
	public String testing(String code, String options) {	
		if(typesList==null) {
			typesList= "";
			for(String s: types){
				typesList += s + ",";
			}
			typesList = typesList.substring(0, typesList.length()-1);
		}
		return typesList;
	}
	
	public String testingImp(int type, String code, String options){
		Log.println("Receive: type=" + type + " options=" + options);
		long startTime = System.currentTimeMillis();
		ITesting testing;
		switch(type){
		case 0:
			System.out.println("0000000");
			testing = new CppASTTesting();
			break;
		case 1:
			System.out.println("1111111");
			testing = new CppBPStumpTesting();
			break;
		case 2:
			System.out.println("2222222");
			testing = new CppBPTesting();
			break;
		case 3:
			System.out.println("3333333");
			testing = new CppConditionStumpTesting();
			break;
		case 4:
			System.out.println("4444444");
			testing = new CppConditionTesting();
			break;
		case 5:
			System.out.println("5555555");
			testing = new JavaASTTesting();
			break;
		case 6:
			System.out.println("6666666");
			testing = new JavaBPStumpTesting();
			break;
		case 7:
			System.out.println("7777777");
			testing = new JavaBPTesting();
			break;
		case 8:
			System.out.println("8888888");
			testing = new JavaConditionStumpTesting();
			break;
		case 9:
			System.out.println("9999999");
			testing = new JavaConditionTesting();
			break;
		case 10:
			System.out.println("10101010");
			testing = new CppFunctionTesting();
			break;
		case 11:
			System.out.println("11,11,11");
			testing = new JavaMethodTesting();
			break;
		case 13:
			System.out.println("13131313");
			testing = new JavaGetFlowGraph();
			break;
		default:
			System.out.println("default...");
			testing = new TestingImp();
			
		}
		
		String result = testing.testing(code, options);
		System.out.println("-----------------------------");
		System.out.println(result);
//		System.out.println("result :¡¡" + result);
		long endTime= System.currentTimeMillis()- startTime;
		Log.println("Complete: cost " + endTime + "ms");
		
		if(type == 2|| type ==4 || type ==7 || type==9){
			return addTime(endTime, result);
		} else{
			return result;
		}
	}
	
	private String addTime(long time, String xml){
		//Document doc = new Document();
		StringReader stream = new StringReader(xml);
		SAXBuilder builder=new SAXBuilder(false);
		System.out.println("______________________________________________");
		System.out.println(xml);
		System.out.println("______________________________________________");
		try {
			Document doc = builder.build(stream);
			Element root = (Element) doc.getRootElement().clone();
			
			Element localTime = new Element("Cost");
			localTime.setAttribute("value", time + "ms");
			root.addContent(2, localTime);
			Document docOut = new Document(root);
			XMLOutputter xmlOut = new XMLOutputter();
			xmlOut.setFormat(Format.getPrettyFormat());
			//Debug.println("cccc");
			return xmlOut.outputString(docOut);
		} catch (Exception e) {			
			e.printStackTrace();
			return xml;
		}
		
	}
	

}

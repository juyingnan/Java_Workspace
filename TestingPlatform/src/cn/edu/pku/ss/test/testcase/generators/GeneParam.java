package cn.edu.pku.ss.test.testcase.generators;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import mock.IUserService;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.ByteGene;
import org.jgap.impl.CharGene;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.FloatGene;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.LongGene;
import org.jgap.impl.MyCrossoverOperator;
import org.jgap.impl.ObjectGene;
import org.jgap.impl.ShortGene;


import cn.edu.pku.ss.test.testcase.MethodParam;

public class GeneParam {
	private static int mockParameter;

	public static void addParameter(){
		mockParameter++;
		System.out.println("@@@@@@@@@@@######## mockParameter : " + mockParameter);
	}
	
	public static void recoverParameter(){
		mockParameter = 0;
	}
	
	public static Gene[] createGene(Method method, Configuration conf){
		Class[] methodParaTypes = method.getParameterTypes();
		Gene[] genes = new Gene[methodParaTypes.length+mockParameter]; // 基因来自方法的参数
//		Gene[] genes = new Gene[methodParaTypes.length]; // 基因来自方法的参数
		try {
			//Complete the code
			for (int i = 0; i < methodParaTypes.length+mockParameter; i++) {
//			for (int i = 0; i < methodParaTypes.length; i++) {
				boolean isObject=true;

				System.out.println("^^^^^^^^in GeneParam : methodParaTypes : " + methodParaTypes[i]);
				if(i<methodParaTypes.length){
					if (methodParaTypes[i] == int.class) {
						isObject=false;
						genes[i] = new IntegerGene(conf, -100, 100);
					}
					if (methodParaTypes[i] == boolean.class) {
						isObject=false;
						genes[i] = new BooleanGene(conf);
					}
					if (methodParaTypes[i] == double.class) {
						isObject=false;
						genes[i] = new DoubleGene(conf, -100, 100);
					}
					if (methodParaTypes[i] == float.class) {
						isObject=false;
						genes[i] = new FloatGene(conf, -100, 100);
					}
					if (methodParaTypes[i] == byte.class) {
						isObject=false;
						genes[i] = new ByteGene(conf,(byte)0, (byte)100);
					}
					if (methodParaTypes[i] == long.class) {
						isObject=false;
						genes[i] = new LongGene(conf,-1000,1000);
					}
					if (methodParaTypes[i] == char.class) {
						isObject=false;
						genes[i] = new CharGene(conf,(char)33,(char)255);
					}
					if (methodParaTypes[i] == short.class) {
						isObject=false;
						genes[i] = new ShortGene(conf,(short)-1000,(short)1000);
					}
//					if(methodParaTypes[i] == IUserService.class){
//						System.out.println("$$@@has interface parameter");
//						isObject = false;
//						IUserService iuserService = null;
//						genes[i] = (Gene) iuserService;
//					}
					if(isObject)
					{					
						conf.addGeneticOperator(new MyCrossoverOperator(conf));
						Class clazz = methodParaTypes[i];
						genes[i] =new ObjectGene(conf,clazz,1);
//						genes[i] =new ObjectGene(conf,methodParaTypes[i].getClass(),1);
//						genes[i] =new ObjectGene(conf,methodParaTypes[i].toString(),1);
					}
				}
				else{
//					if (methodParaTypes[i] == int.class) {
						isObject=false;
						genes[i] = new IntegerGene(conf, -100, 100);
//					}
				}
			}

		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} // initial:
		return genes;
	}
	
	public static MethodParam convertParam(Gene[] genes){
		MethodParam params = new MethodParam();
		for (int i = 0; i < genes.length; i++) {
			params.add(genes[i].getAllele());
		}
		return params;
	}
		
		
	
	public static MethodParam createParam(Method method, Gene[] genes){
		
		Type[] types = method.getParameterTypes();				
		MethodParam testCase=new MethodParam();	
		
		//将染色体转换为TestCase
		for (int i = 0; i < types.length; i++) {
			boolean isObject=true;
			if (types[i] == Integer.TYPE) {
				isObject=false;
				int temp = ((Integer) genes[i].getAllele()).intValue();
				testCase.add(temp);
			}
			if (types[i] == Boolean.TYPE) {
				isObject=false;
				boolean temp = ((Boolean) genes[i].getAllele()).booleanValue();
				testCase.add(temp);
			}
			if (types[i] == Double.TYPE) {
				isObject=false;
				double temp = ((Double) genes[i].getAllele()).doubleValue();
				testCase.add(temp);
			}
			if (types[i] == Float.TYPE) {
				isObject=false;
				float temp = ((Float) genes[i].getAllele()).floatValue();
				testCase.add(temp);
			}
			if (types[i] == Byte.TYPE) {
				isObject=false;
				byte temp = ((Byte) genes[i].getAllele()).byteValue();
				testCase.add(temp);
			}
//			if (types[i] == String.class) {
//				String temp = ((String)genes[i].getAllele()).toString();
//				testCase.add(temp);
//			}
			if (types[i] == Long.TYPE) {
				isObject=false;
				long temp = ((Long) genes[i].getAllele()).longValue();
				testCase.add(temp);
			}
			if (types[i] == Character.TYPE) {
				isObject=false;
				char temp = ((Character) genes[i].getAllele()).charValue();
				testCase.add(temp);
			}
			if (types[i] == Short.TYPE) {
				isObject=false;
				short temp = ((Short) genes[i].getAllele()).shortValue();
				testCase.add(temp);
			}
			if(isObject){
				try {
					testCase.add(((ObjectGene) genes[i]).CreateInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return testCase;
	}
	
}

package pku.main;

import java.io.IOException;

import org.apache.tuscany.sca.node.*;
import org.apache.tuscany.sca.node.impl.NodeFactoryImpl;
//org.apache.tuscany.sca.host.embedded.SCADomain;
import pku.calculator.CalculatorService;

public class CalculatorClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//node factory mode
		String location = ContributionLocationHelper.getContributionLocation(CalculatorService.class);
		System.out.println("Location : "+ location);
		Node node = NodeFactory.newInstance("Calculator.composite").createNode(new Contribution("default", location));
		System.out.println(node.DEFAULT_DOMAIN_URI + " " + node.DEFAULT_NODE_URI);
		
		node.start();
		CalculatorService calculatorService = node.getService(CalculatorService.class, "CalculatorServiceComponent");
		System.out.println("3 + 2=" + calculatorService.add(3, 2));
		System.out.println("3 - 2=" + calculatorService.subtract(3, 2));
		System.out.println("name=" + calculatorService.getName());
		node.stop();
		
		
//		//original
//		SCADomain scaDomain = SCADomain.newInstance("Calculator.composite");
//		CalculatorService calculatorService = scaDomain.getService(
//				CalculatorService.class, "CalculatorServiceComponent");		
//		
//		System.out.println("3 + 2=" + calculatorService.add(3, 2));
//		System.out.println("3 - 2=" + calculatorService.subtract(3, 2));
//
//		System.out.println("name=" + calculatorService.getName());
//
//		System.in.read();
//		scaDomain.close();
	}

}

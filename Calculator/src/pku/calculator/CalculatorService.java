package pku.calculator;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface CalculatorService {
	double add(double n1, double n2);

	double subtract(double n1, double n2);
	
	String getName();
}

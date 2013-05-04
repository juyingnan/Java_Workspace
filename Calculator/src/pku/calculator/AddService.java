package pku.calculator;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface AddService {
	 double add(double n1, double n2);
}

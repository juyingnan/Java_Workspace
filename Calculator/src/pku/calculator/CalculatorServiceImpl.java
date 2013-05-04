package pku.calculator;

import org.oasisopen.sca.annotation.Property;
import org.oasisopen.sca.annotation.Reference;

public class CalculatorServiceImpl implements CalculatorService {
	private AddService addService;
	private SubtractService subtractService;
	@Property
	protected String name;

    @Reference
    public void setAddService(AddService addService) {
        this.addService = addService;
    }

    @Reference
    public void setSubtractService(SubtractService subtractService) {
        this.subtractService = subtractService;
    }
    
	public double add(double n1, double n2) {
		return addService.add(n1, n2);
	}

	public double subtract(double n1, double n2) {
		return subtractService.subtract(n1, n2);
	}

	public String getName() {
		return name;
	}
}

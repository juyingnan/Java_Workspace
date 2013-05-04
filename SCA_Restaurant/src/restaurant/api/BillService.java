package restaurant.api;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface BillService
{
	double getBill(double menuPrice);
}

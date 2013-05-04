package restaurant.api;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface TipService
{
	double getPriceWithTip(double price);
}

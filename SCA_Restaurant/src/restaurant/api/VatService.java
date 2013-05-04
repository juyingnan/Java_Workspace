package restaurant.api;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface VatService
{
	double getPriceWithVat(double price);
}

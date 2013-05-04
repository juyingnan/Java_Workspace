package restaurant.api;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface RestaurantService
{
	Menu[] getMenus();

	double getBill(Menu menu);
}

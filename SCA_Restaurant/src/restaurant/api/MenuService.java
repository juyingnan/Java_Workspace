package restaurant.api;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface MenuService
{
	Menu[] getMenu();

	double getPrice(Menu menu);
}

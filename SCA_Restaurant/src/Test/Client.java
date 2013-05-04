package Test;

import org.apache.tuscany.sca.node.*;
import restaurant.api.Menu;
import restaurant.api.RestaurantService;

public class Client
{
	public static void main(String[] args) throws Exception
	{
		// SCADomain scaDomain = SCADomain.newInstance("Restaurant.composite");
		// RestaurantService restaurantService =
		// scaDomain.getService(RestaurantService.class,
		// "RestaurantServiceComponent");

		// node factory mode
		String location = ContributionLocationHelper.getContributionLocation(RestaurantService.class);
		System.out.println("Location : " + location);
		Node node = NodeFactory.newInstance("Restaurant.composite").createNode(new Contribution("default", location));
		System.out.println(node.DEFAULT_DOMAIN_URI + " " + node.DEFAULT_NODE_URI);

		node.start();
		RestaurantService restaurantService = node.getService(RestaurantService.class, "RestaurantServiceComponent");
		Menu[] menus = restaurantService.getMenus();
		System.out.println("--- Menu ---");
		for (Menu m : menus)
		{
			System.out.println("- " + m.printMenu());
		}
		System.out.println();
		Menu menu = menus[3];
		System.out.println("My choice: " + menu.printMenu());
		System.out.println();
		double price = restaurantService.getBill(menu);
		System.out.println("Price (" + menu.printMenu() + "): " + price);
		node.stop();
		// scaDomain.close();
	}
}
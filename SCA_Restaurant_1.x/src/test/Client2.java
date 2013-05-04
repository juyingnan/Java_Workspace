package test;

import java.rmi.Naming;
import restaurant.api.Menu;
import restaurant.api.RestaurantService;

public class Client2
{
	public static void main(String[] args) throws Exception
	{
		RestaurantService restaurantService = (RestaurantService) Naming.lookup("//localhost:8099/RestaurantServiceRMI");
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
	}
}
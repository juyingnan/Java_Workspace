package restaurant.lib;

import restaurant.api.Menu;

@SuppressWarnings("serial")
public class MenuImpl implements Menu
{
	private int		id;
	private String	details;

	MenuImpl(int idC, String detailsC)
	{
		this.id = idC;
		this.details = detailsC;
	}

	public String printMenu()
	{
		return this.details;
	}

	int getId()
	{
		return this.id;
	}
}
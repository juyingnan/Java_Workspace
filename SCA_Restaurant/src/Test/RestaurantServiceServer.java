package Test;

import java.io.IOException;

import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;

import restaurant.api.RestaurantService;

public class RestaurantServiceServer
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		System.out.println("Starting of the SCA Restaurant Application exposed as RMI Services...");
		String location = ContributionLocationHelper.getContributionLocation(RestaurantService.class);
		System.out.println("Location : " + location);
		Node node = NodeFactory.newInstance("Restaurant.composite").createNode(new Contribution("default", location));
		node.start();
		System.out.println(node.DEFAULT_DOMAIN_URI + " " + node.DEFAULT_NODE_URI);
		System.out.println("... Press Enter to Exit...");
		System.in.read();
		node.stop();
		System.out.println("Exited...");
		System.exit(0);
	}
}

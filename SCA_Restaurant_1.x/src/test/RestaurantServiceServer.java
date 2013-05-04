package test;

import org.apache.tuscany.sca.host.embedded.SCADomain;

/**
 * A restaurant service server. Starts up the SCA runtime which will start
 * listening for RMI service requests.
 */
public class RestaurantServiceServer
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("Starting of the SCA Restaurant Application exposed as RMI Services...");
		SCADomain scaDomain = SCADomain.newInstance("Restaurant.composite");
		System.out.println("... Press Enter to Exit...");
		System.in.read();
		scaDomain.close();
		System.out.println("Exited...");
		System.exit(0);
	}
}
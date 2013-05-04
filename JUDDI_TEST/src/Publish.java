import java.io.IOException;
import junit.framework.Assert;
import org.apache.juddi.v3.client.config.UDDIClerkManager;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.BusinessServices;
import org.uddi.api_v3.Description;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.OverviewDoc;
import org.uddi.api_v3.OverviewURL;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveTModel;
import org.uddi.api_v3.TModel;
import org.uddi.api_v3.TModelDetail;
import org.uddi.api_v3.TModelInstanceDetails;
import org.uddi.api_v3.TModelInstanceInfo;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

public class Publish
{

	public void publish(String businessname, String bdescription, String wsdladdress) throws IOException
	{
		String clazz;
		String authinfo;
		String information = null;
		try
		{
			clazz = UDDIClerkManager.getClientConfig().getUDDINode("default").getProxyTransport();
			Class<?> transportClass = Class.forName(clazz, true, Thread.currentThread().getContextClassLoader());
			if (transportClass != null)
			{
				AnalyWsdl ana = new AnalyWsdl();
				information = ana.readWsdl(wsdladdress);
				String[] section = information.split("\\|");
				// 将AnalyWsdl返回的结果进行分割，从而分离出需要注册到uddi上的信息
				Transport transport = (Transport) transportClass.newInstance();
				UDDISecurityPortType securityService = transport.getUDDISecurityService();
				GetAuthToken getauthToken = new GetAuthToken();
				getauthToken.setUserID("root");
				getauthToken.setCred("root");
				AuthToken authtoken = securityService.getAuthToken(getauthToken);
				authinfo = authtoken.getAuthInfo();
				System.out.println("获得AuthToken");
				System.out.println(authinfo);
				UDDIPublicationPortType publication = transport.getUDDIPublishService();
				// 添加BusinessEntity
				SaveBusiness sb = new SaveBusiness();
				BusinessEntity be = new BusinessEntity();
				Name name = new Name();
				name.setValue(businessname);
				be.getName().add(name);
				Description description = new Description();
				description.setValue(bdescription);
				be.getDescription().add(description);
				// 添加tModel
				SaveTModel ST = new SaveTModel();
				ST.setAuthInfo(authinfo);
				TModel tm = new TModel();
				Name name1 = new Name();
				System.out.println("tmodel name:" + section[0]);
				name1.setValue(section[0]);
				tm.setName(name1);
				if (!(section[1].equals("null")))
				{
					Description description1 = new Description();
					description1.setValue(section[1]);
					System.out.println("tmodel description:" + section[1]);
					tm.getDescription().add(description1);
				}
				OverviewDoc od = new OverviewDoc();
				OverviewURL ou = new OverviewURL();
				ou.setValue(section[2]);
				System.out.println("overviewURL:" + section[2]);

				od.setOverviewURL(ou);
				tm.getOverviewDoc().add(od);
				ST.getTModel().add(tm);
				TModelDetail Tt = publication.saveTModel(ST);
				// 添加BusinessService
				BusinessServices bss = new BusinessServices();
				BusinessService bs = new BusinessService();
				Name name2 = new Name();
				name2.setValue(section[3]);
				System.out.println("service name:" + section[3]);

				bs.getName().add(name2);
				if (!(section[4].equals("null")))
				{
					Description description2 = new Description();
					description2.setValue(section[4]);
					System.out.println("service description:" + section[4]);

					bs.getDescription().add(description2);
				}
				BindingTemplate bt = new BindingTemplate();
				BindingTemplates bts = new BindingTemplates();
				AccessPoint ap = new AccessPoint();
				ap.setUseType("wsdlDeployment");
				ap.setValue(section[5]);
				System.out.println("accesspoint:" + section[5]);

				bt.setAccessPoint(ap);
				if (!(section[6].equals("null")))
				{
					Description description3 = new Description();
					description3.setValue(section[6]);
					System.out.println("binding description:" + section[6]);

					bt.getDescription().add(description3);
				}
				TModelInstanceDetails TD = new TModelInstanceDetails();
				TModelInstanceInfo TI = new TModelInstanceInfo();
				TI.setTModelKey(Tt.getTModel().get(0).getTModelKey());
				TD.getTModelInstanceInfo().add(TI);
				bt.setTModelInstanceDetails(TD);
				bts.getBindingTemplate().add(bt);
				bs.setBindingTemplates(bts);
				bss.getBusinessService().add(bs);
				be.setBusinessServices(bss);
				sb.setAuthInfo(authinfo);
				sb.getBusinessEntity().add(be);
				publication.saveBusiness(sb);
				System.out.println("服务注册成功！！");

			}
			else
			{
				Assert.fail();

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Assert.fail("Could not obtain authInfo token.");
		}

		// out.println("</HTML></BODY>");

	}

	public static void main(String[] args) throws IOException
	{
		Publish pub = new Publish();
		pub.publish("WebServicesTest_AXIS2", "This is a Web Services Test and the server is AXIS2", "http://192.168.213.172/StuInfoWebService.asmx?WSDL");

	}

}
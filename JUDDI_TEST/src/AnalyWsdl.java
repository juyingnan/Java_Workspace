import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.wsdl.xml.WSDLWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AnalyWsdl
{
	public String readWsdl(String wsdl)
	{
		String result = null;
		try
		{
			String targetNamespace = "";
			String serviceName = "";
			String sdescription = "";
			String location = "";
			String bindingdescription = "";
			String tdescription = "";
			String bindingname = "";
			String overviewURL = "";
			String address = "";

			WSDLFactory factory = WSDLFactory.newInstance();
			WSDLReader reader = factory.newWSDLReader();
			reader.setFeature("javax.wsdl.verbose", false);
			reader.setFeature("javax.wsdl.importDocuments", true);
			Definition def = reader.readWSDL(wsdl);
			targetNamespace = def.getTargetNamespace(); // 获取definitions中的targetNamespace，作为tmodel
														// name

			// System.out.println(targetNamespace);
//			if (def.getDocumentationElement() != null)
//				tdescription = def.getDocumentationElement().getTextContent();// 获取definitions中的documentation，作为tmodel
//																				// description
//			else
//				tdescription = def.getQName().getLocalPart();// definitions中的documentation不存在，将name作为tmodel
//																// description

			// System.out.println(tdescription);
			WSDLWriter writer = factory.newWSDLWriter();
			Document doc = writer.getDocument(def);
			Element elem = doc.getDocumentElement();
			NodeList bindingList = elem.getElementsByTagName("wsdl:binding");
			Element binding = (Element) bindingList.item(0);
			bindingname = binding.getAttribute("name");
			// System.out.println(bindingname);
			NodeList list = elem.getElementsByTagName("wsdl:service");
			Element service = (Element) list.item(0);
			serviceName = service.getAttribute("name"); // 获取service name
			// System.out.println(serviceName);
			NodeList serviceList = service.getChildNodes();
			sdescription = "null";
			for (int i = 0; i < serviceList.getLength(); i++)
			{
				Node childNode = serviceList.item(i);
				if (childNode instanceof Element && (childNode.getNodeName().equals("wsdl:documentation")))
				{
					sdescription = childNode.getTextContent(); // 获取service下的documentation，作为service
																// description
				}

			}
			// System.out.println(sdescription);
			NodeList wsdlAddressList = elem.getElementsByTagName("soap:address");
			NodeList wsdlAddressList1 = elem.getElementsByTagName("wsdlsoap:address");
			if (wsdlAddressList.getLength() == 1)
			{
				Element wsdlAddress = (Element) wsdlAddressList.item(0);
				address = wsdlAddress.getAttribute("location");
				//
				if(address.endsWith("Endpoint/"))
				{
					address=address.substring(0, address.lastIndexOf("."));
					//String[] strings= address.split("\\.");
					//address=strings[0];
				}
				//
				location = address + "?wsdl";

			}
			if (wsdlAddressList1.getLength() == 1)
			{
				Element wsdlAddress1 = (Element) wsdlAddressList.item(0);
				address = wsdlAddress1.getAttribute("location");
				//
				if(address.endsWith("Endpoint/"))
				{
					address=address.substring(0, address.lastIndexOf("."));
					//String[] strings= address.split("\\.");
					//address=strings[0];
				}
				//
				location = address + "?wsdl"; // 获取location,作为accesspoint
			}
			// System.out.println(location);
			NodeList portList = elem.getElementsByTagName("wsdl:port");
			Element portType = (Element) portList.item(0);
			NodeList port = portType.getChildNodes();
			bindingdescription = "null";
			for (int j = 0; j < port.getLength(); j++)
			{
				Node childNode = port.item(j);
				if (childNode instanceof Element && (childNode.getNodeName().equals("wsdl:documentation")))
				{
					bindingdescription = childNode.getTextContent(); // 获取service_port的documentation，作为bindingTemplate
																		// description

				}

			}
			// System.out.println(bindingdescription);
			overviewURL = address + "#" + bindingname;
			// System.out.println(overviewURL);

			result = targetNamespace + "|" + tdescription + "|" + overviewURL + "|" + serviceName + "|" + sdescription
					+ "|" + location + "|" + bindingdescription;

		}
		catch (WSDLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

}
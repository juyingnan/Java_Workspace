//import com.inquiry.AnalyticWsdl;  
import junit.framework.Assert;  
  
import org.apache.juddi.v3.client.config.ClientConfig;  
import org.apache.juddi.v3.client.config.UDDIClerkManager;  
import org.apache.juddi.v3.client.transport.Transport;  
import org.apache.log4j.helpers.Loader;  
import org.uddi.v3_service.*;  
import org.uddi.api_v3.*;  
public class Inquiry {  
   
    public void inquiry()  
    {  
        try   
          {       
            String clazz=UDDIClerkManager.getClientConfig().getUDDINode("default").getProxyTransport();  
            Class<?> transportClass = Loader.loadClass(clazz);  
            /* 
                String clazz=UDDIClerkManager.getClientConfig().getUDDINode("default").getProxyTransport(); 
                Class<?> transportClass=Class.forName(clazz, true, Thread.currentThread().getContextClassLoader()); 
              */  
              if(transportClass!=null)  
              {  
               //���authtoken  
               Transport transport=(Transport)transportClass.newInstance();  
               UDDISecurityPortType securityService=transport.getUDDISecurityService();  
               GetAuthToken getauthToken=new GetAuthToken();  
               getauthToken.setUserID("root");    //�û���  
               getauthToken.setCred("root");      //����  
               AuthToken authtoken=securityService.getAuthToken(getauthToken);  
               String authinfo=authtoken.getAuthInfo();  
               System.out.println(authinfo);        //�����ȡ��AuthToken                 
               System.out.println("-----------------��ѯ���з���-----------------") ;      
      
               //���businessinfo  
               UDDIInquiryPortType inquiryService=transport.getUDDIInquiryService();  
               FindBusiness findbusiness=new FindBusiness();  
               findbusiness.setAuthInfo(authinfo);  
               Name name1=new Name();  
               name1.setValue("%");        //�������з������%               
               FindQualifiers qualifiers=new FindQualifiers();  
               qualifiers.getFindQualifier().add(org.apache.juddi.query.util.FindQualifiers.APPROXIMATE_MATCH); //����APPROXIMATE_MATCH���й���  
               findbusiness.getName().add(name1);  
               findbusiness.setFindQualifiers(qualifiers);  
              // BusinessList list=inquiryService.findBusiness(findbusiness);  
                 
               //���serviceinfo  
               FindService findservice=new FindService();  
               findservice.setAuthInfo(authinfo);  
               findservice.getName().add(name1);  
               findservice.setFindQualifiers(qualifiers);  
               ServiceList list1=inquiryService.findService(findservice);  
               GetServiceDetail gsd=new GetServiceDetail();  
                for(ServiceInfo serviceInfo :list1.getServiceInfos().getServiceInfo()){  
                    gsd.getServiceKey().add(serviceInfo.getServiceKey());  
                    System.out.println("--serviceKey:"+serviceInfo.getServiceKey());     //�����ȡ��serviceKey  
                    String servicekey=serviceInfo.getServiceKey();  
                      
                      
                    //���businessservice  
                    GetServiceDetail getServiceDetail=new GetServiceDetail();  
                    getServiceDetail.setAuthInfo(authinfo);  
                    getServiceDetail.getServiceKey().add(servicekey);  
                    ServiceDetail serviceDetail=inquiryService.getServiceDetail(getServiceDetail);  
                    BusinessService businessservice=serviceDetail.getBusinessService().get(0);  
                    System.out.println("**serviceName:"+businessservice.getName().get(0).getValue()); //���serviceName  
                    String bindingkey = businessservice.getBindingTemplates().getBindingTemplate().get(0).getBindingKey();  
                    System.out.println("**bindingKey"+bindingkey);  
  
  
                    //���bindingtemplate�е�accesspoint  
                    GetBindingDetail gbd = new GetBindingDetail();  
                    gbd.setAuthInfo(authinfo);  
                    gbd.getBindingKey().add(bindingkey);  
                    BindingDetail bindingdetail=inquiryService.getBindingDetail(gbd);  
                    BindingTemplate bindingtemplate=bindingdetail.getBindingTemplate().get(0);  
                    String accesspoint=bindingtemplate.getAccessPoint().getValue();  
                    System.out.println("**accessPoint:"+accesspoint+"\n");   //���accessPoint  
                          
                    }  
                      
  
                }  
                  
                System.out.println("��ѯ�ɹ�������");  
    }catch(Exception e)  
          {  
           e.printStackTrace();  
           Assert.fail("Could not obtain authInfo token.");}  
    }  
    public static void main(String[] args){  
        Inquiry inq=new Inquiry();  
        inq.inquiry();  
          
    }   
    } 
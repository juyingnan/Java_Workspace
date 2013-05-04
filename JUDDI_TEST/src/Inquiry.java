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
               //获得authtoken  
               Transport transport=(Transport)transportClass.newInstance();  
               UDDISecurityPortType securityService=transport.getUDDISecurityService();  
               GetAuthToken getauthToken=new GetAuthToken();  
               getauthToken.setUserID("root");    //用户名  
               getauthToken.setCred("root");      //密码  
               AuthToken authtoken=securityService.getAuthToken(getauthToken);  
               String authinfo=authtoken.getAuthInfo();  
               System.out.println(authinfo);        //输出获取的AuthToken                 
               System.out.println("-----------------查询所有服务-----------------") ;      
      
               //获得businessinfo  
               UDDIInquiryPortType inquiryService=transport.getUDDIInquiryService();  
               FindBusiness findbusiness=new FindBusiness();  
               findbusiness.setAuthInfo(authinfo);  
               Name name1=new Name();  
               name1.setValue("%");        //查找所有服务采用%               
               FindQualifiers qualifiers=new FindQualifiers();  
               qualifiers.getFindQualifier().add(org.apache.juddi.query.util.FindQualifiers.APPROXIMATE_MATCH); //按照APPROXIMATE_MATCH进行过滤  
               findbusiness.getName().add(name1);  
               findbusiness.setFindQualifiers(qualifiers);  
              // BusinessList list=inquiryService.findBusiness(findbusiness);  
                 
               //获得serviceinfo  
               FindService findservice=new FindService();  
               findservice.setAuthInfo(authinfo);  
               findservice.getName().add(name1);  
               findservice.setFindQualifiers(qualifiers);  
               ServiceList list1=inquiryService.findService(findservice);  
               GetServiceDetail gsd=new GetServiceDetail();  
                for(ServiceInfo serviceInfo :list1.getServiceInfos().getServiceInfo()){  
                    gsd.getServiceKey().add(serviceInfo.getServiceKey());  
                    System.out.println("--serviceKey:"+serviceInfo.getServiceKey());     //输出获取的serviceKey  
                    String servicekey=serviceInfo.getServiceKey();  
                      
                      
                    //获得businessservice  
                    GetServiceDetail getServiceDetail=new GetServiceDetail();  
                    getServiceDetail.setAuthInfo(authinfo);  
                    getServiceDetail.getServiceKey().add(servicekey);  
                    ServiceDetail serviceDetail=inquiryService.getServiceDetail(getServiceDetail);  
                    BusinessService businessservice=serviceDetail.getBusinessService().get(0);  
                    System.out.println("**serviceName:"+businessservice.getName().get(0).getValue()); //输出serviceName  
                    String bindingkey = businessservice.getBindingTemplates().getBindingTemplate().get(0).getBindingKey();  
                    System.out.println("**bindingKey"+bindingkey);  
  
  
                    //获得bindingtemplate中的accesspoint  
                    GetBindingDetail gbd = new GetBindingDetail();  
                    gbd.setAuthInfo(authinfo);  
                    gbd.getBindingKey().add(bindingkey);  
                    BindingDetail bindingdetail=inquiryService.getBindingDetail(gbd);  
                    BindingTemplate bindingtemplate=bindingdetail.getBindingTemplate().get(0);  
                    String accesspoint=bindingtemplate.getAccessPoint().getValue();  
                    System.out.println("**accessPoint:"+accesspoint+"\n");   //输出accessPoint  
                          
                    }  
                      
  
                }  
                  
                System.out.println("查询成功！！！");  
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
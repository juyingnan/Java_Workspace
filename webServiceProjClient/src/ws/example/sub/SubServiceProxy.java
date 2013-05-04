package ws.example.sub;

public class SubServiceProxy implements ws.example.sub.SubService {
  private String _endpoint = null;
  private ws.example.sub.SubService subService = null;
  
  public SubServiceProxy() {
    _initSubServiceProxy();
  }
  
  public SubServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initSubServiceProxy();
  }
  
  private void _initSubServiceProxy() {
    try {
      subService = (new ws.example.sub.SubServiceServiceLocator()).getSubService();
      if (subService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)subService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)subService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (subService != null)
      ((javax.xml.rpc.Stub)subService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ws.example.sub.SubService getSubService() {
    if (subService == null)
      _initSubServiceProxy();
    return subService;
  }
  
  public double sub(double a1, double a2) throws java.rmi.RemoteException{
    if (subService == null)
      _initSubServiceProxy();
    return subService.sub(a1, a2);
  }
  
  
}
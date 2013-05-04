package ws.example.add;

public class AddServiceProxy implements ws.example.add.AddService {
  private String _endpoint = null;
  private ws.example.add.AddService addService = null;
  
  public AddServiceProxy() {
    _initAddServiceProxy();
  }
  
  public AddServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initAddServiceProxy();
  }
  
  private void _initAddServiceProxy() {
    try {
      addService = (new ws.example.add.AddServiceServiceLocator()).getAddService();
      if (addService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)addService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)addService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (addService != null)
      ((javax.xml.rpc.Stub)addService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ws.example.add.AddService getAddService() {
    if (addService == null)
      _initAddServiceProxy();
    return addService;
  }
  
  public double add(double a1, double a2) throws java.rmi.RemoteException{
    if (addService == null)
      _initAddServiceProxy();
    return addService.add(a1, a2);
  }
  
  
}
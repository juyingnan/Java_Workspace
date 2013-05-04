/**
 * AddServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.example.add;

public class AddServiceServiceLocator extends org.apache.axis.client.Service implements ws.example.add.AddServiceService {

    public AddServiceServiceLocator() {
    }


    public AddServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AddServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AddService
    private java.lang.String AddService_address = "http://localhost:8080/webServiceProj/services/AddService";

    public java.lang.String getAddServiceAddress() {
        return AddService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AddServiceWSDDServiceName = "AddService";

    public java.lang.String getAddServiceWSDDServiceName() {
        return AddServiceWSDDServiceName;
    }

    public void setAddServiceWSDDServiceName(java.lang.String name) {
        AddServiceWSDDServiceName = name;
    }

    public ws.example.add.AddService getAddService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AddService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAddService(endpoint);
    }

    public ws.example.add.AddService getAddService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.example.add.AddServiceSoapBindingStub _stub = new ws.example.add.AddServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getAddServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAddServiceEndpointAddress(java.lang.String address) {
        AddService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ws.example.add.AddService.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.example.add.AddServiceSoapBindingStub _stub = new ws.example.add.AddServiceSoapBindingStub(new java.net.URL(AddService_address), this);
                _stub.setPortName(getAddServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("AddService".equals(inputPortName)) {
            return getAddService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://add.example.ws", "AddServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://add.example.ws", "AddService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AddService".equals(portName)) {
            setAddServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

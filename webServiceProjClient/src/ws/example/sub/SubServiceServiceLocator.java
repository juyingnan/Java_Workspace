/**
 * SubServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.example.sub;

public class SubServiceServiceLocator extends org.apache.axis.client.Service implements ws.example.sub.SubServiceService {

    public SubServiceServiceLocator() {
    }


    public SubServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SubServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SubService
    private java.lang.String SubService_address = "http://localhost:8080/webServiceProj/services/SubService";

    public java.lang.String getSubServiceAddress() {
        return SubService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SubServiceWSDDServiceName = "SubService";

    public java.lang.String getSubServiceWSDDServiceName() {
        return SubServiceWSDDServiceName;
    }

    public void setSubServiceWSDDServiceName(java.lang.String name) {
        SubServiceWSDDServiceName = name;
    }

    public ws.example.sub.SubService getSubService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SubService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSubService(endpoint);
    }

    public ws.example.sub.SubService getSubService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.example.sub.SubServiceSoapBindingStub _stub = new ws.example.sub.SubServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getSubServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSubServiceEndpointAddress(java.lang.String address) {
        SubService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ws.example.sub.SubService.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.example.sub.SubServiceSoapBindingStub _stub = new ws.example.sub.SubServiceSoapBindingStub(new java.net.URL(SubService_address), this);
                _stub.setPortName(getSubServiceWSDDServiceName());
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
        if ("SubService".equals(inputPortName)) {
            return getSubService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sub.example.ws", "SubServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sub.example.ws", "SubService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SubService".equals(portName)) {
            setSubServiceEndpointAddress(address);
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

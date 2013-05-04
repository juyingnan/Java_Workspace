/**
 * AddServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.example.add;

public interface AddServiceService extends javax.xml.rpc.Service {
    public java.lang.String getAddServiceAddress();

    public ws.example.add.AddService getAddService() throws javax.xml.rpc.ServiceException;

    public ws.example.add.AddService getAddService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

/**
 * SDKService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.common;

public interface SDKService extends javax.xml.rpc.Service {
    public java.lang.String getSDKServiceAddress();

    public com.common.SDKClient getSDKService() throws javax.xml.rpc.ServiceException;

    public com.common.SDKClient getSDKService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

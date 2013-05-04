

/**
 * StuInfoWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package org.tempuri;

    /*
     *  StuInfoWebService java interface
     */

    public interface StuInfoWebService {
          

        /**
          * Auto generated method signature
          * 
                    * @param getStudentName0
                
         */

         
                     public org.tempuri.GetStudentNameResponse getStudentName(

                        org.tempuri.GetStudentName getStudentName0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getStudentName0
            
          */
        public void startgetStudentName(

            org.tempuri.GetStudentName getStudentName0,

            final org.tempuri.StuInfoWebServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param insertStudentInfo2
                
         */

         
                     public org.tempuri.InsertStudentInfoResponse insertStudentInfo(

                        org.tempuri.InsertStudentInfo insertStudentInfo2)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param insertStudentInfo2
            
          */
        public void startinsertStudentInfo(

            org.tempuri.InsertStudentInfo insertStudentInfo2,

            final org.tempuri.StuInfoWebServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getStudentMajor4
                
         */

         
                     public org.tempuri.GetStudentMajorResponse getStudentMajor(

                        org.tempuri.GetStudentMajor getStudentMajor4)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getStudentMajor4
            
          */
        public void startgetStudentMajor(

            org.tempuri.GetStudentMajor getStudentMajor4,

            final org.tempuri.StuInfoWebServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getStudentGender6
                
         */

         
                     public org.tempuri.GetStudentGenderResponse getStudentGender(

                        org.tempuri.GetStudentGender getStudentGender6)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getStudentGender6
            
          */
        public void startgetStudentGender(

            org.tempuri.GetStudentGender getStudentGender6,

            final org.tempuri.StuInfoWebServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param deleteStudentInfo8
                
         */

         
                     public org.tempuri.DeleteStudentInfoResponse deleteStudentInfo(

                        org.tempuri.DeleteStudentInfo deleteStudentInfo8)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param deleteStudentInfo8
            
          */
        public void startdeleteStudentInfo(

            org.tempuri.DeleteStudentInfo deleteStudentInfo8,

            final org.tempuri.StuInfoWebServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getStudentEmail10
                
         */

         
                     public org.tempuri.GetStudentEmailResponse getStudentEmail(

                        org.tempuri.GetStudentEmail getStudentEmail10)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getStudentEmail10
            
          */
        public void startgetStudentEmail(

            org.tempuri.GetStudentEmail getStudentEmail10,

            final org.tempuri.StuInfoWebServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param updateStudentInfo12
                
         */

         
                     public org.tempuri.UpdateStudentInfoResponse updateStudentInfo(

                        org.tempuri.UpdateStudentInfo updateStudentInfo12)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param updateStudentInfo12
            
          */
        public void startupdateStudentInfo(

            org.tempuri.UpdateStudentInfo updateStudentInfo12,

            final org.tempuri.StuInfoWebServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
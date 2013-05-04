/**
 * StuInfoWebServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package org.tempuri;

/**
 * StuInfoWebServiceCallbackHandler Callback class, Users can extend this class
 * and implement their own receiveResult and receiveError methods.
 */
public abstract class StuInfoWebServiceCallbackHandler
{

	protected Object	clientData;

	/**
	 * User can pass in any object that needs to be accessed once the
	 * NonBlocking Web service call is finished and appropriate method of this
	 * CallBack is called.
	 * 
	 * @param clientData
	 *            Object mechanism by which the user can pass in user data that
	 *            will be avilable at the time this callback is called.
	 */
	public StuInfoWebServiceCallbackHandler(Object clientData)
	{
		this.clientData = clientData;
	}

	/**
	 * Please use this constructor if you don't want to set any clientData
	 */
	public StuInfoWebServiceCallbackHandler()
	{
		this.clientData = null;
	}

	/**
	 * Get the client data
	 */

	public Object getClientData()
	{
		return clientData;
	}

	/**
	 * auto generated Axis2 call back method for getStudentName method override
	 * this method for handling normal response from getStudentName operation
	 */
	public void receiveResultgetStudentName(org.tempuri.GetStudentNameResponse result)
	{
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getStudentName operation
	 */
	public void receiveErrorgetStudentName(java.lang.Exception e)
	{
	}

	/**
	 * auto generated Axis2 call back method for insertStudentInfo method
	 * override this method for handling normal response from insertStudentInfo
	 * operation
	 */
	public void receiveResultinsertStudentInfo(org.tempuri.InsertStudentInfoResponse result)
	{
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from insertStudentInfo operation
	 */
	public void receiveErrorinsertStudentInfo(java.lang.Exception e)
	{
	}

	/**
	 * auto generated Axis2 call back method for getStudentMajor method override
	 * this method for handling normal response from getStudentMajor operation
	 */
	public void receiveResultgetStudentMajor(org.tempuri.GetStudentMajorResponse result)
	{
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getStudentMajor operation
	 */
	public void receiveErrorgetStudentMajor(java.lang.Exception e)
	{
	}

	/**
	 * auto generated Axis2 call back method for getStudentGender method
	 * override this method for handling normal response from getStudentGender
	 * operation
	 */
	public void receiveResultgetStudentGender(org.tempuri.GetStudentGenderResponse result)
	{
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getStudentGender operation
	 */
	public void receiveErrorgetStudentGender(java.lang.Exception e)
	{
	}

	/**
	 * auto generated Axis2 call back method for deleteStudentInfo method
	 * override this method for handling normal response from deleteStudentInfo
	 * operation
	 */
	public void receiveResultdeleteStudentInfo(org.tempuri.DeleteStudentInfoResponse result)
	{
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from deleteStudentInfo operation
	 */
	public void receiveErrordeleteStudentInfo(java.lang.Exception e)
	{
	}

	/**
	 * auto generated Axis2 call back method for getStudentEmail method override
	 * this method for handling normal response from getStudentEmail operation
	 */
	public void receiveResultgetStudentEmail(org.tempuri.GetStudentEmailResponse result)
	{
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getStudentEmail operation
	 */
	public void receiveErrorgetStudentEmail(java.lang.Exception e)
	{
	}

	/**
	 * auto generated Axis2 call back method for updateStudentInfo method
	 * override this method for handling normal response from updateStudentInfo
	 * operation
	 */
	public void receiveResultupdateStudentInfo(org.tempuri.UpdateStudentInfoResponse result)
	{
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from updateStudentInfo operation
	 */
	public void receiveErrorupdateStudentInfo(java.lang.Exception e)
	{
	}

}

/**
 * StuInfoWebServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package org.tempuri;

/*
 *  StuInfoWebServiceStub java implementation
 */

public class StuInfoWebServiceStub extends org.apache.axis2.client.Stub implements StuInfoWebService
{
	protected org.apache.axis2.description.AxisOperation[]	_operations;

	// hashmaps to keep the fault mapping
	private java.util.HashMap								faultExceptionNameMap		= new java.util.HashMap();
	private java.util.HashMap								faultExceptionClassNameMap	= new java.util.HashMap();
	private java.util.HashMap								faultMessageMap				= new java.util.HashMap();

	private static int										counter						= 0;

	private static synchronized java.lang.String getUniqueSuffix()
	{
		// reset the counter if it is greater than 99999
		if (counter > 99999)
		{
			counter = 0;
		}
		counter = counter + 1;
		return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
	}

	private void populateAxisService() throws org.apache.axis2.AxisFault
	{

		// creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService("StuInfoWebService" + getUniqueSuffix());
		addAnonymousOperations();

		// creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[7];

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentName"));
		_service.addOperation(__operation);

		_operations[0] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "insertStudentInfo"));
		_service.addOperation(__operation);

		_operations[1] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentMajor"));
		_service.addOperation(__operation);

		_operations[2] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentGender"));
		_service.addOperation(__operation);

		_operations[3] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "deleteStudentInfo"));
		_service.addOperation(__operation);

		_operations[4] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentEmail"));
		_service.addOperation(__operation);

		_operations[5] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "updateStudentInfo"));
		_service.addOperation(__operation);

		_operations[6] = __operation;

	}

	// populates the faults
	private void populateFaults()
	{

	}

	/**
	 * Constructor that takes in a configContext
	 */

	public StuInfoWebServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault
	{
		this(configurationContext, targetEndpoint, false);
	}

	/**
	 * Constructor that takes in a configContext and useseperate listner
	 */
	public StuInfoWebServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint, boolean useSeparateListener) throws org.apache.axis2.AxisFault
	{
		// To populate AxisService
		populateAxisService();
		populateFaults();

		_serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext, _service);

		_serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(targetEndpoint));
		_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

		// Set the soap version
		_serviceClient.getOptions()
				.setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);

	}

	/**
	 * Default Constructor
	 */
	public StuInfoWebServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext)
			throws org.apache.axis2.AxisFault
	{

		this(configurationContext, "http://localhost:60519/StuInfoWebService.asmx");

	}

	/**
	 * Default Constructor
	 */
	public StuInfoWebServiceStub() throws org.apache.axis2.AxisFault
	{

		this("http://localhost:60519/StuInfoWebService.asmx");

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public StuInfoWebServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault
	{
		this(null, targetEndpoint);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.tempuri.StuInfoWebService#getStudentName
	 * @param getStudentName14
	 */

	public org.tempuri.GetStudentNameResponse getStudentName(

	org.tempuri.GetStudentName getStudentName14)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0]
					.getName());
			_operationClient.getOptions().setAction("http://tempuri.org/GetStudentName");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentName14,
					optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentName")),
					new javax.xml.namespace.QName("http://tempuri.org/", "getStudentName"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					org.tempuri.GetStudentNameResponse.class, getEnvelopeNamespaces(_returnEnv));

			return (org.tempuri.GetStudentNameResponse) object;

		}
		catch (org.apache.axis2.AxisFault f)
		{

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null)
			{
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
						"GetStudentName")))
				{
					// make the fault by reflection
					try
					{
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentName"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentName"));
						java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					}
					catch (java.lang.ClassCastException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.ClassNotFoundException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.NoSuchMethodException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.reflect.InvocationTargetException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.IllegalAccessException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.InstantiationException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else
				{
					throw f;
				}
			}
			else
			{
				throw f;
			}
		}
		finally
		{
			if (_messageContext.getTransportOut() != null)
			{
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.tempuri.StuInfoWebService#startgetStudentName
	 * @param getStudentName14
	 */
	public void startgetStudentName(

	org.tempuri.GetStudentName getStudentName14,

	final org.tempuri.StuInfoWebServiceCallbackHandler callback)

	throws java.rmi.RemoteException
	{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("http://tempuri.org/GetStudentName");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentName14,
				optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentName")),
				new javax.xml.namespace.QName("http://tempuri.org/", "getStudentName"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback()
		{
			public void onMessage(org.apache.axis2.context.MessageContext resultContext)
			{
				try
				{
					org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

					java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
							org.tempuri.GetStudentNameResponse.class, getEnvelopeNamespaces(resultEnv));
					callback.receiveResultgetStudentName((org.tempuri.GetStudentNameResponse) object);

				}
				catch (org.apache.axis2.AxisFault e)
				{
					callback.receiveErrorgetStudentName(e);
				}
			}

			public void onError(java.lang.Exception error)
			{
				if (error instanceof org.apache.axis2.AxisFault)
				{
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt != null)
					{
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt
								.getQName(), "GetStudentName")))
						{
							// make the fault by reflection
							try
							{
								java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentName"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								// message class
								java.lang.String messageClassName = (java.lang.String) faultMessageMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentName"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[] { messageClass });
								m.invoke(ex, new java.lang.Object[] { messageObject });

								callback.receiveErrorgetStudentName(new java.rmi.RemoteException(ex.getMessage(), ex));
							}
							catch (java.lang.ClassCastException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentName(f);
							}
							catch (java.lang.ClassNotFoundException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentName(f);
							}
							catch (java.lang.NoSuchMethodException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentName(f);
							}
							catch (java.lang.reflect.InvocationTargetException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentName(f);
							}
							catch (java.lang.IllegalAccessException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentName(f);
							}
							catch (java.lang.InstantiationException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentName(f);
							}
							catch (org.apache.axis2.AxisFault e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentName(f);
							}
						}
						else
						{
							callback.receiveErrorgetStudentName(f);
						}
					}
					else
					{
						callback.receiveErrorgetStudentName(f);
					}
				}
				else
				{
					callback.receiveErrorgetStudentName(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext)
			{
				org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
						.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete()
			{
				try
				{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch (org.apache.axis2.AxisFault axisFault)
				{
					callback.receiveErrorgetStudentName(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[0].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener())
		{
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[0].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.tempuri.StuInfoWebService#insertStudentInfo
	 * @param insertStudentInfo16
	 */

	public org.tempuri.InsertStudentInfoResponse insertStudentInfo(

	org.tempuri.InsertStudentInfo insertStudentInfo16)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1]
					.getName());
			_operationClient.getOptions().setAction("http://tempuri.org/InsertStudentInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), insertStudentInfo16,
					optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "insertStudentInfo")),
					new javax.xml.namespace.QName("http://tempuri.org/", "insertStudentInfo"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					org.tempuri.InsertStudentInfoResponse.class, getEnvelopeNamespaces(_returnEnv));

			return (org.tempuri.InsertStudentInfoResponse) object;

		}
		catch (org.apache.axis2.AxisFault f)
		{

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null)
			{
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
						"InsertStudentInfo")))
				{
					// make the fault by reflection
					try
					{
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "InsertStudentInfo"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "InsertStudentInfo"));
						java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					}
					catch (java.lang.ClassCastException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.ClassNotFoundException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.NoSuchMethodException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.reflect.InvocationTargetException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.IllegalAccessException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.InstantiationException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else
				{
					throw f;
				}
			}
			else
			{
				throw f;
			}
		}
		finally
		{
			if (_messageContext.getTransportOut() != null)
			{
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.tempuri.StuInfoWebService#startinsertStudentInfo
	 * @param insertStudentInfo16
	 */
	public void startinsertStudentInfo(

	org.tempuri.InsertStudentInfo insertStudentInfo16,

	final org.tempuri.StuInfoWebServiceCallbackHandler callback)

	throws java.rmi.RemoteException
	{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[1].getName());
		_operationClient.getOptions().setAction("http://tempuri.org/InsertStudentInfo");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), insertStudentInfo16,
				optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "insertStudentInfo")),
				new javax.xml.namespace.QName("http://tempuri.org/", "insertStudentInfo"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback()
		{
			public void onMessage(org.apache.axis2.context.MessageContext resultContext)
			{
				try
				{
					org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

					java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
							org.tempuri.InsertStudentInfoResponse.class, getEnvelopeNamespaces(resultEnv));
					callback.receiveResultinsertStudentInfo((org.tempuri.InsertStudentInfoResponse) object);

				}
				catch (org.apache.axis2.AxisFault e)
				{
					callback.receiveErrorinsertStudentInfo(e);
				}
			}

			public void onError(java.lang.Exception error)
			{
				if (error instanceof org.apache.axis2.AxisFault)
				{
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt != null)
					{
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt
								.getQName(), "InsertStudentInfo")))
						{
							// make the fault by reflection
							try
							{
								java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"InsertStudentInfo"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								// message class
								java.lang.String messageClassName = (java.lang.String) faultMessageMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"InsertStudentInfo"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[] { messageClass });
								m.invoke(ex, new java.lang.Object[] { messageObject });

								callback.receiveErrorinsertStudentInfo(new java.rmi.RemoteException(ex.getMessage(), ex));
							}
							catch (java.lang.ClassCastException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorinsertStudentInfo(f);
							}
							catch (java.lang.ClassNotFoundException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorinsertStudentInfo(f);
							}
							catch (java.lang.NoSuchMethodException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorinsertStudentInfo(f);
							}
							catch (java.lang.reflect.InvocationTargetException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorinsertStudentInfo(f);
							}
							catch (java.lang.IllegalAccessException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorinsertStudentInfo(f);
							}
							catch (java.lang.InstantiationException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorinsertStudentInfo(f);
							}
							catch (org.apache.axis2.AxisFault e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorinsertStudentInfo(f);
							}
						}
						else
						{
							callback.receiveErrorinsertStudentInfo(f);
						}
					}
					else
					{
						callback.receiveErrorinsertStudentInfo(f);
					}
				}
				else
				{
					callback.receiveErrorinsertStudentInfo(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext)
			{
				org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
						.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete()
			{
				try
				{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch (org.apache.axis2.AxisFault axisFault)
				{
					callback.receiveErrorinsertStudentInfo(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[1].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener())
		{
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[1].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.tempuri.StuInfoWebService#getStudentMajor
	 * @param getStudentMajor18
	 */

	public org.tempuri.GetStudentMajorResponse getStudentMajor(

	org.tempuri.GetStudentMajor getStudentMajor18)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2]
					.getName());
			_operationClient.getOptions().setAction("http://tempuri.org/GetStudentMajor");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentMajor18,
					optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentMajor")),
					new javax.xml.namespace.QName("http://tempuri.org/", "getStudentMajor"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					org.tempuri.GetStudentMajorResponse.class, getEnvelopeNamespaces(_returnEnv));

			return (org.tempuri.GetStudentMajorResponse) object;

		}
		catch (org.apache.axis2.AxisFault f)
		{

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null)
			{
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
						"GetStudentMajor")))
				{
					// make the fault by reflection
					try
					{
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentMajor"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentMajor"));
						java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					}
					catch (java.lang.ClassCastException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.ClassNotFoundException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.NoSuchMethodException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.reflect.InvocationTargetException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.IllegalAccessException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.InstantiationException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else
				{
					throw f;
				}
			}
			else
			{
				throw f;
			}
		}
		finally
		{
			if (_messageContext.getTransportOut() != null)
			{
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.tempuri.StuInfoWebService#startgetStudentMajor
	 * @param getStudentMajor18
	 */
	public void startgetStudentMajor(

	org.tempuri.GetStudentMajor getStudentMajor18,

	final org.tempuri.StuInfoWebServiceCallbackHandler callback)

	throws java.rmi.RemoteException
	{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[2].getName());
		_operationClient.getOptions().setAction("http://tempuri.org/GetStudentMajor");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentMajor18,
				optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentMajor")),
				new javax.xml.namespace.QName("http://tempuri.org/", "getStudentMajor"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback()
		{
			public void onMessage(org.apache.axis2.context.MessageContext resultContext)
			{
				try
				{
					org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

					java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
							org.tempuri.GetStudentMajorResponse.class, getEnvelopeNamespaces(resultEnv));
					callback.receiveResultgetStudentMajor((org.tempuri.GetStudentMajorResponse) object);

				}
				catch (org.apache.axis2.AxisFault e)
				{
					callback.receiveErrorgetStudentMajor(e);
				}
			}

			public void onError(java.lang.Exception error)
			{
				if (error instanceof org.apache.axis2.AxisFault)
				{
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt != null)
					{
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt
								.getQName(), "GetStudentMajor")))
						{
							// make the fault by reflection
							try
							{
								java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentMajor"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								// message class
								java.lang.String messageClassName = (java.lang.String) faultMessageMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentMajor"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[] { messageClass });
								m.invoke(ex, new java.lang.Object[] { messageObject });

								callback.receiveErrorgetStudentMajor(new java.rmi.RemoteException(ex.getMessage(), ex));
							}
							catch (java.lang.ClassCastException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentMajor(f);
							}
							catch (java.lang.ClassNotFoundException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentMajor(f);
							}
							catch (java.lang.NoSuchMethodException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentMajor(f);
							}
							catch (java.lang.reflect.InvocationTargetException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentMajor(f);
							}
							catch (java.lang.IllegalAccessException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentMajor(f);
							}
							catch (java.lang.InstantiationException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentMajor(f);
							}
							catch (org.apache.axis2.AxisFault e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentMajor(f);
							}
						}
						else
						{
							callback.receiveErrorgetStudentMajor(f);
						}
					}
					else
					{
						callback.receiveErrorgetStudentMajor(f);
					}
				}
				else
				{
					callback.receiveErrorgetStudentMajor(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext)
			{
				org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
						.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete()
			{
				try
				{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch (org.apache.axis2.AxisFault axisFault)
				{
					callback.receiveErrorgetStudentMajor(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[2].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener())
		{
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[2].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.tempuri.StuInfoWebService#getStudentGender
	 * @param getStudentGender20
	 */

	public org.tempuri.GetStudentGenderResponse getStudentGender(

	org.tempuri.GetStudentGender getStudentGender20)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3]
					.getName());
			_operationClient.getOptions().setAction("http://tempuri.org/GetStudentGender");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentGender20,
					optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentGender")),
					new javax.xml.namespace.QName("http://tempuri.org/", "getStudentGender"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					org.tempuri.GetStudentGenderResponse.class, getEnvelopeNamespaces(_returnEnv));

			return (org.tempuri.GetStudentGenderResponse) object;

		}
		catch (org.apache.axis2.AxisFault f)
		{

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null)
			{
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
						"GetStudentGender")))
				{
					// make the fault by reflection
					try
					{
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentGender"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentGender"));
						java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					}
					catch (java.lang.ClassCastException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.ClassNotFoundException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.NoSuchMethodException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.reflect.InvocationTargetException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.IllegalAccessException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.InstantiationException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else
				{
					throw f;
				}
			}
			else
			{
				throw f;
			}
		}
		finally
		{
			if (_messageContext.getTransportOut() != null)
			{
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.tempuri.StuInfoWebService#startgetStudentGender
	 * @param getStudentGender20
	 */
	public void startgetStudentGender(

	org.tempuri.GetStudentGender getStudentGender20,

	final org.tempuri.StuInfoWebServiceCallbackHandler callback)

	throws java.rmi.RemoteException
	{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[3].getName());
		_operationClient.getOptions().setAction("http://tempuri.org/GetStudentGender");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentGender20,
				optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentGender")),
				new javax.xml.namespace.QName("http://tempuri.org/", "getStudentGender"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback()
		{
			public void onMessage(org.apache.axis2.context.MessageContext resultContext)
			{
				try
				{
					org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

					java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
							org.tempuri.GetStudentGenderResponse.class, getEnvelopeNamespaces(resultEnv));
					callback.receiveResultgetStudentGender((org.tempuri.GetStudentGenderResponse) object);

				}
				catch (org.apache.axis2.AxisFault e)
				{
					callback.receiveErrorgetStudentGender(e);
				}
			}

			public void onError(java.lang.Exception error)
			{
				if (error instanceof org.apache.axis2.AxisFault)
				{
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt != null)
					{
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt
								.getQName(), "GetStudentGender")))
						{
							// make the fault by reflection
							try
							{
								java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentGender"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								// message class
								java.lang.String messageClassName = (java.lang.String) faultMessageMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentGender"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[] { messageClass });
								m.invoke(ex, new java.lang.Object[] { messageObject });

								callback.receiveErrorgetStudentGender(new java.rmi.RemoteException(ex.getMessage(), ex));
							}
							catch (java.lang.ClassCastException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentGender(f);
							}
							catch (java.lang.ClassNotFoundException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentGender(f);
							}
							catch (java.lang.NoSuchMethodException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentGender(f);
							}
							catch (java.lang.reflect.InvocationTargetException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentGender(f);
							}
							catch (java.lang.IllegalAccessException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentGender(f);
							}
							catch (java.lang.InstantiationException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentGender(f);
							}
							catch (org.apache.axis2.AxisFault e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentGender(f);
							}
						}
						else
						{
							callback.receiveErrorgetStudentGender(f);
						}
					}
					else
					{
						callback.receiveErrorgetStudentGender(f);
					}
				}
				else
				{
					callback.receiveErrorgetStudentGender(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext)
			{
				org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
						.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete()
			{
				try
				{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch (org.apache.axis2.AxisFault axisFault)
				{
					callback.receiveErrorgetStudentGender(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[3].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener())
		{
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[3].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.tempuri.StuInfoWebService#deleteStudentInfo
	 * @param deleteStudentInfo22
	 */

	public org.tempuri.DeleteStudentInfoResponse deleteStudentInfo(

	org.tempuri.DeleteStudentInfo deleteStudentInfo22)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4]
					.getName());
			_operationClient.getOptions().setAction("http://tempuri.org/DeleteStudentInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), deleteStudentInfo22,
					optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "deleteStudentInfo")),
					new javax.xml.namespace.QName("http://tempuri.org/", "deleteStudentInfo"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					org.tempuri.DeleteStudentInfoResponse.class, getEnvelopeNamespaces(_returnEnv));

			return (org.tempuri.DeleteStudentInfoResponse) object;

		}
		catch (org.apache.axis2.AxisFault f)
		{

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null)
			{
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
						"DeleteStudentInfo")))
				{
					// make the fault by reflection
					try
					{
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "DeleteStudentInfo"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "DeleteStudentInfo"));
						java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					}
					catch (java.lang.ClassCastException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.ClassNotFoundException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.NoSuchMethodException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.reflect.InvocationTargetException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.IllegalAccessException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.InstantiationException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else
				{
					throw f;
				}
			}
			else
			{
				throw f;
			}
		}
		finally
		{
			if (_messageContext.getTransportOut() != null)
			{
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.tempuri.StuInfoWebService#startdeleteStudentInfo
	 * @param deleteStudentInfo22
	 */
	public void startdeleteStudentInfo(

	org.tempuri.DeleteStudentInfo deleteStudentInfo22,

	final org.tempuri.StuInfoWebServiceCallbackHandler callback)

	throws java.rmi.RemoteException
	{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[4].getName());
		_operationClient.getOptions().setAction("http://tempuri.org/DeleteStudentInfo");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), deleteStudentInfo22,
				optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "deleteStudentInfo")),
				new javax.xml.namespace.QName("http://tempuri.org/", "deleteStudentInfo"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback()
		{
			public void onMessage(org.apache.axis2.context.MessageContext resultContext)
			{
				try
				{
					org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

					java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
							org.tempuri.DeleteStudentInfoResponse.class, getEnvelopeNamespaces(resultEnv));
					callback.receiveResultdeleteStudentInfo((org.tempuri.DeleteStudentInfoResponse) object);

				}
				catch (org.apache.axis2.AxisFault e)
				{
					callback.receiveErrordeleteStudentInfo(e);
				}
			}

			public void onError(java.lang.Exception error)
			{
				if (error instanceof org.apache.axis2.AxisFault)
				{
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt != null)
					{
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt
								.getQName(), "DeleteStudentInfo")))
						{
							// make the fault by reflection
							try
							{
								java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"DeleteStudentInfo"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								// message class
								java.lang.String messageClassName = (java.lang.String) faultMessageMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"DeleteStudentInfo"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[] { messageClass });
								m.invoke(ex, new java.lang.Object[] { messageObject });

								callback.receiveErrordeleteStudentInfo(new java.rmi.RemoteException(ex.getMessage(), ex));
							}
							catch (java.lang.ClassCastException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrordeleteStudentInfo(f);
							}
							catch (java.lang.ClassNotFoundException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrordeleteStudentInfo(f);
							}
							catch (java.lang.NoSuchMethodException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrordeleteStudentInfo(f);
							}
							catch (java.lang.reflect.InvocationTargetException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrordeleteStudentInfo(f);
							}
							catch (java.lang.IllegalAccessException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrordeleteStudentInfo(f);
							}
							catch (java.lang.InstantiationException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrordeleteStudentInfo(f);
							}
							catch (org.apache.axis2.AxisFault e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrordeleteStudentInfo(f);
							}
						}
						else
						{
							callback.receiveErrordeleteStudentInfo(f);
						}
					}
					else
					{
						callback.receiveErrordeleteStudentInfo(f);
					}
				}
				else
				{
					callback.receiveErrordeleteStudentInfo(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext)
			{
				org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
						.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete()
			{
				try
				{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch (org.apache.axis2.AxisFault axisFault)
				{
					callback.receiveErrordeleteStudentInfo(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[4].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener())
		{
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[4].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.tempuri.StuInfoWebService#getStudentEmail
	 * @param getStudentEmail24
	 */

	public org.tempuri.GetStudentEmailResponse getStudentEmail(

	org.tempuri.GetStudentEmail getStudentEmail24)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5]
					.getName());
			_operationClient.getOptions().setAction("http://tempuri.org/GetStudentEmail");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentEmail24,
					optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentEmail")),
					new javax.xml.namespace.QName("http://tempuri.org/", "getStudentEmail"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					org.tempuri.GetStudentEmailResponse.class, getEnvelopeNamespaces(_returnEnv));

			return (org.tempuri.GetStudentEmailResponse) object;

		}
		catch (org.apache.axis2.AxisFault f)
		{

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null)
			{
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
						"GetStudentEmail")))
				{
					// make the fault by reflection
					try
					{
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentEmail"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "GetStudentEmail"));
						java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					}
					catch (java.lang.ClassCastException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.ClassNotFoundException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.NoSuchMethodException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.reflect.InvocationTargetException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.IllegalAccessException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.InstantiationException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else
				{
					throw f;
				}
			}
			else
			{
				throw f;
			}
		}
		finally
		{
			if (_messageContext.getTransportOut() != null)
			{
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.tempuri.StuInfoWebService#startgetStudentEmail
	 * @param getStudentEmail24
	 */
	public void startgetStudentEmail(

	org.tempuri.GetStudentEmail getStudentEmail24,

	final org.tempuri.StuInfoWebServiceCallbackHandler callback)

	throws java.rmi.RemoteException
	{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[5].getName());
		_operationClient.getOptions().setAction("http://tempuri.org/GetStudentEmail");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getStudentEmail24,
				optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "getStudentEmail")),
				new javax.xml.namespace.QName("http://tempuri.org/", "getStudentEmail"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback()
		{
			public void onMessage(org.apache.axis2.context.MessageContext resultContext)
			{
				try
				{
					org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

					java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
							org.tempuri.GetStudentEmailResponse.class, getEnvelopeNamespaces(resultEnv));
					callback.receiveResultgetStudentEmail((org.tempuri.GetStudentEmailResponse) object);

				}
				catch (org.apache.axis2.AxisFault e)
				{
					callback.receiveErrorgetStudentEmail(e);
				}
			}

			public void onError(java.lang.Exception error)
			{
				if (error instanceof org.apache.axis2.AxisFault)
				{
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt != null)
					{
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt
								.getQName(), "GetStudentEmail")))
						{
							// make the fault by reflection
							try
							{
								java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentEmail"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								// message class
								java.lang.String messageClassName = (java.lang.String) faultMessageMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"GetStudentEmail"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[] { messageClass });
								m.invoke(ex, new java.lang.Object[] { messageObject });

								callback.receiveErrorgetStudentEmail(new java.rmi.RemoteException(ex.getMessage(), ex));
							}
							catch (java.lang.ClassCastException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentEmail(f);
							}
							catch (java.lang.ClassNotFoundException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentEmail(f);
							}
							catch (java.lang.NoSuchMethodException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentEmail(f);
							}
							catch (java.lang.reflect.InvocationTargetException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentEmail(f);
							}
							catch (java.lang.IllegalAccessException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentEmail(f);
							}
							catch (java.lang.InstantiationException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentEmail(f);
							}
							catch (org.apache.axis2.AxisFault e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetStudentEmail(f);
							}
						}
						else
						{
							callback.receiveErrorgetStudentEmail(f);
						}
					}
					else
					{
						callback.receiveErrorgetStudentEmail(f);
					}
				}
				else
				{
					callback.receiveErrorgetStudentEmail(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext)
			{
				org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
						.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete()
			{
				try
				{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch (org.apache.axis2.AxisFault axisFault)
				{
					callback.receiveErrorgetStudentEmail(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[5].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener())
		{
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[5].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.tempuri.StuInfoWebService#updateStudentInfo
	 * @param updateStudentInfo26
	 */

	public org.tempuri.UpdateStudentInfoResponse updateStudentInfo(

	org.tempuri.UpdateStudentInfo updateStudentInfo26)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6]
					.getName());
			_operationClient.getOptions().setAction("http://tempuri.org/UpdateStudentInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), updateStudentInfo26,
					optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "updateStudentInfo")),
					new javax.xml.namespace.QName("http://tempuri.org/", "updateStudentInfo"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
					org.tempuri.UpdateStudentInfoResponse.class, getEnvelopeNamespaces(_returnEnv));

			return (org.tempuri.UpdateStudentInfoResponse) object;

		}
		catch (org.apache.axis2.AxisFault f)
		{

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null)
			{
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
						"UpdateStudentInfo")))
				{
					// make the fault by reflection
					try
					{
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "UpdateStudentInfo"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "UpdateStudentInfo"));
						java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					}
					catch (java.lang.ClassCastException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.ClassNotFoundException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.NoSuchMethodException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.reflect.InvocationTargetException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.IllegalAccessException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch (java.lang.InstantiationException e)
					{
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else
				{
					throw f;
				}
			}
			else
			{
				throw f;
			}
		}
		finally
		{
			if (_messageContext.getTransportOut() != null)
			{
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.tempuri.StuInfoWebService#startupdateStudentInfo
	 * @param updateStudentInfo26
	 */
	public void startupdateStudentInfo(

	org.tempuri.UpdateStudentInfo updateStudentInfo26,

	final org.tempuri.StuInfoWebServiceCallbackHandler callback)

	throws java.rmi.RemoteException
	{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[6].getName());
		_operationClient.getOptions().setAction("http://tempuri.org/UpdateStudentInfo");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), updateStudentInfo26,
				optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "updateStudentInfo")),
				new javax.xml.namespace.QName("http://tempuri.org/", "updateStudentInfo"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback()
		{
			public void onMessage(org.apache.axis2.context.MessageContext resultContext)
			{
				try
				{
					org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

					java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
							org.tempuri.UpdateStudentInfoResponse.class, getEnvelopeNamespaces(resultEnv));
					callback.receiveResultupdateStudentInfo((org.tempuri.UpdateStudentInfoResponse) object);

				}
				catch (org.apache.axis2.AxisFault e)
				{
					callback.receiveErrorupdateStudentInfo(e);
				}
			}

			public void onError(java.lang.Exception error)
			{
				if (error instanceof org.apache.axis2.AxisFault)
				{
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt != null)
					{
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt
								.getQName(), "UpdateStudentInfo")))
						{
							// make the fault by reflection
							try
							{
								java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"UpdateStudentInfo"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								// message class
								java.lang.String messageClassName = (java.lang.String) faultMessageMap
										.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
												"UpdateStudentInfo"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[] { messageClass });
								m.invoke(ex, new java.lang.Object[] { messageObject });

								callback.receiveErrorupdateStudentInfo(new java.rmi.RemoteException(ex.getMessage(), ex));
							}
							catch (java.lang.ClassCastException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorupdateStudentInfo(f);
							}
							catch (java.lang.ClassNotFoundException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorupdateStudentInfo(f);
							}
							catch (java.lang.NoSuchMethodException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorupdateStudentInfo(f);
							}
							catch (java.lang.reflect.InvocationTargetException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorupdateStudentInfo(f);
							}
							catch (java.lang.IllegalAccessException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorupdateStudentInfo(f);
							}
							catch (java.lang.InstantiationException e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorupdateStudentInfo(f);
							}
							catch (org.apache.axis2.AxisFault e)
							{
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorupdateStudentInfo(f);
							}
						}
						else
						{
							callback.receiveErrorupdateStudentInfo(f);
						}
					}
					else
					{
						callback.receiveErrorupdateStudentInfo(f);
					}
				}
				else
				{
					callback.receiveErrorupdateStudentInfo(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext)
			{
				org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
						.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete()
			{
				try
				{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch (org.apache.axis2.AxisFault axisFault)
				{
					callback.receiveErrorupdateStudentInfo(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[6].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener())
		{
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[6].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * A utility method that copies the namepaces from the SOAPEnvelope
	 */
	private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env)
	{
		java.util.Map returnMap = new java.util.HashMap();
		java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
		while (namespaceIterator.hasNext())
		{
			org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
			returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
		}
		return returnMap;
	}

	private javax.xml.namespace.QName[]	opNameArray	= null;

	private boolean optimizeContent(javax.xml.namespace.QName opName)
	{

		if (opNameArray == null)
		{
			return false;
		}
		for (int i = 0; i < opNameArray.length; i++)
		{
			if (opName.equals(opNameArray[i]))
			{
				return true;
			}
		}
		return false;
	}

	// http://localhost:60519/StuInfoWebService.asmx
	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentName param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentName.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentNameResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentNameResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.InsertStudentInfo param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.InsertStudentInfo.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.InsertStudentInfoResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.InsertStudentInfoResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentMajor param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentMajor.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentMajorResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentMajorResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentGender param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentGender.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentGenderResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentGenderResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.DeleteStudentInfo param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.DeleteStudentInfo.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.DeleteStudentInfoResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.DeleteStudentInfoResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentEmail param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentEmail.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.GetStudentEmailResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.GetStudentEmailResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.UpdateStudentInfo param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.UpdateStudentInfo.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.tempuri.UpdateStudentInfoResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault
	{

		try
		{
			return param.getOMElement(org.tempuri.UpdateStudentInfoResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
			org.tempuri.GetStudentName param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault
	{

		try
		{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.tempuri.GetStudentName.MY_QNAME, factory));
			return emptyEnvelope;
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
			org.tempuri.InsertStudentInfo param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault
	{

		try
		{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.tempuri.InsertStudentInfo.MY_QNAME, factory));
			return emptyEnvelope;
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
			org.tempuri.GetStudentMajor param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault
	{

		try
		{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.tempuri.GetStudentMajor.MY_QNAME, factory));
			return emptyEnvelope;
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
			org.tempuri.GetStudentGender param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault
	{

		try
		{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.tempuri.GetStudentGender.MY_QNAME, factory));
			return emptyEnvelope;
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
			org.tempuri.DeleteStudentInfo param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault
	{

		try
		{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.tempuri.DeleteStudentInfo.MY_QNAME, factory));
			return emptyEnvelope;
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
			org.tempuri.GetStudentEmail param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault
	{

		try
		{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.tempuri.GetStudentEmail.MY_QNAME, factory));
			return emptyEnvelope;
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
			org.tempuri.UpdateStudentInfo param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault
	{

		try
		{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.tempuri.UpdateStudentInfo.MY_QNAME, factory));
			return emptyEnvelope;
		}
		catch (org.apache.axis2.databinding.ADBException e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	/**
	 * get the default envelope
	 */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory)
	{
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param, java.lang.Class type,
			java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault
	{

		try
		{

			if (org.tempuri.GetStudentName.class.equals(type))
			{

				return org.tempuri.GetStudentName.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.GetStudentNameResponse.class.equals(type))
			{

				return org.tempuri.GetStudentNameResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.InsertStudentInfo.class.equals(type))
			{

				return org.tempuri.InsertStudentInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.InsertStudentInfoResponse.class.equals(type))
			{

				return org.tempuri.InsertStudentInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.GetStudentMajor.class.equals(type))
			{

				return org.tempuri.GetStudentMajor.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.GetStudentMajorResponse.class.equals(type))
			{

				return org.tempuri.GetStudentMajorResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.GetStudentGender.class.equals(type))
			{

				return org.tempuri.GetStudentGender.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.GetStudentGenderResponse.class.equals(type))
			{

				return org.tempuri.GetStudentGenderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.DeleteStudentInfo.class.equals(type))
			{

				return org.tempuri.DeleteStudentInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.DeleteStudentInfoResponse.class.equals(type))
			{

				return org.tempuri.DeleteStudentInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.GetStudentEmail.class.equals(type))
			{

				return org.tempuri.GetStudentEmail.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.GetStudentEmailResponse.class.equals(type))
			{

				return org.tempuri.GetStudentEmailResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.UpdateStudentInfo.class.equals(type))
			{

				return org.tempuri.UpdateStudentInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (org.tempuri.UpdateStudentInfoResponse.class.equals(type))
			{

				return org.tempuri.UpdateStudentInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

		}
		catch (java.lang.Exception e)
		{
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}

}

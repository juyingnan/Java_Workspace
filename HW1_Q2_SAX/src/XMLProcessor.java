import java.io.*;
import java.util.*;

import org.apache.xerces.xni.QName;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class XMLProcessor extends DefaultHandler

{
	/*
	 *  PURPOSE
	 *  this XML processor is to output 
	 *  1. All complex types names
	 *  2. All operation names in portType
	 *  3. service element and its sub-elements
	 */
	
	//constants
	//private static final String _NAMESPACE_URI = "http://webxml.com.cn/";
	
	//part 1 :for All complex types names
	private static final String _ELEMENT_NAME_1= "s:complexTypes";		
	private static final String _ATTRIBUTE_NAME_1= "name";
	
	//part 2 :for All operation names in portType
	private static final String _ELEMENT_NAME_2_1 = "wsdl:porttype";
	private static final String _ELEMENT_NAME_2_2 = "wsdl:operation";	
	private static final String _ATTRIBUTE_NAME_2= "name";	
	
	//part 3 :for service element and its sub-elements
	private static final String _ELEMENT_NAME_3= "wsdl:service";
	
	//part 2 :flag for "portType" elements
	private static boolean FLAG_OF_ELEMENT_NAME_2_1=false;
	
	//part 3 :flag for "service" elements
	private static boolean FLAG_OF_ELEMENT_NAME_3=false;
	
	//part 1 & 2 : The data we're looking for in the document
    private LinkedList _attributes_1 = new LinkedList();
    private LinkedList _attributes_2 = new LinkedList();
    
    //part 3 :string for output of service element and its sub-elements
    private String elementOutput = new String();	
	
	//The method called when the start of a new element is found
	public void startElement(String namespaceURI, String localName,
            String qualifiedName, Attributes attributes) throws SAXException
    {
		//part 1
		//if element = message
		if(qualifiedName.toLowerCase().equals(_ELEMENT_NAME_1))
		{
			//to examine the length of attributes
			int len = attributes.getLength();
			//if any attribute
			if(len>0)
			for (int i = 0; i < len; i++) 
			{
				//if attribute = name
				if(attributes.getLocalName(i).equals(_ATTRIBUTE_NAME_1))
				{
					//add to the list
					_attributes_1.add(attributes.getValue(i));
					break;
				}
			}
		}
		
		//part 2
		//if element = binding
		if(qualifiedName.toLowerCase().equals(_ELEMENT_NAME_2_1))
		{
			//set the flag to true for its sub element
			FLAG_OF_ELEMENT_NAME_2_1=true;
		}		
		
		if(FLAG_OF_ELEMENT_NAME_2_1)
		{
			//if element = binding & sub element = operation
			if(qualifiedName.toLowerCase().equals(_ELEMENT_NAME_2_2))
			{
				//to examine the length of attributes
				int len = attributes.getLength();
				//if any attribute
				if(len>0)
				for (int i = 0; i < len; i++) 
				{
					//if attribute = name
					if(attributes.getLocalName(i).equals(_ATTRIBUTE_NAME_2))
					{
						//add to the list
						_attributes_2.add(attributes.getValue(i));
						break;
					}
				}				
			}
		}
		
		//part3
		//to check whether service element begins
		if(qualifiedName.toLowerCase().equals(_ELEMENT_NAME_3))
		{
			//set the flag to true for its sub element
			FLAG_OF_ELEMENT_NAME_3=true;
		}
		//if service element begins
		if (FLAG_OF_ELEMENT_NAME_3)
		{
			//read service element and its sub element and convert to a string to output 
			elementOutput+="<" +qualifiedName;
			
			//add attributes to string
			int len = attributes.getLength();
			if(len>0)
			for( int i = 0; i < len; i++ )  
            {  
				elementOutput+= " " + attributes.getLocalName( i );  
				elementOutput+= "=\"" + attributes.getValue( i )+"\"";  
            }  
			
			elementOutput+=">";			
		}
		
    }
	
	public void characters(char[] ch, int start, int length) throws SAXException

    {
        //part 3
		//if it is service element and its sub element
		if (FLAG_OF_ELEMENT_NAME_3)
		{
			//check whether there is any content in the element, if any, add to output string 
			elementOutput+=new String(ch,start,length);
		}
    }
	
	public void endElement(String uri, String localName, String qualifiedName)  
            throws SAXException 
    {
		//part 2
		//end of element portType
		if(qualifiedName.toLowerCase().equals(_ELEMENT_NAME_2_1))
		{
			//at the end of the father element, set the flag to false
			FLAG_OF_ELEMENT_NAME_2_1=false;
		}
		
		//part3
		if (FLAG_OF_ELEMENT_NAME_3)
		{
			//add the end tag to the output string
			elementOutput+="</"+qualifiedName+">";
		}
		if(qualifiedName.toLowerCase().equals(_ELEMENT_NAME_3))
		{
			//at the end of the father element, set the flag to false
			FLAG_OF_ELEMENT_NAME_3=false;
		}
	}
	
        //OUTPUT functions
	
		//part 1 : ALL COMPLEXTYPES NAMES
        public StringWriter outputResult1() 
        {
        	StringWriter sw = new StringWriter();
        	
        	sw.write("ALL COMPLEXTYPES NAMES\n");
        	
        	for(int i = 0; i < _attributes_1.size(); i++)
        	{
        	sw.write("<"+_ELEMENT_NAME_1 +" "+ _ATTRIBUTE_NAME_1+"=\"");

            sw.write((String)_attributes_1.get(i));

            sw.write("\"></"+_ELEMENT_NAME_1+">\n");
        	}

            return sw;
		}
        
        //part 2 : ALL OPERATION NAMES OF PORTTYPES
        public StringWriter outputResult2() 
        {
        	StringWriter sw = new StringWriter();
        	
        	sw.write("ALL OPERATION NAMES OF PORTTYPES\n");
        	
        	for(int i = 0; i < _attributes_2.size(); i++)
        	{
        	sw.write("<"+_ELEMENT_NAME_2_2 +" "+ _ATTRIBUTE_NAME_2+"=\"");

            sw.write((String)_attributes_2.get(i));

            sw.write("\"></"+_ELEMENT_NAME_2_2+">\n");
        	}

            return sw;
		}
        
        //part 3 : SERVICE ELEMENT AND ITS SUB-ELEMENTS
        public StringWriter outputResult3() 
        {
        	StringWriter sw = new StringWriter();
        	
        	sw.write("SERVICE ELEMENT AND ITS SUB-ELEMENTS\n");
        	
        	sw.write(elementOutput);
        	
        	return sw;
        }
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		if (args.length!=1)
		{
			return;
		}
		
		XMLProcessor xmlProcessor=new XMLProcessor();
		XMLReader parser = null;
		
		// Create parser
		try 
		{
			parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			// Tell the parser which object will handle SAX parsing events
			parser.setContentHandler(xmlProcessor);	
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			System.err.println("Unable to create Xerces SAX parser - check classpath");
		}
		
		try 
		{
			// The URL that sources the DVD goes here (i.e. perform a GET on some remote Web server).
            parser.parse(args[0]);
            System.out.println(xmlProcessor.outputResult1().toString());
            System.out.println("************************************************************");
            System.out.println(xmlProcessor.outputResult2().toString());
            System.out.println("************************************************************");
            System.out.println(xmlProcessor.outputResult3().toString());
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}

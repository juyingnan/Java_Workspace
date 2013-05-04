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
	private static final String _ELEMENT_NAME_1= "s:complexType";		
	private static final String _ATTRIBUTE_NAME_1= "name";
	
	//part1 : flag of complexType
	private boolean _FLAG_COMPLEXTYPE = false;
	
	//part 1 & 2 : The data we're looking for in the document
    private LinkedList _attributes_1 = new LinkedList();
    private LinkedList _element_Name_1 = new LinkedList();
    
    //part 1 : output string
    private String outputString="";
	
	//The method called when the start of a new element is found
	public void startElement(String namespaceURI, String localName,
            String qualifiedName, Attributes attributes) throws SAXException
    {
		//part 1
		//beginning of complexType, flag=true
		if(qualifiedName.equals(_ELEMENT_NAME_1))
		{
			_FLAG_COMPLEXTYPE=true;
		}		
		
		//if complexType
		if(_FLAG_COMPLEXTYPE)
		{
			//output tag
			outputString+="<"+qualifiedName;
			
			//to examine the length of attributes
			int len = attributes.getLength();
			if(len>0)
			{
				for (int i = 0; i < len; i++) 
				{
				
					//if attribute = name
					if(attributes.getLocalName(i).equals(_ATTRIBUTE_NAME_1))
					{
						//output
						outputString+=" "+attributes.getLocalName(i)+"=\""+attributes.getValue(i)+"\""+">\n";
								break;
					}	
				}
			}
			else
			{
				outputString+=">\n";
			}			
		}		
    }
	
	public void characters(char[] ch, int start, int length) throws SAXException

    {
        
    }
	
	public void endElement(String uri, String localName, String qualifiedName)  
            throws SAXException 
    {
		if(_FLAG_COMPLEXTYPE)
		{
			//output close tag
			outputString+="</"+qualifiedName+">\n";
		}
		
		//end of xomplexType, flag=false
		if(qualifiedName.equals(_ELEMENT_NAME_1))
		{
			_FLAG_COMPLEXTYPE=false;
			outputString+="\n";
		}	
	}
	
        //OUTPUT functions
	
		//part 1 : ALL COMPLEXTYPES NAMES
        public StringWriter outputResult1() 
        {
        	StringWriter sw = new StringWriter();
        	
        	sw.write("ALL COMPLEXTYPES NAMES\n");
        	
        	sw.write(outputString);

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
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}

package com.janaezwadaawa.gcm;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author H.L - admin
 *
 */
public class GcmResponseHandler extends DefaultHandler {

	protected final String TAG = "FSDS: "+ getClass () .getSimpleName();
	
	private final String RESULT 		= "result";
	private final String SUCCESS 		= "success";
	private final String MESSAGE 		= "message";
	
	private GcmResponse gcmResponse;

	// Buffer for a tag XML data
	protected StringBuffer buffer;


	public GcmResponseHandler () {
		super ();
	}

	// receives byte from the parser
	public void characters(char[] ch,int start, int length)	throws SAXException{

		String lecture = new String(ch,start,length);
		if(buffer != null) 
			buffer.append(lecture);
	}



	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		buffer = new StringBuffer();
		
		if (localName.equalsIgnoreCase(RESULT)){
			gcmResponse = new GcmResponse();
		}
		
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (localName.equalsIgnoreCase(SUCCESS)){
			String keyValue = buffer.toString();
			gcmResponse.setSuccess(keyValue.equals("1"));
		}
		else if (localName.equalsIgnoreCase(MESSAGE)){
			String stringValue = buffer.toString();
			gcmResponse.setMessage(stringValue);
		}
	}
	
	
	public GcmResponse getResult(){
		return gcmResponse;
	}

}

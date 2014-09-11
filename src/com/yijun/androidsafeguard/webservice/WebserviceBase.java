package com.yijun.androidsafeguard.webservice;

import java.net.Proxy;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class WebserviceBase {
	
	protected static final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    envelope.dotNet = true;
	    envelope.implicitTypes = true;
	    envelope.setAddAdornments(false);
	    envelope.setOutputSoapObject(request);
	 
	    return envelope;
	}
	
	protected final HttpTransportSE getHttpTransportSE(String URL) {
		HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,URL,60000);
	    ht.debug = true;
	    ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
	    return ht;
	}

}

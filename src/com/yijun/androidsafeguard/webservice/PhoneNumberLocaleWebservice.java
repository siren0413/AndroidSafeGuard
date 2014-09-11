package com.yijun.androidsafeguard.webservice;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.yijun.androidsafeguard.model.Locale;

import android.R.integer;
import android.os.AsyncTask;
import android.util.Log;

public class PhoneNumberLocaleWebservice extends WebserviceBase {
	 private final String NAMESPACE = "http://www.webserviceX.NET";
	 private final String URL = "http://www.webservicex.net/uszip.asmx";
	 private final String SOAP_ACTION = "http://www.webserviceX.NET/GetInfoByAreaCode";
	 private final String METHOD_NAME = "GetInfoByAreaCode";

	private final String TAG = "PhoneNumberLocaleWebservice";

//	private final String NAMESPACE = "http://www.w3schools.com/webservices/";
//	private final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
//	private final String SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius";
//	private final String METHOD_NAME = "FahrenheitToCelsius";

	public Locale query(String areaCode) {
		try {
			AsyncWorker worker = new AsyncWorker();
			AsyncTask<String, Void, Locale> task = worker.execute(areaCode);
			return task.get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.getMessage());
		} catch (ExecutionException e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	private class AsyncWorker extends AsyncTask<String, Void, Locale> {

		@Override
		protected Locale doInBackground(String... params) {
			try {
				String areaCode = params[0];
				Log.i(TAG, "area code: " + areaCode);
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("USAreaCode", areaCode);
				SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);
				
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = getHttpTransportSE(URL);
				transport.call(SOAP_ACTION, envelope);
				Log.i(TAG, "http call done");
				
				SoapObject response = (SoapObject) envelope.bodyIn;
				//SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				//Log.i(TAG, "web service call result: " + response.toString());
				
				if(response.toString().trim().isEmpty()) return null;
				if(!response.toString().contains("CITY")) return null;
				if(!response.toString().contains("STATE")) return null;
				if(!response.toString().contains("ZIP")) return null;
				
				Locale locale = new Locale();
				int cityIdx = response.toString().indexOf("CITY");
				int stateIdx = response.toString().indexOf("STATE");
				int zipIdx = response.toString().indexOf("ZIP");
				String city;
				String state;
				String zip;
				int cursor = cityIdx;
				while(cursor < response.toString().length() && response.toString().charAt(cursor)!=';') cursor++;
				city = response.toString().substring(cityIdx,cursor);
				cursor = stateIdx;
				while(cursor < response.toString().length() && response.toString().charAt(cursor)!=';') cursor++;
				state = response.toString().substring(stateIdx,cursor);
				cursor = zipIdx;
				while(cursor < response.toString().length() && response.toString().charAt(cursor)!=';') cursor++;
				zip = response.toString().substring(zipIdx,cursor);
				locale.setCity(city);
				locale.setState(state);
				locale.setZip(zip);
				return locale;
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			} catch (XmlPullParserException e) {
				Log.e(TAG, e.getMessage());
			}
			return null;
		}

	}

}

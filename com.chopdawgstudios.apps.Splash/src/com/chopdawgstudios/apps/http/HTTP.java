package com.chopdawgstudios.apps.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HTTP {	
	public String get(String page) {
		HttpURLConnection connection = null;
		String data = "";
		
	    try {
	        URL url = new URL(page);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        data = convertStreamToString(connection.getInputStream());
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    	return "Malformed URL";
	    } catch (IOException e) {
	        e.printStackTrace();
	    	return "Problem with Connection";
	    } finally {
	        if(null != connection) { 
	        	connection.disconnect(); 
	        }
	    }
	    
        return data;
	}
	
	public String post(String page, Map<String, String> params) {
		HttpURLConnection connection = null;
		String data = "";
			
	    try {
	        URL url = new URL(page);
	        
	        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
 
	        String param = "";
	        int i = 0;
	        
	        while(iterator.hasNext()) {
	        	i++;
	        	Entry<String, String> value = iterator.next();
	        	param += (i > 1 ? "&" : "") + value.getKey() + "=" + URLEncoder.encode(value.getValue(), "UTF-8");
	        } 
	        	        
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setFixedLengthStreamingMode(param.getBytes().length);
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        
	        PrintWriter out = new PrintWriter(connection.getOutputStream());
	        out.print(param);
	        out.close();
	        	        
	        data = convertStreamToString(connection.getInputStream());
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    	return "Malformed URL";
	    } catch (IOException e) {
	        e.printStackTrace();
	    	return "Problem with Connection";
	    } finally {
	        if(null != connection) { 
	        	connection.disconnect(); 
	        }
	    }
	    
        return data;
	}
	
	public String MD5(String s) {
		MessageDigest m = null;
		
		try {
		        m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		}
		
		m.update(s.getBytes(),0,s.length());
		String hash = new BigInteger(1, m.digest()).toString(16);
		
		return hash;
	}
	
	String convertStreamToString(java.io.InputStream is) {
	    try {
	        return new java.util.Scanner(is).useDelimiter("\\A").next();
	    } catch (java.util.NoSuchElementException e) {
	        return "No Data Recieved, but a connection was established";
	    }
	}
}

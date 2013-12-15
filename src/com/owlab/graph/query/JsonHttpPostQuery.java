package com.owlab.graph.query;
import net.sf.json.JSONObject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class JsonHttpPostQuery {
	static HttpPost request = new HttpPost("http://fresto1.owlab.com:9999/clientHitCount");
	
    public static void main(String[] args) throws Exception {
    	JSONObject json = new JSONObject();
    	json.put("second",System.currentTimeMillis()); 
    	json.put("duration", 5);
    	
    	System.out.println("REQ: "+json.toString());

    	CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    	try {
    	    //HttpPost request = new HttpPost("http://fresto1.owlab.com:9999/clientHitCount");
    	    HttpPost request = new HttpPost("http://fresto1.owlab.com:9999/httpHitCountForSeconds");
    	    //StringEntity params = new StringEntity(json.toString());
    	    /*long curTime = System.currentTimeMillis();
    	    String jbody = "{\"second\":"+curTime+"}";
    	    System.out.println("REQ "+jbody);*/
    	    StringEntity params = new StringEntity(json.toString());
    	    request.addHeader("content-type", "application/json");
    	    request.setEntity(params);
    	    CloseableHttpResponse response = httpClient.execute(request);
    	    
    	    // handle response
    	    System.out.println(EntityUtils.toString(response.getEntity()));
    	    
    	} catch (Exception ex) {
    	   ex.printStackTrace();
    	} finally {
    	    httpClient.close();
    	}
    }
    
    public static long queryResponseTime() throws Exception{
    	CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    	long elapseTime = 0L;

    	try {
    	    //StringEntity params = new StringEntity(json.toString());
    	    long curTime = System.currentTimeMillis();
    	    String jbody = "{\"second\":"+curTime+"}";
    	    System.out.println("REQ "+jbody);
    	    StringEntity params = new StringEntity(jbody);
    	    request.addHeader("content-type", "application/json");
    	    request.setEntity(params);
    	    CloseableHttpResponse response = httpClient.execute(request);
    	    
    	    // handle response
    	    String strJson = EntityUtils.toString(response.getEntity());
    	    System.out.println("RES: "+strJson);
    	    
    	    JSONObject jsonObject = JSONObject.fromObject( strJson );  
            System.out.println(jsonObject.get("status"));
            JSONObject data = (JSONObject)jsonObject.get("data");
            System.out.println(data.toString());
            System.out.println(data.get("second"));
            elapseTime = Long.parseLong((data.get("h0").toString()));
            System.out.println("elapseTime:"+elapseTime);
    	    
    	    
    	} catch (Exception ex) {
    	   ex.printStackTrace();
    	} finally {
    	    httpClient.close();
    	}
        return elapseTime;
    }
    
}

package org.cytoscape.sample.internal;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProxySelector;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class CreateNetworkTask extends AbstractTask {

	private CyAppAdapter adapter;
	
	public CreateNetworkTask(CyAppAdapter a) {
		this.adapter = a;
	}
	
	public void run(TaskMonitor monitor) {
		// Create an empty network
		CyNetwork myNet = adapter.getCyNetworkFactory().createNetwork();
		myNet.getRow(myNet).set(CyNetwork.NAME, "My-network");
		
		// Add two nodes to the network
		CyNode node1 = myNet.addNode();
		CyNode node2 = myNet.addNode();
		
		// set name attribute for new nodes
		myNet.getDefaultNodeTable().getRow(node1.getSUID()).set("name", "Node1");
		myNet.getDefaultNodeTable().getRow(node2.getSUID()).set("name", "Node2");
		
		// Add an edge
		myNet.addEdge(node1, node2, true);
				
		adapter.getCyNetworkManager().addNetwork(myNet);
		
		final CloseableHttpClient client = HttpClientBuilder.create()
		         .setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault())) // use JVM's proxy settings
		        // add more stuff here later to configure the client
		         .build();
		
		HttpPost httpPost = new HttpPost("http://graphspace.org/api/users/anurag30671371@gmail.com/graph/add/My network/");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "anurag30671371@gmail.com"));
		nvps.add(new BasicNameValuePair("password", "dEP-9Cp-vzu-R4S"));
		nvps.add(new BasicNameValuePair("graphname", "{\"graph\":{\"nodes\":{\"data\": {\"shape\": \"ellipse\", \"id\":\"P4314611\", \"content\": \"DCC\", \"height\": 50, \"width\": 50, \"background_color\": \"yellow\", } } } }"));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CloseableHttpResponse response2;
		try {
			response2 = client.execute(httpPost);
			System.out.println(response2.getStatusLine());
		    HttpEntity entity2 = response2.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity2);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
//		URL obj;
//		try {
//			obj = new URL("http://graphspace.org/api/users/anurag30671371@gmail.com/graph/add/My-network/");
//			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//			con.setRequestMethod("POST");
//
//			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//			nvps.add(new BasicNameValuePair("username", "anurag30671371@gmail.com"));
//			nvps.add(new BasicNameValuePair("password", "dEP-9Cp-vzu-R4S"));
//			nvps.add(new BasicNameValuePair("graphname", "{\"graph\":{\"nodes\":{\"data\": {\"shape\": \"ellipse\", \"id\":\"P4314611\", \"content\": \"DCC\", \"height\": 50, \"width\": 50, \"background_color\": \"yellow\", } } } }"));
//			// For POST only - START
//			con.setDoOutput(true);
//			con.connect();
//			OutputStream os;
//			try {
//				os = con.getOutputStream();
//				BufferedWriter writer = new BufferedWriter(
//				        new OutputStreamWriter(os, "UTF-8"));
//				writer.write(getQuery(nvps));
//				writer.flush();
//				writer.close();
//				os.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			
//			// For POST only - END
//
//			int responseCode = con.getResponseCode();
//			System.out.println("POST Response Code :: " + responseCode);
//
//			if (responseCode == HttpURLConnection.HTTP_OK) { 
//				//success
//				System.out.println("POST Successfully");
//			} else {
//				System.out.println("POST request not worked");
//			}
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			
//		}
		
		
		
		//// The following code will destroy the network
		boolean destroyNetwork = false;
		if (destroyNetwork){
			// Destroy it
			 adapter.getCyNetworkManager().destroyNetwork(myNet);			
		}
	}
	
//	private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
//	{
//	    StringBuilder result = new StringBuilder();
//	    boolean first = true;
//
//	    for (NameValuePair pair : params)
//	    {
//	        if (first)
//	            first = false;
//	        else
//	            result.append("&");
//
//	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
//	        result.append("=");
//	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
//	    }
//
//	    return result.toString();
//	}
}

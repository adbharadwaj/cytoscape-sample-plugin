package org.cytoscape.sample.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateNetworkTask extends AbstractTask {
	
	private final CyNetworkManager netMgr;
	private final CyNetworkFactory cnf;
	private final CyNetworkNaming namingUtil; 
	private static CyNetwork myNet;
	
	public CreateNetworkTask(final CyNetworkManager netMgr, final CyNetworkNaming namingUtil, final CyNetworkFactory cnf){
		this.netMgr = netMgr;
		this.cnf = cnf;
		this.namingUtil = namingUtil;
	}
	
	public static void saveNetwork(){
		System.out.println("saveNetwork is called");
		// Graph saved in JSON file
					try {
						JSONObject networkJSON = new JSONObject();

						// For nodes
						JSONArray nodesArray = new JSONArray();
						List<CyNode> nodeList = myNet.getNodeList();
						for (int index = 0; index < nodeList.size(); index++) {
							final CyNode node = nodeList.get(index);
							if (node == null)
								continue;
							JSONObject nodeJSON = new JSONObject();
							nodeJSON.put("shape", "ellipse");
							nodeJSON.put("id", node.getSUID() + "");
							nodeJSON.put("content", myNet.getRow(node).get(CyNetwork.NAME, String.class));

							JSONObject nodesElement = new JSONObject();
							nodesElement.put("data", nodeJSON);

							nodesArray.put(nodesElement);
						}

						// For Edges
						JSONArray edgesArray = new JSONArray();
						List<CyEdge> edgesList = myNet.getEdgeList();
						for (int index = 0; index < edgesList.size(); index++) {
							final CyEdge edge = edgesList.get(index);
							CyNode source = edge.getSource();
							CyNode target = edge.getTarget();
							JSONObject edgeJSON = new JSONObject();
							edgeJSON.put("id", source.getSUID() + "-" + target.getSUID());
							edgeJSON.put("source", source.getSUID() + "");
							edgeJSON.put("target", target.getSUID() + "");

							JSONObject edgesElement = new JSONObject();
							edgesElement.put("data", edgeJSON);

							edgesArray.put(edgesElement);
						}

						// Graph JSON
						JSONObject graphJSON = new JSONObject();
						graphJSON.put("edges", edgesArray);
						graphJSON.put("nodes", nodesArray);

						// METADATA
						JSONObject metadataJSON = new JSONObject();
						metadataJSON.put("description", "sample network");
						metadataJSON.put("name", "My Network");
						metadataJSON.put("tags", new JSONArray());

						// Add these to main network JSON
						networkJSON.put("graph", graphJSON);
						networkJSON.put("metadata", metadataJSON);

						PrintWriter printWriter = new PrintWriter(
								new OutputStreamWriter(new FileOutputStream(new File("network-output.json"))));
						printWriter.print(networkJSON.toString());
						System.out.println(networkJSON.toString());
						printWriter.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					try {
						//Set Proxy here if needed
//						Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.107", 3128));
//						Authenticator authenticator = new Authenticator() {
//							public PasswordAuthentication getPasswordAuthentication() {
//								return (new PasswordAuthentication("ipg_2014019", "fSmtSjqO".toCharArray()));
//							}
//						};
//						Authenticator.setDefault(authenticator);
						Process p = Runtime.getRuntime().exec(
								"curl -X POST http://graphspace.org/api/users/anurag30671371@gmail.com/graph/add/My-network/ -F username=anurag30671371@gmail.com -F password=dEP-9Cp-vzu-R4S -F graphname=@/users/anuragsharma/network-output.json");
					} catch (Exception e) {
						e.printStackTrace();
					}
	}
	
	public void run(TaskMonitor monitor) {
		// Create an empty network
		myNet = cnf.createNetwork();
		myNet.getRow(myNet).set(CyNetwork.NAME,
				      namingUtil.getSuggestedNetworkTitle("My Network"));
		
		// Add two nodes to the network
		CyNode node1 = myNet.addNode();
		CyNode node2 = myNet.addNode();
		
		// set name for new nodes
		myNet.getDefaultNodeTable().getRow(node1.getSUID()).set("name", "Node1");
		myNet.getDefaultNodeTable().getRow(node2.getSUID()).set("name", "Node2");
		
		// Add an edge
		myNet.addEdge(node1, node2, true);
				
		netMgr.addNetwork(myNet);
		
		
		
		// Set the variable destroyNetwork to true, the following code will destroy a network
		boolean destroyNetwork = false;
		if (destroyNetwork){
			// Destroy it
			 netMgr.destroyNetwork(myNet);			
		}
	}
}

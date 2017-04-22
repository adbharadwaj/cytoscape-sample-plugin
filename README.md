# Introduction
This app posts a graph to graphspace using REST API and save the network locally in JSON format.

<img src="docs/Screen Shot 2017-03-26 at 11.29.54 AM.png">

# NOTE
GraphSpace Java library based on new REST API [link](https://github.com/anu0012/graphspace-java-client)

# How to use
1. Cytoscape requires Java so ensures that Java is installed in your system.

2. Download cytoscape from [here](http://www.cytoscape.org/download.php)

3. Download Jar file from [here](https://github.com/anu0012/cytoscape-sample-plugin/blob/master/target/sample-create-network-1.0.jar)

**Note** - First you need to change the username and password in CreateNetworkTask.java file. You can also change the location where you want to save the json file and accordingly make changes in the Process instance in CreateNetworkTask.java file.
After making the required changes install the project from maven using eclipse to get the .jar file. For more instructions click [here](http://wiki.cytoscape.org/Cytoscape_3/AppDeveloper/Cytoscape_App_Ladder/BuildAndRunSampleApp)

4. Then navigate to the folder /Users/[username]/CytoscapeConfiguration/3/apps/installed and paste the downloaded jar file there.

5. Then open the cytoscape app using **sh /Applications/Cytoscape_v3.4.0/cytoscape.sh** in terminal and click on Apps in the menu bar and select **Create Network**

<img src="docs/Screen Shot 2017-03-26 at 11.31.38 AM.png">

<img src="docs/Screen Shot 2017-03-26 at 11.30.50 AM.png">

6. After this click on **Create view** and after few seconds Graph will be loaded.

7. Then you can create nodes and edges in your graph by right clicking and selecting the menu.

8. When you are done with your graph right click on a **node** and select **Save and Upload to Graphspace** on apps in the menu bar.

<img src="docs/Screen Shot 2017-03-26 at 11.29.00 AM.png">

9. This will upload the graph on graphspace and save the graph locally in a file named **network-output.json**

<img src="docs/Screen Shot 2017-03-26 at 11.29.22 AM.png">

<img src="docs/Screen Shot 2017-03-26 at 11.32.10 AM.png">

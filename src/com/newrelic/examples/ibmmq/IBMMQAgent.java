package com.newrelic.examples.ibmmq;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

//import com.ibm.disthub2.impl.client.Logger;
import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.configuration.ConfigurationException;
import com.newrelic.metrics.publish.processors.EpochCounter;
import com.newrelic.metrics.publish.processors.Processor;
import com.newrelic.metrics.publish.util.Logger;

public class IBMMQAgent extends Agent {

	private static final String GUID = "com.newrelic.examples.ibmmq";
    private static final String VERSION = "2.0.0";

    private com.ibm.mq.MQQueue destQueue;
    private String host;
    private int port;
    private String channel;
    private String user;
    private String password;
    private String queuemanager;
    private String queue;

    private String name;
    private static final Logger logger = Logger.getLogger(IBMMQAgent.class);
    
    public IBMMQAgent(String name, String host, int port, String channel, String user, String password, String queuemanager, String queue) 
	{
		super(GUID, VERSION);
		
		this.host = host;
		this.queuemanager = queuemanager;
		this.queue = queue;
		this.name = name;
		this.port = port;
		this.user = user;
		this.password = password;
		this.channel = channel;
				
	}

    
    @Override
    public void pollCycle() 
    {
		MQEnvironment.hostname = host;
		MQEnvironment.port = port;
		MQEnvironment.channel = channel;
		MQEnvironment.userID = user;
		MQEnvironment.password = password;
		
		try
		{
			//MQQueueManager mqm = new MQQueueManager(queuemanager);
			//String queuemanagername = queuemanager;
			//String mqQueue = queue;

			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
			MQQueueManager qMgr = new MQQueueManager(queuemanager);
			int openOptions = MQC.MQOO_INQUIRE;
			destQueue = qMgr.accessQueue(queue, openOptions);		

    		int queuedepth = this.getQueueDepth();
    		int openinputcount = this.getOpenInputCount();
    		int openoutputcount = this.getOpenOutputCount();
    		float percentagefull = this.getPercentageFull();

    		logger.info("QueueDepth: " + queuedepth + " | OpenInputCount: " + openinputcount + " | OpenOutputCount: " + openoutputcount + " | PercentageFull: " + percentagefull);
    		reportMetric("IBMMQ/QueueDepth", "Queue Depth", queuedepth);
        	reportMetric("IBMMQ/OpenInputCount", "Open Input Count", openinputcount);
        	reportMetric("IBMMQ/OpenOutputCount", "Open Output Count", openoutputcount);
        	reportMetric("IBMMQ/PercentageFull", "Percentage Full", percentagefull);

        	if(destQueue.isOpen())
        	{
            	destQueue.close();        		
        	}
        	if(qMgr.isOpen())
        	{
        		qMgr.close();
        	}
		}catch(MQException mqe)
		{
			mqe.printStackTrace();
		}

        	
    }
    
    private float getPercentageFull() throws MQException
    {
    	float currentDepth = (float) destQueue.getCurrentDepth();
    	float maxDepth = (float) destQueue.getMaximumDepth();
    	float percentageFull = currentDepth/maxDepth * 100;
    	return percentageFull;
    }
    
    private int getQueueDepth() throws MQException
    {
    	return destQueue.getCurrentDepth();
    }

    private int getOpenInputCount() throws MQException
    {
    	return destQueue.getOpenInputCount();
    }

    
    private int getOpenOutputCount() throws MQException
    {
    	return destQueue.getOpenOutputCount();
    }

    //@Override
	public String getAgentName() 
    {
		// TODO Auto-generated method stub
		return this.name;
	}

    @Override
    public String getComponentHumanLabel() {
        return this.name;
    }

}

package com.newrelic.examples.ibmmq;

import java.util.Map;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.AgentFactory;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

public class IBMMQAgentFactory extends AgentFactory {

    @Override
    public Agent createConfiguredAgent(Map<String, Object> properties) throws ConfigurationException 
    {
    	String name = (String) properties.get("name");
        String host = (String) properties.get("host");
        String sPort = (String) properties.get("port");
        int port = Integer.parseInt(sPort);
        //Integer port = (Integer) properties.get("port");
        String channel = (String) properties.get("channel");
        String user = (String) properties.get("user");
        String password = (String) properties.get("password");
        String queuemanager = (String) properties.get("queuemanager");
        String queue = (String) properties.get("queue");

        return new IBMMQAgent(name, host, port, channel, user, password, queuemanager, queue);
    }
}

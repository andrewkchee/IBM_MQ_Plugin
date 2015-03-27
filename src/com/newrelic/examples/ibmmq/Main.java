package com.newrelic.examples.ibmmq;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import com.ibm.jms.*;
import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.jms.*;
import com.ibm.msg.client.wmq.WMQConstants;
import com.newrelic.metrics.publish.Runner;
import com.newrelic.metrics.publish.configuration.ConfigurationException;


public class Main 
{

    public static void main(String[] args) throws MQException 
    {
    	
        try 
        {
            Runner runner = new Runner();
            runner.add(new IBMMQAgentFactory());
            runner.setupAndRun(); // Never returns
        } catch (ConfigurationException e) 
        {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(-1);
        }
    }
}

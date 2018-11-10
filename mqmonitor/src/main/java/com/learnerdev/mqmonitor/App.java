package com.learnerdev.mqmonitor;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.DestinationViewMBean;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    	// connection
    	String url = "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi";
    	JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url));
    	MBeanServerConnection connection = connector.getMBeanServerConnection();
    	// get queue size
    	ObjectName nameConsumers = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=test.MyQueue");
    	DestinationViewMBean mbView = MBeanServerInvocationHandler.newProxyInstance(connection, nameConsumers, DestinationViewMBean.class, true);
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	long queueSize = mbView.getQueueSize();
    	System.out.println(queueSize);
    	}catch (IOException | MalformedObjectNameException e) {
			// TODO: handle exception
		}
    }
}

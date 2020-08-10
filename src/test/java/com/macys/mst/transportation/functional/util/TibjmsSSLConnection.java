package com.macys.mst.transportation.functional.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import com.macys.mst.artemis.config.FileConfig;
import org.apache.log4j.Logger;
import org.jruby.ir.operands.Array;
import org.junit.Assert;
import com.tibco.tibjms.TibjmsConnectionFactory;

public class TibjmsSSLConnection implements MessageListener {

	static Logger logger = Logger.getLogger(TibjmsSSLConnection.class.getName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static TibjmsConnectionFactory factory = null;
    public TextMessage m;
	private static String serverUrl = null;
	private static String userName = null;
	private static String password = null;
	private static String SSLPassword = null;
	private static String SSLCertificate = null;
	private static String SSLIdentity = null;
	public static Message messageFromQueue=null;

	
	public static synchronized TibjmsSSLConnection getinstance() {
		
		  serverUrl = FileConfig.getInstance().getStringConfigValue("ems.server.url");
		  userName = FileConfig.getInstance().getStringConfigValue("ems.server.username");
		  password = FileConfig.getInstance().getStringConfigValue("ems.server.pwdobjectid");
		  SSLPassword = FileConfig.getInstance().getStringConfigValue("ems.ssl.pwdobjectid");
		  SSLIdentity = FileConfig.getInstance().getStringConfigValue("ems.ssl.identityFilePath");
		  SSLCertificate = FileConfig.getInstance().getStringConfigValue("ems.ssl.certFilePath");
		  
		  logger.info("EMS Server URL : " + serverUrl);
		  logger.info("EMS Server Username : " +userName);
		  logger.info("EMS SSL Identity File : " +SSLIdentity);
		  logger.info("EMS SSL Certificate File : " +SSLCertificate);

		return new TibjmsSSLConnection();
		

	}
	
	public synchronized ConnectionFactory getConnectionFactory() {
		try {
		if(factory==null){//set it only if it is not already set.. otherwise... the client can also chose to set the connection factory
		 {      factory = new TibjmsConnectionFactory(serverUrl);
				factory.setUserName(userName);
				factory.setUserPassword(password);
				factory.setSSLEnableVerifyHost(false); //using TCP connection
                factory.setSSLEnableVerifyHostName(false);
                factory.setSSLTrace(true);
                factory.setSSLDebugTrace(false);
		
			}
		}}catch(Exception e) {
			Assert.fail("Error creating Connection Factory :"+e);
		}
		return factory;
	}	

	public void sendQueueMessage(String queueName,String messageStr, Map<String, String> propsMap) throws JMSException {
        long startTime = System.currentTimeMillis();
		TextMessage msg;
		MessageProducer msgProducer = null;
		Destination destination = null;
		Connection connection = null;
		Session session = null;
		try {
			logger.info("Publishing to  Queue destination '" + queueName  +"Start Time - "+ sdf.format(new Date())+"'\n");
            connection = getinstance().getConnectionFactory().createConnection();
			logger.info("Tibco Connection:"+ connection.toString());
			connection.start();
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);// create the session

			destination = session.createQueue(queueName);
			msgProducer = session.createProducer(null);

			/* create text message */
			msg = session.createTextMessage();
			if(propsMap != null){
				Set keys = propsMap.keySet();
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					msg.setStringProperty(key, propsMap.get(key).toString());
				}
				logger.info("Seeting jms properties completed");;
			}

			/* set message text */
			msg.setText(messageStr);

			/* publish message */
			msgProducer.send(destination, msg);
			logger.info("Published message: " + msg);
			
		} catch (JMSException e) {
			connection = null;
			e.printStackTrace();
			Assert.fail("Fail !! Connection When publishMessage to Queue"+e);
			throw e;
		}finally{
			if(session != null){
				try {
					session.close(); //close the session
				} catch (JMSException e1) {
					Assert.fail("Error closing session :"+e1);
				}
			}
			if(connection != null){
				try {
					connection.close(); //close the connection
				} catch (JMSException e1) {
					Assert.fail("Error closing Connection :"+e1);
				}
			}
		}
		long totalTimeTaken = System.currentTimeMillis() - startTime;
		logger.info("publishMessage to Queue Exit., destinationName- "+queueName+"Total Time Taken in Millis - "+ totalTimeTaken + ", End Time - " + sdf.format(new Date()));
	}

	public  void publishTopicMessage(String topicName, String messageStr, Map<String, String> propsMap) throws JMSException {
		long startTime = System.currentTimeMillis();
		TextMessage msg;
		MessageProducer msgProducer = null;
		Destination destination = null;
		Connection connection = null;
		Session session = null;
		try {
			logger.info("Publishing to  Topic destination '" + topicName  +"Start Time - "+ sdf.format(new Date())+"'\n");
            connection = getinstance().getConnectionFactory().createConnection();
			logger.info("Tibco Connection:"+ connection.toString());
			connection.start();
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);// create the session
			destination = session.createTopic(topicName);
			msgProducer = session.createProducer(null);

			/* create text message */
			msg = session.createTextMessage();
			if(propsMap != null){
				Set keys = propsMap.keySet();
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					msg.setStringProperty(key, propsMap.get(key).toString());
				}
				logger.info("Seeting jms properties completed");;
			}

			/* set message text */
			msg.setText(messageStr);

			/* publish message */
			msgProducer.send(destination, msg);

			logger.info("Published message: " + msg);
		}catch (JMSException e) {
			connection = null;
			e.printStackTrace();
			Assert.fail("Fail !! Connection When publishMessage to Topic"+e);
			throw e;
		}finally{
			if(session != null){
				try {
					session.close(); //close the session
				} catch (JMSException e1) {
					Assert.fail("Error closing session :"+e1);
				}
			}
			if(connection != null){
				try {
					connection.close(); //close the connection
				} catch (JMSException e1) {
					Assert.fail("Error closing Connection :"+e1);
				}
			}
		}
		long totalTimeTaken = System.currentTimeMillis() - startTime;
		logger.info("publishMessage to Topic Exit., destinationName- "+topicName+"Total Time Taken in Millis - "+ totalTimeTaken + ", End Time - " + sdf.format(new Date()));
	}
	

	public List<Message> receiveMessageFromQueue(String QueueName, String selector) throws Exception {
		
		List<Message> listmessage= new ArrayList<Message>();
		Message tempMsg;
		long startTime = System.currentTimeMillis();
		logger.info("ReceiveMessageFromQueue Enter, receivingName- "+QueueName+"Start Time - "+ sdf.format(new Date()));
		Connection connection = null;
		Session session = null;
		MessageConsumer msgConsumer = null;
		Destination destination = null;
		Queue queue;
		try {
			connection = getinstance().getConnectionFactory().createConnection();
			logger.info("Queue Connection:"+ connection.toString());
			connection.start();
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);// create the session
			destination = session.createQueue(QueueName);
			// create the destination
		    msgConsumer = session.createConsumer(destination,selector);
		    int retry=1;
		    int count=3;
		    while(retry<=count){
		    			tempMsg = (Message) msgConsumer.receive();
		    			listmessage.add(tempMsg);
		   				retry++;
		   							
		    }
			//msgConsumer.setMessageListener(this);				
			Thread.sleep(10000);
			
		} catch (JMSException e) {
			connection = null;
			e.printStackTrace();
			Assert.fail("Fail !! Connection When Receive Message From Queue"+e);
			throw e;
		}finally{
			if(session != null){
				try {
					session.close(); //close the session
				} catch (JMSException e1) {
					Assert.fail("Error closing session :"+e1);
				}
			}
			if(connection != null){
				try {
					connection.close(); //close the connection
				} catch (JMSException e1) {
					Assert.fail("Error closing Connection :"+e1);
				}
			}
		}
		long totalTimeTaken = System.currentTimeMillis() - startTime;
		logger.info("ReceiveMessageFromQueue Exit.,receivingName- "+QueueName+"Total Time Taken in Millis - "+ totalTimeTaken + ", End Time - " + sdf.format(new Date()));
		
		return listmessage;
	}


	@Override
	public void onMessage(Message message) {
		try {
			logger.info("Message from Queue :"+message);
			message.getJMSMessageID();
			message.getJMSDestination();
			message.getJMSTimestamp();
			messageFromQueue=message;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Fail!! :"+e.getMessage());
		}

	}

	public  String subscribeTopicMessage(String topicName) throws JMSException {
		Connection connection = null;
		Session session = null;
		TopicSubscriber topicSubscriber = null;
		Topic destination = null;
		String messageText = null;
		try {
			connection = getinstance().getConnectionFactory().createConnection();
			logger.info("Topic Connection:"+ connection.toString());
			connection.start();
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);// create the session
		    destination = session.createTopic(topicName);
			//destination=topicName;
			topicSubscriber = session.createDurableSubscriber(destination,"ReceiveMessages");
			while (true) {
				TextMessage message = (TextMessage) topicSubscriber
						.receive(120000);
				if (message != null) {
					logger.info("Received message: " + message.getText());
					messageText = message.getText();}
				
				else
					logger.info("No Received message: ");
				break;
			}

		} catch (JMSException e) {
			connection = null;
			e.printStackTrace();
			Assert.fail("Fail !! Connection When receiving Message from Topic"+e);
			throw e;
		}finally{
			if(session != null){
				try {
					session.close(); //close the session
				} catch (JMSException e1) {
					Assert.fail("Error closing session :"+e1);
				}
			}
			if(connection != null){
				try {
					connection.close(); //close the connection
				} catch (JMSException e1) {
					Assert.fail("Error closing Connection :"+e1);
				}
			}
		}
		
		logger.info("ReceiveMessage from Topic Exit., destinationName- "+topicName);
		return messageText;
	}

}

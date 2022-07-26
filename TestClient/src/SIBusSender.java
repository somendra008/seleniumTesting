import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnectionFactory;

import com.ibm.websphere.sib.api.jms.JmsConnectionFactory;
import com.ibm.websphere.sib.api.jms.JmsFactoryFactory;
import com.ibm.websphere.sib.api.jms.JmsQueue;
import com.ibm.websphere.sib.api.jms.JmsTopic;
import com.nsn.oss.fm.event.model.AlarmingObjectLiteEvent;
import com.nsn.oss.fm.event.model.AlarmingObjectLiteInfo;
import com.nsn.oss.fm.event.model.Event;
import com.nsn.oss.fm.model.AlarmingObjectBasicImpl;
import com.nsn.oss.fm.model.AlarmingObjectLite;
import com.nsn.oss.fm.model.AlarmingObjectLiteImpl;
import com.nsn.oss.fm.model.metadata.AdaptationDefKey;
import com.nsn.oss.fm.model.metadata.ObjectClassDefKey;

/**
 * Sample code to programmatically create a connection to a bus and
 * send a text message.
 * 
 * Example command lines:
 *   SIBusSender topic://my/topic?topicSpace=Default.Topic.Space MyBus localhost:7274
 *   SIBusSender queue://myQueue MyBus localhost:7283:BootstrapSecureMessaging InboundSecureMessaging 
 */
public class SIBusSender {
  
  /**
   * @param args DEST_URL,BUS_NAME,PROVIDER_ENDPOINTS,[TRANSPORT_CHAIN]
   */
  public static void main(String[] args) throws JMSException, IOException {
    
	// Parse the arguments
    if (args.length < 3) {
      throw new IllegalArgumentException(
          "Usage: SIBusSender <DEST_URL> <BUS_NAME> <PROVIDER_ENDPOINTS> [TARGET_TRANSPORT_CHAIN]");
    }    
    String destUrl = args[0];
    String busName = args[1];
    String providerEndpoints = args[2];    
    String targetTransportChain = "InboundSecureMessaging";
	// System.setProperty("com.ibm.ws.sib.client.ssl.properties","c:/tls1.3/ssl.client.props");
	//System.setProperty("jdk.tls.client.protocols","TLSv1.3");
	/*System.setProperty("jdk.tls.client.protocols","TLSv1.3");
	System.setProperty("https.protocols","TLSv1.3");
	System.setProperty("jdk.tls.server.protocols","TLSv1.3");*/
	System.setProperty("javax.net.ssl.trustStorePassword","password");
	System.setProperty("javax.net.ssl.keyStorePassword","password");
	System.setProperty("javax.net.ssl.keyStore","C:\\Users\\kesari\\Desktop\\Seetharaman\\MWSKeyFile.jks");
	System.setProperty("javax.net.ssl.trustStore","C:\\Users\\kesari\\Desktop\\Seetharaman\\MWSTrustFile.jks");
	// System.setProperty("javax.net.debug","all");
	//System.setProperty("com.ibm.ejs.ras.lite.traceSpecification","SSL=all:SIBCommunications=all:SIBTrm=all:WLM*=all:SIBJms*=all:SIBJFapChannel=all:SIBMessageTrace=all:SIBJmsRa=all:SIBRa=all:TCPChannel=fine:com.ibm.io.async.*=all");
	//System.setProperty("com.ibm.ejs.ras.lite.traceFileName","test.log");
	//System.setProperty("com.ibm.ejs.ras.lite.maxFileSize","20");
	//System.setProperty("com.ibm.ejs.ras.lite.maxFiles","8");

    if (args.length >= 4) targetTransportChain = args[3];
    
    // Obtain the factory factory
    JmsFactoryFactory jmsFact = JmsFactoryFactory.getInstance();


    // Create a JMS destination
    Destination dest;
    if (destUrl.startsWith("topic://")) {
      JmsTopic topic = jmsFact.createTopic(destUrl);
      // Setter methods could be called here to configure the topic
      dest = topic ;
    }
    else {
      JmsQueue queue = jmsFact.createQueue(destUrl);
      // Setter methods could be called here to configure the queue
      dest = queue;
    }
        
    // Create a unified JMS connection factory
    JmsConnectionFactory connFact = jmsFact.createConnectionFactory();
    
    // Configure the connection factory
    connFact.setBusName(busName);
    connFact.setProviderEndpoints(providerEndpoints);
    connFact.setTargetTransportChain(targetTransportChain);
    
    // Create the connection
    Connection conn = connFact.createConnection();
    
    Session session = null;
    MessageProducer producer = null;
  //  try {
      
      // Create a session
    //  session = conn.createSession(false, // Not transactional 
                              //     Session.AUTO_ACKNOWLEDGE);
      
      // Create a message producer
      //producer = session.createProducer(dest);
      
      // Loop reading lines of text from the console to send
     // System.out.println("Ready to send to " + dest + " on bus " + busName);
      
      //List<AlarmingObjectLite> alarmingObjectLites = new ArrayList<AlarmingObjectLite>();
      //String dn = "PLMN-1/BSC-1";
      //long gid = 1L;
      //AlarmingObjectLiteImpl agent = createAlarmingObjectLite(dn, gid, 0);
      //alarmingObjectLites.add(agent);

     // ObjectMessage message = session.createObjectMessage(); 
     // Collection<AlarmingObjectLiteInfo> alarmingObjectInfos = new ArrayList<AlarmingObjectLiteInfo>();
      //for (AlarmingObjectLite alarmingObjectLite : alarmingObjectLites) {
     //     alarmingObjectInfos.add(new AlarmingObjectLiteInfo(
    //             alarmingObjectLite));
     // }
      
    // for (int i=0; i <= 101; i++)
     // {
        
        // Create a text message containing the line
     //   Event event = new AlarmingObjectLiteEvent(alarmingObjectInfos);
     //   message.setObject(event);  //setText("this is a test message");
	 //  System.out.println("message sent " + new Integer(i).toString());
        
	  //  System.out.println(message.toString());
        // Send the message
      //  producer.send(message,
       //               Message.DEFAULT_DELIVERY_MODE,
       //               Message.DEFAULT_PRIORITY,
       //               Message.DEFAULT_TIME_TO_LIVE);        
        
     // }
      
    //}//try
      
      try {
          
          // Create a session
          session = conn.createSession(false, // Not transactional 
                                       Session.AUTO_ACKNOWLEDGE);
          
          // Create a message producer
          producer = session.createProducer(dest);
          
          // Loop reading lines of text from the console to send
          System.out.println("Ready to send to " + dest + " on bus " + busName);
          for (int i=0; i <= 101; i++)
          {
            
            // Create a text message containing the line
            TextMessage message = session.createTextMessage();
            message.setText("this is a test message");
    	    System.out.println("message sent " + new Integer(i).toString());
            
            // Send the message
            producer.send(message,
                          Message.DEFAULT_DELIVERY_MODE,
                          Message.DEFAULT_PRIORITY,
                          Message.DEFAULT_TIME_TO_LIVE);        
            
          }
          
        }
      
    
    // Finally block to ensure we close our JMS objects 
    finally {
      
      // Close the message producer
      try {
        if (producer != null) producer.close();
      }
      catch (JMSException e) {
        System.err.println("Failed to close message producer: " + e);
      }
      
      // Close the session
      try {
        if (session != null) session.close();
      }
      catch (JMSException e) {
        System.err.println("Failed to close session: " + e);
      }
      
      // Close the connection
      try {
        conn.close();
      }
      catch (JMSException e) {
        System.err.println("Failed to close connection: " + e);
      }
      
    }
  }

  private static AlarmingObjectLiteImpl createAlarmingObjectLite(String dn,
          long gid, int maMoState) {
      AlarmingObjectLiteImpl alarmingObjectLite = new AlarmingObjectLiteImpl();
      String classAbbreviation = dn.substring(dn.lastIndexOf('-') + 1);
		
		 ObjectClassDefKey objectClassDefKey = ObjectClassDefKey.getKey(
		 AdaptationDefKey.getKey("adaptationId", "adaptationReleaseId"),
		 classAbbreviation);
		  alarmingObjectLite.setObjectClassDefKey(objectClassDefKey);
		
      AlarmingObjectBasicImpl alarmingObjectBasic = new AlarmingObjectBasicImpl();
      alarmingObjectBasic.setId(gid);
      alarmingObjectBasic.setDN(dn);
      alarmingObjectBasic.setMaMoState(maMoState);
      alarmingObjectLite.setAlarmingObjectBasic(alarmingObjectBasic);
      return alarmingObjectLite;
  }

}
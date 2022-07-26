import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.nsn.oss.fm.ejb.data.JMSNotificationProperties;
import com.nsn.oss.fm.event.model.AlarmingObjectLiteEvent;
import com.nsn.oss.fm.event.model.AlarmingObjectLiteInfo;
import com.nsn.oss.fm.event.model.Event;
import com.nsn.oss.fm.model.AlarmingObjectBasicImpl;
import com.nsn.oss.fm.model.AlarmingObjectLite;
import com.nsn.oss.fm.model.AlarmingObjectLiteImpl;
import com.nsn.oss.fm.model.metadata.AdaptationDefKey;
import com.nsn.oss.fm.model.metadata.ObjectClassDefKey;
//import com.nsn.oss.fm.ejb.data.ObjectOperationType;


public class FMSIBusSender {

    private static final int MESSAGE_PRIORITY = 0;

	/*
	 * private static final Logger LOGGER = Logger
	 * .getLogger(JMSPublisher.class.getName());
	 */
    private static final String TOPIC_CONNECTION_FACTORY = "jms/FM.SERVER.TCF";

    /**
     * Publishes the specified <code>Serializable</code> object to the
     * specified topic along with the specified properties.
     * 
     * @param topicName
     *            the topic to which message needs to be published
     * @param properties
     *            the properties that need to be published
     * @param objectToPublish
     *            the object that need to be published
     */
    
    public static void main(String[] args) throws JMSException, IOException {
    
    	 TopicConnection topicConnection = null;
         TopicSession topicSession = null;
         TopicPublisher topicPublisher = null;
         Context context = null;
         try {
             context = createContext();
             topicConnection = getTopicConnection(context);
             topicSession = getTopicSession(topicConnection);
             topicPublisher = topicSession.createPublisher((Topic) context
                     .lookup("jms/MOFDMONotificationTopic"));
             ObjectMessage objectMessage = topicSession
                     .createObjectMessage();
             
             List<AlarmingObjectLite> alarmingObjectLites = new ArrayList<AlarmingObjectLite>();
             String dn = "PLMN-1/BSC-1";
             long gid = 1L;
             AlarmingObjectLiteImpl agent = createAlarmingObjectLite(dn, gid, 0);
             alarmingObjectLites.add(agent);
             Collection<AlarmingObjectLiteInfo> alarmingObjectInfos = new ArrayList<AlarmingObjectLiteInfo>();
             for (AlarmingObjectLite alarmingObjectLite : alarmingObjectLites) {
                 alarmingObjectInfos.add(new AlarmingObjectLiteInfo(
                         alarmingObjectLite));
             }
             
             Map<String, Object> properties = null;
             properties.put(JMSNotificationProperties.OBJECT_TYPE, "fm");
             properties.put(JMSNotificationProperties.OPERATION_TYPE, 3);
             properties.put(JMSNotificationProperties.NUM_OF_OBJECTS, alarmingObjectInfos.size());
             for (int i=0; i <= 101; i++)
             {
             Event objectToPublish = new AlarmingObjectLiteEvent(alarmingObjectInfos);
             objectMessage.setObject(objectToPublish);
             System.out.println("message sent " + new Integer(i).toString());
             populateProperties(objectMessage, properties);
             }
             topicPublisher.publish(objectMessage,DeliveryMode.NON_PERSISTENT, MESSAGE_PRIORITY,Message.DEFAULT_TIME_TO_LIVE);
            // LOGGER.log(Level.FINEST, "published message with TTL as {0} miliseconds",timeToLive);
         } catch (NamingException namingException) {
             throw logAndCreateIllegalStateException(namingException,
                     namingException.getMessage());
         } catch (JMSException jmsException) {
             throw logAndCreateIllegalStateException(jmsException,
                     jmsException.getMessage());
         } finally {
             closeResources(context, topicConnection, topicSession,
                     topicPublisher);
         }
    	
    	
    }


    private static void populateProperties(ObjectMessage objectMessage,
            Map<String, Object> properties) throws JMSException {
        for (Entry<String, Object> property : properties.entrySet()) {
            Object propertyValue = property.getValue();
            Class<?> clazz = propertyValue.getClass();
            String propertyName = property.getKey();
            if (clazz.equals(Integer.class)) {
                objectMessage.setIntProperty(propertyName,
                        (Integer) propertyValue);
            } else if (clazz.equals(String.class)) {
                objectMessage.setStringProperty(propertyName,
                        (String) propertyValue);
            } else {
                handleInvalidProperty(clazz);
            }
        }
    }

    private static void handleInvalidProperty(Class<?> clazz) {
        String msg = "Setting " + clazz.getName()
                + " property is not supported";
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(
                msg);
       // LOGGER.log(Level.SEVERE, msg, illegalArgumentException);
        throw illegalArgumentException;
    }

    private static void closeResources(Context context,
            TopicConnection topicConnection, TopicSession topicSession,
            TopicPublisher topicPublisher) {
        try {
            if (topicPublisher != null) {
                topicPublisher.close();
            }
            if (topicSession != null) {
                topicSession.close();
            }
            if (topicConnection != null) {
                topicConnection.close();
            }
            if (context != null) {
                context.close();
            }
        } catch (JMSException jmsException) {
            throw logAndCreateIllegalStateException(jmsException,
                    jmsException.getMessage());
        } catch (NamingException namingException) {
            throw logAndCreateIllegalStateException(namingException,
                    namingException.getMessage());
        }
    }

    private static TopicConnection getTopicConnection(Context context)
            throws JMSException, NamingException {
        TopicConnectionFactory factory = (TopicConnectionFactory) PortableRemoteObject
                .narrow(context.lookup(TOPIC_CONNECTION_FACTORY),
                        TopicConnectionFactory.class);
        return factory.createTopicConnection();
    }

    private static TopicSession getTopicSession(TopicConnection notesJMSConnection)
            throws JMSException {
        return notesJMSConnection.createTopicSession(false,
                Session.AUTO_ACKNOWLEDGE);
    }

    private static IllegalStateException logAndCreateIllegalStateException(
            Exception exceptionToLog, String message) {
        //LOGGER.log(Level.SEVERE, message, exceptionToLog);
        return new IllegalStateException(message, exceptionToLog);
    }

    /**
     * Creates the naming context used for lookup.
     * 
     * @return context to do lookup.
     * @throws NamingException
     *             if a naming exception is encountered
     */
    protected static Context createContext() throws NamingException {
        return new InitialContext();
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

package com.j2ee.admin.utils;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by laizhiyuan on 2017/8/8.
 */
public class EjbHelper {

    /*发送JMS消息*/
    public static synchronized void sendJmsMessage(String destinationStr, String message){
        String connectionFactoryString = System.getProperty("connection.factory", "java:/ConnectionFactory");
        ConnectionFactory connectionFactory = (ConnectionFactory) localByJndi(connectionFactoryString);
        try {
            Connection connection = connectionFactory.createConnection(
                    System.getProperty("username", "admin"), System.getProperty("password", "123456"));

            String destinationString = System.getProperty("destination", destinationStr);
            Destination destination = (Destination) localByJndi(destinationString);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText(message);
            MessageProducer  producer = session.createProducer(destination);

            producer.send(textMessage);

            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /*拼接出无状态会话Bean的JNDI*/
    public static synchronized String getJndi(Class interfaceClazz, String beanName){
        String appName = "";
        String moduleName = "server";
        String distinctName = "";
        String viewClassName = interfaceClazz.getName();

        String jndi = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;

        System.out.println("======================>JNDI: " + jndi);
        return jndi;
    }

    /*拼接出有状态会话Bean的JNDI*/
    public static synchronized String getStatefulJndi(Class interfaceClazz, String beanName){
        String appName = "";
        String moduleName = "server";
        String distinctName = "";
        String viewClassName = interfaceClazz.getName();

        String jndi = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful";

        System.out.println("======================>JNDI: " + jndi);
        return jndi;
    }

    /*在使用同一个JVM情况下，通过JNDI获取实例*/
    public static synchronized Object localByJndi(String jndi){
        Context context;
        Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            context = new InitialContext(jndiProperties);
            context.close();
            return context.lookup(jndi);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*在使用不同的JVM情况下，通过JNDI获取实例*/
    public static synchronized Object remoteByJndi(String jndi){
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.SECURITY_PRINCIPAL, "admin");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "123456");
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProps.put(Context.PROVIDER_URL, "remote://127.0.0.1:4447");
        jndiProps.put("jboss.naming.client.ejb.context", true);

        try {
            InitialContext ctx = new InitialContext(jndiProps);
            System.out.println("-------------" + jndi);
            Object obj = ctx.lookup(jndi);
            ctx.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

package jms;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProducerTopic {

	public static void main(String[] args) throws NamingException, JMSException {
		Properties properties = new Properties();
		
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");
		
		InitialContext initialContext = new InitialContext(properties);
		
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();
		conexao.start();
		
		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topic = (Destination) initialContext.lookup("loja");
		MessageProducer producer = session.createProducer(topic);

		Message message = session.createTextMessage("<pedido><id>333</id></pedido>");
		producer.send(message);
		
		new Scanner(System.in).nextLine();
		
		session.close();
		conexao.close();
		initialContext.close();
	}
}

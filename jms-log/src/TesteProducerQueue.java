

import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProducerQueue {

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
		Destination fila = (Destination) initialContext.lookup("LOG");
		MessageProducer producer = session.createProducer(fila);

		Message message = session.createTextMessage("INFO | .....");
		producer.send(message,DeliveryMode.NON_PERSISTENT,9,50000);
		
		new Scanner(System.in).nextLine();
		
		session.close();
		conexao.close();
		initialContext.close();
	}
}

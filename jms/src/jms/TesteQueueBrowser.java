package jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteQueueBrowser {
	
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();
		conexao.start();
		
		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) initialContext.lookup("financeiro");
		QueueBrowser browser = session.createBrowser((Queue) fila);
		
		Enumeration mgs = browser.getEnumeration();
		while(mgs.hasMoreElements()) {
			TextMessage msg = (TextMessage) mgs.nextElement();
			System.out.println("Message: " + msg.getText());
		}
	}

}

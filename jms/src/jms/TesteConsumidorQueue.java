package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidorQueue {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();
		conexao.start();
		
		Session session = conexao.createSession(true, Session.SESSION_TRANSACTED);
		Destination fila = (Destination) initialContext.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					message.acknowledge();
					TextMessage textMessage = (TextMessage) message;
					System.out.println(textMessage.getText());
					session.commit();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		new Scanner(System.in).nextLine();
		
		session.close();
		conexao.close();
		initialContext.close();
	}
}

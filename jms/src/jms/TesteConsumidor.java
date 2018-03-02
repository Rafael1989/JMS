package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidor {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();
		conexao.start();
		
		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) initialContext.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		Message message = consumer.receive(10000);
		System.out.println("Recebendo msg: " + message);
		
		session.close();
		
		
		new Scanner(System.in).nextLine();
		
		conexao.close();
		initialContext.close();
	}
}

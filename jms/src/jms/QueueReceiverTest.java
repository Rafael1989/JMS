package jms;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueReceiverTest {
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext ctx = new InitialContext();
		QueueConnectionFactory cf = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
		QueueConnection conexao = cf.createQueueConnection();
		conexao.start();
		
		QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue fila = (Queue) ctx.lookup("financeiro");
		QueueReceiver receiver = sessao.createReceiver(fila);
		
		Message message = receiver.receive();
		System.out.println(message);
		
		new Scanner(System.in).nextLine();
		
		sessao.close();
		conexao.close();
		ctx.close();
	}
}

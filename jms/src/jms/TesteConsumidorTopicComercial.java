package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopicComercial {

	public static void main(String[] args) throws NamingException, JMSException {
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");//CONFIGURAR QUAIS PACOTES SÃO SERIALIZES * = TODOS
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();
		conexao.setClientID("comercial");
		conexao.start();
		
		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = (Topic) initialContext.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topic, "assinatura");
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido.getCodigo());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
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

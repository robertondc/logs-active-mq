import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {
	
	public static void main(String[] args) throws Exception{
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("LOG");
		MessageProducer producer = session.createProducer(fila);
		
		Message message = session.createTextMessage("INFO | failed: java.io.EOFException");
		producer.send(message, DeliveryMode.PERSISTENT,3, 80000);

		Message message2 = session.createTextMessage("WARN | failed: java.io.EOFException");
		producer.send(message2, DeliveryMode.NON_PERSISTENT,5, 80000);

		Message message3 = session.createTextMessage("ERROR | failed: java.io.EOFException");
		producer.send(message3, DeliveryMode.NON_PERSISTENT,9, 80000);

		session.close();
		connection.close();
		context.close();
		
		
	}
	
}

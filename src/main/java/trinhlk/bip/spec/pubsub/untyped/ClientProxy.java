package trinhlk.bip.spec.pubsub.untyped;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.BIPActor;
import trinhlk.bip.api.BIPActorAware;
import trinhlk.bip.api.DataOut.AccessType;
import trinhlk.bip.api.PortType;

@Ports({ @Port(name = "addTopic", type = PortType.spontaneous), 
		 @Port(name = "removeTopic", type = PortType.spontaneous),
		 @Port(name = "write", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.untyped.ClientProxy")
public class ClientProxy implements BIPActorAware {

	private PrintWriter output;
	private ArrayList<String> topics;
	private BIPActor bipActor;
	private long id;
	private Socket socket;
	public int noOfTransitions;

	public Socket getSocket() {
		return socket;
	}


	public ClientProxy(int id, OutputStream outputStream) {
		this.topics = new ArrayList<String>(0);
		this.output = new PrintWriter(outputStream, true);
		this.id = id;
	}

	public ClientProxy(int id2, ServerSocket tcpacceptor) throws IOException {
		this.topics = new ArrayList<String>(0);
		this.socket = tcpacceptor.accept();
		this.output = new PrintWriter(this.socket.getOutputStream(), true);
		this.id = id;
	}

	@Transition(name="write", source="0", target="0")
	public void write(@Data(name="msg") String msg) {
		output.println(msg);
		// System.out.printf("Client proxy %s receives messages %s", bipActor, msg);
	}
	
	@Transition(name="addTopic", source="0", target="0")
	public void addTopic(@Data(name="topic") String topic) {
		if (!this.topics.contains(topic)) {
			this.topics.add(topic);
			this.write("subscribe_ack epfl");
		}
		// System.out.printf("Client proxy %s subscribes to topic %s", bipActor, topic);
	}

	@Transition(name="removeTopic", source="0", target="0")
	public void removeTopic(@Data(name="topic") String topic) {
		if (this.topics.contains(topic)) {
			this.topics.remove(topic);
			this.write("unsubscribe_ack epfl");
		}
		// System.out.printf("Client proxy %s unsubscribes from topic %s", bipActor, topic);
	}

	public void setBIPActor(BIPActor actor) {
		this.bipActor = actor;
	}
	
	// TODO : This and BIPActorProxyAware may not be needed in this example.
	@Data(name="bipActor", accessTypePort=AccessType.any)
	public BIPActor getBIPActor() {
		return bipActor;
	}
	
}

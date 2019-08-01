package trinhlk.bip.spec.pubsub.typed;

import java.util.HashMap;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.BIPActor;
import trinhlk.bip.api.PortType;

@Ports({ @Port(name = "executeCommand", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.TopicManager")
public class TopicManager implements TopicManagerInterface {
	

	private static final HashMap<String, TopicInterface> topics = new HashMap<String, TopicInterface>();;

	public TopicManager(TopicInterface proxyForTopic1, TopicInterface proxyForTopic2) {
		topics.put("epfl", proxyForTopic1);
		topics.put("concurrence", proxyForTopic2);
    }
    
	@Transition(name = "executeCommand", source = "0", target = "0")
	public void executeCommand(@Data(name = "value") Command command) {
		// System.err.println("command " + command.getId() + " to execute for client " +
		// command.getClient());
		switch (command.getId()) {
        case SUBSCRIBE:
			// System.out.println("Executing subscribe");
            subscribe(command.getClient(),command.getTopic());
            break;
        case UNSUBSCRIBE:
			// System.out.println("Executing unsubscribe");
            unsubscribe(command.getClient(),command.getTopic());
            break;
        case PUBLISH:
			// System.out.println("Executing publish");
            publish(command.getClient(), command.getTopic(),command.getMessage());
            break;
        default:
            break;
        }
        
    }

	private void subscribe(ClientProxyInterface clientProxy, String topicName) {
    	
		TopicInterface topic = topics.get(topicName);
		// System.err.println("client: " + clientProxy + " topicName: " + topicName);
		topic.addClient(clientProxy);

        
    }
    
	private void unsubscribe(ClientProxyInterface clientProxy, String topicName) {

		TopicInterface topic = topics.get(topicName);
		// System.err.println("client: " + clientProxy + " topicName: " + topicName);
		topic.removeClient(clientProxy);

    }
         
	private void publish(ClientProxyInterface clientProxy, String topicName, String message) {

		TopicInterface topic = topics.get(topicName);
		// System.err.println("client: " + clientProxy + " topicName: " + topicName + "message " +
		// message);
		topic.publish(clientProxy, message);


    }


}
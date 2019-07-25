package trinhlk.bip.spec.pubsub.untyped;

import java.util.HashMap;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.BIPActor;
import trinhlk.bip.api.PortType;

@Ports({ @Port(name = "executeCommand", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.TopicManager")
public class TopicManager {
	
    private HashMap<String, BIPActor> topics;

    public TopicManager(HashMap<String, BIPActor> topics) {
    	this.topics = topics;
    }
    
    
    @Transition(name = "executeCommand", source = "0", target = "0")
	public void executeCommand(@Data(name = "command") Command command) {

		// System.out.printf("Execute command %s: ", command.getId());
        switch(command.getId()){
        case SUBSCRIBE:
            subscribe(command.getClient(),command.getTopic());
            break;
        case UNSUBSCRIBE:
            unsubscribe(command.getClient(),command.getTopic());
            break;
        case PUBLISH:
            publish(command.getClient(), command.getTopic(),command.getMessage());
            break;
        default:
            break;
        }
        
    }
	
	private void subscribe(BIPActor client, String topicName) {
    	
        BIPActor topic = topics.get(topicName);
        
        HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("client", client);
		topic.inform("addClient", data);
        
    }
    
	private void unsubscribe(BIPActor client, String topicName) {

		BIPActor topic = topics.get(topicName);

        HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("client", client);
		topic.inform("removeClient", data);

    }
         
	private void publish(BIPActor client, String topicName, String message) {

		BIPActor topic = topics.get(topicName);
		
        HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("client", client);
		data.put("msg", message);
		topic.inform("publish", data);

    }


}

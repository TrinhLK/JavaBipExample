package trinhlk.bip.spec.pubsub.untyped;

import java.util.HashMap;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.BIPActor;
import trinhlk.bip.api.PortType;

@Ports({ @Port(name = "handleCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.untyped.CommandHandler")
public class CommandHandler {
	
	private BIPActor topicManager;
	
	public CommandHandler(BIPActor topicManager) {
		this.topicManager = topicManager;
	}

	@Transition(name = "handleCommand", source = "0", target = "0")
	public void handleCommand(@Data(name = "command") Command command) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("command", command);
		topicManager.inform("executeCommand", data);	}

}

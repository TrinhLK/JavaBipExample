package trinhlk.bip.spec.pubsub.typed;

import java.util.HashMap;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.BIPActor;
import trinhlk.bip.api.PortType;

@Ports({ @Port(name = "handleCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.CommandHandler")
public class CommandHandler {
	
	private TopicManagerInterface topicManager;

	public CommandHandler(TopicManagerInterface proxyForManager) {
		this.topicManager = proxyForManager;
	}

	@Transition(name = "handleCommand", source = "0", target = "0")
	public void handleCommand(@Data(name = "command") Command command) {
		// System.out.println("Command Handler handling command " + command.getId() + " of topic "
		// + command.getTopic());
		topicManager.executeCommand(command);
	}
		
}


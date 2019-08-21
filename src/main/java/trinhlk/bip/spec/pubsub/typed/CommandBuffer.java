package trinhlk.bip.spec.pubsub.typed;

import java.util.LinkedList;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Guard;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.PortType;

@Ports({ @Port(name = "putCommand", type = PortType.enforceable),
	@Port(name = "getCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.CommandBuffer")
public class CommandBuffer {

private LinkedList<Command> commandList;

private int bufferSize;

public CommandBuffer(int bufferSize){
	this.bufferSize = bufferSize;
	this.commandList = new LinkedList<Command>();
}


@Transition(name = "getCommand", source = "0", target = "0", guard = "isBufferNotEmpty")
public void getCommandToHandler() {
	// System.out.println("CommandBuffer giving command to Handler");
	commandList.remove();
}

@Guard(name = "isBufferNotEmpty")
public boolean isBufferNotEmpty() {
	return !commandList.isEmpty();
}

@Transition(name = "putCommand", source = "0", target = "0", guard = "isBufferNotFull")
public void putCommandFromReader(@Data(name = "input") Command command) {
	// System.out.println("CommandBuffer accepting command");
	commandList.add(command);
}

@Guard(name = "isBufferNotFull")
public boolean isBufferNotFull() {
	return commandList.size() < bufferSize;

}

@Data(name = "command")
public Command getNextCommand() {
	return commandList.get(0);
}

}

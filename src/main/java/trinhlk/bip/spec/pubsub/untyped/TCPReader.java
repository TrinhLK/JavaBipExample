package trinhlk.bip.spec.pubsub.untyped;

import java.io.IOException;
import java.net.Socket;

import lsr.concurrence.provided.server.CommandID;
import lsr.concurrence.provided.server.InputFormatException;
import lsr.concurrence.provided.server.InputReader;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Guard;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.BIPActor;
import trinhlk.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "giveCommandToBuffer", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.untyped.TCPReader")
public class TCPReader {

	Logger logger = LoggerFactory.getLogger(TCPReader.class);
	
	private boolean connected;
	private BIPActor client;
	private Socket client_sock;
	private CommandBuffer command_buff;
	private InputReader reader;
	private Command currentCommand;

	private boolean stillHasCommands;
	private long id;
	
	public TCPReader(Socket socket, int i, CommandBuffer buffer, BIPActor proxyForClient1) throws IOException {

		this.client = proxyForClient1;
		this.id = id;
		this.command_buff = buffer;
		this.client_sock = socket;
		this.reader = new InputReader(this.client_sock.getInputStream());
		this.stillHasCommands = true;

	}


	@Transition(name = "giveCommandToBuffer", source = "0", target = "0", guard = "commandExists")
	public void giveCommandtoBuffer() {
		if (reader.getCommandId() == CommandID.ENDOFCLIENT)
			stillHasCommands = false;
	}

	@Data(name = "readerInput")
	public Command getNextCommand() throws InputFormatException, IOException {
		reader.readCommand();
		currentCommand = new Command(client, reader.getCommandId(), reader.getTopic(), reader.getMessage());
		// System.err.println("Client with id " + id + "received command: " +
		// currentCommand.getId());
		return currentCommand;
	}

	@Guard(name = "commandExists")
	public boolean commandExists() {
		return stillHasCommands;
	}

	
}
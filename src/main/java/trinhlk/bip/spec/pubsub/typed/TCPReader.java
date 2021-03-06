package trinhlk.bip.spec.pubsub.typed;

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
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.typed.TCPReader")
public class TCPReader {

	Logger logger = LoggerFactory.getLogger(TCPReader.class);
	
	private ClientProxyInterface cproxy;
	private Socket client_sock;
	private CommandBuffer command_buff;
	private InputReader reader;
	private Command currentCommand;
	private long id;

	private boolean connected;
	
	public TCPReader(Socket sock, long id, CommandBuffer buff, ClientProxyInterface proxyForClient1) throws IOException {
		this.cproxy = proxyForClient1;
		this.id = id;
		this.command_buff = buff;
		this.client_sock = sock;
		this.reader = new InputReader(this.client_sock.getInputStream());
		this.connected = true;

	}

	@Transition(name = "giveCommandToBuffer", source = "0", target = "0", guard = "commandExists")
	public void giveCommandtoBuffer() {
		// System.out.println("TCPReader giving command to buffer");
		 if (reader.getCommandId() == CommandID.ENDOFCLIENT){
			// System.err.println("Client with id " + id + "is terminating");
			connected = false;
		 }
	}

	@Data(name = "readerInput")
	public Command getNextCommand() throws InputFormatException, IOException {
		reader.readCommand();
		currentCommand = new Command(cproxy, reader.getCommandId(), reader.getTopic(), reader.getMessage());
		// System.err.println("Client with id " + id + "received command: " +
		// currentCommand.getId());
		return currentCommand;
	}

	@Guard(name = "commandExists")
	public boolean commandExists() {
		// System.out.println("Evaluation of guard commandExists: " + stillHasCommands +
		// " in client " + id);
		return connected;
	}

	
}

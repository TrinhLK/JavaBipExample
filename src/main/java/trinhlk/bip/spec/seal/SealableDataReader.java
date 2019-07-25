package trinhlk.bip.spec.seal;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "read", type = PortType.enforceable) })
@ComponentType(initial = "initial", name = "trinhlk.bip.spec.seal.SealableDataReader")
public class SealableDataReader<T> {

	Logger logger = LoggerFactory.getLogger(SealableDataReader.class);

	public int noOfTransitions = 0;

	@Transition(name = "read", source = "initial", target = "initial")
	public void read(@Data(name = "input") T data) {
		logger.debug("Read data {}", data);
		noOfTransitions++;
	}

}

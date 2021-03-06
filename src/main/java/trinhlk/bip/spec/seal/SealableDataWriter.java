package trinhlk.bip.spec.seal;

import trinhlk.bip.annotations.ComponentType;
import trinhlk.bip.annotations.Data;
import trinhlk.bip.annotations.Port;
import trinhlk.bip.annotations.Ports;
import trinhlk.bip.annotations.Transition;
import trinhlk.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "write", type = PortType.enforceable) })
@ComponentType(initial = "initial", name = "org.bip.spec.seal.SealableDataWriter")
public class SealableDataWriter<T> {

	Logger logger = LoggerFactory.getLogger(SealableData.class);

	private T data;
	public int noOfTransitions = 0;

	public SealableDataWriter(T data) {
		this.data = data;
	}

	@Data(name = "value")
	public T getData() {
		return data;
	}

	@Transition(name = "write", source = "initial", target = "written")
	public void write() {
		logger.debug("Writing data {}", this.data);
		noOfTransitions++;
	}

}

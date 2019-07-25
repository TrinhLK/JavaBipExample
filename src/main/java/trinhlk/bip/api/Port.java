package trinhlk.bip.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It specifies the name and the type of the port.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Port {
	
	/**
	 * It returns the name of the port.
	 *
	 * @return the name of the port.
	 */
	String name();
	
	/**
	 * It specifies the type of the port. It is either spontaneous or enforceable.
	 *
	 * @return the type of the port.
	 */
	PortType type(); 
	
}

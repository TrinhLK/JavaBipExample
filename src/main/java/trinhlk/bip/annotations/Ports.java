package trinhlk.bip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import trinhlk.bip.api.Port;

/**
 * It specifies/aggregates the ports for a given BIP specification.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Ports {
	
	/**
	 * The ports of the BIP specification.
	 *
	 * @return the array containing the ports of the BIP specification.
	 */
	Port[] value();
}

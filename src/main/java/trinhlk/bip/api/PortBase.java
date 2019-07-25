package trinhlk.bip.api;

/**
 * It specifies the functionality of the BIP component port.
 */

public interface PortBase {

	/**
	 * It specifies the id of the port.
	 * 
	 * @return the id
	 */
	public String getId();
	

	/**
	 * It returns the spec type to which this port belongs to. Often it is fully qualified name of class specifying the BIP specification.
	 * 
	 * @return the spec type
	 */
	public String getSpecType();
}

package trinhlk.bip.api;

/**
 * It specifies that a given BIP entity has an id that can be used to identify
 * it.
 */
public interface Identifiable {

	/**
	 * Gets the id of the entity so it can be used to identify it.
	 * 
	 * @return the id
	 */
	public String getId();

	public String getType();

}

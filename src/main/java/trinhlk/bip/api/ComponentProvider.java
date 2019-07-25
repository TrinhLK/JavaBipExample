package trinhlk.bip.api;

/**
 * ComponentProvider is used for example by the Port to be able to have information about the component instance it belongs to. In case, of
 * ExecutableBehaviour it is not possible to provide the component information right away because the ports are created without the
 * knowledge of the BIP component.
 */
public interface ComponentProvider {

	/**
	 * Gets the component.
	 * 
	 * @return the component
	 */
	public BIPComponent component();



}

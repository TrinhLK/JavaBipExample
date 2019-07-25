package trinhlk.bip.api;
/**
 * Interface used to specify the identity and type of the data required by transitions/guards.
 *
 * @param <T> the generic type
 */
public interface Data<T> {

	/**
	 * It returns the name of the data.
	 *
	 * @return the string
	 */
	public String name();

	/**
	 * It returns the type of the data.
	 *
	 * @return the class
	 */
	public Class<T> type();
	
}
package trinhlk.bip.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It annotates the function with the name of the guard.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Guard {
	
	/**
	 * It returns the name of the guard.
	 *
	 * @return the name of the guard.
	 */
	String name();
}

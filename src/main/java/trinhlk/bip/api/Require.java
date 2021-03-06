/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package trinhlk.bip.api;

import java.util.List;

/**
 * It specifies the requires within BIP Glue.
 */
public interface Require {

	/**
	 * Gets the effect.
	 * 
	 * @return the effect
	 */
	public PortBase getEffect();

	/**
	 * Gets the causes.
	 * 
	 * @return the causes
	 */
	public List<List<PortBase>> getCauses();

}

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

package trinhlk.bip.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It annotates the function within BIP specification to indicate that this function can be used to obtain 
 * an Executable Behavior for the BIP Specification. 
 * 
 * Function must return BehaviourBuilder class and require no parameters. 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutableBehaviour {

}
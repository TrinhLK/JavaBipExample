/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 01/27/14
 */

package trinhlk.bip.glue;

import javax.xml.bind.annotation.XmlAttribute;

import trinhlk.bip.api.PortBase;

class PortBaseImpl implements PortBase {

	@XmlAttribute
	protected String id;

	@XmlAttribute
	protected String specType;

	public String getId() {
		return id;
	}

	public String getSpecType() {
		return specType;
	}

	public PortBaseImpl(String id, String specificationType) {
		if (id == null) {
			throw new IllegalArgumentException(
					"Port id cannot be null for specification type "
							+ specificationType);
		}
		if (specificationType == null) {
				throw new IllegalArgumentException(
						"Port spec type cannot be null for port id " + id);
		}
		this.id = id;
		this.specType = specificationType;
	}

	public PortBaseImpl() {
	}
	
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (!(o instanceof PortBase)) {
			return false;
		}
		
		PortBase compareTo = (PortBase) o;
		
		if ( !this.getId().equals(compareTo.getId()))
			return false;

		if ( !this.getSpecType().equals(compareTo.getSpecType()))
			return false;
						
		return true;
	}

	
}

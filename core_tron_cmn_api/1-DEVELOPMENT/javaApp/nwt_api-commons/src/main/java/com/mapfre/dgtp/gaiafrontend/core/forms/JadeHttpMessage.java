/**
 * 
 */
package com.mapfre.dgtp.gaiafrontend.core.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode
public abstract class JadeHttpMessage implements Serializable {

	private static final long serialVersionUID = -2843100643347452533L;

	@Setter
	private String currentViewName;

	@JsonIgnore
	public String getCurrentViewName() {
		return currentViewName;
	}

}

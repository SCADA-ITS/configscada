package com.revenga.rits.back.front.core.model.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public abstract class BasicElementVO implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8053183638898261594L;

	public enum LogLevelEnum {

		CRITICAL, ERROR, WARN, INFO, DEBUG, TRACE
	}

	@Id
	protected String id;
	protected String alias;
	protected String description;
	protected String labelAlias;
	protected String labelDescription;
	protected Long createdAt;
	protected Long updatedAt;
}

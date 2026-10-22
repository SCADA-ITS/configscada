package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Entity
public class UserSessionVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5116344403997306080L;

	private String userId;
	private String sessionId;
	private Long loginSince;
	private Long validUntil;
	private String access;
}

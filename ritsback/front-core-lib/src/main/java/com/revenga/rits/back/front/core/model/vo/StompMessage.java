package com.revenga.rits.back.front.core.model.vo;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class StompMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4921618428814116194L;

	public enum Action {

		CREATE, UPDATE, DELETE, BLOCK
	}

	private Action action;

	private List<BasicElementVO> basicElementsVO = new ArrayList<>();

	@JsonIgnore
	private String customDestination;

	long timestamp;

	@JsonIgnore
	public StompMessage(StompMessage... messages) {

		if (!ArrayUtils.isEmpty(messages)) {

			for (StompMessage stompMessage : messages) {

				this.getBasicElementsVO().addAll(stompMessage.getBasicElementsVO());

				this.timestamp = Instant.now().toEpochMilli();
			}
		}
	}

	@JsonIgnore
	public BasicElementVO getFirst() {

		if (!CollectionUtils.isEmpty(basicElementsVO)) {

			return basicElementsVO.get(0);
		}

		return null;
	}
}

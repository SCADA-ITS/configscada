package com.revenga.rits.back.incident.manager.task.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PrimaryKeys(fields = { "id" })
public class SignallingCommandToStretch extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5111519945469291315L;


	private Integer from;
	private Integer to;
	private List<Integer> lanes;
	
	private String elementSubtypeId;
	private CommandElementVO command;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}

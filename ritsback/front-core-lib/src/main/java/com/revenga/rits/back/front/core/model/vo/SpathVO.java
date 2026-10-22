package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class SpathVO extends BasicElementVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3462557930171957215L;

	@EqualsAndHashCode.Exclude
	protected List<SpathStretchVO> spathStretchs;

}

package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import com.revenga.rits.back.data.core.model.Stretch;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class StretchLocationVO extends BasicElementVO{
	
	private static final long serialVersionUID = 176484379324904375L;

	private String stretchId;
	private Integer position;
	
	@EqualsAndHashCode.Exclude
	private Stretch strech;
	
}

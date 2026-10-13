package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.io.Serializable;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Stretch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StretchConfig implements Serializable, Comparable<StretchConfig> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3443889876898294651L;

	private String stretchId;
	private String alias;
	private Long order;
	private List<String> fansOrderId;
	private List<String> candidatesFansId;
	private List<FansByRange> fansToStartByRange;

	private Stretch stretch;
	private List<Element> fanOrder;
	private List<Element> candidatesFans;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
	
	@Override
	public int compareTo(StretchConfig stretchConfig) {

		return (stretchConfig == null || stretchConfig.getOrder() == null) ? 1
				: (this.order== null ? -1 : this.order.compareTo(stretchConfig.getOrder()));
	}
}

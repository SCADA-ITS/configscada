package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class CCTVScenarioNode extends BasicElement {

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6354124084582503830L;

	/**
	 * scenarioId Propertie
	 */
	private Long scenarioId;
	private Long managementAreaId;
		
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "scenarioId=id"})
	private CCTVScenario scenario;	
	
}

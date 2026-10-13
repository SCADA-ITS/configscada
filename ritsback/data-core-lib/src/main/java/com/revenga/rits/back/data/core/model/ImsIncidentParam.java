package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ImsIncidentParam extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5261371260286715099L;
	
	private Long incidentParamGroupId;
	private Long dataTypeId;
	private String threshold1;
	private String threshold2;
	private Long incidentParamUnitId;
	
	@EqualsAndHashCode.Exclude
	@AssociatedKeys(references = { "incidentParamUnitId=id" })
	private ElementTypeParamUnit incidentParamUnit;
	
	@EqualsAndHashCode.Exclude
	@AssociatedKeys(references = { "id=incidentParamId" })
	private List<ImsIncidentEnumParam> incidentEnumsParam;
	
}

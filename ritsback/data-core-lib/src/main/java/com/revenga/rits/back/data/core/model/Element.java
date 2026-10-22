package com.revenga.rits.back.data.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.CalculatedField;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * Defines element
 * 
 * @author aborrajo
 * 
 */
@Log4j2
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeys(fields = { "elementTypeId", "id" })
public class Element extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2153940426062267118L;

	private Long elementTypeId;
	private Long elementSubtypeId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeId=id" })
	private ElementType elementType;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementSubtypeId=id", "elementTypeId=elementTypeId" })
	private ElementSubtype elementSubtype;

	private String onChangeAlarm;
	private String onChangeState;
	private String onChangeMeasure;
	private String onSchedule;

	private Long locationId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "locationId=id" })
	private Location location;

	private Boolean keepValuesOnDisconnected;

	private Long inheritStateId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeId=elementTypeId", "id=elementId" })
	private List<ElementValue> elementValues;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<ElementHierarchy> parentElementHierarchies;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<ElementHierarchy> childElementHierarchies;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private ElementState elementState;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Alarm> alarms;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<ManagementAreaElement> managementAreaElement;

	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<AlarmFiltered> alarmsFiltered;
	
	@CalculatedField
	private long activeAlarms = 0L;
	@CalculatedField
	private long noRecognizeAlarms = 0L;
	@CalculatedField
	private long filteredAlarms = 0L;
	@CalculatedField
	private boolean forceEvaluateReceivedMeasures = false;
	@CalculatedField
	private long conflictSignalins = 0L;
	
	private Boolean measureChangeAll;

	private Long lastUpdatedState = 0L;

	public Element(Long elementTypeId, Long id) {
		super();
		this.elementTypeId = elementTypeId;
		this.id = id;
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	public Element cloneWithValues() {

		Element elementClone = null;
		try {
			
			elementClone = (Element) this.clone();
			if (CollectionUtils.isNotEmpty(this.elementValues)) {

				List<ElementValue> elementValuesClone = new ArrayList<>();
				this.elementValues.forEach(ev -> {
					
					try {
						
						elementValuesClone.add((ElementValue) ev.clone());
						
					} catch (CloneNotSupportedException e) {
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
					
				});
				
				elementClone.setElementValues(elementValuesClone);
				
			}
		} catch (CloneNotSupportedException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementClone;

	}

}

package com.revenga.rits.back.data.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.GenericData.ManagementAreaValue;

import lombok.Data;

@Data
public class GenericDataManager {

	private Map<Long, Long> newMap;
	private long newValue;
	
	private Map<Long, Long> currentMap;
	private long currentValue;
	
	private GenericData genericData;
	
	public  GenericDataManager(GenericData genericData) {

		if(genericData != null){
			
			this.genericData = genericData;
			this.currentValue = Long.parseLong(genericData.getValue());
			this.newValue = 0L;
			
			this.currentMap = new HashMap<>();
			this.newMap = new HashMap<>();
			
			if (!CollectionUtils.isEmpty(genericData.getManagementAreaValues())) {

				for (ManagementAreaValue managementAreaValue : genericData.getManagementAreaValues()) {

					this.currentMap.put(managementAreaValue.getManagementAreaId(),
							managementAreaValue.getValue() != null ? Long.parseLong(managementAreaValue.getValue())
									: 0L);
					
					this.newMap.put(managementAreaValue.getManagementAreaId(), 0L);
				}
			}
		}	
	}
	
	public GenericData updateGenericData() {

		GenericData genericDataToPublish = null;

		if (this.currentValue != this.newValue) {

			genericDataToPublish = new GenericData();
			genericDataToPublish.setId(this.genericData.getId());
			genericDataToPublish.setValue(String.valueOf(this.newValue));
			this.genericData.setValue(String.valueOf(this.newValue));
			
			this.currentValue = this.newValue;
		}
		this.newValue = 0L;

		
		if (CollectionUtils.isNotEmpty(this.genericData.getManagementAreaValues())) {

			for (ManagementAreaValue managementAreaValue : this.genericData.getManagementAreaValues()) {

				if (!this.currentMap.get(managementAreaValue.getManagementAreaId())
						.equals(this.newMap.get(managementAreaValue.getManagementAreaId()))) {

					String strValue = String.valueOf(this.newMap.get(managementAreaValue.getManagementAreaId()));
					
					if (genericDataToPublish == null) {

						genericDataToPublish = new GenericData();
						genericDataToPublish.setId(this.genericData.getId());
					}

					managementAreaValue.setValue(strValue);
					
					genericDataToPublish.addManagementAreaValue(managementAreaValue.getManagementAreaId(), strValue);
					
					this.getCurrentMap().put(managementAreaValue.getManagementAreaId(), this.newMap.get(managementAreaValue.getManagementAreaId()));
				}
				this.getNewMap().put(managementAreaValue.getManagementAreaId(), 0L);

			}

		}

		return genericDataToPublish;
	}
}
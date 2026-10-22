package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.GenericData;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetGenericData extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2673804513967573123L;

	private List<GenericData> genericDatas;
	
	public void addGenericData(GenericData genericData) {
		
		if (genericData != null) {
		
			if (genericDatas == null) {
				
				genericDatas = new ArrayList<>();
			}
			
			genericDatas.add(genericData);
		}
	}
	
	public SetGenericData() {

		super(Command.SET_GENERIC_DATA);
	}
}

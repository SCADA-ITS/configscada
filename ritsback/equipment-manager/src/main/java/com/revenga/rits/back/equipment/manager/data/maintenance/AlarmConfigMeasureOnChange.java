package com.revenga.rits.back.equipment.manager.data.maintenance;

import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

public class AlarmConfigMeasureOnChange extends EntityOnChange {

	public AlarmConfigMeasureOnChange() {
		
		super(AlarmConfigMeasure.class);
	}

	@Override
	protected void create() {
		
		EntitiesManager.getInstance().getRootData().fill(basicElement);
	}

	@Override
	protected void update() {

		EntitiesManager.getInstance().getRootData().fill(basicElement);
	}

	@Override
	protected void delete() {
		
		EntitiesManager.getInstance().getRootData().fill(basicElement);
	}
}

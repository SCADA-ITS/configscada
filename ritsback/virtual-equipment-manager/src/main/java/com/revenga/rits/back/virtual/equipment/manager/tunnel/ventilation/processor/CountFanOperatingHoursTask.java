package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor;

import java.time.Instant;
import java.util.List;
import java.util.TimerTask;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.virtual.equipment.manager.helper.SetElementMeasuresChangeCommandHelper;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CountFanOperatingHoursTask extends TimerTask {

	FanEntityManager fanEntityManager;
	Long lastRun = null;

	public CountFanOperatingHoursTask(FanEntityManager fanEntityManager) {

		this.fanEntityManager = fanEntityManager;
	}

	public void run() {

		log.info("EXECUTE COUNT FAN OPERAING HOURS TASK");

		Long now = Instant.now().toEpochMilli();
		Long time = lastRun != null ? now - lastRun : FanEntityManager.COUNT_FAN_OPERATING_HOURS_TASK_PERIOD;
		lastRun = now;

		List<Element> elements = this.fanEntityManager.updateOperatingHours(time);

		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = SetElementMeasuresChangeCommandHelper
				.get(elements);

		if (setElementMeasuresChangeCommand != null) {

			EntityManager.getInstance().sendCommand(setElementMeasuresChangeCommand);
		}
	}

}

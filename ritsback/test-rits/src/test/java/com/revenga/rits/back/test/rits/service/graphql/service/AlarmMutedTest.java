package com.revenga.rits.back.test.rits.service.graphql.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revenga.rits.back.data.core.model.AlarmMuted;
import com.revenga.rits.back.data.loader.service.CollectionManagerService;
import com.revenga.rits.back.entities.lib.repository.AlarmMutedRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmMutedTest {

	private AlarmMutedRepository alarmMutedRepository;

	CollectionManagerService cms;

	public AlarmMutedTest() {

		super();

		cms = new CollectionManagerService();
		
		// load cache

		cms.load();

		alarmMutedRepository = new AlarmMutedRepository();

	}

	@Test
	public void p1_createData() {

		// insert alarm muted

		AlarmMuted alarmMutedCreate = new AlarmMuted();

		alarmMutedCreate.setAlarmConfigId(3l);
		alarmMutedCreate.setElementId(1l);
		alarmMutedCreate.setElementTypeId(2l);
		alarmMutedCreate.setCreatedAt(2l);
		alarmMutedCreate.setMutedUntil(3l);
		alarmMutedCreate.setUpdatedAt(4l);

		alarmMutedRepository.create(alarmMutedCreate);


		// get AlarmMuted

		List<AlarmMuted> alarmMuteds = alarmMutedRepository.getAll(true);
		AlarmMuted alarmMutedGet;

		for (AlarmMuted alarmMuted : alarmMuteds) {

			alarmMutedGet = alarmMutedRepository.get(alarmMuted.getAlarmConfigId(), alarmMuted.getElementTypeId(),
					alarmMuted.getElementId(), true);

			if (alarmMutedGet.getAlarmConfigId().equals(3l) && alarmMutedGet.getElementId().equals(1l)
					&& alarmMuted.getElementTypeId().equals(2l)) {

				assertEquals(alarmMutedCreate.getAlarmConfigId(), alarmMutedGet.getAlarmConfigId());
				assertEquals(alarmMutedCreate.getElementId(), alarmMutedGet.getElementId());
				assertEquals(alarmMutedCreate.getElementTypeId(), alarmMutedGet.getElementTypeId());
			}

		}

		// get alarm muted by element

		List<AlarmMuted> alarmMutedsByElement = alarmMutedRepository.getsByElement(2l, 1l, false);

		for (AlarmMuted alarmMuted : alarmMutedsByElement) {

			alarmMutedGet = alarmMutedRepository.get(alarmMuted.getAlarmConfigId(), alarmMuted.getElementTypeId(),
					alarmMuted.getElementId(), true);

			if (alarmMutedGet.getAlarmConfigId().equals(3l) && alarmMutedGet.getElementId().equals(1l)
					&& alarmMuted.getElementTypeId().equals(2l)) {

				assertEquals(alarmMutedCreate.getAlarmConfigId(), alarmMutedGet.getAlarmConfigId());
				assertEquals(alarmMutedCreate.getElementId(), alarmMutedGet.getElementId());
				assertEquals(alarmMutedCreate.getElementTypeId(), alarmMutedGet.getElementTypeId());
			}

		}

		// get alarm muted by alarmConfig

		List<AlarmMuted> alarmMutedsByAlarmConfig = alarmMutedRepository.getsByAlarmConfig(3l, false);

		for (AlarmMuted alarmMuted : alarmMutedsByAlarmConfig) {

			alarmMutedGet = alarmMutedRepository.get(alarmMuted.getAlarmConfigId(), alarmMuted.getElementTypeId(),
					alarmMuted.getElementId(), true);

			if (alarmMutedGet.getAlarmConfigId().equals(3l) && alarmMutedGet.getElementId().equals(1l)
					&& alarmMuted.getElementTypeId().equals(2l)) {

				assertEquals(alarmMutedCreate.getAlarmConfigId(), alarmMutedGet.getAlarmConfigId());
				assertEquals(alarmMutedCreate.getElementId(), alarmMutedGet.getElementId());
				assertEquals(alarmMutedCreate.getElementTypeId(), alarmMutedGet.getElementTypeId());
			}

		}

		// delete alarm muted

		alarmMutedRepository.delete(alarmMutedCreate);

		// get alarm muted delete

		alarmMuteds = alarmMutedRepository.getAll(true);

		for (AlarmMuted alarmMuted : alarmMuteds) {

			alarmMutedGet = alarmMutedRepository.get(alarmMuted.getAlarmConfigId(), alarmMuted.getElementTypeId(),
					alarmMuted.getElementId(), true);

			if (alarmMutedGet.getAlarmConfigId().equals(3l) && alarmMutedGet.getElementId().equals(1l)
					&& alarmMuted.getElementTypeId().equals(2l)) {

				assertEquals(alarmMutedCreate.getAlarmConfigId(), alarmMutedGet.getAlarmConfigId());
				assertEquals(alarmMutedCreate.getElementId(), alarmMutedGet.getElementId());
				assertEquals(alarmMutedCreate.getElementTypeId(), alarmMutedGet.getElementTypeId());
			}

		}

	}
}

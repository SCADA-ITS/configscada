package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmFilteredVO;
import com.revenga.rits.back.service.graphql.service.AlarmConfigService;
import com.revenga.rits.back.service.graphql.service.AlarmFilteredService;
import com.revenga.rits.back.service.graphql.service.AlarmService;

@Component
public class AlarmMutation implements GraphQLMutationResolver {

	@Autowired
	private AlarmConfigService alarmConfigService;
	
	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private AlarmFilteredService alarmFilteredService;
	
	public Optional<AlarmConfigVO> createAlarmConfig(final AlarmConfigVO alarmConfig) {

		return alarmConfigService.create(alarmConfig);
	}

	public Optional<AlarmConfigVO> updateAlarmConfig(final AlarmConfigVO alarmConfig) {

		return alarmConfigService.update(alarmConfig);
	}

	public Optional<AlarmConfigVO> deleteAlarmConfig(final String alarmConfigId) {

		return alarmConfigService.delete(alarmConfigId);
	}

	//
	// Command 
	//
	public Boolean sendAlarms(final List<String> alarmConfigIds, final List<String> targetElementIds, final String creationComments, final String userId) {

		return alarmService.sendAlarms(alarmConfigIds, targetElementIds, creationComments, userId);
	}
	
	public Boolean sendRecognizeAlarms(final List<String> alarmIds, final String recognizedComments, final String userId, final Boolean disagree) {

		return alarmService.sendRecognizeAlarms(alarmIds, recognizedComments, userId, disagree);
	}
	
	public Boolean sendDeactivationAlarms(final List<String> alarmIds, final String deactivationComments, final String userId) {

		return alarmService.sendDeactivationAlarms(alarmIds, deactivationComments, userId);
	}
	
	public Boolean sendAlarmFilteredActions(final List<AlarmFilteredVO>  alarmFiltereds) {

		return alarmFilteredService.sendAlarmFilteredActions(alarmFiltereds);
	}
	
}
package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.revenga.rits.back.data.core.model.command.StopProcessesCommand;
import com.revenga.rits.back.front.core.model.helper.command.StopProcessesCommandHelper;
import com.revenga.rits.back.front.core.model.vo.ProcessInfoVO;
import com.revenga.rits.back.service.graphql.type.ProcessInfoGraphql;

@Service
public class ProcessInfoService implements QueryInterface<ProcessInfoVO> {

	@Override
	public Optional<ProcessInfoVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProcessInfoVO> getAll() {
		

		List<ProcessInfoVO> processesInfoVO = new ArrayList<>();

		for (ProcessInfo processInfo : EntitiesManager.getInstance().getProcessInfoRepository().getAll(false)) {

			processesInfoVO.add(new ProcessInfoGraphql(processInfo));
		}
		
		return processesInfoVO;		
	}

	@Override
	public List<ProcessInfoVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean stopProcesses(List<Long> pids) {
		
		StopProcessesCommand command = StopProcessesCommandHelper.get(pids);

		return (command != null && EntitiesManager.getInstance().sendCommand(command) != null);
	}

}

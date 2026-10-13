package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;
import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.revenga.rits.back.front.core.model.vo.ProcessInfoVO;

public class ProcessInfoHelper {
	
	
	private ProcessInfoHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}
	
	public static ProcessInfoVO get(final ProcessInfo processInfo) {

		ProcessInfoVO processInfoVO = null;

		if (processInfo != null) {

			processInfoVO = new ProcessInfoVO();

			assign(processInfo, processInfoVO);
		}

		return processInfoVO;
	}
	
	
	public static ProcessInfo get(final ProcessInfoVO processInfoVO) {
		
		ProcessInfo processInfo = null;
		
		if (processInfoVO != null) {
			
			processInfo = new ProcessInfo();
			
			processInfo.setMain(processInfoVO.getMain());
			processInfo.setPid(processInfoVO.getPid());
			processInfo.setCommandLine(processInfoVO.getCommandLine());
			processInfo.setCpuLoad(processInfoVO.getCpuLoad());
			processInfo.setCpuTime(processInfoVO.getCpuTime());
			processInfo.setCpuAverage(processInfoVO.getCpuAverage());
			processInfo.setHeapSize(processInfoVO.getHeapSize());
			processInfo.setHeapUsed(processInfoVO.getHeapUsed());
			processInfo.setHeapMax(processInfoVO.getHeapMax());
			processInfo.setThreadsLive(processInfoVO.getThreadsLive());
			processInfo.setNumRestarts(processInfoVO.getNumRestarts());
			processInfo.setReady(processInfoVO.getReady());
			processInfo.setUpdatedAt(processInfoVO.getUpdatedAt());
			processInfo.setCreatedAt(processInfoVO.getCreatedAt());
			processInfo.setGitAuthor(processInfoVO.getGitAuthor());
			processInfo.setGitCommit(processInfoVO.getGitCommit());
			processInfo.setGitDate(processInfoVO.getGitDate());
			processInfo.setGitMessage(processInfoVO.getGitMessage());
			
		}
		
		return processInfo;
	}
	

	public static void assign(ProcessInfo processInfo, ProcessInfoVO processInfoVO) {

		if (ObjectUtils.allNotNull(processInfo, processInfoVO)) {
		
			BasicElementHelper.fillBasicProperties(processInfoVO, processInfo);
			
			processInfoVO.setMain(processInfo.getMain());
			processInfoVO.setPid(processInfo.getPid());
			processInfoVO.setCommandLine(processInfo.getCommandLine());
			processInfoVO.setCpuLoad(processInfo.getCpuLoad());
			processInfoVO.setCpuTime(processInfo.getCpuTime());
			processInfoVO.setCpuAverage(processInfo.getCpuAverage());
			processInfoVO.setHeapSize(processInfo.getHeapSize());
			processInfoVO.setHeapUsed(processInfo.getHeapUsed());
			processInfoVO.setHeapMax(processInfo.getHeapMax());
			processInfoVO.setThreadsLive(processInfo.getThreadsLive());
			processInfoVO.setNumRestarts(processInfo.getNumRestarts());
			processInfoVO.setReady(processInfo.getReady());
			processInfoVO.setUpdatedAt(processInfo.getUpdatedAt());
			processInfoVO.setCreatedAt(processInfo.getCreatedAt());
			processInfoVO.setGitAuthor(processInfo.getGitAuthor());
			processInfoVO.setGitCommit(processInfo.getGitCommit());
			processInfoVO.setGitDate(processInfo.getGitDate());
			processInfoVO.setGitMessage(processInfo.getGitMessage());
		
		}
	
	}
	
}
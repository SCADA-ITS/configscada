package com.revenga.rits.back.virtual.equipment.manager.vms;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class VmsTravelTimeExecutor extends GenericExecutor {

	VmsTravelTimeService vmsTravelTimeService;
	
	private static final String CRON_VMS_TRAVEL_TIME = "cron_vms_travel_time";

	public VmsTravelTimeExecutor(Executor executor, short threadNum) {
		super(executor, threadNum);
		this.vmsTravelTimeService = new VmsTravelTimeService(this);
	}

	@Override
	protected Object prepare(Object queueItem) {

		return null;
	}

	@Override
	protected Object publish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initialize() {
		
		log.debug(this.getClass().getSimpleName() + "[" + threadNum + "] now is running");
		
		if (threadNum == 0) {

			String cronVmsTravelTime = getParamValue(CRON_VMS_TRAVEL_TIME);

			vmsTravelTimeService.init(cronVmsTravelTime);
		}
	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void end() {

		if (threadNum == 0) {

			vmsTravelTimeService.deInit();
		}
	}

}

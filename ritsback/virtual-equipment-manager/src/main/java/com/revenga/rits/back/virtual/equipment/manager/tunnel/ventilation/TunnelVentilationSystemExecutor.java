package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansResetTotalHoursCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartReverseCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStopCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationRegimeCommand;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TunnelVentilationSystemExecutor extends GenericExecutor {

	TunnelVentilatonSystemService tunnelVentilatonSystemService;

	public TunnelVentilationSystemExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		this.tunnelVentilatonSystemService = TunnelVentilatonSystemService.getInstance();
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			List<Command> commands = null;

			if (queueItem instanceof SetFansStartCommand) {

				SetFansStartCommand cmd = (SetFansStartCommand) queueItem;
				this.tunnelVentilatonSystemService.setFansStartCommand(cmd);

			} else if (queueItem instanceof SetFansStopCommand) {

				SetFansStopCommand cmd = (SetFansStopCommand) queueItem;
				this.tunnelVentilatonSystemService.setFansStopCommand(cmd);

			} else if (queueItem instanceof SetFansStartReverseCommand) {

				SetFansStartReverseCommand cmd = (SetFansStartReverseCommand) queueItem;
				this.tunnelVentilatonSystemService.setFansStartReverseCommand(cmd);

			} else if (queueItem instanceof SetFansResetTotalHoursCommand) {

				SetFansResetTotalHoursCommand cmd = (SetFansResetTotalHoursCommand) queueItem;
				commands = this.tunnelVentilatonSystemService.setFansResetTotalHoursCommand(cmd);

			} else if (queueItem instanceof SetVentilationModeCommand) {

				SetVentilationModeCommand cmd = (SetVentilationModeCommand) queueItem;
				commands = this.tunnelVentilatonSystemService.setVentilationModeCommand(cmd);

			} else if (queueItem instanceof SetVentilationRegimeCommand) {

				SetVentilationRegimeCommand cmd = (SetVentilationRegimeCommand) queueItem;
				commands = this.tunnelVentilatonSystemService.setVentilationRegimeCommand(cmd);

			} else if (queueItem instanceof SetAlarmsToVentilationCommand) {

				SetAlarmsToVentilationCommand cmd = (SetAlarmsToVentilationCommand) queueItem;
				commands = this.tunnelVentilatonSystemService.setAlarmsToVentilationCommand(cmd);

			} else if (queueItem instanceof SetElementStatesToVentilationCommand) {

				SetElementStatesToVentilationCommand cmd = (SetElementStatesToVentilationCommand) queueItem;
				commands = this.tunnelVentilatonSystemService.setElementStatesToVentilationCommand(cmd);

			} else if (queueItem instanceof SetElementValuesToVentilationCommand) {

				SetElementValuesToVentilationCommand cmd = (SetElementValuesToVentilationCommand) queueItem;
				commands = this.tunnelVentilatonSystemService.setElementValuesToVentilationCommand(cmd);

			} else if (queueItem instanceof SetFireAlarmCommand) {

				SetFireAlarmCommand cmd = (SetFireAlarmCommand) queueItem;
				commands = this.tunnelVentilatonSystemService.setFireAlarmCommand(cmd);

			} else {

				log.warn("Unknown command " + queueItem.getClass().getSimpleName() + " dequeued by "
						+ this.getClass().getSimpleName());
			}

			EntityManager.getInstance().sendCommands(commands);

		} catch (

		Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected Object publish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initialize() {

		if (threadNum == 0 && tunnelVentilatonSystemService != null) {

			List<Command> commands = tunnelVentilatonSystemService.init();

			EntityManager.getInstance().sendCommands(commands);

			getQueueItems().clear();
		}
	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void end() {

		if (threadNum == 0 && tunnelVentilatonSystemService != null) {

			tunnelVentilatonSystemService.deInit();
		}

	}

}

package com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOffCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOnCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetDaliIlluminationRegimeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TunnelIlluminationSystemExecutor extends GenericExecutor {

	TunnelIlluminationSystemService tunnelIlluminationSystemService;

	public TunnelIlluminationSystemExecutor(Executor executor, short threadNum) {
		super(executor, threadNum);
		this.tunnelIlluminationSystemService = new TunnelIlluminationSystemService(this);
	}

	@Override
	protected Object prepare(Object queueItem) {
		try {

			List<Command> commands = null;

			if (queueItem instanceof SetIlluminationModeCommand) {

				SetIlluminationModeCommand cmd = (SetIlluminationModeCommand) queueItem;
				commands = this.tunnelIlluminationSystemService.setIlluminationModeCommand(cmd);

			} else if (queueItem instanceof SetIlluminationRegimeCommand) {

				SetIlluminationRegimeCommand cmd = (SetIlluminationRegimeCommand) queueItem;
				commands = this.tunnelIlluminationSystemService.setIlluminationRegimeCommand(cmd);

			} else if (queueItem instanceof SetAlarmsToIlluminationCommand) {

				SetAlarmsToIlluminationCommand cmd = (SetAlarmsToIlluminationCommand) queueItem;
				commands = this.tunnelIlluminationSystemService.SetAlarmsToIlluminationCommand(cmd);

			} else if (queueItem instanceof SetElementStatesToIlluminationCommand) {

				SetElementStatesToIlluminationCommand cmd = (SetElementStatesToIlluminationCommand) queueItem;
				commands = this.tunnelIlluminationSystemService.SetElementStatesToIlluminationCommand(cmd);

			} else if (queueItem instanceof SetElementValuesToIlluminationCommand) {

				SetElementValuesToIlluminationCommand cmd = (SetElementValuesToIlluminationCommand) queueItem;
				this.tunnelIlluminationSystemService.SetElementValuesToIlluminationCommand(cmd);

			} else if (queueItem instanceof SetCircuitsOffCommand) {

				SetCircuitsOffCommand cmd = (SetCircuitsOffCommand) queueItem;
				commands = this.tunnelIlluminationSystemService.SetCircuitsOffCommand(cmd);

			} else if (queueItem instanceof SetCircuitsOnCommand) {

				SetCircuitsOnCommand cmd = (SetCircuitsOnCommand) queueItem;
				commands = this.tunnelIlluminationSystemService.SetCircuitsOnCommand(cmd);

			} else if (queueItem instanceof SetDaliIlluminationRegimeCommand) {

				SetDaliIlluminationRegimeCommand cmd = (SetDaliIlluminationRegimeCommand) queueItem;
				commands = this.tunnelIlluminationSystemService.SetDaliIlluminationRegimeCommand(cmd);

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
		if (threadNum == 0 && tunnelIlluminationSystemService != null) {

			List<Command> commands = tunnelIlluminationSystemService.init();
			
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

		if (threadNum == 0 && tunnelIlluminationSystemService != null) {

			tunnelIlluminationSystemService.deInit();
		}

	}

}

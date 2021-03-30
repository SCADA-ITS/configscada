import { util } from "../lib/util.js";

const MARGIN_ACTION_PANEL = 15;

export default class CommandsComponent {

    constructor(parentId, commands, parentContext, events) {
		// webix.CustomScroll.init();
        this.parentId = parentId;
        this.commands = commands;
		this.parentContext = parentContext;
		this.events = events;

        this.commandGroups = [];
        this.commandsGrpMap = new Map();

        this.commands.forEach(cmd => {
			let tempCommandGrpId;
			if(cmd.commandGroup){
				tempCommandGrpId = cmd.commandGroup.id;
				this.commandGroups[cmd.commandGroup.position]=cmd.commandGroup;
			}else{
				this.commandGroups[undefined]=true;
			}
			let tempCommands = this.commandsGrpMap.get(tempCommandGrpId);
			if(!tempCommands){
				tempCommands = [];
			}
			tempCommands[cmd.position]=cmd;
			this.commandsGrpMap.set(tempCommandGrpId, tempCommands);
        });

    }

    getComponent() {
        let viewComponent = {rows:[]};
		
		if(this.commandGroups[undefined]){
			let componentRows = [
				this.getHeaderGrpCommand(),
				{ height: 20 }
			];
			this.commandsGrpMap.get(undefined).forEach(command => {
				componentRows.push(this.getRowCommamd(command));
			});
			
			viewComponent.rows.push(this.getBodyComponet(componentRows));
		}

		this.commandGroups.forEach(commandGrp => {
			let componentRows = [
				this.getHeaderGrpCommand(commandGrp),
				{ height: 20 }
			];
			this.commandsGrpMap.get(commandGrp.id).forEach(command => {
				componentRows.push(this.getRowCommamd(command));
			});
			
			viewComponent.rows.push(this.getBodyComponet(componentRows));
		});

		return viewComponent;
    }

	getRowCommamd(command){
		
		let row = {
			view: "button",
			css: "panel-button",
			type: "icon",
			on: {
                onItemClick: () => {
                    
                    if (command) {
                    
                        if (!this.command_exec_callback(command)) {
                                this.open_module(command);		
                        }	
                    }
                }
            }
		};

		row.label = i18n.t(command.label);
		row.tooltip = i18n.t(command.label);
		row.icon = command.icon;
		
		return row;
	}

	getHeaderGrpCommand(commandGrp){
		
		let header = {
			view: "label",
			css: "section",
			borderless: true
		};

		if(commandGrp){
			header.label = `<div class="title"><span class="section-text">${i18n.t(commandGrp.label)}</span></div>`;
		} else {
			header.label = `<div class="title"><span class="section-text">${i18n.t("LBL_ACTIONS")}</span></div>`;
		}
		
		return header;
	}

	getBodyComponet(componentRows){
		
		let body = {
			width: 200,
			css: "panel-action",
			rows: [
				{ height: 20 },
				{
					cols: [
						{ width: MARGIN_ACTION_PANEL },
						{
							rows: componentRows
						},		
						{ width: MARGIN_ACTION_PANEL }
					]
				}
			]
		}
		
		return body;
	}

	command_exec_callback(command) {
		
		let cmdCallback;
		
		if (this.events && this.events[DataTable.EventTypes.ON_COMMAND_EXEC] && 
		    Array.isArray(this.events[DataTable.EventTypes.ON_COMMAND_EXEC]) && 
			this.events[DataTable.EventTypes.ON_COMMAND_EXEC].length > 0) {
			
			let cmdCallbacks = this.events[DataTable.EventTypes.ON_COMMAND_EXEC];
			
			cmdCallback = cmdCallbacks.find(cmdCall => cmdCall.commandId === command.id);
			
			if (cmdCallback) {
			
				cmdCallback.function(this.parentContext.getSelectedIds());
			}
		}															
				
		return cmdCallback != undefined;
	}

    open_module(command) {

		let args = {};
		let obj = this;

		let viewTypeId = undefined;

		if (command && command.moduleAction) {

			let module = findModuleByIdAction(command.moduleAction.id);
			let selectedPks = this.parentContext.getSelectedIds();

			if (command.viewType) {

				viewTypeId = command.viewType.id;
			}

			if (command.args != undefined) {

				try {
					args = JSON.parse(command.args);

				} catch (error) {

					console.error(error);
					args = {};
				}

				args.callbacks = [];
				args.callbacks.push(function() { obj.refresh(obj) });
			}

			// Any selected?
			if (selectedPks.length > 0) {

				// Multiselect not allowed with 2 o more selected
				if (selectedPks.length > 1 && command.multiselect === false) {

					webix.alert({
						title: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT"),
						text: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT_DESCRIPTION"),
						type: "alert-error",
						width: 500
					});
				}
				// One or more selected and multiselect allowed
				else {

					args.selectedPks = selectedPks;
					util.module.open(module, command.moduleAction, viewTypeId, null, args);
				}
			}
			// No rows selected
			else {
				// Required one or more selected
				if (command.itemRequired) {
					webix.alert({
						title: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED"),
						text: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"),
						type: "alert-warning",
						width: 500
					});
				}
				// No required select rows
				else {

					util.module.open(module, command.moduleAction, viewTypeId, null, args);
				}
			}
		}
	}

}
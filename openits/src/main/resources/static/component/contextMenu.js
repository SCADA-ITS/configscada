import { util } from "../lib/util.js";

export default class ContextMenu {
	
    constructor(parentId, commands, parentContext, events) {
        
        this.parentId = parentId;
        this.commands = commands;
        this.parentContext = parentContext;
        this.events = events;
        this.ctxMenuData = [];

        this.commands.forEach(cmd => {
			if(cmd.context === undefined || cmd.context){
				this.ctxMenuData.push({ commandId: cmd.id, value: i18n.t(cmd.label), icon: cmd.icon, default: cmd.default_ });
			}
        });

    }

    getComponent() {
        let self = this;

        let result = {
            view: "contextmenu",
            id: "ctx_menu-" + this.parentId,
            width: 300,
            data: this.ctxMenuData,
            template: (obj) => {

                if (!obj.default) {
                    return `<span class = 'webix_icon ${obj.icon}'></span> ${obj.value}`;
                }
                else {
                    return `<span class = 'webix_icon ${obj.icon}'></span> <span style='font-weight: 500;'>${obj.value}</span>`;
                }

            },
            on: {
                onMenuItemClick: function(id) {
                    
                    let command = self.commands.find(e => {
                        return e.id === this.getItem(id).commandId
                    });
                    
                    if (command) {
                    
                        if (!self.command_exec_callback(command)) {
                                self.open_module(command);		
                        }	
                    }
                }
            }
            
        };
        return result;
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
			
			let executeModule = false;
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

			if (command.applyAll != undefined && command.applyAll){

				args.selectedPks = this.parentContext.getApplyAllIds();
				executeModule = true;

			} else if (selectedPks.length > 0) {

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
					executeModule = true;
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
					executeModule = true;
				}
			}

			if(executeModule){

				let typeCommands;

				if(command.synopticElementTypeCommand != undefined && command.synopticElementTypeCommand.length > 0){
					typeCommands = new Map();
					command.synopticElementTypeCommand.forEach(typeCommand => {
						let tmpCommand = {};
						tmpCommand.moduleAction = typeCommand.moduleAction;
						tmpCommand.args = webix.copy(args);
						tmpCommand.args.selectedPks = [];
						typeCommands.set(typeCommand.elementType, tmpCommand);
					});
				}

				if(typeCommands != undefined){

					let tmpSelectedPKs = [];

					args.selectedPks.forEach(selectedPK => {
						let elementType = window.em.getByField(EntityType.ELEMENT, "id", selectedPK).elementType.id;
						if(typeCommands.get(elementType)){
							typeCommands.get(elementType).args.selectedPks.push(selectedPK);
						} else {
							tmpSelectedPKs.push(selectedPK);
						}
					});

					args.selectedPks = tmpSelectedPKs;

					for (let [key, value] of typeCommands) { 
						if(value.args.selectedPks && value.args.selectedPks.length > 0){
							let module = findModuleByIdAction(value.moduleAction.id);
							util.module.open(module, value.moduleAction, viewTypeId, null, value.args);
						}   
					}
				}
				let module = findModuleByIdAction(command.moduleAction.id);
				util.module.open(module, command.moduleAction, viewTypeId, null, args);		
			}

		}
	}

}
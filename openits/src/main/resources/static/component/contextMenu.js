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
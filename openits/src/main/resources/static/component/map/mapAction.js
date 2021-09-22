import { util } from "../../lib/util.js";

const MARGIN_ACTION_PANEL = 15;
const MARGIN_BETWEEN_COMMANDS = 10;

export default class MapAction {

	constructor(commands, allPks, selectedPksCallback, cmdCallbacks) {

		this.uid = webix.uid();
		this.commands = commands;
		this.allPks = allPks;
		this.selectedPksCallback = selectedPksCallback;
		this.cmdCallbacks = cmdCallbacks;
	}
	
	getView() {

		let self = this;
		
		let view = {
			id: "map-action-" + self.uid,
			css: { "background": "white" }, 
			rows:[]};
			
		let componentRows = [];
		
		self.commands.forEach(command => {
			componentRows.push(getRowCommand(command));
			componentRows.push({ height: MARGIN_BETWEEN_COMMANDS });
		});
		
		view.rows.push(getBodyComponent(componentRows));
		
		view.rows.push({ height: 20 });
		
		function getRowCommand(command){
		
			let row = {
				view: "button",
				css: "panel-button",
				type: "icon",
				on: {
	                onItemClick: () => {
	                    
	                    if (command) {
		
							util.module.executeCommand(command, self.allPks, self.selectedPksCallback, self.cmdCallbacks);
		                }
					}
	            }
			};
	
			row.label = i18n.t(command.label);
			row.tooltip = i18n.t(command.label);
			row.icon = command.icon;
			
			return row;
		}
		
		function getBodyComponent(compRows){
		
			let body;
			
			body = {
				width: 200,
				css: "panel-action",
				rows: [
					{ height: 20 },
					{
						cols: [
							{ width: MARGIN_ACTION_PANEL },
							{
								rows: compRows
							},		
							{ width: MARGIN_ACTION_PANEL }
						]
					}
				]
			}
			
			return body;
		}

		return view;
	}
	
	executeDefault() {
		
		let self = this;
		
		if (Array.isArray(self.commands) && self.commands.length > 0) {
			
			let defaultCmd = self.commands.find(cmd => cmd.default_);
			
			if (defaultCmd) {
		
				this.execute(defaultCmd);		
			}
		}
	}
	
	execute(command) {
		
		let self = this;
		
		util.module.executeCommand(command, self.allPks, self.selectedPksCallback);									
	}
	
	show() {
		
		let mapAction = $$("map-action-" + this.uid);
		
		if (mapAction) {
			
			mapAction.show();	
		}
	}
}


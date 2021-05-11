import { util } from "../lib/util.js";

const MARGIN_ACTION_PANEL = 15;
const MARGIN_BETWEEN_COMMANDS = 10;

export default class MapAction {

	constructor(commands, allPks, selectedPksCallback, events) {

		this.uid = webix.uid();
		this.commands = commands;
		this.allPks = allPks;
		this.selectedPksCallback = selectedPksCallback;
		this.events = events;
		
		let commandGroups = [];
        let commandsGrpMap = new Map();

		initialize();
		
		this.commandGroups = commandGroups;
		this.commandsGrpMap = commandsGrpMap;

		function initialize() {
			
			if(commands){
				commands.forEach(cmd => {
					let tempCommandGrpId;
					if(cmd.commandGroup){
						tempCommandGrpId = cmd.commandGroup.id;
						commandGroups[cmd.commandGroup.position]=cmd.commandGroup;
					}else{
						commandGroups[undefined]=true;
					}
					let tempCommands = commandsGrpMap.get(tempCommandGrpId);
					if(!tempCommands){
						tempCommands = [];
					}
					tempCommands[cmd.position]=cmd;
					commandsGrpMap.set(tempCommandGrpId, tempCommands);
				});
			}
		}
	}

	getView() {

		let self = this;
		
		let view = {
			id: "map-action-" + self.uid,
			css: { "background": "white" }, 
			rows:[]};
		
		if(self.commandGroups[undefined]){
			let componentRows = [
				/*getHeaderGrpCommand(),
				{ height: 20 }*/
			];
			self.commandsGrpMap.get(undefined).forEach(command => {
				componentRows.push(getRowCommamd(command));
				componentRows.push({ height: MARGIN_BETWEEN_COMMANDS });
			});
			
			view.rows.push(getBodyComponent(componentRows));
		}

		self.commandGroups.forEach(commandGrp => {
			let componentRows = [
				getHeaderGrpCommand(commandGrp),
				{ height: 20 }
			];
			self.commandsGrpMap.get(commandGrp.id).forEach(command => {
				componentRows.push(getRowCommamd(command));
				componentRows.push({ height: MARGIN_BETWEEN_COMMANDS });
			});
			
			view.rows.push(getBodyComponent(componentRows));
		});
		
		view.rows.push({ height: 20 });
		
		function getRowCommamd(command){
		
			let row = {
				view: "button",
				css: "panel-button",
				type: "icon",
				on: {
	                onItemClick: () => {
	                    
	                    if (command) {
	                    
							util.module.executeCommand(command, self.allPks, self.selectedPksCallback);
	                    }
	                }
	            }
			};
	
			row.label = i18n.t(command.label);
			row.tooltip = i18n.t(command.label);
			row.icon = command.icon;
			
			return row;
		}
		
		function getHeaderGrpCommand(commandGrp){
		
			let header = {
				view: "label",
				css: "section",
				label: `<div class="title"><span class="section-text">${i18n.t(commandGrp.label)}</span></div>`,
				borderless: true
			};
	
			return header;
		}
		
		function getBodyComponent(componentRows){
		
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

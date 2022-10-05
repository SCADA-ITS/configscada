import { util } from "../../lib/util.js";
import MessageBox from "../messageBox.js";

export default class MapContextMenu {
	
    constructor(commands, allPks, selectedPksCallback, cmdCallbacks) {
        
        this.uid = webix.uid();
        this.commands = commands;
		this.allPks = allPks;
		this.selectedPksCallback = selectedPksCallback;
		this.cmdCallbacks = cmdCallbacks;
		this.sendCommandPermission = util.module.isAllowedPermission(REQUIRED_PERMISSIONS.SEND_COMMAND);
    }

    getView() {
	
        let self = this;

        let view;
        
        view = {
            view: "contextmenu",
            id: "ctx_menu-" + self.uid,
            width: 300,
            submenuConfig:{
        		width: 300,
        		template: function(obj) {
	
					return getMenuItem(obj);
				} 
        	},
            data: [],
            template: function(obj) {
	
				return getMenuItem(obj); 
				
            },
            on: {
				onBeforeShow: function() {
					
					this.clearAll();
					this.define("data", getData());
					this.refresh();
				},
                onMenuItemClick: function(id) {
                    
                    let item = this.getItem(id);
                    
                    if (item && item.commandId !== undefined && item.commandId !== null ) {
	
						let command = self.commands.find(e => {
	                        return e.id === item.commandId
	                    });
	                    
	                    if (command) {
	                    
							util.module.executeCommand(command, self.allPks, self.selectedPksCallback, self.cmdCallbacks);
	                    }
					}
					else {
						
						item = this.getMenuItem(id);
						
						if (item && item.commandElementId) {
							
							let commands = getCommands(item.commandElementId);
							
							if (Array.isArray(commands) && commands.length > 0) {
								
								util.element.sendCommand(commands, function(res) {

									if (res) {

										(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null, null)).show();
									}
								}, 
								item.elementTypeLabelAlias + " - " + i18n.t("LBL_SEND_SIGNALING_CONFIRM") + " => " + item.value);	
							}
						}
					}
	            }
            }
        };
        
        function getMenuItem(obj) {
	
			if (obj.icon) {
					
				if (!obj.default) {
                    return `<span class = 'webix_icon ${obj.icon}'></span> ${obj.value}`;
                }
                else {
                    return `<span class = 'webix_icon ${obj.icon}'></span> <span style='font-weight: 500;'>${obj.value}</span>`;
                }	
			}
			else {
				
				return `<img style='position: absolute; height:16px; width:16px;margin-top: 6px;' src='img/${obj.img}' onerror='this.onerror=null;this.src="img/grid/empty.png"'> <span style='position: absolute;margin-left: 25px;'>${obj.value}</span>`
			}
		}
        
        function getCommands(commandElementId) {
	
			let commands = [];
			let selectedPks = self.selectedPksCallback();
				
			if (Array.isArray(selectedPks) && selectedPks.length > 0) {
				
				selectedPks.forEach(elementId => {
					
					commands.push({
						elementId: elementId,
						commandElementTypeId: commandElementId
					});
				});
			}
			
			return commands;
		}
        
        function getData() {
	
			let data = [];
			let mapElementTypesCount = new Map();
	
			if (Array.isArray(self.commands)) {
				
				self.commands.forEach(cmd => {
				
					if (cmd.context === undefined || cmd.context) {
						
						data.push({ commandId: cmd.id, value: i18n.t(cmd.label), icon: cmd.icon, default: cmd.default_ });
					}
				});
			}
			
			if (self.sendCommandPermission && self.selectedPksCallback) {
				
				let selectedPks = self.selectedPksCallback();
				
				if (Array.isArray(selectedPks) && selectedPks.length > 0) {
					
					selectedPks.forEach(elementId => {
						
						let element = window.em.getByField(EntityType.ELEMENT, "id", elementId);
						
						if (element && element.elementType) {
							
							let elementTypeId = element.elementSubtype ? element.elementSubtype.id : element.elementType.id;
							
							let count = mapElementTypesCount.get(elementTypeId);
							
							mapElementTypesCount.set(elementTypeId, count ? ++count : 1);
						}
					});
					
					let elementTypeCommands = util.element.calculateElementTypeCommands(selectedPks);
			
					if (Array.isArray(elementTypeCommands) && elementTypeCommands.length > 0) {
						
						//elementTypeCommands = elementTypeCommands.filter(e => e.defaultSignallingModule);
						
						if (Array.isArray(elementTypeCommands) && elementTypeCommands.length > 0) {
							
							elementTypeCommands.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
						
							data.push({ $template:"Separator" });
							
							elementTypeCommands.forEach(elementTypeCommand => {
								
								let elementTypeCommandMenu = {
			
									value: elementTypeCommand.aliasCtx + " ( " + mapElementTypesCount.get(elementTypeCommand.id) + " )", 
									img: elementTypeCommand.image,
									submenu: function() {
										
										let cmds = [];
										
										if ((IS_TRUE(elementTypeCommand.elementType.showCtxMenuCommands) || elementTypeCommand.elementType.showCtxMenuCommands === undefined) && Array.isArray(elementTypeCommand.commandElements) && elementTypeCommand.commandElements.length > 0) {
											
											elementTypeCommand.commandElements.sort((a, b) => (i18n.t(a.labelAlias) > i18n.t(b.labelAlias)) ? 1 : ((i18n.t(b.labelAlias) > i18n.t(a.labelAlias)) ? -1 : 0));
											
											elementTypeCommand.commandElements.forEach(cmd => {
												
												if (!cmd.commandElementTypeParams) {
													
													cmds.push({
														value: i18n.t(cmd.labelAlias),
														img: cmd.image,
														commandElementId: cmd.id,
														elementTypeLabelAlias: elementTypeCommand.aliasCtx
													});
												}
											});
										}
										
										return cmds; 
									}()
								}
								
								if (elementTypeCommandMenu.submenu.length > 0) {
									
									data.push(elementTypeCommandMenu);	
								}
							});
						}
					} 
				}
			}
			
			return data;
		}
		
        return view;
    }
}
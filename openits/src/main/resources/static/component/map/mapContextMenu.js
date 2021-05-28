import { util } from "../../lib/util.js";

export default class MapContextMenu {
	
    constructor(commands, allPks, selectedPksCallback, cmdCallbacks) {
        
        this.uid = webix.uid();
        this.commands = commands;
		this.allPks = allPks;
		this.selectedPksCallback = selectedPksCallback;
		this.cmdCallbacks = cmdCallbacks;
        
        let ctxMenuData = [];

		initialize();
		
		this.ctxMenuData = ctxMenuData;
		
		function initialize() {
			
			if(commands){
				commands.forEach(cmd => {
					if(cmd.context === undefined || cmd.context){
						ctxMenuData.push({ commandId: cmd.id, value: i18n.t(cmd.label), icon: cmd.icon, default: cmd.default_ });
					}
				});
			}	
		} 
    }

    getView() {
	
        let self = this;

        let view = {
            view: "contextmenu",
            id: "ctx_menu-" + self.uid,
            width: 300,
            data: self.ctxMenuData,
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
                    
						util.module.executeCommand(command, self.allPks, self.selectedPksCallback, self.cmdCallbacks);
                    }
                }
            }
            
        };
		
        return view;
    }
}
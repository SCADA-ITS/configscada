import GenericModule from "../modules/genericModule.js";
import Taskbar from "../component/general/taskbar.js";
import Task from "../component/general/task.js";
import { util } from "../lib/util.js"


export default class TaskManagerService extends GenericModule {
	
	constructor(module){
		
		super(module, null, null);
		
		this.module = module;
		this.config = module.config;
		
		this.taskbar = undefined;
		
		this.tasks = [];
	}
	
	loadData() {
		
		let self = this;
		
		if (!self.taskbar) {
			
			self.taskbar = new Taskbar();
			
			webix.ui(self.taskbar.getView(), $$(TASKBAR_ID));
			
			webix.attachEvent("onFocusChange", function(view) {
				
				if (view) {
					
					let win = view.getTopParentView();
					
					if (win) {
						
						let id = win.config.id;
						
						if (id.indexOf("win_") != -1) {
							self.focus(id);
						}	
					}
				}
			});
		}
	}
	
	add(id, genericModule) {
		
		let task = undefined;
		
		if (!this.tasks.find(e => e.id === id) && this.taskbar) {
			
			task = new Task(Task.WIN, id, genericModule.module, genericModule.moduleAction);
		
			this.tasks.push(task);
			this.taskbar.add(task);
		}
		
		return task; 
	}
	
	get(id) {
		
		return this.tasks.find(e => e.id === id); 
	}
	
	focus(id) {
		
		let task = this.get(id);
		
		if (task && this.taskbar) {
					
			this.taskbar.focus(task.id);
		}
	}
	
	minimize(id) {
		
		let task = this.get(id);
		
		if (task && this.taskbar) {
					
			this.taskbar.minimize(task.id);
		}
	}
	
	dock(id, genericModule) {
		
		let win = $$(id);
		
		if (win) {
			
			util.module.open(genericModule.module, genericModule.moduleAction, VIEW_TYPE_TAB, null, genericModule.args);
			this.remove(id);
			win.close();
		}
	}
	
	undock(id, genericModule) {
		
		if ($$(MAIN_TAB_ID)) {
			
			let tabItems = $$(MAIN_TAB_ID).getMultiview().getChildViews();

			if (tabItems) {

				let tabBodyId = id.replace("tab", "tab_body")
				let tabBody = tabItems.find(e => e.config.id.indexOf(tabBodyId) !== -1);

				if (tabBody) {
			
					$$(MAIN_TAB_ID).removeView(tabBodyId);
					
					util.module.open(genericModule.module, genericModule.moduleAction, VIEW_TYPE_WINDOW, null, genericModule.args);		
				}
			}
		}
	}
	
	externalize(id, genericModule) {
		
		const CONTAINER_MARGIN = 20;
		let win = undefined;
		
		if (id) {
			
			win = $$(id);
		}
			
		let screenWidth = window.screen.availWidth;
		let screenHeight = window.screen.availHeight;
		let w = screenWidth;
		let h = screenHeight;
		let positionLeft = 0;
		let positionTop = 0;
		let labelWindowParam  = genericModule.module.windowValues ? genericModule.module.windowValues.find(e => e.windowParam.id === WindowParam.LABEL) : undefined;
		let label = labelWindowParam ? labelWindowParam.value : 'LBL_UNDEFINED';
		let isMaximizedWindowParam  = genericModule.module.windowValues ? genericModule.module.windowValues.find(e => e.windowParam.id === WindowParam.IS_MAXIMIZED) : undefined;
		let isMaximized = false;
		
		if (isMaximizedWindowParam) {
			
			isMaximized = isMaximizedWindowParam.value == "true" || isMaximizedWindowParam.value == "1";
		}
		
		w = (isMaximized || !win) ? screenWidth : win.$width + (CONTAINER_MARGIN * 2);
		h = (isMaximized || !win) ? screenHeight : win.$height + (CONTAINER_MARGIN * 2);
		positionLeft = screenWidth / 2 - w / 2;
		positionTop = screenHeight / 2 - h / 2;
		
		if (genericModule.args && genericModule.args.containerId) {
			
			delete genericModule.args.containerId;
		}
		
		let params = {
			width: w - (CONTAINER_MARGIN * 2),
			height: isMaximized ? h - CONTAINER_MARGIN - 100 : h - CONTAINER_MARGIN,
			module: genericModule.module.id
		};
		
		if (genericModule.moduleAction && Object.keys(genericModule.moduleAction).length > 0 && Object.getPrototypeOf(genericModule.moduleAction) === Object.prototype) {
			
			params.moduleAction = genericModule.moduleAction.id;
		}
		
		if (genericModule.args && Object.keys(genericModule.args).length > 0 && Object.getPrototypeOf(genericModule.args) === Object.prototype) {
			
			params.args = JSON.stringify(genericModule.args);
		}
		
		let searchParams = new URLSearchParams(params);
		
		let features = `toolbar=no,
						scrollbars=no,
		    			location=no,
		    			statusbar=no,
		    			menubar=no,
		    			resizable=0,
		    			width=${w},
		    			height=${h},
		    			left=${positionLeft},
		    			top=${positionTop}`;
		
		let browser = window.open("./external.html?" + searchParams.toString(), i18n.t(label), features);
		
		if (browser) {
			
			browser.focus();
			this.remove(id);
			
			if (win) {
				
				win.close();					
			}				
		}
	}
	
	remove(id) {
		
		let task = undefined;
		let idx = this.tasks.findIndex(e => e.id === id);
		
		if (idx !== -1  && this.taskbar) {
			
			task = this.tasks[idx];
			
			this.tasks.splice(idx, 1);
			
			this.taskbar.remove(task.id);
		}
		
		return task;
	}
}

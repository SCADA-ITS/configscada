import GenericModule from "../modules/genericModule.js";
import Taskbar from "../component/general/taskbar.js";
import Task from "../component/general/task.js";

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
	
	externalize(id, genericModule) {
		
		let win = $$(id);
		
		if (win) {
			
			let screenWidth = window.screen.width;
			let screenHeight = window.screen.height;
			let w = screenWidth;
			let h = screenHeight;
			let positionLeft = 0;
			let positionTop = 0;
	
	
			w = win.$width + 20;
			h = win.height + 20;
			positionLeft = screenWidth / 2 - w / 2;
			positionTop = screenHeight / 2 - h / 2;
	
			
			let features = "width=" + w + " ,height=" + h + ", top=" + positionTop + ", left=" + positionLeft + ", toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no";
			
			let params = {
				module: genericModule.module.id,
				moduleAction: genericModule.moduleAction ? genericModule.moduleAction.id : "",
			  	args: genericModule.args ? JSON.stringify(genericModule.args) : ""
			};
			
			let searchParams = new URLSearchParams(params);
			
			window.open("./external.html?" + searchParams.toString(), null, features);
													
			this.remove(id);
			
			win.close();
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

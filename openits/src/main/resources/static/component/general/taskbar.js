//import Task from "./task.js";
import Stack from "../stack.js";

const PREFIX_TASK_BUTTON = "taskbutton_";
const PREFIX_WIN = "win_";

export default class Taskbar {

	constructor() {

		this.tasks = new Map();
		this.taskButtonActive = undefined;
	}

	getView() {

		let view;
		let self = this;
		
		view = {
			id: TASKBAR_ID,
			css: "taskbar",
			view: "toolbar",
			elements: [
				{
					view: "button",
					css: "taskbar-min",
					tooltip: i18n.t("LBL_MINIMIZE_ALL"),
					width: 10,
					on: {
						onItemClick: function() {
							
							self.tasks.forEach(task => {
								
								self.minimize(task.id);
							})
						}
					}
				},
				{
					width: 10
				}
			]
		};

		return view;
	}
	
	add(task) {
		
		let self = this;
		let taskbar = $$(TASKBAR_ID);
		
		if (taskbar) {
			
			addButton();
			this.tasks.set(task.id, task);
		}
		
		function addButton() {
			
			let taskButtonId = task.id.replace(PREFIX_WIN, PREFIX_TASK_BUTTON);
			let icon = 'img/module/default.png';
			let label = 'LBL_UNDEFINED';
			let labelHTML = undefined;
			
			try {
			
				icon = task.module.windowValues.find(e => e.windowParam.id === WindowParam.ICON).value;
				label = task.module.windowValues.find(e => e.windowParam.id === WindowParam.LABEL).value;
			}
			catch {
				
			}

			let template = "<div class='" + task.module.$css + "'><img class='" + task.module.$css + "' src='" + icon + "'></div>"; 
			
			webix.ui({
				view: "button",
				id: taskButtonId,
				css: "taskbar-icon",
				type: "htmlbutton",
				label: template,
				tooltip: {
					template: function() {
						
						let tooltip;
						
						if (labelHTML) {
							
							tooltip = `<img class="icon" src="${icon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/><div class='title'>${labelHTML}</div><div></div>`;	
						}
						else {
							
							tooltip = `<img class="icon" src="${icon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/><div class='title'>${i18n.t(label)}</div><div></div>`;	
						}
						
						let taskButton = $$(taskButtonId); 
						
						if (taskButton && taskButton.config.miniature) {
							
							let canvasData = $$(taskButtonId).config.miniature.toDataURL();
							
							tooltip += `<div align="center"><img height="150" src="${canvasData}"></div>`	
						}

						return tooltip;
					},
					css: "taskbar-icon-tooltip"
				},
				//tooltip: i18n.t(label),
				width: 50,
				on: {
					onItemClick: function() {
						
						let win = $$(task.id);
						
						if (win) {
							
							self.focus(task.id);
							
							win.show();	
							
//							html2canvas(document.body).then(function(canvas) {
//							    document.body.appendChild(canvas);
//							});
						}
					},
					onAfterRender: function() {
			
						let taskButton = this;
									
						if (taskbar) {
							
							taskbar.addView(this);
							
							setTimeout(function() {
								
								if (!taskButton.config.miniature && $$(task.id)) {
									
									html2canvas($$(task.id).getNode()).then(canvas => {
										taskButton.config.miniature = canvas 				    
									});	
								}
								
								let labelWin = $$(task.id + "-label");
						
								if (labelWin && labelWin.config.label) {
									
									labelHTML = labelWin.config.label;
								}

							}, 5000);	
							
							//self.focus(task.id);
						}			
					}
				}
			});
		}
	}
	
	focus(id) {
		
		let win = $$(id);
		let taskButtonId = id.replace(PREFIX_WIN, PREFIX_TASK_BUTTON);
		
		let taskButton = $$(taskButtonId);

		
		if (taskButton) {
			
			if (this.taskButtonActive) {
			
				webix.html.removeCss(this.taskButtonActive.$view, "active");	
			}	
			
			webix.html.addCss(taskButton.$view, "active");
			
			this.taskButtonActive = taskButton;
			
			if (win) {
				
				this.tasks.forEach(task => {
					
					if (task.id === win.config.id) {
					
						webix.html.removeCss(win.getHead().$view, "inactive-window-head");
						webix.html.addCss(win.getHead().$view, "active-window-head");
					}
					else {
						
						let inactiveWin = $$(task.id);
						
						if (inactiveWin) {
							
							webix.html.removeCss(inactiveWin.getHead().$view, "active-window-head");
							webix.html.addCss(inactiveWin.getHead().$view, "inactive-window-head");	
						}
					}
				})
			}
		} 
	}
	
	minimize(id) {
		
		let win = $$(id);
		
		if (win) {
			
			win.hide();
		}
	}
	
	remove(id) {
		
		let taskbar = $$(TASKBAR_ID);

		if (taskbar) {
			
			let task = this.tasks.get(id);
			
			if (task) {
				
				let taskButtonId = id.replace(PREFIX_WIN, PREFIX_TASK_BUTTON);
			
				taskbar.removeView(taskButtonId);
				
				this.tasks.delete(id);
				
				this.taskButtonActive = undefined;
			} 
		}
	}
}

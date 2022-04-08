import { util } from "../../../lib/util.js";
import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";
import MessageBox from "../../../component/messageBox.js";

const QUEUE_COMMAND_HEIGHT = 100;

export default class VmsSignalingQueue {
	
	constructor(config, priorities, width) {

		this.config = config;
		this.priorities = priorities;
		this.width = width;
		this.element = undefined;
		this.maxElementQueueWidth = width - 125;
		this.maxElementQueueHeight = QUEUE_COMMAND_HEIGHT - 25;
		this.lockPrioritySwitches = true; 
		
		this.idStompSubscriptor = undefined;
		this.uid = webix.uid();
	}
	
	getView() {
		
		let self = this;
		
		let viewQueue;
		
		if (Array.isArray(self.priorities) && self.priorities.length > 0) {
			
			viewQueue = {
				id: "view-main-queue-" + self.uid,
				rows: [
					{					
						view: "scrollview",
						scroll: "y",
						body: {
							rows: []	
						},
						on: {
							onDestruct: function() {
						
								if (self.idStompSubscriptor) {
									
									let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
		
									if (stompSubscriptorService) {
										
										stompSubscriptorService.unsubscribe(self.idStompSubscriptor);	
									}
								}
							}	
						}	
					}
				]
					
			}
			
			self.priorities.sort((a, b) => (a.priority > b.priority) ? 1 : ((b.priority > a.priority) ? -1 : 0));
			
			self.priorities.forEach(priority => {
				
				let prioritySection = [
					{ 
						cols: [
							{ width: 20 },
							{
								id: "view-main-queue-section-title-" + priority.id + "-title-" + self.uid, 
								view: "template", 
								template: i18n.t(priority.labelAlias), 
								type: "section" 
							},
							{ width: 10 },
							{ 
								id: "view-main-queue-section-enabled-" + priority.id + "-title-" + self.uid,
								priorityId: priority.id,
								view: "switch",
								width: 70,
								tooltip: function(obj){
							        return obj.value ? i18n.t("LBL_ENABLED") : i18n.t("LBL_DISABLED");
							    },
								value: 1
							},
							{ width: 10 }
						]
					},
					{ height : 5 },
					{
						cols: [
							{ width: 15 },
							{
								rows: [
									{
										id: "view-main-queue-section-data-" + priority.id + "-" + self.uid,
										height: QUEUE_COMMAND_HEIGHT,
										expanded: false,
										rows: [{
											view: "template",
											borderless: true,
											template: function() {
							
												return `<div class='empty-app-inner'>
															<img style='height:40px; width:40px; margin-top: -20px !important;' src='img/not_found_150.png' align='center'>
															<span style='font-size:14px;' class='empty-app-inner-text'>${i18n.t("LBL_EMPTY")}</span>
														</div>`;
											}
										}]		
									},
									{
										id: "view-main-queue-section-data-expand-" + priority.id + "-" + self.uid,
										view: "button",
										type: "icon",
										icon: "mdi mdi-plus-thick",
										label: i18n.t("LBL_SHOW_MORE"),
										hidden: true,
										align: "right",
										css: "expand-collapse-btn",
										on: {
											onItemClick: function() {
												
												let viewSectionDataInstance = $$("view-main-queue-section-data-" + priority.id + "-" + self.uid);
												
												if (viewSectionDataInstance && Array.isArray(viewSectionDataInstance.getChildViews()) && viewSectionDataInstance.getChildViews().length > 1) {
													
													viewSectionDataInstance.define("height", viewSectionDataInstance.getChildViews().length * QUEUE_COMMAND_HEIGHT);
													viewSectionDataInstance.resize();
													
													this.hide();
													$$("view-main-queue-section-data-collapse-" + priority.id + "-" + self.uid).show();
													
													$$("view-main-queue-section-data-" + priority.id + "-" + self.uid).config.expanded = true;
												}
												
												
											}
										}
									},
									{
										id: "view-main-queue-section-data-collapse-" + priority.id + "-" + self.uid,
										view: "button",
										type: "icon",
										icon: "mdi mdi-minus-thick",
										label: i18n.t("LBL_SHOW_LESS"),
										hidden: true,
										align: "right",
										css: "expand-collapse-btn",
										on: {
											onItemClick: function() {
												
												let viewSectionDataInstance = $$("view-main-queue-section-data-" + priority.id + "-" + self.uid);
												
												if (viewSectionDataInstance && Array.isArray(viewSectionDataInstance.getChildViews()) && viewSectionDataInstance.getChildViews().length > 1) {
													
													viewSectionDataInstance.define("height", QUEUE_COMMAND_HEIGHT);
													viewSectionDataInstance.resize();
													
													this.hide();
													$$("view-main-queue-section-data-expand-" + priority.id + "-" + self.uid).show();
													
													$$("view-main-queue-section-data-" + priority.id + "-" + self.uid).config.expanded = false;
												}
											}
										}
									}
								]
							},
							{ width: 20 }
						]
					},
					{ height : 20 }
				]
			
				viewQueue.rows[0].body.rows.push(...prioritySection);	
			});
		}
		
		return viewQueue;
	}
	
	update(element) {
		
		let self = this;
		let queue = element.queue;
		let previousElement = self.element ? webix.copy(self.element) : undefined;
		
		self.element = element;
		
		if (!previousElement || previousElement.id != element.id) {
			
			connectWS();
		}
			
		if (queue && Array.isArray(queue.priorities) && queue.priorities.length > 0) {
			
			queue.priorities.forEach(queuePriority => {
				
				let priority = webix.copy(self.priorities.find(e => e.id = queuePriority.id));
				
				if (priority) {
					
					priority.enable = queuePriority.enable;
					
					updateSection(priority);
					
						
				}
			});
		}
		
		function updateSection(priority) {
			
			let commandPosition = 1;
			
			let viewPriorityEnabledInstance = $$("view-main-queue-section-enabled-" + priority.id + "-title-" + self.uid);
				
			if (viewPriorityEnabledInstance) {
				
				self.lockPrioritySwitches = false;
				viewPriorityEnabledInstance.setValue(priority.enable ? 1: 0);
				self.lockPrioritySwitches = true;
				
				viewPriorityEnabledInstance.attachEvent("onChange", function(newValue, oldValue) {
								
					if (self.lockPrioritySwitches) {
						
						this.blockEvent();
						this.setValue(oldValue);
						this.unblockEvent();
						
						if (newValue != oldValue) {
						
							changePriority(newValue);	
						}
					}
//					
//					if (newValue != oldValue) {
//						
//						changePriority(newValue);	
//					}

					log.debug("change: " + oldValue + " -> " + newValue);
				});
				
				viewPriorityEnabledInstance.attachEvent("onItemClick", function(id) {
								
					log.debug(this.config.id + " click");
				});
				
				let viewSectionDataInstance = $$("view-main-queue-section-data-" + priority.id + "-" + self.uid);
			
				if (viewSectionDataInstance) {
					
					let viewSectionCommands;
					
					let commandElements = undefined;
					
					if (Array.isArray(queue.commandElements) && queue.commandElements.length > 0) {
						
						commandElements = queue.commandElements.filter(e => e.priority.id === priority.id);
					}
					
					let viewMainQueueSectionDataExpandInstance = $$("view-main-queue-section-data-expand-" + priority.id + "-" + self.uid);
					let viewMainQueueSectionDataCollapseInstance = $$("view-main-queue-section-data-collapse-" + priority.id + "-" + self.uid);
					
					if (Array.isArray(commandElements) && commandElements.length > 0) {
						
						commandElements.sort((a, b) => (a.enable ? 0 : 1) - (b.enable ? 0 : 1) || a.startTime - b.startTime);
						
						viewSectionCommands = {
							id: "view-main-queue-section-data-" + priority.id + "-" + self.uid,
							height: QUEUE_COMMAND_HEIGHT,
							rows: []
						}
						
						if (viewMainQueueSectionDataExpandInstance && viewMainQueueSectionDataCollapseInstance && commandElements.length > 1) {
						
							viewMainQueueSectionDataExpandInstance.show();
							viewMainQueueSectionDataCollapseInstance.hide();
						}
						
						commandElements.forEach(cmd => {
							
							viewSectionCommands.rows.push(getViewQueueElement(cmd));
						});
						
						if (viewMainQueueSectionDataExpandInstance && viewMainQueueSectionDataCollapseInstance && commandElements.length > 1 && viewSectionDataInstance.config.expanded) {
							
							viewSectionCommands.height = commandElements.length * QUEUE_COMMAND_HEIGHT;
							
							viewMainQueueSectionDataExpandInstance.hide();
							viewMainQueueSectionDataCollapseInstance.show();
						}
					}
					else {
					
						viewSectionCommands = {
							id: "view-main-queue-section-data-" + priority.id + "-" + self.uid,
							height: 40,
							rows: [{
								view: "template",
								borderless: true,
								template: function() {
				
									return `<div class='empty-app-inner'>
												<img style='height:24px; width:24px; margin-top: -5px !important;' src='img/not_found_150.png' align='center'>
												<span style='font-size:13px;' class='empty-app-inner-text'>${i18n.t("LBL_EMPTY")}</span>
											</div>`;
								}
							}]
						}
						
						if (viewMainQueueSectionDataExpandInstance) {
							
							viewMainQueueSectionDataExpandInstance.hide();	
						}
					}
					
					webix.ui(viewSectionCommands, viewSectionDataInstance);
				}
			}
			
			function changePriority(active) {
			
				util.element.setPriority(self.element.id, priority.id, active, function(res) {
						
					if (res) {
						
						(new MessageBox (MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null)).show();
					}
				}, true);
			}
			
			function getViewQueueElement(commandElement) {
				
				let viewQueueElement;
				
				viewQueueElement = {
					id: "view-main-queue-section-data-command-" + commandElement.id + "-" + self.uid,
					height: QUEUE_COMMAND_HEIGHT,
					cols: [
						getViewQueueElementOptions(),
						{
							rows: [
								{
									cols: [
										getViewQueueElementValue(),
										{}	
									]
								},
								getViewQueueElementValid()
							]
						}
					]
				}
				
				function getViewQueueElementOptions() {
					
					let viewQueueElementOptions;
					
					viewQueueElementOptions = {
						id: "queue-element-options-" + priority.id + "-" + commandElement.id + "-" + self.uid,
						cols: [
							{
								width: 40,
								rows: [
									{ height: 5 },
									{
										id: "queue-element-option-position-" + priority.id + "-" + commandElement.id + "-" + self.uid,
										css: { "margin-left": "10px !important"},
										view: "template",
										borderless: true,
										height: 30,
										template: function() {
											
											const QUEUE_COLOR_ACTIVE = "#009688";
											const QUEUE_COLOR_NO_ACTIVE = "#f44336";
											const QUEUE_COLOR_UNAVAILABLE = "#f44336";
											 
											let color = QUEUE_COLOR_UNAVAILABLE;
											
											let labelCommandPosition = i18n.t(priority.labelAlias) + " #" + commandPosition + ": ";
											
											if (priority.enable) {
												
												color = commandElement.enable ? QUEUE_COLOR_ACTIVE : QUEUE_COLOR_NO_ACTIVE;
												labelCommandPosition += commandElement.enable ? i18n.t("LBL_ACTIVE") : i18n.t("LBL_INACTIVE") 
											}
											else {
												
												labelCommandPosition += i18n.t("LBL_UNAVAILABLE");
											}
											
											return `<div title='${labelCommandPosition}' style='display: table-cell; vertical-align: middle; color: ${color};background-color: ${color + "50"}; width: 20px; height: 20px; text-align: center; border: 1px solid ${color}; border-radius: 5px; font-size: 10px; font-weight: bold' 
														<span>${commandPosition++}</span>
													</div>`;
										}
									},
									{ height : 5 },
									{
										cols: [
											{
												id: "queue-element-option-delete-" + priority.id + "-" + commandElement.id + "-" + self.uid,
												view: "icon",
												icon: "mdi mdi-trash-can-outline",
												tooltip: i18n.t("LBL_UNQUEUE_COMMAND"),
												on: {
													onItemClick: function() {
												
														util.element.deleteCommand([commandElement.id], function(res) {
															
															if (res) {
																
																(new MessageBox (MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null)).show();
															}
														}, true);	
													}
												}
											},
											{ width: 15 }
										]
									},
									{}
								]
							},
							{
								width: 5
							}
						]
					};
					
					return viewQueueElementOptions;
				}
				
				function getViewQueueElementValue() {
					
					let viewQueueElementValue;
					
					if (commandElement.commandElementType.id == self.config.commands.sendMessage.commandElementTypeId) {
						
						viewQueueElementValue = getViewGraphicIcon();
					}
					else {
						
						viewQueueElementValue = getViewOthers();
					}
					
					function getViewGraphicIcon() {
						
						let viewGraphicIcon;
						let svgId = webix.uid(); 
						
						viewGraphicIcon = {
							id: "queue-element-value-" + priority.id + "-" + commandElement.id + "-" + self.uid,
							height: self.maxElementQueueHeight,
							width: self.maxElementQueueWidth,
							borderless: true,
							graphicIconInstance: undefined,
							template: function() {
									
								return `<div>
											<svg id=${"vms-composer-svg-" + svgId} height="${self.maxElementQueueHeight}px"></svg>
										</div>`
							},
							on: {
								onAfterRender: function() {
				
									let config = this.config;
									
									drawGraphicIcon(svgId, function(graphicIconInstance) {
								
										config.graphicIconInstance = graphicIconInstance; 			
									});
								},
								onDestruct: function() {
						
									if (this.config.graphicIconInstance) {
										
										this.config.graphicIconInstance.stop();	
									}
								}
							}
						}
						
						return viewGraphicIcon;
					}
					
					function getViewOthers() {
						
						let viewOthers; 
						
						viewOthers = {
							id: "queue-element-value-" + priority.id + "-" + commandElement.id + "-" + self.uid,
							height: self.maxElementQueueHeight,
							width: self.maxElementQueueWidth,
							borderless: true,
							template: function() {
								
								return `<div>
											<image style="width: 32px; height: 32px; display: block; margin: 0 auto" src="img/${commandElement.commandElementType.image}" onerror="this.onerror=null;this.src='img/send-command-default.png';" />
											<span>${i18n.t(commandElement.commandElementType.labelAlias)}</span>
										</div>`
							}
						}
						
						return viewOthers;
					}
					
					function drawGraphicIcon(svgId, onAfterRender) {

						let property = self.config.properties.find(e => e.id === self.element.elementSubtype.id);
						let graphicIcon = undefined;
											
						if ((graphicIcon = property.graphicIcon) != null) {
							
							graphicIcon.width = self.maxElementQueueWidth;
							graphicIcon.height = self.maxElementQueueHeight;
							
							const lazy = async _ => {
					
								let jsFile = util.getContextPath() + '/' + graphicIcon.jsDrawFile; 
					
								const mod = await import(jsFile);
								
								let layerElement = {
									graphicIcon: graphicIcon
								}
								
								let s = Snap("#" + "vms-composer-svg-" + svgId);
								s.attr({ width: self.maxElementQueueWidth, height: self.maxElementQueueHeight });
								
								let container = { 
									type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW, 
									obj: s
								}
								
								let graphicIconInstance = new mod.default(layerElement, self.element, container);
								
								graphicIconInstance.load(function() {
									
									graphicIconInstance.loadRender(function() {
										
										graphicIconInstance.updateState({
											id: "ElementTypeState:71:1"
										});
										
										let jsonValue = [];
										
										if (Array.isArray(commandElement.commandElementValues) && 
											commandElement.commandElementValues.length > 0) {
											
											let commandElementValue = commandElement.commandElementValues.find(e => e.commandElementTypeParam.id === self.config.commands.sendMessage.commandElementValues[0].commandElementTypeParamId);
										
											if (commandElementValue) {
												
												jsonValue = JSON.parse(commandElementValue.value); 
											}	
										}
										
										let elementValueJsonData = {
		
											elementTypeParam: {
												id: self.config.vms.jsonData
											},
											value: JSON.stringify(jsonValue)
										}
																				
										graphicIconInstance.updateValues([elementValueJsonData]);	
										
										if (onAfterRender) {
								
											onAfterRender(graphicIconInstance);
										}
									});
								});	
							}
							
							lazy();	
						}
					}
					
					return viewQueueElementValue;
				}
				
				function getViewQueueElementValid() {
					
					let viewQueueElementValid;
					
					
					let validSince = util.toLocaleString(commandElement.startTime / 1000);
					let validUntil = util.toLocaleString(commandElement.endTime / 1000);
					
					viewQueueElementValid = {
						id: "queue-element-valid-" + priority.id + "-" + commandElement.id + "-" + self.uid,
						borderless: true,
						tooltip: i18n.t("LBL_SINCE") + ": " + validSince + " " + i18n.t("LBL_UNTIL") + ": " + validUntil, 
						template: function() {
							
							return `<div>
										<span><p style='font-size:11px; font-style:italic; display: block; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; margin-top: 0px;'><strong>${i18n.t("LBL_SINCE")}</strong>: ${validSince} <strong>${i18n.t("LBL_UNTIL")}</strong>: ${validUntil}</p></span>
									</div>`;
						}
					};
					
					return viewQueueElementValid;
				}
				
				return viewQueueElement;
			}		
		}
		
		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {
				
				if (self.idStompSubscriptor) {
				
					stompSubscriptorService.unsubscribe(self.idStompSubscriptor);	
				}

				if (self.element) {

					let topic = self.config.topics.elementQueue + "/" + self.element.id;
					self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessage);

				}
			}

			function onMessage(data) {

				if (data) {
					
					refresh();						
				}
			}
		}
		
		function refresh() {
			
			let loadingView = {
				
				id: "view-main-queue-" + self.uid,
				rows: [
					util.ui.getViewDrawLoading(true)
				] 
			}
			
			webix.ui(loadingView, $$("view-main-queue-" + self.uid));
		
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: self.element.id })).then(function(elementResponse) {
				
				webix.ui(self.getView(), $$("view-main-queue-" + self.uid));
				
				if (elementResponse) {
					
					self.update(elementResponse.json());	
				}
			});	
		}
	}
}	

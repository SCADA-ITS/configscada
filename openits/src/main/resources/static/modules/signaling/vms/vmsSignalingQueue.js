import { util } from "../../../lib/util.js";
import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";

const QUEUE_COMMAND_HEIGHT = 100;
const VMS_MAX_WIDTH = 300;
const VMS_MAX_HEIGHT = 100;

export default class VmsSignalingQueue {
	
	constructor(config, priorities) {

		this.config = config;
		this.priorities = priorities;
		this.element = undefined;
		
		this.idStompSubscriptorTube = undefined;
		this.uid = webix.uid();
	}
	
	getView() {
		
		let self = this;
		
		let viewQueue;
		
		if (Array.isArray(self.priorities) && self.priorities.length > 0) {
			
			viewQueue = {
				
				id: "view-main-queue-" + self.uid,
				view: "scrollview",
				scroll: "y",
				body: {
					rows: []	
				}
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
								value: 1,
								on: {
									onChange: function(newValue, oldValue, config) {
										
										this.blockEvent();
										this.setValue(oldValue);
										this.unblockEvent();
										
										changePriority(this.config.priorityId, newValue);
									}
								}
							},
							{ width: 10 }
						]
					},
					{ height : 5 },
					{
						cols: [
							{ width: 20 },
							{
								id: "view-main-queue-section-data-" + priority.id + "-" + self.uid,
								height: QUEUE_COMMAND_HEIGHT,
								rows: [{
									view: "template",
									template: function() {
					
										return `<div class='empty-app-inner'>
													<img style='height:40px; width:40px; margin-top: -20px !important;' src='img/not_found_150.png' align='center'>
													<span style='font-size:14px;' class='empty-app-inner-text'>${i18n.t("LBL_EMPTY")}</span>
												</div>`;
									}
								}]
							},
							{ width: 10 }
						]
					},
					{ height : 20 }
				]
			
				viewQueue.body.rows.push(...prioritySection);	
			});
		}
		
		function changePriority(priorityId, active) {
			
			(new MessageBox (MessageBox.CONFIRM, active ? i18n.t("LBL_ENABLE_PRIORITY_QUESTION") : i18n.t("LBL_DISABLE_PRIORITY_QUESTION"), onAccept, null)).show();
			
			function onAccept() {
				
			}
		}
		
		return viewQueue;
	}
	
	update(element) {
		
		let self = this;
		let queue = element.queue;
		
		if (!self.element) {
			
			connectWS();
		}
		
		self.element = element;
			
		if (queue && Array.isArray(queue.priorities) && queue.priorities.length > 0) {
			
			queue.priorities.forEach(priority => {
				
				let viewPriorityEnabledInstance = $$("view-main-queue-section-enabled-" + priority.id + "-title-" + self.uid);
				
				if (viewPriorityEnabledInstance) {
					
					viewPriorityEnabledInstance.setValue(priority.enable ? 1: 0);
					
					updateSection(priority);
				}
			});
		}
		
		function updateSection(priority) {

			let viewSectionDataInstance = $$("view-main-queue-section-data-" + priority.id + "-" + self.uid);
			
			if (viewSectionDataInstance) {
				
				let viewSectionCommands;
				
				let commandElements = undefined;
				
				if (Array.isArray(queue.commandElements) && queue.commandElements.length > 0) {
					
					commandElements = queue.commandElements.filter(e => e.priority.id === priority.id);
				}
				
				if (Array.isArray(commandElements) && commandElements.length > 0) {
					
					viewSectionCommands = {
						id: "view-main-queue-section-data-" + priority.id + "-" + self.uid,
						rows: []
					}
					
					commandElements.forEach(cmd => {
						
						viewSectionCommands.rows.push(getViewQueueElement(cmd));
					});
				}
				else {
				
					viewSectionCommands = {
						id: "view-main-queue-section-data-" + priority.id + "-" + self.uid,
						height: QUEUE_COMMAND_HEIGHT,
						rows: [{
							view: "template",
							template: function() {
			
								return `<div class='empty-app-inner'>
											<img style='height:40px; width:40px; margin-top: -20px !important;' src='img/not_found_150.png' align='center'>
											<span style='font-size:14px;' class='empty-app-inner-text'>${i18n.t("LBL_EMPTY")}</span>
										</div>`;
							}
						}]
					}					
				}
				
				webix.ui(viewSectionCommands, viewSectionDataInstance);
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
								getViewQueueElementValue(),
								getViewQueueElementValid()
							]
						}
					]
				}
				
				function getViewQueueElementOptions() {
					
					let viewQueueElementOptions;
					
					viewQueueElementOptions = {};
					
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
							height: VMS_MAX_HEIGHT,
							width: VMS_MAX_WIDTH,
							borderless: true,
							graphicIconInstance: undefined,
							template: function() {
									
								return `<div>
											<svg id=${"vms-composer-svg-" + svgId} height="${VMS_MAX_HEIGHT}px"></svg>
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
							height: VMS_MAX_HEIGHT,
							width: VMS_MAX_WIDTH,
							borderless: true,
							on: {
								onAfterRender: function() {
				
									drawOthers();
								}
							}
						}
						
						return viewOthers;
					}
					
					function drawGraphicIcon(svgId, onAfterRender) {

						let property = self.config.properties.find(e => e.id === self.element.elementSubtype.id);
						let graphicIcon = undefined;
											
						if ((graphicIcon = property.graphicIcon) != null) {
							
							graphicIcon.width = VMS_MAX_WIDTH;
							graphicIcon.height = VMS_MAX_HEIGHT;
							
							const lazy = async _ => {
					
								let jsFile = util.getContextPath() + '/' + graphicIcon.jsDrawFile; 
					
								const mod = await import(jsFile);
								
								let layerElement = {
									graphicIcon: graphicIcon
								}
								
								let s = Snap("#" + "vms-composer-svg-" + svgId);
								s.attr({ width: VMS_MAX_WIDTH, height: VMS_MAX_HEIGHT });
								
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
					
					function drawOthers() {
						
						let viewOthers;
						
						viewOthers = {};
						
						return viewOthers;
					}
					
					return viewQueueElementValue;
				}
				
				function getViewQueueElementValid() {
					
					let viewQueueElementValid;
					
					viewQueueElementValid = {};
					
					return viewQueueElementValid;
				}
				
				return viewQueueElement;
			}		
		}
		
		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.element) {

					let topic = self.config.topics.topics + "/" + self.element.id;
					self.idStompSubscriptorTube = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessage);

				}
			}

			function onMessage(data) {

				if (data) {
					
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: self.element.elementId })).then(function(elementResponse) {
			
						if (elementResponse) {
							
							self.update(elementResponse.json());	
						}
					});
			
				}
			}
		}
	}
}	

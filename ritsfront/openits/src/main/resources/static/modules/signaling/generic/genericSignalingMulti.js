import { util } from "../../../lib/util.js";

export default class GenericSignalingMulti {

	constructor(config, availableElements, selectedElements, currentElement, onAccept) {
		
		this.uid = webix.uid();
		this.config = config;
		this.availableElements = availableElements;
		this.selectedElements = selectedElements;
		this.currentElement = currentElement;
		this.onAccept = onAccept;
		
		this.containerId = undefined;
	}

	show() {

		let self = this;	
		let view = {};
		
		view = {
			id: self.uid,
			rows: [
				drawHeader(),
				{
					cols: [
						{ width: 20 },
						drawContent(),
						{ width: 20 }		
					]	
				},
				{
					cols: [
						{ width: 20 },
						drawActionButtons(),
						{ width: 20 }		
					]	
				}
			]
		};
		
		function drawHeader() {
			
			let viewHeader = {};
			let label = i18n.t(self.currentElement.elementType.labelAlias);
			let description = i18n.t(self.currentElement.elementType.labelDescription);

			viewHeader = {

				css: "global-header",
				cols: [
					{
						view: "label",
						label: "<img src='img/books.png' class='grid-icon'/>",
						height: 75,
						css: "icon"
					},
					{
						view: "label",
						height: 75,
						template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + description + "</span>",
						css: "label"
					}
				]
			};

			return viewHeader;
		}
		
		function drawContent() {
			
			let viewContent = {
				id: "view-generic-multi-" + self.uid,
				css: { "background-color": "white"},
				rows: [
					{ height: 20 },
					getViewLists(),
					{ height: 20 }
				]		
			};
			
			function getData() {
				
				let data = [];
				
				if (Array.isArray(self.availableElements) && self.availableElements.length > 0) {
					
					data = self.availableElements.map(e => {
						
						return {
							id: e.id,
							value: e.alias		
						}
					});
				}
				
				return data;
			}
			
			function getSelected() {
				
				let data = [];
				
				if (Array.isArray(self.selectedElements) && self.selectedElements.length > 0) {
					
					data = self.selectedElements.map(e => e.id);
				}
				
				return data;
			}
			
			function getViewLists() {
			
				let viewLists;
				
				viewLists = { 
					id: "view-generic-multi-dbllist-" + self.uid,
					view: "dbllist",
					css: "dbllist",
				  	labelLeft: i18n.t("LBL_AVAILABLE"),
				  	labelRight: i18n.t("LBL_SELECTED"),
				  	data: getData(),
					value: getSelected(),
					on: {
						onChange() {
								
							let selectedValuesStr = this.getValue();
							let acceptButton = $$("btn-multi-accept-" + self.uid);

							if (acceptButton){
								
								if(selectedValuesStr == "") {
								
									acceptButton.disable();	

								} else {

									acceptButton.enable();
								}								
							}
						}
					}
				};
				
				return viewLists;
			}
			
			return viewContent;
		}
		
		function drawActionButtons() {
			
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					{ 
						view: "button",
						css: "dialog-cancel", 
						width: 130,
						height: 35, 
						label: i18n.t("LBL_CANCEL"),
						on: {
							onItemClick() {
							
								$$(self.containerId).close();
							}
						}
					},
					{},
					{ 
						id: "btn-multi-accept-" + self.uid,
						view: "button", 
						css: "dialog-accept",
						width: 130,
						height: 35,
						label: i18n.t("LBL_ACCEPT"),
						on: {
							onItemClick() {
		
								let dblList = $$("view-generic-multi-dbllist-" + self.uid);
								
								if (self.onAccept && dblList) {
									
									let valueStr = dblList.getValue();
									
									if (valueStr != "") {
										
										let values = valueStr.split(",");
										
										let selectedElements = self.availableElements.filter(e => values.includes(e.id));
									
										self.onAccept(selectedElements);
										
										$$(self.containerId).close();
									}
								}
							}
						}
					}
				]
			};
			
			return viewToolbarActions;
		}
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_MULTI_GENERIC"), view, 550, 800, true);
	}
}	

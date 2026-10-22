import { util } from "../../lib/util.js";

export let travelTimeConfigUtils = {

	async getTravelTime(config, elementId) {
			
		let elementResponse = await webix.ajax().get(appConfig.restUrl + util.fillTemplate(config.restEndpoints.element, { id: elementId }));
		
		if (elementResponse) {
			
			let travelTime = undefined;
			let element = elementResponse.json();
			
			if (element) {
				
				travelTime = {};
				travelTime.id = element.id;
				travelTime.alias = element.alias;
				travelTime.description = element.description;
				
				if (Array.isArray(element.elementValues)) {
					
					let directionParam = element.elementValues.find(e => e.elementTypeParam.id === config.travelTime.params.direction);
					let pkIniParam = element.elementValues.find(e => e.elementTypeParam.id === config.travelTime.params.pkIni);
					let pkEndParam = element.elementValues.find(e => e.elementTypeParam.id === config.travelTime.params.pkEnd);
					let messageParam = element.elementValues.find(e => e.elementTypeParam.id === config.travelTime.params.message);
					let panelsParam = element.elementValues.find(e => e.elementTypeParam.id === config.travelTime.params.panels);
					
					travelTime.descendingDirection = directionParam ? IS_TRUE(directionParam.value) : false;
					travelTime.pkIni = pkIniParam ? pkIniParam.value : undefined;
					travelTime.pkEnd = pkEndParam ? pkEndParam.value : undefined;
					travelTime.message = messageParam ? messageParam.value : undefined;
					travelTime.panels = panelsParam ? panelsParam.value : undefined;
				}
				
				if (Array.isArray(element.childs)) {
					
					travelTime.sections = element.childs.map(child => {
						
						let pk = undefined;
						
						if (Array.isArray(child.elementValues)) {
							
							let elementValuePk = child.elementValues.find(ev => ev.elementTypeParam.id === config.section.params.pk);
							
							pk = (elementValuePk && !isNaN(elementValuePk.value)) ? parseFloat(elementValuePk.value) : undefined;
						} 
						
						return {
							
							id: child.id,
							alias: child.alias,
							pk: pk 
						}
					});
				}
			}
			
			return travelTime;
		}
	}
};
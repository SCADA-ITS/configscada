export default class DialogEntitiesRepository {

	constructor() {

	}

	get(dialogEntity) {
		
		let data = undefined;
		let metadata = undefined;
		
		let getFieldName = function(field) {
				
			if (field) {
			
				let offset = field.indexOf("=");
				
				if (offset !== -1) {
					
					return field.substring(0, offset);
				}	
			}
			
			return field;
		};
		
		let getFieldLabel = function(field) {
			
			if (field) {
			
				let offset = field.indexOf("=");
				
				if (offset !== -1) {
					
					return field.substring(offset + 1);
				}	
			}
		};
		
		let getData = function() {
			
			let getFromEntitiesManager = function(em_type) {

				return window.em.getAll(em_type);
			};

			if (dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_EM) {
					
				let entities = getFromEntitiesManager(EntityType[dialogEntity.endpoint]);
				
				if (Array.isArray(entities) && entities.length > 0) {
					
					return entities.map(function(e) {
						
						let output = {};
						
						output.id = e[dialogEntity.pkField];
						
						dialogEntity.fields.forEach(field => {
							
							let fieldName = getFieldName(field); 
							
							output[fieldName] = e[fieldName];
						});
						
						return output;
					});
				}
			}
			else if (dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_REST) {
				
				// TODO
			}
		};
		
		let getMetadata = function() {
			
			let gridMetadata = {};
			
			if (dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
				
				const gridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "true" },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 10 }, value: "true" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 12 }, value: "false" },
					{ gridOption: { id: 13 }, value: "true" },
					{ gridOption: { id: 14 }, value: "false" },
					{ gridOption: { id: 15 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "auto" }
				];
				
				gridMetadata = {
					filter: true,
					label: "LBL_LOGS",
					visible: true,
					gridOptionValues: gridOptionValues,
					gridFields: []
				};
				
				let position = 0;
				
				gridMetadata.gridFields.push({
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: true,
					needsTranslation: false,
					parameterized: false,
					position: ++position,
					required: true,
					sourceField: dialogEntity.pkField,
					visible: false,
					gridFieldDataType: {
						id: 1,
						name: "string"
					}
				});
				
				dialogEntity.fields.forEach(field => {
							
					let fieldName = getFieldName(field);
					let fieldLabel = getFieldLabel(field);
					
					let adjust = (position + 1 >= dialogEntity.fields.length);
					
					gridMetadata.gridFields.push({
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: fieldLabel,
						needsTranslation: false,
						parameterized: false,
						position: ++position,
						required: true,
						adjust: adjust,
						sourceField: fieldName,
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					});
				});
			}
			
			return gridMetadata;
		};
		
		data = getData();
		metadata = getMetadata();
		
		return { type: dialogEntity.dataType, data: data, metadata: metadata };
	}
}

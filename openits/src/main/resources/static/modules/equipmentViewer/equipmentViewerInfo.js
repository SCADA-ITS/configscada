import EquipmentViewerInfoMeasure from "./equipmentViewerInfoMeasure.js";
import { util } from "../../lib/util.js";

export default class EquipmentViewerInfo {
	
	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.equipmentViewerInfoMeasure = undefined;
	}
	
	getView() {
		
		let view = {};
		
		this.equipmentViewerInfoMeasure = new EquipmentViewerInfoMeasure(this.parent, this.element, this.equipmentViewer);
		
		view = {
			rows: [
				{},
				this.equipmentViewerInfoMeasure.getView()
			]
		}
		
		return view;
	}
	
	updateMeasures(elementValues) {

		if (this.equipmentViewerInfoMeasure) {
			
			this.equipmentViewerInfoMeasure.updateMeasure(elementValues);
		}
	}
	
	refresh() {
		
		if (this.equipmentViewerInfoMeasure) {
			
			this.equipmentViewerInfoMeasure.refresh();
		}
	}
}	

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
				{ height: 300 },
				this.equipmentViewerInfoMeasure.getView()
			]
		}
		
		return view;
	}
	
	refresh() {
		
		if (this.equipmentViewerInfoMeasure) {
			
			this.equipmentViewerInfoMeasure.refresh();
		}
	}
}	

import InputDialogBox from "../../component/inputDialogBox.js";
import { util } from "../../lib/util.js";

export default class IncidentTypeContent {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "incident-type-content-" + self.uid,
			css: { "background-color": "white" },
			rows: [
				drawHeader(),
				drawIncidentTypeDetail(),
				drawResizer(),
				drawIncidentTypeTask()
			]
		};
		
		function drawHeader() {

			let viewHeader;
			
			viewHeader = {}
			
			return viewHeader;
		}
		
		function drawIncidentTypeDetail() {
			
			let viewPlan;
			
			viewPlan = {}
			
			return viewPlan;
		}
		
		function drawResizer() {

			let viewResizer = {}

			viewResizer = {
				view: "resizer"
			}

			return viewResizer;
		}
		
		function drawIncidentTypeTask() {
			
			let viewIncidentTypeTask; 
			
			viewIncidentTypeTask = {}
			
			return viewIncidentTypeTask;
		}
			
		return view;
	}
}	

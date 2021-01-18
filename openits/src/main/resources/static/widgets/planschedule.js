import PlanSchedule from "../classes/PlanSchedule.js";

let planSchedule = new PlanSchedule();

export let planschedules=() =>{
	let result = {
		id:"schelduleWidget",
		body: planSchedule.getComponent(),
		on: {
			onViewShow: function(){
				planSchedule.loadData()
			},
			onHide: function(obj){
				planSchedule.disconnect();
			}
		}
	};
	return result;
} 

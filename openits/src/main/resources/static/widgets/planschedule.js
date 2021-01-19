import PlanSchedule from "../classes/PlanSchedule.js";


export let planschedules=() =>{
	let planSchedule = new PlanSchedule();
	let result = {
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

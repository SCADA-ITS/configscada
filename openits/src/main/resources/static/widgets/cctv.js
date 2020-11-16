import CCTV from "../classes/CCTV.js";

let cctvInstance = new CCTV();
export let cctv = {
	id: "cctvWidget",
	body: cctvInstance.getComponent(),
	on: {
		onViewShow: function(){
			cctvInstance.loadData()
		},
		onHide: function(obj){
			cctvInstance.disconnect();
		}
	}
};

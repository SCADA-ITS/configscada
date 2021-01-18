import CCTV from "../classes/CCTV.js";

export function cctv(){
	let widgetId=webix.uid();
	let cctvInstance = new CCTV(widgetId);
	let result ={
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
	return result;
}
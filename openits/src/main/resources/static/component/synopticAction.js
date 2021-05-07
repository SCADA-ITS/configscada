export default class SynopticAction {

	constructor(uid) {

		this.uid = uid;
	}

	getView() {

		let self = this;
		let view;

		view = {
			
			id: "synoptic-action-" + self.uid,
			css: { "background": "white" },
			template: "TODO: Synoptic action list"
		}
		
		return view;
	}
}

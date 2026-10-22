import SearchDialogBox from "./searchDialogBox.js";

export default class TableMessageBox {

	static get INFO() { return { id: 1, color: "#3498db", icon: "./img/info.png", title: "LBL_INFO" } }
	static get WARN() { return { id: 2, color: "#fdbf4c", icon: "./img/warn.png", title: "LBL_WARN" } }
	static get ERROR() { return { id: 3, color: "#ff5c4c", icon: "./img/error.png", title: "LBL_ERROR" } }
	static get CONFIRM() { return { id: 4, color: "#3498db", icon: "./img/question.png", title: "LBL_CONFIRM" } }

	constructor(title, messageType, table, onAccept) {

		this.title = title;
		this.messageType = messageType;
		this.table = table;
		this.onAccept = onAccept;

		this.uid = webix.uid();
	}

	show() {
		
		let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
		dialogEntity.data = this.table.data;
		dialogEntity.icons = this.table.icons;
		dialogEntity.fields = this.table.fields;
		dialogEntity.pkField = this.table.pkField;
		dialogEntity.sortFieldId = this.table.sortFieldId;
		dialogEntity.messageType = this.messageType;
		dialogEntity.height = this.table.height;
		dialogEntity.width = this.table.width;
		dialogEntity.multiselect = this.table.multiselect;

		let s = new SearchDialogBox(this.title, dialogEntity, this.onAccept);
		s.show();
	}

}
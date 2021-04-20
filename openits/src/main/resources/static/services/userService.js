import GenericModule from "../modules/genericModule.js";
import UserControlPanel  from "../component/general/userControlPanel.js";

export default class UserService extends GenericModule {
	
	constructor(module, mod){
		
		super(module, null, null);
		
		this.module = module;
		this.config = module.config;
		this.mod = mod;
		
		this.userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
		
		this.users = {};
		this.stomp = {};
	}
	
	getView() {

		let views = [];
		
		let labelUser = { 
			id: "label-user-service",
			view: "label", 
			label: this.userData.name, 
			align: "right", 
			maxWidth: 350 
		};
		
		let iconUser = { 
			id: "icon-user-service", 
			view: "icon", 
			css: "user-template",
			//width: 75,
			badge: "",
			icon: "mdi mdi-account-circle", 
			value:"User",
			tooltip: i18n.t("LBL_USER") 
		};
			
		views.push(labelUser);
		views.push(iconUser);
		
		return views;
	}
	
	loadData() {
		
		let self = this;
		let moduleValue = this.module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_SERVICE_CONFIG);
		
		if (moduleValue != undefined) {
			
			if (!window.userControlPanel) {

				window.userControlPanel = new UserControlPanel(this.config);
			}
			
			$$("icon-user-service").attachEvent("onItemClick", function(id, e) {

				self.callUserControlPanel();
			});
			
			this.users.stats = {};
			
			if (moduleValue.value) {
			
				this.users.config = JSON.parse(moduleValue.value);	
			}
			
			this.updateStatistics(); 
		}
	}
	
	updateStatistics() {
	
		this.updateUsers();
	}
	
	updateUsers() {
		
	}
	
	callUserControlPanel() {

		if (window.userControlPanel.hidden()) {
			window.userControlPanel.show();
		} else {
			window.userControlPanel.hide();
		}
	}
}

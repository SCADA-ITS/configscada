import { util } from "../../lib/util.js";
import GenericModule from "../../modules/genericModule.js";
import TableMessageBox from "../../component/tableMessageBox.js";

export default class AboutModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;

		this.clientInfo = undefined;
		this.serverInfo = undefined;
		this.systemParams = undefined;
	}

	loadData() {

		let self = this;
		
		$$(self.uid).hide();
		$$("loading-" + self.uid).show();
		
		let promises = [];
		let dbSystemParams = [];
		
		Object.keys(self.config.dbSystemParams).forEach(function(key) {
		    
		    dbSystemParams.push(self.config.dbSystemParams[key]);
		});

		promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.clientInfo));
		promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.serverInfo));
		promises.push(webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.systemParams, JSON.stringify(dbSystemParams)));

		webix.promise.all(promises).then(function(responses) {

			if (responses.length === 3) {
				
				self.clientInfo = responses[0].json();
				self.serverInfo = responses[1].json();
				self.systemParams = responses[2].json();
				
				webix.ui(getViewMain(), $$(self.uid));
				
				$$("loading-" + self.uid).hide();
				$$(self.uid).show();
			}
		});
				
		function getViewMain() {
			
			let viewMain;
			
			viewMain = {
				id: self.uid,
				rows: [
					{
						view: "template",
						css: "splash-screen-logo-inner",
						height: 300,
						template: function(data) {
							
							let logo = "<img src='img/open_its_full.png' align='center'>";
			
							return `<div class="splash-screen-logo-inner">${logo}</div>`;
						}		
					},
					{ height: 50 },
					{
						cols: [
							{ width: 100 },
							{
								view: "template",
								borderless: true,
								template: function(data) {
									
									const year = new Date().getFullYear();
									const company = "Revenga Ingenieros, S.A."
							
									return `<div style='text-align: center'>
												<p>${(APP_TITLE ? APP_TITLE : "OpenITS") + " " + i18n.t("LBL_VERSION") + ": " + "1.0.0"}</p>
												<p>${"Copyright " + year + " " + company + i18n.t("LBL_ALL_RIGHTS_RESERVED") }</p>
											</div>`;
								}
							},
							{ width: 100 }
						]
					},
					{},
					{
						cols: [
							{},
							{
								view: "label",
								label: i18n.t("LBL_COMPONENTS_INFO"),
								align: "right",
								width: 350,
								css: "label-window-alarms",
								on: {
									onItemClick: function(id) {
		
										showComponentsInfo()
									}
								}
							},
							{ width: 50 }
						]
					}
				]
			};
			
			return viewMain;
		}
		
		function showComponentsInfo() {
			
			let label = i18n.t("LBL_COMPONENTS_INFO");
		
			let table = {
				fields: ["component=LBL_ALIAS", "version=", "date=LBL_DATE", "commit="],
				pkField: "id",
				data: [],
				width: 800,
				height: 450
			};
			
			if (self.clientInfo && self.clientInfo.gitInfo) {
					
				table.data.push({
					id: (APP_TITLE ? APP_TITLE : "OpenITS"),
					component: (APP_TITLE ? APP_TITLE : "OpenITS"), 
					version: "1.0.0",
					date: self.clientInfo.gitInfo.date,
					commit: self.clientInfo.gitInfo.commit
				});
			}
			
			if (Array.isArray(self.systemParams) && self.systemParams.length === Object.keys(self.config.dbSystemParams).length) {
					
				let commit = self.systemParams.find(e => e.id === self.config.dbSystemParams.commit);
				let date = self.systemParams.find(e => e.id === self.config.dbSystemParams.date);
				
				table.data.push({
					id: i18n.t("LBL_DATABASE"),
					component: i18n.t("LBL_DATABASE"), 
					version: i18n.t(""),
					date: date ? date.value : undefined,
					commit: commit ? commit.value : undefined
				});
			}
			
			if (Array.isArray(self.serverInfo)) {
				
				table.data.push(...self.serverInfo.map(e => {
					
						let component = e.main;
						let jarExtIdx = e.main.indexOf(".jar"); 
						if (jarExtIdx > -1) {
							
							component = e.main.substring(0, jarExtIdx);
							
							let fileSeparatorIdx = component.lastIndexOf("/");
							
							if (fileSeparatorIdx > -1) {
								
								component = component.substring(fileSeparatorIdx + 1);	
							}
						}
						
						return {
							id: component,
							component: component, 
							version: "",
							commit: e.gitCommit,
							date: e.gitDate
						}
					})
				);
			}
			
			(new TableMessageBox(label, TableMessageBox.INFO, table)).show()
		}
	}
}

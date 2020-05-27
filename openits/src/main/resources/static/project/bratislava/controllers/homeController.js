import { menu_data_multi, toolbar_data_menu } from "../data/dataProject.js";

import { equipments } from "../../../widgets/equipments.js";
import { bratislava } from "../../../widgets/bratislava.js";
import { alarms } from "../../../widgets/alarms.js";
import { plans } from "../../../widgets/plans.js";
import { users } from "../../../widgets/users.js";
import { roles } from "../../../widgets/roles.js";
import { alarmconfigs } from "../../../widgets/alarmconfigs.js";
import { banner } from "../../../widgets/banner.js";

let getActiveAlarms = webix.proxy(  "GraphQL", `query {
		  getGenericData(genericDataId:"GenericData:1") {
			    id
			    value
				}
			}`);

	let getNoRecognizeAlarms = webix.proxy(  "GraphQL", `query {
			  getGenericData(genericDataId:"GenericData:2") {
				    id
				    value
					}
				}`);

let grafana = {view:"iframe", src:  webix.storage.session.get("url.grafana") };
let jasper = {view:"iframe", src:  webix.storage.session.get("url.jasper")};

toolbar_data_menu.elements.find(x => x.icon === "mdi mdi-menu").click=function(){$$("$sidebar1").toggle();};
toolbar_data_menu.elements.find(x => x.icon === "webix_icon wxi-pencil").click=function(){changeStyle();};

var isLogin = webix.storage.session.get("loginSession");
if (!isLogin) {
    window.location = "./login.html";
}

let main_body = {
    cols: [{
            view: "sidebar",
            data: menu_data_multi,
            multiview: true,
            on: {
                onAfterSelect: function(id) {
                    $$("multiview").setValue(id);
                }
            },
            ready: function() {
                let firstItem = this.getFirstId();
                this.select(firstItem);
            }
        },
        {
            id: "multiview",
            cells: [
            {
                id: "alarms",
                rows: [alarms]
            },
            {
                id: "users",
                rows: [users]
            },
            {
                id: "roles",
                rows: [roles]
            },
            {
                id: "alarmconfigs",
                rows: [alarmconfigs]
            },
            {
                id: "equipments",
                rows: [equipments]
            },
            {
                id: "plans",
                rows: [plans]                            
            },
            {
            	id: "bratislava",
                rows: [bratislava]
            },
            {
                id: "jasper",
                rows: [jasper]
            }]
        }
    ]
};

webix.ready(function() {
    webix.ui({
    	id:"mainBody",
        rows: [
            toolbar_data_menu,
            main_body,
            banner
        ]
    });
    webix.extend($$("mainBody"), webix.ProgressBar);

    getActiveAlarms.load().then(data => {
    	try {
    		$$("bell").data.badge=data.value;
    		$$("bell").refresh();
    	} catch (err) {
    		console.log("Error: " + err.message);
    	}
    });
    
    getNoRecognizeAlarms.load().then(data => {
    	try {
    		if(data== null ||  data.value == 0){
    			$$("bannerTemp").hide();
    		}
    		else{
        		$$("bannerTemp").setHTML("<div class='move-me move-me-3'>Alarmas no reconocidas: "+data.value+"</div>");
        		$$("bannerTemp").show();
    		}
    	} catch (err) {
    		console.log("Error: " + err.message);
    	}
    });
    
    $$("nameUser").setValue(userName);
    
    if (userPermissions !== "") {
        let userPermissionsList = userPermissions.split(",");
        if (userPermissionsList.includes("SystemPermission:2")) {//Edit User
            console.log();
        } else {
            $$("b_new_user").disable();
            $$("b_edit_user").disable();
            $$("b_delete_user").disable();	
        }
        if (userPermissionsList.includes("SystemPermission:3")) {//Edit Role
            console.log();
        } else {
            $$("b_new_role").disable();
            $$("b_edit_role").disable();
            $$("b_delete_role").disable();	
        }
    } else {
        $$("b_new_user").disable();
        $$("b_edit_user").disable();
        $$("b_delete_user").disable();

        $$("b_new_role").disable();
        $$("b_edit_role").disable();
        $$("b_delete_role").disable();
    }
});

function changeStyle(){
    let styleCss = "compact";
    switch (Math.floor(Math.random()*10)) {
        case 0,1:
        styleCss="contrast";
        break;
        case 2,3:
        styleCss="flat";
        break;
        case 4,5:
        styleCss="material";
        case 6,7:
        styleCss="compact";
        default:
        styleCss="mini";
        break;
    }
    if(document.getElementById("pagestyle").getAttribute("href")==="./lib/webix/skins/"+styleCss+".css"){
        changeStyle();
    }else{
        document.getElementById("pagestyle").setAttribute("href", "./lib/webix/skins/"+styleCss+".css");
    }
}

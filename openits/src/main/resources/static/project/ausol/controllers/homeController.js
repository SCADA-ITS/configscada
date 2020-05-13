import { menu_data_multi, toolbar_data_menu } from "../data/dataProject.js";
import { metro } from "../widgets/metro.js";
import { global } from "../widgets/global.js";
import { synoptic } from "../widgets/synoptic.js";
import { equipments } from "../widgets/equipments.js";

import { alarms } from "../../../widgets/alarms.js";
import { plans } from "../../../widgets/plans.js";
import { users } from "../../../widgets/users.js";
import { roles } from "../../../widgets/roles.js";
import { alarmconfigs } from "../../../widgets/alarmconfigs.js";
import { banner } from "../../../widgets/banner.js";

let getAlrams = webix.proxy(  "GraphQL", `query {
	  getGenericData(genericDataId:"GenericData:1") {
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
                id: "metro",
                rows: [metro]
            },{
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
                id: "synoptic1",
                rows: [synoptic]
            },
            {
                id: "plans",
                rows: [plans]                            
            },
            {
                id: "global",
                rows: [global]
            },
            {
                id: "grafana",
                rows: [grafana]
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

    getAlrams.load().then(data => {
    	try {
    		console.log(data);
    		$$("bell").data.badge=data.value;
    		$$("bell").refresh();
    		$$("bannerTemp").setHTML("<div class='move-me move-me-3'>Alarmas activas: "+data.value+"</div>");
    	} catch (err) {
    		console.log("Error: " + err.message);
    	}
    });
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

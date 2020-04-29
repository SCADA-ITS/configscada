import { menu_data_multi, toolbar_data_menu } from "../data/productData.js";
import { alarms } from "../widgets/alarms.js";
import { synoptic } from "../widgets/synoptic.js";
import { plans } from "../widgets/plans.js";
import { metro } from "../widgets/metro.js";
import { global } from "../widgets/global.js";
import { users } from "../widgets/users.js";
import { roles } from "../widgets/roles.js";
import { alarmconfigs } from "../widgets/alarmconfigs.js";
import { equipments } from "../widgets/equipments.js";

let grafana = {view:"iframe", src:  webix.storage.session.get("url.grafana") };
let jasper = {view:"iframe", src:  webix.storage.session.get("url.jasper")};


toolbar_data_menu.elements.find(x => x.icon === "mdi mdi-menu").click=function(){$$("$sidebar1").toggle();};
toolbar_data_menu.elements.find(x => x.icon === "webix_icon wxi-pencil").click=function(){changeStyle();};

var isLogin = webix.storage.session.get("loginSession");
if (!isLogin) {
    window.location = "./login.html";
}

webix.ready(function() {
    webix.ui({
    	id:"mainBody",
        rows: [
            toolbar_data_menu,
            {
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
            }
        ]
    });
    webix.extend($$("mainBody"), webix.ProgressBar);
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

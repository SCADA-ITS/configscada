import { menu_data_multi, toolbar_data_menu } from "../data/dataProject.js";
import {synopticData} from "../data/synoptics.js";

//import { map } from "../widgets/map.js";
import { equipments } from "../widgets/equipments.js";
import { alarms } from "../widgets/alarms.js";
import { plans } from "../widgets/plans.js";
import { users } from "../widgets/users.js";
import { roles } from "../widgets/roles.js";
import { alarmconfigs } from "../widgets/alarmconfigs.js";
import Synoptic from "../classes/Synoptic.js";
import AlarmNotification from "../classes/AlarmNotification.js";
import Alarms from "../classes/Alarms.js";

import { planschedules } from "../widgets/planschedule.js";
import {usersmanager} from "../widgets/usersmanager.js";
import { cctv } from "../widgets/cctv.js";
import * as revenga from "../lib/revenga.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";

import { mapsData } from "../data/mapsData.js";
import Map from "../classes/Map.js";

let grafana = {view:"iframe", src:  webix.storage.session.get("url.grafana") };
let jasper = {view:"iframe", src:  webix.storage.session.get("url.jasper")};

toolbar_data_menu.elements.find(x => x.icon === "mdi mdi-menu").click=function(){$$("$sidebar1").toggle();};
toolbar_data_menu.elements.find(x => x.icon === "webix_icon wxi-pencil").click=function(){changeStyle();};
toolbar_data_menu.elements.find(x => x.icon === "mdi mdi-bell").click=function(){$$("$sidebar1").select("alarms");};

var isLogin = webix.storage.session.get("loginSession");
if (!isLogin) {
    window.location = "./login.html";
}

var selectView = "";

let viewCells = [
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
    /*
    {
        id: "map",
        rows: [map]
    },
    */
    {
        id: "plans",
        rows: [plans]                            
    },
    {
        id: "planschedule",
        rows: [planschedules]                      
    },
    {
        id: "grafana",
        rows: [grafana]
    },
    {
        id: "jasper",
        rows: [jasper]
    },
    {
        id: "usersmanager",
        rows: [usersmanager]
    },
    {
        id: "cctv",
        rows: [cctv]
    }
    ];
    synopticData.forEach((item, index) => {
        let syn = new Synoptic(item);
        viewCells.push({
            id: "syn"+index,
            rows: [{
                view: "template",
                id: item.id,
                url: function() {
                  syn.init();
                }
              }]
        });
    });

    mapsData.forEach((item, index) => {
        let map = new Map(item, item.parentId);
        viewCells.push({
            id: "map" + index,
            rows: [{
                view: "template",
                id: item.id,
                url: function() {
                    map.init();
                }
            }]
        });
    });

let main_body = {
    cols: [{
            view: "sidebar",
            data: menu_data_multi,
            multiview: true,
            on: {
                onAfterSelect: function(id) {
                    $$("multiview").setValue(id);

                    if(selectView === "alarms" && id !=="alarms"){
                        //Como el anterior era alarms lo destruimos.
                        let table = new Alarms(null, null);
                        table.disconnect();
                    }else{
                        selectView = id;
                    }
                }
            },
            ready: function() {
                let firstItem = this.getFirstId();
                this.select("syn1");
            }
        },
        {
            id: "multiview",
            cells: viewCells
        }
    ]
};
let alarm_notification = new AlarmNotification();
webix.ready(function() {
    webix.ui({
    	id:"mainBody",
        rows: [
            toolbar_data_menu,
            main_body,
            alarm_notification.getComponent()
        ]
    });
    webix.extend($$("mainBody"), webix.ProgressBar);
    alarm_notification.loadData();

    $$("nameUser").setValue(userName);
    
    if (userPermissions !== "") {
        if (checkUserPermission(RULE.EDIT_USER)) {//Edit User
            console.log();
        } else {
            $$("b_new_user").disable();
            $$("b_edit_user").disable();
            $$("b_delete_user").disable();	
        }
        if (checkUserPermission(RULE.EDIT_ROLE)) {//Edit Role
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
    changeConnectedIcon();
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

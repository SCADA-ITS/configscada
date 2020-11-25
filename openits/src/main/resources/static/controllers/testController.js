// import { alarms } from "../widgets/alarms.js";
// import { synoptic } from "../widgets/synoptic.js";
import { plans } from "../widgets/plans.js";
import { users } from "../widgets/users.js";
import { roles } from "../widgets/roles.js";

import {columnsElementFormList} from "../data/elementForm.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";

// import { metro } from "../widgets/metro.js";
// import { global } from "../widgets/global.js";
// import { users } from "../widgets/users.js";
// import { roles } from "../widgets/roles.js";
// import { alarmconfigs } from "../widgets/alarmconfigs.js";
// import { equipments } from "../widgets/equipments.js";
import ElementForm from "../classes/ElementForm.js";
import ElementWindow from "../classes/ElementWindow.js"
import UserManagerInt from "../classes/UserManagerInterface.js";
import ElementChildsList from "../classes/ElementChildsList.js";
let elementForm = new ElementWindow("Element:2:1","ElementType:2");
//let elementForm2 = new ElementForm("Element:9:2");
//let elementForm3 = new ElementForm("Element:10:1");
//let elementForm4 = new ElementForm("Element:2:1");
let elementForm5 = new ElementForm("Element:51:1",columnsElementFormList);



var grid = {
    view:"dashboard",
    id:"grid",
    gridColumns:2,
    gridRows:4,
    cellHeight:200,
    factory:function(obj){
      obj.view = "panel";
      obj.resize = true;
      obj.body = $$("list").getItem(obj.name).body;
      return obj;
    },
    on:{
      onChange:function(){
        var state = this.serialize();
        /*alert(JSON.stringify(state))*/
        webix.storage.local.put("grid-dashboard-state", state);
      }
    }
  };

  var widgets = {
    rows:[
      {
        type:"header", 
        template:"Widgets, drag from here",
      },
      {
        view:"list",
        id:"list",
        width:200,
        scroll:"auto",
        drag:true,
        template:"#value# - (#dx#x#dy#)",
        data:[
         { id:"elementForm", value:"elementForm", dx:1, dy:1, body: elementForm.getComponent()}
          ,
           //{ id:"elementForm3", value:"elementForm3", dx:1, dy:1, body: elementForm3.getComponent()}
           //,
           { id:"plans", value:"plans", dx:1, dy:1, body: plans }
          ,
          //{ id:"elementForm4", value:"elementForm4", dx:1, dy:1, body: elementForm4.getComponent() }
          //,
          { id:"users", value:"users", dx:1, dy:1, body: users }
           ,
          { id:"roles", value:"roles", dx:1, dy:1, body: roles },
          { id:"aaa", value:"asas", dx:1, dy:1, body: { 
            view: "form",     
            id:"log_form",
            width:300,
            elements:[
                { view:"text", label:"Email", name:"email"},
                { view:"text", type:"password", label:"Password", name:"password"},
                { margin:5, cols:[
                    { view:"button", value:"Login" , 
                    click:function(id,event){
                        webix
                        .ui({
                          id: "win" + elementForm5.id,
                          view: "window",
                          move: true,
                          close: true,
                          position: "right",
                          body: {id: "cuerpo"+elementForm5.id,
                          cols:[
                            elementForm5.getComponent()
                          ]},
                          on: {
                            onDestruct: function(obj){
                              //elementForm.disconnect();
                            },
                            onHide: function(obj){
                              elementForm5.disconnect();
                              $$("win" + elementForm5.id).destructor();
                            },
                            onShow: function(obj){
                              let hijos = new ElementChildsList(elementForm5.id);
                              hijos.getComponent();
                            }
                          }                            
                        })
                        .show();
                      
                    },
                    css:"webix_primary"},
                    { view:"button", value:"Cancel"}
                ]}
            ] 
          }//elementForm5.getComponent(),

          },
          // ,
          // { id:"alarmconfigs", value:"alarmconfigs", dx:1, dy:1, body: alarmconfigs }
          // ,
          // { id:"equipments", value:"equipments", dx:1, dy:1, body: equipments }
        ]
      },
      {
        view:"button", value:"Reset", click:function(){
          $$("grid").clearAll();
        }
      }
    ]
  };

    webix.ready(function(){
  

  webix.ui({
    type:"space", cols:[
      { view:"scrollview", scroll:"auto", body:grid },
      widgets
    ]
  });

  var state = webix.storage.local.get("grid-dashboard-state");
  if (state)
    $$("grid").restore(state);
});

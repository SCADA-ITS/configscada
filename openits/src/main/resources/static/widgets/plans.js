import PlanTree from "../classes/PlansTree.js";
import Plan from "../classes/Plan.js";
import Order from "../classes/Order.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";
// GraphQL
//initialize a proxy
var graphPlans = webix.proxy(
  "GraphQL",
  `
  query {
    getPlans {
        id
        alias
        description
        planStateType{
          id
          alias
          description
          labelAlias
          labelDescription
        }
        planSubtype{
          id
          alias
          planType{
            id
            alias
          }
        }
        planModeType
        planOrders{
          id
          alias
          description
          planOrderValues{
            id
            value
            planOrderTypeParam{
              id
              alias              
            }
          }
          planOrderType{
            id
            alias
          }
          comandElement
          {
            id
            element{id alias}
            commandElementType{id alias}
            commandElementValues{
              id
              value
              commandElementTypeParam{id alias }
            }
          }
        }
    }
  }
  `
);



export function plans(){
	let widgetId=webix.uid();
	let barTreeId= "bar_tree"+widgetId;
	let btnAddOrderId= "btn_add_order"+widgetId;
  let btnEditId= "btn_edit"+widgetId;
  let btnSaveId= "btn_save"+widgetId;
  let btnDeleteId= "btn_delete"+widgetId;
  let gridId= "grid"+widgetId;
  let barId= "bar"+widgetId;
  let btnPlayId= "btn_play"+widgetId;
  let btnStopId= "btn_stop"+widgetId;
  let btnPauseId= "btn_pause"+widgetId;
  
  var tree1 = {
    view:"tree",
    id:widgetId,
    select:true,
    template:"{common.icon()} {common.folder()} <span>#alias#</span>",
    url: function() {
      syn.init();
    }
    ,
    on:{
        onItemClick: open_new_tab
    }
  };
  
  var plangrid1 = {    
    scroll:"y",
    rows:[{   
      view:"scrollview", 
      body:{
        id: gridId, 
        rows:[]
      }
    }]
  };
  
  function removeGrid(view){
    const hijos = $$(view).getChildViews();
    if(hijos[0] !== undefined){
      console.log(hijos[0].config.id);
      if (hijos[0].config.id.toString().split("_")[0] === "planHeader") {
        console.log("SOMOS UN PLAN");
        let plan = new Plan(hijos[0].config.id.toString().split("_")[1],null, widgetId);
        plan.disconnect();
      }
      for(let i = hijos.length - 1; i>=0; i--){ 
        $$(view).removeView(hijos[i].config.id.toString());    
      }
    }
  }
  
  function form_types(view,obj){
    $$(view).addView({ view:"template",type:"header", autoheight:true, heigth: 150, select:true, id:obj.id.toString() ,template: "<div>" + obj.alias + "</div>" },3);
    $$(view)
    .addView({
      view:"form", 
      id:obj.qid.toString() + "_form", 
      scroll:false, 
      elements:[{
        rows: [
          {
          cols:[
            { view:"text", id: obj.qid.toString() + "_alias", name: "alias", label:$.i18n("plan.form.alias"), value: obj.alias, labelAlign:"left", labelWidth: 125, inputWidth:300 },
            { view:"text", id: obj.qid.toString() + "_description", name: "description", label:$.i18n("plan.form.description"), value: obj.description.toString(), labelAlign:"left", labelWidth: 125, inputWidth:400  }
          ]},{
          cols:[
            { view:"text", id: obj.qid.toString() + "_qid", disabled: true, name: "qid", label:$.i18n("plan.form.id"), value: obj.qid.toString(), labelAlign:"left", labelWidth: 125, inputWidth:300  }
          ]}
        ]
      }]
    });
  }
  
  
  function open_new_tab(id){
    let item = $$(widgetId).getItem(id);
  
    removeGrid(gridId);
    $$(barTreeId).show();
    $$(btnDeleteId).hide();
    $$(widgetId).data.each(function(obj){  
      
      if ( obj.id.toString() === item.id.toString() ) {
       
        switch (obj.$level) {
          case 4:  //PLAN
            $$(barId).show();
            $$(btnEditId).hide();
            $$(btnSaveId).hide();
            $$(btnPlayId).hide();
            $$(btnStopId).hide();
            $$(btnPauseId).hide();
            $$(btnDeleteId).hide();
            $$(btnAddOrderId).hide();
  
            if (obj.qid==="new") {
              let newplan = new Plan(null,null, widgetId);
              $$(btnSaveId).show();
              newplan.newForm(gridId,obj.id.toString());
            } else {
              let plan = new Plan(null,obj, widgetId);
              if (checkUserPermission(RULE.PLAN_ADMIN)){
                $$(btnEditId).show();
                $$(btnEditId).enable();
              }else{
                $$(btnEditId).show();
                $$(btnEditId).disable();
              }
  
              $$(btnSaveId).hide();
              if (checkUserPermission(RULE.PLAN_ACTION)){
                $$(btnPlayId).show();
                $$(btnStopId).show();
                $$(btnPauseId).show();
  
                $$(btnPlayId).enable();
                $$(btnStopId).enable();
                $$(btnPauseId).enable();
              }else{
                $$(btnPlayId).show();
                $$(btnStopId).show();
                $$(btnPauseId).show();
  
                $$(btnPlayId).disable();
                $$(btnStopId).disable();
                $$(btnPauseId).disable();
  
              }
  
              
              if (checkUserPermission(RULE.PLAN_ADMIN)){
                $$(btnAddOrderId).show();
                $$(btnDeleteId).show();
  
                $$(btnAddOrderId).enable();
                $$(btnDeleteId).enable();
              }else{
                $$(btnAddOrderId).show();
                $$(btnDeleteId).show();
  
                $$(btnAddOrderId).disable();
                $$(btnDeleteId).disable();
  
              }
  
              plan.editForm(gridId,plan.data);
              plan.activate();
            }
            break;          
          case 3:  //planSubtype
            form_types(gridId,obj);
            $$(barId).show();  
            $$(btnPlayId).hide();
            $$(btnEditId).hide();
            $$(btnStopId).hide();
            $$(btnPauseId).hide();
            $$(btnSaveId).show(); 
            if (item.$count === 0)
              $$(btnDeleteId).show();
            $$(btnAddOrderId).hide();
            break;
          case 2: //planType
            form_types(gridId,obj);
            $$(barId).show();  
            $$(btnPlayId).hide();
            $$(btnEditId).hide();
            $$(btnStopId).hide();
            $$(btnPauseId).hide();
            $$(btnSaveId).show();
            if (item.$count === 0)
              $$(btnDeleteId).show();
            $$(btnAddOrderId).hide();
            break;
          case 1:
            $$(barId).hide(); 
            $$(btnEditId).hide();
            if (item.$count === 0)
              $$(btnDeleteId).show();
            $$(btnAddOrderId).hide();
            break;
          default:
            //console.log("");
            break;
        } 
      }   
           
    });
  };
  
  function sendPlan(id){
    syn.sendPlan();
  }
  
  function saveItems(id){
    syn.saveItems();
  }
  
  function addToTree(id){
    syn.addToTree();
  }
  
  function editPlan(id){
    syn.editPlan();
  }
  
  function removeToTree(id){
    let re = syn.removeToTree();
    re ? null : removeGrid(gridId);
  }
  //NUEVA ORDEN 
  function new_order(id){
    let selectPlan = $$(widgetId).getSelectedId();
    let item = $$(widgetId).getItem(selectPlan);
  
    if( $$("new_order_window_" + item.qid)){
      $$("new_order_window_" + item.qid).show();
    }else{
      let newOrder = new Order(null,"new",null,null,item.qid,gridId, this.parentId);
      webix.ui({
        id:"new_order_window_" + item.qid,
        view:"window",	
        move:false,
        position:"center",
        modal:true,
        close: true,
        body: newOrder.getInitComponent(),
        on: {
          onShow: function(obj){
            newOrder.loadComponent();
          },
          onHide: function(obj){
            $$("new_order_window_" + item.qid).destructor();
          }
          }
        }).show();
    }
  }
  
  let syn = new PlanTree(graphPlans, null, widgetId);

  let result ={
    rows:[
      {
        cols:[
          { 
            rows:[
              { view:"template", type:"header", template: "<div>" + $.i18n("plan.tree.title") + "</div>" ,  align:"left" },
              { body:tree1, width: 250 },
              {
                view: "toolbar",
                id: barTreeId,
                heigth: 50,
                hidden:true,
                rows: [{
                  cols:[
                    { gravity: 4},
                    { view:"button", autowidth:true, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN) ,tooltip:$.i18n("plan.button.tooltip.add"), icon:"mdi mdi-plus",
                      click: addToTree                    
                    },
                    { view:"button", id: btnDeleteId, autowidth:true, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN) ,tooltip:$.i18n("plan.button.tooltip.remove"), icon:"mdi mdi-minus",
                      click: removeToTree
                    }
                  ]
                }]
              }                        
            ]
          },
          { 
            rows:[ 
              { body:plangrid1 },
              {
                view: "toolbar",
                id: barId,
                heigth: 50,
                hidden:true,
                align:"right",
                rows: [{
                  cols:[
                    { gravity: 8}, 
                    { view:"button", id: btnAddOrderId, align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.addOrder"), icon:"mdi mdi-plus", 
                      click:new_order
                    },
                    { 
                      view:"button", id: btnPlayId, align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.play"), icon:"mdi mdi-play", 
                      click:sendPlan
                    },
                    { view:"button", id: btnStopId, align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.stop"), icon:"mdi mdi-stop" },
                    { view:"button", id: btnPauseId, align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.pause"), icon:"mdi mdi-pause" }, 
                    { view:"button", id: btnEditId , type:"icon", icon:"mdi mdi-grease-pencil", width:50, tooltip:$.i18n("plan.button.tooltip.edit") ,
                      click: editPlan              
                    },
                    { view:"button", id: btnSaveId, align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.save"), icon:"mdi mdi-content-save",
                      click:saveItems
                    },
                  ]
                }]
              }
            ]
          }
        ]
      },
    ]
  };
  return result;
}

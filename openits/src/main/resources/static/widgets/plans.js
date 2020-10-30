import PlanTree from "../classes/PlansTree.js";
import Plan from "../classes/Plan.js";
import Order from "../classes/Order.js";

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
        state
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
          state
          planOrderValues{
            id
            value
            planOrderTypeParam{
              id
              alias
              dataType
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
              commandElementTypeParam{id alias dataType}
            }
          }
        }
    }
  }
  `
);

var tree1 = {
  view:"tree",
  id:"treePlan",
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
  id: "accordion", 
  scroll:"y",
  rows:[{   
    view:"scrollview", 
    body:{
      id: "planParent", 
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
      let plan = new Plan(hijos[0].config.id.toString().split("_")[1],null);
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
  let item = $$('treePlan').getItem(id);

  removeGrid("planParent");
  $$("toolbar_tree").show();
  $$("b_remove").hide();
  $$("treePlan").data.each(function(obj){  
    
    if ( obj.id.toString() === item.id.toString() ) {
     
      switch (obj.$level) {
        case 4:  //PLAN
          $$("toolbar_plan").show();
          $$("b_edit").hide();
          $$("b_save").hide();
          $$("b_play").hide();
          $$("b_stop").hide();
          $$("b_pause").hide();
          $$("b_remove").hide();
          $$("b_add_order").hide();

          if (obj.qid==="new") {
            let newplan = new Plan(null,null);
            $$("b_save").show();
            newplan.newForm("planParent",obj.id.toString());
          } else {
            let plan = new Plan(null,obj);
            if (checkUserPermission('plan_admin')){
              $$("b_edit").show();
            }
            $$("b_save").hide();
            if (checkUserPermission('plan_action')){
              $$("b_play").show();
              $$("b_stop").show();
            }
            $$("b_pause").show();
              $$("b_remove").show();
            
            if (checkUserPermission('plan_admin')){
                $$("b_add_order").show();
            }
            plan.editForm("planParent",plan.data);
            plan.activate();
          }
          break;          
        case 3:  //planSubtype
          form_types("planParent",obj);
          $$("toolbar_plan").show();  
          $$("b_play").hide();
          $$("b_edit").hide();
          $$("b_stop").hide();
          $$("b_pause").hide();
          $$("b_save").show(); 
          if (item.$count === 0)
            $$("b_remove").show();
          $$("b_add_order").hide();
          break;
        case 2: //planType
          form_types("planParent",obj);
          $$("toolbar_plan").show();  
          $$("b_play").hide();
          $$("b_edit").hide();
          $$("b_stop").hide();
          $$("b_pause").hide();
          $$("b_save").show();
          if (item.$count === 0)
            $$("b_remove").show();
          $$("b_add_order").hide();
          break;
        case 1:
          $$("toolbar_plan").hide(); 
          $$("b_edit").hide();
          if (item.$count === 0)
            $$("b_remove").show();
          $$("b_add_order").hide();
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
  //no es propio del treePlan
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
  re ? null : removeGrid("planParent");
}
//NUEVA ORDEN 
function new_order(id){
  let selectPlan = $$("treePlan").getSelectedId();
  let item = $$('treePlan').getItem(selectPlan);

  if( $$("new_order_window_" + item.qid)){
    $$("new_order_window_" + item.qid).show();
  }else{
    let newOrder = new Order(null,"new",null,null,item.qid,"planParent");
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

let syn = new PlanTree(graphPlans, null);

export let plans = {
  rows:[
    {
      cols:[
        { 
          rows:[
            { view:"template", type:"header", template: "<div>" + $.i18n("plan.tree.title") + "</div>" ,  align:"left" },
            { body:tree1, width: 250 },
            {
              view: "toolbar",
              id: "toolbar_tree",
              heigth: 50,
              hidden:true,
              rows: [{
                cols:[
                  { gravity: 4},
                  { view:"button", id: "b_add", autowidth:true, type:"icon", hidden: !checkUserPermission('plan_admin') ,tooltip:$.i18n("plan.button.tooltip.add"), icon:"mdi mdi-plus",
                    click: addToTree                    
                  },
                  { view:"button", id: "b_remove", autowidth:true, type:"icon", hidden: !checkUserPermission('plan_admin') ,tooltip:$.i18n("plan.button.tooltip.remove"), icon:"mdi mdi-minus",
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
              id: "toolbar_plan",
              heigth: 50,
              hidden:true,
              align:"right",
              rows: [{
                cols:[
                  { gravity: 8}, 
                  { view:"button", id: "b_add_order", align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.addOrder"), icon:"mdi mdi-plus", 
                    click:new_order
                  },
                  { 
                    view:"button", id: "b_play", align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.play"), icon:"mdi mdi-play", 
                    click:sendPlan
                  },
                  { view:"button", id: "b_stop", align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.stop"), icon:"mdi mdi-stop" },
                  { view:"button", id: "b_pause", align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.pause"), icon:"mdi mdi-pause" }, 
                  { view:"button", id: "b_edit" , type:"icon", icon:"mdi mdi-grease-pencil", width:50, tooltip:$.i18n("plan.button.tooltip.edit") ,
                    click: editPlan              
                  },
                  { view:"button", id: "b_save", align:"right", autowidth:true, type:"icon", tooltip:$.i18n("plan.button.tooltip.save"), icon:"mdi mdi-content-save",
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

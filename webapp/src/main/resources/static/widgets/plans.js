import PlanTree from "../classes/PlansTree.js";
import Plan from "../classes/Plan.js";

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
  //filterMode:{ showSubItems:true },
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
  for(let i = hijos.length - 1; i>=0; i--){ 
    $$(view).removeView(hijos[i].config.id.toString());    
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
          { view:"text", id: obj.qid.toString() + "_alias", name: "alias", label:"Alias", value: obj.alias, labelAlign:"left", labelWidth: 125, inputWidth:300 },
          { view:"text", id: obj.qid.toString() + "_description", name: "description", label:"Description", value: obj.description.toString(), labelAlign:"left", labelWidth: 125, inputWidth:400  }
        ]},{
        cols:[
          { view:"text", id: obj.qid.toString() + "_qid", disabled: true, name: "qid", label:"Id", value: obj.qid.toString(), labelAlign:"left", labelWidth: 125, inputWidth:300  }
        ]}
      ]
    }]
  });
}


function open_new_tab(id){
  let item = $$('treePlan').getItem(id);

  removeGrid("planParent");
  $$("toolbar_tree").show();
  $$("treePlan").data.each(function(obj){  
    if ( obj.id.toString() === item.id.toString() ) {
      switch (obj.$level) {
        case 4:  //PLAN
          let plan = new Plan(null,obj);
          plan.dinamicFormViewWithOrders("planParent");
          $$("toolbar_plan").show();
          $$("b_save").hide();
          $$("b_play").show();
          $$("b_stop").show();
          $$("b_pause").show();
          $$("b_remove").show();
          break;          
        case 3:  //planSubtype
          form_types("planParent",obj);
          //obj.qid === "new" ? form_types("planParent",obj,true) : form_types("planParent",obj,false);
          $$("toolbar_plan").show();  
          $$("b_play").hide();
          $$("b_stop").hide();
          $$("b_pause").hide();
          $$("b_save").show(); 
          $$("b_remove").show();
          break;
        case 2: //planType
          //obj.qid === "new" ? form_types("planParent",obj,true) : form_types("planParent",obj,false);
          form_types("planParent",obj);
          $$("toolbar_plan").show();  
          $$("b_play").hide();
          $$("b_stop").hide();
          $$("b_pause").hide();
          $$("b_save").show();
          $$("b_remove").show();
          break;
        case 1:
          $$("toolbar_plan").hide(); 
          $$("b_remove").hide();
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

function removeToTree(id){
  let re = syn.removeToTree();
  re ? null : removeGrid("planParent");
}

let syn = new PlanTree(graphPlans, null);
let planprueba = new Plan("Plan:2",null);
//console.log(planprueba.id);
//console.log(planprueba);
export let plans = {
  rows:[
    {
      cols:[
        { 
          rows:[
            { view:"template", type:"header", template: "<div>Plan Tree</div>" ,  align:"left" },
            { body:tree1, width: 250 },
            {
              view: "toolbar",
              id: "toolbar_tree",
              heigth: 50,
              hidden:true,
              rows: [{
                cols:[
                  { gravity: 4},
                  { view:"button", id: "b_add", autowidth:true, type:"icon", icon:"mdi mdi-plus",
                    click: addToTree                    
                  },
                  { view:"button", id: "b_remove", autowidth:true, type:"icon", icon:"mdi mdi-minus",
                    click: removeToTree
                  }
                ]
              }]
            }                        
          ]
        },
        { 
          rows:[ //{body: planprueba.getComponentWithOrders()},
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
                  { 
                    view:"button", id: "b_play", align:"right", autowidth:true, type:"icon", icon:"mdi mdi-play", 
                    click:sendPlan
                  },
                  { view:"button", id: "b_stop", align:"right", autowidth:true, type:"icon", icon:"mdi mdi-stop" },
                  { view:"button", id: "b_pause", align:"right", autowidth:true, type:"icon", icon:"mdi mdi-pause" },
                  { view:"button", id: "b_save", align:"right", autowidth:true, type:"icon", icon:"mdi mdi-content-save",
                    click:saveItems
                  },
                  //{ gravity: 4},
                ]
              }]
            }
          ]
        }
          //{ header:"Linked Grid", body:grid1 },
          //{ header:"Linked Tree", body:tree2 }
      ]
    },
  ]
};

import Order from "./Order.js";

let optionPlanModeType = [
	{id:1, value:"AUTO"},
	{id:2, value:"SEMI"},
	{id:3, value:"MANUAL"}
];

var planStateType = {
	 0: "UNKNOWN",
	 1: "STOPPED",
	 2: "RUNNING",
	 3: "SUCCESS",
   4: "FAILED"
};
 
var planOrderStateType = {
  0: "UNKNOWN",
  1: "RUNNING",
  2: "SUCCESS",
  3: "FAILED"
};
 
export default class Plan {
  constructor(id, data) {
    this.data = data;
    if (id === null)
    {
      if ( data !== null ) {
        this.id = data.qid;
        this.alias = data.alias;
        this.description = data.description;
        this.state = data.state;
        this.planSubtype = data.planSubtype;
        this.planModeType = data.planModeType;
        this.planOrders = data.planOrders;
      }
    } else {
      this.id = id;
      this.alias = null;
      this.description = null;
      this.state = null;
      this.planSubtype = null;
      this.planModeType = null;
      this.planOrders = null;
    }

		var graphElem = `query {
      getPlan(planId: "` + this.id + `") {
        id
        alias
        description
        state
        planSubtype {
          id
          alias
          planType {
            id
            alias
          }
        }
        planModeType
        planOrders {
          id
          alias
          description
          state
          planOrderValues {
            id
            value
            planOrderTypeParam {
              id
              alias
              description
              dataType
            }
          }
          planOrderType {
            id
            alias
            description
            planOrderTypeParams {
              id
              alias
              description
              dataType
            }
          }
          comandElement {
            id
            element {
              id
              alias
              elementType {
                id
                alias
              }
            }
            commandElementType {
              id
              alias
              description
              commandElementTypeParams {
                id
                alias
                description
                dataType
              }
            }
            commandElementValues {
              id
              value
              commandElementTypeParam {
                id
                alias
                description
                dataType
              }
            }
          }
        }
      }
    }
    `;
		
  this.graphql = webix.proxy("GraphQL", graphElem);
  this.header = {
    view: "template", //optional
    type: "header",
    id: "template2"+this.id,
    data: {
      value: ""
    },
    template: function (obj) {
      return obj.value;
    },
    css:{"text-align":"center !important"}
  };
  
  this.form = {
    view: "form",
    id: "planformresult_"+this.id,
    margin: 0,
    elements: [
      { view: "text", name: "id", label: $.i18n("plan.form.id"), disabled: true },
      { view: "text", name: "alias", label: $.i18n("plan.form.alias") },
      { view: "text", name: "description", label: $.i18n("plan.form.description") },
      { view: "text", name: "state", label: $.i18n("plan.form.state") },
      { view: "text", name: "planSubtype", label: $.i18n("plan.form.planSubtype") },
      { view: "combo", name: "planModeType", label: $.i18n("plan.form.planModeType"), options:optionPlanModeType},
    ]
  };
}

  newForm(container,idForm){
    $$(container).addView({ view:"template",type:"header", autoheight:true, heigth: 150, select:true, id:idForm.toString() ,template: "<div>" + $.i18n("plan.form.new.title") + "</div>" },3);
    $$(container)
      .addView({
        view:"form", 
        id:idForm + "_form", 
        scroll:false, 
        elements:[{
          rows: [{
            cols:[
              { view:"text", id:idForm + "_alias",  name: "alias",  label: $.i18n("plan.form.alias"), labelAlign:"left", labelWidth: 125, inputWidth:0 },                        
            ]
          },{
            cols:[
              { view:"text", id:idForm + "_description",  name: "description",  label:$.i18n("plan.form.description"),  labelAlign:"left", labelWidth: 125, inputWidth:0 },
              { view:"combo",id:idForm + "_planModeType", name: "planModeType", label:$.i18n("plan.form.planModeType"), labelAlign:"left", options: optionPlanModeType, labelWidth: 200, inputWidth:0 }
            ]
          }]
        }]
    });
  }

  editForm(container,data1){
    $$(container).addView({ view:"template",type:"header", autoheight:true, heigth: 150, select:true, id:this.id.toString() ,template: "<div>" + this.alias + "</div>" },3);
    $$(container)
      .addView({
        view:"form", 
        id:this.id.toString() + "_form", 
        scroll:false, 
        elements:[{ responsive:this.id.toString() + "_form", 
          rows: [{ responsive:this.id.toString() + "_form", 
            cols:[
              { view:"text", id: this.id.toString() + "_alias",  disabled: true, name: "alias",  label:$.i18n("plan.form.alias"), value: this.alias,         labelAlign:"left", labelWidth: 125, inputWidth:0 },                        
              { view:"text", id: this.id.toString() + "_planId", disabled: true, name: "planId", label:$.i18n("plan.form.id"),    value: this.id.toString(), labelAlign:"left", labelWidth: 200, inputWidth:0 },
              { view:"text", id: this.id.toString() + "_state",  disabled: true, name: "state",  label:$.i18n("plan.form.state"), value: planStateType[this.state],         labelAlign:"left", labelWidth: 200, inputWidth:0 }
            ]
          },{ responsive:this.id.toString() + "_form", 
            cols:[
              { view:"text", id: this.id.toString() + "_description",  disabled: true, name: "description",  label:$.i18n("plan.form.description"),  value: this.description,       labelAlign:"left", labelWidth: 125, inputWidth:0 },
              { view:"text", id: this.id.toString() + "_planSubtype",  disabled: true, name: "planSubtype",  label:$.i18n("plan.form.planSubtype"),  value: this.planSubtype.alias, labelAlign:"left", labelWidth: 200, inputWidth:0 },
              { view:"combo", id: this.id.toString() + "_planModeType", disabled: true, name: "planModeType", label:$.i18n("plan.form.planModeType"), options: optionPlanModeType, value: this.planModeType, labelAlign:"left", labelWidth: 200, inputWidth:0 }
            ]
          }]
        }]
    });
    let orderelements = [];
    let orderplanOrderType = [];
    let neworder = new Order(null,"new",orderelements,orderplanOrderType,container);
    neworder.new_dinamicFormOrder(container,this.id.toString());
    try {
      this.planOrders.forEach(e => {
        let order = new Order(null,e,orderelements,orderplanOrderType,container);
        order.dinamicFormOrder(container);
      });
    } catch {}
  };

  sendExecutionPlan(){
    let planMutation = webix.proxy(
      "GraphQL",
      `
      mutation  sendPlayPlan($planIds: [String!]!){
        sendPlayPlan(planIds: $planIds)  
      }
      `
    );
    // loads the data
    planMutation
      .load({
        planIds: [this.id]
      })
      .then(data => {
        // process the received data
        if (data === true) {
          webix.message(this.id + " " + $.i18n("plan.execution.correct"));
        } else {
          webix.message(this.id + " " + $.i18n("plan.execution.error"));
        }
      });
  }

  loadData() {
    this.graphql.load().then(d => {
      let datos = d;
      this.data = d;
      this.alias = datos.alias;
      this.description = datos.description;
      this.state = planStateType[datos.state];
      this.planSubtype = datos.planSubtype;
      this.planModeType = datos.planModeType;
      this.planOrders = datos.planOrders;
      const prueba = new Promise(function(resolve, reject){
        resolve();
      });
      webix.message(this.id + " Ejecutado Correctamente");
      webix.message(this.alias + " Ejecutado Correctamente");
      webix.message(this.description + " Ejecutado Correctamente");
      webix.message(this.planSubtype + " Ejecutado Correctamente");
      webix.message(this.planModeType + " Ejecutado Correctamente");
      webix.message(this.planOrders + " Ejecutado Correctamente");
    });
  }

  init() {
    this.loadData();
  }
}
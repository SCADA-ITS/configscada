import { plan } from "../data/plan.js";
import Order from "./Order.js";

export default class Plan {
  constructor(id,data) {
    this.data = data;
    if (id === null)
    {
      this.id = data.qid;
      this.alias = data.alias;
      this.description = data.description;
      this.state = data.state;
      this.planSubtype = data.planSubtype;
      this.planModeType = data.planModeType;
      this.planOrders = data.planOrders;
    } else {
      this.id = id;
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
			//view: "template", type: "header", id: "header", template: "wrg"
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
      { view: "text", name: "id", label: "id", disabled: true },
      { view: "text", name: "alias", label: "alias" },
      { view: "text", name: "description", label: "description" },
      //{ view: "text", name: "state", label: "state" },
      { view: "combo", name: "state", label: 'state', options:[{id: 0},{id: 1},{id: 2},{id: 3}]},
      { view: "text", name: "planSubtype", label: "planSubtype" },
      //{ view: "combo", id: "planformresult_"+this.id + "_planSubtype" ,name: "planSubtype", label: 'planSubtype', options:[]},
      //{ view: "text", name: "planModeType", label: "planModeType" },
      { view: "combo", name: "planModeType", label: 'planModeType', options:[{id: 0},{id: 1},{id: 2},{id: 3}]},
    ]
  };
}

getComponent() {
  if (this.id !== "new" ) 
  {
    let planSubtype = [];
    let getPlanSubtypes = webix.proxy(
      "GraphQL",
      `
      query {
      getPlanSubtypes{
        id
        alias
        description
        planType{
          id
          alias
          description
        }
      }}
      `
    );
    getPlanSubtypes.load()
    .then(data => {
      data.forEach(element => {
          this.planSubtype.push({ id: element.id, value: element.alias});
          //let ptype = this.planType.find( x => x.id === element.planType.id );
          //ptype ? null : this.planType.push(element.planType);
        });
      });

    this.graphql.load().then(data => {
      var datos = data
      let header = "";
      //icon = "img/" + staticsIcons[data.elementType.id].state[data.state].icon;
      header = data.alias;
      console.log(this.planSubtype);
      $$("planformresult_"+this.id).setValues({
        id: datos.id,
        alias: datos.alias,
        description: datos.description,
        state: datos.state,
        planSubtype: datos.planSubtype.alias,
        planModeType: datos.planModeType
      });
      //this.planSubtype.forEach(element => {
      //  $$("planformresult_"+this.id + "_planSubtype").add({ id: element.id, value: element.alias },0);
      //});
     // .getList().add({ $empty:true, value:"" },0);
     // $$("planformresult_"+this.id + "_planSubtype").parse("options", this.planSubtype);
      //$$("planformresult_"+this.id + "_planSubtype").refresh();


      //console.log(typeof header);
      $$("template2"+this.id).setValues({ value: header });
      //$$("header").setValue({template:data.elementType.alias});
    });
  }
  let genericForm = {
    rows: [
      this.header,
      this.form, 
      {}
    ]
  };
  return genericForm;
}

getComponentWithOrders() {
  let orderView = [];

  let orders = {   
    view:"scrollview", 
    body:{
      id: "planOrders", 
      rows:[]
    }
  };

  this.graphql.load().then(data => {
    var datos = data
    let header = "";
    header = data.alias;

    datos.planOrders.forEach(function(e){

      /*
      let orderheader = {            
        view: "template", //optional
        type: "header",
        id: "template2"+obj.id,
        data: {
          value: obj.alias
        },
        template: function (obj2) {
          return obj2.value;
        },
        css:{"text-align":"center !important"}
      };
      orderView.push(orderheader);
      let order = {
            view: "form",
            id: "planformresult_"+obj.id,
            margin: 0,
            elements: [
              { view: "text", name: "id", label: "id", value: obj.id },
              { view: "text", name: "alias", label: "alias", value: obj.id },
              { view: "text", name: "description", label: "description", value: obj.id },
              { view: "text", name: "state", label: "state", value: obj.id },
              { view: "text", name: "planOrderType", label: "planOrderType", value:obj.planOrderType.alias, },
              { view: "text", name: "commandElementType", label: "commandElementType", value:obj.comandElement.commandElementType.alias },
              { view: "text", name: "commandElementValues", label: "commandElementValues", value:obj.comandElement.commandElementValues },
              { view: "text", name: "element", label: "element", value:obj.comandElement.element.alias },
            ]
         // }
        //]
      };
      orderView.push(order);
     
*/



     $$("planOrders")
     .addView({
       view:"form", 
       id:e.id, 
       scroll:false, 
       elements:[{
         rows: [
           {view:"template", type:"header", template: e.id.toString(), height:50,  align:"left"},
           {
             cols:[
               { view:"text", id: e.id.toString() + "_alias",   disabled: true, name: "alias",   label:"Alias", value: e.alias,         labelAlign:"left", labelWidth: 125, inputWidth:300 },                        
               { view:"text", id: e.id.toString() + "_orderId", disabled: true, name: "orderId", label:"Id",    value: e.id.toString(), labelAlign:"left", labelWidth: 200, inputWidth:400 },
               { view:"text", id: e.id.toString() + "_state",   disabled: true, name: "state",   label:"State", value: e.state,         labelAlign:"left", labelWidth: 200, inputWidth:400 }
             ]
           },{
             cols:[
               { view:"text", id: e.id.toString() + "_planOrderType",        disabled: true, name: "planOrderType",        label:"planOrderType",        value:e.planOrderType.alias,                    labelAlign:"left", labelWidth: 125, inputWidth:300 },
               { view:"text", id: e.id.toString() + "_commandElementType",   disabled: true, name: "commandElementType",   label:"commandElementType",   value:e.comandElement.commandElementType.alias, labelAlign:"left", labelWidth: 200, inputWidth:400 },
               { view:"text", id: e.id.toString() + "_commandElementValues", disabled: true, name: "commandElementValues", label:"commandElementValues", value:e.comandElement.commandElementValues,     labelAlign:"left", labelWidth: 200, inputWidth:400 }
             ]
           },
           { view:"text", id: e.id.toString() + "_element", disabled: true, name: "element", label:"Element", value:e.comandElement.element.alias, labelAlign:"left", labelWidth: 125, inputWidth:300 }
         ]
       }]
     });



    });

    $$("planformresult_"+this.id).setValues({
      id: datos.id,
      alias: datos.alias,
      description: datos.description,
      state: datos.state,
      planSubtype: datos.planSubtype.alias,
      planModeType: datos.planModeType
    });

    //console.log(typeof header);
    $$("template2"+this.id).setValues({ value: header });
    //$$("header").setValue({template:data.elementType.alias});
  });
  //console.log(genericForm);
  console.log("OOOOOOOOOOO");
  let genericForm = {
    rows: [
      this.header,
      this.form,
      orders,
      {}
    ]
  };
  return genericForm;
}

  dinamicFormViewWithOrders(container){
    $$(container).addView({ view:"template",type:"header", autoheight:true, heigth: 150, select:true, id:this.id.toString() ,template: "<div>" + this.alias + "</div>" },3);
    $$(container)
      .addView({
        view:"form", 
        id:this.id.toString() + "_form", 
        scroll:false, 
        elements:[{
          rows: [{
            cols:[
              { view:"text", id: this.id.toString() + "_alias",  disabled: true, name: "alias",  label:"Alias", value: this.alias,         labelAlign:"left", labelWidth: 125, inputWidth:300 },                        
              { view:"text", id: this.id.toString() + "_planId", disabled: true, name: "planId", label:"Id",    value: this.id.toString(), labelAlign:"left", labelWidth: 200, inputWidth:400  },
              { view:"text", id: this.id.toString() + "_state",  disabled: true, name: "state",  label:"State", value: this.state,         labelAlign:"left", labelWidth: 200, inputWidth:400 }
            ]
          },{
            cols:[
              { view:"text", id: this.id.toString() + "_description",  disabled: true, name: "description",  label:"description",  value: this.description,       labelAlign:"left", labelWidth: 125, inputWidth:300 },
              { view:"text", id: this.id.toString() + "_planSubtype",  disabled: true, name: "planSubtype",  label:"planSubtype",  value: this.planSubtype.alias, labelAlign:"left", labelWidth: 200, inputWidth:400 },
              { view:"text", id: this.id.toString() + "_planModeType", disabled: true, name: "planModeType", label:"planModeType", value: this.planModeType,      labelAlign:"left", labelWidth: 200, inputWidth:400 }
            ]
          }]
        }]
    });

    let orderelements = [];
		let graphorderElems = webix.proxy("GraphQL",`
      query  {
        getElements{
          id
          alias
          description
          elementType
          {
            id
            alias
            description
            commandElementTypes{
              id
              alias
              description
              commandElementTypeParams{
                id
                alias
                description
                dataType
              }
            }
          }
          elementValues
          {
            id
            value
            elementTypeParam
            {
              id
              alias
              paramTypeLabel
              paramType
              dataTypeLabel
              dataType
            }
          }
        }
      }`);
      graphorderElems.load()
      .then(data => {
        data.forEach(element => {
            orderelements.push(element);
          });
        });
    let orderplanOrderType = [];
    let planOrderType = webix.proxy(
      "GraphQL",
      `query {
        getPlanOrderTypes{
          id
          alias
          description
          planOrderTypeParams{
            id
            alias
            description
            dataType
          }
        }
      }`
    );
    planOrderType.load()
    .then(data => {
      data.forEach(element => {
          orderplanOrderType.push(element); 
        });
      });
  
    
      //console.log("qwqwqwqwqwqwqwqwqwqw");
      //console.log(orderelements);
    this.planOrders.forEach(e => {
      let order = new Order(null,e,orderelements,orderplanOrderType);
      order.dinamicFormOrder(container);
      //order.init();
      /*
      console.log(order);
      console.log(order.planOrderType);
      console.log(order.elements);
      order.dinamicFormOrder(container);
      orderelements.forEach(el => {
        console.log("DENTRO");
        $$(order.id.toString() + "_element").getList().add({id:el.id, value:el.alias});
        //console.log("FUERA2");
        //this.elementsOption.push({id: element.id, value: element.alias});
      });
      orderplanOrderType.forEach(el => {
        console.log("DENTRO");
        $$(order.id.toString() + "_planOrderType").getList().add({id:el.id, value:el.alias});
        //console.log("FUERA2");
        //this.elementsOption.push({id: element.id, value: element.alias});
      });      
      */
    });
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
          webix.message(this.id + " Ejecutado Correctamente");
        } else {
          webix.message(this.id + " ERROR AL EJECUTAR");
        }
      });
  }

}
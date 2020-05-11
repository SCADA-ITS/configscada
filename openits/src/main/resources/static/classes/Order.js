
 var planOrderStateType = {
	 0: "UNKNOWN",
	 1: "RUNNING",
	 2: "SUCCESS",
	 3: "FAILED"};
 
var upOrderGrap = webix.proxy("GraphQL",`
  mutation updatePlanOrder($plan: PlanOrderVO!) {
    updatePlanOrder(planOrder: $plan) {
      id
      alias
      description
      state
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
`);

var createOrderGrap = webix.proxy("GraphQL",`
  mutation updatePlanOrder($plan: PlanOrderVO!) {
    createPlanOrder(planOrder: $plan) {
      id
      alias
      description
      state
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
`);

function orderEdit(id){

  //console.log("EDIT");
  //console.log(id.toString().split("_"));
  $$(id.toString().split("_")[1] + "_element").enable();
  $$(id.toString().split("_")[1] + "_planOrderType").enable();
  $$(id.toString().split("_")[1] + "_commandElementType").enable();
  $$(id.toString().split("_")[1] + "_alias").enable();
  $$(id.toString().split("_")[1] + "_description").enable();
  $$("bsave_" + id.toString().split("_")[1]).show();
  $$("bedit_" + id.toString().split("_")[1]).hide();
  $$("bremove_" + id.toString().split("_")[1]).hide();

  $$(id.toString().split("_")[1] + "_element").getList().clearAll();
  $$(id.toString().split("_")[1] + "_commandElementType").getList().clearAll();
  $$(id.toString().split("_")[1] + "_planOrderType").getList().clearAll();
  let value = $$(id.toString().split("_")[1] + "_element").data.value;
  let texto = $$(id.toString().split("_")[1] + "_element").data.text;
  let value_commandElementType =  $$(id.toString().split("_")[1] + "_commandElementType").data.value;
  let texto_commandElementType =  $$(id.toString().split("_")[1] + "_commandElementType").data.text;
  let value_planOrderType = $$(id.toString().split("_")[1] + "_planOrderType").data.value;
  let texto_planOrderType = $$(id.toString().split("_")[1] + "_planOrderType").data.text;
  //console.log($$(id.toString().split("_")[1] + "_planOrderType").data.value);
  //console.log($$(id.toString().split("_")[1] + "_planOrderType").data.value.split("_")[1]);
  let graphElems = webix.proxy("GraphQL",`
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

  graphElems.load()
  .then(data => {
    data.forEach(element => {
      if ($$(id.toString().split("_")[1] + "_element").data.value.split("_")[1] !== element.id) {
        $$(id.toString().split("_")[1] + "_element").getList().add({id:id.toString().split("_")[1] + "_" + element.id, value:element.alias});
      } else {
        element.elementType.commandElementTypes.forEach(e => {
          if ( $$(id.toString().split("_")[1] + "_commandElementType").data.value.split("_")[1] !== e.id) {
            $$(id.toString().split("_")[1] + "_commandElementType").getList().add({id:id.toString().split("_")[1] + "_" + e.id, value:e.alias});
          }
        });
      }
      });
      $$(id.toString().split("_")[1] + "_element").setValue({id: value ,value: texto });
      $$(id.toString().split("_")[1] + "_commandElementType").setValue({id: value_commandElementType ,value: texto_commandElementType });
    });
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
        if ($$(id.toString().split("_")[1] + "_planOrderType").data.value.split("_")[1] !== element.id)
          $$(id.toString().split("_")[1] + "_planOrderType").getList().add({id:id.toString().split("_")[1] + element.id, value:element.alias});
      });
    });
    $$(id.toString().split("_")[1] + "_planOrderType").setValue({id: value_planOrderType ,value: texto_planOrderType  });
}

function orderSave(id){
  $$("bsave_" + id.toString().split("_")[1]).hide();
  $$("bedit_" + id.toString().split("_")[1]).show();
  $$("bremove_" + id.toString().split("_")[1]).show();

  $$(id.toString().split("_")[1] + "_element").disable();
  $$(id.toString().split("_")[1] + "_planOrderType").disable();
  $$(id.toString().split("_")[1] + "_commandElementType").disable();
  $$(id.toString().split("_")[1] + "_alias").disable();
  $$(id.toString().split("_")[1] + "_description").disable();
  let idform = $$(id.toString().split("_")[1] + "_form").getValues();

  upOrderGrap.load({
    plan: {
      "id": idform.orderId,
      "alias": idform.alias,
      "description": idform.description,
      "planOrderTypeId": idform.planOrderType.split("_")[1],
      "planOrderValues": null,
      "comandElement": {
        "commandElementTypeId": idform.commandElementType.split("_")[1],
        "elementId": idform.element.split("_")[1],
        "commandElementValues": null //idform.commandElementValues === "" ? null : idform.commandElementValues
      }
    }
  })
  .then(data => {
    if(data) {
      let selectPlan = $$("treePlan").getSelectedId();
      let item = $$('treePlan').getItem(selectPlan);
      let itemPlanOrders = item["planOrders"].filter(function(value, index, arr){ return value.id !== data.id;});
      itemPlanOrders.push(data);
      item["planOrders"] = itemPlanOrders;
      $$('treePlan').updateItem(selectPlan, item);
      webix.message("Guardado Correctamente");
    }
  });
}

function orderRemove(id){
  let idform = id.toString().split("_")[1];
  let delOrderGrap = webix.proxy("GraphQL",`
  mutation deletePlanOrder($planOrderId: String!){
    deletePlanOrder(planOrderId: $planOrderId){
       id
     }
   }`
   );
   delOrderGrap.load({
    planOrderId: idform.toString()
  })
  .then(data => {
    if(data) {
      webix.message("Eliminado Correctamente");
      $$(idform + "_form").hide();
    }
  });
}

function buildc(id){
  let element = id.toString().split("_")[1];
  $$(id.toString().split("_")[0] + "_commandElementType").getList().clearAll();
  $$(id.toString().split("_")[0] + "_commandElementType").setValue({id:"" ,value: ""});
  let graphElems = webix.proxy("GraphQL",`
  query($rowId: String!) {
    getElement(elementId: $rowId){
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
    }
  }`);
  graphElems.load({
    rowId: element
  })
  .then(data => {
    //data.forEach(ele => {
      
      data.elementType.commandElementTypes.forEach(e => {
        $$(id.toString().split("_")[0] + "_commandElementType").getList().add({id:id.toString().split("_")[0] +"_" + e.id, value:e.alias});
      });
    //});
  });
}

function buildc_new(id){
  let element = id.toString().split("_")[2];
  let idplan  = id.toString().split("_")[3];
  //console.log(idplan);
  //console.log(element);
  $$("order_new_commandElementType_" + idplan).getList().clearAll();
  $$("order_new_commandElementType_" + idplan).setValue({id:"" ,value: ""});
  let graphElems = webix.proxy("GraphQL",`
  query($rowId: String!) {
    getElement(elementId: $rowId){
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
    }
  }`);
  graphElems.load({
    rowId: element
  })
  .then(data => {
    //data.forEach(ele => {
      data.elementType.commandElementTypes.forEach(e => {
        $$("order_new_commandElementType_" + idplan).getList().add({id:"order_new_" + e.id, value:e.alias});
      });
    //});
  });
}

export default class Order {
  constructor(id,data,elements,planOrderType) {
    this.data = data;
    //this.id = data.id;
    this.elements = [];
    this.planOrderType = [];
    this.elementsOption = [];
    this.elementsList = elements;
    this.planOrderTypeList = planOrderType;
    this.planOrderTypeOption = [];
    this.data = data;
    if (id === null)
    {
      if (data !== "new") {
        this.id = data.id;
        this.description = data.description === undefined ? null : data.description;
        this.alias = data.alias;
        this.state = data.state;
        this.planOrderValues = data.planOrderValues;
        this.planOrderType = data.planOrderType;
        this.comandElement = data.comandElement;
      } else {
        this.id = "";
        this.description = "";
        this.alias = "";
        this.state = "";
        this.planOrderValues = "";
        this.planOrderType = "";
        this.comandElement = "";
      }
    } else {
      this.id = id;
    }

    let self = this;

  }

  orderNewShow(){
    $$("order_new_form").show();
    
  }

  orderSaveNew(id){
    let idplan = id.toString().split("_")[3];
    let idform = $$("order_new_form_" + idplan).getValues();
    createOrderGrap.load({
      plan: {
        "planId": id.toString().split("_")[3],
        "alias": idform.alias,
        "description": idform.description,
        "planOrderTypeId": idform.planOrderType.split("_")[2],
        //"planOrderValues": {},
        "comandElement": {
          "commandElementTypeId": idform.commandElementType.split("_")[2],
          "elementId": idform.element.split("_")[2],
          "commandElementValues":[]//idform.commandElementValues
        }
      }
    })
    .then(data => {
      if(data) {
        let selectPlan = $$("treePlan").getSelectedId();
        let item = $$('treePlan').getItem(selectPlan);
        item["planOrders"].push(data);
        $$('treePlan').updateItem(selectPlan, item);
        $$("order_new_form_" + idplan).hide();
        $$("order_new_commandElementType_" + idplan).getList().clearAll();
        $$("order_new_planOrderType_" + idplan).getList().clearAll();
        $$("order_new_form_" + idplan).setValues({
          alias: "",
          description: "", 
          planOrderType: { id: "", value: "" }, 
          element: { id: "", value: "" },
          commandElementType: { id: "", value: "" },
          commandElementValues: ""
        });

        webix.message("Creado Correctamente");
      }
    });
  }

  dinamicFormOrder(view) {
    $$(view)
    .addView({
      view:"form", 
      id:this.id + "_form", 
      scroll:false, 
      elements:[{ responsive:this.id + "_form", 
        rows: [
          { view:"template", type:"header", template: this.id.toString(), height:50, align:"left" },
          { responsive:this.id + "_form",
            cols:[
              { view:"text", id: this.id.toString() + "_alias",   disabled: true, name: "alias",   label:$.i18n("plan.form.alias"), value: this.alias,         labelAlign:"left", labelWidth: 120, inputWidth:0 },                        
              { view:"text", id: this.id.toString() + "_orderId", disabled: true, name: "orderId", label:$.i18n("plan.form.id"), value: this.id.toString(), labelAlign:"left", labelWidth: 120, inputWidth:0 },
              { view:"text", id: this.id.toString() + "_state",   disabled: true, name: "state",   label:$.i18n("plan.form.state"), value: planOrderStateType[this.state],         labelAlign:"left", labelWidth: 120, inputWidth:0 }
            ]
          },{ responsive:this.id + "_form",
            cols:[
              { view:"text",  id: this.id.toString() + "_description",   disabled: true, name: "description",   label:$.i18n("plan.form.description"),   value:this.description, labelAlign:"left", labelWidth: 120, inputWidth:0 },
              { view:"combo", id: this.id.toString() + "_planOrderType", disabled: true, name: "planOrderType", label:$.i18n("plan.form.planOrderType"), value: { id:this.id.toString() + "_" + this.planOrderType.id, value:this.planOrderType.alias },   options:[] , labelAlign:"left", labelWidth: 120, inputWidth:0 },
              { gravity: 1 }
            ]
          },{ responsive:this.id + "_form",
            cols:[
              { 
                view:"combo", 
                id: this.id.toString() + "_element",              
                disabled: true, 
                name: "element", 
                label:$.i18n("plan.form.element"), 
                value:{ id: this.id.toString() + "_" + this.comandElement.element.id,
                        value: this.comandElement.element.alias, 
                        elementType: this.comandElement.element.elementType }, 
                options: [], 
                labelAlign:"left", 
                labelWidth: 120, 
                inputWidth:0,
                on: {
                      onChange: buildc
                    }
              },
              { view:"combo", id: this.id.toString() + "_commandElementType",   disabled: true, name: "commandElementType",  label:$.i18n("plan.form.commandElementType"), value:{id: this.id.toString() + "_" + this.comandElement.commandElementType.id, value:this.comandElement.commandElementType.alias}, options: [], labelAlign:"left", labelWidth: 120, inputWidth:0 },      
              { view:"text",  id: this.id.toString() + "_commandElementValues", disabled: true, name: "commandElementValues", label:$.i18n("plan.form.commandElementValues"), value: this.comandElement.commandElementValues ? this.id.toString() + "_" + this.comandElement.commandElementValues : "" , labelAlign:"left", labelWidth: 120, inputWidth:0 },
              //{gravity: 1},
            ]
          },{ responsive:this.id + "_form",
            cols:[
              { gravity: 3 },
              { view:"button", id: "bedit_" + this.id, type:"icon", icon:"mdi mdi-grease-pencil", width:50, placeHolder: $.i18n("plan.button.tooltip.edit"),
                click: orderEdit              
              },
              { view:"button", id: "bsave_" + this.id, type:"icon", icon:"mdi mdi-content-save", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.save"),
                click: orderSave
              },
              { view:"button", id: "bremove_" + this.id, type:"icon", icon:"mdi mdi-delete", width:50, placeHolder: $.i18n("plan.button.tooltip.cancel"),
                click: orderRemove
              }
            ]
          }
        ]
      }]
    });
/*
		let graphElems = webix.proxy("GraphQL",`
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
      graphElems.load()
      .then(data => {
        data.forEach(element => {
          //console.log(element);
          if (this.comandElement.element.id !== element.id) {
            $$(this.id.toString() + "_element").getList().add({id:this.id.toString() + "_" + element.id, value:element.alias});
          } else {
            element.elementType.commandElementTypes.forEach(e => {
              if ( this.comandElement.commandElementType.id !== e.id) {
                $$(this.id.toString() + "_commandElementType").getList().add({id:this.id.toString() + "_" + e.id, value:e.alias});
              }
            });
          }
          });
        });
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
            if (this.planOrderType.id !== element.id)
              $$(this.id.toString() + "_planOrderType").getList().add({id:element.id, value:element.alias});
          });
        });
      
/*
    new Promise((resolver, rechazar) => {
      console.log('Inicial');
      const elementsList2 = this.elementsList;
      resolver();
    })
    .then(() => {
        elementsList2.forEach(el => {
          console.log("DENTRO");
          $$(this.id.toString() + "_element").getList().add({id:el.id, value:el.alias});
          console.log("FUERA2");
          //this.elementsOption.push({id: element.id, value: element.alias});
        });
        throw new Error('Algo falló');
            
        console.log('Haz esto');
    })
    .catch(() => {
        console.log('Haz aquello');
    })
    .then(() => {
        console.log('Haz esto sin que importe lo que sucedió antes');
    });
/*    
    const elementsList2 = this.elementsList;
    console.log("FUERA");
    console.log(elementsList2);
    console.log(elementsList2.length);
    console.log(this.elementsList);
    for (let element of elementsList2) {
      console.log("DSDSDSDSDSD");
      console.log(element);
    }
    //console.log(this.elementsList.classList);
    $$(this.id.toString() + "_element").getList().add({id:"element.id1", value:"element.alias1"});
    $$(this.id.toString() + "_element").getList().add({id:"element.id2", value:"element.alias2"});
    elementsList2.forEach(el => {
      console.log("DENTRO");
      $$(this.id.toString() + "_element").getList().add({id:el.id, value:el.alias});
      console.log("FUERA2");
      //this.elementsOption.push({id: element.id, value: element.alias});
    });
    //elementsList2.each(function(obj){ console.log(obj); });

    //$$(this.id + "_element").getList().add({id:item.id, value:item.alias});
  */  
  }


  new_dinamicFormOrder(view,planId) {
    $$(view)
    .addView({
      view:"form", 
      id:"order_new_form_" + planId, 
      hidden: true,
      scroll:false, 
      elements:[{
        rows: [
          { view:"template", id: "order_new_title_" + planId, type:"header", template: $.i18n("plan.form.order.new.title"), height:50, align:"left" },
          {
            cols:[
              { view:"text", id: "order_new_alias_" + planId, name: "alias", label:$.i18n("plan.form.alias"), labelAlign:"left", labelWidth: 125, inputWidth:300 }
            ]
          },{
            cols:[ 
              { view:"text", id: "order_new_description_" + planId, name: "description",   label:$.i18n("plan.form.description"),  labelAlign:"left", labelWidth: 125, inputWidth:300 },
              { view:"combo",id: "order_new_planOrderType_" + planId, name: "planOrderType", label:$.i18n("plan.form.planOrderType"), options:[], labelAlign:"left", labelWidth: 200, inputWidth:400 },
              { gravity: 1 }
            ]
          },{
            cols:[
              { 
                view:"combo",  
                id: "order_new_element_" + planId,              
                name: "element", 
                label:$.i18n("plan.form.element"), 
                options: [], 
                labelAlign:"left", 
                labelWidth: 125, 
                inputWidth:300,
                on: {
                      onChange: buildc_new
                    }
              },   
              { view:"combo", id: "order_new_commandElementType_" + planId, name: "commandElementType",  label:$.i18n("plan.form.commandElementType"), options: [], labelAlign:"left", labelWidth: 200, inputWidth:400 },      
              { view:"text",  id: "order_new_commandElementValues_" + planId, name: "commandElementValues", label:$.i18n("plan.form.commandElementValues"), labelAlign:"left", labelWidth: 200, inputWidth:400 },
              //{gravity: 1},
            ]
          },{
            cols:[
              { gravity: 1 }, 
              { view:"button", id: "bsave_order_new_" + planId, type:"icon", icon:"mdi mdi-content-save", width:50, placeHolder: $.i18n("plan.button.tooltip.save"),
                click: this.orderSaveNew
              },
              { view:"button", id: "bclose_order_new_" + planId, type:"icon", icon:"mdi mdi-close", width:50, placeHolder: $.i18n("plan.button.tooltip.cancel"),
                click: function(){
                  $$("order_new_form_" + planId).hide();

                  let valias = "order_new_alias_" + planId;
                  let vdescription = "order_new_description_" + planId;
                  let vplanOrderType = "order_new_planOrderType_" + planId;
                  let velement = "order_new_element_" + planId;
                  let vcommandElementType = "order_new_commandElementType_" + planId;
                  let vcommandElementValues = "order_new_commandElementValues_" + planId;
 
                  $$(valias).setValue("");
                  $$(vdescription).setValue("");
                  $$(vplanOrderType).setValue({ id: "", value: "" });
                  $$(velement).setValue({ id: "", value: "" });
                  $$(vcommandElementType).setValue({ id: "", value: "" });
                  $$(vcommandElementValues).setValue("");
                }
              }
            ]
          }
        ]
      }]
    });

		let graphElems = webix.proxy("GraphQL",`
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
      graphElems.load()
      .then(data => {
        $$("order_new_element_" + planId).getList().clearAll();
        data.forEach(element => {
            //console.log(element);
            $$("order_new_element_" + planId).getList().add({id:"order_new_" + element.id + "_" + planId, value:element.alias});
          });
        });
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
          $$("order_new_planOrderType_" + planId).getList().clearAll();
          data.forEach(element => {  
              $$("order_new_planOrderType_" + planId).getList().add({id:"order_new_" + element.id + "_" + planId, value:element.alias});
          });
        });
     



/*
    new Promise((resolver, rechazar) => {
      console.log('Inicial');
      const elementsList2 = this.elementsList;
      resolver();
    })
    .then(() => {
        elementsList2.forEach(el => {
          console.log("DENTRO");
          $$(this.id.toString() + "_element").getList().add({id:el.id, value:el.alias});
          console.log("FUERA2");
          //this.elementsOption.push({id: element.id, value: element.alias});
        });
        throw new Error('Algo falló');
            
        console.log('Haz esto');
    })
    .catch(() => {
        console.log('Haz aquello');
    })
    .then(() => {
        console.log('Haz esto sin que importe lo que sucedió antes');
    });
/*    
    const elementsList2 = this.elementsList;
    console.log("FUERA");
    console.log(elementsList2);
    console.log(elementsList2.length);
    console.log(this.elementsList);
    for (let element of elementsList2) {
      console.log("DSDSDSDSDSD");
      console.log(element);
    }
    //console.log(this.elementsList.classList);
    $$(this.id.toString() + "_element").getList().add({id:"element.id1", value:"element.alias1"});
    $$(this.id.toString() + "_element").getList().add({id:"element.id2", value:"element.alias2"});
    elementsList2.forEach(el => {
      console.log("DENTRO");
      $$(this.id.toString() + "_element").getList().add({id:el.id, value:el.alias});
      console.log("FUERA2");
      //this.elementsOption.push({id: element.id, value: element.alias});
    });
    //elementsList2.each(function(obj){ console.log(obj); });

    //$$(this.id + "_element").getList().add({id:item.id, value:item.alias});
  */  
  }


  generateCombo(){}

  loadData() {
/*
		let graphElems = webix.proxy("GraphQL",`
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
      graphElems.load()
      .then(data => {
        data.forEach(element => {
            this.elements.push(element);
            this.elementsOption.push({id: element.id, value: element.alias});
          });
        });

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
          this.planOrderType.push(element);
          this.planOrderTypeOption.push({id: element.id, value: element.alias});
        });
      });
      */
  }

  init() {
    this.loadData();
  }
}
import * as elementTypeMap from "../lib/elementTypeMap.js";
import PanelManager from "../classes/PanelManager.js";
import {panelData} from "../data/panelData.js";
import * as revenga from "../lib/revenga.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";

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
      planOrderType {
        id
        alias
        description
        planOrderTypeParams {
          id
          alias
          description
        }
      }
      planOrderValues {
        id
        value
        planOrderTypeParam {
          id
          alias
          description
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
          }
        }
        commandElementValues {
          id
          value
          commandElementTypeParam {
            id
            alias
            description
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
      planOrderType {
        id
        alias
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
          }
        }
        commandElementValues {
          id
          value
          commandElementTypeParam {
            id
            alias
            description
          }
        }
      }
    }
  }
`);

export default class Order {
  constructor(id,data,elements,planOrderType,planId,view) {
    this.data = data;
    this.elements = [];
    this.planOrderType = [];
    this.elementsOption = [];
    this.elementsList = elements;
    this.planOrderTypeList = planOrderType;
    this.planOrderTypeOption = [];
    //this.data = data;
    this.planId = planId;
    this.view = view;
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

    this.elementtypegrahp = webix.proxy("GraphQL",`
    query  {
      getElementTypes {
          id
          alias
          commandElementTypes{id alias}
          }
      }
  `);
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
        "comandElement": {
          "commandElementTypeId": idform.commandElementType.split("_")[2],
          "elementId": idform.element.split("_")[2],
          "commandElementValues":[]
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

    if (this.comandElement.commandElementValues === null){
      $$(view)
      .addView({
        view:"form", 
        id:this.id + "_form", 
        scroll:false, 
        elements:[{ responsive:this.id + "_form", 
          rows: [
            //{ view:"template", type:"header", template: this.id.toString(), height:50, align:"left" },
            {
              id: "orderHeader"+this.id,
              cols:[
                {
                  id: "orderHeaderTitle"+this.id,
                  view: "template",
                  type: "header",
                  data: {
                    value: ""
                  },
                  template: "<div>" + this.alias + "</div>",
                  heigth: 150,
                  //css: { "text-align": "center !important" }
                  gravity:5
                }
              ]
            },
            { 
              cols:[
                {
                rows:[
                  { view:"text", id: this.id.toString() + "_alias",   disabled: true, name: "alias",   label:$.i18n("plan.form.alias"), value: this.alias,         labelAlign:"left", labelWidth: 120, inputWidth:0 },                        
                  { view:"combo", id: this.id.toString() + "_planOrderType", disabled: true, name: "planOrderType", label:$.i18n("plan.form.planOrderType"), value: { id:this.id.toString() + "_" + this.planOrderType.id, value:this.planOrderType.alias },   options:[] , labelAlign:"left", labelWidth: 120, inputWidth:0 },                
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
                    //on: {
                    //      onChange: buildc
                    //    }
                    }
                  ]
                },
                {
                  rows:[
                    { view:"textarea",  id: this.id.toString() + "_description",   disabled: true, name: "description",   label:$.i18n("plan.form.description"),   value:this.description, labelAlign:"left", labelWidth: 120, inputWidth:0 },  
                    { 
                      view:"combo", 
                      id: this.id.toString() + "_commandElementType",   
                      disabled: true, name: "commandElementType",  
                      label:$.i18n("plan.form.commandElementType"), 
                      value:{id: this.id.toString() + "_" + this.comandElement.commandElementType.id, value:this.comandElement.commandElementType.alias}, 
                      options: [], 
                      labelAlign:"left", 
                      labelWidth: 120, 
                      inputWidth:0 ,
                      //on: {
                      //  onChange: build_type_panel_edit
                      //}
                    },        
                  ]
                }
              ],  
            },
            { view:"textarea",  id: this.id.toString() + "_commandElementValues", disabled: true, name: "commandElementValues", label:$.i18n("plan.form.commandElementValues"), value: this.comandElement.commandElementValues ? JSON.stringify(this.comandElement.commandElementValues) : "" , labelAlign:"left", labelWidth: 120, inputWidth:0, hidden: true },
            { responsive:this.id + "_form",
              cols:[
                { gravity: 3 },
                { view:"button", id: "bedit_" + this.id, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN), icon:"mdi mdi-grease-pencil", width:50, placeHolder: $.i18n("plan.button.tooltip.edit"),
                  click: orderEdit              
                },
                { view:"button", id: "bsave_" + this.id, type:"icon", icon:"mdi mdi-content-save", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.save"),
                  click: orderSave
                },
                { view:"button", id: "bremove_" + this.id, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN), icon:"mdi mdi-delete", width:50, placeHolder: $.i18n("plan.button.tooltip.remove"),
                  click: orderRemove
                },
                { view:"button", id: "becancel_" + this.id, type:"icon", icon:"mdi mdi-close-outline", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.cancel"),
                  click: orderEditCancel
                }
              ]
            }
          ]
        }]
      });
    }else{
      let message = this.comandElement.commandElementType.alias;
      let idtype = "ElementType:" + this.comandElement.element.id.split(":")[1];
      let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
      if (pmvTypes.includes(this.comandElement.element.id.split(":")[1]) && message === "SEND MESSAGE"){ 
        let text_commands = this.comandElement.commandElementValues;
        $$(view)
        .addView({
          view:"form", 
          id:this.id + "_form", 
          scroll:false, 
          elements:[{ responsive:this.id + "_form", 
            rows: [
              //{ view:"template", type:"header", template: this.id.toString(), height:50, align:"left" },
              {
                id: "elementHeader"+this.id,
                cols:[
                  {
                    id: "orderHeaderTitle"+this.id,
                    view: "template",
                    type: "header",
                    data: {
                      value: ""
                    },
                    template: "<div>" + this.alias + "</div>",
                    heigth: 150,
                    //css: { "text-align": "center !important" }
                    gravity:5
                  }
                ]
              },
              { 
                cols:[
                  {
                  rows:[
                    { view:"text", id: this.id.toString() + "_alias",   disabled: true, name: "alias",   label:$.i18n("plan.form.alias"), value: this.alias,         labelAlign:"left", labelWidth: 120, inputWidth:0 },                        
                    { view:"combo", id: this.id.toString() + "_planOrderType", disabled: true, name: "planOrderType", label:$.i18n("plan.form.planOrderType"), value: { id:this.id.toString() + "_" + this.planOrderType.id, value:this.planOrderType.alias },   options:[] , labelAlign:"left", labelWidth: 120, inputWidth:0 },                
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
                      //on: {
                      //      onChange: buildc
                      //    }
                      }
                    ]
                  },
                  {
                    rows:[
                      { view:"textarea",  id: this.id.toString() + "_description",   disabled: true, name: "description",   label:$.i18n("plan.form.description"),   value:this.description, labelAlign:"left", labelWidth: 120, inputWidth:0 },  
                      { 
                        view:"combo", 
                        id: this.id.toString() + "_commandElementType",   
                        disabled: true, name: "commandElementType",  
                        label:$.i18n("plan.form.commandElementType"), 
                        value:{id: this.id.toString() + "_" + this.comandElement.commandElementType.id, value:this.comandElement.commandElementType.alias}, 
                        options: [], 
                        labelAlign:"left", 
                        labelWidth: 120, 
                        inputWidth:0 ,
                        //on: {
                        //  onChange: build_type_panel_edit
                        //}
                      },        
                    ]
                  }
                ],  
              },
              { view:"textarea",  id: this.id.toString() + "_commandElementValues", hidden: true, disabled: true, name: "commandElementValues", label:$.i18n("plan.form.commandElementValues"), value: this.comandElement.commandElementValues ? JSON.stringify(this.comandElement.commandElementValues) : "" , labelAlign:"left", labelWidth: 120, inputWidth:0, hidden: true },
              { responsive:this.id + "_form",
                cols:[
                  { gravity: 3 },
                  { view:"button", id: "bedit_" + this.id, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN) ,icon:"mdi mdi-grease-pencil", width:50, placeHolder: $.i18n("plan.button.tooltip.edit"),
                    click: orderEdit              
                  },
                  { view:"button", id: "bsave_" + this.id, type:"icon", icon:"mdi mdi-content-save", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.save"),
                    click: orderSave
                  },
                  { view:"button", id: "bremove_" + this.id, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN), icon:"mdi mdi-delete", width:50, placeHolder: $.i18n("plan.button.tooltip.remove"),
                    click: orderRemove
                  },
                  { view:"button", id: "becancel_" + this.id, type:"icon", icon:"mdi mdi-close-outline", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.cancel"),
                    click: orderEditCancel
                  }
                ]
              }
            ]
          }]
        });   
        let datos = null;
        let lista_ordenada = [];
        let lista_valores = {};
        if(text_commands !== "" && text_commands !== undefined && text_commands !== null){
          datos = text_commands;//JSON.parse(text_commands.replace('\"','"'));
          datos.forEach(item => {
            lista_ordenada.push(parseInt(item.id.split(":")[3]));
            lista_valores[parseInt(item.id.split(":")[3])] = { value: item.value };
          });
          lista_ordenada.sort();
        }
        let scala = panelData[idtype].scala_List;
        let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN,scala,lista_valores,this.id);
        $$(this.id + "_form").addView(
          {
            view:"dashboard",
            id:"order_edit_dashboard_" + this.id,
            gridColumns:1,
            //width:tamx,
            gridRows:1,
            disable: true,
            cellHeight:300,
            cells:[
              { view:"panel", x:0, y:0, dx:1, dy:1, body: panelMan.getInitComponent()},
            ],
          }
        );
        panelMan.loadComponent();
      }else{
        $$(view)
        .addView({
          view:"form", 
          id:this.id + "_form", 
          scroll:false, 
          elements:[{ responsive:this.id + "_form", 
            rows: [
              { view:"template", type:"header", template: this.id.toString(), height:50, align:"left" },
              { 
                cols:[
                  {
                  rows:[
                    { view:"text", id: this.id.toString() + "_alias",   disabled: true, name: "alias",   label:$.i18n("plan.form.alias"), value: this.alias,         labelAlign:"left", labelWidth: 120, inputWidth:0 },                        
                    { view:"combo", id: this.id.toString() + "_planOrderType", disabled: true, name: "planOrderType", label:$.i18n("plan.form.planOrderType"), value: { id:this.id.toString() + "_" + this.planOrderType.id, value:this.planOrderType.alias },   options:[] , labelAlign:"left", labelWidth: 120, inputWidth:0 },                
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
                      //on: {
                      //      onChange: buildc
                      //    }
                      }
                    ]
                  },
                  {
                    rows:[
                      { view:"textarea",  id: this.id.toString() + "_description",   disabled: true, name: "description",   label:$.i18n("plan.form.description"),   value:this.description, labelAlign:"left", labelWidth: 120, inputWidth:0 },  
                      { 
                        view:"combo", 
                        id: this.id.toString() + "_commandElementType",   
                        disabled: true, name: "commandElementType",  
                        label:$.i18n("plan.form.commandElementType"), 
                        value:{id: this.id.toString() + "_" + this.comandElement.commandElementType.id, value:this.comandElement.commandElementType.alias}, 
                        options: [], 
                        labelAlign:"left", 
                        labelWidth: 120, 
                        inputWidth:0 ,
                        //on: {
                        //  onChange: build_type_panel_edit
                        //}
                      },        
                    ]
                  }
                ],  
              },
              { view:"textarea",  id: this.id.toString() + "_commandElementValues", hidden: true, disabled: true, name: "commandElementValues", label:$.i18n("plan.form.commandElementValues"), value: this.comandElement.commandElementValues ? JSON.stringify(this.comandElement.commandElementValues) : "" , labelAlign:"left", labelWidth: 120, inputWidth:0 },
              { responsive:this.id + "_form",
                cols:[
                  { gravity: 3 },
                  { view:"button", id: "bedit_" + this.id, type:"icon",  disabled: !checkUserPermission(RULE.PLAN_ADMIN), icon:"mdi mdi-grease-pencil", width:50, placeHolder: $.i18n("plan.button.tooltip.edit"),
                    click: orderEdit              
                  },
                  { view:"button", id: "bsave_" + this.id, type:"icon", icon:"mdi mdi-content-save", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.save"),
                    click: orderSave
                  },
                  { view:"button", id: "bremove_" + this.id, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN), icon:"mdi mdi-delete", width:50, placeHolder: $.i18n("plan.button.tooltip.remove"),
                    click: orderRemove
                  },
                  { view:"button", id: "becancel_" + this.id, type:"icon", icon:"mdi mdi-close-outline", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.cancel"),
                    click: orderEditCancel
                  }
                ]
              }
            ]
          }]
        });  
      }
    }
  }

  new_dinamicFormOrder(view,planId){
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
  }

  getInitComponent(){
    let initComponent = {
			view:"form",
			id:'newOrderForm',
			elements:{}
		  };
		return initComponent;
  }

  elementReady(selector) {
    return new Promise((resolve, reject) => {
        let el = document.querySelector(selector);
        if (el) {
            resolve(el);
        }
        new MutationObserver((mutationRecords, observer) => {
            // Query for elements matching the specified selector
            Array.from(document.querySelectorAll(selector)).forEach((element) => {
                resolve(element);
                //Once we have resolved we don't need the observer anymore.
                observer.disconnect();
            });
        }).observe(document.documentElement, {
            childList: true,
            subtree: true,
        });
    });
  }

  loadComponent(){

    this.elementReady('[view_id="' + "newOrderForm" + '"]').then((c) => {
			let spinnerDivToAdd = document.createElement("div");
			spinnerDivToAdd.setAttribute("id", "newOrderForm"  + "spinner");
			spinnerDivToAdd.setAttribute("class", "spinner");
			document
			.querySelector('[view_id="' + "newOrderForm"  + '"]')
			.prepend(spinnerDivToAdd);
		});
    

		this.elementtypegrahp.load().then(dataElement => {
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
            }
          }
        }`
      );
      let lista = [];
      this.planOrderTypeList = [];
      planOrderType.load()
      .then(data => {
        data.forEach(item => { 
          lista.push({id:"window_order_new_" + item.id + "_" + this.planId, value:item.alias});
        });
      
        let etypesList = [];
        dataElement.forEach(etype => { 
          if (etype.commandElementTypes !== null)
            etypesList.push({ id: "window_order_" + this.planId + "_" + etype.id, value: etype.alias });
        });
        
        this.grid = {
          view:"form", 
          id:"window_order_new_form_" + this.planId, 
          hidden: true,
          scroll:false, 
          elements:[{
            rows: [
              { view:"template", id: "window_order_new_title_" + this.planId, type:"header", template: $.i18n("plan.form.order.new.title"), height:50, align:"left" },
              {
                cols:[
                  {
                    rows: [
                      { view:"text", id: "window_order_new_alias_" + this.planId, name: "alias", label:$.i18n("plan.form.alias"), labelAlign:"left", labelWidth: 125, inputWidth:400 },
                      { view:"combo",id: "window_order_new_planOrderType_" + this.planId, name: "planOrderType", label:$.i18n("plan.form.planOrderType"), options:lista, labelAlign:"left", labelWidth: 125, inputWidth:400 },
                      { 
                        view:"combo",  
                        id: "window_order_new_element_" + this.planId,              
                        name: "element", 
                        label:"ElementType",//$.i18n("plan.form.element"), 
                        options: etypesList, 
                        labelAlign:"left", 
                        labelWidth: 125, 
                        inputWidth:400,
                        on: {
                              onChange: build_equipment_table
                            }
                      },
                    ]
                  },
                  {
                    rows: [
                    { view:"textarea", id: "window_order_new_description_" + this.planId, name: "description",   label:$.i18n("plan.form.description"),  labelAlign:"left", labelWidth: 125, inputWidth:400 },
                    { 
                      view:"combo", 
                      id: "window_order_new_commandElementType_" + this.planId, 
                      name: "commandElementType",  
                      label:$.i18n("plan.form.commandElementType"), 
                      options: [], 
                      labelAlign:"left", 
                      labelWidth: 125, 
                      inputWidth:400,
                      on: {
                        onChange: build_type_table
                      }
                    }
                    ]
                  }
                ],
              },
              { template:"" },
              {},
              {
                cols:[
                  { 
                    id: "window_order_new_datatable_" + this.planId,	
                    view:"datatable",
                    width:240,
                    yCount: 5,
                    scrollX:false,
                    columns:[
                      { id:"ch1", header:{ content:"masterCheckbox" }, checkValue:'on', uncheckValue:'off', template:"{common.checkbox()}", width:40},
                      { id:"title",	sort:"string", header:"Equipo",width:200},
                    ],
                  },
                  {
                    view:"dashboard",
                    id:"window_order_new_dashboard_" + this.planId,
                    gridColumns:1,
                    width:950,
                    gridRows:1,
                    cellHeight:380,
                    cells:[
                      { id: "window_order_new_panel_" + this.planId, view:"panel", x:0, y:0, dx:1, dy:1, body:{
                          template:"Commands"
                      }},
                    ],

                  }
                ]
              },
              {
                view:"toolbar",
                id:"window_order_new_toolbar",
                cols:[
                    { view:"button", id: this.planId + "_" + this.view + "_window_order_new_button_load", value:"Guardar", width:100, align:"right", 
                    click: saveWindowOrder
                  }]
            }
            ]
          }]
        };     
        webix.ui(this.getComponent(), $$('newOrderForm'));
        document.getElementById("newOrderForm" +  "spinner").style.display = "none";
      });
    });
  }

  getComponent() {
    let result = {
      type:"space", cols:[
        { view:"scrollview", scroll:"auto", body:this.grid, 
        width:1250,
        height:700 },
      ]
    };
    return result; 
  }
}

function saveWindowOrder(id){
  let container = id.split("_")[1];
  if ($$("window_order_new_alias_" + id.split("_")[0]).data.value !== "") {
    let v_alias = $$("window_order_new_alias_" + id.split("_")[0]).data.value;
    let v_description = $$("window_order_new_description_" + id.split("_")[0]).data.value;
    if ($$("window_order_new_planOrderType_" + id.split("_")[0]).data.value !== "") {
      let v_planOrderType = $$("window_order_new_planOrderType_" + id.split("_")[0]).data.value.split("_")[3];
      if ($$("window_order_new_commandElementType_" + id.split("_")[0]).data.value !== "") {
        let v_commandElementType = $$("window_order_new_commandElementType_" + id.split("_")[0]).data.value.split("_")[0];
        let elements = [];
        $$("window_order_new_datatable_" + id.split("_")[0]).data.order.forEach(item => {
          let v_dato = $$("window_order_new_datatable_" + id.split("_")[0]).data.pull[item];
          if(v_dato.ch1 !== undefined && v_dato.ch1 === "on")
            elements.push(v_dato.id.split("_")[0]);
        });
        let v_commands = [];
        let v_commands_values = [];
        if (elements.length > 0) {
          let idtype = $$("window_order_new_element_" + id.split("_")[0]).data.value.split("_")[3];

          let graphElemType = `query {
            getElementType(elementTypeId: "` + idtype + `")
            {
              id
              alias
              description
              commandElementTypes{  
                id 
                alias 
                commandElementTypeParams { 
                  id 
                  alias 
                }
              }
            }
          }`;
          
          let graphqlEType = webix.proxy("GraphQL", graphElemType);
          graphqlEType.load().then(data => {
            let send_command = null;
            data.commandElementTypes.forEach(element => {
              if (element.alias === "SEND MESSAGE") {
                try {
                  send_command = element.id;
                  element.commandElementTypeParams.forEach(item => {
                    v_commands.push(item.id);
                  });
                } catch (e) {
                  console.log(e);
                }
              }
            });
            let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
            if (pmvTypes.includes(idtype.split(":")[1])){
              if(v_commandElementType === send_command){ //si es SEND COMMAND MIRAMOS LOS VALORES DEL PANEL
                let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN,3,null,undefined);
                let dataList = panelMan.configExportData();

                let panels = dataList[0];
                let datos = [];
                let panelHash = {};
                let panelesList = [];
                panels.forEach(item =>{
                  panelesList.push(item.split("_")[1]);
                });
                let panelUnique = [...new Set(panelesList)];
                panelUnique.forEach(item =>{
                  let hashdato = {
                    type: "",
                    numLineas: 0
                  }
                  panelHash[item] = hashdato;
                });
                panels.forEach(item =>{
                  if (item.split("_")[2] === "G") {
                    panelHash[item.split("_")[1]].type = "G";
                  } else {
                    panelHash[item.split("_")[1]].type = "T";
                    panelHash[item.split("_")[1]].numLineas = parseInt(item.split("_")[4]) + 1;
                  }
                });
                Object.entries(panelHash).map(([k, v]) => {
                  if (v.type === "G"){
                    datos.push($$("panel_" + k + "_G_undefined").data.src);
                  } else {
                    let texto = "";
                    for(let i=0;i<v.numLineas;i++) {
                      texto = texto + $$("panel_" + k + "_T_L_" + i + "_undefined").data.value;
                      if(i !== (v.numLineas - 1 )){
                        texto = texto + ":$";
                      }
                    }
                    datos.push(texto);
                  }
                });
                if (v_commands.length > 0) {
                  let vcommands = v_commands.sort();
                  
                  for (let i=0;i<vcommands.length; i++) {
                    v_commands_values.push({
                      "commandElementTypeParamId": vcommands[i],
                      "value": datos[i]
                    });
                  }
                }
              }
            }

            let query_completa = ``;
            let text_query = ``;
            let text_query_head = `mutation createPlanOrder(`;
            let data_query = {};
            if(elements.length > 1) {
              let contador1 = 1;
              elements.forEach(item => {
                if(contador1>1){
                  text_query_head = text_query_head + `,$planOrder` + contador1 + `: PlanOrderVO!`;
                }else{
                  text_query_head = text_query_head + `$planOrder` + contador1 + `: PlanOrderVO!`;
                }
                text_query = text_query + ` order` + contador1 + `:createPlanOrder(planOrder: $planOrder` + contador1 + `) {
                  id
                  alias
                  description
                  planOrderType {
                    id
                    alias
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
                      }
                    }
                    commandElementValues {
                      id
                      value
                      commandElementTypeParam {
                        id
                        alias
                        description
                      }
                    }
                  }
                }
                `;
                data_query["planOrder"+ contador1]={
                  "planId": id.split("_")[0],
                  "alias": v_alias,
                  "description": v_description,
                  "planOrderTypeId": v_planOrderType,
                  "comandElement": {
                    "commandElementTypeId": v_commandElementType,
                    "elementId": item,
                    "commandElementValues":v_commands_values
                  }
                }
                contador1 +=1;
              });
              query_completa = text_query_head + `) {` + text_query + `}`;
              let createOrden = webix.proxy("GraphQL",query_completa);
              createOrden.load(data_query).then(data => {
                if(data) {
                  Object.entries(data).map(([k, v]) => {
                    createOrderDinamic(container,v);
                  });
                  $$("new_order_window_" + id.split("_")[0]).hide();
                  webix.message("Guardado Correctamente");
                } else {
                  webix.message("ERROR AL CREAR");
                }
              });
            } else {
              elements.forEach(item => {
                createOrderGrap.load({
                  plan: {
                    "planId": id.split("_")[0],
                    "alias": v_alias,
                    "description": v_description,
                    "planOrderTypeId": v_planOrderType,
                    "comandElement": {
                      "commandElementTypeId": v_commandElementType,
                      "elementId": item,
                      "commandElementValues":v_commands_values
                    }
                  }
                })
                .then(data => {
                  if(data) {
                    createOrderDinamic(container,data);
                    $$("new_order_window_" + id.split("_")[0]).hide();
                    webix.message("Creado Correctamente");
                  } else {
                    webix.message("ERROR AL CREAR");
                  }
                });
              });
            }
          });
        } else {
          webix.message({type:"error", text:$.i18n("lbl_order_window_element_error")});
        }
      } else {
        webix.message({type:"error", text:$.i18n("lbl_order_window_command_error")});
      }
    } else {
      webix.message({type:"error", text:$.i18n("lbl_order_window_planordertype_error")});
    }
  } else {
    webix.message({type:"error", text:$.i18n("lbl_order_window_alias_error")});
  }
}

function createOrderDinamic(container,data){
  let result = updateOrderTree("treePlan",data,"new");

  try {
    let v_view = $$(data.id + "_form");
    if (v_view !== undefined){
      $$(data.id + "_form").show();
      $$(data.id.toString() + "_alias").setValue(data.alias);
      $$(data.id.toString() + "_description").setValue(data.description);
      $$(data.id.toString() + "_planOrderType").setValue({ id:data.id.toString() + "_" + data.planOrderType.id, value:data.planOrderType.alias });
      $$(data.id.toString() + "_element").setValue({ id: data.id.toString() + "_" + data.comandElement.element.id, value: data.comandElement.element.alias });
      $$(data.id.toString() + "_commandElementType").setValue({ id: data.id.toString() + "_" + data.comandElement.commandElementType.id, value:data.comandElement.commandElementType.alias });
      $$(data.id.toString() + "_commandElementValues").setValue(data.comandElement.commandElementValues ? JSON.stringify(data.comandElement.commandElementValues) : "" );
    } else {

      $$(container)
      .addView({
        view:"form", 
        id:data.id + "_form", 
        scroll:false, 
        elements:[{ responsive:data.id + "_form", 
          rows: [
            { view:"template", type:"header", template: data.id.toString(), height:50, align:"left" },
            { 
              cols:[
                {
                rows:[
                  { view:"text", id: data.id.toString() + "_alias",   disabled: true, name: "alias",   label:$.i18n("plan.form.alias"), value: data.alias,         labelAlign:"left", labelWidth: 120, inputWidth:0 },                        
                  { view:"combo", id: data.id.toString() + "_planOrderType", disabled: true, name: "planOrderType", label:$.i18n("plan.form.planOrderType"), value: { id:data.id.toString() + "_" + data.planOrderType.id, value:data.planOrderType.alias },   options:[] , labelAlign:"left", labelWidth: 120, inputWidth:0 },                
                  { 
                    view:"combo", 
                    id: data.id.toString() + "_element",              
                    disabled: true, 
                    name: "element", 
                    label:$.i18n("plan.form.element"), 
                    value:{ id: data.id.toString() + "_" + data.comandElement.element.id,
                            value: data.comandElement.element.alias, 
                            }, 
                    options: [], 
                    labelAlign:"left", 
                    labelWidth: 120, 
                    inputWidth:0,
                    //on: {
                    //      onChange: buildc
                    //    }
                    }
                  ]
                },
                {
                  rows:[
                    { view:"textarea",  id: data.id.toString() + "_description",   disabled: true, name: "description",   label:$.i18n("plan.form.description"),   value:data.description, labelAlign:"left", labelWidth: 120, inputWidth:0 },  
                    { 
                      view:"combo", 
                      id: data.id.toString() + "_commandElementType",   
                      disabled: true, 
                      name: "commandElementType",  
                      label:$.i18n("plan.form.commandElementType"), 
                      value:{
                        id: data.id.toString() + "_" + data.comandElement.commandElementType.id, 
                        value:data.comandElement.commandElementType.alias
                      }, 
                      options: [], 
                      labelAlign:"left", 
                      labelWidth: 120, 
                      inputWidth:0,
                      //on: {
                      //  onChange: build_type_panel_edit
                      //}
                    },        
                  ]
                }
              ],  
            },
            { view:"textarea",  id: data.id.toString() + "_commandElementValues", hidden: true, disabled: true, name: "commandElementValues", label:$.i18n("plan.form.commandElementValues"), value: data.comandElement.commandElementValues ? JSON.stringify(data.comandElement.commandElementValues) : "" , labelAlign:"left", labelWidth: 120, inputWidth:0 },
            { //responsive:data.id + "_form",
              cols:[
                { gravity: 3 },
                { view:"button", id: "bedit_" + data.id, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN), icon:"mdi mdi-grease-pencil", width:50, placeHolder: $.i18n("plan.button.tooltip.edit"),
                  click: orderEdit              
                },
                { view:"button", id: "bsave_" + data.id, type:"icon", icon:"mdi mdi-content-save", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.save"),
                  click: orderSave
                },
                { view:"button", id: "bremove_" + data.id, type:"icon", disabled: !checkUserPermission(RULE.PLAN_ADMIN), icon:"mdi mdi-delete", width:50, placeHolder: $.i18n("plan.button.tooltip.remove"),
                  click: orderRemove
                },
                { view:"button", id: "becancel_" + data.id, type:"icon", icon:"mdi mdi-close-outline", width:50, hidden: true, placeHolder: $.i18n("plan.button.tooltip.cancel"),
                  click: orderEditCancel
                }
              ]
            }
          ]
        }]
      });

      let message = data.comandElement.commandElementType.alias;
      let idtype = "ElementType:" + data.comandElement.element.id.split(":")[1];
      let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
      if (pmvTypes.includes(data.comandElement.element.id.split(":")[1]) && message === "SEND MESSAGE"){ 
        let text_commands = data.comandElement.commandElementValues;

        let datos = null;
        let lista_ordenada = [];
        let lista_valores = {};
        if(text_commands !== "" && text_commands !== undefined && text_commands !== null){
          datos = text_commands;//JSON.parse(text_commands.replace('\"','"'));
          datos.forEach(item => {
            lista_ordenada.push(parseInt(item.id.split(":")[3]));
            lista_valores[parseInt(item.id.split(":")[3])] = { value: item.value };
          });
          lista_ordenada.sort();
        }
        let scala = panelData[idtype].scala_List;
        let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN,scala,lista_valores,data.id);
        $$(data.id + "_form").addView(
          {
            view:"dashboard",
            id:"order_edit_dashboard_" + data.id,
            gridColumns:1,
            //width:tamx,
            gridRows:1,
            disable: true,
            cellHeight:300,
            cells:[
              { view:"panel", x:0, y:0, dx:1, dy:1, body: panelMan.getInitComponent()},
            ],
          }
        );
        panelMan.loadComponent();
      }
    }
  } catch (e) {
    console.log(e);
  }
}

function orderEditCancel(id) {
  let idOrder = id.split("_")[1];
  let selectPlan = $$("treePlan").getSelectedId();
  let item = $$("treePlan").getItem(selectPlan);
  let itemPlanOrders = item["planOrders"].find(function(value, index, arr){ return value.id === idOrder;});
  let idform = $$(idOrder + "_form").getValues();

  if (idform.alias !== itemPlanOrders.alias) 
    //restauramos el alias
    $$(idOrder + "_alias").setValue(itemPlanOrders.alias);

  if (idform.description !== itemPlanOrders.description)
    $$(idOrder + "_description").setValue(itemPlanOrders.description);

  if (idform.planOrderType.split("_")[1] !== itemPlanOrders.planOrderType.id)
    $$(idOrder + "_planOrderType").setValues({ id:idOrder + "_" + itemPlanOrders.planOrderType.id, value:itemPlanOrders.planOrderType.alias });

//  if (idform.element.split("_")[1] !== itemPlanOrders.comandElement.element.id)
//    $$(idOrder + "_element").setValues({ id: idOrder + "_" + itemPlanOrders.comandElement.element.id, value: itemPlanOrders.comandElement.element.alias });

//  if (idform.commandElementType.split("_")[1] !== itemPlanOrders.comandElement.commandElementType.id)
//    $$(idOrder + "_commandElementType").setValue({ id: idOrder + "_" + itemPlanOrders.comandElement.commandElementType.id, value: itemPlanOrders.comandElement.commandElementType.alias });

  //$$(idOrder + "_commandElementValues").setValue(itemPlanOrders.comandElement.commandElementValues ? JSON.stringify(itemPlanOrders.comandElement.commandElementValues) : "");

  $$("bsave_" + idOrder).hide();
  $$("becancel_" + idOrder).hide();
  $$("bedit_" + idOrder).show();
  $$("bremove_" + idOrder).show();

  $$(idOrder + "_element").disable();
  $$(idOrder + "_planOrderType").disable();
  $$(idOrder + "_commandElementType").disable();
  $$(idOrder + "_alias").disable();
  $$(idOrder + "_description").disable();
  //remove dashboard
  if ($$("order_edit_dashboard_" + idOrder))
    $$(idOrder + "_form").removeView("order_edit_dashboard_" + idOrder);

  let text_commands = $$(idOrder + "_commandElementValues").data.value;
  let value = $$(idOrder + "_element").data.value;
  let idtype = "ElementType:" + elementTypeMap.getElementType(value.split("_")[1]);
  let datos = null;
  let message = $$(idOrder + "_commandElementType").config.text;
  let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
  if (pmvTypes.includes(idtype.split(":")[1]) && message === "SEND MESSAGE"){
    let lista_ordenada = [];
    let lista_valores = {};
    if(text_commands !== "" && text_commands !== undefined){
      datos = JSON.parse(text_commands.replace('\"','"'));
      datos.forEach(item => {
        lista_ordenada.push(parseInt(item.id.split(":")[3]));
        lista_valores[parseInt(item.id.split(":")[3])] = { value: item.value };
      });
      lista_ordenada.sort();
    }

    let scala = panelData[idtype].scala_List;
    let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN,scala,lista_valores,idOrder);   
    $$(idOrder + "_form").addView(
      {
        view:"dashboard",
        id:"order_edit_dashboard_" + idOrder,
        gridColumns:1,
        //width:tamx,
        gridRows:1,
        cellHeight:300,
        cells:[
          { view:"panel", x:0, y:0, dx:1, dy:1, body: panelMan.getInitComponent()},
        ],
      }
    );
    panelMan.loadComponent();
  }
  let myEvent = $$(idOrder + "_element").detachEvent("onChange");
  let myEvent2 = $$(idOrder + "_commandElementType").detachEvent("onChange");
}

function orderEdit(id){
  //DESHABILITAMOS LOS OTROS POSIBLES EDIT Y ELIMMINAMOS LOS OTROS DASHBOARDS QUE TENGAMOS
  let padre = $$(id.toString().split("_")[1] + "_form").getParentView();
  let hijos = padre.getChildViews();
  let count01 = 0;
  hijos.forEach(item => {
    if(count01 > 1){
      if(item.config.id.split("_")[0] !== id.toString().split("_")[1]){
        try {
          if($$("order_edit_dashboard_" + item.config.id.split("_")[0])) {
            $$("bsave_" + item.config.id.split("_")[0]).hide();
            $$("becancel_" + item.config.id.split("_")[0]).hide();
            $$("bedit_" + item.config.id.split("_")[0]).show();
            $$("bremove_" + item.config.id.split("_")[0]).show();
          
            $$(item.config.id.split("_")[0] + "_element").disable();
            $$(item.config.id.split("_")[0] + "_planOrderType").disable();
            $$(item.config.id.split("_")[0] + "_commandElementType").disable();
            $$(item.config.id.split("_")[0] + "_alias").disable();
            $$(item.config.id.split("_")[0] + "_description").disable();
            //$$(item.config.id.split("_")[0] + "_form").removeView("order_edit_dashboard_" + item.config.id.split("_")[0]);
          }
        } 
        catch(e) {console.log(e)}
      }
    }
    count01 +=1;
  });

  $$(id.toString().split("_")[1] + "_element").enable();
  $$(id.toString().split("_")[1] + "_planOrderType").enable();
  $$(id.toString().split("_")[1] + "_commandElementType").enable();
  $$(id.toString().split("_")[1] + "_alias").enable();
  $$(id.toString().split("_")[1] + "_description").enable();
  $$("bsave_" + id.toString().split("_")[1]).show();
  $$("becancel_" + id.toString().split("_")[1]).show();
  $$("bedit_" + id.toString().split("_")[1]).hide();
  $$("bremove_" + id.toString().split("_")[1]).hide();
  $$(id.toString().split("_")[1] + "_element").getList().clearAll();
  $$(id.toString().split("_")[1] + "_commandElementType").getList().clearAll();
  //$$(id.toString().split("_")[1] + "_planOrderType").getList().clearAll();
  let value = $$(id.toString().split("_")[1] + "_element").data.value;
  let idtype = "ElementType:" + elementTypeMap.getElementType(value.split("_")[1]);

  //let texto = $$(id.toString().split("_")[1] + "_element").data.text;
  let value_commandElementType =  $$(id.toString().split("_")[1] + "_commandElementType").data.value;

  let graphElems = webix.proxy("GraphQL",`
  query($rowId: String!) {
    getElementsByType(elementTypeId: $rowId){
      id
      alias
    }
  }`);
  graphElems.load({
    rowId: idtype
  })
  .then(data => {
    data.forEach(item => {
      if (value.split("_")[1] !== item.id) {
        $$(id.toString().split("_")[1] + "_element").getList().add({ id: id.toString().split("_")[1] + "_" + item.id, value: item.alias });
      }
    });
  });

  let graphElems1 = webix.proxy("GraphQL",`
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
          }
        }
      }
    }
  }`);
  graphElems1.load({
    rowId: value.split("_")[1]
  })
  .then(data => {
    data.elementType.commandElementTypes.forEach(e => {
      if(value_commandElementType.split("_")[1] !== e.id)
        $$(id.toString().split("_")[1] + "_commandElementType").getList().add({id:id.toString().split("_")[1] +"_" + e.id, value:e.alias});
    });
  });

  let myEvent = $$(id.toString().split("_")[1] + "_element").attachEvent("onChange", buildc);
  let myEvent2 = $$(id.toString().split("_")[1] + "_commandElementType").attachEvent("onChange", build_type_panel_edit);
  let text_commands = $$(id.toString().split("_")[1] + "_commandElementValues").data.value;
  
  let datos = null;
  let message = $$(id.toString().split("_")[1] + "_commandElementType").config.text;
  let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
  if (pmvTypes.includes(idtype.split(":")[1]) && message === "SEND MESSAGE"){
    let lista_ordenada = [];
    let lista_valores = {};
    if(text_commands !== "" && text_commands !== undefined){
      datos = JSON.parse(text_commands.replace('\"','"'));
      datos.forEach(item => {
        lista_ordenada.push(parseInt(item.id.split(":")[3]));
        lista_valores[parseInt(item.id.split(":")[3])] = { value: item.value };
      });
      lista_ordenada.sort();
    }

    let scala = panelData[idtype].scala_List;
    let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN_EDIT,scala,lista_valores,id.toString().split("_")[1]);

    if($$("order_edit_dashboard_" + id.toString().split("_")[1])) {
      $$(id.toString().split("_")[1] + "_form").removeView("order_edit_dashboard_" + id.toString().split("_")[1]);
    }     
    $$(id.toString().split("_")[1] + "_form").addView(
      {
        view:"dashboard",
        id:"order_edit_dashboard_" + id.toString().split("_")[1],
        gridColumns:1,
        //width:tamx,
        gridRows:1,
        cellHeight:300,
        cells:[
          { view:"panel", x:0, y:0, dx:1, dy:1, body: panelMan.getInitComponent()},
        ],
      }
    );
    panelMan.loadComponent();
  }
}

function orderSave(id){
  let idform = $$(id.toString().split("_")[1] + "_form").getValues();
  let value = $$(id.toString().split("_")[1] + "_element").data.value;
  let idtype = "ElementType:" + elementTypeMap.getElementType(value.split("_")[1]);
  let message = $$(id.toString().split("_")[1] + "_commandElementType").config.text;
  let v_commands = [];
  let v_commands_values = [];
  let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
  if (pmvTypes.includes(idtype.split(":")[1]) && message === "SEND MESSAGE"){
    //SI TENEMOS UN SEND MESSAGE
    let graphElemType = `query {
      getElementType(elementTypeId: "` + idtype + `")
      {
        id
        alias
        description
        commandElementTypes{  
          id 
          alias 
          commandElementTypeParams { 
            id 
            alias 
          }
        }
        elementTypeParams{
          id
          alias
          description
          graphics {
            id 
            alias 
            graphicType       
            graphicValues {id value}
          }
        }
      }
    }`;
    
    let graphqlEType = webix.proxy("GraphQL", graphElemType);
    graphqlEType.load().then(data => {
      let send_command = null;
      data.commandElementTypes.forEach(element => {
        if (element.alias === "SEND MESSAGE") {
          try {
            send_command = element.id;
            element.commandElementTypeParams.forEach(item => {
              v_commands.push(item.id);
            });
          } catch (e) {
            console.log(e);
          }
        }
      });
      let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN_EDIT,3,null,id.toString().split("_")[1]);
      let controlPanels = panelMan.controlPanels(data);
      if (controlPanels !== -1) {
        if (v_commands.length > 0) {
          let vcommands = v_commands.sort();
          for (let i=0;i<vcommands.length; i++) {
            v_commands_values.push({
              "commandElementTypeParamId": vcommands[i],
              "value": controlPanels[i]
            });
          }
        }
        upOrderGrap.load({
          plan: {
            "id": id.toString().split("_")[1],
            "alias": idform.alias,
            "description": idform.description,
            "planOrderTypeId": idform.planOrderType.split("_")[1],
            "planOrderValues": null,
            "comandElement": {
              "commandElementTypeId": idform.commandElementType.split("_")[1],
              "elementId": idform.element.split("_")[1],
              "commandElementValues": v_commands_values
            }
          }
        })
        .then(data => {
          if(data) {
            let result = updateOrderTree("treePlan",data,"update");
            let result_list = updateOrderList(data,"update");
            if (result && result_list) {
              $$("bsave_" + data.id).hide();
              $$("becancel_" + data.id).hide();
              $$("bedit_" + data.id).show();
              $$("bremove_" + data.id).show();
            
              $$(data.id + "_element").disable();
              $$(data.id + "_planOrderType").disable();
              $$(data.id + "_commandElementType").disable();
              $$(data.id + "_alias").disable();
              $$(data.id + "_description").disable();

              if($$("order_edit_dashboard_" + id.toString().split("_")[1])) {
                $$(id.toString().split("_")[1] + "_form").removeView("order_edit_dashboard_" + id.toString().split("_")[1]);
              } 

              let scala2 = panelData[idtype].scala_List;
              let lista_valores = {};
              if(data.comandElement.commandElementValues !== "" && data.comandElement.commandElementValues !== undefined){
                data.comandElement.commandElementValues.forEach(item => {
                  lista_valores[parseInt(item.id.split(":")[3])] = { value: item.value };
                });
              }

              let panelMan2 = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN,scala2,lista_valores,id.toString().split("_")[1]);
              $$(id.toString().split("_")[1] + "_form").addView(
                {
                  view:"dashboard",
                  id:"order_edit_dashboard_" + id.toString().split("_")[1],
                  gridColumns:1,
                  //width:tamx,
                  gridRows:1,
                  disable: true,
                  cellHeight:300,
                  cells:[
                    { view:"panel", x:0, y:0, dx:1, dy:1, body: panelMan2.getInitComponent()},
                  ],
                }
              );
              panelMan2.loadComponent();

              webix.message("Guardado Correctamente");
            } else {
              webix.message("ERROR AL GUARDAR");
            }
          } else {
            webix.message("ERROR AL GUARDAR");
          }
        });
      } else {
        webix.message("ERROR AL GUARDAR");
      }
    });
  } else {
    upOrderGrap.load({
      plan: {
        "id": id.toString().split("_")[1],
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
        let result = updateOrderTree("treePlan",data,"update");
        let result_list = updateOrderList(data,"update");

        if (result && result_list) {
          $$("bsave_" + data.id).hide();
          $$("becancel_" + data.id).hide();
          $$("bedit_" + data.id).show();
          $$("bremove_" + data.id).show();
        
          $$(data.id + "_element").disable();
          $$(data.id + "_planOrderType").disable();
          $$(data.id + "_commandElementType").disable();
          $$(data.id + "_alias").disable();
          $$(data.id + "_description").disable();
          //remove dashboard
          //if ($$("order_edit_dashboard_" + data.id))
          //  $$(data.id + "_form").removeView("order_edit_dashboard_" + data.id);
        } else {
          webix.message("ERROR AL GUARDAR");
        }
      } else {
        webix.message("ERROR AL GUARDAR");
      }
    });
  }
  let myEvent = $$(id.toString().split("_")[1] + "_element").detachEvent("onChange");
  let myEvent2 = $$(id.toString().split("_")[1] + "_commandElementType").detachEvent("onChange");
}

function updateOrderList(data,parameter){
  try {
    switch (parameter) {
      case "remove":
        break;
      case "new":
        break;
      case "update":
        $$(data.id.toString() + "_commandElementValues").setValue(data.comandElement.commandElementValues ? JSON.stringify(data.comandElement.commandElementValues) : "");
        break;
    }
    return true;
  } catch (e) {
    console.log(e);
    return false;
  }
}

function updateOrderTree(view,data,parameter){
  try {
    let selectPlan = $$(view).getSelectedId();
    let item = $$(view).getItem(selectPlan);
    let itemPlanOrders = item["planOrders"].filter(function(value, index, arr){ return value.id !== data.id;});
    switch (parameter) {
      case "remove":
        break;
      case "new":
        itemPlanOrders.push(data);
        break;
      case "update":
        itemPlanOrders.push(data);
        break;
    }
    item["planOrders"] = itemPlanOrders;
    $$(view).updateItem(selectPlan, item);
    return true;
  } catch (e) {
    console.log(e);
    return false;
  }
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
      let padre = $$(idform + "_form").getParentView();
      $$(padre).removeView(idform + "_form");
      let result = updateOrderTree("treePlan",data,"remove");
      webix.message("Eliminado Correctamente");
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
          }
        }
      }
    }
  }`);
  graphElems.load({
    rowId: element
  })
  .then(data => {
    data.elementType.commandElementTypes.forEach(e => {
      $$(id.toString().split("_")[0] + "_commandElementType").getList().add({id:id.toString().split("_")[0] +"_" + e.id, value:e.alias});
    });
  });

  if($$("order_edit_dashboard_" + id.toString().split("_")[0])) {
    $$(id.toString().split("_")[0] + "_form").removeView("order_edit_dashboard_" + id.toString().split("_")[0]);
  } 

}

function buildc_new(id){
  let element = id.toString().split("_")[2];
  let idplan  = id.toString().split("_")[3];
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
          }
        }
      }
    }
  }`);
  graphElems.load({
    rowId: element
  })
  .then(data => {
    data.elementType.commandElementTypes.forEach(e => {
      $$("order_new_commandElementType_" + idplan).getList().add({id:"order_new_" + e.id, value:e.alias});
    });
  });
}

function build_equipment_table(id) {
  let idtype = id.split("_")[3];
  let planid = id.split("_")[2];
  
  $$("window_order_new_datatable_" + planid).clearAll();
  $$("window_order_new_commandElementType_" + planid).getList().clearAll();
  $$("window_order_new_commandElementType_" + planid).$setValue("");
  let graphType = webix.proxy("GraphQL",`
  query($rowId: String!) {
    getElementType(elementTypeId: $rowId)
    {
      id
      alias
      description
      commandElementTypes{id alias}
    }  
  }`);
  graphType.load({
    rowId: idtype
  })
  .then(data => {
    data.commandElementTypes.forEach(item => {
      let row = { 
        id: item.id + "_" + planid + "_window_order_new_combo" , 
        value: item.alias
      };
      $$("window_order_new_commandElementType_" + planid).getList().add(row);
    });
  });

  let graphElems = webix.proxy("GraphQL",`
  query($rowId: String!) {
    getElementsByType(elementTypeId: $rowId){
      id
      alias
    }
  }`);
  graphElems.load({
    rowId: idtype
  })
  .then(data => {
    data.forEach(item => {
      let row = { 
        id: item.id + "_window_order_new_table" , 
        title: item.alias, 
      };
      $$("window_order_new_datatable_" + planid).add(row);
    });
  });
  var toolbar = $$("window_order_new_dashboard_" + planid).getChildViews()[0];
  if (toolbar !== null && toolbar !== undefined) {
    $$("window_order_new_dashboard_" + planid).removeView(toolbar.config.id);
  }
  $$("window_order_new_dashboard_" + planid).addView({ view:"panel", x:0, y:0, dx:1, dy:1, body:{ template:"Commands" }});
}

function build_type_table(id) {
  let planid = id.split("_")[1];
  let message = $$("window_order_new_commandElementType_" + planid).config.text;
  if (message !== ""){
    let idcommand = id.split("_")[0];
    let idtype = "ElementType:" + idcommand.split(":")[2];
    var toolbar = $$("window_order_new_dashboard_" + planid).getChildViews()[0];
    if (toolbar !== null && toolbar !== undefined) {
      $$("window_order_new_dashboard_" + planid).removeView(toolbar.config.id);
    }
    let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
    if (pmvTypes.includes(idtype.split(":")[1]) && message === "SEND MESSAGE"){
      let scala = panelData[idtype].scala;
      let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN_EDIT,scala,null);
      $$("window_order_new_dashboard_" + planid).addView({ view:"panel", x:0, y:0, dx:1, dy:1, body: panelMan.getInitComponent()});
      panelMan.loadComponent();
    } else {
      $$("window_order_new_dashboard_" + planid).addView({ view:"panel", x:0, y:0, dx:1, dy:1, body:{
        template:"text" }});
    }
  }
}

function build_type_panel_edit(id){

  let value = $$(id["id"].toString().split("_")[0] + "_element").data.value;
  let idtype = "ElementType:" + elementTypeMap.getElementType(value.split("_")[1]);
  let text_commands = $$(id["id"].toString().split("_")[0] + "_commandElementValues").data.value;
  let datos = null;
  let message = $$(id["id"].toString().split("_")[0] + "_commandElementType").config.text;
  let pmvTypes = [elementTypeMap.ELEMENT_TYPE_10_DMS, elementTypeMap.ELEMENT_TYPE_24_PMV_A_C, elementTypeMap.ELEMENT_TYPE_26_PMV_64X64, elementTypeMap.ELEMENT_TYPE_27_PMV_A_B, elementTypeMap.ELEMENT_TYPE_28_PMV_192X64, elementTypeMap.ELEMENT_TYPE_29_PMV_B_D, elementTypeMap.ELEMENT_TYPE_30_PMV_128X64, elementTypeMap.ELEMENT_TYPE_31_PMV, elementTypeMap.ELEMENT_TYPE_32_PMV, elementTypeMap.ELEMENT_TYPE_35_PMV, elementTypeMap.ELEMENT_TYPE_36_PMV];
  if (pmvTypes.includes(idtype.split(":")[1]) && message === "SEND MESSAGE"){
    let lista_ordenada = [];
    let lista_valores = {};
    if(text_commands !== "" && text_commands !== undefined){
      datos = JSON.parse(text_commands.replace('\"','"'));
      datos.forEach(item => {
        lista_ordenada.push(parseInt(item.id.split(":")[3]));
        lista_valores[parseInt(item.id.split(":")[3])] = { value: item.value };
      });
      lista_ordenada.sort();
    }
    let scala = panelData[idtype].scala_List;

    let panelMan = new PanelManager(null, idtype,elementTypeMap.PANELMANAGER_TYPE_PLAN_EDIT,scala,lista_valores,id["id"].toString().split("_")[0]);

    $$(id["id"].toString().split("_")[0] + "_form").addView(
      {
        view:"dashboard",
        id:"order_edit_dashboard_" + id["id"].toString().split("_")[0],
        gridColumns:1,
        //width:800,
        gridRows:1,
        cellHeight:200,
        cells:[
          { view:"panel", x:0, y:0, dx:1, dy:1, body: panelMan.getInitComponent()},
        ],
      }
    );
    panelMan.loadComponent();
  } else {
    if($$("order_edit_dashboard_" + id["id"].toString().split("_")[0])) {
      $$(id["id"].toString().split("_")[0] + "_form").removeView("order_edit_dashboard_" + id["id"].toString().split("_")[0]);
    }
  }
}


function statusBadge(value) {
  var cssStatusName = planOrderStateType[value].toLowerCase();
  //var html = "<div style='background-color: #ffdedb; color: #ff6455; display: flex; align-items: center; height: 100%; overflow: hidden; height: 17px; line-height: 17px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;'>"  + PLAN_STATE_TYPE[value] + "</div>";
  var html = "<div class=\"webix_um_badge_container\"><span class=\"webix_um_status_badge planschedule_state_" + cssStatusName + "\">" + planOrderStateType[value] + "</span></div>";
  //var html = "<span class=\"webix_um_status_badge planschedule_state_" + cssStatusName + "\">" + PLAN_STATE_TYPE[value] + "</span>";
  //var html = "<div class=\"webix_um_badge_container webix_um_status_badge planschedule_state_" + cssStatusName  + "\">" + PLAN_STATE_TYPE[value] + "</div>";
  return html;
}

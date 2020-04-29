import { plan } from "../data/plan.js";


function orderEdit(id){
  console.log("EDIT");
  console.log(id.toString().split("_"));
  $$(id.toString().split("_")[1] + "_element").enable();
  $$(id.toString().split("_")[1] + "_planOrderType").enable();
  console.log($$(id.toString().split("_")[1] + "_element"));
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
      this.id = data.id;
      this.description = data.description === undefined ? null : data.description;
      this.alias = data.alias;
      this.state = data.state;
      this.planOrderValues = data.planOrderValues;
      this.planOrderType = data.planOrderType;
      this.comandElement = data.comandElement;
    } else {
      this.id = id;
    }

    let self = this;

  }

  dinamicFormOrder(view) {
    console.log("SSSASASASASASASASASASASAS");
    console.log(this.elementsList);
    console.log(this.elementsList[0]);
    console.log(this.elements);
    console.log(this.planOrderType);
    console.log(this.planOrderTypeOption);
    console.log(this.elementsOption);
    console.log("RERERERERERE");
    //this.loadData();
    $$(view)
    .addView({
      view:"form", 
      id:this.id, 
      scroll:false, 
      elements:[{
        rows: [
          { view:"template", type:"header", template: this.id.toString(), height:50, align:"left" },
          {
            cols:[
              { view:"text", id: this.id.toString() + "_alias",   disabled: true, name: "alias",   label:"Alias", value: this.alias,         labelAlign:"left", labelWidth: 125, inputWidth:300 },                        
              { view:"text", id: this.id.toString() + "_orderId", disabled: true, name: "orderId", label:"Id",    value: this.id.toString(), labelAlign:"left", labelWidth: 200, inputWidth:400 },
              { view:"text", id: this.id.toString() + "_state",   disabled: true, name: "state",   label:"State", value: this.state,         labelAlign:"left", labelWidth: 200, inputWidth:400 }
            ]
          },{
            cols:[
              { view:"text",  id: this.id.toString() + "_description",   disabled: true, name: "description",   label:"Description",   value:this.description, labelAlign:"left", labelWidth: 125, inputWidth:300 },
              { view:"combo", id: this.id.toString() + "_planOrderType", disabled: true, name: "planOrderType", label:"planOrderType", value: { id:this.planOrderType.id, value:this.planOrderType.alias },   options:this.planOrderTypeOption, labelAlign:"left", labelWidth: 200, inputWidth:400 },
              { gravity: 1 }
            ]
          },{
            cols:[
              { view:"combo", id: this.id.toString() + "_element",              disabled: true, name: "element", label:"Element", value:{id: this.comandElement.element.id, value: this.comandElement.element.alias }, options: [], labelAlign:"left", labelWidth: 125, inputWidth:300 },
              { view:"text",  id: this.id.toString() + "_commandElementType",   disabled: true, name: "commandElementType",  label:"commandElementType", value:this.comandElement.commandElementType.alias, labelAlign:"left", labelWidth: 200, inputWidth:400 },      
              { view:"text",  id: this.id.toString() + "_commandElementValues", disabled: true, name: "commandElementValues", label:"commandElementValues", value:this.comandElement.commandElementValues, labelAlign:"left", labelWidth: 200, inputWidth:400 },
              //{gravity: 1},
            ]
          },{
            cols:[
              { gravity: 3 },
              { view:"button", id: "bedit_" + this.id, type:"icon", icon:"mdi mdi-grease-pencil", width:50, placeHolder: "Editar",
                click: orderEdit              
              },
              { view:"button", id: "b_save_" + this.id, type:"icon", icon:"mdi mdi-content-save", width:50, hidden: true,
                //click: removeToTree
              },
              { view:"button", id: "b_remove_" + this.id, type:"icon", icon:"mdi mdi-delete", width:50, placeHolder: "Eliminar",
                //click: removeToTree
              }
            ]
          }
        ]
      }]
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
      return $$(this.id.toString() + "_element").getList().add({id:el.id, value:el.alias});
      console.log("FUERA2");
      //this.elementsOption.push({id: element.id, value: element.alias});
    });
    //elementsList2.each(function(obj){ console.log(obj); });

    //$$(this.id + "_element").getList().add({id:item.id, value:item.alias});
    */
  }

  generateCombo(){}

  loadData() {

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
  }

  init() {
    this.loadData();
  }
}
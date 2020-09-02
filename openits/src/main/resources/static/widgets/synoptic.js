import Synoptic from "../classes/Synoptic.js";
import {columnsElementFormList} from "../data/elementForm.js";
import ElementCommand from "../classes/ElementCommand.js";

// GraphQL
//initialize a proxy
var graphSynoptic = webix.proxy(
  "GraphQL",
  `
  query   {
    getElement(elementId: "Element:7:1")
    {
      id
      alias
      description
      state
      childs{
        id
        alias
        state
        description
        elementValues{
          id
          value
        }
      }
    }
  }
  `
);

let syn = new Synoptic(graphSynoptic, null, "synoptic2", "Element:7:1");

export let synoptic = {
  rows: [
    {
      view: "template",
      id: "synoptic2",
      url: function() {
        syn.init("tunnel");
      }
    },
    {
      view: "toolbar", id:"toolbar_synoptic", elements:[
      {
        view: "icon", icon: "mdi mdi-menu",
        click: function(){
          if( $$("menu").config.hidden){
            $$("menu").show();
          }
          else {
            $$("menu").hide();
          }
        }
      }]
    },
  ]
};

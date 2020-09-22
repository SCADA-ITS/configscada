import Synoptic from "../classes/Synoptic.js";
import {columnsElementFormList} from "../data/elementForm.js";
import ElementCommand from "../classes/ElementCommand.js";

let syn = new Synoptic("synopticTunnel", "Element:7:1");

export let synoptic = {
  rows: [
    {
      view: "template",
      id: "synopticTunnel",
      url: function() {
        syn.init("synopticTunnel");
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

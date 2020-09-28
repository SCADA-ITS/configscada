import Map from "../classes/Map.js";
import {columnsElementFormList} from "../data/elementForm.js";
import ElementCommand from "../classes/ElementCommand.js";

// GraphQL
//initialize a proxy
var graphSynoptic = webix.proxy(
  "GraphQL",
  `
  query   {
    getElement(elementId: "Element:7:4")
    {
      id
      alias
      description
      state
      icon
      childs{
        id
        alias
        positionX
        positionY
        state
        elementType {
          id
          alias
        }
        elementValues{
          id
          value
          alerted
          elementTypeParam {
            description
          }
        }
      }
    }
  }
  `
);

let syn = new Map(graphSynoptic, null, "map1", "Element:7:4");

webix.protoUI({
  name: "sidemenu-win",
  defaults: {
    head:false,
  },
  $init: function(){
    this.$ready.push(function() {
      this.attachEvent("onShow", function() {
        var base = this.$view.className.split("animated")[0];
        this.$view.className = base + " animated slideInRight";  
      })
      this.attachEvent("onHide", function() {
        this.$view.style.display = "block";
        this.$view.className += " animated slideOutRight";  
      })
    });
  }
}, webix.ui.window);

export let map = {
  rows: [
    {
      view: "template",
      id: "map1",
      url: function() {
        syn.init("map");
      }
    },
  ]
};

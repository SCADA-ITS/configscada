import Synoptic from "../classes/Synoptic.js";

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

let pan = new Synoptic(graphSynoptic, null, "synoptic_panels");

export let panels = {
  rows: [
    {
      view: "template",
      id: "synoptic_panels",
      url: function() {
        pan.init("panels");
      }
    }
  ]
};

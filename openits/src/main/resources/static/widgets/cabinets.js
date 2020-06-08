import Synoptic from "../classes/Synoptic.js";

// GraphQL
//initialize a proxy
var graphSynoptic = webix.proxy(
  "GraphQL",
  `
  query   {
    getElement(elementId: "Element:7:2")
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

let bra = new Synoptic(graphSynoptic, null, "synoptic_bratislava_cabinets");

export let cabinets = {
  rows: [
    {
      view: "template",
      id: "synoptic_bratislava_cabinets",
      url: function() {
        bra.init("cabinets");
      }
    }
  ]
};

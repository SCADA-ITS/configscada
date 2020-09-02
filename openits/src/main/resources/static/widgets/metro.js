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

let met = new Synoptic(graphSynoptic, null, "synoptic_metro", "Element:7:2");

export let metro = {
  rows: [
    {
      view: "template",
      id: "synoptic_metro",
      url: function() {
        met.init("metro");
      }
    }
  ]
};

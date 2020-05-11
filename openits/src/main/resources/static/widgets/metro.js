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
      state
      childs{
        id    
        state
        elementType {
          id
          alias
        }
        elementValues{
          id
          value
          elementTypeParam {
            description
          }
        }
      }
    }
  }
  `
);

let met = new Synoptic(graphSynoptic, null, "synoptic_metro");

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

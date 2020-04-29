import Synoptic from "../classes/Synoptic.js";

// GraphQL
//initialize a proxy
var graphSynoptic = webix.proxy(
  "GraphQL",
  `
  query   {
    getElement(elementId: "Element:7:3")
    {
      id
      alias
      description
      state
      icon
      childs{
        id    
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

let glob = new Synoptic(graphSynoptic, null, "synoptic_global");

export let global = {
  rows: [
    {
      view: "template",
      id: "synoptic_global",
      url: function() {
        glob.init("global");
      }
    }
  ]
};

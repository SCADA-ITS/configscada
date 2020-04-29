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

let syn = new Synoptic(graphSynoptic, null, "synoptic2");

export let synoptic = {
  rows: [
    {
      view: "template",
      id: "synoptic2",
      url: function() {
        syn.init("tunnel");
      }
    }
  ]
};

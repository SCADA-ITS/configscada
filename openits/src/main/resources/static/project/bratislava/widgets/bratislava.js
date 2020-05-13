import Synoptic from "../../../classes/Synoptic.js";

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

let bra = new Synoptic(graphSynoptic, null, "synoptic_bratislava");

export let bratislava = {
  rows: [
    {
      view: "template",
      id: "synoptic_bratislava",
      url: function() {
        bra.init("bratislava");
      }
    }
  ]
};

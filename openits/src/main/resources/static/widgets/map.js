import Map from "../classes/Map.js";

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

let syn = new Map(graphSynoptic, null, "map1");

export let map = {
  rows: [
    {
      view: "template",
      id: "map1",
      url: function() {
        syn.init("map");
      }
    }
  ]
};

import Map from "../classes/Map.js";
import {columnsElementFormList} from "../data/elementForm.js";
import ElementCommand from "../classes/ElementCommand.js";
import { element_tree } from "./element_tree.js";

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

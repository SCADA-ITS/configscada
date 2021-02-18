import * as elementTypeMap from "../lib/elementTypeMap.js";

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
      // this.attachEvent("onHide", function() {
      //   this.$view.style.display = "block";
      //   this.$view.className += " animated slideOutRight";  
      // })
    });
  }
}, webix.ui.window);


export default class EquipmentTree {
  elementTree = [];

  constructor(id, data) {
    this.id = id;
    this.data = data;
    var graphElements =
      `query   {
          getElement(elementId: "` + this.id + `")
          {
            id
            alias
            description
            elementTypeState { id }
            elementType {
              id
            }
            childs {
              id
              alias
              elementTypeState { id }
              description
              elementType {
                id
              }
            }
          }
        }`;

    this.graphqlElements = webix.proxy("GraphQL", graphElements);
  }

  addToElementTree(element) {
    let thisElementType = elementTypeMap.getElementType(element.id);

    // find the elementType in the tree list
    let elementTypeKey = this.elementTree.find((e) => e.id === thisElementType);
    if (typeof elementTypeKey == "undefined") {
      // adds the elementType
      this.elementTree.push({
        id: thisElementType,
        value: elementTypeMap.getElementTypeName(thisElementType),
        data: [],
      });
    }

    // search for the elementType key
    let elementTypeKeyIndex = this.elementTree.findIndex(
      (e) => e.id === thisElementType
    );

    // set the width and height based on the elementType
    if (typeof this.elementTree[elementTypeKeyIndex]["data"] == "undefined") {
      this.elementTree[elementTypeKeyIndex]["data"] = [];
    }
    this.elementTree[elementTypeKeyIndex]["data"].push({
      id: element.id,
      value: "<div style=\"display: inline;vertical-align: text-top;\">" + element.alias + "</div>"
    });
  }

  checkSelected(id) {

  }

  // got from Github
  elementReady(selector) {
    return new Promise((resolve, reject) => {
      let el = document.querySelector(selector);
      if (el) {
        resolve(el);
      }
      new MutationObserver((mutationRecords, observer) => {
        // Query for elements matching the specified selector
        Array.from(document.querySelectorAll(selector)).forEach((element) => {
          resolve(element);
          //Once we have resolved we don't need the observer anymore.
          observer.disconnect();
        });
      }).observe(document.documentElement, {
        childList: true,
        subtree: true,
      });
    });
  }

  getComponent(componentId="") {
    let self = this;

    // waits for the element and starts drawing
    this.elementReady('[view_id="' + "element_tree" + componentId + '"]').then(
      (c) => {
        let spinnerDivToAdd = document.createElement("div");
        spinnerDivToAdd.setAttribute("id", "element_tree" + componentId + "spinner");
        spinnerDivToAdd.setAttribute("class", "spinner");
        document.querySelector('[view_id="' + "element_tree" + componentId + '"]').prepend(spinnerDivToAdd);
      }
    );

    this.graphqlElements.load().then((data) => {
        // if componentId is not given, 
        // the default tree will be grouped by elementTypes
        if(componentId == "") {
          data.childs.forEach((element) => {
            self.addToElementTree(element);
          });
        }
      }).then(() => {
        $$("element_tree" + componentId).clearAll();
        $$("element_tree" + componentId).parse(self.elementTree);
        // adds the callback for the element selected
        $$("element_tree" + componentId).attachEvent("onItemCheck", function(id) {
          self.checkSelected(id);
        });

        $$("element_tree_filter" + componentId).attachEvent("onTimedKeyPress", function () {
          $$("element_tree" + componentId).filter("#value#", this.getValue());
        });

        // all the elements are checked initially
        $$("element_tree" + componentId).checkAll();

        document.getElementById("element_tree" + componentId + "spinner").style.display = "none";

      });
  }
}

import * as elementTypeMap from "../lib/elementTypeMap.js";
import { getElementSVG, getElementIcon } from "../lib/iconGenerator.js";
import ElementForm from "../classes/ElementForm.js";
import { columnsElementFormList } from "../data/elementForm.js";


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
      this.attachEvent("onHide", function() {
        this.$view.style.display = "block";
        this.$view.className += " animated slideOutRight";  
      })
    });
  }
}, webix.ui.window);


function getIconWidth(elementType, height) {
  let ratio = 1;
  let width = height;

  switch(elementType) {
    case "10":
    case "36":
      ratio = height / 24;
      width = 76 * ratio;
      break;
    case "24":
      ratio = height / 64;
      width = 48 * ratio;
      break;
    case "26":
      ratio = height / 64;
      width = 64 * ratio;
      break;
    case "28":
      ratio = height / 64;
      width = 32 * ratio;
      break;
    case "29":
      ratio = height / 64;
      width = 48 * ratio;
      break;
    case "30":
      ratio = height / 64;
      width = 128 * ratio;
      break;
    case "31":
      ratio = height / 48;
      width = 64 * ratio;
      break;
    case "32":
      ratio = height / 96;
      width = 64 * ratio;
      break;
    case "35":
      ratio = height / 48;
      width = 200 * ratio;
      break;
    default:
      width = 32;
      break;
  }

  return width;
}

export default class EquipmentTree {
  elementTree = [];

  constructor(id, data) {
    this.id = id;
    this.data = data;

    let self = this;

    var graphElements =
      `
        query   {
          getElement(elementId: "` + this.id + `")
          {
            id
            alias
            description
            state
            childs {
              id
              alias
              state
              description
              elementValues {
                id
                value
              }
            }
          }
        }`;

    this.graphqlElements = webix.proxy("GraphQL", graphElements);

    // creates the stomp connection
    this.client = new StompJs.Client({
      brokerURL: webix.storage.session.get("url.ws.stomp"),
      connectHeaders: {
        login: "user",
        passcode: "user",
      },
      //debug: function(str) {
      //console.log(str);
      //},
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.client.onConnect = function (frame) {
      // Do something, all subscribes must be done is this callback
      // This is needed because this will be executed after a (re)connect
      this.suscription = self.client.subscribe(
        "/topic/element.>",
        self.update_element
      );
    };

    this.client.onStompError = function (frame) {
      // Will be invoked in case of error encountered at Broker
      // Bad login/passcode typically will cause an error
      // Complaint brokers will set `message` header with a brief message. Body may contain details.
      // Compliant brokers will terminate the connection after any error
      console.log("Broker reported error: " + frame.headers["message"]);
      console.log("Additional details: " + frame.body);
      alert(`[error] ${error.message}`);
    };

    this.client.activate();
  }

  getIconWidth(elementType, height) {
    let ratio = 1;
    let width = height;

    switch(elementType) {
      case "10":
      case "36":
        ratio = height / 24;
        width = 76 * ratio;
        break;
      case "24":
        ratio = height / 64;
        width = 48 * ratio;
        break;
      case "26":
        ratio = height / 64;
        width = 64 * ratio;
        break;
      case "28":
        ratio = height / 64;
        width = 32 * ratio;
        break;
      case "29":
        ratio = height / 64;
        width = 48 * ratio;
        break;
      case "30":
        ratio = height / 64;
        width = 128 * ratio;
        break;
      case "31":
        ratio = height / 48;
        width = 64 * ratio;
        break;
      case "32":
        ratio = height / 96;
        width = 64 * ratio;
        break;
      case "35":
        ratio = height / 48;
        width = 200 * ratio;
        break;
      default:
        width = 32;
        break;
    }

    return width;
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
    let height = 32;
    let width = this.getIconWidth(thisElementType, height);

    if (typeof this.elementTree[elementTypeKeyIndex]["data"] == "undefined") {
      this.elementTree[elementTypeKeyIndex]["data"] = [];
    }
    this.elementTree[elementTypeKeyIndex]["data"].push({
      id: element.id,
      value: "<div style=\"display: inline;vertical-align: text-top;\">" + element.alias + "</div>",
      icon: "<div style=\"float: right\">" + getElementSVG(element, width, height, ":Tree") + "</div>",
    });

    // add the icon callback
    /*
    let treeElement = document.getElementById(element.id + ":Tree");
    if(treeElement != null) {
      treeElement.setAttribute("onclick", alert("CLICK!"));
      treeElement.style["z-index"] = 1000;
      treeElement.addEventListener('click', function(evt) { 
        alert("HELLO");

        let id =  evt.target.parentElement.id;
  
        // open the detail window
        if($$("win" + id) != null) {
          $$("win" + id).destructor();
        }

        
  
        let elementForm = new ElementForm(id.toString(), columnsElementFormList);
        webix
          .ui({
            id: "win" + id,
            view: "window",
            move: true,
            close: true,
            position: "right",
            body: elementForm.getComponent(),
            on: {
              onDestruct: function (obj) {
                elementForm.disconnect();
              },
              onHide: function (obj) {
                elementForm.disconnect();
              },
            },
          })
          .show();
        });
    }
    */
  }

  checkSelected(id) {
    /*
    // check if there are objects selected
    let shouldEnableButton = false;

    if($$("element_tree").getChecked().length > 0) {
      //let first_item = elementTypeMap.getElementType($$("element_tree").getChecked()[0]);
      let first_item = elementTypeMap.getElementType($$("element_tree").getChecked().find(e => e.match(/Element/g)));
      
      if(typeof first_item != 'undefined') {
        for(let i=0; i<$$("element_tree").getChecked().length; i++) {
          let checkedElement = $$("element_tree").getChecked()[i];
          let checkedElementLevel = $$("element_tree").getItem(checkedElement).$level;
  
          if(checkedElementLevel != 1) {
            if(elementTypeMap.getElementType(checkedElement) == first_item) {
              shouldEnableButton = true
            } else {
              shouldEnableButton = false;
              break;
            }
          }
  
        }

      }
    }

    if(shouldEnableButton == true) {
      $$("element_tree_button").enable();
    } else {
      $$("element_tree_button").disable();
    }
    */

  }

  update_element(message) {
    try {
			let obj = JSON.parse(JSON.parse(message.body));
			obj.basicElementsVO.forEach((element) => {
				try {
          if (
            elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_10_DMS
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_24_PMV_A_C
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_26_PMV_64X64
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_27_PMV_A_B
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_28_PMV_192X64
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_29_PMV_B_D
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_30_PMV_128X64
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_31_PMV
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_32_PMV
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_35_PMV
            || elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_36_PMV
            ) {
              let elementQuery = webix.proxy(
                "GraphQL",`
                query ($elem: String!) {
                  getElement(elementId: $elem)
                  {
                    id
                    state
                    elementType {
                      id
                      alias
                    }
                    elementValues
                    {
                      id
                      value
                      alerted
                      elementTypeParam {
                        description
                      }
                    }
                  }
                }
                `
              );

              // loads the data
              elementQuery
                .load({
                  elem: element.id,
                })
                .then((data) => {
                  // process the received data
                  let element = data;
                  let thisElementType = elementTypeMap.getElementType(element.id);

                  getElementIcon(data, ":Tree");

                  // set the width and height based on the elementType
                  let height = 32;
                  let width = getIconWidth(thisElementType, height);

                  if(typeof $$("element_tree").getItem(element.id) != 'undefined') {
                    $$("element_tree").getItem(element.id).icon = "<div style=\"float: right\">" + getElementSVG(element, width, height, ":Tree") + "</div>"; //getElementSVG(element, 32, 32, ":Tree");
                  }
                });
            } else {
              let thisElementType = elementTypeMap.getElementType(element.id);

              // set the width and height based on the elementType
              let height = 32;
              let width = getIconWidth(thisElementType, height);

              getElementIcon(element, ":Tree");
              // check if the tree exists
              if(typeof $$("element_tree").getItem(element.id) != 'undefined') {
                $$("element_tree").getItem(element.id).icon = "<div style=\"float: right\">" + getElementSVG(element, width, height, ":Tree") + "</div>"; //getElementSVG(element, 32, 32, ":Tree");
              }
            }
        } catch (err) {
          console.log("Error: " + err.message);
        }
      });
    } catch (err) {
      console.log("Error: " + err.message);
    }
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
        document
          .querySelector('[view_id="' + "element_tree" + componentId + '"]')
          .prepend(spinnerDivToAdd);
      }
    );

    this.graphqlElements
      .load()
      .then((data) => {
        // if componentId is not given, 
        // the default tree will be grouped by elementTypes
        if(componentId == "") {
          data.childs.forEach((element) => {
            self.addToElementTree(element);
          });
        }
      })
      .then(() => {
        $$("element_tree" + componentId).clearAll();
        $$("element_tree" + componentId).parse(self.elementTree);
        // adds the callback for the element selected
        $$("element_tree" + componentId).attachEvent("onItemCheck", function(id) {
          self.checkSelected(id);
        });

        $$("element_tree_filter" + componentId).attachEvent("onTimedKeyPress", function () {
          $$("element_tree" + componentId).filter("#value#", this.getValue());
        });

        /*
        $$("element_tree").attachEvent("onSelectChange", function(id) {
          // open the detail window
          if($$("win" + id) != null) {
            $$("win" + id).destructor();
          }
          let elementForm = new ElementForm(id.toString(), columnsElementFormList);
          webix
            .ui({
              id: "win" + id,
              view: "window",
              move: true,
              close: true,
              position: "right",
              body: elementForm.getComponent(),
              on: {
                onDestruct: function (obj) {
                  elementForm.disconnect();
                },
                onHide: function (obj) {
                  elementForm.disconnect();
                },
              },
            })
            .show();
        });
        */

        // all the elements are checked initially
        $$("element_tree" + componentId).checkAll();

        document.getElementById("element_tree" + componentId + "spinner").style.display = "none";

      });
  }
}

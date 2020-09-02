import * as elementTypeMap from "../lib/elementTypeMap.js";
import { getElementSVG, getElementIcon } from "../lib/iconGenerator.js";
import ElementForm from "../classes/ElementForm.js";
import { columnsElementFormList } from "../data/elementForm.js";

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
    if (typeof this.elementTree[elementTypeKeyIndex]["data"] == "undefined") {
      this.elementTree[elementTypeKeyIndex]["data"] = [];
    }
    this.elementTree[elementTypeKeyIndex]["data"].push({
      id: element.id,
      value: element.alias,
      icon: getElementSVG(element, 32, 32, ":Tree"),
    });
  }

  checkSelected(id) {
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

                  getElementIcon(data, ":Tree");
                });
            } else {
              getElementIcon(element, ":Tree");
            }
        } catch {
          console.log("Error: " + err.message);
        }
      });
    } catch {
      console.log("Error: " + err.message);
    }
  }

  getComponent() {
    let self = this;

    this.graphqlElements
      .load()
      .then((data) => {
        data.childs.forEach((element) => {
          self.addToElementTree(element);
        });
      })
      .then(() => {
        $$("element_tree").clearAll();
        $$("element_tree").parse(self.elementTree);
        // adds the callback for the element selected
        $$("element_tree").attachEvent("onItemCheck", function(id) {
          self.checkSelected(id);
        });

        $$("element_tree_filter").attachEvent("onTimedKeyPress", function () {
          $$("element_tree").filter("#value#", this.getValue());
        });

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
      });
  }
}

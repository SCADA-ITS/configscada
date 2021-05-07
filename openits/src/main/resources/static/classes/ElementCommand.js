import * as elementTypeMap from "../lib/elementTypeMap.js";
import { columnsElementFormList as colMetaData } from "../data/elementForm.js";
import PanelManager from "../classes/PanelManager.js";
import { util } from "../lib/util.js"


var idtable = null;

export default class ElementCommand {
  constructor(id, columnsElementFormList) {
    this.id = id[0];
    if (columnsElementFormList) {
      this.columnsElementFormList = columnsElementFormList;
    } else {
      this.columnsElementFormList = colMetaData;
    }
    //var idtable = this.id;
    idtable = id;
    let self = this;

    var graphElem =
      `query { 
			getElement(elementId: "` +this.id +`"){
				id
				alias
				description
				state
				elementType {
					id
					alias
					commandElementTypes{
						id
            alias
            labelAlias
						commandElementTypeParams{
						  id
						  alias
						}
					}
				}
			}
		}`;

    this.graphql = webix.proxy("GraphQL", graphElem);
    this.header = {
      id: "elementHeader" + this.id,
      cols: [
        {
          id: "elementHeaderIcon" + this.id,
          view: "template",
          type: "header",
          data: {
            value: "",
          },
          template: function (obj) {
            return obj.value;
          },
          css: { "text-align": "center !important" },
        },
        {
          id: "elementHeaderTitle" + this.id,
          view: "template",
          type: "header",
          data: {
            value: "",
          },
          width: 400,
          template: function (obj) {
            return obj.value;
          },
          css: { "text-align": "center !important" },
        },
      ],
    };

    this.body = {
      id: "elementBody" + this.id,
      view: "datatable",
      editable: true,
      width: this.columnsElementFormList[
        "ElementType:" + elementTypeMap.getElementType(this.id)
      ]["width"],
      scroll: this.columnsElementFormList[
        "ElementType:" + elementTypeMap.getElementType(this.id)
      ]["scroll"],
      yCount: this.columnsElementFormList[
        "ElementType:" + elementTypeMap.getElementType(this.id)
      ]["rowCounts"],
      margin: 0,
      header: false,
      autoConfig: true,
      tooltip: true,
      columns: [
        {
          id: "iconAlerted",
          width: 40,
          template: function (obj) {
            if (obj.iconAlerted === true) {
              return "<span style='color:red;' class='webix_icon mdi mdi-alert-circle'></span>";
            } else {
              return "";
            }
          },
        },
        {
          id: "title",
          width: 300,
          tooltip: function (obj) {
            return $.i18n(
              elementTypeMap.getElementParamTypeIdi18(obj.tooltiplab) +
                ".description"
            );
          },
        },
        {
          id: "measure",
          width: 500,
          tooltip: function (obj) {
            return $.i18n(
              elementTypeMap.getElementParamTypeIdi18(obj.tooltiplab) +
                ".description"
            );
          },
        },
      ],
      data: [],
    };

    this.streamtimeout = null;
  }

  getDate(timestamp) {
    return timestamp !== null ? new Date(timestamp).toISOString() : "-";
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

  getComponent() {
    // waits for the element and starts drawing
    this.elementReady('[view_id="' + "elementBody" + this.id + '"]').then(
      (c) => {
        let spinnerDivToAdd = document.createElement("div");
        spinnerDivToAdd.setAttribute("id", "elementBody" + this.id + "spinner");
        spinnerDivToAdd.setAttribute("class", "spinner");
        document
          .querySelector('[view_id="' + "elementBody" + this.id + '"]')
          .prepend(spinnerDivToAdd);
      }
    );

    this.graphql.load().then((data) => {
      // HEADER
      try {
        let headerTitle =
          $.i18n(data.elementType.id + ".alias") + " " + data.alias;
        $$("elementHeaderTitle" + this.id).setValues({ value: headerTitle });
      } catch (e) {
        console.error("Error. id: " + this.id);
        console.error(e);
      }

      //COMMANDS
      try {
        if (data.elementType.commandElementTypes) {
          this.commands = $$("component" + this.id).addView(
            {
              view: "form",
              id: "comandos" + this.id,
              elements: [],
            },
            -1
          );

          for (
            let index = 0;
            index < data.elementType.commandElementTypes.length;
            index++
          ) {
            if (
              data.elementType.commandElementTypes[index].id ===
                "CommandElementType:" + elementTypeMap.COMMAND_TYPE_PMV_A_C ||
              data.elementType.commandElementTypes[index].id ===
                "CommandElementType:" +
                  elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_64X64 ||
              data.elementType.commandElementTypes[index].id ===
                "CommandElementType:" + elementTypeMap.COMMAND_TYPE_PMV_A_B ||
              data.elementType.commandElementTypes[index].id ===
                "CommandElementType:" +
                  elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_192X64 ||
              data.elementType.commandElementTypes[index].id ===
                "CommandElementType:" + elementTypeMap.COMMAND_TYPE_PMV_B_D ||
              data.elementType.commandElementTypes[index].id ===
                "CommandElementType:" +
                  elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_128X64 
                || data.elementType.commandElementTypes[index].id === "CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_TEMP 
                || data.elementType.commandElementTypes[index].id === "CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_TEMP_A 
                || data.elementType.commandElementTypes[index].id === "CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_DGT
                || data.elementType.commandElementTypes[index].id === "CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_DGT_TXT
                || data.elementType.commandElementTypes[index].id === "CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_DGT_GR_TXT
                || data.elementType.commandElementTypes[index].id === "CommandElementType:" + elementTypeMap.COMMAND_TYPE_PMV_WEIGHT_LIMIT
            ) {
              $$(this.commands).addView(
                {
                  view: "button",
                  id: "command" + index + "_" + this.id,
                  value: $.i18n(data.elementType.commandElementTypes[index].id),
                  click: function () {
                    if ($$("panelManager" + data.id)) {
                      $$("panelManager" + data.id).show();
                    } else {
                      //let panelMan = new PanelManager(data.id, data.elementType.id,elementTypeMap.PANELMANAGER_TYPE_MULTI_PANEL,null);//data.elementType.commandElementTypes[index]);
                      let panelMan = new PanelManager(idtable, data.elementType.id,elementTypeMap.PANELMANAGER_TYPE_MULTI_PANEL,null,null);
                      webix
                        .ui({
                          id: "panelManager" + data.id,
                          view: "window",
                          move: false,
                          position: "center",
                          modal: true,
                          close: true,
                          body: panelMan.getInitComponent(),
                          head: $.i18n("elementForm.senialization.panel.head"),
                          on: {
                            onShow: function (obj) {
                              panelMan.loadComponent();
                            },
                            onHide: function (obj) {
                              $$("panelManager" + data.id).destructor();
                            },
                          },
                        })
                        .show();
                    }
                  },
                },
                -1
              );
            } else {
              $$(this.commands).addView(
                {
                  view: "button",
                  id: "command" + index + "_" + this.id,
                  value: $.i18n(data.elementType.commandElementTypes[index].id),
                  click: function () {
                    for (let i = 0; i < idtable.length; i++) {
                      var sendCommand = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
											sendCommandElement(commands: $commands)  
										}`;
                      var graphql = webix.proxy("GraphQL", sendCommand);
                      graphql.save({
                          commands: [
                            {
                              commandElementTypeId: data.elementType.commandElementTypes[index].id,
                              elementId: idtable[i],
                            },
                          ],
                        })
                        .then(() => {
                          webix.message({
                            type: "success",
                            text: $.i18n("lbl_command_sended"),
                          });
                          //Audit
                          let auditLog={
                            "logTypeId": "LogType:3",
                            "logSubtypeId": "LogSubtype:1",
                            "auditLogValues": [
                              {
                                "value": data.elementType.commandElementTypes[index].labelAlias,
                                "logTypeParamId": "LogTypeParam:3:1"
                              },{
                                "value": idtable[i],
                                "logTypeParamId": "LogTypeParam:3:2"
                              },{
                                "value": "",
                                "logTypeParamId": "LogTypeParam:3:3"
                              }
                            ]
                          };
                          auditMutation(auditLog, util.user.auth());
                        });
                    }
                  },
                },
                -1
              );
            }
          }
        }
      } catch (e) {
        console.error("Error. id: " + this.id);
        console.error(e);
      }

      //document.getElementById("elementBody" + this.id + "spinner").style.display = "none";
      return data;
    });

    let result = {
      id: "component" + this.id,
      rows: [
        this.header,
        //this.body,
        {},
      ],
    };

    return result;
  }
}

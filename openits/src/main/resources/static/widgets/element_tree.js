import {columnsElementFormList} from "../data/elementForm.js";
import EquipmentTree from "../classes/EquipmentTree.js";
import ElementCommand from "../classes/ElementCommand.js";

/*
$$("element_tree_filter").attachEvent("onTimedKeyPress",function(){
  $$("element_tree").filter("#value#",this.getValue());
});
*/

function element_tree_generator(id) {
  /*
  let et = new EquipmentTree(id, null);

  et.getComponent();
  */
}

var element_tree_view = {
  id: "elementTreeView", 
  rows: [
    {
      id: "element_tree_filter",
      view: "text",
      label: "Filter",
      labelPosition: "left"
    },
    {
      id: "element_tree",
      view: "tree",
      borderless: true,
      scroll: true,
      select: true,
      multiselect: true,
      threeState: true,
      template: function(obj, common){
        if (obj.$level > 1) {
          return common.icon(obj,common) + common.checkbox(obj, common) + common.folder(obj,common) + "<span class='elementTreeLeaf'>" + obj.value + "</span>" + obj.icon;
        } else {
          return common.icon(obj,common) + common.checkbox(obj, common) + common.folder(obj,common) + "<span class='elementTreeLeaf'>" + obj.value + "</span>";
        }
      }
    },
    {
      id: "element_tree_button",
      view: "button",
      disabled: true,
      value: "Command",
      on: {
        "onItemClick": function(id, e) {
          let elementCommand = new ElementCommand($$("element_tree").getChecked(), columnsElementFormList);
          webix
            .ui({
              id: "win_command" + $$("element_tree").getChecked()[0],
              view: "window",
              move: true,
              close: true,
              position: "right",
              body: elementCommand.getComponent(),
              on: {
                onDestruct: function (obj) {
                  
                },
                onHide: function (obj) {
                  $$("win_command" + $$("element_tree").getChecked()[0]).destructor();
                },
              },
            })
            .show();
        }
      }
    }
  ],
  url: element_tree_generator("Element:7:1")
}

export let element_tree = {
  rows: [element_tree_view]
}

/*
export let element_tree = {
  rows: []
}
*/
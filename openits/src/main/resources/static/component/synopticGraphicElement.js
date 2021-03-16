import GraphicElement from "./graphicElement.js";
import { util } from "../lib/util.js"

export default class SynopticGraphicElement extends GraphicElement {

  constructor(element, synopticLayerElement, parentSynoptic) {
    super(element, synopticLayerElement.graphicIcon);

    this.parentSynoptic = parentSynoptic;
    this.isCtrlClicked = false;
  }

  addOnClickEvent(el){
    el.click((event) => {
      if (event.ctrlKey) {
        if(this.isCtrlClicked){
          this.isCtrlClicked = false;
        } else {
          this.isCtrlClicked = true;
        }
      } else {
        this.isCtrlClicked = false;
      }
    });
  }

  addOnDblClickEvent(el){
    if(el && this.parentSynoptic.metadata.synopticCommands){
      el.dblclick((event) => {
        let defaultCommand = this.parentSynoptic.metadata.synopticCommands.find(gridCommand => {
          return gridCommand.default_ === true
        });
        let module = findModuleByIdAction(defaultCommand.moduleAction.id);
        let args = {};
        args.parentId = this.element.id;
        args.parentType = this.element.elementType.id;
        util.module.open(module, defaultCommand.moduleAction, (defaultCommand.viewType)?defaultCommand.viewType.id:undefined, null, args);
      });
    }
    
  }

}

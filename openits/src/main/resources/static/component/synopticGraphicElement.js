import GraphicElement from "./graphicElement.js";
import { util } from "../lib/util.js"

export default class SynopticGraphicElement extends GraphicElement {

  constructor(element, synopticLayerElement, parentSynoptic, parentContext) {
    super(element, synopticLayerElement.graphicIcon);

    this.parentSynoptic = parentSynoptic;
    this.parentContext = parentContext;
    this.isCtrlClicked = false;
  }

  addEvents(el){

    super.addEvents(el);
    
    el.attr({ cursor: "pointer" });
  }

  addOnClickEvent(el){
    el.click((event) => {
      if (event.ctrlKey) {
        if(this.isCtrlClicked){
          this.isCtrlClicked = false;
          document.getElementById(this.element.id).classList.remove("synoptic-highlight");
        } else {
          this.isCtrlClicked = true;
          document.getElementById(this.element.id).classList.add("synoptic-highlight");
        }
      } else {
        this.parentContext.resetSelectedIds();
        this.isCtrlClicked = true;
        document.getElementById(this.element.id).classList.add("synoptic-highlight");
      }

    });
  }

  addOnDblClickEvent(el){
    if(el && this.parentSynoptic.metadata.synopticCommands){
      el.dblclick((event) => {

        let command ={};      
        let defaultCommand = this.parentSynoptic.metadata.synopticCommands.find(synopticCommand => {
          return synopticCommand.default_ === true
        });
        command.moduleAction = defaultCommand.moduleAction;
        command.viewType = defaultCommand.viewType;
        
        if(defaultCommand.synopticElementTypeCommand != undefined && defaultCommand.synopticElementTypeCommand.length > 0){
          defaultCommand.synopticElementTypeCommand.forEach(typeCommand => {
            if(typeCommand.elementType == this.element.elementType.id ){
              command.moduleAction = typeCommand.moduleAction
            }
          });
        }
        
        let module = findModuleByIdAction(command.moduleAction.id);
        let args = {};
        args.selectedPks = [this.element.id];
        util.module.open(module, command.moduleAction, (command.viewType)?command.viewType.id:undefined, null, args);
      });
    }
    
  }

}

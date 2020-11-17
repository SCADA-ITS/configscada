import Synoptic from "../classes/Synoptic.js";

export default class TabSynoptic {
    constructor(parentId,idSynoptic,synoptic,width,height) {
        this.parentId = parentId.replaceAll(":","_");
        this.idSynoptic = idSynoptic;
        this.synoptic = synoptic; 
        this.width = width !== null ? width : 0;
        this.height = height !== null ? height : 0;
    }

    getComponent(){
        if(this.synoptic !== undefined){
            this.synoptic.id = this.idSynoptic + this.parentId;
            this.syn = new Synoptic(this.synoptic);
            let result = {
                id: "tabtabSyn" + this.parentId,
                width: this.width,
                height: this.height,
                rows: [{
                    view: "template",
                    id: this.idSynoptic + this.parentId,
                }]
            };
            console.log(result);
            return result;
        }
    }

    loadData(){
        this.syn.init();
    }
}



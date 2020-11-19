import Synoptic from "../classes/Synoptic.js";
import {synopticData} from "../data/synoptics.js";

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
            let vSinoptico = {};
            vSinoptico["id"] = this.idSynoptic + this.parentId;
            Object.entries(this.synoptic).map(([k, v]) => {
                if(k!== "id")
                    vSinoptico[k] = v;
            });
            this.syn = new Synoptic(vSinoptico);
            let result = {
                id: "tabtabSyn" + this.parentId,
                //width: this.width,
                //height: this.height,
                rows: [{
                    view: "template",
                    id: this.idSynoptic + this.parentId,
                }]
            };
            return result;
        }
    }

    loadData(){
        this.syn.init();
    }
}



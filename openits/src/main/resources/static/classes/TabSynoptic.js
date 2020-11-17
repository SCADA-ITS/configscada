import Synoptic from "../classes/Synoptic.js";
import {synopticData} from "../data/synoptics.js";

export default class TabSynoptic {
    constructor(parentId,idSynoptic) {
        this.parentId = parentId.replaceAll(":","_");
        this.idSynoptic = idSynoptic;
    }

    getComponent(){
        //console.log(this.idSynoptic);
        //this.synoptic = synopticData.find(x => x.id === idSynop);
        synopticData.forEach(element => {
            if(element.id === this.idSynoptic){
                this.synoptic = element;
                console.log("OK");
            }
        });
        console.log(this.parentId);
        console.log(this.idSynoptic);
        console.log(this.synoptic);
        if(this.synoptic !== undefined){
            this.synoptic.id = this.idSynoptic + this.parentId;
            this.syn = new Synoptic(this.synoptic);
            let result = {
                id: "tabtabSyn" + this.parentId,
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



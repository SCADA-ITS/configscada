import TabVideo from "../classes/TabVideo.js";

export default class TabVideoList {
    constructor(parentId,parentType,videoList,urlParamVideo) {
        this.parentId = parentId;
        this.parentType = parentType;
        this.videoList = videoList;
        this.urlParamVideo = urlParamVideo;
        this.graphQuery = webix.proxy(
            "GraphQL",
            `query ($elem: String!) {
                getElementsByType(elementTypeId: $elem)
                {
                    id
                    alias
                }
            }`
        );
    }

    getComponent(){
        var parentId = this.parentId;
        if(this.videoList){
            this.graphQuery.load({ elem: this.parentType }).then(data => {
                let vdata = [];
                data.forEach(element => {
                    try {
                        vdata.push({
                            id: "videoList_" + parentId + "_" + element.id + "_" + this.urlParamVideo,
                            alias: element.alias,
                        });
                    } catch (e) { console.log(e); }
                });
                $$("cuerpo" + this.parentId).addView({
                    id: "videoList" + this.parentId,
                    view: "list", 
                    width: 150,
                    data:vdata,
                    select: true,
                    template: "#alias#",
                    click: function(obj) {
                        if($$("vvtabVideo"+parentId) !== undefined) {
                            $$("cuerpo" + parentId).removeView($$("vvtabVideo"+parentId));
                        }
                        else {
                            $$($$("cuerpo" + parentId).getChildViews()[0].config.id).data.order.forEach(element => {
                                if($$("vvtabVideo"+element) !== undefined) {
                                    $$("cuerpo" + parentId).removeView($$("vvtabVideo"+element));
                                }
                            });
                        }
                        let eTabVideo = new TabVideo(parentId,obj.split("_")[3]);
                        $$("cuerpo" + parentId).addView(eTabVideo.getComponent(),1);
                        eTabVideo.loadData();
                    }
                },0);
            });
        }
    }
}
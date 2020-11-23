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
                        $$($$("cuerpo" + parentId).getChildViews()[0].config.id).data.order.forEach(element => {
                            if($$("vvtabVideo_"+parentId+"_"+element.split("_")[2]) !== undefined) {
                                $$("cuerpo" + parentId).removeView($$("vvtabVideo_"+parentId+"_"+element.split("_")[2]));
                            }
                        });
                        let eTabVideo = new TabVideo(obj.split("_")[2],obj.split("_")[1],obj.split("_")[3]);
                        $$("cuerpo" + parentId).addView(eTabVideo.getComponent(),1);
                        eTabVideo.loadData();
                    }
                },0);
                $$("videoList" + this.parentId).select("videoList_" + parentId + "_" + parentId + "_" + this.urlParamVideo);
            });
        }
    }
}
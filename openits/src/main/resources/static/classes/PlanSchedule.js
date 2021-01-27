import Table from "../classes/Table.js";
import PlanScheduleForm from "../classes/PlanScheduleForm.js";
import * as revenga from "../lib/revenga.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";

const LBL_PLAN_SCHEDULE_STATE = {
    0: 'UNKNOWN',
    1: 'PAUSE',
    2: 'RUNNING',
    3: 'FAILED'
};
const PLAN_STATE_TYPE = {
    0: "UNKNOWN",
    1: "STOPPED",
    2: "RUNNING",
    3: "SUCCESS",
    4: "FAILED"
};

export default class PlanSchedule {
    constructor() {

        // for using the object inside callbacks
        this.id = webix.uid();


        let edit_planSchedule =() =>{
            let selectPlan = $$(this.id+"Table").getSelectedId();
            let item = $$(this.id+"Table").getItem(selectPlan);
            loadDetailForm(item.id, 1)
        }

        let detail_planSchedule=()=>{
            let selectPlan = $$(this.id+"Table").getSelectedId();
            let item = $$(this.id+"Table").getItem(selectPlan);
            loadDetailForm(item.id, 0); 
        }
        
        let unLoadDetailForm=()=>{
            $$(this.id+"Table").unselect();
            $$("btn_delete"+this.id).hide();
            if($$(this.id+"DetailForm")){
                $$(this.id).removeView(this.id+"Resizer");
                $$(this.id).removeView(this.id+"DetailForm");
            }
        }
        
        let delete_planSchedule=(id)=>{
            let selectPlan = $$(this.id+"Table").getSelectedId();
            let item = $$(this.id+"Table").getItem(selectPlan);
            try {
                $$("planSchedule_win").destructor();
            } catch  {}
        
            let planScheduleForm = new PlanScheduleForm(item.id,null,3, this.id);
            webix
              .ui({ 
                id: "planSchedule_win",
                view: "window",
                width: 400,
                move: true,
                close: true,
                position: "right",
                body: planScheduleForm.getComponent(),
                on: {
                    onHide: function(obj){
                        planScheduleForm.disconnect();
                        $$("planSchedule_win").destructor();
                        unLoadDetailForm();
                    }
                }
              })
              .show();	
        }
        
        let loadDetailForm=(id, num)=>{
            let auxPlanSchedule = new PlanScheduleForm(id,null,num, this.id);
            if(!$$(this.id+"DetailForm")){
                $$(this.id).addView({ id:this.id+"Resizer", view:"resizer" });
                $$(this.id).addView(auxPlanSchedule.getComponent());
            }else{
                $$(this.id).removeView(this.id+"Resizer");
                $$(this.id).removeView(this.id+"DetailForm");
                $$(this.id).addView({ id:this.id+"Resizer", view:"resizer" });
                $$(this.id).addView(auxPlanSchedule.getComponent());
            }
        }

        let addToTable=()=>{
            unLoadDetailForm();
            loadDetailForm(null, 2); 
        }
            
        let onKeyPressTable=(code, e)=>{
            if(code==27){
                unLoadDetailForm();
            }
        }

        // creates the stomp connection
        this.client = new StompJs.Client({
	        brokerURL: webix.storage.session.get("url.ws.stomp"),
	        connectHeaders: {
	            login: "user",
	            passcode: "user"
	        },
	        debug: function(str) {
	        },
	        reconnectDelay: 5000,
	        heartbeatIncoming: 4000,
	        heartbeatOutgoing: 4000
        });

        let processStompMessageWithFallback =(message) => {
            try {
                let obj = JSON.parse(JSON.parse(message.body));
                obj.basicElementsVO.forEach(element => {
                    try {
                        let item = $$(this.id+"Table").getItem(element.id);
                        if(item != null) {
                            let planScheduleStateType = element.planScheduleStateType !== undefined ? element.planScheduleStateType : null;
                            if(planScheduleStateType !== null){
                                item['planScheduleStateType'] = {id: planScheduleStateType.id, alias: LBL_PLAN_SCHEDULE_STATE[planScheduleStateType.id.split(":")[1]]};
                                $$(this.id+"Table").updateItem(element.id, item); 
                            } else {
                                let v_alias = element.alias !== undefined ? element.alias : null;
                                if (v_alias !== null){
                                    if(element.plan.id !== item.plan.id) {
                                        let graphElem = `query { getPlan(planId: "` + element.plan.id + `") {
                                            id
                                            alias
                                            planStateType{
                                                id
                                                alias
                                            }
                                        }}`;
                                        let graphql = webix.proxy("GraphQL", graphElem);
                                        graphql.load().then(data => {
                                            if (v_alias !== item.alias)
                                                item['alias'] = v_alias;
        
                                            if(element.description !== item.description)
                                                item['description'] = element.description;
        
                                            if(element.cronExpression !== item.cronExpression)
                                                item['cronExpression'] = element.cronExpression;
                                            
                                            item['plan'] = {id: element.plan.id, alias: data.alias, planStateType: data.planStateType};
                                            item['planState'] = {id: data.planStateType.id, alias: data.planStateType.alias};
                                            $$(this.id+"Table").updateItem(element.id, item); 
                                        });
                                    }else{
                                        if (v_alias !== null && v_alias !== item.alias)
                                        item['alias'] = v_alias;
    
                                        if(element.description !== item.description)
                                            item['description'] = element.description;
    
                                        if(element.cronExpression !== item.cronExpression)
                                            item['cronExpression'] = element.cronExpression;
    
                                        $$(this.id+"Table").updateItem(element.id, item);
                                    }
                                }
                            }
                        }
                    } catch (err) { console.log("Error: " + err.message); }
                });
            }catch (err) {
                console.error(err);
            }
        }
        this.client.onConnect =(frame) => {
	        this.suscription = this.client.subscribe("/topic/planSchedules.>",processStompMessageWithFallback);
        };
        this.client.onStompError = (frame) => {
	        console.log("Broker reported error: " + frame.headers["message"]);
	        console.log("Additional details: " + frame.body);
        };
        
        this.graph = `query {
            getPlanSchedules{
              id
              plan{id alias description planStateType{id alias description}}
              cronExpression
              alias
              description
              planScheduleStateType{id alias}
            }
        }`;

        this.graphql = webix.proxy("GraphQL", this.graph);
        this.genericTablei18n = {
            id:this.id+"Table",
            "view":"datatable",
            "autoConfig":true,
            "select":true,
            resizeColumn:true,
            "columns":[
                { "id":"id", "header": $.i18n("planSchedule.table.id"), hidden: true},
                { "id":"alias", "header": [$.i18n("planSchedule.table.alias"),  {content:"textFilter"}], sort:"text", fillspace:1 },
                { "id":"description", "header": [$.i18n("planSchedule.table.description"),  {content:"textFilter"}],  sort:"text", fillspace:2, hidden: true },
                { "id":"cronDescription", "header": [$.i18n("planSchedule.table.cronDescription"), {content:"textFilter"}],   sort:"text", fillspace:2, "template": function (obj) {
                        return prettyCron.toString(obj.cronExpression, true);
                    } 
                },
                { 
                    "id":"cronNextDate", 
                    "header": [
                        $.i18n("planSchedule.table.cronNextDate"), 
                        {
                            content:"dateRangeFilter",
                            inputConfig:{format:"%d/%m/%Y %H:%i:%s"}, 
                            compare:function(item, value, data){  
                                let fecha = moment(prettyCron.getNextDate(data.cronExpression, true));
                                if (fecha >= value.start && fecha <= value.end)
                                    return true;
                                return false;
                            }
                        }
                    ],   
                    sort:"text", 
                    fillspace:2, 
                    "template": function (obj) {return moment(prettyCron.getNextDate(obj.cronExpression, true)).format("DD/MM/YYYY HH:mm:ss");} 
                },
                {
                    id: "planScheduleStateType",
                    "header": [
                        $.i18n("planSchedule.table.state"), 
                        {
                            content:"richSelectFilter", 
                            options: [],
                            compare:function(item, value, data){  
                                if (item.alias === value.split("$_$")[1])
                                    return true;
                                return false;
                            }
                        }
                    ],   
                    sort:"text",
                    width: 150,
                    template: (data, type, value) => { 
                        let tmpValue=data.planScheduleStateType !== undefined ? data.planScheduleStateType.alias : "";
                        var html = "\n\t\t<div class=\"webix_um_badge_container\">\n\t\t\t<span class=\"webix_um_status_badge planschedule_state_" + tmpValue.toLowerCase() + "\">" + tmpValue + "</span>\n\t\t</div>";
                        return html;
                    },
                },
                { 
                    "id":"plan", 
                    "header": [
                        $.i18n("planSchedule.table.plan"), 
                        {
                            content:"richSelectFilter", 
                            options: [],
                            compare:function(item, value, data){  
                                if (item.alias === value.split("$_$")[1])
                                    return true;
                                return false;
                            }
                        }
                    ], 
                    sort:"text",  
                    "template": function (obj) {return obj.plan.alias}, 
                    fillspace:1 
                },
                { 
                    "id":"planState", 
                    "header": [
                        $.i18n("planSchedule.table.planState"), 
                        {
                            content:"richSelectFilter",
                            options: [],
                            compare:function(item, value, data){  
                                if (item.alias === value.split("$_$")[1])
                                    return true;
                                return false;
                            }
                        }
                    ], 
                    sort:"text",  
                    "template": (data, type, value) => {
                        let tmpValue=data.plan.planStateType !== undefined ? data.plan.planStateType.alias : "UNKNOWN";
                        var html = "\n\t\t<div class=\"webix_um_badge_container\">\n\t\t\t<span class=\"webix_um_status_badge planschedule_state_" + tmpValue.toLowerCase() + "\">" + tmpValue + "</span>\n\t\t</div>";
                        return html;
                    }, 
                    fillspace:1 
                },
            ],
            on:{
                onItemClick: (id) =>{
                    $$("btn_delete"+this.id).show();
                    edit_planSchedule()
                },
                onKeyPress: onKeyPressTable
            }
        };

        this.toolbar = {
            view: "toolbar",
            heigth: 50,
            rows: [
                {
                    cols:[
                        { gravity: 8},
                        { 
                            view:"button", 
                            autowidth:true,
                            hidden: false,
                            //disabled: !checkUserPermission(RULE.PLAN_ADMIN), 
                            type:"icon", 
                            tooltip:$.i18n("plan.button.tooltip.add"), icon:"mdi mdi-plus",
                            click: addToTable                    
                        },				
                        { 
                            view:"button", 
                            id: "btn_detail"+this.id, 
                            align:"right", 
                            hidden:true, 
                            autowidth:true, 
                            type:"icon", 
                            tooltip:$.i18n("planSchedule.button.tooltip.details"), 
                            icon:"mdi mdi-format-align-justify", 
                            click: detail_planSchedule
                        },
                        { 
                            view:"button", 
                            id: "btn_edit"+this.id, 
                            type:"icon", 
                            hidden:true, 
                            icon:"mdi mdi-grease-pencil", 
                            width:50, 
                            tooltip:$.i18n("planSchedule.button.tooltip.edit") ,
                            click: edit_planSchedule             
                        },
                        { 
                            view:"button", 
                            id: "btn_delete"+this.id, 
                            align:"right", 
                            hidden:true, 
                            autowidth:true, 
                            type:"icon", 
                            tooltip:$.i18n("planSchedule.button.tooltip.delete"), 
                            icon:"wxi-trash",
                            click: delete_planSchedule
                        },
                    ]
                }
            ]
        };
    }

    getComponent() {
        let generic = {
            id:this.id,
            cols:
            [
                {rows:[
                    { type:"header", template:$.i18n("menu.plans.schedule") },
                    //this.table,
                    this.genericTablei18n,
                    this.toolbar
                ]}
            ]
        };
        return generic;
    }

    disconnect(){
        this.client.deactivate();
        //$$(this.id).destructor();
        $$(this.id+"Table").clearAll();
        unLoadDetailForm()
    }
    
    loadData() {

        this.graphql
        .load()
        .then(d => {
            let datos = [];
            let planAlias = [];
            let planScheduleStates = [];
            let planState = [];
            d.forEach(element => {
                let data = {
                    id: element.id,
                    plan: element.plan,
                    cronExpression: element.cronExpression,
                    alias: element.alias,
                    description: element.description,
                    planState: element.plan.planStateType,
                    //state: element.state,
                    planScheduleStateType: element.planScheduleStateType
                };
                datos.push(data);
                planAlias.push(element.plan.alias);
                planScheduleStates.push(element.planScheduleStateType.alias);
                planState.push(element.plan.planStateType.alias);
            });
            
            const v_planAlias = Array.from(new Set(planAlias));
            const v_planScheduleStates = Array.from(new Set(planScheduleStates));
            const v_planState = Array.from(new Set(planState));
            $$(this.id+"Table").parse({
				"data": datos,
				"pos": $$(this.id+"Table").count(),
            });
            $$(this.id+"Table").config.columns.forEach(element => {
                if(element.id === "plan"){
                    let counter = 0;
                    element.header[1].options.clearAll();
                    v_planAlias.forEach(e => {
                        try {
                            element.header[1].options.add({ id: counter.toString() + "$_$" + e, value: e});
                            counter += 1;
                        } catch (e) { console.log(e); }
                    });
                }
                if(element.id === "planScheduleStateType"){
                    let counter = 0;
                    element.header[1].options.clearAll();
                    v_planScheduleStates.forEach(e => {
                        try {
                            element.header[1].options.add({ id: counter.toString() + "$_$" + e, value: e});
                            counter += 1;
                        } catch (e) { console.log(e); }
                    });
                }
                if(element.id === "planState"){
                    let counter = 0;
                    element.header[1].options.clearAll();
                    v_planState.forEach(e => {
                        try {
                            element.header[1].options.add({ id: counter.toString() + "$_$" + e, value: e});
                            counter += 1;
                        } catch (e) { console.log(e); }
                    });
                }
            });
            $$(this.id+"Table").refresh();
            this.client.activate();
            return d;
        });
    }

    init() {
        this.loadData();
    }
    
}



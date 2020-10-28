import Table from "../classes/Table.js";
import PlanScheduleForm from "../classes/PlanScheduleForm.js";

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
        let self = this;

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

        this.client.onConnect = function(frame) {
	        // Do something, all subscribes must be done is this callback
	        // This is needed because this will be executed after a (re)connect
	        this.suscription = self.client.subscribe(
	            "/topic/planSchedules.>",
	            self.processStompMessageWithFallback
	        );
        };
        //topic://planSchedules.PlanSchedules:4
        this.client.onStompError = function(frame) {
	        // Will be invoked in case of error encountered at Broker
	        // Bad login/passcode typically will cause an error
	        // Complaint brokers will set `message` header with a brief message. Body may contain details.
	        // Compliant brokers will terminate the connection after any error
	        console.log("Broker reported error: " + frame.headers["message"]);
	        console.log("Additional details: " + frame.body);
        };

        this.graph = `query {
            getPlanSchedules{
              id
              plan{id alias description state}
              cronExpression
              alias
              description
              state
            }
        }`;

        this.graphql = webix.proxy("GraphQL", this.graph);
        this.genericTablei18n = {
            id:"planSchedulesTable",
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
                { "id":"cronNextDate", "header": [$.i18n("planSchedule.table.cronNextDate"), {content:"textFilter"}
                    ],   sort:"text", fillspace:2, 
                    "template": function (obj) {return moment(prettyCron.getNextDate(obj.cronExpression, true)).format("YYYY-MM-DD HH:mm:ss");} 
                },
                //{ "id":"cronExpression", "header": [$.i18n("planSchedule.table.cronExpression"), //{content:"textFilter"}
                //    ],   sort:"text", fillspace:1 },
                {
                    id: "state",
                    //header: _("Status"),
                    "header": [$.i18n("planSchedule.table.state"), {content:"textFilter"}
                    ],   
                    sort:"text",
                    width: 150,
                    template: function (data, type, value) { return statusBadge(value); },
                },
                { "id":"plan", "header": [$.i18n("planSchedule.table.plan"), {content:"textFilter"}], sort:"text",  "template": function (obj) {return obj.plan ? obj.plan.alias : "-"}, fillspace:1 },
                { "id":"planState", "header": [$.i18n("planSchedule.table.planState"), {content:"textFilter"}], sort:"text",  "template": function (obj) {return obj.plan ? planStatusBadge(obj.plan.state) : "-"}, fillspace:1 },
            ],
            on:{
                onItemClick: planSchedule_select,
                onKeyPress: onKeyPressTable
            }
        };

        this.toolbar = {
            view: "toolbar",
            id: "toolbar_planSchedule",
            heigth: 50,
            rows: [
                {
                    cols:[
                        { gravity: 8},
                        { 
                            view:"button", 
                            id: "planSchedule_add_button", 
                            autowidth:true, 
                            type:"icon", 
                            tooltip:$.i18n("plan.button.tooltip.add"), icon:"mdi mdi-plus",
                            click: addToTable                    
                        },				
                        { 
                            view:"button", 
                            id: "b_details_planSchedule", 
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
                            id: "b_edit_planSchedule" , 
                            type:"icon", 
                            hidden:true, 
                            icon:"mdi mdi-grease-pencil", 
                            width:50, 
                            tooltip:$.i18n("planSchedule.button.tooltip.edit") ,
                            click: edit_planSchedule             
                        },
                        { 
                            view:"button", 
                            id: "b_delete_planSchedule", 
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

    processStompMessageWithFallback(message) {
        try {
            let obj = JSON.parse(JSON.parse(message.body));
            obj.basicElementsVO.forEach(element => {
                console.log(element);
                //Modificar

                //crear

                //Eliminar

            });
        }catch (err) {
            console.log("Error: " + err.message);
            console.log("RETRY WITH GRAPHQL");
        }
    }

    getComponent() {
        let generic = {
            id:"schelduleContainer",
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
        //$$("schelduleContainer").destructor();
        $$("planSchedulesTable").clearAll();
        unLoadDetailForm()
    }
    
    loadData() {

        this.graphql
        .load()
        .then(d => {
			let datos = [];
            d.forEach(element => {
                let data = {
                    id: element.id,
                    plan: element.plan,
                    cronExpression: element.cronExpression,
                    alias: element.alias,
                    description: element.description,
                    state: element.state
                };
                datos.push(data);
            });
            $$('planSchedulesTable').parse({
				"data": datos,
				"pos": $$('planSchedulesTable').count(),
			});
			$$("planSchedulesTable").refresh();
            this.client.activate();
            return d;
        });
    }

    init() {
        this.loadData();
    }
}

function statusBadge(value) {
    var cssStatusName = LBL_PLAN_SCHEDULE_STATE[value].toLowerCase();
    var html = "\n\t\t<div class=\"webix_um_badge_container\">\n\t\t\t<span class=\"webix_um_status_badge planschedule_state_" + cssStatusName + "\">" + LBL_PLAN_SCHEDULE_STATE[value] + "</span>\n\t\t</div>";
    return html;
}

function planStatusBadge(value) {
    var cssStatusName = PLAN_STATE_TYPE[value].toLowerCase();
    var html = "\n\t\t<div class=\"webix_um_badge_container\">\n\t\t\t<span class=\"webix_um_status_badge planschedule_state_" + cssStatusName + "\">" + PLAN_STATE_TYPE[value] + "</span>\n\t\t</div>";
    return html;
}

function planSchedule_select(id){
	$$("b_delete_planSchedule").show();
	edit_planSchedule()
}

function addToTable(){
	unLoadDetailForm();
	loadDetailForm(null, 2); 
}

function detail_planSchedule(){
	let selectPlan = $$("planSchedulesTable").getSelectedId();
	let item = $$('planSchedulesTable').getItem(selectPlan);
	loadDetailForm(item.id, 0); 
}

function edit_planSchedule(id){
	let selectPlan = $$("planSchedulesTable").getSelectedId();
	let item = $$('planSchedulesTable').getItem(selectPlan);
	loadDetailForm(item.id, 1)
}

function loadDetailForm(id, num){
	let auxPlanSchedule = new PlanScheduleForm(id,null,num);
	if(!$$("scheduleDetailForm")){
		$$("schelduleContainer").addView({ id:"scheduleResizer", view:"resizer" });
		$$("schelduleContainer").addView(auxPlanSchedule.getComponent());
	}else{
		webix.ui(
			auxPlanSchedule.getComponent()
		  , $$('scheduleDetailForm'));
	}
}

function onKeyPressTable(code, e){
	if(code==27){
		unLoadDetailForm();
	}
}

function unLoadDetailForm(){
	$$("planSchedulesTable").unselect();
	$$("b_delete_planSchedule").hide();
	if($$("scheduleDetailForm")){
		$$("schelduleContainer").removeView("scheduleResizer");
		$$("schelduleContainer").removeView("scheduleDetailForm");
	}
}

function delete_planSchedule(id){
	let selectPlan = $$("planSchedulesTable").getSelectedId();
	let item = $$('planSchedulesTable').getItem(selectPlan);
	try {
		$$("planSchedule_win").destructor();
	} catch  {}

	let planScheduleForm = new PlanScheduleForm(item.id,null,3);
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
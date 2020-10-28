import CronUi from "../classes/CronUi.js";

const PLAN_ACTION_START = 'START';
const PLAN_ACTION_STOP = 'STOP';
const PLAN_ACTION_PAUSE = 'PAUSE';
const PLAN_ACTION_PLAY = 'PLAY';
const PLAN_ACTION_RESET = 'RESET';

const LBL_PLAN_SCHEDULE_STATE = {
    0: 'UNKNOWN',
    1: 'PAUSE',
    2: 'RUNNING',
    3: 'FAILED'
};

export default class PlanScheduleForm {
	constructor(id,data,mode) {
        this.id = id;
        this.mode = mode;
        if (this.id === null && data !== null) {
            this.alias = data.alias;
            this.description = data.description;
            this.plan = data.plan;
            this.cronExpression = data.cronExpression;
        }
        ( this.id === null && data === null ) ? this.id = "new" : null;

        // creates the stomp connection
        this.client = new StompJs.Client({
            brokerURL: webix.storage.session.get("url.ws.stomp"),
            connectHeaders: {
            login: "user",
            passcode: "user"
            },
            //debug: function(str) {
            //console.log(str);
            //},
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000
        });
		this.topic = "/topic/planSchedules." + this.id;
		this.client.onConnect = function(frame) {
			this.suscription = self.client.subscribe(self.topic, self.update_table);
		};
		this.client.onStompError = function(frame) {
			// Will be invoked in case of error encountered at Broker
			// Bad login/passcode typically will cause an error
			// Complaint brokers will set `message` header with a brief message. Body may contain details.
			// Compliant brokers will terminate the connection after any error
			console.log("Broker reported error: " + frame.headers["message"]);
			console.log("Additional details: " + frame.body);
		};
	
		var graphPlanSchedule = `query  {
            getPlanSchedule(planScheduleId: "` + this.id + `" ){
                id
                plan{id alias description}
                cronExpression
                alias
                state
                description
            }
        }`;
        
        this.graphqlSendPlanQuery = `query  {
            mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) {
                sendPlanScheduleActions(planSchedules: $plan) { id }
            }`; 
        this.graphqlSendPlan = webix.proxy("GraphQL", this.graphqlSendPlanQuery);

        this.graphql = webix.proxy("GraphQL", graphPlanSchedule);
        
        this.graphqlPlansQuery = `query  {
            getPlans{
              id
              alias
            }
          }`;
        this.graphqlPlans = webix.proxy("GraphQL", this.graphqlPlansQuery);

		this.formHeader = {
			view: "template",
			type: "header",
			id: "planScheduleFormHeader_"+this.id,
			data: {
				value: ""
			},
			template: function (obj) {
				return obj.value;
			},
			css: { "text-align": "left !important" }
        };
        
        function saveEditPlanSchedule(id) {
            let itemId = id.toString().split("_")[2];
            let updateplanScheduleForm = $$("planScheduleForm_" + itemId).getValues();
            if($$("planScheduleForm_" + itemId).validate()){
                let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) {sendPlanScheduleActions(planSchedules: $plan){ id }}`; 
                let graphqlSendPlan2 = webix.proxy("GraphQL", graphqlSendPlanQuery2);
                graphqlSendPlan2.load({
                    plan: [
                        {
                        id: updateplanScheduleForm.id,
                        alias: updateplanScheduleForm.alias,
                        description: updateplanScheduleForm.description,
                        action: PLAN_ACTION_RESET,
                        planId: updateplanScheduleForm.plan.split("_")[2],
                        cronExpression: updateplanScheduleForm.cronExpression    
                        }
                    ]
                }).then(data => {
                    if(data){
                        let graphPlanScheduleQuery = `query  {
                            getPlanSchedule(planScheduleId: "` + updateplanScheduleForm.id + `" ){
                                id
                                plan{id alias description}
                                cronExpression
                                alias
                                description
                            }
                            }`;
                        let graphqlSendPlanU = webix.proxy("GraphQL", graphPlanScheduleQuery);
                        graphqlSendPlanU.load({}).then(dataPlan => {
                            let item = $$("planSchedulesTable").getItem(dataPlan.id);
                            item["alias"] = dataPlan.alias;
                            item["description"] = dataPlan.description;
                            item["cronExpression"] = dataPlan.cronExpression;
                            item["plan"] = dataPlan.plan;
                            $$("planSchedulesTable").updateItem(dataPlan.id, item);
    
                            $$("planSchedulesTable").unselect();
                            $$("b_delete_planSchedule").hide();
                            if($$("scheduleDetailForm")){
                                $$("schelduleContainer").removeView("scheduleResizer");
                                $$("schelduleContainer").removeView("scheduleDetailForm");
                            }
                        });
                    }
                });
            }else{
                webix.message({ type:"error", text:$.i18n("planSchedule.form.errorMessage")});
            }  
        }

        function createNewPlanSchedule(id) {
            let itemId = id.toString().split("_")[2];
            let createplanScheduleForm = $$("planScheduleForm_" + itemId).getValues();
            if($$("planScheduleForm_" + itemId).validate()){
                let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) { sendPlanScheduleActions(planSchedules: $plan) { id } }`; 
                let graphqlSendPlan2 = webix.proxy("GraphQL", graphqlSendPlanQuery2);
                let graphPlanSchedulesQuery = `query  {
                    getPlanSchedules{
                        id
                        plan{id alias description}
                        cronExpression
                        alias
                        description
                    }
                    }`;
                let graphPlanSchedules = webix.proxy("GraphQL", graphPlanSchedulesQuery);
                graphqlSendPlan2.load({
                    plan: [
                        {
                        alias: createplanScheduleForm.alias,
                        description: createplanScheduleForm.description,
                        action: PLAN_ACTION_START,
                        planId: createplanScheduleForm.plan.split("_")[2],
                        cronExpression: createplanScheduleForm.cronExpression
                        }
                    ]
                }).then(data => {
                    if(data){
                    //Como no tenemos el id del plan creado pedimos todos y volvemos a rellenar la tabla
                    $$("planSchedulesTable").clearAll();
                    $$("planSchedulesTable").load(`GraphQL->{ 
                        getPlanSchedules{
                            id
                            plan{id alias description}
                            cronExpression
                            alias
                            description
                        }
                        }`);

                        $$("planSchedulesTable").unselect();
                        $$("b_delete_planSchedule").hide();
                        if($$("scheduleDetailForm")){
                            $$("schelduleContainer").removeView("scheduleResizer");
                            $$("schelduleContainer").removeView("scheduleDetailForm");
                        }
                    }
                });
            }else{
                webix.message({ type:"error", text:$.i18n("planSchedule.form.errorMessage")});
            }
        }

        this.form = {   
            view: "form",
            id: "planScheduleForm_" + this.id,
            width: 0,
            margin: 0,
            elements: [
                { view: "text",  id: "planScheduleForm_" + this.id + "_id", name: "id", label: $.i18n("planSchedule.popup.id") , labelWidth: 200, disabled: true } ,
                { view: "text",  id: "planScheduleForm_" + this.id + "_alias",name: "alias", label: $.i18n("planSchedule.popup.alias") , labelWidth: 200, disabled: true },
                { view: "textarea",  id: "planScheduleForm_" + this.id + "_description",name: "description", label: $.i18n("planSchedule.popup.description") ,labelWidth: 200, disabled: true },
                { 
                    view: "text",  
                    id: "planScheduleForm_" + this.id + "_cronExpression",
                    name: "cronExpression", label: $.i18n("planSchedule.popup.cronExpression") ,
                    labelWidth: 200, 
                    disabled: true,
                    on: {
                        "onFocus": function(id){
                            let popupid = "cronUiPopup";
                            let cron = new CronUi(id,popupid);
                            webix.ui({
                                id:popupid,
                                view:"popup",	
                                position:"center",
                                close: true,
                                width: 800,
                                body: cron.getComponent(),
                                on: {
                                    onHide: function(obj){
                                        $$(popupid).getChildViews()[0].destructor();
                                        $$(popupid).destructor();
                                        $$(id).enable();
                                    },
                                    onShow: function(obj){
                                        cron.loadData();
                                        $$(id).disable();
                                    }
                                }
                              }).show();
                        }
                    } 
                },
                { view: "combo", id: "planScheduleForm_" + this.id + "_plan",name: "plan", label: $.i18n("planSchedule.popup.plan") ,labelWidth: 200, disabled: true, options: [],},
                {
                    view: "switch",
                    name: "state",
                    id: "planScheduleForm_" + this.id + "_state",
                    label: "state",
                    //css: "webix_um_status_switch",
                    hidden: (this.id === "new" ? true : false),
                    on: {
                        /*
                        onChange: function (value) {
                            console.log("onChange");
                            console.log(id);
                            console.log(value);
                            UpdateBadge(parseInt(value) - 1); 
                        },
                        */
                        onItemClick: function(value) {
                            console.log("onClick");
                            console.log(id);
                            console.log(value);
                            let dato = parseInt($$(value).getValue());
                            console.log(dato);
                            if(dato === 0) {
                                //Mandamos la pausa al server
                                let planScheduleForm = $$("planScheduleForm_" + id).getValues();
                                let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) {sendPlanScheduleActions(planSchedules: $plan){ id state }}`; 
                                let graphqlSendPlan2 = webix.proxy("GraphQL", graphqlSendPlanQuery2);
                                graphqlSendPlan2.load({
                                    plan: [
                                        {
                                        id: planScheduleForm.id,
                                        alias: planScheduleForm.alias,
                                        description: planScheduleForm.description,
                                        action: PLAN_ACTION_PAUSE,
                                        planId: planScheduleForm.plan.split("_")[2],
                                        cronExpression: planScheduleForm.cronExpression    
                                        }
                                    ]
                                }).then(data => {
                                    console.log(data);
                                    if(data[0] !== null){
                                        //actualizamos el panel
                                        let control = $$("planScheduleForm_" + data[0].id + "_state");
                                        console.log(control);
                                        let html = statusBadge(data[0].state);
                                        control.config.labelRight = html;
                                        console.log(html);
                                        control.refresh();
                                        //mandamos mensaje de planSchedule PAUSE
                                        webix.message({type:"success", text:$.i18n("lbl_panel_signaling_correctly")});
                                    }else{
                                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.second")});
                                    }
                                });
                            }
                            if(dato === 1) {
                                //mandamos el running al server
                                let planScheduleForm = $$("planScheduleForm_" + id).getValues();
                                let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) {sendPlanScheduleActions(planSchedules: $plan){ id state }}`; 
                                let graphqlSendPlan2 = webix.proxy("GraphQL", graphqlSendPlanQuery2);
                                graphqlSendPlan2.load({
                                    plan: [
                                        {
                                        id: planScheduleForm.id,
                                        alias: planScheduleForm.alias,
                                        description: planScheduleForm.description,
                                        action: PLAN_ACTION_PLAY,
                                        planId: planScheduleForm.plan.split("_")[2],
                                        cronExpression: planScheduleForm.cronExpression    
                                        }
                                    ]
                                }).then(data => {
                                    console.log(data);
                                    if(data[0] !== null){
                                        //actualizamos el panel
                                        let control = $$("planScheduleForm_" + data[0].id + "_state");
                                        console.log(control);
                                        let html = statusBadge(data[0].state);
                                        control.config.labelRight = html;
                                        console.log(html);
                                        control.refresh();
                                        //mandamos mensaje de planSchedule RUNNING
                                        webix.message({type:"success", text:$.i18n("lbl_panel_signaling_correctly")});
                                    }else{
                                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.second")});
                                    }                                
                                });
                            }
                        }
                    },
                },
            ],
            rules:{
                "cronExpression":webix.rules.isNotEmpty,
                "plan":webix.rules.isNotEmpty
            }               
        };

        function UpdateBadge(value){
            var control = $$("planScheduleForm_" + this.id + "_state");
            var html = statusBadge(value);
            control.config.labelRight = html;
            control.refresh();
        }


/*
        function statusBadge(value) {
            var status = value ? "Active" : "Not active";
            var cssStatusName = value ? "active" : "not_active";
            var html = "\n\t\t<div class=\"webix_um_badge_container\">\n\t\t\t<span class=\"webix_um_status_badge webix_um_" + cssStatusName + "\">" + status + "</span>\n\t\t</div>";
            return html;
        }
*/
		this.buttons = {
            id: "planScheduleFormButtons_" + this.id,
			view: "form",
			elements: [
                { 
                    view: "button", hidden: true, id: "planScheduleFormButtons_create_" + this.id, label: $.i18n("planSchedule.popup.button.create"), value: "",
                    click: createNewPlanSchedule
                },
                { 
                    view: "button", hidden: true, id: "planScheduleFormButtons_save_" + this.id,  label: $.i18n("planSchedule.popup.button.save"), value: "",
                    click: saveEditPlanSchedule
                }
			]
		};
	}

	update_table(message) {
		try {
			let obj = JSON.parse(JSON.parse(message.body));
            obj.basicElementsVO.forEach((element) => {
                console.log(element);
                //Modificar

                //crear

                //Eliminar

            });
        } catch (err) {
			console.log("Error: " + err.message);
		}
	}

    disconnect(){
		this.client.deactivate();
    }

	getComponent() {
        let header = "";

		let genericForm = {
            id:"scheduleDetailForm",
            width:"500",
			rows: [
                this.formHeader,
                this.form,
                this.buttons,
				{}
			]
		};
        
        switch (this.mode) {
            case 0:  //Details Mode
                this.graphql.load().then(data => {
                    if (data) {
                        header = data.id + " " + data.alias;
                        try {
                            $$("planScheduleForm_" + this.id).setValues({
                                id: data.id,
                                alias: data.alias,
                                description: data.description,
                                cronExpression: data.cronExpression,
                                //state: data.state
                            });
                            $$("planScheduleForm_" + this.id + "_state").setValue(data.state === 2 ? 1 : 0);
                            let control = $$("planScheduleForm_" + this.id + "_state");
                            let html = statusBadge(data.state);
                            control.config.labelRight = html;
                            control.refresh();
                            $$("planScheduleForm_" + this.id + "_plan").setValue({ id: "planScheduleForm_" + this.id + "_" + data.plan.id, value: data.plan.alias });
                            $$("planScheduleFormHeader_"+this.id).setValues({ value: data.alias });
                        } catch {}
                    } 
                });
                this.client.activate();
                break;
            case 1: //Edit Mode
                this.graphql.load().then(data => {
                    if (data) {
                        $$("planScheduleForm_" + this.id + "_alias").enable();
                        $$("planScheduleForm_" + this.id + "_description").enable();
                        $$("planScheduleForm_" + this.id + "_cronExpression").enable();
                        $$("planScheduleForm_" + this.id + "_plan").enable();
                        $$("planScheduleFormButtons_save_" + this.id).show();
                        header = data.id + " " + data.alias;
                        try {
                            $$("planScheduleForm_" + this.id).setValues({
                                id: data.id,
                                alias: data.alias,
                                description: data.description,
                                cronExpression: data.cronExpression,
                                //state: data.state
                            });
                            $$("planScheduleForm_" + this.id + "_state").setValue(data.state === 2 ? 1 : 0);
                            //let state = data.state === 2 ? 1 : 0;
                            //$$("planScheduleForm_" + this.id + "_state").setValue(state);
                            let control = $$("planScheduleForm_" + this.id + "_state");
                            let html = statusBadge(data.state);
                            control.config.labelRight = html;
                            control.refresh();
                            this.graphqlPlans.load().then(data2 => {
                                if(data2){
                                    data2.forEach(item => {
                                        if(data.plan.id !== item.id){
                                            $$("planScheduleForm_" + this.id + "_plan").getList().add({ id: "planScheduleForm_" + this.id + "_" + item.id, value: item.alias });
                                        }
                                    });
                                }
                            });
                            $$("planScheduleForm_" + this.id + "_plan").setValue({ id: "planScheduleForm_" + this.id + "_" + data.plan.id, value: data.plan.alias });
                            $$("planScheduleFormHeader_"+this.id).setValues({ value: data.alias });
                        } catch {}
                    } 
                });
                this.client.activate();
                break;
            case 2: //new Mode
                this.graphqlPlans.load().then(data2 => {
                    $$("planScheduleForm_" + this.id + "_id").hide();
                    $$("planScheduleForm_" + this.id + "_alias").enable();
                    $$("planScheduleForm_" + this.id + "_description").enable();
                    $$("planScheduleForm_" + this.id + "_cronExpression").enable();
                    $$("planScheduleForm_" + this.id + "_plan").enable();
                    $$("planScheduleFormButtons_create_" + this.id).show();
                    if(data2){
                        data2.forEach(item => {
                            $$("planScheduleForm_" + this.id + "_plan").getList().add({ id: "planScheduleForm_" + this.id + "_" + item.id, value: item.alias });
                        });
                    }
                    $$("planScheduleFormHeader_"+this.id).setValues({ value: data.alias });
                });
                break;
            case 3: // delete Mode
                genericForm = 
                {
                    rows: [
                        { margin:5, cols:[
                            { view:"button", value:"Confirmar", css:"webix_primary", id: this.id,
                                click: function deletePlanSchedule(obj){
                                    let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) { sendPlanScheduleActions(planSchedules: $plan){ id }}`; 
                                    let graphqlSendPlan2 = webix.proxy("GraphQL", graphqlSendPlanQuery2);
                                    graphqlSendPlan2.load({
                                        plan: [
                                            {
                                                id: obj,
                                                action: PLAN_ACTION_STOP,
                                                description: "null",
                                                planId: "null",
                                                cronExpression: "0 0 * ? * *"
                                            }
                                        ]
                                    }).then(data => {
                                        try {
                                            if(data){
                                                let graphPlanScheduleQuery = `query  {
                                                    getPlanSchedule(planScheduleId: "` + obj + `" ){
                                                        id
                                                        plan{id alias description}
                                                        cronExpression
                                                        alias
                                                        description
                                                    }
                                                    }`;
                                                let graphPlanScheduleQuery2 = webix.proxy("GraphQL", graphPlanScheduleQuery);
                                                graphPlanScheduleQuery2.load({}).then(dataPlan => {
                                                    if(dataPlan === null){
                                                        $$("planSchedulesTable").remove(obj);
                                                        $$("planSchedule_win").destructor();
                                                        $$("b_details_planSchedule").hide();
                                                        $$("b_edit_planSchedule" ).hide();
                                                        $$("b_delete_planSchedule").hide();
                                                    }
                                                });
                                            }
                                        } catch  {}
                                    });
                                }
                            },
                            { view:"button", value:"Cancel",
                                click: function cancelUser(obj){
                                    try {
                                        $$("planSchedule_win").destructor();
                                    } catch  {}
                                }                        
                            }
                        ]},
                        //{}
                    ]
                };
                break;
        }
		return genericForm;
    }
    onAction(){
        alert("aaa")
       
    }
}

function statusBadge(value) {
    var cssStatusName = LBL_PLAN_SCHEDULE_STATE[value].toLowerCase();
    var html = "\n\t\t<div class=\"webix_um_badge_container\">\n\t\t\t<span class=\"webix_um_status_badge planschedule_state_" + cssStatusName + "\">" + LBL_PLAN_SCHEDULE_STATE[value] + "</span>\n\t\t</div>";
    return html;
}
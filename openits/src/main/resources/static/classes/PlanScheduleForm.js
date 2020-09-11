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
		var graphPlanSchedule = `query  {
            getPlanSchedule(planScheduleId: "` + this.id + `" ){
                id
                plan{id alias description}
                cronExpression
                alias
                description
            }
            }`;
        
        this.graphqlSendPlanQuery = `query  {
            mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) {
                sendPlanScheduleActions(planSchedules: $plan) 
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

		this.header = {
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
            $$("planScheduleForm_" + itemId).validate();
            let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) {sendPlanScheduleActions(planSchedules: $plan)}`; 
            let graphqlSendPlan2 = webix.proxy("GraphQL", graphqlSendPlanQuery2);
            graphqlSendPlan2.load({
                plan: [
                    {
                    id: updateplanScheduleForm.id,
                    alias: updateplanScheduleForm.alias,
                    description: updateplanScheduleForm.description,
                    action: true,
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
                        $$("planSchedule_win").destructor();
                    });
                }
            });
        }

        function createNewPlanSchedule(id) {
            let itemId = id.toString().split("_")[2];
            let createplanScheduleForm = $$("planScheduleForm_" + itemId).getValues();
            $$("planScheduleForm_" + itemId).validate();
            let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) { sendPlanScheduleActions(planSchedules: $plan) }`; 
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
                    action: true,
                    planId: createplanScheduleForm.plan.split("_")[2],
                    cronExpression: createplanScheduleForm.cronExpression
                    }
                ]
            }).then(data => {
                if(data){
                    //Como no tenemos el id del plan creado pedimos todos y volvemos a rellenar la tabla
                    $$("planSchedulesTable").clearAll();
                    graphPlanSchedules.load({}).then(dataPlan => {
                        dataPlan.forEach(item =>{
                            $$("planSchedulesTable").add({
                                id:item.id,
                                alias:item.alias,
                                description: item.description,
                                plan: { id: "planScheduleForm_" + item.id + "_" + item.plan.id, value: item.plan.alias },
                                cronExpression:item.cronExpression
                              });
                        });
                        $$("planSchedule_win").destructor();
                    });
                }
            });
        }

        this.form = {   
            view: "form",
            id: "planScheduleForm_" + this.id,
            width: 0,
            margin: 0,
            elements: [
                { view: "text",  id: "planScheduleForm_" + this.id + "_id", name: "id", label: $.i18n("planSchedule.popup.id") , labelWidth: 200, disabled: true } ,
                { view: "text",  id: "planScheduleForm_" + this.id + "_alias",name: "alias", label: $.i18n("planSchedule.popup.alias") , labelWidth: 200, disabled: true },
                { view: "text",  id: "planScheduleForm_" + this.id + "_description",name: "description", label: $.i18n("planSchedule.popup.description") ,labelWidth: 200, disabled: true },
                { view: "text",  id: "planScheduleForm_" + this.id + "_cronExpression",name: "cronExpression", label: $.i18n("planSchedule.popup.cronExpression") ,labelWidth: 200, disabled: true },
                { view: "combo", id: "planScheduleForm_" + this.id + "_plan",name: "plan", label: $.i18n("planSchedule.popup.plan") ,labelWidth: 200, disabled: true, options: [],},
            ],
            rules:{
                "alias":webix.rules.isNotEmpty,
                "cronExpression":webix.rules.isNotEmpty,
                "plan":webix.rules.isNotEmpty
            }               
        };
		this.buttons = {
            id: "planScheduleFormButtons_" + this.id,
			view: "form",
			elements: [
                { view: "button", hidden: true, id: "planScheduleFormButtons_create_" + this.id, label: $.i18n("planSchedule.popup.button.create"), value: "",
                    click: createNewPlanSchedule
                },
                { view: "button", hidden: true, id: "planScheduleFormButtons_save_" + this.id,  label: $.i18n("planSchedule.popup.button.save"), value: "",
                    click: saveEditPlanSchedule
                }
			]
		};
	}

	getComponent() {
        let header = "";

		let genericForm = {
			rows: [
                //this.header,
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
                                cronExpression: data.cronExpression
                            });
                            $$("planScheduleForm_" + this.id + "_plan").setValue({ id: "planScheduleForm_" + this.id + "_" + data.plan.id, value: data.plan.alias });
                        } catch {}
                    } 
                });
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
                                cronExpression: data.cronExpression
                            });

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
                            
                        } catch {}
                    } 
                });
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
                });
                break;
            case 3: // delete Mode
                genericForm = {
                    rows: [
                        { margin:5, cols:[
                            { view:"button", value:"Confirmar", css:"webix_primary", id: this.id,
                                click: function deletePlanSchedule(obj){
                                    let graphqlSendPlanQuery2 = `mutation sendPlanScheduleActions($plan: [PlanSchedulesVO!]!) { sendPlanScheduleActions(planSchedules: $plan)}`; 
                                    let graphqlSendPlan2 = webix.proxy("GraphQL", graphqlSendPlanQuery2);
                                    graphqlSendPlan2.load({
                                        plan: [
                                            {
                                                id: obj,
                                                action: false,
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
                                                graphPlanScheduleQuery.load({}).then(dataPlan => {
                                                    if(dataPlan){
                                                        $$("planSchedulesTable").remove(dataPlan.id);
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
}

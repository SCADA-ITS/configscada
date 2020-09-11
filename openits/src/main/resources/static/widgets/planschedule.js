import Table from "../classes/Table.js";
import PlanScheduleForm from "../classes/PlanScheduleForm.js";

var graph =  `GraphQL->{ 
        getPlanSchedules{
          id
          plan{id alias description}
          cronExpression
          alias
          description
        }
      }`;

let genericTablei18n = {
		id:"planSchedulesTable",
		"view":"datatable",
		"autoConfig":true,
		"select":true,
		"columns":[
			{ "id":"id", "header": $.i18n("planSchedule.table.id"), hidden: true},
			{ "id":"alias", "header": [$.i18n("planSchedule.table.alias"),  {content:"textFilter"}], 
				width:550, 
				sort:"text" },
            { "id":"description", "header": [$.i18n("planSchedule.table.description"),  {content:"textFilter"}], width:550, sort:"text" },
			{ "id":"cronExpression", "header": [$.i18n("planSchedule.table.cronExpression"), {content:"textFilter"}], adjust:true,  sort:"text" },
			{ "id":"plan", "header": [$.i18n("planSchedule.table.plan"), {content:"textFilter"}], sort:"text", adjust:true, "template": function (obj) {return obj.plan ? obj.plan.alias : "-"}  },
		],
		on:{
			onItemClick: planSchedule_select,
		}
	};

let table = new Table(graph, genericTablei18n ).getComponent();
//table.scrollY=true;

export let planschedules= {
	rows:[
        table,
        {
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
							click:detail_planSchedule
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
							click:delete_planSchedule
						},
                    ]
                }
            ]
        }
    ]
};

function planSchedule_select(id){
	$$("b_details_planSchedule").show();
	$$("b_edit_planSchedule" ).show();
	$$("b_delete_planSchedule").show();
}

function addToTable(id){
	try {
		if($$("planSchedule_win")) 
			$$("planSchedule_win").destructor();
	} catch  {}
	let planScheduleForm = new PlanScheduleForm(null,null,2);
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
				$$("planSchedule_win").destructor();
			},
		}
	  })
	  .show();
}

function detail_planSchedule(id){
	let selectPlan = $$("planSchedulesTable").getSelectedId();
	let item = $$('planSchedulesTable').getItem(selectPlan);
	try {
		$$("planSchedule_win").destructor();
	} catch  {}

	let planScheduleForm = new PlanScheduleForm(item.id,null,0);
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
				$$("planSchedule_win").destructor();
			}
		}
	  })
	  .show();
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
				$$("planSchedule_win").destructor();
			}
		}
	  })
	  .show();	
}

function edit_planSchedule(id){
	let selectPlan = $$("planSchedulesTable").getSelectedId();
	let item = $$('planSchedulesTable').getItem(selectPlan);
	try {
		$$("planSchedule_win").destructor();
	} catch  {}

	let userForm = new PlanScheduleForm(item.id,null,1);
	webix
	  .ui({ 
		id: "planSchedule_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
		on: {
			onHide: function(obj){
				$$("planSchedule_win").destructor();
			}
		}
	  })
	  .show();
}
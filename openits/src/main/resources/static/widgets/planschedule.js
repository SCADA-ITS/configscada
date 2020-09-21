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
		resizeColumn:true,
		"columns":[
			{ "id":"id", "header": $.i18n("planSchedule.table.id"), hidden: true},
			{ "id":"alias", "header": [$.i18n("planSchedule.table.alias"),  {content:"textFilter"}], sort:"text", fillspace:1 },
			{ "id":"description", "header": [$.i18n("planSchedule.table.description"),  {content:"textFilter"}],  sort:"text", fillspace:2 },
			{ "id":"cronDate", "header": [$.i18n("planSchedule.table.cronExpression"), {content:"textFilter"}],   sort:"text", fillspace:2, "template": function (obj) {return prettyCron.toString(obj.cronExpression, true);} },
			{ "id":"cronExpression", "header": [$.i18n("planSchedule.table.cronExpression"), {content:"textFilter"}],   sort:"text", fillspace:1 },
			{ "id":"plan", "header": [$.i18n("planSchedule.table.plan"), {content:"textFilter"}], sort:"text",  "template": function (obj) {return obj.plan ? obj.plan.alias : "-"}, fillspace:1 },
		],
		on:{
			onItemClick: planSchedule_select,
			// onBlur: function(){
			// 	$$("planSchedulesTable").unselect()
			// },
			onKeyPress: onKeyPressTable
		}
	};

let table = new Table(graph, genericTablei18n ).getComponent();
let toolbar = {
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

export let planschedules= {
	id:"schelduleContainer",
	cols:
	[
		{rows:[
			{ type:"header", template:$.i18n("menu.plans.schedule") },
			table,
			toolbar
		]}
	]
};


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
				$$("planSchedule_win").destructor();
				unLoadDetailForm();
			}
		}
	  })
	  .show();	
}

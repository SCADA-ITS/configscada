import { plan } from "../data/plan.js";
import Plan from "../classes/Plan.js";

export default class PlansTree {
  constructor(data, metadata) {
    this.planSubtype = [];
    this.planType = [];
    this.planes = [];
    this.nodes = [];
    this.data = data;
    this.dato = [];
    this.order = [];

    // for using the object inside callbacks
    let self = this;

    // creates the stomp connection
    this.client = new StompJs.Client({
      brokerURL: webix.storage.session.get("url.ws.stomp"),
      connectHeaders: {
        login: "user",
        passcode: "user"
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });

    this.client.onConnect = function(frame) {
      // Do something, all subscribes must be done is this callback
      // This is needed because this will be executed after a (re)connect
      this.suscription = self.client.subscribe(
        "/topic/plan",
        self.processStompMessageWithFallback
      );
    };

    this.client.onStompError = function(frame) {
      // Will be invoked in case of error encountered at Broker
      // Bad login/passcode typically will cause an error
      // Complaint brokers will set `message` header with a brief message. Body may contain details.
      // Compliant brokers will terminate the connection after any error
      console.log("Broker reported error: " + frame.headers["message"]);
      console.log("Additional details: " + frame.body);
    };

    this.client.activate();

    if (metadata == null) {
      this.metadata = plan;
    } else {
      this.metadata = metadata;
    }
  }


  /*
   *	Stomp without GraphQL
  */
	processStompMessage(message) {
    try {
			let obj = JSON.parse(JSON.parse(message.body));
			
      obj.basicElementsVO.forEach(element => {
        var hijos = $$(element.planOrders[0].planId).getChildViews();
        for(let i = 0; i<hijos.length; i++){ 
            console.log(hijos[i].config);
        }
      });
    } catch (err) {
      console.log("Error: " + err.message);
    }
	}
	
	/*
	*	queries GraphQL based on the element ID received by Stomp
  */
  
  processStompMessageGraphQL(message) {
    try {
			let obj = JSON.parse(JSON.parse(message.body));
			
      obj.basicElementsVO.forEach(element => {
				//initialize the proxy
				let elementQuery = webix.proxy(
					"GraphQL",
					`
					query ($elem: String!) {

					}
					`
				);
        // loads the data
        elementQuery
          .load({
            elem: element.id
          })
          .then(data => {
						// process the received data
						//console.log(data);
						let element = data;

          });
      });
    } catch (err) {
      console.log("Error: " + err.message);
    }
	}
	
	/*
	*	Tries to get the data from Stomp, but if it doesn't find the needed data,
	* gets it by GraphQL
	*/
	processStompMessageWithFallback(message) {
    try {
			let obj = JSON.parse(JSON.parse(message.body));
			
      obj.basicElementsVO.forEach(element => {
        $$(element.planOrders[0].planId.toString() + "_state").setValue(element.planOrders[0].state);
        webix.message(element.planOrders[0].planId.toString() + " Ejecutado Correctamente");
        //var hijos = $$(element.planOrders[0].planId).getChildViews();
        //for(let i = 0; i<hijos.length; i++){ 
        //    console.log(hijos[i]);
        //}

      });
    } catch (err) {
			console.log("Error: " + err.message);
			console.log("RETRY WITH GRAPHQL");
			//console.log(message.body);

			let obj = JSON.parse(JSON.parse(message.body));
/*
      obj.basicElementsVO.forEach(element => {
				//initialize the proxy
				let elementQuery = webix.proxy(
					"GraphQL",
					`
					query ($elem: String!) {

					}
					`
				);

        // loads the data
        elementQuery
          .load({
            elem: element.id
          })
          .then(data => {
						// process the received data
						console.log(data);
						let element = data;

          });
      });
      */
    }
	}

  addToTree(){
    let selectPlan = $$("treePlan").getSelectedId();
    let item = $$('treePlan').getItem(selectPlan);
    let pos = $$("treePlan").getBranchIndex(item.id);
    let counte = item.$count * 20;
    if (item.$level < 4 ) {
      $$('treePlan').add({id: item.id + "." + counte.toString() ,alias:"New item", description: "", qid: "new" }, pos, item.id);
    }
  }

  editPlan(){
    let selectPlan = $$("treePlan").getSelectedId();
    let item = $$('treePlan').getItem(selectPlan);
    let pos = $$("treePlan").getBranchIndex(item.id);

    console.log($$(item.qid + "_alias"));
    $$(item.qid + "_alias").enable();
    $$(item.qid + "_description").enable();
    $$(item.qid + "_planModeType").enable();
    $$("b_edit").hide();
    $$("b_save").show();
    $$("b_play").hide();
    $$("b_stop").hide();
    $$("b_pause").hide();
    $$("b_remove").hide();
    $$("b_add_order").hide();
  }

  removeToTree(){
    let selectPlan = $$("treePlan").getSelectedId();
    let item = $$('treePlan').getItem(selectPlan);
    if (item.$count > 0) {
      webix.message("ERROR, Eliminar Hijos");
    } else {
      switch (item.$level) {
        case 4:  //PLAN
          if (item.qid !== "new") {
            let planMutation = webix.proxy(
              "GraphQL",`mutation deletePlan($plan: String!){
                deletePlan(planId: $plan ){
                  id
                }
              }
              `);
            // loads the data
            planMutation
              .load({
                plan: item.qid
              })
              .then(data => {
                if (data.id === item.qid) {
                  $$('treePlan').remove(item.id);
                  return true;
                } else {
                  webix.message("ERROR, Al Eliminar");
                  return false;
                }
              });
          } else {
            $$('treePlan').remove(item.id);
            return true;
          }
          break;
        case 3: //SubType
          if (item.qid !== "new") {
          let planMutation = webix.proxy(
            "GraphQL",`mutation deletePlanSubtype($planSubtype: String!){
              deletePlanSubtype(planSubtypeId: $planSubtype){
                id
              }
            }
            `);
          // loads the data
          planMutation
            .load({
              planSubtype: item.qid
            })
            .then(data => {
              if (data.id === item.qid) {
                $$('treePlan').remove(item.id);
                return true;
              } else {
                webix.message("ERROR, Al Eliminar");
                return false;
              }
            });
          } else {
            $$('treePlan').remove(item.id);
            return true;
          }
          break; 
        case 2: //Type
          if (item.qid !== "new") {
            let planMutation2 = webix.proxy(
              "GraphQL",`mutation deletePlanType($planSubtype: String!){
                deletePlanType(planTypeId: $planSubtype){
                  id
                }
              }
              `);
            // loads the data
            planMutation2
              .load({
                planSubtype: item.qid
              })
              .then(data => {
                if (data.id === item.qid) {
                  $$('treePlan').remove(item.id);
                  return true;
                } else {
                  webix.message("ERROR, Al Eliminar");
                  return false;
                }
              }); 
          } else {
            $$('treePlan').remove(item.id);
            return true;
          }     
          break;
      }
    }
  }

  saveItems(){
    let selectPlan = $$("treePlan").getSelectedId();
    let item = $$('treePlan').getItem(selectPlan);
    let parentId = $$("treePlan").getParentId(item.id);
    let parentItem = $$('treePlan').getItem(parentId);
  
    switch (item.$level) {
      case 4:  //PLAN

        if ( item.qid === "new" ) {
          let planForm = $$(item.id + "_form").getValues();
          let planMutation = webix.proxy(
            "GraphQL",`mutation createPlan($plan: PlanVO!){
            createPlan(plan: $plan){
              id
              alias
              description
              state
              planModeType
              planSubtype{id alias description}
            }
          }`);
          planMutation
          .load({
            plan: {
              "alias": planForm.alias,
              "description": planForm.description,
              "planModeType": planForm.planModeType,
              "planSubtypeId": parentItem.qid
            }
          })
          .then(data => {
            item["qid"] = data.id;
            item["alias"] = data.alias;
            item["description"] = data.description; 
            item["planModeType"] = data.planModeType; 
            item["planSubtype"] = data.planSubtype;
            item["state"] = data.state;
            item["data"] = [];
            item["planOrders"] = [];
            $$('treePlan').updateItem(selectPlan, item);
            let item2 = $$('treePlan').getItem(item.id);
            removeGrid("planParent");
            
            $$("toolbar_tree").show();
            $$("b_remove").hide();
            $$("treePlan").data.each(function(obj){  
              if ( obj.id.toString() === item2.id.toString() ) {
               
                switch (obj.$level) {
                  case 4:  //PLAN
                    $$("toolbar_plan").show();
                    $$("b_edit").hide();
                    $$("b_save").hide();
                    $$("b_play").hide();
                    $$("b_stop").hide();
                    $$("b_pause").hide();
                    $$("b_remove").hide();
                    $$("b_add_order").hide();
          
                    let plan = new Plan(null,obj);
                    $$("b_edit").show();
                    $$("b_save").hide();
                    $$("b_play").show();
                    $$("b_stop").show();
                    $$("b_pause").show();
                    $$("b_remove").show();
                    $$("b_add_order").show();
                    plan.editForm("planParent",plan.data);
                  break;
                }
              }
            });
          });
        } else {
          let planForm = $$(item.qid + "_form").getValues();
          let planMutation = webix.proxy(
            "GraphQL",`mutation updatePlan($plan: PlanVO!){
              updatePlan(plan: $plan) {
                id
                alias
                description
                planModeType
                state
              }
            }`);
          planMutation
          .load({
            plan: {
              "id": item.qid,
              "alias": planForm.alias,
              "description": planForm.description,
              "planModeType": planForm.planModeType,
              "planSubtypeId": parentItem.qid
            }
          })
          .then(data => {
            if (data) {
              item["qid"] = data.id;
              item["alias"] = data.alias;
              item["description"] = data.description; 
              item["planModeType"] = data.planModeType; 
              
              $$('treePlan').updateItem(selectPlan, item);
            }
          });
        }
        break;

      case 3:
        if(item.qid =="new"){
          let planMutation = webix.proxy(
            "GraphQL",`mutation createPlanSubtype($planType: PlanSubtypeVO!) {
              createPlanSubtype(planSubtype: $planType) {
                id
                alias
                description
                planType {
                  id
                }
              }
            }
            `);
          // loads the data
          planMutation
            .load({
              planType: {
                "alias": $$("new_form").elements.alias.getValue(),
                "description": $$("new_form").elements.description.getValue(),
                "planTypeId": parentItem.qid
              }
            })
            .then(data => {
              item["qid"] = data.id;
              item["alias"] = data.alias;
              item["description"] = data.description; 
              $$('treePlan').updateItem(selectPlan, item);
            });
        }else{
          let planMutation = webix.proxy(
            "GraphQL",`mutation updatePlanSubtype($planType: PlanSubtypeVO!) {
              updatePlanSubtype(planSubtype: $planType) {
                id
                alias
                description
                planType {
                  id
                }
              }
            }`);
          // loads the data
          planMutation
            .load({
              planType: {
                "id": item.qid,
                "alias": $$(item.qid + "_form").elements.alias.getValue(),
                "description": $$(item.qid + "_form").elements.description.getValue(),
                "planTypeId": parentItem.qid
              }
            })
            .then(data => {
              item["qid"] = data.id;
              item["alias"] = data.alias;
              item["description"] = data.description; 
              $$('treePlan').updateItem(selectPlan, item);
            });
        }                          
        break;
      case 2:
        if(item.qid =="new"){
          let planMutation = webix.proxy(
            "GraphQL",`mutation  createPlanType($planType: PlanTypeVO!){
              createPlanType(planType: $planType){
                id
                alias
                description
              }
            }`);
          // loads the data
          planMutation
            .load({
              planType: {
                "alias": $$("new_form").elements.alias.getValue(),
                "description": $$("new_form").elements.description.getValue() 
              }
            })
            .then(data => {
              item["qid"] = data.id;
              item["alias"] = data.alias;
              item["description"] = data.description; 
              $$('treePlan').updateItem(selectPlan, item);
            });
        }else{
          let planMutation = webix.proxy(
            "GraphQL",`mutation  updatePlanType($planType: PlanTypeVO!){
              updatePlanType(planType: $planType){
                id
                alias
                description
              }
            }`);
          // loads the data
          planMutation
            .load({
              planType: {
                "id": item.qid,
                "alias": $$(item.qid + "_form").elements.alias.getValue(),
                "description": $$(item.qid + "_form").elements.description.getValue() 
              }
            })
            .then(data => {
              item["qid"] = data.id;
              item["alias"] = data.alias;
              item["description"] = data.description; 
              $$('treePlan').updateItem(selectPlan, item);
            });
        }
        break;
      case 1:
        break;
      default:
        break;
    }
  }

  sendPlan(){
    let selectPlan = $$("treePlan").getSelectedId();
    let item = $$('treePlan').getItem(selectPlan);
    let plan = new Plan(null,item);
    plan.sendExecutionPlan();
  }

  getComponent() {
    if (this.data.includes("http") || this.data.includes("GraphQL")) {
      this.metadata.url = this.data;
    } else {
      this.metadata.data = this.data;
    }
    return this.metadata;
  }

  loadData() {

    let getPlantypes = webix.proxy(
      "GraphQL",`query  {
        getPlanTypes{
        id
        alias
        description
      }
    }`);
    getPlantypes.load()
    .then(data => {
      data.forEach(element => {
          this.planType.push(element);
        });
      });

    let getPlanSubtypes = webix.proxy(
      "GraphQL",`query {
        getPlanSubtypes{
          id
          alias
          description
          planType{
            id
            alias
            description
          }
        }
      }`);
    getPlanSubtypes.load()
    .then(data => {
      data.forEach(element => {
        this.planSubtype.push(element);
      });
    });

    this.data
      .load()
      .then(d => {
        d.forEach(element => {
          this.planes.push(element);
        });
        $$("treePlan").add({ alias:"\\", id: "1", open:true, qid: "0" }, 0);
        let count = 1;
        this.order.push("1");
        this.planType.forEach(element => {
          $$("treePlan").add({ id: "1." + count.toString(), qid: element.id, alias: element.alias, description: element.description }, 0, "1");
          this.order.push("1." + count.toString());
          count += 1;
        });
        let count1 = 1000;
        this.planSubtype.forEach(etype => {
          this.order.forEach(e => {
            if (etype.planType.id === $$("treePlan").data.pull[e].qid ) {
              let parentBranch = $$("treePlan").data.pull[e].id;
              $$("treePlan").add({ id: parentBranch + "." + count1.toString(), qid: etype.id, alias: etype.alias, description: etype.description }, 0, parentBranch );
              this.order.push(parentBranch + "." + count1.toString());
              count1 += 1;
            }
          });
          
        });

        let counter = 1;
        this.planes.map(element => {
          let planOrders = [];
          try {
            element.planOrders.map(order => { 
              planOrders.push(order);
            });
          } catch { console.log(""); }

          this.order.forEach(e => {
            try {
              if (element.planSubtype.id === $$("treePlan").data.pull[e].qid ) {
                let parentBranch = $$("treePlan").data.pull[e].id;
                $$("treePlan").add({ id: parentBranch + "." + counter.toString(), qid: element.id, alias: element.alias, description: element.description, state: element.state, planSubtype: element.planSubtype, planModeType: element.planModeType, planOrders: element.planOrders, data: planOrders },0, parentBranch );
                this.order.push(parentBranch + "." + counter.toString());
              }
            } catch {
              console.log("ERROR");
            }
          });
          counter +=1;
        });
        return d;
      });
  }

  /*
   *	Initializes everything
   */
  init() {
      this.loadData();
  }
}

function removeGrid(view){
  const hijos = $$(view).getChildViews();
  for(let i = hijos.length - 1; i>=0; i--){ 
    $$(view).removeView(hijos[i].config.id.toString());    
  }
}
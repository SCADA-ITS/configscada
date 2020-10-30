import Order from "./Order.js";

let optionPlanModeType = [
	{id:1, value:"AUTO"},
	{id:2, value:"SEMI"},
	{id:3, value:"MANUAL"}
];

const PLAN_STATE_TYPE = {
	 0: "UNKNOWN",
	 1: "STOPPED",
	 2: "RUNNING",
	 3: "SUCCESS",
   4: "FAILED"
};
 
const PLAN_ORDER_STATE_TYPE = {
  0: "UNKNOWN",
  1: "RUNNING",
  2: "SUCCESS",
  3: "FAILED"
};
 
export default class Plan {
  constructor(id, data) {
    this.data = data;
    if (id === null)
    {
      if ( data !== null ) {
        this.id = data.qid;
        this.alias = data.alias;
        this.description = data.description;
        this.state = data.state;
        this.planSubtype = data.planSubtype;
        this.planModeType = data.planModeType;
        this.planOrders = data.planOrders;
        this.planStateType = data.planStateType;
      }
    } else {
      this.id = id;
      this.alias = null;
      this.description = null;
      this.state = null;
      this.planSubtype = null;
      this.planModeType = null;
      this.planOrders = null;
      this.planStateType = null;
    }

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
    this.topic = "/topic/plan." + this.id;
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

		var graphElem = `query {
      getPlan(planId: "` + this.id + `") {
        id
        alias
        description
        planStateType{
          id
          alias
          description
          labelAlias
          labelDescription
        }
        planSubtype {
          id
          alias
          planType {
            id
            alias
          }
        }
        planModeType
        planOrders {
          id
          alias
          description
          planOrderValues {
            id
            value
            planOrderTypeParam {
              id
              alias
              description
            }
          }
          planOrderType {
            id
            alias
            description
            planOrderTypeParams {
              id
              alias
              description
            }
          }
          comandElement {
            id
            element {
              id
              alias
              elementType {
                id
                alias
              }
            }
            commandElementType {
              id
              alias
              description
              commandElementTypeParams {
                id
                alias
                description
              }
            }
            commandElementValues {
              id
              value
              commandElementTypeParam {
                id
                alias
                description
              }
            }
          }
        }
      }
    }`;
		
    this.graphql = webix.proxy("GraphQL", graphElem);
    this.header = {
      view: "template", //optional
      type: "header",
      id: "template2"+this.id,
      data: {
        value: ""
      },
      template: function (obj) {
        return obj.value;
      },
      css:{"text-align":"center !important"}
    };
    
    this.form = {
      view: "form",
      id: "planformresult_"+this.id,
      margin: 0,
      elements: [
        { view: "text", name: "id", label: $.i18n("plan.form.id"), disabled: true },
        { view: "text", name: "alias", label: $.i18n("plan.form.alias") },
        { view: "text", name: "description", label: $.i18n("plan.form.description") },
        { view: "text", name: "state", label: $.i18n("plan.form.state") },
        { view: "text", name: "planSubtype", label: $.i18n("plan.form.planSubtype") },
        { view: "combo", name: "planModeType", label: $.i18n("plan.form.planModeType"), options:optionPlanModeType},
      ]
    };
  }

  update_table(message) {
		try {
      let obj = JSON.parse(JSON.parse(message.body));
        //console.log(obj);
        obj.basicElementsVO.forEach((element) => {
          if(element.planStateType !== undefined)
            $$("planHeaderState" + element.id ).setValues({ value: element.planStateType.alias });
          
          /*
          if (element.planOrders !== undefined)
            element.planOrders.forEach(e => {
              $$("orderHeaderState" + e.planId).setValues({ value: e.state });
            });
          */
        });
    } catch (err) {
      console.log("Error: " + err.message);
    }
	}

  disconnect(){
    this.client.deactivate();
  }

  activate(){
    this.client.activate();
  }

  newForm(container,idForm){
    $$(container).addView({ view:"template",type:"header", autoheight:true, heigth: 150, select:true, id:idForm.toString() ,template: "<div>" + $.i18n("plan.form.new.title") + "</div>" },3);
    $$(container)
      .addView({
        view:"form", 
        id:idForm + "_form", 
        scroll:false, 
        elements:[{
          rows: [{
            cols:[
              { view:"text", id:idForm + "_alias",  name: "alias",  label: $.i18n("plan.form.alias"), labelAlign:"left", labelWidth: 125, inputWidth:0 },                        
            ]
          },{
            cols:[
              { view:"text", id:idForm + "_description",  name: "description",  label:$.i18n("plan.form.description"),  labelAlign:"left", labelWidth: 125, inputWidth:0 },
              { view:"combo",id:idForm + "_planModeType", name: "planModeType", label:$.i18n("plan.form.planModeType"), labelAlign:"left", options: optionPlanModeType, labelWidth: 200, inputWidth:0 }
            ]
          }]
        }]
    });
  }

  editForm(container,data1){
    $$(container).addView({
    id: "planHeader_"+this.id,
    cols:[
      {
        id: "planHeaderTitle"+this.id,
        view: "template",
        type: "header",
        data: {
          value: ""
        },
        template: "<div>" + this.alias + "</div>",
        heigth: 150,
        //css: { "text-align": "center !important" }
        gravity:5
      },

      {
        id: "planHeaderState"+this.id,
        view: "template",
        type: "header",
        data: {
          value: this.data.planStateType.alias
        },
        heigth: 150,
        template: function (obj) {
          return statusBadge(obj.value);
        },
        //css: { "text-align": "center !important" }
      }
    ]
    },3);


    //$$(container).addView({ view:"template",type:"header", autoheight:true, heigth: 150, select:true, id:this.id.toString() ,template: "<div>" + this.alias + statusBadge(this.state) + "</div>" },3);
    $$(container)
      .addView({
        view:"form", 
        id:this.id.toString() + "_form", 
        scroll:false, 
        elements:[{ responsive:this.id.toString() + "_form", 
          rows: [{ responsive:this.id.toString() + "_form", 
            cols:[
              { view:"text", id: this.id.toString() + "_alias",  disabled: true, name: "alias",  label:$.i18n("plan.form.alias"), value: this.alias,         labelAlign:"left", labelWidth: 125, inputWidth:0 },                        
              { view:"text", id: this.id.toString() + "_planId", disabled: true, name: "planId", label:$.i18n("plan.form.id"),    value: this.id.toString(), labelAlign:"left", labelWidth: 200, inputWidth:0 },
              { view:"text", id: this.id.toString() + "_state",  disabled: true, name: "state",  label:$.i18n("plan.form.state"), value: this.planStateType.alias,         labelAlign:"left", labelWidth: 200, inputWidth:0 }
            ]
          },{ responsive:this.id.toString() + "_form", 
            cols:[
              { view:"text", id: this.id.toString() + "_description",  disabled: true, name: "description",  label:$.i18n("plan.form.description"),  value: this.description,       labelAlign:"left", labelWidth: 125, inputWidth:0 },
              { view:"text", id: this.id.toString() + "_planSubtype",  disabled: true, name: "planSubtype",  label:$.i18n("plan.form.planSubtype"),  value: this.planSubtype.alias, labelAlign:"left", labelWidth: 200, inputWidth:0 },
              { view:"combo", id: this.id.toString() + "_planModeType", disabled: true, name: "planModeType", label:$.i18n("plan.form.planModeType"), options: optionPlanModeType, value: this.planModeType, labelAlign:"left", labelWidth: 200, inputWidth:0 }
            ]
          }]
        }]
    });
    let orderelements = [];
    let orderplanOrderType = [];
    //let neworder = new Order(null,"new",orderelements,orderplanOrderType,container);
    //neworder.new_dinamicFormOrder(container,this.id.toString());
    try {
      this.planOrders.forEach(e => {
        let order = new Order(null,e,orderelements,orderplanOrderType,container);
        order.dinamicFormOrder(container);
      });
    } catch {}
    
  };

  sendExecutionPlan(){
    let planMutation = webix.proxy(
      "GraphQL",
      `
      mutation  sendPlayPlan($planIds: [String!]!){
        sendPlayPlan(planIds: $planIds)  
      }
      `
    );
    // loads the data
    planMutation
      .load({
        planIds: [this.id]
      })
      .then(data => {
        // process the received data
        if (data === true) {
          webix.message(this.id + " " + $.i18n("plan.execution.correct"));
        } else {
          webix.message(this.id + " " + $.i18n("plan.execution.error"));
        }
      });
  }

  loadData() {
    this.graphql.load().then(d => {
      let datos = d;
      this.data = d;
      this.alias = datos.alias;
      this.description = datos.description;
      this.state = planStateType[datos.state];
      this.planSubtype = datos.planSubtype;
      this.planModeType = datos.planModeType;
      this.planOrders = datos.planOrders;
      const prueba = new Promise(function(resolve, reject){
        resolve();
      });
      webix.message(this.id + " Ejecutado Correctamente");
      webix.message(this.alias + " Ejecutado Correctamente");
      webix.message(this.description + " Ejecutado Correctamente");
      webix.message(this.planSubtype + " Ejecutado Correctamente");
      webix.message(this.planModeType + " Ejecutado Correctamente");
      webix.message(this.planOrders + " Ejecutado Correctamente");
    });
  }

  init() {
    this.loadData();
  }
}

function statusBadge(value) {
  //var cssStatusName = PLAN_STATE_TYPE[value].toLowerCase();
  //var html = "<div style='background-color: #ffdedb; color: #ff6455; display: flex; align-items: center; height: 100%; overflow: hidden; height: 17px; line-height: 17px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;'>"  + PLAN_STATE_TYPE[value] + "</div>";
  var html = "<div class=\"webix_um_badge_container\"><span class=\"webix_um_status_badge planschedule_state_" + value.toLowerCase() + "\">" + value + "</span></div>";
  //var html = "<span class=\"webix_um_status_badge planschedule_state_" + cssStatusName + "\">" + PLAN_STATE_TYPE[value] + "</span>";
  //var html = "<div class=\"webix_um_badge_container webix_um_status_badge planschedule_state_" + cssStatusName  + "\">" + PLAN_STATE_TYPE[value] + "</div>";
  return html;
}


/*

  height: 17px;
  line-height: 17px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  display: flex;
  align-items: center;
  height: 100%;
  overflow: hidden;

*/

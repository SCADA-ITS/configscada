let getElementTypes = webix.proxy(  "GraphQL", `query {
		getElementTypes{ 
			id, alias
		}
	}`);



let getElementType = webix.proxy("GraphQL", `query getElementType ($elementTypeId: String!){
			getElementType(elementTypeId: $elementTypeId){
				id
				elementTypeParams{
			   		id
			   		alias
				}
			}
		}`);

let getElements = webix.proxy("GraphQL", `query getElementsByType ($elementTypeId: String!){
		getElementsByType(elementTypeId:$elementTypeId){
		    id
		    alias
		    state
		    elementValues {
		      id
		      value
		      alerted
		      elementTypeParam{id alias}
		    }
		}
	}`);
  
function state_styling(value){
	
	  if (value == "0")
		  return { "background-color":"Gold" };
	    
	  if (value == "2")
		 return { "background-color":"RebeccaPurple" }; 
			  
	  if (value == "3")
		  return { "background-color":"LightCoral" };  
}

function value_styling(value){
	 	  
	  if (value.includes("!!"))
		  
		  return { "background-color":"LightCoral" };  
}

function build_table(){

	var elementTypeId = $$("combo_element_type").getValue();
	  
	  $$("tabla_element").clearAll();
	  $$("tabla_element").config.columns = [];
	  $$("tabla_element").config.columns.push({ id:"id",  header:["id", { content:"textFilter"}], adjust:true, sort:"text" });
	  $$("tabla_element").config.columns.push({ id:"alias",  header:["alias", { content:"textFilter"}], adjust:true, sort:"text", editor:"text" });
	  $$("tabla_element").config.columns.push({ id:"state",  header:["state", { content:"textFilter"}], adjust:true, sort:"text", cssFormat:state_styling});
	  $$("tabla_element").refreshColumns();
	  
	  getElementType.load({ 
		  elementTypeId: elementTypeId
	  }).then(data => {
		  
		  data.elementTypeParams.forEach(item => {
			  $$("tabla_element").config.columns.push(
					  { id:item.id,
						header: [item.alias,{ content:"textFilter"}],
						adjust:true,
						sort:"text",
						editor:"text",
						cssFormat:value_styling});
			  
			  $$("tabla_element").refreshColumns();
		  });
	 });

	  getElements.load({ 
		  elementTypeId: elementTypeId
	  }).then(data => {
		  
		  data.forEach(item => {
			  var row = {id: item.id, alias: item.alias, state: item.state};

			  if(item.elementValues) {
				  
		    	  item.elementValues.forEach(obj => {
		    		  var value = null;
		    		  
		    		  if(obj.alerted != null && obj.alerted) 
		    			  value = "!!" + obj.value + "!!";
		    		  else
		    			  value = obj.value;
		    				  
		    		  Reflect.set(row, obj.id, obj.elementTypeParam.id);
		    		  Reflect.set(row, obj.elementTypeParam.id, value);
  			  });	
			  }
	  
			  $$("tabla_element").add(row);
			  $$("tabla_element").sort({
				    as:"string",
				    dir:"asc",
				    by:"id"
				});
			  

		  });
	 });
}

function update_table(message){
     let obj = JSON.parse(JSON.parse(message.body));

     obj.basicElementsVO.forEach(element => {
       var item = $$("tabla_element").getItem(element.id);
    
       if(item != null) {
    	   
    	   if (element.state != null){
    		
               item["state"] = element.state;
    	   }
    	   
    	   if (element.elementValues != null){
    		  
    		   element.elementValues.forEach(elementValue => {
    			   
    			   if(elementValue.id != null && elementValue.value != null && item[elementValue.id] != null){

    				   var elementTypeParamId = item[elementValue.id];
    				   
    				   if(elementValue.alerted != null && elementValue.alerted){
    					  
    					   item[elementTypeParamId] = "!!" + elementValue.value + "!!";  
    					   
    				   }else{
    					   item[elementTypeParamId] = elementValue.value;
    				   }
    					   
    			   }
    		     });
    	   }
           $$("tabla_element").updateItem(element.id, item);
       }
     });
}


getElementTypes.load().then(data => { data.forEach(item => {
		$$("combo_element_type").getList().add({id:item.id, value:item.alias});
      });
});

// creates the stomp connection
let client = new StompJs.Client({
  brokerURL: webix.storage.session.get("url.ws.stomp"),
  connectHeaders: {
    login: "user",
    passcode: "user"
  },
  debug: function(str) {
    console.log(str);
  },
  reconnectDelay: 5000,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000
});

client.onConnect = function(frame) {
  // Do something, all subscribes must be done is this callback
  // This is needed because this will be executed after a (re)connect
  this.suscription = self.client.subscribe(
    "/topic/element.>",
    update_table
  );
};

client.onStompError = function(frame) {
    // Will be invoked in case of error encountered at Broker
    // Bad login/passcode typically will cause an error
    // Complaint brokers will set `message` header with a brief message. Body may contain details.
    // Compliant brokers will terminate the connection after any error
    console.log("Broker reported error: " + frame.headers["message"]);
    console.log("Additional details: " + frame.body);
    alert(`[error] ${error.message}`);
    };

    client.activate();


var comboElementType = {
  id:"combo_element_type",	
  view:"combo",
  autoConfig:true,
  label: "Element Type",
  labelWidth:160,
  labelAlign:"center",
  icon: "mdi mdi-arrow-down",
  tooltip:"Element Type #value#",
  options:[ ],
  on:{
	  onChange: build_table
    }
};

var tablaElement = {
	  id:"tabla_element",	
	  view:"datatable",
	  editable:true,
	  autoConfig:true,
	  columns:[],
	  data:[]
};

export let equipments = {
	rows:[comboElementType, tablaElement]
};


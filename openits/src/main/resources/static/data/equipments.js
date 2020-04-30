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

export let optionElementType = [
	{id:"ElementType:8", value:"BARRIER"},
	{id:"ElementType:9", value:"FAN"},
	{id:"ElementType:10", value:"ROTAPANEL"}
]

export let columnsElementType = {
		
	"ElementType:8":
		[
			{ id:"id", header:["id", { content:"textFilter"}], adjust:true, sort:"text" },
			{ id:"alias", header:["alias", { content:"textFilter"}], adjust:true, sort:"text", editor:"text" },
			{ id:"state", header:["state", { content:"textFilter"}], adjust:true, sort:"text", cssFormat:state_styling},
			{ id: "ElementTypeParam:8:3:1", header: ["bar_cmd",{ content:"textFilter"}], adjust: true, sort: "text", editor: "text", cssFormat:value_styling}
		],
	"ElementType:9":
		[
			{ id:"id", header:["id", { content:"textFilter"}], adjust:true, sort:"text" },
			{ id:"alias", header:["alias", { content:"textFilter"}], adjust:true, sort:"text", editor:"text" },
			{ id:"state", header:["state", { content:"textFilter"}], adjust:true, sort:"text", cssFormat:state_styling},
			{ id: "ElementTypeParam:9:3:1", header: ["fan_cmd",{ content:"textFilter"}], adjust: true, sort: "text", editor: "text", cssFormat:value_styling}
		],	
	"ElementType:10":
		[
			{ id:"id", header:["id", { content:"textFilter"}], adjust:true, sort:"text" },
			{ id:"alias", header:["alias", { content:"textFilter"}], adjust:true, sort:"text", editor:"text" },
			{ id:"state", header:["state", { content:"textFilter"}], adjust:true, sort:"text", cssFormat:state_styling},
			{ id: "ElementTypeParam:10:3:1", header: ["rotapanel_cmd", { content:"textFilter"}], adjust: true, sort: "text", editor: "text", cssFormat:value_styling},
			{ id: "ElementTypeParam:10:2:2", header: ["door_open", { content:"textFilter"}], adjust: true, sort: "text", editor: "text", cssFormat:value_styling}
		]	
}


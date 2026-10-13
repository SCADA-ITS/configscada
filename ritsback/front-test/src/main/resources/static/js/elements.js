$(document).ready(function () {

	var paint_all = true;
	
	$("#lblStatusOnline").hide();
	$("#lblStatusOffline").show();
	$("#lblStatusConnecting").hide();
	$("#lblStatusRx").hide();
	
	var stateUnknownIcon = function(value, data, cell, row, options){ 
        return "<img class='infoImage' src='../img/stateUnknown.png'>";
	};
	
	var stateUpIcon = function(value, data, cell, row, options){ 
        return "<img class='infoImage' src='../img/stateUp.png'>";
	};
	
	var stateDownIcon = function(value, data, cell, row, options){ 
        return "<img class='infoImage' src='../img/stateDown.png'>";
	};
	
	var stateAlarmIcon = function(value, data, cell, row, options){ 
        return "<img class='infoImage' src='../img/stateAlarm.png'>";
	};
	
	$("#tblWStreamSubscriptor").tabulator(
	{
		// data : data.OUTPUT,
		height : $(document).height() - 145,
		layout : "fitColumns", // fit columns to width of table

		// (optional)
		pagination : "local", // enable local pagination
		paginationSize : 50,
		movableColumns : true,
		placeholder:"No data available",
		tooltips : function(cell) {
			return cell.getColumn().getDefinition().title + " = "
					+ cell.getValue();
		},
		
		columns : [ // Define Table Columns
		{
			title : "CREATION TIME",
			field : "creationTime",
			width: 200
		}, {
			title : "LAST UPDATE",
			field : "lastUpdate",
			width: 200
		}, {
			title : "OPER.",
			field : "lastOperation",
			width: 75
		}, {
			title : "ID",
			field : "id",
			width: 100
		}, {
			title : "STATE",
			field : "stateLabel",
			width: 200
		}, {
			field : "state",
			width: 20,
			formatter:function(cell, formatterParams) {
		     
				var value = cell.getValue();
			    
				if (value == 0){
					
					return stateUnknownIcon;
			    } else if (value == 1){
					
					return stateUpIcon;
			    } else if (value == 2){
									
					return stateDownIcon;
			    } else if (value == 3){
					
					return stateAlarmIcon;
				} else{
			    	
			    	return null;
			    }
			}
		}, {
			title : "ALIAS",
			field : "alias",
			width: 200
		}, {
			title : "DESCRIPTION",
			field : "description",
			width: 500
		}],
		rowClick : function(e, row) { // trigger an alert message

		},
		rowUpdated:function(row) {

			if (row.getData().alerted == true) {

				row.getElement().css({backgroundColor: '#d2ffde', color: '#2d2d2d'})
			    .animate({backgroundColor: '#2d2d2d', color: 'red'}, 600);
			}
			else {
			
				row.getElement().css({backgroundColor: '#d2ffde', color: '#2d2d2d'})
			    .animate({backgroundColor: '#2d2d2d', color: '#90ee90'}, 600);
			}
	    },
	    rowAdded:function(row) {
	    	
    		row.getElement().css({backgroundColor: '#c1ffff', color: '#2d2d2d'})
			    .animate({backgroundColor: '#2d2d2d', color: '#add8e6'}, 600);
			
	    },
	    dataLoaded:function(data){
	    	
	    	var rows = $("#tblWStreamSubscriptor").tabulator("getRows");
	    	
	    	rows.forEach(function(row) {
	    		row.getElement().css({backgroundColor: '#c1ffff', color: '#2d2d2d'})
			    .animate({backgroundColor: '#2d2d2d', color: '#add8e6'}, 600);
	    	});
	    }
	});
	
	// Trigger setFilter function with correct parameters
	function str_contains(str, substr) {
		var str_low = "";
		var substr_low = "";

		if (str === undefined || substr === undefined) {
			return false;
		} else {
			str_low = String(str).toLowerCase();
			substr_low = String(substr).toLowerCase();

			return str_low.indexOf(substr_low) >= 0;
		}

	}

	function customFilter(data, filterParams) {

		return str_contains(data.id, filterParams.value)
			|| str_contains(data.alias, filterParams.value)
			|| str_contains(data.description, filterParams.value);
	}

	function updateFilter() {
		var filterVal = $("#elements-list-filter-value").val();
		$("#tblWStreamSubscriptor").tabulator("setFilter", customFilter, {
			value : filterVal
		});
	}

	// Update filters on value change
	$("#elements-list-filter-value").keyup(updateFilter);

	// Clear filters on "Clear Filters" button click
	$("#elements-list-filter-clear").click(function() {
		$("#elements-list-filter-value").val("");

		$("#tblWStreamSubscriptor").tabulator("clearFilter");
	});
	
	function getElements() {

		if (paint_all) {
			
			paint_all = false;
			
			$("#tblWStreamSubscriptor").tabulator("clearData");
		}
		
		$("#lblStatusOnline").hide();
		$("#lblStatusOffline").hide();
		$("#lblStatusConnecting").hide();
		$("#lblStatusRx").show();
		
		$.ajax({url: gConfig.graphql_url,
            contentType: "application/json",type:'POST',
            data: JSON.stringify({ query:`{
											  getElements{
											    id
											    alias
											    description
											    state
											    stateLabel
											  }
											}`
            }),
            success: function(result) {

            	console.log(JSON.stringify(result));
               
               var rowDataAdd = [];
               var tsTime;
               
               $.each(result.data.getElements, function(index, item) {
            	   
            	   var row = {};
            	   
            	   row.id = item.id;
            	   row.alias = item.alias;
            	   row.description = item.description;
                   row.state = item.state;
                   row.stateLabel = item.stateLabel;
    	
                   row.lastOperation = "NEW";
                   tsTime = new Date();
                   
                   row.lastUpdate = ("0" + tsTime.getDate()).slice(-2) + "/" + ("0"+(tsTime.getMonth()+1)).slice(-2) + "/" +
                   						tsTime.getFullYear() + " " + ("0" + tsTime.getHours()).slice(-2) + ":" + ("0" + tsTime.getMinutes()).slice(-2) + ":" + ("0" + tsTime.getSeconds()).slice(-2) + "," + ("0" + tsTime.getMilliseconds()).slice(-3);
                   
                   row.creationTime = row.lastUpdate;

                   rowDataAdd.push(row);
					
               });
	
               if (rowDataAdd.length > 0) {
    				
            	   $("#tblWStreamSubscriptor").tabulator("setData", rowDataAdd);
               }
               
               $("#lblStatusOnline").show();
			   $("#lblStatusOffline").hide();
			   $("#lblStatusConnecting").hide();
			   $("#lblStatusRx").hide();
             }
         });
	}
		
	function connect() {
		
		if (window.WebSocket) {
			$("#lblStatusOnline").hide();
			$("#lblStatusOffline").hide();
			$("#lblStatusConnecting").show();
			$("#lblStatusRx").hide();
			
			var user = null;
			var password = null;
			//var url = "ws://192.168.88.162:61614";
			var url = gConfig.stomp_url;
//			url += '?session-id=1583143697372';
//			url += '&user-id=1';
			var client = Stomp.client(url);
			
			
			
			client.connect(user, password, onConnect, onError, onClose);
						
			client.debug = null;
				    
		    function onConnect() {
		        // called back after the client is connected and authenticated to the STOMP server
		    	
		    	var subscription = client.subscribe("/topic/element.>", onMessage);
		    	
		    	console.log(subscription);
		    	
		    	// Use this for Apache Artemis 2.x
		    	//var subscription = client.subscribe("/topic/element.*", onMessage);
		    	
		    	$("#lblStatusOnline").show();
				$("#lblStatusOffline").hide();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").hide();
				
				getElements();
		    }
		    
		    function onError(error) {
		    	// display the error's message header:
		    	//console.log(error.headers.message);
		    	
		    	paint_all = true;
				$("#lblStatusOnline").hide();
				$("#lblStatusOffline").show();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").hide();
				
				    
				$("#tblWStreamSubscriptor").tabulator("clearData");
				
				console.log('Socket is closed. Reconnect will be attempted in 5 second.');
			    setTimeout(function() {
			      connect();
			    }, 5000);
		    }
		    
		    function onClose(error) {
		    	// display the error's message header:
		    	//console.log(error.headers.message);
		    	
		    	paint_all = true;
				$("#lblStatusOnline").hide();
				$("#lblStatusOffline").show();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").hide();
				
				    
				$("#tblWStreamSubscriptor").tabulator("clearData");
				
				console.log('Socket is closed. Reconnect will be attempted in 5 second.');
			    setTimeout(function() {
			      connect();
			    }, 5000);
		    }
		    	    
		    function onMessage(message) {
		    	
		    	if (message.body) {
		    		//console.log(message.body)
		    	} else {
		    		console.log("got empty message");
		    	}
		    	
		    	$("#lblStatusOnline").hide();
				$("#lblStatusOffline").hide();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").show();
				
				var jsonObj = {};
				var basicElement = {};
				jsonObj = JSON.parse(JSON.parse(message.body));
				basicElement = jsonObj.basicElementsVO[0];
				
				console.log(jsonObj);
				
				if (basicElement.state !== undefined) {

					var listItems;
	
					var rowDataUpdate = [];
					
					listItems = $("#tblWStreamSubscriptor").tabulator("getData");
					
					var itemToUpdate = {};
									
					var row = {};
					var tsTime;
					
					row.id = basicElement.id;
					row.state = basicElement.state;
		    		row.stateLabel = basicElement.stateLabel;
					
		    		row.lastOperation = "UPDATE";
					tsTime = new Date();
					row.lastUpdate = ("0" + tsTime.getDate()).slice(-2) + "/" + ("0"+(tsTime.getMonth()+1)).slice(-2) + "/" +
								tsTime.getFullYear() + " " + ("0" + tsTime.getHours()).slice(-2) + ":" + ("0" + tsTime.getMinutes()).slice(-2) + ":" + ("0" + tsTime.getSeconds()).slice(-2) + "," + ("0" + tsTime.getMilliseconds()).slice(-3);
					
					rowDataUpdate.push(row);
	
					if (rowDataUpdate.length > 0) {
						
						$("#tblWStreamSubscriptor").tabulator("updateData", rowDataUpdate);
					}
					
					
					$("#lblStatusOnline").show();
					$("#lblStatusOffline").hide();
					$("#lblStatusConnecting").hide();
					$("#lblStatusRx").hide();
				}
		    }
		}
		else {
			alert("WebSocket not supported by this browser");
		}
	}
	
	connect();
	
});


$(document).ready(function () {

	var paint_all = true;
	
	$("#lblStatusOnline").hide();
	$("#lblStatusOffline").show();
	$("#lblStatusConnecting").hide();
	$("#lblStatusRx").hide();
	
	var alertedIcon = function(value, data, cell, row, options){ 
        return "<img class='infoImage' src='../img/alerted.png'>";
	};
	
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
			width: 150
		}, {
			title : "LAST UPDATE",
			field : "lastUpdate",
			width: 150
		}, {
			title : "LAST OPERATION",
			field : "lastOperation",
			width: 100
		}, {
			title : "ELEMENT ID",
			field : "elementId",
			width: 150
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
			title : "ID",
			field : "id",
			cellClick:function(e, cell) {
				
//				window.open("./crossroad.html?id=" + cell.getRow().getData().id, "id="+cell.getRow().getData().id,
//					"height=700,width=800,toolbar=no,directories=no,status=no, linemenubar=no,scrollbars=no,resizable=no ,modal=yes");
			
			},
			width: 150
		}, {
			title : "VALUE",
			field : "value",
			width: 100,
			align: "right"
		}
//		}, {
//			title : "VALUE",
//			field : "value",
//			width: 200, 
//			formatter:function(cell, formatterParams) {
//			     
//				var value = cell.getValue();
//			    
//				if(cell.getRow().getData().alerted === true){
//					return "<span style='color:red; font-weight:bold;'>" + value + "</span>";
//			    }else{
//			    	return "<span style='color:#03f4eb; font-weight:bold;'>" + value + "</span>";
//			    }
//			}
		, {
			field : "alerted",
			width: 30,
			formatter:function(cell, formatterParams) {
		     
				var value = cell.getValue();
			    
				if(value == true){
					
					//return "<img style='height:16px; width:16px;cursor:pointer;' src='../img/alerted.png'>";
					
					return alertedIcon;
			    }else{
			    	
			    	return null;
			    }
			}
		}],
		rowClick : function(e, row) { // trigger an alert message

		},
		rowUpdated:function(row) {

			if (row.getData().alerted == true) {

				row.getElement().css({backgroundColor: '#d2ffde', color: 'black'})
			    .animate({backgroundColor: 'black', color: 'red'}, 600);
			}
			else {
			
				row.getElement().css({backgroundColor: '#d2ffde', color: 'black'})
			    .animate({backgroundColor: 'black', color: '#40de1a'}, 600);
			}
	    },
	    rowAdded:function(row) {
	    	
    		row.getElement().css({backgroundColor: '#c1ffff', color: 'black'})
			    .animate({backgroundColor: 'black', color: '#03f4eb'}, 600);
			
	    },
	    dataLoaded:function(data){
	    	
	    	var rows = $("#tblWStreamSubscriptor").tabulator("getRows");
	    	
	    	rows.forEach(function(row) {
	    		row.getElement().css({backgroundColor: '#c1ffff', color: 'black'})
			    .animate({backgroundColor: 'black', color: '#03f4eb'}, 600);
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
				|| str_contains(data.elementId, filterParams.value)
				|| str_contains(data.description, filterParams.value);
	}

	function updateFilter() {
		var filterVal = $("#measures-list-filter-value").val();
		$("#tblWStreamSubscriptor").tabulator("setFilter", customFilter, {
			value : filterVal
		});
	}

	// Update filters on value change
	$("#measures-list-filter-value").keyup(updateFilter);

	// Clear filters on "Clear Filters" button click
	$("#measures-list-filter-clear").click(function() {
		$("#measures-list-filter-value").val("");

		$("#tblWStreamSubscriptor").tabulator("clearFilter");
	});
		
	function connect() {
		
		if (window.WebSocket) {
			$("#lblStatusOnline").hide();
			$("#lblStatusOffline").hide();
			$("#lblStatusConnecting").show();
			$("#lblStatusRx").hide();
			
			var user = null;
			var password = null;
			//var url = "ws://192.168.88.162:61614";
			var url = "ws://192.168.244.12:61614";
			var client = Stomp.client(url);
			
			client.connect(user, password, onConnect, onError, onClose);
			
			client.debug = null;
				    
		    function onConnect() {
		        // called back after the client is connected and authenticated to the STOMP server
		    	
		    	var subscription = client.subscribe("/topic/element.>", onMessage);
		    	
		    	// Use this for Apache Artemis 2.x
		    	//var subscription = client.subscribe("/topic/element.*", onMessage);
		    	
		    	$("#lblStatusOnline").show();
				$("#lblStatusOffline").hide();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").hide();
		    };
		    
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
		    };
		    
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
		    };
		    
		    function updateState(data) {
		    	
		    	var rowDataUpdate = [];
		    	listItems = $("#tblWStreamSubscriptor").tabulator("getData");
		    	
		    	$.each(listItems, function(index, itm) {
		    				    		
		    		if (data.id == itm.elementId) {
		    			
		    			var row = {};
		    		
		    			row = itm;
		    			row.state = data.state;
			    		row.stateLabel = data.stateLabel;
			    		
			    		rowDataUpdate.push(row);
		    		}
				});
		    	
		    	$("#tblWStreamSubscriptor").tabulator("updateData", rowDataUpdate);
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
				var listItems;
				var found = false;

				var rowDataUpdate = [];
				var rowDataAdd = [];
				
				if (paint_all) {
					
					paint_all = false;
					
					$("#tblWStreamSubscriptor").tabulator("clearData");
				}
				
				listItems = $("#tblWStreamSubscriptor").tabulator("getData");
				
				var itemToUpdate = {};
				var operation = 'NEW';			
				var itemToUpdate = {};
				
				if (basicElement.stateLabel != null) {
					
					updateState(basicElement);
				}
				
				$.each(listItems, function(index, itm) {
					
					$.each(basicElement.elementValues, function(index, item) {
					
						if (itm.id == item.id) {
							
							itemToUpdate = itm;
							operation = 'UPDATE';

							return false; // break
						}
					});
				});
				
				$.each(basicElement.elementValues, function(index, item) {
					
					var row = {};
									
					if (operation == 'UPDATE') {
					
						row.id = item.id;
						row.elementId = basicElement.id;
						row.value = item.value;
						row.alerted = item.alerted;
						row.lastOperation = operation;
						row.creationTime = itemToUpdate.creationTime;
						var tsTime = new Date();
						row.lastUpdate = ("0" + tsTime.getDate()).slice(-2) + "/" + ("0"+(tsTime.getMonth()+1)).slice(-2) + "/" +
									tsTime.getFullYear() + " " + ("0" + tsTime.getHours()).slice(-2) + ":" + ("0" + tsTime.getMinutes()).slice(-2) + ":" + ("0" + tsTime.getSeconds()).slice(-2) + "," + ("0" + tsTime.getMilliseconds()).slice(-3);
						
						rowDataUpdate.push(row);
					}
					else if (operation == 'NEW') {
						
						row.id = item.id;
						row.elementId = basicElement.id;
						row.value = item.value;
						row.alerted = item.alerted;
						row.lastOperation = operation;
						var tsTime = new Date();
						row.creationTime = ("0" + tsTime.getDate()).slice(-2) + "/" + ("0"+(tsTime.getMonth()+1)).slice(-2) + "/" +
									tsTime.getFullYear() + " " + ("0" + tsTime.getHours()).slice(-2) + ":" + ("0" + tsTime.getMinutes()).slice(-2) + ":" + ("0" + tsTime.getSeconds()).slice(-2) + "," + ("0" + tsTime.getMilliseconds()).slice(-3);
						
						rowDataAdd = listItems;
						
						rowDataAdd.push(row);
					}
				});
				
				if (rowDataAdd.length > 0) {
				
					$("#tblWStreamSubscriptor").tabulator("setData", rowDataAdd);
				}
				if (rowDataUpdate.length > 0) {
					
					$("#tblWStreamSubscriptor").tabulator("updateData", rowDataUpdate);
				}
				
				
				$("#lblStatusOnline").show();
				$("#lblStatusOffline").hide();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").hide();
		    }
		}
		else {
			alert("WebSocket not supported by this browser");
		}
	}
		
	connect();
	
});


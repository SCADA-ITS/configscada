$(document).ready(function () {

	var paint_all = true;
	
	$("#lblStatusOnline").hide();
	$("#lblStatusOffline").show();
	$("#lblStatusConnecting").hide();
	$("#lblStatusRx").hide();
	
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
			width: 160
		}, {
			title : "LAST UPDATE",
			field : "lastUpdate",
			width: 160
		}, {
			title : "OPER",
			field : "lastOperation",
			width: 75
		}, {
			title : "ID",
			field : "id",
			cellClick:function(e, cell) {
				
//				window.open("./crossroad.html?id=" + cell.getRow().getData().id, "id="+cell.getRow().getData().id,
//					"height=700,width=800,toolbar=no,directories=no,status=no, linemenubar=no,scrollbars=no,resizable=no ,modal=yes");
			
			},
			width: 150
		}, {
			title : "ALARM_TYPE",
			field : "alarmType",
			width: 100
		}, {
			title : "SRC. ID",
			field : "sourceElementId",
			width: 100
		}, {
			title : "TGT. ID",
			field : "targetElementId",
			width: 100
		}, {
			title : "ALARM_CFG_ID",
			field : "alarmConfigId",
			width: 125
		}, {
			title : "ALARM",
			field : "alarmConfig",
			width: 300
		}, {
			title : "ACTIVATION",
			field : "activation",
			width: 160
		}, {
			title : "RECOGNIZED",
			field : "recognized",
			width: 160
		}, {
			title : "DEACTIVATION",
			field : "deactivation",
			width: 160
		}, {
			title : "MUTED_UNTIL",
			field : "mutedUntil",
			width: 150
		}],
		rowClick : function(e, row) { // trigger an alert message

		},
		rowUpdated:function(row) {
		
			row.getElement().css({backgroundColor: '#d2ffde', color: '#2d2d2d'})
		    .animate({backgroundColor: '#2d2d2d', color: '#90ee90'}, 600);
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
	
	function getLocalTimeStr(tsTime) {
	
		return ("0" + tsTime.getDate()).slice(-2) + "/" + ("0"+(tsTime.getMonth()+1)).slice(-2) + "/" +
				tsTime.getFullYear() + " " + ("0" + tsTime.getHours()).slice(-2) + ":" + ("0" + tsTime.getMinutes()).slice(-2) + ":" + ("0" + tsTime.getSeconds()).slice(-2) + "," + ("0" + tsTime.getMilliseconds()).slice(-3);
	}

	function customFilter(data, filterParams) {
		
		return str_contains(data.id, filterParams.value)
				|| str_contains(data.alias, filterParams.value)
				|| str_contains(data.targetElementId, filterParams.value)
				|| str_contains(data.alarmConfigId, filterParams.value)
				|| str_contains(data.alarmConfig, filterParams.value);
	}

	function updateFilter() {
		var filterVal = $("#alarms-list-filter-value").val();
		$("#tblWStreamSubscriptor").tabulator("setFilter", customFilter, {
			value : filterVal
		});
	}

	// Update filters on value change
	$("#alarms-list-filter-value").keyup(updateFilter);

	// Clear filters on "Clear Filters" button click
	$("#alarms-list-filter-clear").click(function() {
		$("#alarms-list-filter-value").val("");

		$("#tblWStreamSubscriptor").tabulator("clearFilter");
	});
	
	$("#test-mutation").click(function() {
		var settings = {
				  "async": true,
				  "crossDomain": true,
				  "url": "http://192.168.88.205:8082/graphql",
				  "method": "POST",
				  "headers": {
				    "content-type": "application/json"
				  },
				  "data": "{\"query\":\"mutation  sendMeasures($elementValues: [ElementValueVO!]!){\\n  sendMeasures(elementValues: $elementValues)   {\\n      id\\n      value\\n      elementTypeParam{\\n        id\\n        alias\\n      }\\n  }\\n}\\n\\n\",\"variables\":{\"elementValues\":[{\"id\":\"ElementValue:33:1:101:3\",\"value\":\"71\"},{\"id\":\"ElementValue:33:1:102:3\",\"value\":\"20\"},{\"id\":\"ElementValue:33:1:103:3\",\"value\":\"30\"},{\"id\":\"ElementValue:33:2:101:3\",\"value\":\"40\"},{\"id\":\"ElementValue:33:2:102:3\",\"value\":\"50\"},{\"id\":\"ElementValue:33:2:103:3\",\"value\":\"60\"}]},\"operationName\":\"sendMeasures\"}"
				}

				$.ajax(settings).done(function (response) {
				  console.log(response);
				});
	});
	
	function getAlarms() {
		
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
											  getAlarms {
													    id,
													    alarmConfig {
													      id,
													      alias,
													      alarmType
													    }
													    recognized,
													    recognizedComments,
													    activation,
													    activationComments,
													    deactivation,
													    deactivationComments,
													    mutedUntil,
													    sourceElement {
													     id
													    }
													    targetElement {
													     id
													    }
													  }
											}`
            }),
            success: function(result) {

            	console.log(JSON.stringify(result));
               
            	var rowData = [];
            	var tsTime;
               
            	if (result != null) {
            		
            		$.each(result.data.getAlarms, function(idxAlarm, alarm) {
                		
                		var row = {};
            			
            			if (alarm.sourceElement != null) {
            			
            				row.sourceElementId = alarm.sourceElement.id;
            			}
            			
            			if (alarm.targetElement != null) {
            			
            				row.targetElementId = alarm.targetElement.id;
            			}
            			
            			row.id = alarm.id;
            			row.alarmType = alarm.alarmConfig.alarmType;
            			row.alarmConfigId = alarm.alarmConfig.id;
            			row.alarmConfig = alarm.alarmConfig.alias;
            			
            			if (alarm.activation != null) {
            				
            				var alarmActivation = new Date(alarm.activation);
            				row.activation = getLocalTimeStr(alarmActivation);
            			}
            			
            			if (alarm.deactivation != null) {
            				
            				var alarmDeactivation = new Date(alarm.deactivation);
            				row.deactivation = getLocalTimeStr(alarmDeactivation);
            			}
            			
            			if (alarm.recognized != null) {
            				
            				var alarmRecognized = new Date(alarm.recognized);
            				row.recognized = getLocalTimeStr(alarmRecognized);
            			}
            			
            			if (alarm.mutedUntil != null) {
            				
            				var alarmMutedUntil = new Date(alarm.mutedUntil);
            				row.mutedUntil = getLocalTimeStr(alarmMutedUntil);
            			}
            			            			
            			row.lastOperation = "NEW";
                		tsTime = new Date();
                       
                		row.lastUpdate = getLocalTimeStr(tsTime);
                       
                		row.creationTime = row.lastUpdate;

                		rowData.push(row);
                	});
    	
                	if (rowData.length > 0) {
        				
                		$("#tblWStreamSubscriptor").tabulator("setData", rowData);
                	}
                }
                
                $("#lblStatusOnline").show();
				$("#lblStatusOffline").hide();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").hide();
            }
         });
	}
	
	function getAlarm(alarmId) {
		
		$.ajax({url: gConfig.graphql_url,
            contentType: "application/json",type:'POST',
            data: JSON.stringify({ query:`{
											  getAlarm (alarmId:"${alarmId}"){
													    id,
													    alarmConfig {
													      id,
													      alias,
													      alarmType
													    }
													    recognized,
													    recognizedComments,
													    activation,
													    activationComments,
													    deactivation,
													    deactivationComments,
													    mutedUntil,
													    sourceElement {
													     id
													    }
													    targetElement {
													     id
													    }
													  }
											}`
            }),
            success: function(result) {

            	
            	
            	if (result != null) {
            	
            		var operation = 'NEW';			
    				var rowToUpdate = null;
    				
    				var rows = $("#tblWStreamSubscriptor").tabulator("getRows");
    				
    				var alarm = result.data.getAlarm;

    				if (alarm != null) {
    				
    					var rowData = [];
    					
    					$.each(rows, function(index, row) {

        					var item = row.getData();
        					
        					if (alarm.id == item.id) {
        						
        						rowToUpdate = row;
        						operation = 'UPDATE';
        					}
        					else {
        						
        						rowData.push(item);
        					}
        					
        				});
        				
                    	console.log(JSON.stringify(result));
                       
                    	var tsTime;
                    	
                    	
                		var row = {};
                		
                		if (alarm.sourceElement != null) {
                			
            				row.sourceElementId = alarm.sourceElement.id;
            			}
            			
            			if (alarm.targetElement != null) {
            			
            				row.targetElementId = alarm.targetElement.id;
            			}
            			
            			row.id = alarm.id;
            			row.alarmType = alarm.alarmConfig.alarmType;
            			row.alarmConfigId = alarm.alarmConfig.id;
            			row.alarmConfig = alarm.alarmConfig.alias;
            			row.activation = null;
            			row.deactivation = null;
            			row.recognized = null;
            			row.mutedUntil = null;
            			
            			if (alarm.activation != null) {
            				
            				var alarmActivation = new Date(alarm.activation);
            				row.activation = getLocalTimeStr(alarmActivation);
            			}
            			
            			if (alarm.deactivation != null) {
            				
            				var alarmDeactivation = new Date(alarm.deactivation);
            				row.deactivation = getLocalTimeStr(alarmDeactivation); 
            			}
            			
            			if (alarm.recognized != null) {
            				
            				var alarmRecognized = new Date(alarm.recognized);
            				row.recognized = getLocalTimeStr(alarmRecognized);
            			}
            			
            			if (alarm.mutedUntil != null) {
            				
            				var alarmMutedUntil = new Date(alarm.mutedUntil);
            				row.mutedUntil = getLocalTimeStr(alarmMutedUntil);
            			}
            			
            			if (alarm.deactivation != null && alarm.recognized != null) {
            				
            				operation = 'DELETED';
            			}
            			
            			row.lastOperation = operation;
                		tsTime = new Date();
                		               
                		row.lastUpdate = getLocalTimeStr(tsTime);
                       
                		if (operation === 'NEW') {
                			
                			row.creationTime = row.lastUpdate;	
                		}

                		rowData.push(row);
        	
                		if (operation === 'NEW') {
                			
                			$("#tblWStreamSubscriptor").tabulator("setData", rowData);
                		}
                		else if (operation === 'UPDATE') {
                    	
                			$("#tblWStreamSubscriptor").tabulator("updateData", rowData);
                    	}
                		else if (operation === 'DELETED') {
                			
                			if (rowToUpdate != null) {
                			
                				rowToUpdate.delete();
                			}
                		}
    				}
    				else {
    					
    					var row = {};
    					
    					row = $("#tblWStreamSubscriptor").tabulator("getRow", alarmId);
    					
    					if (row != null) {
    						
    						row.delete();
    					}
    				}
            	}
            	else {
            		
            		var row = {};
					
					row = $("#tblWStreamSubscriptor").tabulator("getRow", alarmId);
					
					if (row != null) {
						
						row.delete();
					}
            	}
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
			var url = gConfig.stomp_url;
			var client = Stomp.client(url);
			
			client.connect(user, password, onConnect, onError, onClose);
			
			client.debug = null;
				    
		    function onConnect() {
		        // called back after the client is connected and authenticated to the STOMP server
		    	
		    	var subscription = client.subscribe("/topic/alarm.>", onMessage);
		    	
		    	// Use this for Apache Artemis 2.x
		    	//var subscription = client.subscribe("/topic/element.*", onMessage);
		    	
		    	$("#lblStatusOnline").show();
				$("#lblStatusOffline").hide();
				$("#lblStatusConnecting").hide();
				$("#lblStatusRx").hide();
				
				getAlarms();
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
				basicElements = jsonObj.basicElementsVO;
				
				console.log(jsonObj);

				var rowDataUpdate = [];
								
				$.each(basicElements, function(idxAlarm, alarm) {
					
					var row = {};
					var tsTime;
					
        			row.id = alarm.id;
        			
        			getAlarm(row.id);
				});
				
				
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


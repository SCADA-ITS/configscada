if(window.app)
	window.app.login = {
		
		getView : function() {
			
			var view = {
				id: "sign-in",
				//css: "sign-in",
				rows: [
					{
					},
					{
						cols: [
							{},
							{
								paddingY: 50,
								paddingX: 60,
								width: 600,
								css: "sing-in-form",
								rows: [
									{
										data:{
									        userImage: "img/user_logo.png", 
											locked: false
									    },
										template: function(data) {
			
											var userData = webix.storage.session.get(SESSION_STORAGE_SESSION_DATA);
											var userName;
											var userTemplate;
											
											if (userData) {
												
												userName = userData.userName;
											}
											
											var userTemplate = "<img class='user-logo' src='" + data.userImage + "'/><br/>";
											
											if (userName != undefined) {
												
												userTemplate += userName;	
											}
											
											if (data.locked) {
												
												userTemplate += "<div class='locked'>" + i18n.t("LBL_LOCK_SCREEN") + "</div>";
											} 
											
											return userTemplate;
										},  
										id: "login-template",
										height: 120, 
										borderless: true
									},
									{
										view: "template",
										css: "sing-in-form-result",
										height: 40,
									    id: "login-result",
									    data:{
									        msg: null,
											allow: false
									    },
									    template:function(obj){
										
											if (!obj.msg) {
												return "<div><span></span></div>"	
											}
											else {
												if (obj.allow) {
													return "<div class='authorized'><span>" + obj.msg + "</span></div>"	
												}
												else {
													return "<div class='unauthorized'><span>" + obj.msg + "</span></div>"
												}
													
											}
									    }
									},
									{
										view: "form",
				                        id: "loginForm",
				                        elementsConfig: {
				                            bottomPadding: 18,
				                            labelWidth: 150
										},
										elements: [
											{},
											{
				                                view: "text",
												label: i18n.t("LBL_LOGIN_USER"),
				                                name: "user", 
				                                id: "login-user", 
				                                required: true,
												validate: webix.rules.isNotEmpty, invalidMessage: i18n.t("LBL_PLEASE_ENTER_USER"), validateEvent: "key"
				                            },
				                            { 
				                                view: "text", 
				                                type: "password", 
				                                label: i18n.t("LBL_LOGIN_PASSWORD"), 
				                                name: "password",
												id: "login-password", 
				                                required: true, 
				                                validate: webix.rules.isNotEmpty, invalidMessage: i18n.t("LBL_PLEASE_ENTER_PASSWORD"), validateEvent: "key"
				                            },
											{
												margin: 10,
				                                paddingX: 2,
				                                borderless: true,
												cols: [
													{},
													{
														view: "button", 
														type: "form", 
														label: i18n.t("LBL_LOGIN_SIGN_IN"), 
														width: 150, 
														height: 45,
														hotkey: "enter",
														click: function() {
															var form = $$('loginForm');
															
															if (form.validate()) {
																if (window.app.login.signIn(form.elements.user.data.value, form.elements.password.data.value)) {
																													
																}
															}
														} 
													},
													{}												
												]
											}
										]
			                        },
								]
							},
							{}
						]
					},
					{}
				]
			}; 
			
			return view;
		},
		init: function() {
			
			var userData = webix.storage.session.get(SESSION_STORAGE_SESSION_DATA);
			
			if (userData != null) {

				$$("loginForm").clear();				
				$$("login-user").config.text = userData.user;
				$$("login-user").config.value = userData.user;
				$$("login-user").hide();
				$$("login-template").setValues({userAlias: userData.user, locked: true}, true);
			}
			else {
				$$("loginForm").clear();
				$$("login-user").config.text = "";
				$$("login-user").config.value = "";
				$$("login-user").show();
				$$("login-template").setValues({userAlias: "", locked: false}, true);
			}
			
			//$$("login-result").hide();
		},
		signIn : function(user, password) {

			var sessionData = webix.storage.session.get(SESSION_STORAGE_SESSION_DATA);
			var userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);

			if (sessionData) {
				
				user = sessionData.userName;
			}
			
			const encodedString = btoa((user + ':' + password).toString('utf8')).toString('base64');
			let response = webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).sync().post(appConfig.restUrl + "auth/login");

			if (response != undefined) {
				
				let login = JSON.parse(response.responseText);
				
				$$("login-result").define("height", 30);
				$$("login-result").resize();	
				$$("login-result").show();
				
				if (login.access === "ALLOWED") {
					
					webix.storage.session.put(SESSION_STORAGE_SESSION_LOCKED, false);
					webix.storage.session.put(SESSION_STORAGE_SESSION_DATA, login);
					
					if (userData == undefined) {
					
						let userDataResponse = webix.ajax().sync().get(appConfig.restUrl + `users/${login.userId}`);
						
						if (userDataResponse != undefined) {
							
							userData = JSON.parse(userDataResponse.responseText);
							
							webix.storage.session.put(SESSION_STORAGE_USER_DATA, userData);
						}	
					}
					
					$$("login-result").setValues({msg: login.access, allow: true});
					
					webix.delay(function(){
					
						window.app.signIn();
						$$("login-result").setValues({msg: null, allow: true});	
					}, null, null, 1000);
				}
				else {
					
					$$("login-result").setValues({msg: login.access, allow: false});
					
					webix.delay(function(){
					
//						$$("login-result").define("height", 0);
//						$$("login-result").resize();	
//						$$("login-result").hide();

						$$("login-result").setValues({msg: null, allow: false});
					}, null, null, 5000);
				
					
				}
			}
			
			return true;
		} 
	}

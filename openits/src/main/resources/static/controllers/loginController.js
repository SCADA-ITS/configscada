import { CONFIG } from "./loginController_conf.js";

let uid = webix.uid();

let isValidating = false;

const LANGUAGE = navigator.language || navigator.userLanguage;

webix.ready(function() {

	webix.ui({
		css: { "background-image": "url(" + CONFIG.img.wallpaper + ")", "background-size": "100% 100%" },
		rows: [
			{},
			{
				cols: [
					{},
					{
						css: "rectangle",
						width: 1138,
						height: 394,
						cols: [
							{
								cols: [
									{ width: 80 },
									{
										width: 445,
										rows: [getLogos()]
									}
								]
							},
							{ width: 176 },
							{
								cols: [
									{
										width: 328,
										rows: [
											{ height: 77 },
											getForm(),
											{ height: 70 }
										]
									},
									{ width: 109 }
								]
							}
						]
					},
					{}
				]
			},
			{}
		]
	});

	showPassword();
	webix.UIManager.setFocus($$("user-" + uid));

});


function getLogos() {

	let logos;
	
	logos = {
		rows: [
			{ height: 112 },
			{
				css: "product-logo",
				template: `<img src='${CONFIG.img.product}'>`
			},
			{ height: 66 },
			{
				cols: [
					{
						css: "company-logo",
						template: `<img src='${CONFIG.img.company}'>`
					},
					{ width: 65, height: 94 },
					{
						css: "project-logo",
						template: `<img src='${CONFIG.img.project}'>`
					}
				]
			}
		]
	}

	return logos

}


function getForm() {

	let buttonText = translateLabel("LBL_LOGIN");
	let alertText = "";

	let loginForm = {
		id: "login-form-" + uid,
		view: "form",
		css: "form",
		elementsConfig: { inputWidth: 328 },
		elements: [
			{
				view: "text",
				id: "user-" + uid,
				name: "user",
				inputHeight: 54,
				css: "form-container",
				template: `	<div class="input-container">
							<div class="input-image" style="background-image: url(${CONFIG.img.user});"></div>   
							<input class="input-text input-text-user" type='text' name='user'>
							</div>`,
				on: {
					onEnter: function(ev) {

						webix.html.triggerEvent($$("btn-auth-" + uid).getInputNode(), "MouseEvents", "click");
					},
					onKeyPress: function(code, e) {
						$$("alertText-" + uid).config.label = "";
						$$("alertText-" + uid).refresh();
					}
				}
			},
			{
				height: 40,
				width: 328,
				css: { "margin": "0px !important" }
			},
			{
				view: "text",
				id: "password-" + uid,
				type: "password",
				name: "password",
				inputHeight: 54,
				keyPressTimeout: 1,
				css: "form-container",
				template: `	<div class="input-container">
							<div class="input-image" style="background-image: url(${CONFIG.img.lock});"></div>   
							<input id="password-input" class="input-text input-text-pass" type='password' name='password'>
							<div id="show-icon" class="input-image-show" style="display:none; background-image: url(${CONFIG.img.show});"></div>   
							</div>`,
				on: {
					onEnter: function(ev) {

						webix.html.triggerEvent($$("btn-auth-" + uid).getInputNode(), "MouseEvents", "click");
					},
					onKeyPress: function(code, e) {
												
						$$("alertText-" + uid).config.label = "";
						$$("alertText-" + uid).refresh();
					},
					onTimedKeyPress: function() {
						
						let icon = $$("login-form-" + uid).getNode().querySelector('#show-icon');
						
						icon.style.display = (this.getValue() != "") ? "block" : "none";
					}
				}
			},
			{
				id: "alertText-" + uid,
				view: "label",
				label: alertText,
				css: "alert-text"
			},
			{
				id: "btn-auth-" + uid,
				view: "button",
				value: buttonText,
				inputHeight: 54,
				css: "form-button",
				on: {
					onItemClick: function() {

						if (!isValidating) {
							
							let validation = $$(loginForm.id).validate();

							if (validation) {
								
								login($$(loginForm.id).getValues());
							}	
						}
					},
					onEnter: function(ev) {
						
						webix.html.triggerEvent(this.getInputNode(), "MouseEvents", "click");
					}
				}
			}
		],
		rules: {
			"user": webix.rules.isNotEmpty
		},
		on: {
			onValidationError: function(key, obj) {

				if (key == "user") {

					let messageTranslate = translateLabel("LBL_USER_CANT_BE_EMPTY");

					if (messageTranslate) {
						$$("alertText-" + uid).config.label = messageTranslate;
						$$("alertText-" + uid).refresh();
					}
				}
			}
		}
	}

	return loginForm;

}

function validating(inProcess) {
	
	isValidating = inProcess;
	
	let messageTranslate = translateLabel("LBL_USER_AUTHENTICATION");

	if (inProcess) {
		
		$$("alertText-" + uid).config.label = messageTranslate;
		webix.html.addCss($$("alertText-" + uid).getNode(), "alert-text-authorized");
		$$("alertText-" + uid).refresh();
		$$("btn-auth-" + uid).focus();
	}
	else {
		
		$$("login-form-" + uid).enable();
	}
}

function login(values) {

	let labelAlertMessage = "LBL_UNKNOWN_ACCESS_ERROR";
	
	try {

		validating(true);
		const encodedString = btoa((values.user + ':' + values.password).toString('utf8')).toString('base64');
		//let loginResponse = webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).sync().post(appConfig.restUrl + "auth/login");
		
		console.log("request");
		
		webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).post(appConfig.restUrl + "auth/login")
			.then(
				function(loginResponse) {
			
					if (loginResponse != undefined) {
			
						let loginData = loginResponse.json();
				
						if (loginData.access === LOGIN_MESSAGE.AUTHORIZED.id) {
							
							if (loginData) {
								
								access(loginData);							
							}
				
						} else {
							
							labelAlertMessage = LOGIN_MESSAGE[loginData.access].label;
				
							let translateMessage = translateLabel(labelAlertMessage);
				
							$$("alertText-" + uid).config.label = translateMessage;
							webix.html.removeCss($$("alertText-" + uid).getNode(), "alert-text-authorized");
							$$("alertText-" + uid).refresh();
				
							$$("password-" + uid).getNode().querySelector('#password-input').focus();
							
							validating(false);
						}
					}
				},
				function(loginError) {
				
					let translateMessage = translateLabel(labelAlertMessage);
				
					$$("alertText-" + uid).config.label = translateMessage;
					webix.html.removeCss($$("alertText-" + uid).getNode(), "alert-text-authorized");
					$$("alertText-" + uid).refresh();
					console.error(loginError);
					
					validating(false);
				}
			);
		
	
		
	}
	catch {
	
		validating(false);		
	}
}

function access(loginData) {
	
	let permissions = [];
	let userData = loginData.user;
	
	if (loginData && userData) {
					
		userData.password = undefined; // protect password

		if (userData.role && Array.isArray(userData.role.rolePermissions)) {
	
			userData.role.rolePermissions.forEach(item => {
	
				permissions.push({
					'id': item.id.split(":")[1],
					'alias': item.alias
				});
			});
		}
	
		if (Array.isArray(userData.userPermissions)) {
	
			userData.userPermissions.forEach(item => {
				permissions.push(item.id);
			});
	
		}
	
		if (Array.isArray(permissions)) {
			
			if (permissions.find(e => e.id == SYSTEM_PERMISSION_LOGIN)) {
			
				webix.storage.session.put(SESSION_STORAGE_SESSION_DATA, loginData);
				
				$$("alertText-" + uid).config.label = translateLabel("LBL_AUTHORIZED");
				webix.html.addCss($$("alertText-" + uid).getNode(), "alert-text-authorized");
				$$("alertText-" + uid).refresh();
				
				webix.delay(function() {
					window.location = "./home.html";
				}, null, null, 1000);	
			}
			else {
				
				$$("alertText-" + uid).config.label = translateLabel("LBL_UNAUTHORIZED");
				webix.html.removeCss($$("alertText-" + uid).getNode(), "alert-text-authorized");
				$$("alertText-" + uid).refresh();
				
				validating(false);
			}
		}
	}
}


function translateLabel(labelToTranslate) {

	let labelsArr = CONFIG.i18n.data;
	let labelTranslated = "¿¿¿" + labelToTranslate +"???";
	
	let label = labelsArr.find(e => e.label === labelToTranslate);

	if (label) {

		labelTranslated = (label[LANGUAGE] != undefined) ? label[LANGUAGE] : label["en-US"];
	}
	
	return labelTranslated;
}


function showPassword() {

	const INPUT_TYPE = {
		PASSWORD: "password",
		TEXT: "text"
	}

	let icon = $$("login-form-" + uid).getNode().querySelector('#show-icon');
	let password = $$("login-form-" + uid).getNode().querySelector('#password-input');

	icon.addEventListener("mousedown", show);
	icon.addEventListener("mouseup", show);

	function show() {

		if (password.type == INPUT_TYPE.PASSWORD) {

			password.type = INPUT_TYPE.TEXT;
			icon.style.backgroundImage = "url(" + CONFIG.img.show_light + ")";

		} else {

			password.type = INPUT_TYPE.PASSWORD;
			icon.style.backgroundImage = "url(" + CONFIG.img.show + ")";
		}
	}
}




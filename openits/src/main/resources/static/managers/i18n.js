const I18N_ENDPOINT_TRANSLATIONS = appConfig.restUrl + "i18n/translations";
const I18N_MODULE = 1;
const I18N_PREFIX = "LBL_";

function load(i18n, onLoad) {
			
	let translations;
	
	webix.ajax().get(`${I18N_ENDPOINT_TRANSLATIONS}/${i18n.localeId}/${I18N_MODULE}`).then(function(dat){
		
		if (dat) {
						
			translations = dat.json();
			
			if (translations != undefined && Array.isArray(translations)) {
				
				translations.forEach(e => {
					
					i18n.labels[e.label] = e.translation;
				});
			}
		}
		
		if (onLoad) {
			
			onLoad();
		}
	});
	
	webix.i18n.setLocale(i18n.localeCode);
	webix.Date.startOnMonday = START_ON_MONDAY;
}

export default class I18n {

	constructor(locale, onLoad) {
	
		this.localeId = locale.id;
		this.localeCode = locale.code.replace("_", "-");
		this.labels = {};
		
		load(this, onLoad);
	}
	
	getLocale() {
		
		return this.localeCode;
	}
	
	tPhrase(string) {

		let stringArr = string.split(" ");

		for (let i = 0; i < stringArr.length; i++) {

			if (stringArr[i].startsWith(I18N_PREFIX)) {

				stringArr[i] = this.t(stringArr[i]);

				if (i != 0) {

					stringArr[i] = stringArr[i].toLowerCase();
				}
			}
		}
		
		return stringArr.join(" ");
	}
	
	t(label, values) {
		
		let translation = undefined;
		let fields = undefined;
		
		try {
			
			if (values != undefined && Array.isArray(values)) {
				
				fields = values.join();
			}
			 
			if (this.localeId != undefined && label != undefined && label != "") {
				
				if (this.labels != undefined && !fields) {
					
					translation = this.labels[label];
				}
				
				if (translation == undefined ) {
					
					let translationUrl = `${I18N_ENDPOINT_TRANSLATIONS}/${this.localeId}/${I18N_MODULE}/${label}`;
					
					if (fields) {
						
						translationUrl = translationUrl + `/${fields}`;	
					}
						
					let translationResponse = webix.ajax().sync().get(translationUrl); 
								
					if (translationResponse && translationResponse.responseText) {
								
						let translationJson = JSON.parse(translationResponse.responseText);
	
						if (translationJson != undefined) {
							
							translation = translationJson.translation;
							
							if (!fields) {
							
								this.labels[label] = translation;	
							}
						}
					}	
				}				
			}
		}
		catch {
			
		}
		
		return (translation != undefined) ? translation : ((label != undefined) ? "¿¿" + label + "??" : "");
	}
}

const I18N_ENDPOINT_TRANSLATIONS = appConfig.restUrl + "i18n/translations";
const I18N_MODULE = 1;

function load(i18n) {
			
	let translations;
	
//	let translationsResponse = webix.ajax().sync().get(`${I18N_ENDPOINT_TRANSLATIONS}/${i18n.localeId}/${I18N_MODULE}`);
//	
//	if (translationsResponse && translationsResponse.responseText) {
//						
//		translations = JSON.parse(translationsResponse.responseText);
//		
//		if (translations != undefined && Array.isArray(translations)) {
//			
//			translations.forEach(e => {
//				
//				i18n.labels[e.label] = e.translation;
//			});
//		}
//	}
	
	webix.ajax().get(`${I18N_ENDPOINT_TRANSLATIONS}/${i18n.localeId}/${I18N_MODULE}`).then(function(dat){
		
		if (dat) {
						
			translations = dat.json();
			
			if (translations != undefined && Array.isArray(translations)) {
				
				translations.forEach(e => {
					
					i18n.labels[e.label] = e.translation;
				});
			}
		}
	});
	
	webix.i18n.setLocale(i18n.localeCode);
	webix.Date.startOnMonday = START_ON_MONDAY;
}

export default class I18n {

	constructor(locale) {
	
		this.localeId = locale.id;
		this.localeCode = locale.code.replace("_", "-");
		this.labels = {};
		
		load(this);
	}
	
	getLocale() {
		
		return this.localeCode;
	}
	
	t(label, values) {
		
		let translation;
		let fields = undefined;
		
		if(values != undefined && Array.isArray(values)){
			
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
		
		return (translation != undefined) ? translation : ((label != undefined) ? "¿¿" + label + "??" : "");
	}
}

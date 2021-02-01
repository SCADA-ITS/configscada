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
}

export default class I18n {

	constructor(localeId) {
	
		this.localeId = localeId;
		this.labels = {};
		
		load(this);
	}
	
	t(label) {
		
		let translation;
		 
		if (this.localeId != undefined && label != undefined && label != "") {
		
			if (this.labels != undefined) {
				
				translation = this.labels[label];
			}
			
			if (translation == undefined ) {
				
				let translationResponse = webix.ajax().sync().get(`${I18N_ENDPOINT_TRANSLATIONS}/${this.localeId}/${I18N_MODULE}/${label}`);
				
				if (translationResponse && translationResponse.responseText) {
							
					let translationJson = JSON.parse(translationResponse.responseText);

					if (translationJson != undefined) {
						
						translation = translationJson.translation;
						
						this.labels[label] = translation;
					}
				}
			}
		}
		
		return (translation != undefined) ? translation : ((label != undefined) ? "¿¿" + label + "??" : "");
	}
}

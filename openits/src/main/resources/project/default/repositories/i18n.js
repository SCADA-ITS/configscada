const I18N_LOCALE_ID_PREFIX = "I18nLocale";
//const I18N_ENDPOINT_LOCALES = appConfig.restUrl + "i18n/locales";
const I18N_ENDPOINT_TRANSLATIONS = appConfig.restUrl + "i18n/translations";
const I18N_MODULE = 1;

function load(i18n) {
			
	let translations;
	
	let translationsResponse = webix.ajax().sync().get(`${I18N_ENDPOINT_TRANSLATIONS}/${I18N_LOCALE_ID_PREFIX}:${i18n.localeId}/${I18N_MODULE}`);
		
	if (translationsResponse != undefined) {
						
		translations = JSON.parse(translationsResponse.responseText);
		
		if (translations != undefined && Array.isArray(translations)) {
			
			translations.forEach(label => {
				
				i18n.labels[label] = label.translation;
			});
		}
	}
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
				
				let translationResponse = webix.ajax().sync().get(`${I18N_ENDPOINT_TRANSLATIONS}/${I18N_LOCALE_ID_PREFIX}:${this.localeId}/${I18N_MODULE}/${label}`);
				
				if (translationResponse != undefined) {
							
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

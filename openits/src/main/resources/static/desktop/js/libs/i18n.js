const I18N_LOCALE_ID_PREFIX = "I18nLocale";
const i18n_endpoint_locales = appConfig.restUrl + "i18n/locales";
const i18n_endpoint_translations = appConfig.restUrl + "i18n/translations";

i18n = {
	
	labels: {},
	localeId: undefined,
	
	load: function(localeId) {
		
		let translations;
		
		i18n.localeId = localeId;
		let translationsResponse = webix.ajax().sync().get(`${i18n_endpoint_translations}/${I18N_LOCALE_ID_PREFIX}:${localeId}/${I18N_MODULE}`);
			
		if (translationsResponse != undefined) {
							
			translations = JSON.parse(translationsResponse.responseText);
			
			if (translations != undefined && Array.isArray(translations)) {
				
				translations.forEach(label => {
					
					i18n.labels[label] = label.translation;
				});
			}
		}
	},

	t: function(label) {
		
		let translation;
		 
		if (i18n.localeId != undefined && label != undefined && label != "") {
		
			if (i18n.labels != undefined) {
				
				translation = i18n.labels[label];
			}
			
			if (translation == undefined ) {
				
				let translationResponse = webix.ajax().sync().get(`${i18n_endpoint_translations}/${I18N_LOCALE_ID_PREFIX}:${i18n.localeId}/${I18N_MODULE}/${label}`);
				
				if (translationResponse != undefined) {
							
					let translationJson = JSON.parse(translationResponse.responseText);

					if (translationJson != undefined) {
						
						translation = translationJson.translation;
						
						i18n.labels[label] = translation;
					}
				};
			}
		}
		
		return (translation != undefined) ? translation : ((label != undefined) ? "¿¿" + label + "??" : "");

//		if (label != undefined && label != "") {
//		
//			return "¿¿" + label + "??";	
//		}
//		else {
//			
//			return "";
//		}
		
	}
}

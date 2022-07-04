const I18N_ENDPOINT_TRANSLATIONS = appConfig.restUrl + "i18n/translations";
const I18N_MODULE = 1;

function load(i18n, onLoad) {
			
	let translations;
	
	webix.ajax().get(`${I18N_ENDPOINT_TRANSLATIONS}/${i18n.localeId}/${I18N_MODULE}`).then(
		function(dat){
		
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
		},
		function(err) {
			
			console.log(err);
			
			if (onLoad) {
				
				onLoad();
			}
		}
	);
	
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

	t(txt, values) {

		let self = this;
		let lblTranslated = "";
		const regEx = /LBL_[a-zA-Z0-9_]*/gm;
		
		if (txt != undefined && txt.trim().includes(" ") && !values) {

			lblTranslated = translateText(txt);

		} else {

			lblTranslated = translateLabel(txt, values);
		}

		function translateText(txt) {

			let regExRes;
			let items = [];
			let txtRes = txt;

			while ((regExRes = regEx.exec(txt)) !== null) {

				items.push({
					label: regExRes[0],
					translate: translateLabel(regExRes[0])
				});
			}

			for (let item of items) {

				txtRes = txtRes.replace(item.label, item.translate.toLowerCase());
			}

			return txtRes;
		}

		function translateLabel(label, values) {

			let translation = undefined;
			let fields = undefined;

			try {
				
				if (regEx.exec(label) == null) {
	
					return label;
				}

				if (values != undefined && Array.isArray(values)) {

					fields = values.join();
				}

				if (self.localeId != undefined && label != undefined && label != "") {

					if (self.labels != undefined && !fields) {

						translation = self.labels[label];
					}

					if (translation == undefined) {

						let translationUrl = `${I18N_ENDPOINT_TRANSLATIONS}/${self.localeId}/${I18N_MODULE}/${label}`;

						if (fields) {

							translationUrl = translationUrl + `/${fields}`;
						}

						let translationResponse = webix.ajax().sync().get(translationUrl);

						if (translationResponse && translationResponse.responseText) {

							let translationJson = JSON.parse(translationResponse.responseText);

							if (translationJson != undefined) {

								translation = translationJson.translation;

								if (!fields) {

									self.labels[label] = translation;
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

		return lblTranslated;

	}
}

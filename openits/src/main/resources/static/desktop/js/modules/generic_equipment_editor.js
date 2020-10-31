function getGenericEquipmentEditorView(module, instance, args) {

	let moduleInstance = "module-" + module.id + "-" + instance;
	let view;
	
	if (module.moduleType.id == MODULE_TYPE_CUSTOM_WINDOW) {
	
		view = {
			//layout: moduleInstance,
			css: "custom_window",
			cols: [
				{ width: 6 },
				{
					rows: [
						{ height: 6 },
						{
							view: "toolbar",
							elements: [
								{ width: 4 },
								{
									view: "search",
									id: "txtSearchEquipment-" + moduleInstance,
									placeholder: i18n.t("LBL_EQUIPMENT_SEARCH")
								},
								{ width: 4 }
							]
						},
						{},
						{ height: 6 }
					]
				},
				{ view: "resizer" },
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		}
		
		app.wins.setWindowTitle(module, instance, args[0]);
	}

	return view;
}

function assingGenericEquipmentEditorCallbacks(module, instance, args) {

	let moduleInstance = "module-" + module.id + "-" + instance;
}

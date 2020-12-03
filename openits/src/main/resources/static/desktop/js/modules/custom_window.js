export default class CustomWindow {

	constructor(view, module, instance, args) {

		this.module = module;
		this.instance = instance;
		this.args = args;
		this.moduleInstance = "module-" + this.module.id + "-" + this.instance;

		// for using the object inside callbacks
		self = this;

		try {
			
			const lazy = async _ => {

			    const mod = await import(self.module.jsFile);
			    const lazyClass = new mod.default(self.module, self.instance, self.args);

				webix.ui(lazyClass.getView(), view);
				lazyClass.loadData();
			}
			
			lazy();
			
		} catch (error) {
			
			console.log(error);
		}
	}
}

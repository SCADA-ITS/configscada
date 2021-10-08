import GenericModule from "./genericModule.js";
import {util} from "../lib/util.js";

const NO_VIDEO_IMAGE = './img/no-video.jpg';

export default class CamTest1 extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.module = module;
		this.mouleAction = moduleAction;
		this.args = args;

		this.uid = webix.uid();
	}

	getView() {

		let view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: false
								},
								{
									id: "loading-" + this.uid,
									rows: [getViewDrawLoading()],
									hidden: true
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};


		function getViewDrawLoading() {

			return {
				view: "template",
				template: function() {

					let icon = "<img src='./img/loading.svg' align='center'>";

					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

		return view;
	}

	loadData() {

		let self = this;
		let view = {};

		$$("loading-" + self.uid).show();

		view = {
			id: self.uid,
			rows: [
				{},
				{
					cols: [
						{
							id: "cam-1" + self.uid,
							width: 600,
							height: 300,
							cols: [getContent()]
						},
						{
							id: "cam-2" + self.uid,
							width: 600,
							height: 300,
							cols: [getContent()]
						},
						{
							id: "cam-3" + self.uid,
							width: 600,
							height: 300,
							cols: [getContent()]
						}
					]
				},
				{},
				{
					cols: [
						{
							id: "cam-4" + self.uid,
							width: 600,
							height: 300,
							cols: [getContent()]
						},
						{
							id: "cam-5" + self.uid,
							width: 600,
							height: 300,
							cols: [getContent()]
						},
						{
							id: "cam-6" + self.uid,
							width: 600,
							height: 300,
							cols: [getContent()]
						}
					]
				},
				{}
			]
		}


		function getContent() {

			let camImg = {
				template: `<img align='center' style="width:100%; height: 100%;" src="${NO_VIDEO_IMAGE}">`
			};

			return camImg;
		}

		$$("loading-" + self.uid).hide();
		$$(self.uid).show();
		
		util.ui.setTitle(self.args.containerId, "LBL_CAM_TEST");

		webix.ui(view, $$(self.uid));
	}

}
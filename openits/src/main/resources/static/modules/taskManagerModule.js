import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import ListView from "../component/listview.js";
import DataTable from "../component/datatable.js";

const MB_CORRECTION_FACTOR = 1 / (1024 * 1024);

export default class TaskManagerModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.processes = undefined;
		this.table = undefined;
		this.list = undefined;
		this.maintenanceInterval = undefined;
		this.selectedUpdateTime = undefined;
		this.dataMap = new Map();
		this.optionMap = new Map();
		this.firstCharge = true;
	}


	getView() {

		let view;

		view = {
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
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [util.ui.getViewDrawLoading()]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		return view;
	}

	loadData() {

		let self = this;

		webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.processesInfo).then(function(response) {

			if (response != null) {

				self.processes = response.json();

				let timeToInit = new Date();
				let initHour = timeToInit.getHours().toString();
				let initMinute = timeToInit.getMinutes().toString();
				let initTime = initHour.padStart(2, "0") + ":" + initMinute.padStart(2, "0");
				let optionsArr = ["cpuLoad", "cpuAverage", "heapUsed", "heapSize", "heapMax", "numRestarts"];

				for (let p of self.processes) {

					self.dataMap.set(
						p.pid,
						[{
							time: initTime,
							cpuLoad: p.cpuLoad.toFixed(2),
							cpuAverage: p.cpuAverage.toFixed(2),
							heapUsed: (p.heapUsed * MB_CORRECTION_FACTOR).toFixed(2),
							heapMax: (p.heapMax * MB_CORRECTION_FACTOR).toFixed(2),
							heapSize: (p.heapSize * MB_CORRECTION_FACTOR).toFixed(2),
							numRestarts: p.numRestarts
						}]
					);
				}

				for (let option of optionsArr) {

					let d = {};
					d["xValue"] = initTime;

					for (let p of self.processes) {

						let newData = p[option];

						if (option === "cpuLoad" || option === "cpuAverage") {

							newData = newData.toFixed(2);
						}

						if (option === "heapMax" || option === "heapSize" || option === "heapUsed") {

							newData = (newData * MB_CORRECTION_FACTOR).toFixed(2);
						}

						d["pid" + p.pid] = newData;
					}

					self.optionMap.set(option, [d]);
				}

				drawComponents();

				if (self.table) {

					self.table.callbacks();
				}

				if (self.list) {

					self.list.selectByIndex(0);
					self.firstCharge = false;
				}

				initMaintenance();
			}
		});


		function drawComponents() {

			let viewMain = {};

			viewMain = {
				id: "view-main-" + self.uid,
				rows: [
					getHeader(),
					getTabs(),
					getSlider()
				],
				on: {
					onDestruct: function() {

						clearInterval(self.maintenanceInterval);
					}
				}
			};

			$$("loading-" + self.uid).hide();
			webix.ui(viewMain, $$(self.uid));


			function getHeader() {

				let header = {};

				header = {
					id: "header-" + self.uid,
					cols: [
						{
							view: "label",
							label: "<img src='img/menu/task-menu.png' class='grid-icon'/>",
							height: 75,
							css: "global-header-icon"
						},
						{
							view: "label",
							height: 75,
							id: "label-bottom-" + self.uid,
							template: "<span>" + i18n.t("LBL_TASK_MANAGER") + "</span> <br> <span style='font-size: 13px'>" + i18n.t("LBL_TASK_MANAGER_DESCRIPTION") + "</span>",
							css: "global-header-label"
						}
					]
				};

				return header;
			}


			function getTabs() {

				let tabs = {};

				tabs = {
					view: "tabview",
					id: "tabs-" + self.uid,
					borderless: true,
					css: "secondary-tab",
					tabbar: {
						tabMargin: 6
					},
					cells: [
						{
							header: i18n.t("LBL_PERFORMANCE"),
							body: getChart()
						},
						{
							header: i18n.t("LBL_PROCESSES"),
							body: getDataTable()
						}
					]
				};


				function getChart() {

					let chart = {};
					let data = {};
					let dataSet = [];
					let dataSeries = [];

					let listElements = [{
						id: -1,
						alias: i18n.t("LBL_ALL"),
						description: i18n.t("LBL_ALL")
					}];

					let options = [
						{ id: "cpuLoad", value: "cpuLoad (%)" },
						{ id: "cpuAverage", value: "cpuAverage (%)" },
						{ id: "heapUsed", value: "heapUsed (MB)" },
						{ id: "heapSize", value: "heapSize (MB)" },
						{ id: "heapMax", value: "heapMax (MB)" },
						{ id: "numRestarts", value: "Restarts" }
					];

					if (Array.isArray(self.processes) && self.processes.length > 0) {

						let timeToInit = new Date();
						let initHour = timeToInit.getHours().toString();
						let initMinute = timeToInit.getMinutes().toString();
						let initTime = initHour.padStart(2, "0") + ":" + initMinute.padStart(2, "0");

						for (let p of self.processes) {

							let mainArr = p.main.split(' ')[0];
							let processName = mainArr.slice(0, -4);
							let pid = p.pid;
							let color = (Math.random() * 0xFFFFFF << 0).toString(16).padStart(6, '0');

							listElements.push({
								id: pid,
								alias: processName,
								description: p.main,
								color: '#' + color
							});

							data['pid' + pid] = p.cpuLoad.toFixed(2);
							data['xValue'] = initTime;

							dataSeries.push(
								{
									id: pid + self.uid,
									value: '#pid' + pid + '#',
									item: {
										borderColor: "#" + color,
										color: "#ffffff"
									},
									line: {
										color: "#" + color,
										width: 2
									},
									tooltip: {
										template: processName + " - " + '#pid' + pid + '#',
									}
								}
							);
						}

						dataSet.push(data);
					}

					chart = {
						id: "chart-view-" + self.uid,
						css: { "border": " 1px solid #dadee0", "box-sizing": " border-box" },
						cols: [
							{
								width: 300,
								rows: [
									{
										id: "richselect-" + self.uid,
										view: "richselect",
										value: "cpuLoad",
										options: options,
										on: {
											onChange: function(obj) {

												$$("chart-" + self.uid).clearAll();

												let selectedItemId = self.list.getSelectedItems()[0].id;

												if (self.dataMap.has(selectedItemId)) {

													let newData = [];
													let dataSerie = self.dataMap.get(selectedItemId);

													dataSerie.forEach(dat => {

														let newObj = {};

														newObj['pid' + selectedItemId] = dat[obj];
														newObj.xValue = dat.time
														newData.push(newObj);
													});

													$$("chart-" + self.uid).define("data", newData);
												}

												if (selectedItemId === -1 && !self.firstCharge) {

													let newData = [];

													if (self.optionMap.has(obj)) {

														newData = self.optionMap.get(obj);
													}

													$$("chart-" + self.uid).define("data", newData);
												}

												$$("chart-" + self.uid).refresh();
											}
										}
									},
									getList()
								]
							},
							{
								id: "chart-" + self.uid,
								view: "chart",
								type: "line",
								value: "#yValue#",
								cellWidth: 50,
								dynamic: true,
								item: {
									borderColor: "#1293f8",
									color: "#ffffff"
								},
								line: {
									color: "#1293f8",
									width: 3
								},
								xAxis: {
									template: "#xValue#"
								},
								yAxis: {},
								series: dataSeries,
								data: dataSet
							}
						]
					};


					function getList() {

						let listview = {};
						let listEvents = {};

						listEvents[ListView.EventTypes.ON_SELECT_ROW] = function() {

							let optionSelected = $$("richselect-" + self.uid).getValue();
							let processSelected = self.list.getSelectedItems()[0];
							let newData = [];

							if (self.dataMap.has(processSelected.id)) {

								$$("chart-" + self.uid).clearAll();

								let dataSerie = self.dataMap.get(processSelected.id);

								dataSerie.forEach(dat => {

									let d = {};
									d["pid" + processSelected.id] = dat[optionSelected];
									d["xValue"] = dat.time;

									newData.push(d);
								});
							}

							if (processSelected.id === -1 && !self.firstCharge) {

								$$("chart-" + self.uid).clearAll();

								let selectedOption = $$("richselect-" + self.uid).getValue();

								if (self.optionMap.has(selectedOption)) {

									newData = self.optionMap.get(selectedOption);
								}
							}

							$$("chart-" + self.uid).define("data", newData);
							$$("chart-" + self.uid).refresh();
						}

						let listOptions = {};

						listOptions[ListView.Options.MODE] = ListView.DrawMode.COMPACT;
						listOptions[ListView.Options.SEARCH] = false;

						self.list = new ListView(listElements, listOptions, listEvents);
						listview = self.list.getView();

						return listview;
					}

					return chart;
				}


				function getDataTable() {

					let dataTable = {};

					dataTable = {
						id: "dt-" + self.uid,
						css: "bordered-layout-without-border-top",
						rows: getTable()
					};

					function getTable() {

						let data = [];
						let timeService = window.sm.getService(Services.TIME_SERVICE);
						let dat = new Date(timeService.getEpoch());

						if (Array.isArray(self.processes) && self.processes.length > 0) {

							for (let p of self.processes) {

								let mainArr = p.main.split(' ')[0];
								let processName = mainArr.slice(0, -4);

								let status = (Math.floor(dat) - Math.floor(p.updatedAt) > 15000) ? "LBL_DOWN" : "LBL_UP";

								data.push(
									{
										id: p.pid,
										pid: p.pid,
										process: processName,
										status: status,
										cpuLoad: p.cpuLoad.toFixed(2),
										cpuAv: p.cpuAverage.toFixed(2),
										heapUsed: (p.heapUsed * MB_CORRECTION_FACTOR).toFixed(2),
										heapSize: (p.heapSize * MB_CORRECTION_FACTOR).toFixed(2),
										heapMax: (p.heapMax * MB_CORRECTION_FACTOR).toFixed(2),
										restarts: p.numRestarts,
										since: p.updatedAt,
										commandLine: p.commandLine
									}
								);
							}
						}

						const defaultGridOptionValues = [
							{ gridOption: { id: 1 }, value: "false" },
							{ gridOption: { id: 2 }, value: "true" },
							{ gridOption: { id: 4 }, value: "asc" },
							{ gridOption: { id: 7 }, value: "true" },
							{ gridOption: { id: 8 }, value: "false" },
							{ gridOption: { id: 9 }, value: "true" },
							{ gridOption: { id: 11 }, value: "true" },
							{ gridOption: { id: 12 }, value: "true" },
							{ gridOption: { id: 13 }, value: "true" },
							{ gridOption: { id: 14 }, value: "false" },
							{ gridOption: { id: 16 }, value: "img/menu/taskmanager.png" },
							{ gridOption: { id: 21 }, value: "custom" },
							{ gridOption: { id: 23 }, value: "true" },
							{ gridOption: { id: 25 }, value: "true" }
						];

						let metadata = {
							filter: true,
							label: "LBL_PROCESSES",
							visible: true,
							gridOptionValues: defaultGridOptionValues,
							gridFields: [
								{
									id: 1,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: true,
									needsTranslation: false,
									parameterized: false,
									position: 1,
									required: true,
									sourceField: "pid",
									visible: false,
									gridFieldDataType: {
										id: 1,
										name: "string"
									}
								},
								{
									id: 1,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: true,
									label: "LBL_PID",
									labelTooltip: "LBL_PID",
									needsTranslation: false,
									parameterized: false,
									position: 1,
									required: true,
									sourceField: "pid",
									visible: true,
									widthPx: 95,
									gridFieldDataType: {
										id: 1,
										name: "string"
									}
								},
								{
									id: 2,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: true,
									label: "LBL_PROCESSES",
									labelTooltip: "LBL_PROCESSES",
									needsTranslation: false,
									parameterized: false,
									position: 2,
									required: true,
									sourceField: "process",
									visible: true,
									widthPx: 250,
									gridFieldDataType: {
										id: 1,
										name: "string"
									}
								},
								{
									id: 3,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_STATUS",
									labelTooltip: "LBL_STATUS",
									needsTranslation: true,
									parameterized: false,
									position: 3,
									required: true,
									sourceField: "status",
									visible: true,
									widthPx: 65,
									gridFieldIcons: [
										{
											id: 1,
											avatar: false,
											badgeColor: false,
											enabled: true,
											icon: "./img/state/stateDown.png",
											showValue: false,
											value: "LBL_DOWN",
											labelValue: "LBL_DOWN"
										},
										{
											id: 2,
											avatar: false,
											badgeColor: false,
											enabled: true,
											icon: "./img/state/stateUp.png",
											showValue: false,
											value: "LBL_UP",
											labelValue: "LBL_UP"
										}
									],
									gridFieldDataType: {
										id: 1,
										name: "string"
									}
								},
								{
									id: 4,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_CPU_LOAD",
									labelTooltip: "LBL_CPU_LOAD",
									needsTranslation: false,
									parameterized: false,
									position: 4,
									required: true,
									sourceField: "cpuLoad",
									widthPx: 150,
									visible: true,
									gridFieldDataType: {
										id: 3,
										name: "decimal"
									}
								},
								{
									id: 5,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_CPU_AVERAGE",
									labelTooltip: "LBL_CPU_AVERAGE",
									needsTranslation: false,
									parameterized: false,
									position: 5,
									required: true,
									sourceField: "cpuAv",
									visible: true,
									widthPx: 150,
									gridFieldDataType: {
										id: 3,
										name: "decimal"
									}
								},
								{
									id: 6,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_HEAP_USED",
									labelTooltip: "LBL_HEAP_USED",
									needsTranslation: false,
									parameterized: false,
									position: 6,
									required: true,
									sourceField: "heapUsed",
									visible: true,
									widthPx: 150,
									gridFieldDataType: {
										id: 3,
										name: "decimal"
									}
								},
								{
									id: 7,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_HEAP_SIZE",
									labelTooltip: "LBL_HEAP_SIZE",
									needsTranslation: false,
									parameterized: false,
									position: 7,
									required: true,
									sourceField: "heapSize",
									visible: true,
									widthPx: 150,
									gridFieldDataType: {
										id: 3,
										name: "decimal"
									}
								},
								{
									id: 8,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_HEAP_MAX",
									labelTooltip: "LBL_HEAP_MAX",
									needsTranslation: false,
									parameterized: false,
									position: 8,
									required: true,
									sourceField: "heapMax",
									visible: true,
									widthPx: 150,
									gridFieldDataType: {
										id: 3,
										name: "decimal"
									}
								},
								{
									id: 9,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_RESTARTS",
									labelTooltip: "LBL_RESTARTS",
									needsTranslation: false,
									parameterized: false,
									position: 9,
									required: true,
									sourceField: "restarts",
									visible: true,
									widthPx: 120,
									gridFieldDataType: {
										id: 2,
										name: "int"
									}
								},
								{
									id: 10,
									extended: false,
									filtered: false,
									isEpochMilli: true,
									isPk: false,
									label: "LBL_SINCE",
									labelTooltip: "LBL_SINCE",
									needsTranslation: false,
									parameterized: false,
									position: 9,
									required: true,
									sourceField: "since",
									visible: true,
									widthPx: 200,
									gridFieldDataType: {
										id: 4,
										name: "date"
									}
								},
								{
									id: 11,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_COMMAND_LINE",
									labelTooltip: "LBL_COMMAND_LINE",
									needsTranslation: false,
									parameterized: false,
									position: 11,
									required: true,
									sourceField: "commandLine",
									visible: true,
									widthPx: 300,
									gridFieldDataType: {
										id: 1,
										name: "string"
									}
								}
							],
							gridCommands: [
								{
									id: 1,
									default_: true,
									icon: "mdi mdi-close-octagon",
									label: "LBL_RESTART_PROCESS",
									multiselect: false,
									position: 1,
									showText: true,
									itemRequired: true
								}
							]
						};

						let events = {};
						events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: 1,
							function: (id) => {

								let selectedItems = self.table.getSelectedItems();
							}
						});

						self.table = new DataTable(null, null, metadata, data, events);

						return self.table.getView();
					}

					return dataTable;
				}

				return tabs;
			}


			function getSlider() {

				let slider = {};

				slider = {
					cols: [
						{},
						{
							id: "slider-" + self.uid,
							view: "slider",
							value: self.config.defaultUpdateTime,
							label: i18n.t("LBL_UPDATE_TIME"),
							labelWidth: 175,
							gravity: 0.5,
							min: 5,
							max: 30,
							step: 5,
							title: webix.template("#value# secs"),
							on: {
								onChange: function(newv) {

									self.selectedUpdateTime = newv;
									initMaintenance();
								}
							}
						}
					]
				};

				return slider;
			}
		}

		function initMaintenance() {

			clearInterval(self.maintenanceInterval);

			self.maintenanceInterval = setInterval(function() {

				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.processesInfo).then(function(response) {

					if (response != null) {

						self.processes = response.json();

						let optionSelected = $$("richselect-" + self.uid).getValue();
						let processSelected = self.list.getSelectedItems();
						let data = [];
						let dataChart = {};
						let timeService = window.sm.getService(Services.TIME_SERVICE);
						let dat = new Date(timeService.getEpoch());
						let timeToInit = new Date();
						let initHour = timeToInit.getHours().toString();
						let initMinute = timeToInit.getMinutes().toString();
						let initTime = initHour.padStart(2, "0") + ":" + initMinute.padStart(2, "0");

						if (Array.isArray(self.processes) && self.processes.length > 0) {

							for (let p of self.processes) {

								let pid = p.pid;

								if (self.dataMap.has(pid)) {

									self.dataMap.get(pid).push({
										time: initTime,
										cpuLoad: p.cpuLoad.toFixed(2),
										cpuAverage: p.cpuAverage.toFixed(2),
										heapUsed: (p.heapUsed * MB_CORRECTION_FACTOR).toFixed(2),
										heapMax: (p.heapMax * MB_CORRECTION_FACTOR).toFixed(2),
										heapSize: (p.heapSize * MB_CORRECTION_FACTOR).toFixed(2),
										numRestarts: p.numRestarts
									});
								}

								let status = (Math.floor(dat) - Math.floor(p.updatedAt) > 15000) ? "LBL_DOWN" : "LBL_UP";
								let mainArr = p.main.split(' ')[0];
								let processName = mainArr.slice(0, -4);

								data.push(
									{
										id: pid,
										pid: pid,
										process: processName,
										status: status,
										cpuLoad: p.cpuLoad.toFixed(2),
										cpuAv: p.cpuAverage.toFixed(2),
										heapUsed: (p.heapUsed * MB_CORRECTION_FACTOR).toFixed(2),
										heapSize: (p.heapSize * MB_CORRECTION_FACTOR).toFixed(2),
										heapMax: (p.heapMax * MB_CORRECTION_FACTOR).toFixed(2),
										restarts: p.numRestarts,
										since: new Date(p.updatedAt),
										commandLine: p.commandLine
									}
								);

								self.table.update(data);

								if (processSelected[0].id === -1) {

									let newData = p[optionSelected];

									if (optionSelected === "cpuLoad" || optionSelected === "cpuAverage") {

										newData = newData.toFixed(2);
									}

									if (optionSelected === "heapMax" || optionSelected === "heapSize" || optionSelected === "heapUsed") {

										newData = (newData * MB_CORRECTION_FACTOR).toFixed(2);
									}

									dataChart['pid' + pid] = newData;
									dataChart['xValue'] = initTime;
								}
							}

							let optionsArr = ["cpuLoad", "cpuAverage", "heapUsed", "heapSize", "heapMax", "numRestarts"];

							for (let option of optionsArr) {

								if (self.optionMap.has(option)) {

									let d = {};
									d["xValue"] = initTime;

									for (let p of self.processes) {

										let newData = p[option];

										if (option === "cpuLoad" || option === "cpuAverage") {

											newData = newData.toFixed(2);
										}

										if (option === "heapMax" || option === "heapSize" || option === "heapUsed") {

											newData = (newData * MB_CORRECTION_FACTOR).toFixed(2);
										}

										d["pid" + p.pid] = newData;
									}

									self.optionMap.get(option).push(d);
								}
							}

							if (processSelected[0].id != -1) {

								let updatedProcess = self.processes.find(e => e.pid === processSelected[0].id);

								if (updatedProcess) {

									let newData = updatedProcess[optionSelected];

									if (optionSelected === "cpuLoad" || optionSelected === "cpuAverage") {

										newData = newData.toFixed(2);
									}

									if (optionSelected === "heapMax" || optionSelected === "heapSize" || optionSelected === "heapUsed") {

										newData = (newData * MB_CORRECTION_FACTOR).toFixed(2);
									}

									dataChart['pid' + updatedProcess.pid] = newData;
									dataChart['xValue'] = initTime;
								}
							}

							$$("chart-" + self.uid).add(dataChart);
							$$("chart-" + self.uid).refres();
						}
					}
				});

			}, (self.selectedUpdateTime * 1000) || (self.config.defaultUpdateTime * 1000));
		}
	}
}
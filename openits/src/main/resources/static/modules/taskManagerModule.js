import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import ListView from "../component/listview.js";
import DataTable from "../component/datatable.js";
import MessageBox from "../component/messageBox.js";

const MB_CORRECTION_FACTOR = 1 / (1024 * 1024);
const LINES_COLORS = ["#FF0000", "#0000FF", "#008000", "#FFFF00", "#800080", "#FFA500", "#A52A2A", "#C0C0C0", "#EE82EE", "#87CEFA"];

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
				let optionsArr = ["cpuLoad", "cpuAverage", "heapUsed", "heapSize", "heapMax"];

				for (let p of self.processes) {

					let hashId = Math.abs(util.getHash(p.commandLine));
					let nameProcess = p.main;
					let jarExtIdx = nameProcess.indexOf(".jar");

					if (jarExtIdx > -1) {

						nameProcess = nameProcess.substring(0, jarExtIdx);

						let fileSeparatorIdx = nameProcess.lastIndexOf("/");

						if (fileSeparatorIdx > -1) {

							nameProcess = nameProcess.substring(fileSeparatorIdx + 1);
						}
					}

					self.dataMap.set(
						hashId,
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
						let hashId = Math.abs(util.getHash(p.commandLine));
						if (option === "cpuLoad" || option === "cpuAverage") {

							newData = newData.toFixed(2);
						}

						if (option === "heapMax" || option === "heapSize" || option === "heapUsed") {

							newData = (newData * MB_CORRECTION_FACTOR).toFixed(2);
						}

						d["pid" + hashId] = newData;
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
						{ id: "heapMax", value: "heapMax (MB)" }
					];

					if (Array.isArray(self.processes) && self.processes.length > 0) {

						let timeToInit = new Date();
						let initHour = timeToInit.getHours().toString();
						let initMinute = timeToInit.getMinutes().toString();
						let initTime = initHour.padStart(2, "0") + ":" + initMinute.padStart(2, "0");

						for (let i = 0; i < self.processes.length; i++) {

							let nameProcess = self.processes[i].main;
							let jarExtIdx = nameProcess.indexOf(".jar");

							if (jarExtIdx > -1) {

								nameProcess = nameProcess.substring(0, jarExtIdx);

								let fileSeparatorIdx = nameProcess.lastIndexOf("/");

								if (fileSeparatorIdx > -1) {

									nameProcess = nameProcess.substring(fileSeparatorIdx + 1);
								}
							}

							let hashId = Math.abs(util.getHash(self.processes[i].commandLine));
							let color = LINES_COLORS[i % LINES_COLORS.length];

							listElements.push({
								id: hashId,
								alias: nameProcess,
								description: self.processes[i].main,
								color: color,
								commandLine: self.processes[i].commandLine
							});

							data['pid' + hashId] = self.processes[i].cpuLoad.toFixed(2);
							data['xValue'] = initTime;

							dataSeries.push(
								{
									id: hashId + self.uid,
									value: '#pid' + hashId + '#',
									item: {
										radius: 0
									},
									line: {
										color: color,
										width: 2
									},
									tooltip: {
										template: nameProcess + " - " + '#pid' + hashId + '#',
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
								type: "spline",
								value: "#yValue#",
								cellWidth: 50,
								dynamic: true,
								item: {
									radius: 0
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

								let hashId = Math.abs(util.getHash(p.commandLine));
								let nameProcess = p.main;
								let jarExtIdx = nameProcess.indexOf(".jar");

								if (jarExtIdx > -1) {

									nameProcess = nameProcess.substring(0, jarExtIdx);

									let fileSeparatorIdx = nameProcess.lastIndexOf("/");

									if (fileSeparatorIdx > -1) {

										nameProcess = nameProcess.substring(fileSeparatorIdx + 1);
									}
								}

								let status = (Math.floor(dat) - Math.floor(p.updatedAt) > (self.config.defaultStatusTime * 1000)) ? "LBL_DOWN" : "LBL_UP";

								data.push(
									{
										id: hashId,
										pid: p.pid,
										process: nameProcess,
										status: status,
										cpuLoad: p.cpuLoad.toFixed(2),
										cpuAv: p.cpuAverage.toFixed(2),
										heapUsed: (p.heapUsed * MB_CORRECTION_FACTOR).toFixed(2),
										heapSize: (p.heapSize * MB_CORRECTION_FACTOR).toFixed(2),
										heapMax: (p.heapMax * MB_CORRECTION_FACTOR).toFixed(2),
										restarts: p.numRestarts,
										since: p.createdAt,
										commandLine: p.commandLine
									}
								);
							}
						}

						const defaultGridOptionValues = [
							{ gridOption: { id: 1 }, value: "true" },
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
									sourceField: "id",
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
									},
									gridFieldBalloons: (function() {

										let fieldBallons = [];

										if (Array.isArray(self.processes) && self.processes.length > 0) {

											for (let i = 0; i < self.processes.length; i++) {

												fieldBallons.push({

													id: self.processes[i].pid,
													color: LINES_COLORS[i % LINES_COLORS.length],
													value: self.processes[i].pid
												});
											}
										}

										return fieldBallons;
									})(),
									gridFieldIcons: [
										{
											id: 1,
											badgeColor: true
										}
									]
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
									label: "cpuLoad (%)",
									labelTooltip: "cpuLoad (%)",
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
									label: "cpuAverage (%)",
									labelTooltip: "cpuAverage (%)",
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
									label: "heapUsed (MB)",
									labelTooltip: "heapUsed (MB)",
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
									label: "heapSize (MB)",
									labelTooltip: "heapSize (MB)",
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
									label: "heapMax (MB)",
									labelTooltip: "heapMax (MB)",
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
									multiselect: true,
									position: 1,
									showText: true,
									itemRequired: true,
									moduleAction: { id: self.config.moduleActions.restart }
								}
							]
						};

						let events = {};
						events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: 1,
							function: (ids) => {

								let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_RESTART_PROCESSES_CONFIRM_DESCRIPTION"), null, onAccept, null);
								ms.show();

								function onAccept() {

									let selectedItems = self.table.getSelectedItems();
									let idsArr = [];

									for (let selectedItem of selectedItems) {

										idsArr.push(selectedItem.pid);
									}

									let body = {

										pids: idsArr
									};

									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.stop, body);
								}
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

								let hashId = Math.abs(util.getHash(p.commandLine));

								if (self.dataMap.has(hashId)) {

									self.dataMap.get(hashId).push({
										time: initTime,
										cpuLoad: p.cpuLoad.toFixed(2),
										cpuAverage: p.cpuAverage.toFixed(2),
										heapUsed: (p.heapUsed * MB_CORRECTION_FACTOR).toFixed(2),
										heapMax: (p.heapMax * MB_CORRECTION_FACTOR).toFixed(2),
										heapSize: (p.heapSize * MB_CORRECTION_FACTOR).toFixed(2),
										numRestarts: p.numRestarts
									});
								}

								let status = (Math.floor(dat) - Math.floor(p.updatedAt) > (self.config.defaultStatusTime * 1000)) ? "LBL_DOWN" : "LBL_UP";
								let nameProcess = p.main;
								let jarExtIdx = nameProcess.indexOf(".jar");

								if (jarExtIdx > -1) {

									nameProcess = p.main.substring(0, jarExtIdx);

									let fileSeparatorIdx = nameProcess.lastIndexOf("/");

									if (fileSeparatorIdx > -1) {

										nameProcess = nameProcess.substring(fileSeparatorIdx + 1);
									}
								}

								data.push(
									{
										id: hashId,
										pid: p.pid,
										process: nameProcess,
										status: status,
										cpuLoad: p.cpuLoad.toFixed(2),
										cpuAv: p.cpuAverage.toFixed(2),
										heapUsed: (p.heapUsed * MB_CORRECTION_FACTOR).toFixed(2),
										heapSize: (p.heapSize * MB_CORRECTION_FACTOR).toFixed(2),
										heapMax: (p.heapMax * MB_CORRECTION_FACTOR).toFixed(2),
										restarts: p.numRestarts,
										since: new Date(p.createdAt),
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

									dataChart['pid' + hashId] = newData;
									dataChart['xValue'] = initTime;
								}
							}

							let optionsArr = ["cpuLoad", "cpuAverage", "heapUsed", "heapSize", "heapMax"];

							for (let option of optionsArr) {

								if (self.optionMap.has(option)) {

									let d = {};
									d["xValue"] = initTime;

									for (let p of self.processes) {

										let newData = p[option];
										let hashId = Math.abs(util.getHash(p.commandLine));

										if (option === "cpuLoad" || option === "cpuAverage") {

											newData = newData.toFixed(2);
										}

										if (option === "heapMax" || option === "heapSize" || option === "heapUsed") {

											newData = (newData * MB_CORRECTION_FACTOR).toFixed(2);
										}

										d["pid" + hashId] = newData;
									}

									self.optionMap.get(option).push(d);
								}
							}

							if (processSelected[0].id != -1) {

								let updatedProcess = self.processes.find(e => e.commandLine === processSelected[0].commandLine);

								if (updatedProcess) {

									let newData = updatedProcess[optionSelected];
									let hashId = Math.abs(util.getHash(updatedProcess.commandLine));

									if (optionSelected === "cpuLoad" || optionSelected === "cpuAverage") {

										newData = newData.toFixed(2);
									}

									if (optionSelected === "heapMax" || optionSelected === "heapSize" || optionSelected === "heapUsed") {

										newData = (newData * MB_CORRECTION_FACTOR).toFixed(2);
									}

									dataChart['pid' + hashId] = newData;
									dataChart['xValue'] = initTime;
								}
							}

							$$("chart-" + self.uid).add(dataChart);
							$$("chart-" + self.uid).refresh();
						}
					}
				});

			}, (self.selectedUpdateTime * 1000) || (self.config.defaultUpdateTime * 1000));
		}
	}
}
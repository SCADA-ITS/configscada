import GenericModule from "../modules/genericModule.js";


export default class CctvRioVerdeModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.camerasRelayList = {
            0:["50059","55611","55612","55613","55614"],
            1:["50058","55616","55617","55618","55619"]
        };

        this.camerasTitleList = {
            0:["BM","SM1","SM2","SM3","SM4"],
            1:["BC","SC1","SC2","SC3","SC4"]
        };
        this.urlIp = "http://10.5.6.10";
        this.ncols = 5;
        this.nrows = 2;
	}

	getView() {

		let view = undefined;

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
									hidden: false
								},
								{
									id: "loading-" + this.uid,
									rows: [getViewDrawNoData()],
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

		function getViewDrawNoData() {

			return {
				view: "template",
				template: function() {

					let icon = "<img src='img/loading.svg' align='center'>";

					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

		return view;
	}
	
		loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
            drawComponents();


		}

		function drawComponents() {

			let viewMain = {
				rows:[
					{
						id: self.uid + "gridlayout",
						view: "gridlayout",
						gridColumns: self.ncols, 
						gridRows: self.nrows,
						height: 500,
						cells:drawSVG()
					},
					{}
					]
			};

			function drawSVG() {
				
				let camerasList = [];

                for(let i=0; i<self.nrows; i++) {
                    for(let j=0; j<self.ncols; j++) {
                        
                        let cameraStream = self.urlIp + ":" + self.camerasRelayList[i][j];

                        camerasList.push({ 
                                id: self.uid + "camera" + i + j, 
                                template: self.camerasTitleList[i][j] + '<br><img src=' + cameraStream + "/stream" + '" id="img_cam' + i + j + '" style="width:100%;" />', 
                                x:j, 
                                y:i, 
                                dx:1, 
                                dy:1 
                            })
                        
                    }
                }
				
				return camerasList;
			}
			
			$$("loading-" + self.uid).hide();
			
			webix.ui(viewMain, $$(self.uid));
			
			$$(self.uid).show();

		}
	}
}

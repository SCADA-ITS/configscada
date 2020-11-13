import {SVG_7_ZONE,SVG_8_BAR, SVG_9_FAN, SVG_12_LUX, SVG_13_SEM, SVG_14_LUM, SVG_17_ANEM_EXT, SVG_18_ANEM_INT, SVG_19_CO, SVG_20_NO, SVG_21_OPAC, SVG_51_ETD, SVG_53_SEM, SVG_54_ATC, SVG_55_CT, SVG_56_UNIFILAR, SVG_57_MAGNETOTERMICO} from "./SVG_ELEMENT_TYPE_PATTERN.js";

export const ELEMENT_TYPE = {
  ID_1_WS: "ElementType:1",
  ID_2_CAM : "ElementType:2",
  ID_3_DET : "ElementType:3",
  ID_4_SEC : "ElementType:4",
  ID_5_PLC : "ElementType:5",
  ID_7_ZONE : "ElementType:7",
  ID_8_BAR : "ElementType:8",
  ID_9_FAN : "ElementType:9",
  ID_10_DMS : "ElementType:10",
  ID_11_SOS : "ElementType:11",
  ID_12_LUX : "ElementType:12",
  ID_13_SEM : "ElementType:13",
  ID_14_LUM : "ElementType:14",
  ID_15_FIRE : "ElementType:15",
  ID_16_MEG : "ElementType:16",
  ID_17_ANEM_EXT : "ElementType:17",
  ID_18_ANEM_INT : "ElementType:18",
  ID_19_CO : "ElementType:19",
  ID_20_NO : "ElementType:20",
  ID_21_OPAC : "ElementType:21",
  ID_24_PMV_A_C : "ElementType:24",
  ID_25_CABIN : "ElementType:25",
  ID_26_PMV_64X64 : "ElementType:26",
  ID_27_PMV_A_B : "ElementType:27",
  ID_28_PMV_192X64 : "ElementType:28",
  ID_29_PMV_B_D : "ElementType:29",
  ID_30_PMV_128X64 : "ElementType:30",
  ID_31_PMV : "ElementType:31",
  ID_32_PMV : "ElementType:32",
  ID_33_ETD : "ElementType:33",
  ID_34_CABIN_SCN : "ElementType:34",
  ID_35_PMV : "ElementType:35",
  ID_36_PMV : "ElementType:36",
  ID_41_CABIN_ATC : "ElementType:41",
  ID_42_CABIN_NRL : "ElementType:42",
  ID_43_CABIN_NRS : "ElementType:43",
  ID_44_CABIN_TFA3D : "ElementType:44",
  ID_45_CABIN_TFA : "ElementType:45",
  ID_46_CABIN_TOLL : "ElementType:46",
  ID_47_CABIN_TU : "ElementType:47",
  ID_51_ETD : "ElementType:51",
  ID_53_SEM : "ElementType:53",
  ID_54_ATC : "ElementType:54",
  ID_55_CT : "ElementType:55",
  ID_56_UNIFILAR : "ElementType:56",
  ID_57_MAGNETOTERMICO : "ElementType:57",

  values: {
    "ElementType:1":{ id: "ElementType:1", uiType: UI_ELEMENT_TYPE_STATIC, value: 1, scale_x: 28, scale_y: 28},
    "ElementType:2" :{ id: "ElementType:2", uiType: UI_ELEMENT_TYPE_STATIC, value: 2, scale_x: 28, scale_y: 28, 
      tabs:[
        {ELEMENT_FORM_TAB_FORM: {}},
        {ELEMENT_FORM_TAB_VIDEO: {
          urlParamVideo:"ElementTypeParam:2:1:3"}
        },
        {ELEMENT_FORM_TAB_SYN: {
          idSynoptic:"metroTunnel"}
        },
      ] 
    },
    "ElementType:3" :{ id: "ElementType:3", uiType: UI_ELEMENT_TYPE_DEFAULT, value: 3, scale_x: 28, scale_y: 28},
    "ElementType:4" :{ id: "ElementType:4", uiType: UI_ELEMENT_TYPE_DEFAULT, value: 4, scale_x: 28, scale_y: 28, 
      elementTypeParams:[
        {SERVICE_LEVEL : "12:2"}
      ]},
    "ElementType:5" :{ id: "ElementType:5", uiType: UI_ELEMENT_TYPE_DEFAULT, value: 5, scale_x: 28, scale_y: 28},
    "ElementType:7" :{ id: "ElementType:7", uiType: UI_ELEMENT_TYPE_GENERIC, value: 7, scale_x: 4122.35, scale_y: 3382.97, svgRaw:SVG_7_ZONE, 
      color: {
        undefined:"gray",
        null:"gray",
        0: "blue",
        1: "blue",
        2: "blue",
        3: "red"
      }
    },
    "ElementType:8" :{ id: "ElementType:8", uiType: UI_ELEMENT_TYPE_GENERIC, value: 8, scale_x: 30.163, scale_y: 16.13958, svgRaw:SVG_8_BAR, 
      elementTypeParams:[
        {BAR_COMMAND_FOR_BARRIER : "1:2"}
      ], 
      color: {
        undefined:"gray",
        null:"gray",
        0: "#ffcc00",
        1: "#c0c0c0",
        2: "#800080",
        3: "#ff0000"
      }
    },
    "ElementType:9" :{ id: "ElementType:9", uiType: UI_ELEMENT_TYPE_GENERIC, value: 9, scale_x: 392, scale_y: 528, svgRaw:SVG_9_FAN, 
      elementTypeParams:[
        {FAN_COMMAND_FOR_FAN: "1:2"}
      ]
    },
    "ElementType:10" :{ id: "ElementType:10", uiType: UI_ELEMENT_TYPE_DEFAULT, value: 10, scale_x: 76, scale_y: 24},
    "ElementType:11" :{ id: "ElementType:11", uiType: UI_ELEMENT_TYPE_STATIC, value: 11, scale_x: 28, scale_y: 28},
    "ElementType:12" :{ id: "ElementType:12", uiType: UI_ELEMENT_TYPE_GENERIC, value: 12, scale_x: 14.628, scale_y: 15.698, svgRaw:SVG_12_LUX, 
      elementTypeParams:[
        {LUX_LUMINOSITY: "1:2"}
      ]
    },
    "ElementType:13" :{ id: "ElementType:13", uiType: UI_ELEMENT_TYPE_GENERIC, value: 13, scale_x: 8.752, scale_y: 10.247, svgRaw:SVG_13_SEM, 
      elementTypeParams:[
        {SEM_STATUS: "1:2"}
      ] 
    },
    "ElementType:14" :{ id: "ElementType:14", uiType: UI_ELEMENT_TYPE_GENERIC, value: 14, scale_x: 14.628, scale_y: 15.698, svgRaw:SVG_14_LUM, 
      elementTypeParams:[
        {LUM_LUMINOSITY: "2:2"}
      ]
    },
    "ElementType:15" :{ id: "ElementType:15", uiType: UI_ELEMENT_TYPE_STATIC, value: 15, scale_x: 28, scale_y: 28},
    "ElementType:16" :{ id: "ElementType:16", uiType: UI_ELEMENT_TYPE_STATIC, value: 16, scale_x: 28, scale_y: 28},
    "ElementType:17" :{ id: "ElementType:17", uiType: UI_ELEMENT_TYPE_GENERIC, value: 17, scale_x: 14.628, scale_y: 15.698, svgRaw:SVG_17_ANEM_EXT,
      elementTypeParams:[
        {ANEM_EXT_WIND_SPEED: "1:2"}
      ]
    },
    "ElementType:18" :{ id: "ElementType:18", uiType: UI_ELEMENT_TYPE_GENERIC, value: 18, scale_x: 14.628, scale_y: 15.698, svgRaw:SVG_18_ANEM_INT,
      elementTypeParams:[
        {ANEM_INT_WIND_SPEED: "1:2"}
      ]
    },
      "ElementType:19" :{ id: "ElementType:19", uiType: UI_ELEMENT_TYPE_GENERIC, value: 19, scale_x: 14.628, scale_y: 15.698, svgRaw:SVG_19_CO,
      elementTypeParams:[
        {CO_CONCENTRATION: "1:2"}
      ]
    },
    "ElementType:20" :{ id: "ElementType:20", uiType: UI_ELEMENT_TYPE_GENERIC, value: 20, scale_x: 14.628, scale_y: 15.698, svgRaw:SVG_20_NO,
      elementTypeParams:[
        {NO_CONCENTRATION: "1:2"}
      ]
    },
    "ElementType:21" :{ id: "ElementType:21", uiType: UI_ELEMENT_TYPE_GENERIC, value: 21, scale_x: 14.628, scale_y: 15.698, svgRaw:SVG_21_OPAC,
      elementTypeParams:[
        {OPAC_CONCENTRATION: "1:2"}
      ]
    },
    "ElementType:24" :{ id: "ElementType:24", uiType: UI_ELEMENT_TYPE_MATRIX, value: 24, scale_x: 28, scale_y: 28},
    "ElementType:25" :{ id: "ElementType:25", uiType: UI_ELEMENT_TYPE_STATIC, value: 25, scale_x: 28, scale_y: 28},
    "ElementType:26" :{ id: "ElementType:26", uiType: UI_ELEMENT_TYPE_MATRIX, value: 26, scale_x: 28, scale_y: 28},
    "ElementType:27" :{ id: "ElementType:27", uiType: UI_ELEMENT_TYPE_MATRIX, value: 27, scale_x: 28, scale_y: 28},
    "ElementType:28" :{ id: "ElementType:28", uiType: UI_ELEMENT_TYPE_MATRIX, value: 28, scale_x: 28, scale_y: 28},
    "ElementType:29" :{ id: "ElementType:29", uiType: UI_ELEMENT_TYPE_MATRIX, value: 29, scale_x: 28, scale_y: 28},
    "ElementType:30" :{ id: "ElementType:30", uiType: UI_ELEMENT_TYPE_MATRIX, value: 30, scale_x: 28, scale_y: 28},
    "ElementType:31" :{ id: "ElementType:31", uiType: UI_ELEMENT_TYPE_MATRIX, value: 31, scale_x: 45, scale_y: 45},
    "ElementType:32" :{ id: "ElementType:32", uiType: UI_ELEMENT_TYPE_MATRIX, value: 32, scale_x: 28, scale_y: 28},
    "ElementType:33" :{ id: "ElementType:33", uiType: UI_ELEMENT_TYPE_DEFAULT, value: 33, scale_x: 28, scale_y: 28},
    "ElementType:34" :{ id: "ElementType:34", uiType: UI_ELEMENT_TYPE_STATIC, value: 34, scale_x: 28, scale_y: 28},
    "ElementType:35" :{ id: "ElementType:35", uiType: UI_ELEMENT_TYPE_MATRIX, value: 35, scale_x: 28, scale_y: 28},
    "ElementType:36" :{ id: "ElementType:36", uiType: UI_ELEMENT_TYPE_MATRIX, value: 36, scale_x: 220, scale_y: 52},
    "ElementType:41" :{ id: "ElementType:41", uiType: UI_ELEMENT_TYPE_STATIC, value: 41, scale_x: 28, scale_y: 28},
    "ElementType:42" :{ id: "ElementType:42", uiType: UI_ELEMENT_TYPE_STATIC, value: 42, scale_x: 28, scale_y: 28},
    "ElementType:43" :{ id: "ElementType:43", uiType: UI_ELEMENT_TYPE_STATIC, value: 43, scale_x: 28, scale_y: 28},
    "ElementType:44" :{ id: "ElementType:44", uiType: UI_ELEMENT_TYPE_STATIC, value: 44, scale_x: 28, scale_y: 28},
    "ElementType:45" :{ id: "ElementType:45", uiType: UI_ELEMENT_TYPE_STATIC, value: 45, scale_x: 28, scale_y: 28},
    "ElementType:46" :{ id: "ElementType:46", uiType: UI_ELEMENT_TYPE_STATIC, value: 46, scale_x: 28, scale_y: 28},
    "ElementType:47" :{ id: "ElementType:47", uiType: UI_ELEMENT_TYPE_STATIC, value: 47, scale_x: 28, scale_y: 28},
    "ElementType:51" :{ id: "ElementType:51", uiType: UI_ELEMENT_TYPE_GENERIC, value: 51, scale_x: 50, scale_y: 50, svgRaw:SVG_51_ETD},
    "ElementType:53" :{ id: "ElementType:53", uiType: UI_ELEMENT_TYPE_GENERIC, value: 53, scale_x: 8.752, scale_y: 10.247, svgRaw:SVG_53_SEM },
    "ElementType:54" :{ id: "ElementType:54", uiType: UI_ELEMENT_TYPE_GENERIC, value: 54, scale_x: 50, scale_y: 50, svgRaw:SVG_54_ATC},
    "ElementType:55" :{ id: "ElementType:55", uiType: UI_ELEMENT_TYPE_GENERIC,  value: 55, scale_x: 392, scale_y: 528, svgRaw:SVG_55_CT },
    "ElementType:56" :{ id: "ElementType:56", uiType: UI_ELEMENT_TYPE_GENERIC,  value: 56, scale_x: 392, scale_y: 528, svgRaw:SVG_56_UNIFILAR },
    "ElementType:57" :{ id: "ElementType:57", uiType: UI_ELEMENT_TYPE_GENERIC,  value: 57, scale_x: 120, scale_y: 120, svgRaw:SVG_57_MAGNETOTERMICO,
      elementTypeParams:[
        {MAGNETOTERMICO_STATE: "1:2"}
      ] 
    },
  }
};

export const ICON_COLOR = {
  undefined:"gray",
  null:"gray",
  0: "#ffcc00",
  1: "#008000",
  2: "#800080",
  3: "#ff0000"
}

export const WIDTH_RESOLUTION= 1920;
export const HEIGHT_RESOLUTION= 1080;

export const RULE = {
  PLAN_ADMIN:"plan_admin",
  PLAN_ACTION:"plan_action",
  COMMAND_ACTION: "command_action",
  ALARM_RECOGNIZE: "alarm_recognize",
  EDIT_USER: "edit_user",
  EDIT_ROLE: "edit_role",
  LOGIN_ROLE: "login"
}

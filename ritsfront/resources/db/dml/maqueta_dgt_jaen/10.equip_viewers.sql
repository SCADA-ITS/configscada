INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, no_show_measures, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_properties, show_historical_chart) VALUES
('ElementType:1','Meteo DGT','Meteo DGT','LBL_ELEMENT_SUBTYPE_METEO_DGT',NULL,true,False,false,null,false,null, null, null,050001,true,false),
('ElementSubtype:1:2', 'CAMERA DOMO', 'CAMERA DOMO', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 2, true, null, null, null, 010002, null, false),
('ElementSubtype:2:2', 'CAMERA DAI', 'CAMERA DAI', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 4, false, null, null, null, 020002, null, false),
('ElementType:3', 'Detector', 'Detector', 'LBL_ELEMENT_TYPE_DETECTOR', NULL, true, false, false, null, false, 225, 3, null, 3, true, true),
('ElementType:4', 'Section', 'Section', 'LBL_ELEMENT_TYPE_SECTION', NULL, true, true, false, null, false, 225, 3, null, 020004, true, true),
('ElementType:8', 'BARRIER', 'BARRIER', 'LBL_ELEMENT_TYPE_BARRIER', NULL, true, false, true, null, true, 300, 1, null, 000008, true, false),
('ElementSubtype:4:9', 'JETFAN', 'JETFAN', 'LBL_ELEMENT_TYPE_JETFAN', NULL, true, false, true, null, false, null, null, null, 040009, null, false),
('ElementSubtype:5:9', 'JETFAN', 'JETFAN', 'LBL_ELEMENT_TYPE_JETFAN', NULL, true, false, true, null, false, null, null, null, 040009, null, false),
('ElementSubtype:4:11', 'SOS', 'SOS', 'LBL_ELEMENT_TYPE_SOS', NULL, true, false, false, null, false, 300, 1, null, 000011, false, false),
('ElementType:13', 'SEM', 'SEM', 'LBL_ELEMENT_TYPE_TRAFFIC_LIGHT', NULL, true, false, true, null, false, 300, 1, null, 000013, true, false),
('ElementType:14', 'LUM', 'LUM', 'LBL_ELEMENT_TYPE_LUM', NULL, true, false, false, null, false, 300, 1, true, 000014, true, false),
('ElementType:15', 'FIRE_DET', 'FIRE_DET', 'LBL_ELEMENT_TYPE_FIRE_DET', NULL, true, false, false, null, false, 300, 1, true, 000015, true, false),
('ElementType:17','GALIBO','GALIBO','LBL_ELEMENT_TYPE_GALIBO',null,true,False,false,null,true, 300,3,null,17, true,false),
('ElementType:18','ANEM_INT','ANEM_INT','LBL_ELEMENT_TYPE_ANEM_INT',null,true,False,true,null,false, 300,3,null,18, false,false),
('ElementType:19','CO','CO','LBL_ELEMENT_TYPE_CO',null, true, False, true, null, false, 300, 3, null,19,false, false),
('ElementType:20','NO','NO','LBL_ELEMENT_TYPE_NO',null, true, False, false, null, false, 300, 3, null,20,false, false),
('ElementType:21','OPAC','OPAC','LBL_ELEMENT_TYPE_OPAC',null, true, False, true, null, false, 300, 3, null,21,false, false),
('ElementType:26','SWITCH','SWITCH','LBL_ELEMENT_TYPE_SWITCH',null, true, False, false, null, false, 300, 3, null,26,false, false),
('ElementType:27','PLC','PLC','LBL_ELEMENT_TYPE_PLC',null, true, False, false, null, false, 300, 3, null,27,false, false),
('ElementType:29','FIBROLASER_CT','FIBROLASER_CT','LBL_ELEMENT_TYPE_FIBROLASER_CT',null, true, False, false, null, true, 300, 3, null,29,false, false),
('ElementSubtype:3:32', 'PRESSURIZATION', 'PRESSURIZATION', 'LBL_ELEMENT_TYPE_PRESURIZACION', NULL, true, false, true, null,null, 300, 3, null,030032, true, false),
('ElementType:51', 'ETD', 'ETD', 'LBL_ELEMENT_TYPE_ETD', NULL, true, true, false, null,true, 450, 3, null, 51, null, false),
('ElementSubtype:2:53', 'SEM_2_GRUPOS', 'SEM_2_GRUPOS', 'LBL_ELEMENT_TYPE_SEM_2_LUCES', NULL, true, false, false, null, false, null, null, null, 020053, true, false),
('ElementSubtype:1:57', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 000057, true, false),
('ElementSubtype:2:57', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005704, true, false),
('Element:57:2', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:29', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:43', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:68', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:82', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:86', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:92', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:119', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:122', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:126', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:148', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:162', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:166', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:188', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:213', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('Element:57:221', 'SWITCH', 'SWITCH', 'LBL_ELEMENT_TYPE_SWITCH', NULL, true, false, false, null, false, 300, 1, null, 00005701, true, false),
('ElementType:61','FIRE_DET_CT','FIRE_DET_CT','LBL_ELEMENT_TYPE_FIRE_DET_CT',null, true, False, false, null, true, 300, 3, null,000061,true, false),
('ElementType:65','SAI','SAI','LBL_ELEMENT_TYPE_SAI',null, true, False, false, null, true, 300, 3, null,000065,false, false),
('ElementType:66','VANE','VANE','LBL_ELEMENT_TYPE_VANE',null, true, False, false, null, false, 300, 3, null,000066,true, false),
('ElementType:67','ILUM_EMER_CT','ILUM_EMER_CT','LBL_ELEMENT_TYPE_ILUM_EMER_CT',null, true, False, false, null, false, 300, 3, null,000067,true, false),
('ElementSubtype:3:70','ANALIZADOR_RED','ANALIZADOR_RED','LBL_ELEMENT_TYPE_ANALIZADOR_RED',null, true, False, false, null, false, 300, 3, null,000070,false, false),
('ElementSubtype:11:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 110071, null, false),
('ElementSubtype:12:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 120071, null, false),
('ElementSubtype:18:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 180071, null, false),
('ElementSubtype:22:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 220071, false, false),
('ElementSubtype:26:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 260071, false, false),
('ElementSubtype:27:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, false, null, true, null, null, null, 270071, false, false),
('ElementType:73','GRUPO iLUMINACION EMERGENCIA','GRUPO iLUMINACION EMERGENCIA','LBL_ELEMENT_TYPE_G_ILUM_EMER',null,True,False,True,null,false,300,1,null,000073,True, False),
('ElementType:75','GE','GE','LBL_ELEMENT_TYPE_GE',null, true, False, false, null, true, 300, 3, null,000075,true, false),
('ElementSubtype:1:84', 'LIGHTNING_CIRCUIT', 'LIGHTNING_CIRCUIT', 'LBL_ELEMENT_TYPE_LIGHTNING_CIRCUIT', NULL, true, false, true, null, false, 300, 1, null, 000084, true, false),
('ElementSubtype:2:84', 'LIGHTNING_CIRCUIT', 'LIGHTNING_CIRCUIT', 'LBL_ELEMENT_TYPE_LIGHTNING_CIRCUIT', NULL, true, false, false, null, true, 300, 1, null, 00008401, true, false),
('ElementSubtype:3:84', 'LIGHTNING_CIRCUIT', 'LIGHTNING_CIRCUIT', 'LBL_ELEMENT_TYPE_LIGHTNING_CIRCUIT', NULL, true, false, true, null, false, 300, 1, null, 00008401, false, false),
('Element:86:1','PUMP','PUMP','LBL_ELEMENT_TYPE_PUMP',null, true, False, false, null, true, 300, 3, null,00008601,true, false),
('Element:86:2','PUMP','PUMP','LBL_ELEMENT_TYPE_PUMP',null, true, False, false, null, true, 300, 3, null,00008601,true, false),
('Element:86:3','PUMP','PUMP','LBL_ELEMENT_TYPE_PUMP',null, true, False, false, null, true, 300, 3, null,00008601,true, false),
('Element:86:4','PUMP','PUMP','LBL_ELEMENT_TYPE_PUMP',null, true, False, false, null, true, 300, 3, null,01008601,true, false),
('ElementType:87', 'TANK', 'TANK', 'LBL_ELEMENT_TYPE_TANK', NULL, true, false, false, null, false, 300, 1, null, 000087, true, false),
('ElementType:89', 'GATE', 'GATE', 'LBL_ELEMENT_TYPE_GATE', NULL, true, false, false, null, false, 300, 1, null, 000089, true, false),
('ElementType:2000', 'Tube', 'Tube', 'LBL_ELEMENT_TYPE_Tube', NULL, true, false, true, null, true, 300, 3, null, 2000, true, false),
('ElementType:2001', 'Ventilation manager', 'Ventilation manager', 'LBL_ELEMENT_TYPE_VENTILATION', NULL, false, false, false, null, false, 300, 3, null, null, null, false),
('ElementType:2002', 'Lighting manager', 'Lighting manager', 'LBL_ELEMENT_TYPE_ILUMINATION', NULL, false, false, false, null, false, 300, 3, null, null, null, false);



INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:1',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True),
('ElementSubtype:1:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementSubtype:1:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementSubtype:2:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementSubtype:2:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementType:3', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementType:8', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:4:9', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:5:9', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:4:11', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:13', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:14', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:15', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:17', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
--('ElementType:18',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:19',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:20',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:21',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:26',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:27',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:29',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementSubtype:3:32', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:51', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:2:53', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:1:57', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:2:57', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('Element:57:2',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:29',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:43',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:68',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:82',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:86',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:92',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:119',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:122',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:126',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:148',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:162',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:166',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:188',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:213',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:57:221',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:61',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:65',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:66',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:67',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementSubtype:3:70',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementSubtype:11:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:12:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:18:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:22:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:26:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:27:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:73',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True ),
('ElementType:75',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementSubtype:1:84', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:2:84', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:3:84',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True),
('Element:86:1',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:86:2',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:86:3',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('Element:86:4',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:87', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:89', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:2001', 200002, 'Synoptic', 'Synoptic', 'LBL_SYNOPTIC', NULL, 1, true),
('ElementType:2002', 200002, 'Synoptic', 'Synoptic', 'LBL_SYNOPTIC', NULL, 1, true);




INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(05000101, 'ElementType:1', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True),
(0301, 'ElementType:3', 'General', 'General', 'LBL_ELEMENT_GENERAL', NULL, 1, true),
(0302, 'ElementType:3', 'Clasificación velocidad', 'Clasificación velocidad', 'LBL_ELEMENT_SPEED_CLASSIFICATION', NULL, 2, true),
(0303, 'ElementType:3', 'Clasificación longitud', 'Clasificación longitud', 'LBL_ELEMENT_LONG_CLASSIFICATION', NULL, 3, true),
(0401, 'ElementType:4', 'General', 'General', 'LBL_ELEMENT_GENERAL', NULL, 1, true),
(0402, 'ElementType:4', 'Clasificación velocidad', 'Clasificación velocidad', 'LBL_ELEMENT_SPEED_CLASSIFICATION', NULL, 2, true),
(0403, 'ElementType:4', 'Clasificación longitud', 'Clasificación longitud', 'LBL_ELEMENT_LONG_CLASSIFICATION', NULL, 3, true),
(040901, 'ElementSubtype:4:9', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(050901, 'ElementSubtype:5:9', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1101, 'ElementSubtype:4:11', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1301, 'ElementType:13', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1401, 'ElementType:14', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1501, 'ElementType:15', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1801, 'ElementType:18', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(1901, 'ElementType:19', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(2001, 'ElementType:20', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(2101, 'ElementType:21', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(2601, 'ElementType:26', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(2701, 'ElementType:27', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(2901, 'ElementType:29', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(033201, 'ElementSubtype:3:32', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(5101, 'ElementType:51', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(025301, 'ElementSubtype:2:53', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(5701, 'ElementSubtype:1:57', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5718, 'ElementSubtype:2:57', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5702, 'Element:57:2', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5703, 'Element:57:29', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5704, 'Element:57:43', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5705, 'Element:57:68', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5706, 'Element:57:82', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5707, 'Element:57:86', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5708, 'Element:57:92', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5709, 'Element:57:119', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5710, 'Element:57:122', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5711, 'Element:57:126', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5712, 'Element:57:148', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5713, 'Element:57:162', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5714, 'Element:57:166', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5715, 'Element:57:188', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5716, 'Element:57:213', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(5717, 'Element:57:221', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(6601, 'ElementType:66', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(6701, 'ElementType:67', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(037001, 'ElementSubtype:3:70', 'InstMeasures', 'InstMeasures', 'LBL_ELEMENT_INST_MEASURES', null, 1, true),
(00010073, 'ElementType:73', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True),
(018401, 'ElementSubtype:1:84', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(028401, 'ElementSubtype:2:84', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(03008401, 'ElementSubtype:3:84', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True),
(8701, 'ElementType:87', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false),
(8901, 'ElementType:89', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, false);




INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
--Meteo DGT
(05000101,'ElementTypeParam:1:2:4', 5, True),
(05000101,'ElementTypeParam:1:2:10', 4, True),
(05000101,'ElementTypeParam:1:2:11', 11, True),
(05000101,'ElementTypeParam:1:2:17', 3, True),
(05000101,'ElementTypeParam:1:2:18', 10, True),
(05000101,'ElementTypeParam:1:2:19', 12, True),
(05000101,'ElementTypeParam:1:2:20', 6, True),
(05000101,'ElementTypeParam:1:2:23', 13, True),
(05000101,'ElementTypeParam:1:2:26', 7, True),
(05000101,'ElementTypeParam:1:2:27', 8, True),
(05000101,'ElementTypeParam:1:2:28', 9, True),
(05000101,'ElementTypeParam:1:2:29', 1, True),
(05000101,'ElementTypeParam:1:2:30', 2, True),

(0301, 'ElementTypeParam:3:2:1', 1, true),
(0301, 'ElementTypeParam:3:2:2', 8, true),
(0301, 'ElementTypeParam:3:2:3', 9, true),
(0301, 'ElementTypeParam:3:2:4', 3, true),
(0301, 'ElementTypeParam:3:2:5', 10, true),
(0301, 'ElementTypeParam:3:2:6', 5, true),
(0301, 'ElementTypeParam:3:2:7', 6, true),
(0301, 'ElementTypeParam:3:2:8', 7, true),
(0301, 'ElementTypeParam:3:2:9', 2, true),
(0301, 'ElementTypeParam:3:2:10', 11, true),
(0301, 'ElementTypeParam:3:2:13', 11, true),
(0301, 'ElementTypeParam:3:2:17', 11, true),
(0302, 'ElementTypeParam:3:2:40', 1, true),
(0302, 'ElementTypeParam:3:2:41', 2, true),
(0302, 'ElementTypeParam:3:2:42', 2, true),
(0303, 'ElementTypeParam:3:2:20', 1, true),
(0303, 'ElementTypeParam:3:2:21', 2, true),

(0401, 'ElementTypeParam:4:2:1', 1, true),
(0401, 'ElementTypeParam:4:2:2', 6, true),
(0401, 'ElementTypeParam:4:2:4', 2, true),
(0401, 'ElementTypeParam:4:2:6', 3, true),
(0401, 'ElementTypeParam:4:2:7', 4, true),
(0401, 'ElementTypeParam:4:2:18', 5, true),
(0401, 'ElementTypeParam:4:2:100', 7, true),
(0402, 'ElementTypeParam:4:2:80', 1, true),
(0402, 'ElementTypeParam:4:2:81', 2, true),
(0402, 'ElementTypeParam:4:2:82', 3, true),
(0403, 'ElementTypeParam:4:2:60', 1, true),
(0403, 'ElementTypeParam:4:2:61', 2, true),

(040901, 'ElementTypeParam:9:2:1', 1, true),
(040901, 'ElementTypeParam:9:2:2', 2, true),
(040901, 'ElementTypeParam:9:2:15', 4, true),
(040901, 'ElementTypeParam:9:2:28', 5, true),
(040901, 'ElementTypeParam:9:2:29', 6, true),

(050901, 'ElementTypeParam:9:2:1', 1, true),
(050901, 'ElementTypeParam:9:2:2', 2, true),
(050901, 'ElementTypeParam:9:2:15', 4, true),
(050901, 'ElementTypeParam:9:2:29', 5, true),

(1101, 'ElementTypeParam:11:2:4', 1, true),
(1101, 'ElementTypeParam:11:2:15', 2, true),
(1101, 'ElementTypeParam:11:2:16', 3, true),

(1301, 'ElementTypeParam:13:2:1', 1, true),

(1401, 'ElementTypeParam:14:2:2', 1, true),

(1501, 'ElementTypeParam:15:2:1', 1, true),

(1801,'ElementTypeParam:18:2:1', 1, true),

(1901,'ElementTypeParam:19:2:1', 1, true),

(2001,'ElementTypeParam:20:2:1', 1, true),

(2101,'ElementTypeParam:21:2:1', 1, true),

(033201, 'ElementTypeParam:32:2:8', 1, true),
(033201, 'ElementTypeParam:32:2:9', 2, true),
(033201, 'ElementTypeParam:32:2:10', 3, true),

(025301, 'ElementTypeParam:53:2:1', 1, true),

(5701,'ElementTypeParam:57:2:1', 1, true),

(5702,'ElementTypeParam:57:2:1', 1, true),

(5703,'ElementTypeParam:57:2:1', 1, true),

(5704,'ElementTypeParam:57:2:1', 1, true),

(5705,'ElementTypeParam:57:2:1', 1, true),

(5706,'ElementTypeParam:57:2:1', 1, true),

(5707,'ElementTypeParam:57:2:1', 1, true),

(5708,'ElementTypeParam:57:2:1', 1, true),

(5709,'ElementTypeParam:57:2:1', 1, true),

(5710,'ElementTypeParam:57:2:1', 1, true),

(5711,'ElementTypeParam:57:2:1', 1, true),

(5712,'ElementTypeParam:57:2:1', 1, true),

(5713,'ElementTypeParam:57:2:1', 1, true),

(5714,'ElementTypeParam:57:2:1', 1, true),

(5715,'ElementTypeParam:57:2:1', 1, true),

(5716,'ElementTypeParam:57:2:1', 1, true),

(5717,'ElementTypeParam:57:2:1', 1, true),

(5718,'ElementTypeParam:57:2:2', 1, true),

(6601,'ElementTypeParam:66:2:1', 1, true),
(6601,'ElementTypeParam:66:2:2', 2, true),

(6701,'ElementTypeParam:67:2:1', 1, true),

(037001,'ElementTypeParam:70:2:1', 1, true),
(037001,'ElementTypeParam:70:2:2', 2, true),
(037001,'ElementTypeParam:70:2:3', 3, true),
(037001,'ElementTypeParam:70:2:6', 4, true),
(037001,'ElementTypeParam:70:2:7', 5, true),
(037001,'ElementTypeParam:70:2:8', 6, true),
(037001,'ElementTypeParam:70:2:30', 7, true),
(037001,'ElementTypeParam:70:2:63', 8, true),
(037001,'ElementTypeParam:70:2:64', 9, true),

(00010073,'ElementTypeParam:73:2:1', 1, True),
(00010073,'ElementTypeParam:73:2:2', 2, True),
(00010073,'ElementTypeParam:73:2:3', 3, True),

(018401,'ElementTypeParam:84:2:1', 1, true),

(03008401,'ElementTypeParam:84:2:2', 2, True),
(03008401,'ElementTypeParam:84:2:3', 3, True),

(8701,'ElementTypeParam:87:2:4', 1, true);




INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(020101, 'ElementSubtype:1:2', 'ElementTypeParam:2:1:1' , 1, true),
(020102, 'ElementSubtype:1:2', 'ElementTypeParam:2:1:2' , 2, true),
(020201, 'ElementSubtype:2:2', 'ElementTypeParam:2:1:1' , 1, true),
(020202, 'ElementSubtype:2:2', 'ElementTypeParam:2:1:2' , 2, true),

(090401, 'ElementSubtype:4:9', 'ElementTypeParam:9:1:1' , 1, true),
(090402, 'ElementSubtype:4:9', 'ElementTypeParam:9:1:2' , 2, true),

(090501, 'ElementSubtype:5:9', 'ElementTypeParam:9:1:1' , 1, true),
(090502, 'ElementSubtype:5:9', 'ElementTypeParam:9:1:2' , 2, true),

(1101, 'ElementSubtype:4:11', 'ElementTypeParam:11:1:1', 1, true),
(1102, 'ElementSubtype:4:11', 'ElementTypeParam:11:1:2', 2, true),

(1801,'ElementType:18','ElementTypeParam:18:1:1', 1, true),
(1802,'ElementType:18','ElementTypeParam:18:1:2', 2, true),

(1901,'ElementType:19','ElementTypeParam:19:1:1', 1, true),
(1902,'ElementType:19','ElementTypeParam:19:1:2', 2, true),

(2001,'ElementType:20','ElementTypeParam:20:1:1', 1, true),

(2101,'ElementType:21','ElementTypeParam:21:1:1', 1, true),
(2102,'ElementType:21','ElementTypeParam:21:1:2', 2, true),

(2601,'ElementType:26','ElementTypeParam:26:1:1', 1, true),
(2602,'ElementType:26','ElementTypeParam:26:1:2', 2, true),

(2701,'ElementType:27','ElementTypeParam:27:1:1', 1, true),
(2702,'ElementType:27','ElementTypeParam:27:1:2', 2, true),

(2901,'ElementType:29','ElementTypeParam:29:1:1', 1, true),
(2902,'ElementType:29','ElementTypeParam:29:1:2', 2, true),

(51101, 'ElementType:51', 'ElementTypeParam:51:1:1' , 1, true),
(51102, 'ElementType:51', 'ElementTypeParam:51:1:2' , 1, true),

(6501, 'ElementType:65', 'ElementTypeParam:65:1:1', 1, true),
(6502, 'ElementType:65', 'ElementTypeParam:65:1:2', 2, true),

(037001, 'ElementSubtype:3:70', 'ElementTypeParam:70:1:1', 1, true),
(037002, 'ElementSubtype:3:70', 'ElementTypeParam:70:1:2', 2, true),

(711101, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:1' , 1, true),
(711102, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:3' , 2, true),

(711201, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:1' , 1, true),
(711202, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:3' , 2, true),

(711801, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:1' , 1, true),
(711802, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:3' , 2, true),

(712201, 'ElementSubtype:22:71', 'ElementTypeParam:71:1:1' , 1, true),
(712202, 'ElementSubtype:22:71', 'ElementTypeParam:71:1:3' , 2, true),

(712601, 'ElementSubtype:26:71', 'ElementTypeParam:71:1:1' , 1, true),
(712602, 'ElementSubtype:26:71', 'ElementTypeParam:71:1:3' , 2, true),

(712701, 'ElementSubtype:27:71', 'ElementTypeParam:71:1:1' , 1, true),
(712702, 'ElementSubtype:27:71', 'ElementTypeParam:71:1:3' , 2, true),

(840301,'ElementSubtype:3:84','ElementTypeParam:84:1:6', 1, True),
(840302,'ElementSubtype:3:84','ElementTypeParam:84:1:7', 2, True),
(840303,'ElementSubtype:3:84','ElementTypeParam:84:1:8', 3, True);

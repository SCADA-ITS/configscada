INSERT INTO ui.adapter_types(id, "name", description, enabled, visible) VALUES
(1, 'VO TO GRID DATA', 'VO TO GRID DATA', true, true),
(2, 'VO TO TREE DATA', 'VO TO TREE DATA', true, true);

INSERT INTO ui.adapters(id, adapter_type_id, "name", description, enabled, visible) VALUES
(1, 1, 'ROLE TO GRID DATA', 'ROLE TO GRID DATA', true, true),
(2, 1, 'ELEMENTS TO GRID DATA', 'ELEMENTS TO GRID DATA', true, true),
(3, 1, 'USERS TO GRID DATA', 'USERS TO GRID DATA', true, true),
(4, 1, 'ALARMCONFIG TO GRID DATA', 'ALARMCONFIG TO GRID DATA', true, true),
(5, 1, 'INCIDENT REPORTS TO GRID DATA', 'INCIDENT REPORTS TO GRID DATA', true, true),
(6, 1, 'AUDIT LOG TO GRID DATA', 'AUDIT LOG TO GRID DATA', true, true),
(7, 1, 'PLANSCHEDULE TO GRID DATA', 'PLANSCHEDULE TO GRID DATA', true, true),
(8, 2, 'ELEMENTS TO TREE DATA', 'ELEMENTS TO TREE DATA', true, true),
(9, 1, 'ALARMS FILTERED TO GRID DATA', 'ALARMS FILTERED TO GRID DATA', true, true),
(10, 2, 'ELEMENT HIERCHIES TO TREE DATA', 'ELEMENT HIERCHIES TO TREE DATA', true, true),
(11, 1, 'PLANS TO GRID DATA', 'PLANS TO GRID DATA', true, true),
(12, 1, 'ELEMENTS LIST TO GRID DATA', 'ELEMENTS LIST TO GRID DATA', true, true),
(13, 1, 'IDELAYED_ INCIDENT REPORTS TO GRID DATA', 'DELAYED_ INCIDENT REPORTS TO GRID DATA', true, true);

INSERT INTO ui.adapter_params(id, adapter_id, pos, param, enabled, visible) VALUES
(1, 2, 1, 'elementTypeId', true, true),
(2, 6, 1, 'logTypeId', true, true);

INSERT INTO ui.adapter_data_source_types(id, name, description, enabled, visible) VALUES
(1, 'groovy', 'groovy adapter file', true, true),
(2, 'js', 'javascript adapter', true, true);

INSERT INTO ui.adapter_data_sources(adapter_id, data_source_id, adapter_data_source_type_id, value) values
(2, 182, 1, 'config/groovy/adapter/grid/AdapterGridElement.groovy'),
(2, 3, 2, 'ws.element.getList'),
(3, 148, 1, 'config/groovy/adapter/grid/AdapterGridGeneric.groovy'),
--(3, 234, 1, 'config/groovy/adapter/grid/AdapterGridGeneric.groovy'),
(1, 44, 1, 'config/groovy/adapter/grid/AdapterGridGeneric.groovy'),
--(1, 235, 1, 'config/groovy/adapter/grid/AdapterGridGeneric.groovy'),
(4, 23, 1, 'config/groovy/adapter/grid/AdapterGridAlarmConfig.groovy'),
(5, 127, 1, 'config/groovy/adapter/grid/AdapterGridIncidentReport.groovy'),
(5, 140, 2, 'ws.incidentReport.getList'),
(6, 147, 1, 'config/groovy/adapter/grid/AdapterGridAuditLog.groovy'),
(6, 152, 2, 'ws.auditLog.getList'),
(7, 86, 1, 'config/groovy/adapter/grid/AdapterGridPlanSchedule.groovy'),
(7, 154, 2, 'ws.planSchedule.getList'),
-- TODO: DataSource = 5 temp. Check if valid
(8, 5, 1, 'config/groovy/adapter/tree/AdapterTreeElementWithStates.groovy'),
-- TODO: DataSource = 3 temp. Change to new datasource ws with all equips
(8, 3, 2, 'ws.element.getList'),
(9, 39, 1, 'config/groovy/adapter/grid/AdapterGridAlarmFiltered.groovy'),
(10, 161, 1, 'config/groovy/adapter/tree/AdapterTreeElementHierarchyIterative.groovy'),
(10, 3, 2, 'ws.element.getList'),
(11, 153, 1, 'config/groovy/adapter/grid/AdapterGridPlanMonitor.groovy'),
(11, 165, 2, 'ws.plan.getList'),
(12, 183, 1, 'config/groovy/adapter/grid/AdapterGridGeneric.groovy'),
(13, 249, 1, 'config/groovy/adapter/grid/AdapterGridGeneric.groovy');

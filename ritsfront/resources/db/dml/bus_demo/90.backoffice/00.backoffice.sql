INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
VALUES(1, 'bus_demo', NULL, NULL, 'jdbc:sqlserver://192.168.243.30;databaseName=bus_demo', 'sa', 'uMYPvMKCGY9vF5+NUzPvIg==', 'dbo',
-- default_metadata_backoffice
'{}',
-- default_metadata_table
'{
	"formHeight": 700,
	"formWidth": 1000,
	"tableHeight": 700,
	"tableWidth": 1000,
	"showIdForm": false,
	"showIdTable": false,
	"showIdSearch": false,
	"gridFilters": true,
	"editable": false
}',
-- default_metadata_column
'{
    "refAddButton": false
}',
true);

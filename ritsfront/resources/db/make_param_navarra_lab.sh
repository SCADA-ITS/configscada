#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl_navarra_lab
sh ./ddl.sh $1
cd ..
cd ddlbackoffice/navarra_lab
sh ./ddl_param.sh $1 $2
cd ../..
cd dml/master
sh ./dml_param_navarra_lab.sh $1
cd ../..
cd dml/navarra_lab
sh ./dml.sh $1
cd ../..
psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$1/controltrafico -c "UPDATE ui.data_source_values SET value='http://10.253.5.83:8082/graphql' WHERE data_source_param_id = 1;"
psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$1/controltrafico -c "UPDATE ui.data_source_values SET value='ws://10.253.5.83:61614' WHERE data_source_param_id = 5;"
psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$1/controltrafico -c "UPDATE ui.data_source_values SET value='http://10.253.5.83:8082/graphql' WHERE data_source_param_id = 8;"
echo Done

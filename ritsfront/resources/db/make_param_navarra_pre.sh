#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl_navarra_pre
sh ./ddl.sh $1
cd ..
cd dml/master
sh ./dml_param_navarra_pre.sh $1
cd ../..
cd dml/navarra_pre
sh ./dml.sh $1
cd ../..
psql postgresql://dbo_controltrafico:fj9.pha9t5h57E@$1/controltrafico -c "UPDATE ui.data_source_values SET value='http://10.251.248.52:8082/graphql' WHERE data_source_param_id = 1;"
psql postgresql://dbo_controltrafico:fj9.pha9t5h57E@$1/controltrafico -c "UPDATE ui.data_source_values SET value='ws://10.251.248.52:61614' WHERE data_source_param_id = 5;"
psql postgresql://dbo_controltrafico:fj9.pha9t5h57E@$1/controltrafico -c "UPDATE ui.data_source_values SET value='http://10.251.248.52:8082/graphql' WHERE data_source_param_id = 8;"
echo Done

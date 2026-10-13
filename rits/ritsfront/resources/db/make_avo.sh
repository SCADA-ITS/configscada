#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl.sh $1
cd ..
cd dml/master
sh ./dml.sh $1
cd ../..
cd dml/avo
sh ./dml.sh $1
cd ../..
psql postgresql://rits:rits@$1/rits -c "UPDATE ui.data_source_values SET value='http://10.133.50.38:8082/graphql' WHERE data_source_param_id = 1;"
psql postgresql://rits:rits@$1/rits -c "UPDATE ui.data_source_values SET value='ws://10.133.50.38:61614' WHERE data_source_param_id = 5;"
psql postgresql://rits:rits@$1/rits -c "UPDATE ui.data_source_values SET value='http://10.133.50.38:8082/graphql' WHERE data_source_param_id = 8;"
echo Done

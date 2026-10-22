#!/bin/bash 

echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl.sh $2:$3
cd ..
cd dml/master
sh ./dml.sh $2:$3
cd ../..
cd dml/$1
sh ./dml.sh $2:$3
cd ../..

psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='http://172.17.0.1:8082/graphql' WHERE data_source_param_id = 1;"
psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='ws://172.17.0.1:61614' WHERE data_source_param_id = 5;"
psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='http://172.17.0.1:8082/graphql' WHERE data_source_param_id = 8;"
echo Done

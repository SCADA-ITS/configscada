#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl_param.sh $2 $3
cd ..
cd dml/master
sh ./dml_param.sh $2 $3
cd ../..
cd dml/$1
sh ./dml_param.sh $2 $3
cd ../..

if [ "$#" -gt 3 ]; then
	psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='http://$4:8082/graphql' WHERE data_source_param_id = 1;"
	psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='ws://$4:61614' WHERE data_source_param_id = 5;"
	psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='http://$4:8082/graphql' WHERE data_source_param_id = 8;"
else
	psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='http://$2:8082/graphql' WHERE data_source_param_id = 1;"
	psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='ws://$2:61614' WHERE data_source_param_id = 5;"
	psql postgresql://rits:rits@$2:$3/rits -c "UPDATE ui.data_source_values SET value='http://$2:8082/graphql' WHERE data_source_param_id = 8;"
fi
echo Done

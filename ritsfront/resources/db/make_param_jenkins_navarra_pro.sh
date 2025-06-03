#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl_navarra_pro
sh ./ddl_param_jenkins.sh $2 $3
if [ $? -ne 0 ]; then
    exit 1  # Sale del script con un código de salida diferente de 0
fi

cd ..
cd dml/master
sh ./dml_param_jenkins_navarra_pro.sh $2 $3
if [ $? -ne 0 ]; then
    exit 1  # Sale del script con un código de salida diferente de 0
fi


cd ../..
cd dml/$1
sh ./dml_param_jenkins.sh $2 $3
if [ $? -ne 0 ]; then
    exit 1  # Sale del script con un código de salida diferente de 0
fi

cd ../..

if [ "$#" -gt 3 ]; then
	psql postgresql://dbo_controltrafico:12hJklfj9.pha9@$4/controltrafico -c "UPDATE ui.data_source_values SET value='http://$4:8082/graphql' WHERE data_source_param_id = 1;"
	psql postgresql://dbo_controltrafico:12hJklfj9.pha9@$4/controltrafico -c "UPDATE ui.data_source_values SET value='ws://$4:61614' WHERE data_source_param_id = 5;"
	psql postgresql://dbo_controltrafico:12hJklfj9.pha9@$4/controltrafico -c "UPDATE ui.data_source_values SET value='http://$4:8082/graphql' WHERE data_source_param_id = 8;"
else
	psql postgresql://dbo_controltrafico:12hJklfj9.pha9@$2/controltrafico -c "UPDATE ui.data_source_values SET value='http://$2:8082/graphql' WHERE data_source_param_id = 1;"
	psql postgresql://dbo_controltrafico:12hJklfj9.pha9@$2/controltrafico -c "UPDATE ui.data_source_values SET value='ws://$2:61614' WHERE data_source_param_id = 5;"
	psql postgresql://dbo_controltrafico:12hJklfj9.pha9@$2/controltrafico -c "UPDATE ui.data_source_values SET value='http://$2:8082/graphql' WHERE data_source_param_id = 8;"
fi
echo Done

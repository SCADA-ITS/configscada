#!/bin/bash

project="$1"
psqlHost="$2"
psqlPort="$3"

echo Executing sql scripts to generate Environment... \(errors that occur are shown below\)
cd ddl_navarra_lab
/bin/bash ./ddl_param_jenkins.sh $psqlHost $psqlPort
if [ $? -ne 0 ]; then
    exit 1  # Sale del script con un código de salida diferente de 0
fi

cd ..
cd ddlrt_navarra_lab
/bin/bash ./ddl_param_jenkins.sh $psqlHost $psqlPort
if [ $? -ne 0 ]; then
    exit 1  # Sale del script con un código de salida diferente de 0
fi

cd ..
cd dml/master
/bin/bash ./dml_param_jenkins_navarra_lab.sh $psqlHost $psqlPort
if [ $? -ne 0 ]; then
    echo "Error ejecutando psql para $eachfile"
    exit 1  # Sale del script con un código de salida diferente de 0
fi
    
cd ..
cd $project
/bin/bash ./dml_param_jenkins.sh $psqlHost $psqlPort
if [ $? -ne 0 ]; then
    echo "Error ejecutando psql para $eachfile"
    exit 1  # Sale del script con un código de salida diferente de 0
fi
    
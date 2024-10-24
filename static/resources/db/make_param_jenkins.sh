#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl_param.sh $2 $3
cd ..

cd dml/$1
sh ./dml_param_jenkins.sh $2 $3
if [ $? -ne 0 ]; then
    exit 1  # Sale del script con un código de salida diferente de 0
fi

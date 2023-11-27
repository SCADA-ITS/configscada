#!/bin/bash 
echo Executing sql scripts to generate bus_demo backoffice environment... \(errors that occur are shown below\)
cd ddl
[ -f ./ddl_mssql.sh ] && /bin/bash ./ddl_mssql.sh $1
cd ..
cd dml/bus_demo
[ -f ./dml_mssql.sh ] && /bin/bash ./dml_mssql.sh $1
echo Done

#!/bin/bash 
echo Executing sql scripts to generate Bratislava environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl.sh
cd ..
cd dml/master
sh ./dml.sh
cd ..
cd maqueta_ausol
sh ./dml.sh
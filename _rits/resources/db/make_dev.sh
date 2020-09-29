#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl.sh
cd ..
cd dml
sh ./dml.sh
cd ..
echo Done

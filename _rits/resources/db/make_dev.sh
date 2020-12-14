#!/bin/bash 
echo Executing sql scripts to generate dev environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl.sh $1
cd ..
cd dml/master
sh ./dml.sh $1
cd ../..
cd dml/dev
sh ./dml.sh $1
cd ../..
echo Done

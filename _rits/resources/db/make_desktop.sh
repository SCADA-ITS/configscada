#!/bin/bash 
echo Executing sql scripts to generate desktop environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl.sh
cd ..
cd dml/master
sh ./dml.sh
cd ../..
cd dml/desktop
sh ./dml.sh
cd ../..
echo Done

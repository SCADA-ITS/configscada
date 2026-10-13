#!/bin/bash 

echo "Number of arguments: $#"
if test "$#" -ne 1; then
		echo "usage:"
		echo "  param1 = name of sql project"
else
	echo Deleting old resource files...
	sudo rm -Rf ~/dev03/backrits/resources/*
	echo Copying new resources...
	cp -R ../resources/master/* ~/dev03/backrits/resources
	cp -R ../resources/project/$1/* ~/dev03/backrits/resources
	sleep 2
	echo Script finalized
fi

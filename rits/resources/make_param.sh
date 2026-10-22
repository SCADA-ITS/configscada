#!/bin/bash 

if [ -n "$1" ]; then # 

	echo Clean target.....
	rm -r ./target/
	mkdir ./target
	
	echo Copy master in target.....
	cp -r ./master/* target/
	echo Copy project in target.....
	cp -r ./project/$1/* target/
else

	echo "No parameters found."
	echo "./make_param.sh dev /home/agomez/repositorio/ritsback/resources"
fi

if [ -n "$2" ]; then # 

	echo Clean RITS_RESOURCES.....
	rm -r $2/*

	echo Copy in RITS_RESOURCES.....
	cp -r ./target/* $2
fi
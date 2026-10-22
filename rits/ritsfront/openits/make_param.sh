#!/bin/bash 

if [ -n "$1" ]; then # 

	echo Copy properties ...
	cp *.properties $1
	echo Copy static files ...
	cp -r static $1
	echo Copy project files ...
	cp -r project $1
else

	echo "No parameters found."
	echo "Usage: ./make_param.sh [target path]"
fi


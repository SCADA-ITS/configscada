#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
for eachfile in $yourfilenames
do
	psql postgresql://rits:rits@localhost:5430/rits -b -f $eachfile > /dev/null
done

#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
for eachfile in $yourfilenames
do
	psql postgresql://rits:rits@127.0.0.1:5430/rits -b -f $eachfile >/dev/null
done

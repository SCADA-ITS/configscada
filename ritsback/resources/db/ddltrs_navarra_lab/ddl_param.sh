#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
for eachfile in $yourfilenames
do
	psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$1:$2/controltrafico -b -f $eachfile >/dev/null
done

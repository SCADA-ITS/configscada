#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
for eachfile in $yourfilenames
do
	psql postgresql://dbo_controltrafico:fj9.pha9t5h57E@$1:$2/controltrafico -b -f $eachfile >/dev/null	
done

#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
for eachfile in $yourfilenames
do
	psql postgresql://dbo_controltrafico:12hJklfj9.pha9@$1/controltrafico -b -f $eachfile >/dev/null
done

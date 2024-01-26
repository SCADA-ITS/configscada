#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
for eachfile in $yourfilenames
do
	psql -v ON_ERROR_STOP=1 postgresql://rits:rits@$1:$2/rits -b -f $eachfile >/dev/null
done

#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
files_count=`echo $yourfilenames | wc -w`
current_file=0
for eachfile in $yourfilenames; do
	current_file=$((current_file+1))
	echo -ne "DML($current_file / $files_count): $eachfile                                                                           \r"
	psql -v ON_ERROR_STOP=1 postgresql://rits:rits@$1:$2/rits -b -f $eachfile >/dev/null
done
echo

#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
files_count=`echo $yourfilenames | wc -w`
current_file=0
for eachfile in $yourfilenames; do
	current_file=$((current_file+1))
	file_name=$(basename "$eachfile")
	echo -ne "DDL($current_file / $files_count): $file_name                               \r"
	psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$1/controltrafico -b -f $eachfile >/dev/null
done
echo

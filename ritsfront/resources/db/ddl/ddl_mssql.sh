#!/bin/bash 
yourfilenames=`find . -name '*.mssql' -print0 | sort -z | xargs -r0`
files_count=`echo $yourfilenames | wc -w`
current_file=0
for eachfile in $yourfilenames; do
	current_file=$((current_file+1))
	echo -ne "DDL($current_file / $files_count): $eachfile                                   \r"
	sqlcmd -S $1 -U sa -P sa__2019 -C -i $eachfile
done
echo

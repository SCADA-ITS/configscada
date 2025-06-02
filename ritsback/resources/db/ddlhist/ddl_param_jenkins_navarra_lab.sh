c#!/bin/bash 

yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`

for eachfile in $yourfilenames; do                                                       
	psql -v ON_ERROR_STOP=1 postgresql://dbo_controltrafico:ddl_param_jenkins.sh@$1:$2/controltrafico -b -f $eachfile >/dev/null
	
	if [ $? -ne 0 ]; then
        exit 1  # Sale del script con un código de salida diferente de 0
    fi
done
echo

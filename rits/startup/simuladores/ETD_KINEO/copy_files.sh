#!/bin/bash 

echo "Number of arguments: $#"
if test "$#" -ne 1; then
		echo "usage:"
		echo "  param1 = name of sql project"
else
    echo Copying files...
    cp  ETD_KINEO.json ../../mango/esqueletos_json/$1/ETD_KINEO.json
    cp  ETD_KINEO.csv ../../mango/csvs_proyecto/$1/Properties/ETD_KINEO.csv
    echo Done!
    sleep 2
fi
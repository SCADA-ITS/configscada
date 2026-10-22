#!/bin/bash 
echo Executing sql scripts to generate Environment... \(errors that occur are shown below\)
cd ddl
sh ./ddl_param.sh $2 $3
cd ..
cd dml/master
sh ./dml_param.sh $2 $3
cd ..
cd $1
sh ./dml_param.sh $2 $3
cd ..
cd ..
echo Updating IPs for Mango.

case $1 in
	maqueta_ausol )
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://192.168.88.119:8080/rest/v1' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=13;"
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://192.168.88.119:8080/rest/v2/login' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=14;"	
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.alarm_configs SET treatment=true WHERE treatment=false;";;	
	bratislava )		
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://10.120.51.248:8080/rest/v1' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=13;"
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://10.120.51.248:8080/rest/v2/login' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=14;"	
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.alarm_configs SET treatment=true WHERE treatment=false;";;	
	maqueta_tg )		
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://192.168.88.172:8080/rest/v1' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=13;"
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://192.168.88.172:8080/rest/v2/login' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=14;"	
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.alarm_configs SET treatment=true WHERE treatment=false;";;
	maqueta_bratislava )
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://10.120.51.248:8080/rest/v1' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=13;"
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://10.120.51.248:8080/rest/v2/login' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=14;"			
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:(R4D7)Revenga@192.168.88.251/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=1;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:(R4D7)Revenga@192.168.88.251/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=2;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:(R4D7)Revenga@192.168.88.251/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=3;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:(R4D7)Revenga@192.168.88.251/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=4;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:(R4D7)Revenga@192.168.88.251/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=5;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=6;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=7;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=8;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=9;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.element_values SET value='http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1' WHERE element_type_id=2 AND element_type_param_id=3 AND param_type_id=1 AND element_id=10;"
		#psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.alarm_configs SET treatment=true WHERE treatment=false;"
		psql postgresql://rits:rits@$2:$3/rits -c "DROP SCHEMA hist CASCADE;"
		psql postgresql://rits:rits@$2:$3/rits -c "CREATE SCHEMA hist;";;
	maqueta_dev )		
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://172.17.0.1:8080/rest/v1' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=13;"
		psql postgresql://rits:rits@$2:$3/rits -c "UPDATE conf.io_controller_module_values SET value='http://172.17.0.1:8080/rest/v2/login' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=14;"
		psql postgresql://rits:rits@$2:$3/rits -c "DROP SCHEMA hist CASCADE;"
		psql postgresql://rits:rits@$2:$3/rits -c "CREATE SCHEMA hist;";;
esac
echo Done

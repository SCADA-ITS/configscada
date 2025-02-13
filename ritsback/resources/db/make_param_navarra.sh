#!/bin/bash
echo "$(tput setaf 6)"
echo "make_param.sh v1.01 (2023-11-15)"
echo "Revenga Ingenieros, S.A."
echo ""
echo "Parameters:"
echo -e "\t- Project: $1"
echo -e "\t- PostgreSQL: $2:$3"
if [ "$4" == "-backup" ]
then
	echo -e "\t- Backup: true"
else
	echo -e "\t- Backup: false"
fi
echo "$(tput setaf 7)"
tables_filename="dml/$1/tables_backup.txt"
backup_schema='rits_backup'
sql=''
tables=''
tables_reverse=''
substr='.'
prefix=''
lines=''
source_schema=''
source_table=''
target_table=''
target_schema=''
project="$1"
psqlHost="$2"
psqlPort="$3"
psqlDB="$2:$3"

backupProc() {

	echo "Deleting schema $backup_schema..." 
	psql postgresql://rits:rits@$psqlDB/controltrafico -c "SET client_min_messages TO WARNING; DROP SCHEMA IF EXISTS $backup_schema CASCADE;" >/dev/null
	
	echo "Creating schema $backup_schema..."
	psql postgresql://rits:rits@$psqlDB/controltrafico -c "SET client_min_messages TO WARNING; CREATE SCHEMA $backup_schema;" >/dev/null
	
	echo "Create data backup in schema $backup_schema for $lines tables..."

	while IFS= read -r line; 
	do
		arrIN=(${line//./ })
		source_schema=${arrIN[0]}
		source_table=${arrIN[1]}
		target_table=$source_schema"___"$source_table

		echo -e "\tBackup table: $source_schema.$source_table"
		sql="CREATE TABLE $backup_schema.$target_table AS (SELECT * FROM $source_schema.$source_table);"
		#echo -e "\t$sql"
		psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "$sql" >/dev/null
	
	done <<< "$tables"
}

restoreProc() {

	echo "Cleaning $lines tables before restore data ..."

	while IFS= read -r line; 
	do
		arrIN=(${line//./ })
		target_schema=${arrIN[0]}
		target_table=${arrIN[1]}
	
		echo -e "\tClean table: $target_schema.$target_table"
		sql="DELETE FROM $target_schema.$target_table"
		#echo -e "\t$sql"
		psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "$sql" >/dev/null 
	done <<< "$tables_reverse"
	
	echo "Restoring data into $lines tables ..."

	while IFS= read -r line; 
	do
		arrIN=(${line//./ })
		source_schema=$backup_schema
		target_schema=${arrIN[0]}
		target_table=${arrIN[1]}
		source_table=$target_schema"___"$target_table

		echo -e "\tRestore table: $target_schema.$target_table"
		sql="INSERT INTO $target_schema.$target_table SELECT * FROM $source_schema.$source_table;"
		#echo -e "\t$sql"
		psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "$sql" >/dev/null
	done <<< "$tables"
}

mainProc() {

	echo Executing sql scripts to generate Environment... \(errors that occur are shown below\)
	cd ddl_navarra
	/bin/bash ./ddl_param.sh $psqlHost $psqlPort
	cd ..
	cd dml/master
	/bin/bash ./dml_param_navarra.sh $psqlHost $psqlPort
	cd ..
	cd $project
	/bin/bash ./dml_param.sh $psqlHost $psqlPort
	cd ../..
	cd updates
	/bin/bash ./dml_param_navarra.sh $psqlHost $psqlPort
	cd ..
	echo Updating IPs for Mango.
	
	case $project in
		maqueta_dev )		
			psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "UPDATE conf.io_controller_module_values SET value='http://172.17.0.1:8080/rest/v1' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=13;"
			psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "UPDATE conf.io_controller_module_values SET value='http://172.17.0.1:8080/rest/v2/login' WHERE module_type_id=1 AND module_id=1 AND module_type_param_id=14;"
			psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "DROP SCHEMA hist CASCADE;"
			psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "CREATE SCHEMA hist;";;
	esac
	
	# git data
	commit=$(git log --pretty=format:'%h %d' --abbrev-commit -n 1)
	author=$(git log --pretty=format:'%an <%ae>' -n 1)
	date=$(git log --pretty=format:'%ci' -n 1)
	
	if [ -n "$commit" ] && [ -n "$author" ] && [ -n "$date" ] 
	then
		psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "INSERT INTO conf.system_param_values (system_param_id, value, enabled, visible, created_at, updated_at) VALUES (10, '$commit', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);"
		psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "INSERT INTO conf.system_param_values (system_param_id, value, enabled, visible, created_at, updated_at) VALUES (11, '$author', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);"
		psql postgresql://dbo_controltrafico:ha9t5h57Emh4h1@$psqlDB/controltrafico -c "INSERT INTO conf.system_param_values (system_param_id, value, enabled, visible, created_at, updated_at) VALUES (12, '$date', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);"
	fi
}

if [ "$4" == "-backup" ] && [ -f "$tables_filename" ]
then
	tables=$(cat "$tables_filename")
	tables_reverse=$(tac "$tables_filename")
	prefix=${tables_filename%%$substr*}
	lines=$(< "$tables_filename" wc -l)
	
	echo ""
	echo "$(tput setaf 2)--- B A C K U P   P R O C E S S (INI)---$(tput setaf 7)"
	backupProc
	echo "$(tput setaf 2)--- B A C K U P   P R O C E S S (END)---$(tput setaf 7)"
fi

echo ""
echo "$(tput setaf 2)--- M A I N   P R O C E S S (INI)---$(tput setaf 7)"
mainProc
echo "$(tput setaf 2)--- M A I N   P R O C E S S (END)---$(tput setaf 7)"

if [ "$4" == "-backup" ] && [ -f "$tables_filename" ]
then
	echo ""
	echo "$(tput setaf 2)--- R E S T O R E   P R O C E S S (INI) ---$(tput setaf 7)"
	restoreProc
	echo "$(tput setaf 2)--- R E S T O R E   P R O C E S S (END) ---$(tput setaf 7)"
fi

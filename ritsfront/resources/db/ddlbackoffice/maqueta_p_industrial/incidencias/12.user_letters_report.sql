DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.user_letter_recipient_titles (
		id serial not null,
		alias varchar null,
		constraint pk_user_letter_recipient_titles primary key (id)
	) tablespace tbl_backoffice;
	
	create table backoffice.user_letter_reports (
		id serial not null,
		incident_id int null,
		report_date timestamptz,
		gg_number varchar null,
		recipient_title_id int null,
		recipient_name varchar null,
		recipient_address varchar null,
		recipient_region varchar null,
		reference varchar null,
		incident_description varchar null,
		
  		constraint pk_user_letter_reports primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_user_letter_reports_incident_id on
	backoffice.user_letter_reports
		using btree (incident_id);
		
	create index idx_user_letter_reports_recipient_title_id on
	backoffice.user_letter_reports
		using btree (recipient_title_id);
		
	alter table backoffice.user_letter_reports add constraint fk_user_letter_report_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.user_letter_reports add constraint fk_user_letter_report_recipient_title_id foreign key (recipient_title_id) references backoffice.user_letter_recipient_titles(id);
  END IF;
END $$;
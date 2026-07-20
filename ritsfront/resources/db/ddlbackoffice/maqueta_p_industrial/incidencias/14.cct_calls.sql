DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
	create table backoffice.cct_call_reasons (
		id serial not null,
		alias varchar null,

  		constraint pk_cct_call_reasons primary key (id)
	) tablespace tbl_backoffice;

	create table backoffice.cct_call_shifts (
		id serial not null,
		alias varchar null,

  		constraint pk_cct_call_shifts primary key (id)
	) tablespace tbl_backoffice;

	create table backoffice.cct_calls (
		id serial not null,
		incident_id int null,
		call_number int null,
		call_datetime timestamptz,
		response_time varchar null,
		reason_id int null,
		call_description varchar null,
		operator varchar null,
		shift_id int null,

  		constraint pk_cct_calls primary key (id)
	) tablespace tbl_backoffice;

	create index idx_cct_calls_incident_id on
	backoffice.cct_calls
		using btree (incident_id);

	create index idx_cct_calls_reason_id on
	backoffice.cct_calls
		using btree (reason_id);

	create index idx_cct_calls_shift_id on
	backoffice.cct_calls
		using btree (shift_id);

	alter table backoffice.cct_calls add constraint fk_cct_calls_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;

	alter table backoffice.cct_calls add constraint fk_cct_calls_reason_id foreign key (reason_id) references backoffice.cct_call_reasons(id);

	alter table backoffice.cct_calls add constraint fk_cct_calls_shift_id foreign key (shift_id) references backoffice.cct_call_shifts(id);

  END IF;
END $$;
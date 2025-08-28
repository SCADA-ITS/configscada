DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_alarms_backoffice') THEN

		create table alarms_backoffice.normalize_procedures (
			id serial not null,
			name varchar null,
			constraint pk_normalize_procedures primary key (id)
		) tablespace tbl_alarms_backoffice;
		
		create table alarms_backoffice.possible_causes (
			id serial not null,
			name varchar null,
			constraint pk_possible_causes primary key (id)
		) tablespace tbl_alarms_backoffice;

		create table alarms_backoffice.responsibilities (
			id serial not null,
			name varchar null,
			constraint pk_responsibilities primary key (id)
		) tablespace tbl_alarms_backoffice;

		create table alarms_backoffice.registers (
			id serial not null,
			alarm_id varchar not null,
			activation timestamptz not null,
			alarm varchar not null,
			element_type varchar not null,
			element varchar not null,
			possible_causes_id int4 null,
			normalize_procedures_id int4 null,
			responsibilities_id int4 null,
			comments varchar null,
			revised_alarm bool null,
			constraint pk_registers primary key (id)
		) tablespace tbl_alarms_backoffice;
		alter table alarms_backoffice.registers add constraint fk_possible_causes_id foreign key (possible_causes_id) references alarms_backoffice.possible_causes(id) ON DELETE CASCADE;
		alter table alarms_backoffice.registers add constraint fk_normalize_procedures_id foreign key (normalize_procedures_id) references alarms_backoffice.normalize_procedures(id) ON DELETE CASCADE;
		alter table alarms_backoffice.registers add constraint fk_responsibilities_id foreign key (responsibilities_id) references alarms_backoffice.responsibilities(id) ON DELETE CASCADE;

  END IF;
END $$;
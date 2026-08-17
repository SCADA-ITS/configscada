DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_transits_bo') THEN

		create table transits_bo.notification_types (
			id serial not null,
			name varchar not null,
			constraint pk_notification_types primary key (id),
			constraint uk_notification_types_name unique (name)
		) tablespace tbl_transits_bo;

		create table transits_bo.administrations (
			id serial not null,
			name varchar not null,
			notification_type_id int not null,
			constraint pk_administrations primary key (id),
			constraint uk_administrations_name unique (name),
			constraint fk_administrations_notification_type foreign key (notification_type_id)
				references transits_bo.notification_types(id)
		) tablespace tbl_transits_bo;

		create index idx_administrations_notification_type_id on
		transits_bo.administrations using btree (notification_type_id);

		create table transits_bo.administration_recipients (
			id serial not null,
			administration_id int not null,
			recipient varchar not null,
			constraint pk_administration_recipients primary key (id),
			constraint uk_administration_recipients unique (administration_id, recipient),
			constraint fk_administration_recipients_administration foreign key (administration_id)
				references transits_bo.administrations(id) on delete cascade
		) tablespace tbl_transits_bo;

		create index idx_administration_recipients_administration_id on
		transits_bo.administration_recipients using btree (administration_id);

  END IF;
END $$;


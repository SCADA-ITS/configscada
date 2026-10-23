DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_transits_bo') THEN

		create table transits_bo.discarded_reasons (
			id serial not null,
			alias varchar null,
			description varchar NULL,
			constraint pk_discarded_reasons primary key (id)
		) tablespace tbs_controltrafico_transits_bo;

  END IF;
END $$;

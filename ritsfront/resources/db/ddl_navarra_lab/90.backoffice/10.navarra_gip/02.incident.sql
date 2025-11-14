DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN

  		create table incidents.affection_details (
			id serial not null,
			alias varchar null,
			constraint pk_affection_details primary key (id)
		) tablespace tbs_controltrafico_incidents;
  
		create table incidents.incidents (
			id serial not null,
			incident_report_id  varchar null,
			incident_type varchar null,
			incident_sub_type varchar null,
			level varchar null,
			location varchar null,
			stretch varchar null,
			lat varchar null,
			lon varchar null,
			comment  varchar null,
			created_at timestamptz default current_timestamp,
			finish_at timestamptz,
			affection_detail_id int null,
			deceassed boolean null,
			num_decesseased int null,
			max_affection varchar null,
			constraint pk_incidents primary key (id)
		) tablespace tbs_controltrafico_incidents;	
		
		create index idx_affection_detail_id on
		incidents.incidents
			using btree (affection_detail_id);
			
		alter table incidents.incidents add constraint fk_affection_detail_id foreign key (affection_detail_id) references incidents.affection_details(id) ON DELETE CASCADE;
		
		
		
		-- Cargar la extensión `tablefunc` si no está ya instalada
	    CREATE EXTENSION IF NOT EXISTS tablefunc;
	
	    -- Crear o reemplazar la vista
	    EXECUTE '
	    CREATE OR REPLACE VIEW incidents.incidents_extended AS
			    SELECT DISTINCT ON (ir.incident_report_id)
		    ir.*, 
		    CASE 
		        WHEN value IN (
		            ''Circulación condicionada'',
		            ''Circulación irregular'',
		            ''Circulación difícil'',
		            ''Circulación interrumpida''
		        )
		        THEN value
		        ELSE ''''
		    END AS end_value
		FROM (
		    SELECT 
		        iirl.incident_report_id,
		        iirlv.value,
		        ROW_NUMBER() OVER (
		            PARTITION BY iirl.incident_report_id
		            ORDER BY CASE iirlv.value
		                WHEN ''Circulación condicionada'' THEN 1
		                WHEN ''Circulación irregular'' THEN 2
		                WHEN ''Circulación difícil'' THEN 3
		                WHEN ''Circulación interrumpida'' THEN 4
		                ELSE 0
		            END DESC
		        ) AS rn
		    FROM rt.ims_incident_report_log_values iirlv
		    JOIN rt.ims_incident_report_logs iirl
		        ON iirlv.incident_report_log_id = iirl.incident_report_log_id
		) sub
		JOIN incidents.incidents ir
		    ON sub.incident_report_id = ir.incident_report_id::integer
		WHERE sub.rn = 1
		ORDER BY ir.incident_report_id';

	END IF;
END $$;




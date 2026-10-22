INSERT INTO conf.plan_schedules (plan_schedule_id,plan_id,cron_expression,alias,description,enabled,visible,created_at,updated_at) VALUES
	 (2,12,'0 * 15-18 ? * FRI *','Riziko kolony D4-5 Ivanka automat','2x text panel výjazd Ivanka',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (3,24,'0 1-5 9 ? * MON,TUE,WED,THU,FRI *','Riziko kolony R7-1 automat vypnut','2x text panel vjazd do BA z R7',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,10,'0 * 7-8 ? * MON,TUE,WED,THU,FRI *','Riziko kolony R7-1 automat','2x text panel vjazd do BA z R7',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

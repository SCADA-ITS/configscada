package com.revenga.rits.back.incident.manager.service;
import java.text.SimpleDateFormat
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;

import groovy.sql.Sql


class BackOfficeAlarmCreate {

	org.apache.logging.log4j.Logger log
	private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5430/rits";
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String DB_USER = "rits";
	private static final String DB_PASSWORD = "rits";
	private static final String DB_SCHEMA = "alarms_backoffice";
	private static final String DB_TABLE = "registers";

	 BackOfficeAlarmCreate(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

	boolean process(Alarm alarm) {
        def sql = null
        try {
            sql = Sql.newInstance(CONNECTION_URL, DB_USER, DB_PASSWORD, DRIVER)

            String alarm_id = null;
            Timestamp activation = null;
            String alarm_name = null;
            String element_type = null;
            String element = null;

            if (alarm != null) {
                if (alarm.getId()){
                    alarm_id = alarm.getId()
                }

                if (alarm.getActivation() != null) {
                    activation = new Timestamp(alarm.getActivation())
                }

                if (alarm.getAlarmConfigId() != null) {
                    alarm_name = EntitiesManager.getInstance().getAlarmConfig(alarm.getAlarmConfigId()).getLabelAlias()
                }
                if (alarm.getSourceElementTypeId() != null) {
                    element_type = EntitiesManager.getInstance().getElementType(alarm.getSourceElementTypeId()).getLabelAlias();
                }

                if (alarm.getSourceElementId() != null) {
                    element = EntitiesManager.getInstance().getElement(alarm.getSourceElementTypeId(), alarm.getSourceElementId()).getAlias()
                }
            }

            def insertSql = 'INSERT INTO ' + DB_SCHEMA + '.' + DB_TABLE + '(alarm_id, activation, alarm, element_type, element) VALUES (?,?,?,?,?);'
            def params = [alarm_id, activation, alarm_name, element_type, element]
            def keys = sql.executeInsert insertSql, params

            log.debug("Alarm " + alarm_id + " inserted on BackOffice.")
            
            return true;

        } catch (SQLException e) {
            log.error(e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        } finally {
            if (sql != null) {
                try {
                    sql.close();
                } catch (SQLException e) {
                    log.error("Error closing connection: " + e.getMessage());
                }
            }
        }

        return false;
    }
    
}
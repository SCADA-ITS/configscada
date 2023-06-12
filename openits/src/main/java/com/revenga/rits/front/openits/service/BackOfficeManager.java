package com.revenga.rits.front.openits.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.BackOfficeProcessor;
import com.revenga.rits.front.openits.model.backoffice.Basic;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BackOfficeManager extends FrontManager {

	private static final String BACKOFFICE_ENABLED = props.get("backoffice.enabled");
	private static final String BACKOFFICE_SHOW_SQL = props.get("backoffice.show_sql");
	private static final String BACKOFFICE_TRACE_TABLE = props.get("backoffice.trace_table");
	
	private Map<Pair<Integer, String>, Map<String, String>> translations = new HashMap<>();
	private Map<Pair<Integer, String>, Table> tables = new HashMap<>();
	
	@Autowired
	BackOfficeService backOfficeService;
	
	@Autowired
	GroovyManager groovyManager;
	
	@Getter
	private boolean showSql;
	
	@Getter
	private boolean traceTable;
	
	@PostConstruct
	private void afterLoad() {
		
		this.enabled = StringUtils.equals(BACKOFFICE_ENABLED, Boolean.TRUE.toString()); 
		this.showSql = StringUtils.equals(BACKOFFICE_SHOW_SQL, Boolean.TRUE.toString());
		this.traceTable = StringUtils.equals(BACKOFFICE_TRACE_TABLE, Boolean.TRUE.toString());
	}
	
	public Table getTable(BackOffice backOffice, String tableName) {
		
		return this.tables.get(new ImmutablePair<>(backOffice.getId(), tableName));
	}
	
	public boolean containsTable(BackOffice backOffice, String tableName) {
		
		return this.tables.containsKey(new ImmutablePair<>(backOffice.getId(), tableName));
	}
	
	public void putTable(BackOffice backOffice, Table table) {
		
		this.tables.put(new ImmutablePair<>(backOffice.getId(), table.getName()), table);
	}
	
	public void clearTables(BackOffice backOffice) {
		
		Set<Pair<Integer, String>> keysToRemove = this.tables.keySet().stream().filter(e -> e.getKey().equals(backOffice.getId())).collect(Collectors.toSet());
		
		if (!CollectionUtils.isEmpty(keysToRemove)) {
			
			this.tables.keySet().removeAll(keysToRemove);
		}
	}
	
	public void translateBasic(BackOffice backOffice, Connection conn, String localeCode, Basic basic) throws SQLException {
		
		basic.setLabelTranslated(translate(backOffice, conn, localeCode, basic.getLabel()));
		basic.setLabelDescriptionTranslated(translate(backOffice, conn, localeCode, basic.getLabelDescription()));
	}
	
	public String translate(BackOffice backOffice, Connection conn, String localeCode, String label) throws SQLException {
		
		String translation = label;
		
		if (!StringUtils.isEmpty(localeCode) && !StringUtils.isEmpty(label) && label.indexOf("LBL_") == 0) {
			
			Map<String, String> translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
			
			if (CollectionUtils.isEmpty(translationsByLocale)) {
				
				loadI18nLabels(localeCode, conn, backOffice);
				
				translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
			}
			
			if (!CollectionUtils.isEmpty(translationsByLocale)) {
				
				translation = translationsByLocale.get(label);
			}
		}
		
		return translation;
	}
	
	private void loadI18nLabels(String localeCode, Connection conn, BackOffice backOffice) throws SQLException {
		
		final String QRY_I18N_LABELS = String.format("SELECT label, translation FROM %s.sg_i18n_labels WHERE locale_code = ?", backOffice.getSchema());
		
		if (!StringUtils.isEmpty(localeCode)) {
			
			Map<String, String> translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
			
			if (translationsByLocale == null) {
				
				try (PreparedStatement stmt = conn.prepareStatement(QRY_I18N_LABELS)) {
					
					stmt.setString(1, localeCode);
					
					ResultSet rs = stmt.executeQuery();
					
					if (rs.next() ) {
						
						translationsByLocale = new HashMap<>();
						
						this.translations.put(new ImmutablePair<>(backOffice.getId(), localeCode), translationsByLocale);
					 
						do {
						
							translationsByLocale.put(rs.getString("label"), rs.getString("translation"));
							
					    } while (rs.next());
					}
				}
			}
		}
	}

	@Override
	protected void load() {
		
		List<BackOffice> backOffices = backOfficeService.getAll();
		
		if (!CollectionUtils.isEmpty(backOffices)) {
			
			for (BackOffice backOffice : backOffices) {
				
				BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, this, null, false);
				
				Instant timeRef = Instant.now();
				
				List<Table> t = backOfficeProcessor.getAll(backOffice.getId());
				
				log.info("Cached backoffice " + backOffice.getId() + " [" + backOffice.getName() + "]: "
						+ (t != null ? t.size() : "0") + " ("
						+ Duration.between(timeRef, Instant.now()).toMillis() + " ms." + ")");
			}
		}
	}

	@Override
	protected void launchScheduled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterStart() {
		// TODO Auto-generated method stub
		
	}
}

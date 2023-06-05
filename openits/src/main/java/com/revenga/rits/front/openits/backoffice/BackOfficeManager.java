package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openitsdao.model.BackOffice;

@Service
public class BackOfficeManager {

	private Map<Pair<Integer, String>, Map<String, String>> translations = new HashMap<>();
	private Map<Pair<Integer, String>, Table> tables = new HashMap<>();
	
	public Table getTable(BackOffice backOffice, String tableName) {
		
		return this.tables.get(new ImmutablePair<>(backOffice.getId(), tableName));
	}
	
	public boolean containsTable(BackOffice backOffice, String tableName) {
		
		return this.tables.containsKey(new ImmutablePair<>(backOffice.getId(), tableName));
	}
	
	public void putTable(BackOffice backOffice, Table table) {
		
		this.tables.put(new ImmutablePair<>(backOffice.getId(), table.getName()), table);
	}
	
	public String getTranslation(BackOffice backOffice, Connection conn, String localeCode, String label) throws SQLException {
		
		String translation = null;
		Map<String, String> translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
		
		if (CollectionUtils.isEmpty(translationsByLocale)) {
			
			loadI18nLabels(localeCode, conn, backOffice);
			
			translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
		}
		
		if (!CollectionUtils.isEmpty(translationsByLocale)) {
			
			translation = translationsByLocale.get(label);
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
}

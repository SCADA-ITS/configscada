package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeys(fields = { "localeCode", "moduleId", "label" })
public class I18nLabel extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3716335926147995691L;
	
	private String localeCode;
	private Long moduleId;
	private String label;
	private String translation;
	
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}

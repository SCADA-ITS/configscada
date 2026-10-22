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
@PrimaryKeys(fields = { "code" })
public class I18nLocale extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -44002338578410713L;
	
	private String code;
	private String name;
	
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}

package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeys(fields = { "id" })
public class I18nModule extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 342112217544626599L;
	
	// region I18N MODULE constants

	public static final long I18N_MODULE_DEFAULT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "i18n.module.default"));
	public static final long I18N_MODULE_OPENITS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "i18n.module.openits"));
	public static final long I18N_MODULE_RITSBACK = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "i18n.module.ritsback"));
	public static final long I18N_MODULE_JASPER = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "i18n.module.jasper"));

	// endregion I18N MODULE constants
	
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}

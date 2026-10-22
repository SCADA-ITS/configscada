package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.vo.AuditLogValueVO;

public class AuditLogValueHelper {

	private AuditLogValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static AuditLogValueVO get(final AuditLogValue auditLogValue) {

		AuditLogValueVO auditLogValueVO = null;

		if (auditLogValue != null) {

			auditLogValueVO = new AuditLogValueVO();

			assign(auditLogValue, auditLogValueVO);
		}

		return auditLogValueVO;
	}

	public static AuditLogValue get(final AuditLogValueVO auditLogValueVO) {

		AuditLogValue auditLogValue = null;

		if (auditLogValueVO != null) {

			auditLogValue = new AuditLogValue();

			if (auditLogValueVO.getLogTypeParamId() != null) {

				Key key = new Key(auditLogValueVO.getLogTypeParamId());

				if (key.getClazz() == LogTypeParam.class) {

					auditLogValue.setLogTypeId(Long.valueOf(key.getId(1)));
					auditLogValue.setLogParamId(Long.valueOf(key.getId(2)));
				}
			}
			auditLogValue.setValue((auditLogValueVO.getValue()));

		}

		return auditLogValue;
	}

	public static void assign(final AuditLogValue auditLogValue, AuditLogValueVO auditLogValueVO) {

		if (ObjectUtils.allNotNull(auditLogValue, auditLogValueVO)) {

			BasicElementHelper.fillBasicProperties(auditLogValueVO, auditLogValue);

			auditLogValueVO.setValue(auditLogValue.getValue());

		}
	}

}

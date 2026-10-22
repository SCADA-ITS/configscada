package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.helper.AuditLogValueHelper;
import com.revenga.rits.back.front.core.model.vo.AuditLogValueVO;
import com.revenga.rits.back.front.core.model.vo.LogTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogValueGraphql extends AuditLogValueVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;
	private AuditLogValue auditLogValue;

	public AuditLogValueGraphql(final AuditLogValue auditLogValue) {

		AuditLogValueHelper.assign(auditLogValue, this);

		this.auditLogValue = auditLogValue;
	}

	@Override
	public LogTypeParamVO getLogTypeParam() {

		if (super.getLogTypeParam() == null) {

			if (auditLogValue != null) {

				LogTypeParam logTypeParam = EntitiesManager.getInstance().getLogTypeParamRepository()
						.get(auditLogValue.getLogTypeId(), auditLogValue.getLogParamId(), false);

				if (logTypeParam != null) {

					super.setLogTypeParam(new LogTypeParamGraphql(logTypeParam));
				}

			} else {

				log.error("auditLogValue has null pointer");
			}
		}

		return super.getLogTypeParam();
	}
}

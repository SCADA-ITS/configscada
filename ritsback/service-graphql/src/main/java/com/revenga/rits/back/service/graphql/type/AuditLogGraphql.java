package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.helper.AuditLogHelper;
import com.revenga.rits.back.front.core.model.vo.AuditLogVO;
import com.revenga.rits.back.front.core.model.vo.AuditLogValueVO;
import com.revenga.rits.back.front.core.model.vo.LogSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.LogTypeVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogGraphql extends AuditLogVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;
	private AuditLog auditLog;

	public AuditLogGraphql(final AuditLog auditLog) {

		AuditLogHelper.assign(auditLog, this);

		this.auditLog = auditLog;
	}

	@Override
	public List<AuditLogValueVO> getAuditLogValues() {

		if (super.getAuditLogValues() == null) {

			if (auditLog != null) {

				List<AuditLogValue> auditLogValues = EntitiesManager.getInstance().getAuditLogValueRepository()
						.getsByAuditLog(auditLog.getLogTypeId(), auditLog.getId(), false);

				if (!CollectionUtils.isEmpty(auditLogValues)) {

					List<AuditLogValueVO> auditLogValueVOs = new ArrayList<>();

					for (AuditLogValue auditLogValue : auditLogValues) {

						auditLogValueVOs.add(new AuditLogValueGraphql(auditLogValue));
					}
					super.setAuditLogValues(auditLogValueVOs);
				}

			} else {

				log.error("auditLog has null pointer");
			}
		}

		return super.getAuditLogValues();
	}

	@Override
	public LogTypeVO getLogType() {

		if (super.getLogType() == null) {

			if (auditLog != null && auditLog.getLogTypeId() != null) {

				LogType logType = EntitiesManager.getInstance().getLogTypeRepository().get(auditLog.getLogTypeId(),
						false);

				if (logType != null) {

					super.setLogType(new LogTypeGraphql(logType));
				}
			} else {

				log.error("auditLog has null pointer");
			}
		}
		return super.getLogType();
	}

	@Override
	public LogSubtypeVO getLogSubtype() {

		if (super.getLogSubtype() == null) {

			if (auditLog != null && auditLog.getLogSubtypeId() != null) {

				LogSubtype logSubtype = EntitiesManager.getInstance().getLogSubtypeRepository()
						.get(auditLog.getLogSubtypeId(), false);

				if (logSubtype != null) {

					super.setLogSubtype(new LogSubtypeGraphql(logSubtype));
				}
			}
		}
		return super.getLogSubtype();
	}

	@Override
	public UserVO getUser() {

		if (super.getUser() == null) {

			if (auditLog != null && auditLog.getUserId() != null) {

				User user = EntitiesManager.getInstance().getUserRepository().get(auditLog.getUserId(), false);

				if (user != null) {

					super.setUser(new UserGraphql(user));
				}
			} else {

				log.error("auditLog has null pointer");
			}
		}
		return super.getUser();
	}
}

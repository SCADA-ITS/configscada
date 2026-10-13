package com.revenga.rits.back.front.core.model.helper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.vo.AuditLogVO;
import com.revenga.rits.back.front.core.model.vo.AuditLogValueVO;

public class AuditLogHelper {

	private AuditLogHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static AuditLogVO get(final AuditLog auditLog) {

		AuditLogVO auditLogVO = null;

		if (auditLog != null) {

			auditLogVO = new AuditLogVO();

			assign(auditLog, auditLogVO);
		}

		return auditLogVO;
	}

	public static AuditLog get(final AuditLogVO auditLogVO) {

		AuditLog auditLog = null;

		if (auditLogVO != null) {

			auditLog = new AuditLog();
			auditLog.generateId();

			if (auditLogVO.getLogTypeId() != null) {

				Key key = new Key(auditLogVO.getLogTypeId());

				if (key.getClazz() == LogType.class) {

					auditLog.setLogTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (auditLogVO.getLogSubtypeId() != null) {

				Key key = new Key(auditLogVO.getLogSubtypeId());

				if (key.getClazz() == LogSubtype.class) {

					auditLog.setLogSubtypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (auditLogVO.getUserId() != null) {

				Key key = new Key(auditLogVO.getUserId());

				if (key.getClazz() == User.class) {

					auditLog.setUserId(Long.valueOf(key.getId(1)));
				}
			}

			auditLog.setTime(Instant.now().toEpochMilli());
			auditLog.setLevelId(LogLevelHelper.get(auditLogVO.getLevel()));
			
			if (auditLogVO.getAuditLogValues() != null) {

				List<AuditLogValue> auditLogValues = new ArrayList<>();

				for (AuditLogValueVO auditLogValueVO : auditLogVO.getAuditLogValues()) {

					AuditLogValue auditLogValue = AuditLogValueHelper.get(auditLogValueVO);
					auditLogValue.setAuditLogId(auditLog.getId());
					
					auditLogValues.add(auditLogValue);
				}

				auditLog.setAuditLogValues(auditLogValues);
			}
		}

		return auditLog;
	}

	public static void assign(final AuditLog auditLog, AuditLogVO auditLogVO) {

		if (ObjectUtils.allNotNull(auditLog, auditLogVO)) {

			auditLogVO.setId(BasicElementHelper.getIdFromElement(auditLog));
			auditLogVO.setTime(auditLog.getTime());
			auditLogVO.setLevel(LogLevelHelper.get(auditLog.getLevelId()));

			if (auditLog.getAuditLogValues() != null) {

				List<AuditLogValueVO> auditLogValuesVO = new ArrayList<>();

				for (AuditLogValue auditLogValue : auditLog.getAuditLogValues()) {

					auditLogValuesVO.add(AuditLogValueHelper.get(auditLogValue));
				}

				auditLogVO.setAuditLogValues(auditLogValuesVO);
			}
		}
	}

}

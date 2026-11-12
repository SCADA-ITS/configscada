// Uncomment following line for groovy file
package config.groovy.backoffice.backoffice.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.service.user.SessionService;

public class CctCallsTrigger extends AbstractBackOfficeTrigger {

	public CctCallsTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onBeforeInsert() throws Exception {
		return true;
	}

	@Override
	public boolean onAfterInsert() throws Exception {
		fillCctCallValuesInDatabase();
		return true;
	}

	@Override
	public boolean onBeforeUpdate() throws Exception {
		return true;
	}

	@Override
	public boolean onAfterUpdate() throws Exception {
		fillCctCallValuesInDatabase();
		return true;
	}

	private void fillCctCallValuesInDatabase() throws Exception {
		Long callId = getCallId();

		if (callId == null) {
			return;
		}

		updateCctCallValues(callId, getCurrentSessionUser());
	}

	private Long getCallId() throws Exception {
		if (this.id != null) {
			return this.id;
		}

		if (this.newRow != null) {
			String rowId = getFieldValue(this.newRow, "id");

			if (rowId != null && !rowId.trim().isEmpty()) {
				return Long.valueOf(rowId);
			}
		}

		if (this.oldRow != null) {
			String rowId = getFieldValue(this.oldRow, "id");

			if (rowId != null && !rowId.trim().isEmpty()) {
				return Long.valueOf(rowId);
			}
		}

		return null;
	}

	private String getCurrentSessionUser() throws Exception {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

		if (!(attributes instanceof ServletRequestAttributes)) {
			return null;
		}

		ServletRequestAttributes requestAttributes =
			(ServletRequestAttributes) attributes;
		HttpSession httpSession =
			requestAttributes.getRequest().getSession(false);

		if (httpSession == null) {
			return null;
		}

		SessionService sessionService =
			WebApplicationContextUtils
				.getRequiredWebApplicationContext(
					requestAttributes.getRequest().getServletContext()
				)
				.getBean(SessionService.class);

		UserSessionUI userSession =
			sessionService.getSession(httpSession.getId());

		if (userSession == null
				|| !UserSessionUI.AUTHORIZED.equals(userSession.getAccess())
				|| userSession.getUser() == null) {
			return null;
		}

		Object userName =
			userSession.getUser().get(UserSessionUI.USER_FIELD_USER);

		if (userName == null || userName.toString().trim().isEmpty()) {
			return null;
		}

		return userName.toString();
	}

	private void updateCctCallValues(Long callId, String operator)
			throws Exception {

		final String qry = String.format(
			"UPDATE %s.cct_calls c " +
			"   SET call_number = COALESCE( " +
			"           c.call_number, " +
			"           ( " +
			"               SELECT COALESCE(MAX(c2.call_number), 0) + 1 " +
			"                 FROM %s.cct_calls c2 " +
			"                WHERE c2.id <> c.id " +
			"           ) " +
			"       ), " +
			"       call_datetime = COALESCE( " +
			"           c.call_datetime, " +
			"           CURRENT_TIMESTAMP " +
			"       ), " +
			"       operator = COALESCE( " +
			"           NULLIF(TRIM(c.operator), ''), " +
			"           ? " +
			"       ) " +
			" WHERE c.id = ?",
			this.backOffice.getSchema(),
			this.backOffice.getSchema()
		);

		PreparedStatement stmt = null;

		try {
			stmt = this.conn.prepareStatement(qry);
			stmt.setString(1, operator);
			stmt.setLong(2, callId);
			stmt.executeUpdate();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
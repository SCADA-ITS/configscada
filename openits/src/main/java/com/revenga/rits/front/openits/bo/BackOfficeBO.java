package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revenga.rits.front.openits.backoffice.BackOfficeProcessor;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistResult;
import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;

@Service
public class BackOfficeBO {

	
	@Autowired
	BackOfficeService backOfficeService;
	
	@Autowired
	SessionBO sessionBO;
	
	@Autowired
	GroovyManager groovyManager;
	
	@Autowired
	BackOfficeManager backOfficeManager;
	
	public List<BackOffice> getList() {
		
		return backOfficeService.getAll();
	}

	public BackOffice getItem(Integer id) {
		
		return backOfficeService.getById(id);
	}
	
	public List<Table> reload(Integer id) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.getAll(id);
	}

	public List<Table> get(Integer backOfficeId, String tableName) {

		return get(backOfficeId, tableName, null);
	}
	
	public List<Table> get(Integer backOfficeId, String tableName, String conditions) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), true);
		
		return backOfficeProcessor.get(backOfficeId, tableName, conditions);	
	}

	public PersistResult persist(Integer backOfficeId, Persist persist) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), true);
		
		return backOfficeProcessor.persist(backOfficeId, persist);
	}
	
	private String getLocaleCode() {
		
		String localeCode = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		
		UserSessionUI userSession = sessionBO.getUserSession(httpSession.getId());
		
		if (userSession != null) {
			
			Object localeObj = userSession.getUser().get(UserSessionUI.USER_LOCALE);
			
			if (localeObj != null) {
				
				Map<String, Object> localeMap = ConversionUtil.objectToMap(localeObj);
				
				localeCode = String.valueOf(localeMap.get(UserSessionUI.USER_LOCALE_CODE));			
			}
		}

		return localeCode;
	}
	
	
}

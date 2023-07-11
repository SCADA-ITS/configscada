package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.revenga.rits.front.openits.backoffice.BackOfficeProcessor;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.model.backoffice.Attachment;
import com.revenga.rits.front.openits.model.backoffice.Request;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestResult;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;

@Service
public class BackOfficeBO {

	private static final String FILE_BASEDIR = "file.basedir";
	
	@Autowired
	BackOfficeService backOfficeService;
	
	@Autowired
	SessionBO sessionBO;
	
	@Autowired
	GroovyManager groovyManager;
	
	@Autowired
	BackOfficeManager backOfficeManager;
	
	@Autowired
	private Environment env;
	
	private String baseDir; 
	
	@PostConstruct
	private void postConstruct() {
		
		this.baseDir = env.getProperty(FILE_BASEDIR);
	}
	
	public List<BackOffice> getList() {
		
		return backOfficeService.getAll();
	}

	public BackOffice getItem(Integer id) {
		
		return backOfficeService.getById(id);
	}
	
	public String translate(Integer id, String label) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.translate(id, label);
	}
	
	public List<Table> reload(Integer id) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.getAll(id);
	}

	public List<Table> get(Integer backOfficeId, String tableName, String conditions) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), true);
		
		return backOfficeProcessor.get(backOfficeId, tableName, conditions);
	}
		
	public List<Attachment> getAttachments(Integer backOfficeId, Attachment.Type attachmentType, String tableName, Integer id) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.getAttachments(backOfficeId, attachmentType, tableName, id);	
	}
	
	public Attachment editAttachment(String attachmentType, Integer backOfficeId, String tableName, Attachment attachment) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.editAttachment(attachmentType, backOfficeId, tableName, attachment);	
	}
	
	public Attachment upload(String attachmentType, Integer backOfficeId, String tableName, Integer id, String groupName, Integer position, MultipartFile multipartFile) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.upload(attachmentType, this.baseDir, backOfficeId, tableName, id, groupName, position, multipartFile);	
	}
	
	public byte[] downloadImage(Integer backOfficeId, String tableName, Integer imageId) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.downloadImage(baseDir, backOfficeId, tableName, imageId);
	}
	
	public Resource downloadAttachment(Integer backOfficeId, String tableName, Integer imageId) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.downloadAttachment(baseDir, backOfficeId, tableName, imageId);
	}
	
	public Attachment deleteAttachment(String attachment, Integer backOfficeId, String tableName, Integer imageId) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), false);
		
		return backOfficeProcessor.deleteAttachment(attachment, baseDir, backOfficeId, tableName, imageId);
	}

	public RequestResult persist(Integer backOfficeId, Request request) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), true);
		
		return backOfficeProcessor.persist(backOfficeId, request);
	}
	
	public RequestResult command(Integer backOfficeId, Request request) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), true);
		
		return backOfficeProcessor.command(backOfficeId, request);
	}
	
	public RequestResult filler(Integer backOfficeId, Request request) {
		
		BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, backOfficeManager, getLocaleCode(), true);
		
		return backOfficeProcessor.filler(backOfficeId, request);
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

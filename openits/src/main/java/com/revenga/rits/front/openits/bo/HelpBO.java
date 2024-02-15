package com.revenga.rits.front.openits.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revenga.rits.front.openitsdao.model.Module;
import com.revenga.rits.front.openitsdao.service.ModuleService;
import com.revenga.rits.front.openitsdao.service.ModuleValueService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class HelpBO extends GenericBO {
	
	private static final String MAIN_HELP_FILE = "index.html";
	
	private static final String MODULE_INI_TEMPLATE = "{{module_%d.ini}}";
	private static final String MODULE_END_TEMPLATE = "{{module_%d.end}}";
	
	private static final String TAG_INI_TEMPLATE = "{{tag_%s.ini}}";
	private static final String TAG_END_TEMPLATE = "{{tag_%s.end}}";
	
	@Autowired
	ModuleService moduleService;
	
	@Autowired
	ModuleValueService moduleValueService;
	
	private static final String STATIC_FOLDER = "static";
	private static final String PROJECT_FOLDER = "project";
	private static final String HELP_FOLDER = "help";
	private static final String ORIGINAL_IMAGES_FOLDER = "images";
	
	private static final String HELP_CUSTOM_MODULE_FOLDER = "help/module_%d";
	private static final String HELP_CUSTOM_TAG_FOLDER = "help/tag_%s";
	
	private static final String ACTIVE_PROFILE = "spring.profiles.active";
	
	private enum TypeHelp {
		
		GENERIC, CUSTOM
	}
	
	@Autowired
	private Environment env;
		
	
	public String getByModuleId(Integer moduleId) {
	
		String html = "";
		Pair<String, TypeHelp> rst = getHtmlHelpModule(moduleId);
		
		if (rst.getValue() == TypeHelp.GENERIC) {
			
			html = extractHtml(rst.getKey(), getModuleLimits(rst.getKey(), moduleId));
		}
		else {
			
			html = rst.getKey();
		}
		
		html = replaceHtmlModuleImages(html, rst.getValue(), moduleId);
		
		return html;
	}
	
	public String getByTag(String tag) {
		
		String html = "";
		Pair<String, TypeHelp> rst = getHtmlHelpTag(tag);
		
		if (rst.getValue() == TypeHelp.GENERIC) {
			
			html = extractHtml(rst.getKey(), getTagLimits(rst.getKey(), tag));
		}
		else {
			
			html = rst.getKey();
		}
		
		html = replaceHtmlTagImages(html, rst.getValue(), tag);
		
		return html;
	}
	
	public List<Module> getModulesWithHelpAvailable(List<Module> modules) {

		List<Module> modulesWithHelpAvailable = new ArrayList<>();
		
		if (!CollectionUtils.isEmpty(modules)) {
			
			Pair<String, TypeHelp> rst = getHtmlHelpModule(null);
				
			for (Module module : modules) {

				if (rst.getValue() == TypeHelp.CUSTOM) {
					
					modulesWithHelpAvailable.add(module);
				}
				else if (!StringUtils.isEmpty(rst.getKey())) {
					
					Pair<Integer, Integer> limits = getModuleLimits(rst.getKey(), module.getId());
					
					if (limits.getLeft() > 0 && limits.getRight() > 0 && limits.getLeft() < limits.getRight()) {
						
						modulesWithHelpAvailable.add(module);
					}
				}
			}
		}
		
		return modulesWithHelpAvailable;
	}
	
	
	private Pair<String, TypeHelp> getHtmlHelpModule(Integer moduleId) {

		String helpHtml = "";
		InputStream is = null;
		TypeHelp typeHelp = TypeHelp.GENERIC;
		
		if (moduleId != null) {
			
			// Get specific help file in project folder
			is = this.getClass().getClassLoader()
					.getResourceAsStream(PROJECT_FOLDER + File.separator + env.getProperty(ACTIVE_PROFILE) + File.separator
							+ String.format(HELP_CUSTOM_MODULE_FOLDER, moduleId) + File.separator + MAIN_HELP_FILE);

			// Get specific help file in static folder
			if (is == null) {

				is = this.getClass().getClassLoader()
						.getResourceAsStream(STATIC_FOLDER + File.separator
								+ String.format(HELP_CUSTOM_MODULE_FOLDER, moduleId) + File.separator + MAIN_HELP_FILE);
			}
		}
		
		if (is != null) {
			
			typeHelp = TypeHelp.CUSTOM;
		}
		

		helpHtml = getHtmlHelp(is);

		return new ImmutablePair<>(helpHtml, typeHelp) ;
	}

	private Pair<String, TypeHelp> getHtmlHelpTag(String tag) {

		String helpHtml = "";
		InputStream is = null;
		TypeHelp typeHelp = TypeHelp.GENERIC;

		// Get specific help file in project folder
		is = this.getClass().getClassLoader()
				.getResourceAsStream(PROJECT_FOLDER + File.separator + env.getProperty(ACTIVE_PROFILE) + File.separator
						+ String.format(HELP_CUSTOM_TAG_FOLDER, tag) + File.separator + MAIN_HELP_FILE);

		// Get specific help file in static folder
		if (is == null) {

			is = this.getClass().getClassLoader()
				.getResourceAsStream(STATIC_FOLDER + File.separator
						+ String.format(HELP_CUSTOM_TAG_FOLDER, tag) + File.separator + MAIN_HELP_FILE);
		}

		if (is != null) {
			
			typeHelp = TypeHelp.CUSTOM;
		}
		

		helpHtml = getHtmlHelp(is);

		return new ImmutablePair<>(helpHtml, typeHelp) ;
	}
	
	private String getHtmlHelp(InputStream is) {
		
		String helpHtml = "";

		// Get specific module from global help in project folder
		if (is == null) {

			is = this.getClass().getClassLoader().getResourceAsStream(PROJECT_FOLDER + File.separator
					+ env.getProperty(ACTIVE_PROFILE) + File.separator + HELP_FOLDER + File.separator + MAIN_HELP_FILE);
		}

		// Get specific module from global help in static folder
		if (is == null) {

			is = this.getClass().getClassLoader().getResourceAsStream(
					STATIC_FOLDER + File.separator + HELP_FOLDER + File.separator + MAIN_HELP_FILE);
		}
		
		if (is != null) {
		
			try (BufferedReader buf = new BufferedReader(new InputStreamReader(is))) {
				
				String line = buf.readLine(); 
				StringBuilder sb = new StringBuilder();

				while(line != null) { 
					sb.append(line).append("\n"); 
					line = buf.readLine(); 
				} 
				
				helpHtml = sb.toString();
				
			} catch (Exception e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return helpHtml;
	}
	
	private Pair<Integer, Integer> getModuleLimits(String html, Integer moduleId) {
	
		return new ImmutablePair<>(html.indexOf(String.format(MODULE_INI_TEMPLATE, moduleId)),
				html.indexOf(String.format(MODULE_END_TEMPLATE, moduleId)));
	}
	
	private Pair<Integer, Integer> getTagLimits(String html, String tag) {
		
		return new ImmutablePair<>(html.indexOf(String.format(TAG_INI_TEMPLATE, tag)),
				html.indexOf(String.format(TAG_END_TEMPLATE, tag)));
	}
	
	private String extractHtml(String html, Pair<Integer, Integer> limits) {
		
		String retHtml = "";
		
		if (limits.getLeft() > 0 && limits.getRight() > 0 && limits.getLeft() < limits.getRight()) {
			
			Document document = Jsoup.parse(html);
			
			document.body().children().remove();
			
			String htmlResult = html.substring(limits.getLeft(), limits.getRight());
			
			htmlResult = htmlResult.substring(htmlResult.indexOf("</p>") + 4);
			
			htmlResult = htmlResult.substring(0, htmlResult.lastIndexOf("<p class"));
			
			document.body().append(htmlResult);
			
			retHtml = document.toString();
		}
		
		return retHtml;
	}
	
	private String replaceHtmlModuleImages(String html, TypeHelp typeHelp, Integer moduleId) {
	
		String htmlResult = html;
		
		// Replace images src
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest httpSession = attr.getRequest();
		
		String srcImgHelpPath;
		
		if (typeHelp == TypeHelp.GENERIC) {
		
			srcImgHelpPath = "src=\"" + httpSession.getContextPath() + "/" + HELP_FOLDER + "/" + ORIGINAL_IMAGES_FOLDER;
		}
		else {
			
			srcImgHelpPath = "src=\"" + httpSession.getContextPath() + "/" + String.format(HELP_CUSTOM_MODULE_FOLDER, moduleId) + "/" + ORIGINAL_IMAGES_FOLDER;
		}
		
		htmlResult = htmlResult.replaceAll("src=\"" + ORIGINAL_IMAGES_FOLDER, srcImgHelpPath);
		
		return htmlResult;
	}
	
	private String replaceHtmlTagImages(String html, TypeHelp typeHelp, String tag) {
		
		String htmlResult = html;
		
		// Replace images src
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest httpSession = attr.getRequest();
		
		String srcImgHelpPath;
		
		if (typeHelp == TypeHelp.GENERIC) {
		
			srcImgHelpPath = "src=\"" + httpSession.getContextPath() + "/" + HELP_FOLDER + "/" + ORIGINAL_IMAGES_FOLDER;
		}
		else {
			
			srcImgHelpPath = "src=\"" + httpSession.getContextPath() + "/" + String.format(HELP_CUSTOM_TAG_FOLDER, tag) + "/" + ORIGINAL_IMAGES_FOLDER;
		}
		
		htmlResult = htmlResult.replaceAll("src=\"" + ORIGINAL_IMAGES_FOLDER, srcImgHelpPath);
		
		return htmlResult;
	}
}

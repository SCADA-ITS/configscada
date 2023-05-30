package com.revenga.rits.front.openits.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.core.util.FileUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.service.CacheManager;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class GroovyManager {
	
	public enum GroovyType { ADAPTER, BACKOFFICE }
	
	Map<String, Pair<GroovyObject, Long>> groovyInstances;
	
	@Autowired
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	CacheManager cacheManager;

//	private static GroovyManager instance;
//
//	public static GroovyManager getInstance() {
//
//		if (instance == null) {
//
//			instance = new GroovyManager();
//		}
//
//		return instance;
//	}

	private GroovyManager() {
		
		this.groovyInstances = Collections.synchronizedMap(new HashMap<>());
	}

	public GroovyObject getGroovyObject(String groovyFile, GroovyType groovyType) {
		
		GroovyObject groovyObject = null;
		
		try {
				
			if (!MapUtils.isEmpty(groovyInstances)) {
				
				Pair<GroovyObject, Long> pair = groovyInstances.get(groovyFile);
				
				if (pair != null) {
					
					Long lastModified = pair.getRight();
					groovyObject = pair.getLeft();
					
					File file = FileUtils.fileFromUri(getClass().getClassLoader().getResource(groovyFile).toURI());
					
					if (file != null) {
						
						BasicFileAttributes basicFileAttributes = Files.readAttributes(
								Paths.get(file.getAbsolutePath()),
								BasicFileAttributes.class);
						
						Long newLastModified = basicFileAttributes.lastModifiedTime().toMillis();
						
						if (lastModified < newLastModified) {
							
							Calendar cal = Calendar.getInstance();
							
							cal.setTimeInMillis(newLastModified);
							
							log.debug("Groovy file " + groovyFile + " changes at " + cal.getTime().toString());
					
							groovyObject = loadGroovy(groovyFile, groovyType);	
						}
					}
				}
			}
			
			if (groovyObject == null) {
				
				groovyObject = loadGroovy(groovyFile, groovyType);
			}
		
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return groovyObject;
	}
	
	private GroovyObject loadGroovy(String groovyFile, GroovyType groovyType) {
		
		GroovyObject groovyObject = null;
		
		try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
			
			//String strGroovy = Files.readString(Paths.get(getClass().getClassLoader().getResource(groovyFile).toURI()));
			
			InputStream is = getClass().getClassLoader().getResourceAsStream(groovyFile);
			
			String strGroovy = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			
			Class<?> groovy = classLoader.parseClass(strGroovy); 

			if (groovyType.equals(GroovyType.ADAPTER)) {
				
				groovyObject = (GroovyObject) groovy.getDeclaredConstructor(CacheManager.class, VO2UIAdapter.class, org.apache.logging.log4j.Logger.class)
						.newInstance(cacheManager, vo2uiAdapter, log);	
			}
			else if (groovyType.equals(GroovyType.BACKOFFICE)) {
				
				groovyObject = (GroovyObject) groovy.getDeclaredConstructor(org.apache.logging.log4j.Logger.class)
						.newInstance(log);
			}
			
			
			if (groovyObject != null) {
			
				groovyInstances.put(groovyFile, new MutablePair<>(groovyObject, Instant.now().toEpochMilli()));
			}
			
		} catch (IOException | IllegalArgumentException | SecurityException | InstantiationException
				| IllegalAccessException | InvocationTargetException | NoSuchMethodException | CompilationFailedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return groovyObject;
	}
}

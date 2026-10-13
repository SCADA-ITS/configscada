package com.revenga.rits.back.data.core.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GroovyManager {
	
	Map<String, Pair<GroovyObject, Long>> groovyInstances;

	private static GroovyManager instance;

	public static GroovyManager getInstance() {

		if (instance == null) {

			instance = new GroovyManager();
		}

		return instance;
	}

	private GroovyManager() {
		
		this.groovyInstances = Collections.synchronizedMap(new HashMap<>());
	}

	public GroovyObject getGroovyObject(String groovyFile) {
		
		GroovyObject groovyObject = null;
				
		if (!MapUtils.isEmpty(groovyInstances)) {
			
			Pair<GroovyObject, Long> pair = groovyInstances.get(groovyFile);
			
			if (pair != null) {
				
				Long lastModified = pair.getRight();
				BasicFileAttributes basicFileAttributes = ResourcesUtil.getFileAttributes(groovyFile);
				Long newLastModified = basicFileAttributes.lastModifiedTime().toMillis();
				
				if (lastModified < newLastModified) {
					
					Calendar cal = Calendar.getInstance();
					
					cal.setTimeInMillis(newLastModified);
					
					log.debug("Groovy file " + groovyFile + " changes at " + cal.getTime().toString());
			
					groovyObject = loadGroovy(groovyFile);	
				}
				else {
					
					groovyObject = pair.getLeft();
				}
			}
		}
		
		if (groovyObject == null) {
			
			groovyObject = loadGroovy(groovyFile);
		}
		
		return groovyObject;
	}
	
	private GroovyObject loadGroovy(String groovyFile) {
		
		GroovyObject groovyObject = null;
		
		try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
			
			Class<?> groovy = classLoader.parseClass(ResourcesUtil.getFile(groovyFile));

			groovyObject = (GroovyObject) groovy.getDeclaredConstructor(org.apache.logging.log4j.Logger.class)
					.newInstance(log);
			
			if (groovyObject != null) {
			
				groovyInstances.put(groovyFile, new MutablePair<>(groovyObject, Instant.now().toEpochMilli()));
			}
			
		} catch (IOException | IllegalArgumentException | SecurityException | InstantiationException
				| IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return groovyObject;
	}
}

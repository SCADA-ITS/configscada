package com.revenga.rits.back.data.core.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
	
	private final static String GROOVY_EXTENSION = ".groovy";

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
	
	public Object executeGroovy(String fileName, String filePath, String packageName, String methodName, Object... methodParams) {

		if (!StringUtils.isEmpty(fileName) && !StringUtils.isEmpty(methodName)) {

			try {

				if (!fileName.contains(GROOVY_EXTENSION)) {

					fileName = fileName.concat(GROOVY_EXTENSION);
				}

				if (!StringUtils.isEmpty(packageName)) {

					Class<?> clazz = null;
					
					try {
						
						clazz = Class.forName(packageName + "." + fileName.substring(0, fileName.indexOf(".")));

					} catch(ClassNotFoundException ex) {
						
						log.debug("Class not found, ready to try to execute Groovy");
					}

					if (clazz != null) {

						// We have a java class, creating an object:
						Object object = clazz.getDeclaredConstructor(org.apache.logging.log4j.Logger.class)
								.newInstance(log);

						if (object != null) {

							Method method = Arrays.stream(object.getClass().getMethods())
		                            .filter(m -> m.getName().equals(methodName))
		                            .findFirst()
		                            .orElse(null);

							if (method != null && checkParameterTypes(method, methodParams)) {

								return method.invoke(object, methodParams);
							}
						}
					}
				}

				if (!StringUtils.isEmpty(filePath)) {

					GroovyObject groovyObj;

					fileName = filePath.concat(fileName);

					if (ResourcesUtil.getFile(fileName) != null) {

						groovyObj = getGroovyObject(fileName);

						if (groovyObj != null) {

							return groovyObj.invokeMethod(methodName, methodParams);
						}
					}
				}
			}

			catch (Exception e) {

				log.error("Error on java class or groovy execution");
				log.debug(e.getMessage());
				
				return null;
			}
		}
		
		log.error("Not enought info for Groovy execution");

		return null;
	}
	
	private boolean checkParameterTypes(Method method, Object... args) {
        
		if (args == null) {
			
			return true;
		}
		
		Class<?>[] paramTypes = method.getParameterTypes();
		
        if (paramTypes.length != args.length) {
        	
            return false;
        }
        
        for (int i = 0; i < paramTypes.length; i++) {
        	
            if (!paramTypes[i].isInstance(args[i])) {
            	
                return false;
            }
        }
        
        return true;
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

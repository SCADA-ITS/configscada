package com.revenga.rits.back.adapter.lib.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.util.ResourcesUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SessionAdapter {
	
	Map<String, Pair<Long, Object>> mapAdapters = Collections.synchronizedMap(new HashMap<String, Pair<Long, Object>>());
	
	public Object getAdapter(String name, Class<?> classToBeBound) {
		
		Object retAdapter = null;
		
		BasicFileAttributes basicFileAttributes = ResourcesUtil.getFileAttributes(name);
		
		Long newLastModified = basicFileAttributes.lastModifiedTime().toMillis();
		
		if (mapAdapters.containsKey(name)) {
			
			Pair<Long, Object> adapter = mapAdapters.get(name);
			
			Long lastModified = adapter.getKey();
			
			if (lastModified < newLastModified) {
				
				retAdapter = loadAdapter(name, classToBeBound, newLastModified);
				
				Calendar cal = Calendar.getInstance();
				
				cal.setTimeInMillis(newLastModified);
				
				log.debug("Adapter file " + name + " changes at " + cal.getTime().toString());
			}
			else {
				
				retAdapter = adapter.getValue();
			}
		}
		else {
			
			retAdapter = loadAdapter(name, classToBeBound, newLastModified);
		}
		
		return retAdapter;
	}
	
	private Object loadAdapter(String name, Class<?> classToBeBound, Long newLastModified) {
		
		Object object = null;
		
		try {
		
			InputStream is = ResourcesUtil.getResource(name);
			
			JAXBContext context = JAXBContext.newInstance(classToBeBound);
			object = context.createUnmarshaller().unmarshal(is);
			
			is.close();
			
			mapAdapters.put(name, new MutablePair<Long, Object>(newLastModified, object));
			
		} catch (JAXBException | IOException e) {
			log.error("name = " + name);
			log.error("classToBeBound = " + classToBeBound);
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return object;
		
		
	}
}

package com.revenga.rits.back.entities.lib.repository.entity;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomMap {

	Map<String, Pair<Map<String, BasicElement>, Long>> map = Collections.synchronizedMap(new HashMap<String, Pair<Map<String, BasicElement>, Long>>());

	Class<?> clazz;
	String[] fields;
	Long bufferRententionTime;

	public CustomMap(Long bufferRententionTime, Class<?> clazz, String... fields) {
		super();
		this.bufferRententionTime = bufferRententionTime;
		this.clazz = clazz;
		this.fields = fields;
	}
	
	public Map<String, BasicElement> get(String[] ids) {
		
		Pair<Map<String, BasicElement>, Long> entry = getEntry(ids);
		
		if (entry != null && isUpdate(entry)) {
				
			return entry.getLeft();	
		}
		
		return null;
	}
	
	private Pair<Map<String, BasicElement>, Long> getEntry(String[] ids) {
				
		return map.get(Arrays.toString(ids));
	}
	
	private boolean isUpdate(Pair<Map<String, BasicElement>, Long> entry) {
		
		return (Duration.between(Instant.ofEpochMilli(entry.getRight()), Instant.now()).toMillis() < bufferRententionTime);
	}
		
	public BasicElement put(String[] ids, BasicElement basicElement) {
		
		Pair<Map<String, BasicElement>, Long> entry = getEntry(ids);
		
		if (entry == null) {
			
			Map<String, BasicElement> basicElementsMap = Collections.synchronizedMap(new HashMap<String, BasicElement>());
			
			Key keyBE = new Key(basicElement);
			basicElementsMap.put(Arrays.toString(keyBE.getArrayOfIds()), basicElement);
			
			entry = new MutablePair<>(basicElementsMap, Instant.now().toEpochMilli());
						
			map.put(Arrays.toString(ids), entry);
				
			return basicElement;
		}
		else {
				
			Map<String, BasicElement> basicElements = entry.getLeft();
			
			if (!MapUtils.isEmpty(basicElements)) {
				
				Key keyBE = new Key(basicElement);
				
				if (basicElements.get(Arrays.toString(keyBE.getArrayOfIds())) == null) {
					
					entry.getKey().put(Arrays.toString(keyBE.getArrayOfIds()), basicElement);
					entry.setValue(Instant.now().toEpochMilli());
									
					return basicElement;
				}
				else {
					
					if (!isUpdate(entry)) {
						
						if (map.remove(Arrays.toString(ids)) != null) {
							
							this.put(ids, basicElement);
						}
					}
				}
			}
		}
		
		return null;
	}

	public String getName() {

		return clazz.getSimpleName() + ":" + Arrays.toString(fields);
	}
}

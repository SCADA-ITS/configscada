package com.revenga.rits.back.equipment.manager.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.equipment.manager.data.ThreadDataWriter;
import com.revenga.rits.back.equipment.manager.data.ThreadDataWriter.Action;
import com.revenga.rits.back.equipment.manager.executor.service.ElementValueService;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GroovyHelper {

	private GroovyHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static Command sendElementToHistoriclManager(Element element) {

		return EntitiesManager.getInstance().sendElementToHistoriclManager(element);
	}
	
	public static void setChangeMeasure(Element element) {
		
		ElementValueService elementValueService = new ElementValueService(null);
		elementValueService.setChangeMeasure(element);
	}

	public static List<ElementHierarchy> getsByParent(Long parentElementTypeId, Long parentElementId, Boolean fillObject) {

		List<BasicElement> basicElements = ((ElementHierarchiesRepository) EntitiesManager.getInstance()
				.getRepositoryData().getRepository(ElementHierarchy.class))
				.getsFromCache(new String[] { String.valueOf(parentElementTypeId), String.valueOf(parentElementId),
						null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public static boolean putElementValues(ElementValue[] elementValues) {

		boolean res = false;
		
		try {
			
			if (!ArrayUtils.isEmpty(elementValues)) {
				
				ThreadDataWriter threadDataWriter = (ThreadDataWriter) EntitiesManager.getInstance().getRootData().getThread(ThreadDataWriter.class);
				
				if (threadDataWriter != null) {
					
					Arrays.asList(elementValues).forEach(elementValue -> threadDataWriter.add(elementValue, Action.PUT));
				}
			}
			
			res = true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
	
	public static List<ElementHierarchy> getsByChild(Long childElementTypeId, Long childElementId) {

		return ((ElementHierarchiesRepository) EntitiesManager.getInstance().getRepositoryData()
				.getRepository(ElementHierarchy.class)).getsByChild(childElementTypeId, childElementId, false);
	}
	
	public static Command sendElementStateToIllumination(ElementState elementState) {

		return EntitiesManager.getInstance().sendElementStateToIllumination(elementState);
	}
	
	public static Command sendElementStateToVentilation(ElementState elementState) {

		return EntitiesManager.getInstance().sendElementStateToVentilation(elementState);
	}
	
	public static Command sendElementValuesToIllumination(List<ElementValue> elementValues) {

		return EntitiesManager.getInstance().sendElementValuesToIllumination(elementValues);
	}
	
	public static Command sendElementValuesToVentilation(List<ElementValue> elementValues) {

		return EntitiesManager.getInstance().sendElementValuesToVentilation(elementValues);
	}
	
	public static Command sendAlarmToIllumination(Alarm alarm) {

		return EntitiesManager.getInstance().sendAlarmToIllumination(alarm);
	}
	
	public static Command sendAlarmToVentilation(Alarm alarm) {

		return EntitiesManager.getInstance().sendAlarmToVentilation(alarm);
	}
}

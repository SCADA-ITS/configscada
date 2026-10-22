package com.revenga.rits.back.traffic.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.traffic.manager.model.Detector;
import com.revenga.rits.back.traffic.manager.model.Section;
import com.revenga.rits.back.traffic.manager.model.TravelTime;
import com.revenga.rits.back.traffic.manager.model.helper.TrafficDataHelper;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TrafficDataEntitiesManager {

	private static TrafficDataEntitiesManager instance;

	@Getter
	private Map<Long, Detector> detectors;
	@Getter
	private Map<Long, Section> sections;
	@Getter
	private Map<Long, TravelTime> travelTimes;
	
	
	/**
	 * Structure: List<Pair<Period, Map<elementId, section>>>
	 */
	@Getter
	private List<Pair<Long, Map<Long, Section>>> sectionsHistory = new ArrayList<>();

	public static TrafficDataEntitiesManager getInstance() {

		if (instance == null) {

			instance = new TrafficDataEntitiesManager();
		}

		return instance;
	}

	private TrafficDataEntitiesManager() {
	}

	public void loadTravelTimes(List<ElementValue> elementValueConfigs, List<ElementHierarchy> elementHierarchies) {

		if (travelTimes != null) {

			travelTimes.clear();

		} else {

			travelTimes = new HashedMap<>();
		}

		while (!elementValueConfigs.isEmpty()) {

			Long elementId = null;

			for (int i = elementValueConfigs.size() - 1; i >= 0 && elementId == null; i--) {

				elementId = elementValueConfigs.get(i).getElementId();

				if (elementId == null) {

					elementValueConfigs.remove(i);
				}
			}

			if (elementId != null) {

				Long id = elementId;

				List<ElementValue> configValues = elementValueConfigs.stream().filter(b -> b.getElementId().equals(id))
						.collect(Collectors.toList());

				elementValueConfigs.removeAll(configValues);

				TravelTime travelTime = (TravelTime) TrafficDataHelper.get(TravelTime.class, configValues, null);

				log.debug("Put travel time:" + travelTime);
				travelTimes.put(elementId, travelTime);
			}
		}

		if (!CollectionUtils.isEmpty(elementHierarchies)) {

			for (ElementHierarchy elementHierarchy : elementHierarchies) {

				TravelTime travelTime = travelTimes.get(elementHierarchy.getParentElementId());
				Section section = sections.get(elementHierarchy.getChildElementId());

				if (ObjectUtils.allNotNull(travelTime, section)) {

					if (travelTime.getSections() == null) {

						travelTime.setSections(new ArrayList<>());
					}

					travelTime.getSections().add(section);
				}
			}
		}

	}

	public void loadSections(List<ElementValue> elementValueConfigs, List<ElementHierarchy> elementHierarchies) {

		if (sections != null) {

			sections.clear();

		} else {

			sections = new HashedMap<>();
		}

		while (!elementValueConfigs.isEmpty()) {

			Long elementId = null;

			for (int i = elementValueConfigs.size() - 1; i >= 0 && elementId == null; i--) {

				elementId = elementValueConfigs.get(i).getElementId();

				if (elementId == null) {

					elementValueConfigs.remove(i);
				}
			}

			if (elementId != null) {

				Long id = elementId;

				List<ElementValue> configValues = elementValueConfigs.stream().filter(b -> b.getElementId().equals(id))
						.collect(Collectors.toList());

				elementValueConfigs.removeAll(configValues);

				Section section = (Section) TrafficDataHelper.get(Section.class, configValues, null);

				log.debug("Put section:" + section);
				sections.put(elementId, section);
			}
		}

		if (!CollectionUtils.isEmpty(elementHierarchies)) {

			for (ElementHierarchy elementHierarchy : elementHierarchies) {

				Section section = sections.get(elementHierarchy.getParentElementId());
				Detector detector = detectors.get(elementHierarchy.getChildElementId());

				if (ObjectUtils.allNotNull(section, detector)) {

					if (section.getDetectors() == null) {

						section.setDetectors(new ArrayList<>());
					}

					section.getDetectors().add(detector);
				}

			}
		}
	}
	
	public void addSectionsHistory(Long currentPeriod) {
			
		Integer travelTimePeriods = EntitiesManager.getInstance().getTravelTimePeriods();
		
		if (sections != null && travelTimePeriods != null && travelTimePeriods > 0) {
			
			Set<Entry<Long, Section>> entries = sections.entrySet();
			
			HashMap<Long, Section> sectionsCopy = (HashMap<Long, Section>) entries.stream()
			  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			
			Pair<Long, Map<Long, Section>> pair = new MutablePair<>(currentPeriod, sectionsCopy);
			
			sectionsHistory.add(pair);
			
			while (sectionsHistory.size() > travelTimePeriods) {
				
				sectionsHistory.remove(0);
			}
		}
	}

	public void loadDetector(List<ElementValue> elementValueConfigs, List<ElementValue> elementValueMeasures) {

		if (detectors != null) {

			detectors.clear();

		} else {

			detectors = new HashedMap<>();
		}

		while (!elementValueConfigs.isEmpty()) {

			Long elementId = null;

			for (int i = elementValueConfigs.size() - 1; i >= 0 && elementId == null; i--) {

				elementId = elementValueConfigs.get(i).getElementId();

				if (elementId == null) {

					elementValueConfigs.remove(i);
				}
			}

			if (elementId != null) {

				Long id = elementId;

				List<ElementValue> configValues = elementValueConfigs.stream().filter(b -> b.getElementId().equals(id))
						.collect(Collectors.toList());

				elementValueConfigs.removeAll(configValues);

				List<ElementValue> measuresValues = elementValueMeasures.stream()
						.filter(elementValue -> id.equals(elementValue.getElementId())).collect(Collectors.toList());

				elementValueMeasures.removeAll(measuresValues);

				Detector detector = (Detector) TrafficDataHelper.get(Detector.class, configValues, measuresValues);
				log.debug("Put detector:" + detector);

				detectors.put(elementId, detector);
			}

		}

	}
}

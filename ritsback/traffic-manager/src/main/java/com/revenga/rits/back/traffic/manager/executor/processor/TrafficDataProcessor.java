package com.revenga.rits.back.traffic.manager.executor.processor;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.data.core.model.command.SetHistoricElementCommand;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.traffic.manager.executor.TrafficExecutor;
import com.revenga.rits.back.traffic.manager.model.Detector;
import com.revenga.rits.back.traffic.manager.model.Section;
import com.revenga.rits.back.traffic.manager.model.TravelTime;
import com.revenga.rits.back.traffic.manager.model.helper.TrafficDataHelper;
import com.revenga.rits.back.traffic.manager.service.DetectorService;
import com.revenga.rits.back.traffic.manager.service.EntitiesManager;
import com.revenga.rits.back.traffic.manager.service.SectionService;
import com.revenga.rits.back.traffic.manager.service.TravelTimeService;
import com.revenga.rits.back.traffic.manager.service.TrafficDataEntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TrafficDataProcessor {

	GenericExecutor genericExecutor;
	DetectorService detectorService;
	SectionService sectionService;
	TravelTimeService travelTimeService;

	Map<Long, Long> lastDetectorsDate;

	public TrafficDataProcessor(TrafficExecutor trafficExecutor) {

		this.genericExecutor = trafficExecutor;
		this.detectorService = new DetectorService(EntitiesManager.getInstance().getPeriod());
		this.sectionService = new SectionService(EntitiesManager.getInstance().getPeriod(),
				EntitiesManager.getInstance().getLevelOfServices(), EntitiesManager.getInstance().getLosArrays(),
				EntitiesManager.getInstance().getLosSoftenedArrays());
		this.travelTimeService = new TravelTimeService();

		this.lastDetectorsDate = new HashedMap<>();
	}

	private boolean processDetector(final List<Detector> detectors, final Long currentPeriod) {

		boolean ok = false;
		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = null;
		SetHistoricElementCommand setHistoricElementCommand = null;

		if (currentPeriod != null && !CollectionUtils.isEmpty(detectors)) {

			try {
				List<Detector> detectorsToHistory = new ArrayList<>();
				for (Detector detector : detectors) {

					detectorService.init(detector);

					if (detectorService.process(currentPeriod) && checkDetectorDate(detector)) {
						detectorsToHistory.add(detector);
					}

				}

				if (!CollectionUtils.isEmpty(detectors)) {

					List<Detector> detectorsToEquipmenManager = new ArrayList<>();

					for (Detector detector : detectors) {

						Detector detectorOut = new Detector();
						detectorOut.setId(detector.getId());
						detectorOut.setFailData(detector.getFailData());
						detectorOut.setIntensity(detector.getIntensity());
						detectorOut.setVehLengthPercent(detector.getVehLengthPercent());
						detectorOut.setVehSpeedPercent(detector.getVehSpeedPercent());
						detectorOut.setVehPaPercent(detector.getVehPaPercent());
						detectorsToEquipmenManager.add(detectorOut);

					}

					setElementMeasuresChangeCommand = TrafficDataHelper
							.getSetElementMeasuresChangeCommand(detectorsToEquipmenManager);

					if (setElementMeasuresChangeCommand != null) {

						EntitiesManager.getInstance().sendCommand(setElementMeasuresChangeCommand);

					}

					setHistoricElementCommand = TrafficDataHelper.getSetHistoricElementCommandfinal(detectorsToHistory);

					if (setHistoricElementCommand != null) {

						EntitiesManager.getInstance().sendCommand(setHistoricElementCommand);
					}

					ok = true;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}

		}
		return ok;
	}

	private boolean processSections(final List<Section> sections, final Long currentPeriod) {

		boolean ok = false;
		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();
		SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();
		SetHistoricElementCommand setHistoricElementCommand = null;

		if (currentPeriod != null && !CollectionUtils.isEmpty(sections)) {

			try {
				List<Section> sectionsToHistory = new ArrayList<>();
				
				for (Section section : sections) {

					sectionService.init(section);

					if (sectionService.process(currentPeriod).booleanValue()) {
						
						sectionsToHistory.add(section);
					}
					
					ElementState elementState = new ElementState();
					elementState.setElementId(section.getId());
					elementState.setElementTypeId(Section.ELEMENT_TYPE);

					sectionService.init(section);

					if (sectionService.process(currentPeriod).booleanValue()) {

						elementState.setStateId(Section.STATE_VALID);
						setElementMeasuresChangeCommand.addElement(TrafficDataHelper.get(section));

					} else {

						elementState.setStateId(Section.STATE_INVALID);
					}
					setElementStatesChangeCommand.addElementState(elementState);
					
					setHistoricElementCommand = TrafficDataHelper.getSetHistoricElementCommandfinal(sectionsToHistory);
				}

				if (!CollectionUtils.isEmpty(setElementStatesChangeCommand.getElementStates())) {

					EntitiesManager.getInstance().sendCommand(setElementStatesChangeCommand);
				}

				if (!CollectionUtils.isEmpty(setElementMeasuresChangeCommand.getElements())) {

					EntitiesManager.getInstance().sendCommand(setElementMeasuresChangeCommand);
				}
				
				if (setHistoricElementCommand != null
						&& !CollectionUtils.isEmpty(setHistoricElementCommand.getElements())) {

					EntitiesManager.getInstance().sendCommand(setHistoricElementCommand);
				}

				ok = true;

			} catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return ok;
	}

	private Boolean processTravelTimes(final List<TravelTime> travelTimes, final Long currentPeriod) {

		Boolean ok = false;
		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();
		SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();
		SetHistoricElementCommand setHistoricElementCommand = null;

		if (currentPeriod != null && !CollectionUtils.isEmpty(travelTimes)) {

			try {
				List<TravelTime> travelTimesToHistory = new ArrayList<>();
				
				for (TravelTime travelTime : travelTimes) {

					ElementState elementState = new ElementState();
					elementState.setElementId(travelTime.getId());
					elementState.setElementTypeId(TravelTime.ELEMENT_TYPE);

					travelTimeService.init(travelTime);
					
					if (travelTimeService.process(currentPeriod).booleanValue()) {

						elementState.setStateId(Section.STATE_VALID);
						setElementMeasuresChangeCommand.addElement(TrafficDataHelper.get(travelTime));
						
						travelTimesToHistory.add(travelTime);

					} else {

						elementState.setStateId(Section.STATE_INVALID);
					}
					
					setElementStatesChangeCommand.addElementState(elementState);
					
					setHistoricElementCommand = TrafficDataHelper.getSetHistoricElementCommandfinal(travelTimesToHistory);
				}

				if (!CollectionUtils.isEmpty(setElementStatesChangeCommand.getElementStates())) {

					EntitiesManager.getInstance().sendCommand(setElementStatesChangeCommand);
				}

				if (!CollectionUtils.isEmpty(setElementMeasuresChangeCommand.getElements())) {

					EntitiesManager.getInstance().sendCommand(setElementMeasuresChangeCommand);
				}
				
				if (setHistoricElementCommand != null
						&& !CollectionUtils.isEmpty(setHistoricElementCommand.getElements())) {

					EntitiesManager.getInstance().sendCommand(setHistoricElementCommand);
				}
				
				ok = true;
			} catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return ok;
	}

	public void processTrafficData() {

		Long currentPeriod = Instant.now().toEpochMilli();
		Instant startInstant = Instant.now();

		currentPeriod -= (currentPeriod % (EntitiesManager.getInstance().getPeriod() * 60000));
		log.info("Init procces period: " + Instant.ofEpochMilli(currentPeriod).toString());

		log.debug("Init Load");

		TrafficDataEntitiesManager.getInstance().loadDetector(
				new ArrayList<>(EntitiesManager.getInstance().getElementValueConfig(Detector.ELEMENT_TYPE)),
				new ArrayList<>(EntitiesManager.getInstance().getElementValueMeasure(Detector.ELEMENT_TYPE)));

		TrafficDataEntitiesManager.getInstance().loadSections(
				new ArrayList<>(EntitiesManager.getInstance().getElementValueConfig(Section.ELEMENT_TYPE)),
				EntitiesManager.getInstance().getHierarchiesByElementType(Section.ELEMENT_TYPE, Detector.ELEMENT_TYPE));

		TrafficDataEntitiesManager.getInstance().loadTravelTimes(
				new ArrayList<>(EntitiesManager.getInstance().getElementValueConfig(TravelTime.ELEMENT_TYPE)),
				EntitiesManager.getInstance().getHierarchiesByElementType(TravelTime.ELEMENT_TYPE, Section.ELEMENT_TYPE));

		log.debug("End Load :  in: " + Duration.between(startInstant, Instant.now()).toMillis() + " ms");

		List<Detector> detectors = new ArrayList<>(TrafficDataEntitiesManager.getInstance().getDetectors().values());
		if (processDetector(detectors, currentPeriod)) {

			List<Section> sections = new ArrayList<>(TrafficDataEntitiesManager.getInstance().getSections().values());

			if (processSections(sections, currentPeriod)) {
				
				TrafficDataEntitiesManager.getInstance().addSectionsHistory(currentPeriod);
				
				List<TravelTime> travelTimes = new ArrayList<>(
						TrafficDataEntitiesManager.getInstance().getTravelTimes().values());

				processTravelTimes(travelTimes, currentPeriod);
			}
		}

		log.info("End procces period: " + Instant.ofEpochMilli(currentPeriod).toString() + " in: "
				+ Duration.between(startInstant, Instant.now()).toMillis() + " ms");
	}

	private boolean checkDetectorDate(Detector detector) {

		boolean bOk = false;

		if (detector != null && detector.getDate() != null && detector.getId() != null) {

			Long lastDate = lastDetectorsDate.get(detector.getId());

			if (lastDate == null || lastDate < detector.getDate()) {

				bOk = true;
			}

			lastDetectorsDate.put(detector.getId(), detector.getDate());
		}

		return bOk;
	}
}

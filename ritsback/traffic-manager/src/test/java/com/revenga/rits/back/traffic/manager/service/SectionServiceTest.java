package com.revenga.rits.back.traffic.manager.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.LevelOfService;
import com.revenga.rits.back.data.core.model.LosArray;
import com.revenga.rits.back.data.core.model.LosSoftenedArray;
import com.revenga.rits.back.traffic.manager.model.Detector;
import com.revenga.rits.back.traffic.manager.model.Section;

class SectionServiceTest {

	static private final Integer period = 1;

	static private final Integer[][] LEVEL_OF_SERVICES = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 } };

	static private final Integer[][] ARRAY_LOS = { { 1, 1, 1 }, { 1, 2, null }, { 1, 3, null }, { 1, 4, null },
			{ 1, 5, 4 }, { 1, 6, 4 }, { 2, 1, null }, { 2, 2, null }, { 2, 3, 2 }, { 2, 4, 3 }, { 2, 5, 4 },
			{ 2, 6, 4 }, { 3, 1, null }, { 3, 2, 2 }, { 3, 3, 2 }, { 3, 4, 3 }, { 3, 5, 4 }, { 3, 6, 4 }, { 4, 1, 1 },
			{ 4, 2, 2 }, { 4, 3, 2 }, { 4, 4, 4 }, { 4, 5, 3 }, { 4, 6, 3 }, { 5, 1, 1 }, { 5, 2, 1 }, { 5, 3, 1 },
			{ 5, 4, 2 }, { 5, 5, null }, { 5, 6, null }, { 6, 1, 1 }, { 6, 2, 1 }, { 6, 3, 1 }, { 6, 4, 2 },
			{ 6, 5, null }, { 6, 6, null } };

	static private final Integer[][] ARRAY_LOS_SOFTENED = { { null, 1, 2 }, { null, 2, 2 }, { null, 3, 2 },
			{ null, 4, 2 }, { 1, null, 1 }, { 1, 2, 2 }, { 1, 3, 3 }, { 1, 4, 4 }, { 2, null, 1 }, { 2, 1, 1 },
			{ 2, 3, 2 }, { 2, 4, 3 }, { 3, null, 1 }, { 3, 1, 2 }, { 3, 2, 1 }, { 3, 4, 2 }, { 4, null, 1 },
			{ 4, 1, 3 }, { 4, 2, 2 }, { 4, 3, 1 } };

	SectionService sectionService = null;
	DetectorService detectorService = null;

	private List<point> inputs;
	private List<point> outputs;

	SectionServiceTest() {

		detectorService = new DetectorService(period);

		List<LevelOfService> levelOfServices = new ArrayList<>();
		for (Integer[] item : LEVEL_OF_SERVICES) {
			LevelOfService levelOfService = new LevelOfService();
			levelOfService.setId(item[0].longValue());
			levelOfService.setLos(item[1] != null ? item[1] : null);
			levelOfServices.add(levelOfService);
		}

		ArrayList<LosArray> losArrays = new ArrayList<>();
		for (Integer[] item : ARRAY_LOS) {
			LosArray losArray = new LosArray();
			losArray.setLosThresholdSpeed(item[0]);
			losArray.setLosThresholdOccupancy(item[1]);
			losArray.setLevelOfServiceId(item[2] != null ? item[2].longValue() : null);
			losArrays.add(losArray);
		}

		ArrayList<LosSoftenedArray> losSoftenedArrays = new ArrayList<>();
		for (Integer[] item : ARRAY_LOS_SOFTENED) {
			LosSoftenedArray losSoftenedArray = new LosSoftenedArray();
			losSoftenedArray.setInitLosId(item[0] != null ? item[0].longValue() : null);
			losSoftenedArray.setEndLosId(item[1] != null ? item[1].longValue() : null);
			losSoftenedArray.setCountSoftened(item[2]);
			losSoftenedArrays.add(losSoftenedArray);
		}
		sectionService = new SectionService(period, levelOfServices, losArrays, losSoftenedArrays);

		ObjectMapper mapeador = new ObjectMapper();

		try {

			InputStream is = getClass().getClassLoader().getResourceAsStream("inputs.json");
			inputs = mapeador.readValue(is, new TypeReference<List<point>>() {
			});

			is = getClass().getClassLoader().getResourceAsStream("outputs.json");
			outputs = mapeador.readValue(is, new TypeReference<List<point>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void test() {
		try {
			if (CollectionUtils.isNotEmpty(inputs) && CollectionUtils.isNotEmpty(outputs)) {
				Instant instant = Instant.now();

				for (int index = 0; index < inputs.size() && index < outputs.size(); index++) {

					Long currentPeriod = instant.toEpochMilli();
					currentPeriod -= (currentPeriod % (period * 60000));

					System.out.println("Current Period: " + Instant.ofEpochMilli(currentPeriod).toString());

					List<Section> sections = inputs.get(0).getSections();
					List<Detector> detectors = inputs.get(index).getDetectors();

					if (!CollectionUtils.isEmpty(detectors)) {

						for (int i = 0; i < detectors.size(); i++) {

							detectors.get(i).setDate(currentPeriod);
							detectorService.init(detectors.get(i));
							detectorService.process(currentPeriod);

							assertEquals(detectors.get(i).getIntensity(),
									outputs.get(index).getDetectors().get(i).getIntensity());

							assertEquals(detectors.get(i).getVehSpeedPercent(),
									outputs.get(index).getDetectors().get(i).getVehSpeedPercent());

							assertEquals(detectors.get(i).getVehLengthPercent(),
									outputs.get(index).getDetectors().get(i).getVehLengthPercent());
						}

						if (sections.size() > 1 && detectors.size() > 3) {

							sections.get(0).setDetectors(detectors.subList(0, 2));
							sectionService.init(sections.get(0));
							sectionService.process(currentPeriod);

							assertEquals(sections.get(0).getCongestion(),
									outputs.get(index).getSections().get(0).getCongestion());
							assertEquals(sections.get(0).getKamikaze(),
									outputs.get(index).getSections().get(0).getKamikaze());
							assertEquals(sections.get(0).getCurrentDirection(),
									outputs.get(index).getSections().get(0).getCurrentDirection());
							assertEquals(sections.get(0).getIntensity(),
									outputs.get(index).getSections().get(0).getIntensity());
							assertEquals(sections.get(0).getNVehicles(),
									outputs.get(index).getSections().get(0).getNVehicles());
							assertEquals(sections.get(0).getLength(),
									outputs.get(index).getSections().get(0).getLength());
							assertEquals(sections.get(0).getOccupancy(),
									outputs.get(index).getSections().get(0).getOccupancy());
							assertEquals(sections.get(0).getVehLengthPercent(),
									outputs.get(index).getSections().get(0).getVehLengthPercent());
							assertEquals(sections.get(0).getVehSpeedPercent(),
									outputs.get(index).getSections().get(0).getVehSpeedPercent());
							assertEquals(sections.get(0).getVehLength(),
									outputs.get(index).getSections().get(0).getVehLength());
							assertEquals(sections.get(0).getVehSpeed(),
									outputs.get(index).getSections().get(0).getVehSpeed());
							assertEquals(sections.get(0).getLength(),
									outputs.get(index).getSections().get(0).getLength());
							assertEquals(sections.get(0).getSpeed(),
									outputs.get(index).getSections().get(0).getSpeed());

							assertEquals(sections.get(0).getLos(), outputs.get(index).getSections().get(0).getLos());
							assertEquals(sections.get(0).getLosPercent(),
									outputs.get(index).getSections().get(0).getLosPercent());

							sections.get(1).setDetectors(detectors.subList(2, 4));
							sectionService.init(sections.get(1));
							sectionService.process(currentPeriod);

							assertEquals(sections.get(1).getCongestion(),
									outputs.get(index).getSections().get(1).getCongestion());
							assertEquals(sections.get(1).getKamikaze(),
									outputs.get(index).getSections().get(1).getKamikaze());
							assertEquals(sections.get(1).getCurrentDirection(),
									outputs.get(index).getSections().get(1).getCurrentDirection());
							assertEquals(sections.get(1).getIntensity(),
									outputs.get(index).getSections().get(1).getIntensity());
							assertEquals(sections.get(1).getNVehicles(),
									outputs.get(index).getSections().get(1).getNVehicles());
							assertEquals(sections.get(1).getLength(),
									outputs.get(index).getSections().get(1).getLength());
							assertEquals(sections.get(1).getOccupancy(),
									outputs.get(index).getSections().get(1).getOccupancy());
							assertEquals(sections.get(1).getVehLengthPercent(),
									outputs.get(index).getSections().get(1).getVehLengthPercent());
							assertEquals(sections.get(1).getVehSpeedPercent(),
									outputs.get(index).getSections().get(1).getVehSpeedPercent());
							assertEquals(sections.get(1).getVehLength(),
									outputs.get(index).getSections().get(1).getVehLength());
							assertEquals(sections.get(1).getVehSpeed(),
									outputs.get(index).getSections().get(1).getVehSpeed());
							assertEquals(sections.get(1).getLength(),
									outputs.get(index).getSections().get(1).getLength());
							assertEquals(sections.get(1).getSpeed(),
									outputs.get(index).getSections().get(1).getSpeed());

							assertEquals(sections.get(1).getLos(), outputs.get(index).getSections().get(1).getLos());
							assertEquals(sections.get(1).getLosPercent(),
									outputs.get(index).getSections().get(1).getLosPercent());
						}
					}

					instant = instant.plusSeconds(period * 60);
					TimeUnit.MICROSECONDS.sleep(1);

					ObjectMapper mapeador = new ObjectMapper();
					try {
						InputStream is = getClass().getClassLoader().getResourceAsStream("inputs.json");
						inputs = mapeador.readValue(is, new TypeReference<List<point>>() {
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (InterruptedException e) {
		}

	}

	@Test
	void tes2() {
		try {
			if (CollectionUtils.isNotEmpty(inputs) && CollectionUtils.isNotEmpty(outputs)) {
				Instant instant = Instant.now();

				for (int index = 0; index < inputs.size() && index < outputs.size(); index++) {

					Long currentPeriod = instant.toEpochMilli();
					currentPeriod -= (currentPeriod % (period * 60000));

					System.out.println("Current Period: " + Instant.ofEpochMilli(currentPeriod).toString());

					List<Section> sections = inputs.get(0).getSections();
					List<Detector> detectors = inputs.get(index).getDetectors();

					if (!CollectionUtils.isEmpty(detectors)) {

						for (int i = 0; i < detectors.size(); i++) {

							detectors.get(i).setDate(currentPeriod - 60000);
							detectorService.init(detectors.get(i));
							detectorService.process(currentPeriod);

							assertNull(detectors.get(i).getIntensity());

							assertEquals(detectors.get(i).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));

							assertEquals(detectors.get(i).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
						}

						if (sections.size() > 1 && detectors.size() > 3) {

							sections.get(0).setDetectors(detectors.subList(0, 2));
							sectionService.init(sections.get(0));
							sectionService.process(currentPeriod);

							assertNull(sections.get(0).getCongestion());
							assertNull(sections.get(0).getKamikaze());
							assertNull(sections.get(0).getCurrentDirection());
							assertNull(sections.get(0).getIntensity());
							assertNull(sections.get(0).getNVehicles());
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getOccupancy());
							assertEquals(sections.get(0).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehLength(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeed(), new ArrayList<>(Collections.nCopies(16, null)));
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getSpeed());
						}
					}

					instant = instant.plusSeconds(period * 60);
					TimeUnit.MICROSECONDS.sleep(1);

					ObjectMapper mapeador = new ObjectMapper();
					try {
						InputStream is = getClass().getClassLoader().getResourceAsStream("inputs.json");
						inputs = mapeador.readValue(is, new TypeReference<List<point>>() {
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (InterruptedException e) {
		}

	}

	@Test
	void tes3() {
		try {
			if (CollectionUtils.isNotEmpty(inputs) && CollectionUtils.isNotEmpty(outputs)) {
				Instant instant = Instant.now();

				for (int index = 0; index < inputs.size() && index < outputs.size(); index++) {

					Long currentPeriod = instant.toEpochMilli();
					currentPeriod -= (currentPeriod % (period * 60000));

					System.out.println("Current Period: " + Instant.ofEpochMilli(currentPeriod).toString());

					List<Section> sections = inputs.get(0).getSections();
					List<Detector> detectors = inputs.get(index).getDetectors();

					if (!CollectionUtils.isEmpty(detectors)) {

						for (int i = 0; i < detectors.size(); i++) {

							detectorService.init(detectors.get(i));
							detectorService.process(currentPeriod);

							assertNull(detectors.get(i).getIntensity());

							assertEquals(detectors.get(i).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));

							assertEquals(detectors.get(i).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
						}

						if (sections.size() > 1 && detectors.size() > 3) {

							sections.get(0).setDetectors(detectors.subList(0, 2));
							sectionService.init(sections.get(0));
							sectionService.process(currentPeriod);

							assertNull(sections.get(0).getCongestion());
							assertNull(sections.get(0).getKamikaze());
							assertNull(sections.get(0).getCurrentDirection());
							assertNull(sections.get(0).getIntensity());
							assertNull(sections.get(0).getNVehicles());
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getOccupancy());
							assertEquals(sections.get(0).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehLength(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeed(), new ArrayList<>(Collections.nCopies(16, null)));
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getSpeed());
						}
					}

					instant = instant.plusSeconds(period * 60);
					TimeUnit.MICROSECONDS.sleep(1);

					ObjectMapper mapeador = new ObjectMapper();
					try {
						InputStream is = getClass().getClassLoader().getResourceAsStream("inputs.json");
						inputs = mapeador.readValue(is, new TypeReference<List<point>>() {
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (InterruptedException e) {
		}

	}

	@Test
	void tes4() {
		try {
			if (CollectionUtils.isNotEmpty(inputs) && CollectionUtils.isNotEmpty(outputs)) {
				Instant instant = Instant.now();

				for (int index = 0; index < inputs.size() && index < outputs.size(); index++) {

					Long currentPeriod = instant.toEpochMilli();
					currentPeriod -= (currentPeriod % (period * 60000));

					System.out.println("Current Period: " + Instant.ofEpochMilli(currentPeriod).toString());

					List<Section> sections = inputs.get(0).getSections();
					List<Detector> detectors = inputs.get(index).getDetectors();

					if (!CollectionUtils.isEmpty(detectors)) {

						for (int i = 0; i < detectors.size(); i++) {

							detectors.get(i).setPeriod(2);

							detectorService.init(detectors.get(i));
							detectorService.process(currentPeriod);

							assertNull(detectors.get(i).getIntensity());

							assertEquals(detectors.get(i).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));

							assertEquals(detectors.get(i).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
						}

						if (sections.size() > 1 && detectors.size() > 3) {

							sections.get(0).setDetectors(detectors.subList(0, 2));
							sectionService.init(sections.get(0));
							sectionService.process(currentPeriod);

							assertNull(sections.get(0).getCongestion());
							assertNull(sections.get(0).getKamikaze());
							assertNull(sections.get(0).getCurrentDirection());
							assertNull(sections.get(0).getIntensity());
							assertNull(sections.get(0).getNVehicles());
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getOccupancy());
							assertEquals(sections.get(0).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehLength(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeed(), new ArrayList<>(Collections.nCopies(16, null)));
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getSpeed());
						}
					}

					instant = instant.plusSeconds(period * 60);
					TimeUnit.MICROSECONDS.sleep(1);

					ObjectMapper mapeador = new ObjectMapper();
					try {
						InputStream is = getClass().getClassLoader().getResourceAsStream("inputs.json");
						inputs = mapeador.readValue(is, new TypeReference<List<point>>() {
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (InterruptedException e) {
		}
	}
	
	@Test
	void tes5() {
		try {
			if (CollectionUtils.isNotEmpty(inputs) && CollectionUtils.isNotEmpty(outputs)) {
				Instant instant = Instant.now();

				for (int index = 0; index < inputs.size() && index < outputs.size(); index++) {

					Long currentPeriod = instant.toEpochMilli();
					currentPeriod -= (currentPeriod % (period * 60000));

					System.out.println("Current Period: " + Instant.ofEpochMilli(currentPeriod).toString());

					List<Section> sections = inputs.get(0).getSections();
					List<Detector> detectors = inputs.get(index).getDetectors();

					if (!CollectionUtils.isEmpty(detectors)) {

						for (int i = 0; i < detectors.size(); i++) {

							detectors.get(i).setFailData(true);

							detectorService.init(detectors.get(i));
							detectorService.process(currentPeriod);

							assertNull(detectors.get(i).getIntensity());

							assertEquals(detectors.get(i).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));

							assertEquals(detectors.get(i).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
						}

						if (sections.size() > 1 && detectors.size() > 3) {

							sections.get(0).setDetectors(detectors.subList(0, 2));
							sectionService.init(sections.get(0));
							sectionService.process(currentPeriod);

							assertNull(sections.get(0).getCongestion());
							assertNull(sections.get(0).getKamikaze());
							assertNull(sections.get(0).getCurrentDirection());
							assertNull(sections.get(0).getIntensity());
							assertNull(sections.get(0).getNVehicles());
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getOccupancy());
							assertEquals(sections.get(0).getVehLengthPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeedPercent(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehLength(),
									new ArrayList<>(Collections.nCopies(16, null)));
							assertEquals(sections.get(0).getVehSpeed(), new ArrayList<>(Collections.nCopies(16, null)));
							assertNull(sections.get(0).getLength());
							assertNull(sections.get(0).getSpeed());
						}
					}

					instant = instant.plusSeconds(period * 60);
					TimeUnit.MICROSECONDS.sleep(1);

					ObjectMapper mapeador = new ObjectMapper();
					try {
						InputStream is = getClass().getClassLoader().getResourceAsStream("inputs.json");
						inputs = mapeador.readValue(is, new TypeReference<List<point>>() {
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (InterruptedException e) {
		}
	}

}

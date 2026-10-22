package com.revenga.rits.back.traffic.manager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import com.revenga.rits.back.data.core.model.LevelOfService;
import com.revenga.rits.back.data.core.model.LosArray;
import com.revenga.rits.back.data.core.model.LosSoftenedArray;
import com.revenga.rits.back.traffic.manager.model.Detector;
import com.revenga.rits.back.traffic.manager.model.Section;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SectionService {

	public class LosSoftened {

		@Getter
		Integer losS;
		Integer[] arrayCounts;

		private void dec() {

			if (arrayCounts != null) {

				for (Integer count : arrayCounts) {
					if (count.intValue() > 0)
						count--;
				}
			}
		}

		public LosSoftened(int size, Integer losC) {

			arrayCounts = new Integer[size];
			Arrays.fill(arrayCounts, 0);
		}

		public void proccesLosS(Integer losC) {

			if (arrayCounts != null) {
				if (losC == null) {

					arrayCounts[0]++;

				} else if (losC.equals(losS)) {

					dec();

					// LOSc < LOSs
				} else if (losC.intValue() < losS.intValue()) {

					for (int i = losC.intValue(); i < losS.intValue() && arrayCounts.length > losS.intValue(); i++) {

						arrayCounts[i]++;
					}
					arrayCounts[0] = 0;

					for (int i = losS.intValue() + 1; i < arrayCounts.length; i++) {

						arrayCounts[i]--;
					}

					for (int i = 1; i < losS.intValue() && arrayCounts.length > (losS.intValue()); i++) {

						LosSoftenedArray losSoftenedArray = getEndLos(losS.longValue(), arrayCounts[i]);

						if (losSoftenedArray != null) {
							losS = getLevelOfService(losSoftenedArray.getEndLosId());

							if (losS != null) {

								for (int j = losS; j < arrayCounts.length; j++) {

									arrayCounts[j] = 0;
								}
							} else {

								for (int j = 0; j < arrayCounts.length; j++) {
									arrayCounts[j] = 0;
								}
							}
							break;
						}

					}
					// LOSs < LOSc
				} else {

					for (int i = losS.intValue() + 1; i < arrayCounts.length && i <= losC.intValue(); i++) {

						arrayCounts[i]++;
					}

					for (int i = 1; i < losS.intValue() && i < arrayCounts.length; i++) {

						arrayCounts[i]--;
					}
					arrayCounts[0] = 0;

					for (int i = 4; i < arrayCounts.length && i > losS.intValue(); i--) {

						LosSoftenedArray losSoftenedArray = getEndLos(losS.longValue(), arrayCounts[i]);

						if (losSoftenedArray != null) {
							losS = getLevelOfService(losSoftenedArray.getEndLosId());

							if (losS != null) {

								for (int j = 0; j <= losS && j < arrayCounts.length; j++) {

									arrayCounts[j] = 0;
								}
							} else {
								for (int j = 0; j < arrayCounts.length; j++) {
									arrayCounts[j] = 0;
								}
							}
							break;
						}
					}
				}
			}

		}
	}

	private Map<Long, LosSoftened> losSofteneds;
	private List<LosArray> losArrays;
	private List<LosSoftenedArray> losSoftenedArrays;
	private List<LevelOfService> levelOfServices;

	@Getter
	private Section section;

	private Integer period;

	// Private method
	private Integer processLos() {
		Integer los = null;

		if (section != null && section.getSpeed() != null && section.getOccupancy() != null
				&& !CollectionUtils.isEmpty(section.getLosThresholdOccupancy())
				&& !CollectionUtils.isEmpty(section.getLosThresholdSpeed())) {

			Integer losThresholdOccupancy = null;
			Integer losThresholdSpeed = null;

			for (int i = 0; i < section.getLosThresholdOccupancy().size(); i++) {

				if (section.getLosThresholdOccupancy().get(i) != null) {
					if (section.getOccupancy() <= section.getLosThresholdOccupancy().get(i)) {

						losThresholdOccupancy = i + 1;
						break;

					} else {
						losThresholdOccupancy = i + 2;
					}
				} else {
					losThresholdOccupancy = null;
					break;
				}

			}

			if (losThresholdOccupancy != null) {

				for (int i = 0; i < section.getLosThresholdSpeed().size(); i++) {

					if (section.getLosThresholdSpeed().get(i) != null) {

						if (section.getSpeed() <= section.getLosThresholdSpeed().get(i)) {

							losThresholdSpeed = i + 1;
							break;

						} else {
							losThresholdSpeed = i + 2;
						}
					} else {
						losThresholdSpeed = null;
						break;
					}
				}
			}

			los = getLevelOfService(losThresholdSpeed, losThresholdOccupancy);
			int nlos = Math.max(section.getLosThresholdOccupancy().size(), section.getLosThresholdSpeed().size()) + 1;
			section.setNlos(nlos);

			if (los != null && section.getLosSoften() != null && section.getLosSoften().booleanValue()) {

				los = processLosSoften(section.getId(), los, nlos);
			}

		}
		return los;
	}

	private Integer processLosPercent() {

		return section.getNlos() != null && section.getLos() != null && section.getNlos().intValue() > 0
				? section.getLos() * 100 / section.getNlos().intValue()
				: null;
	}

	private Integer processLosSoften(Long sectionId, Integer losC, Integer nLos) {

		Integer losS = null;

		if (sectionId != null && losC != null && nLos != null) {

			LosSoftened losSoftened = losSofteneds.get(sectionId);

			if (losSoftened != null) {

				losSoftened.proccesLosS(losC);

			} else {

				losSoftened = new LosSoftened(nLos.intValue() + 1, losC);
			}
			losS = losSoftened.getLosS();
		}
		return losS;
	}

	private Boolean processCat(final List<Integer> src, final List<List<Integer>> cats, List<Integer> dst) {

		Boolean bOk = true;

		if (!CollectionUtils.isEmpty(src) && !CollectionUtils.isEmpty(cats) && !CollectionUtils.isEmpty(dst)) {

			for (int i = 0; i < cats.size() && i < dst.size(); i++) {

				if (cats.get(i) != null) {

					dst.set(i, 0);

					for (Integer j : cats.get(i)) {

						if (j != null) {

							int index = j.intValue() - 1;

							if (src.size() > index && src.get(index) != null) {

								dst.set(i, dst.get(i) + src.get(index));
							} else {

								dst.set(i, null);
								break;
							}
						} else {
							dst.set(i, null);
							break;
						}
					}
				}
			}
		} else {
			bOk = false;
		}

		return bOk;
	}

	private Integer getLevelOfService(Long id) {

		LevelOfService levelOfService = null;

		if (!CollectionUtils.isEmpty(levelOfServices) && id != null) {
			levelOfService = levelOfServices.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
		}

		return levelOfService != null ? levelOfService.getLos() : null;
	}

	private Integer getLevelOfService(Integer losThresholdSpeed, Integer losThresholdOccupancy) {

		LosArray losArray = null;

		if (losThresholdSpeed != null && losThresholdOccupancy != null && !CollectionUtils.isEmpty(losArrays)) {

			losArray = losArrays.stream().filter(e -> e.getLosThresholdOccupancy().equals(losThresholdOccupancy)
					&& e.getLosThresholdSpeed().equals(losThresholdSpeed)).findFirst().orElse(null);
		}

		return losArray != null && losArray.getLevelOfServiceId() != null
				? getLevelOfService(losArray.getLevelOfServiceId())
				: null;
	}

	private LosSoftenedArray getEndLos(Long initLosId, Integer countSoftened) {

		LosSoftenedArray losSoftenedArray = null;

		if (!CollectionUtils.isEmpty(losSoftenedArrays)) {

			losSoftenedArray = losSoftenedArrays.stream()
					.filter(e -> e.getInitLosId().equals(initLosId) && e.getCountSoftened().equals(countSoftened))
					.findFirst().orElse(null);
		}
		return losSoftenedArray;
	}
	// Public method

	public SectionService(Integer period, List<LevelOfService> levelOfServices, List<LosArray> losArrays,
			List<LosSoftenedArray> losSoftenedArrays) {

		losSofteneds = new HashedMap<>();
		this.period = period;
		this.levelOfServices = levelOfServices;
		this.losArrays = losArrays;
		this.losSoftenedArrays = losSoftenedArrays;
	}

	public Boolean process(Long currentPeriod) {

		Integer nVehicles = 0;
		Integer occupancy = 0;
		Integer speed = 0;
		Integer gap = 0;
		Integer headway = 0;
		Integer length = 0;
		boolean bOk = true;
		Integer delay = null;
		Long date = null;

		if (currentPeriod != null && section != null && CollectionUtils.isNotEmpty(section.getDetectors())) {

			List<Integer> vehLength = new ArrayList<>(Collections.nCopies(Detector.VEH_LENGTH, 0));
			List<Integer> vehSpeed = new ArrayList<>(Collections.nCopies(Detector.VEH_SPEED, 0));
			List<Integer> vehPa = new ArrayList<>(Collections.nCopies(Detector.VEH_PA, 0));

			for (Detector detector : section.getDetectors()) {

				if (detector == null || period == null || !period.equals(detector.getPeriod())) {

					log.error("Period: " + period + " detector: " + (detector != null ? detector.getId() : null)
							+ " period detector: " + (detector != null ? detector.getPeriod() : null) + " in section: "
							+ section.getId());
					bOk = false;

				} else if (detector.getFailData() == null || detector.getFailData().booleanValue()) {

					log.error("Fail data in detector: " + detector.getId() + " in section " + section.getId());
					bOk = false;

				} else if (delay != null && (detector.getDelay() != null || delay != 0)
						&& (detector.getDelay() == null || !delay.equals(detector.getDelay()))) {

					log.error("All detectors must the same delay: " + delay + " detector: " + detector.getId()
							+ " delay detector: " + detector.getDelay() + " in Section " + section.getId());
					bOk = false;

				} else {

					if (delay == null && detector.getDelay() == null) {

						delay = 0;

					} else if (delay == null && detector.getDelay() != null) {

						delay = detector.getDelay();
					}

					if (date == null) {

						date = detector.getDate();
					}
					
					if (detector.getDate() == null
							|| currentPeriod.longValue() > (detector.getDate() + (delay * period * 60000))) {

						log.error("CurrentPeriod: " + currentPeriod + " date: " + detector.getDate() + " detector: "
								+ detector.getId() + " in section: " + section.getId());
						bOk = false;

					} else if (date != null && !date.equals(detector.getDate())) {

						log.error("All detectors must the same date: " + date + " detector: " + detector.getId()
								+ " date detector: " + detector.getDate() + " in Section " + section.getId());
						bOk = false;

					} else {

						if (nVehicles != null && detector.getNVehicles() != null)
							nVehicles += detector.getNVehicles();
						else
							nVehicles = null;

						if (!CollectionUtils.isEmpty(detector.getVehLength())) {

							for (int i = 0; i < Detector.VEH_LENGTH; i++) {

								if (vehLength.get(i) != null && detector.getVehLength().size() > i
										&& detector.getVehLength().get(i) != null) {

									vehLength.set(i, vehLength.get(i) + detector.getVehLength().get(i));

								} else {
									vehLength.set(i, null);
								}
							}
						}

						if (!CollectionUtils.isEmpty(detector.getVehSpeed())) {

							for (int i = 0; i < Detector.VEH_SPEED; i++) {

								if (vehSpeed.get(i) != null && detector.getVehSpeed().size() > i
										&& detector.getVehSpeed().get(i) != null) {

									vehSpeed.set(i, vehSpeed.get(i) + detector.getVehSpeed().get(i));

								} else {
									vehSpeed.set(i, null);
								}
							}
						}

						if (!CollectionUtils.isEmpty(detector.getVehPa())) {

							for (int i = 0; i < Detector.VEH_PA; i++) {

								if (vehPa.get(i) != null && detector.getVehPa().size() > i
										&& detector.getVehPa().get(i) != null) {

									vehPa.set(i, vehPa.get(i) + detector.getVehPa().get(i));

								} else {
									vehPa.set(i, null);
								}
							}
						}
						if (detector.getCongestion() != null) {

							if (!detector.getCongestion().booleanValue()
									&& (section.getCongestion() == null || !section.getCongestion().booleanValue()))
								section.setCongestion(false);
							else
								section.setCongestion(true);
						}

						if (detector.getKamikaze() != null) {

							if (!detector.getKamikaze().booleanValue()
									&& (section.getKamikaze() == null || !section.getKamikaze().booleanValue()))
								section.setKamikaze(false);
							else
								section.setKamikaze(true);
						}

						if (detector.getCurrentDirection() != null) {

							if (!detector.getCurrentDirection().booleanValue() && (section.getCurrentDirection() == null
									|| !section.getCurrentDirection().booleanValue()))
								section.setCurrentDirection(false);
							else
								section.setCurrentDirection(true);
						}

						if (section.getVirtual() == null || !section.getVirtual().booleanValue()) {

							if (occupancy != null && detector.getOccupancy() != null)
								occupancy += (detector.getNVehicles() * detector.getOccupancy());
							else
								occupancy = null;

							if (speed != null && detector.getSpeed() != null)
								speed += (detector.getNVehicles() * detector.getSpeed());
							else
								speed = null;

							if (gap != null && detector.getGap() != null)
								gap += (detector.getNVehicles() * detector.getGap());
							else
								gap = null;

							if (headway != null && detector.getHeadway() != null)
								headway += (detector.getNVehicles() * detector.getHeadway());
							else
								headway = null;

							if (length != null && detector.getLength() != null)
								length += (detector.getNVehicles() * detector.getLength());
							else
								length = null;
						}
					}
				}

				if (!bOk)
					break;
			}

			if (bOk) {

				if (nVehicles != null && period != null && period != 0) {

					section.setIntensity(nVehicles * 60 / period);
				}

				section.setDate(date);
				section.setPeriod(period);
				section.setNVehicles(nVehicles);

				if (!processCat(vehLength, section.getCatVehLength(), section.getVehLength()).booleanValue()) {
					log.error("Error process vehLengthcat");
				}

				if (!processCat(vehSpeed, section.getCatVehSpeed(), section.getVehSpeed()).booleanValue()) {
					log.error("Error process vehSpeedcat");
				}

				if (!processCat(vehPa, section.getCatVehPa(), section.getVehPa()).booleanValue()) {
					log.error("Error process vehPacat");
				}

				if (nVehicles != null) {

					for (int i = 0; i < section.getVehLength().size()
							&& i < section.getVehLengthPercent().size(); i++) {

						if (section.getVehLength().get(i) != null) {

							section.getVehLengthPercent().set(i,
									nVehicles > 0 ? (section.getVehLength().get(i) * 100 / nVehicles) : 0);
						}
					}

					for (int i = 0; i < section.getVehSpeed().size() && i < section.getVehSpeedPercent().size(); i++) {

						if (section.getVehSpeed().get(i) != null) {

							section.getVehSpeedPercent().set(i,
									nVehicles > 0 ? (section.getVehSpeed().get(i) * 100 / nVehicles) : 0);
						}
					}

					for (int i = 0; i < section.getVehPa().size() && i < section.getVehPaPercent().size(); i++) {

						if (section.getVehPa().get(i) != null) {

							section.getVehPaPercent().set(i,
									nVehicles > 0 ? (section.getVehPa().get(i) * 100 / nVehicles) : 0);
						}
					}

					if (section.getVirtual() == null || !section.getVirtual().booleanValue()) {

						if (occupancy != null)
							section.setOccupancy(nVehicles > 0 ? (occupancy / nVehicles) : 0);

						if (speed != null)
							section.setSpeed(nVehicles > 0 ? (speed / nVehicles) : 0);

						if (gap != null)
							section.setGap(nVehicles > 0 ? (gap / nVehicles) : 0);

						if (headway != null)
							section.setHeadway(nVehicles > 0 ? (headway / nVehicles) : 0);

						if (length != null)
							section.setLength(nVehicles > 0 ? (length / nVehicles) : 0);

						section.setLos(processLos());
						section.setLosPercent(processLosPercent());
					}
				}
			}

		} else {
			bOk = false;
		}

		return bOk;
	}

	public void init(Section section) {

		this.section = section;
	}

}

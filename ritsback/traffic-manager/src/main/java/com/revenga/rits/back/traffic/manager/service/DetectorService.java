package com.revenga.rits.back.traffic.manager.service;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.traffic.manager.model.Detector;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DetectorService {

	@Getter
	private Detector detector;

	private Integer period;

	public DetectorService(Integer period) {

		this.period = period;
	}

	public void init(Detector detector) {

		this.detector = detector;
	}

	public boolean process(Long currentPeriod) {

		boolean failData = false;

		if (detector == null || period == null || !period.equals(detector.getPeriod())) {

			log.error("Period: " + period + " detector: " + (detector != null ? detector.getId() : null)
					+ " period detector: " + (detector != null ? detector.getPeriod() : null));
			failData = true;

		} else if (detector.getFailData() == null || detector.getFailData().booleanValue()) {

			log.error("Fail data in detector: " + detector.getId());
			failData = true;

		} else if (detector.getDate() == null || currentPeriod > detector.getDate()
				+ ((detector.getDelay() != null ? detector.getDelay() : 0) * period * 60000)) {

			log.error("CurrentPeriod: " + currentPeriod.longValue() + " date: " + detector.getDate() + " in detector "
					+ detector.getId() + " delay:" + (detector.getDelay() != null ? detector.getDelay() : 0));
			failData = true;

		} else {

			if (period != 0 && detector.getNVehicles() != null) {

				this.detector.setIntensity(detector.getNVehicles() * 60 / period);

			}

			if (detector.getNVehicles() != null) {

				if (!CollectionUtils.isEmpty(detector.getVehLength())
						&& !CollectionUtils.isEmpty(detector.getVehLengthPercent())) {

					for (int i = 0; i < detector.getVehLength().size()
							&& i < detector.getVehLengthPercent().size(); i++) {

						if (detector.getVehLength().get(i) != null) {

							detector.getVehLengthPercent().set(i,
									detector.getNVehicles() > 0
											? (detector.getVehLength().get(i) * 100 / detector.getNVehicles())
											: 0);
						}
					}
				}

				if (!CollectionUtils.isEmpty(detector.getVehSpeed())
						&& !CollectionUtils.isEmpty(detector.getVehSpeedPercent())) {

					for (int i = 0; i < detector.getVehSpeed().size()
							&& i < detector.getVehSpeedPercent().size(); i++) {

						if (detector.getVehSpeed().get(i) != null) {

							detector.getVehSpeedPercent().set(i,
									detector.getNVehicles() > 0
											? (detector.getVehSpeed().get(i) * 100 / detector.getNVehicles())
											: 0);
						}
					}
				}

				if (!CollectionUtils.isEmpty(detector.getVehPa())
						&& !CollectionUtils.isEmpty(detector.getVehPaPercent())) {

					for (int i = 0; i < detector.getVehPa().size() && i < detector.getVehPaPercent().size(); i++) {

						if (detector.getVehPa().get(i) != null) {

							detector.getVehPaPercent().set(i,
									detector.getNVehicles() > 0
											? (detector.getVehPa().get(i) * 100 / detector.getNVehicles())
											: 0);
						}
					}
				}
			}
		}
		return !failData;
	}

}

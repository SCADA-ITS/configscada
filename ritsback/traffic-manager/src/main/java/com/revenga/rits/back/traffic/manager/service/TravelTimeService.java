package com.revenga.rits.back.traffic.manager.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.traffic.manager.model.Section;
import com.revenga.rits.back.traffic.manager.model.TravelTime;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TravelTimeService {

	@Getter
	private TravelTime travelTime;
	
	
	private class TravelTimeData {
		
		TravelTime travelTime;
		List<List<Pair<Double, Integer>>> speeds;
		
		@Getter
		Integer sumPeriods = 0;
		
		@Getter
		List<Double> weightedAvgSpeedSections = new ArrayList<>();
		
		@Getter
		List<Double> reverseWeightedAvgSpeedSections = new ArrayList<>();
		
		@Getter
		Double sourceX;
		
		@Getter
		Double targetX;
		
		public TravelTimeData(TravelTime travelTime) {
			
			this.travelTime = travelTime;
			
//			speeds = new ArrayList<>(
//					Collections.nCopies(EntitiesManager.getInstance().getTravelTimePeriods(), new ArrayList<>()));
			
			speeds = new ArrayList<>();
			
			for (int period = 1; period <= EntitiesManager.getInstance().getTravelTimePeriods(); period++) {
				
				sumPeriods += period;
			}
			
			if (travelTime.getPkIni() <= travelTime.getPkEnd()) {
				
				sourceX = travelTime.getPkIni();
				targetX = travelTime.getPkEnd();
			}
			else {
				
				sourceX = travelTime.getPkEnd();
				targetX = travelTime.getPkIni();
			}
		}
		
		public void add(Double pk, int period, Integer speed) {
			
			if (travelTime != null) {
				
				List<Pair<Double, Integer>> speedsSectionPeriod = null;
				Pair<Double, Integer> pair = null;
				
				if (speeds.size() > period) {
				
					speedsSectionPeriod = speeds.get(period);
				}
				
				if (speedsSectionPeriod == null) {
					
					speedsSectionPeriod = new ArrayList<>();
					
					speeds.add(speedsSectionPeriod);
				}
				else {
				
					pair = speedsSectionPeriod.stream().filter(e -> e != null && e.getLeft().equals(pk)).findFirst()
							.orElse(null);
				}
				
				if (pair == null) {
					
					speedsSectionPeriod.add(new MutablePair<>(pk, speed));
					
					Comparator<Pair<Double, Integer>> compareByPk = (Pair<Double, Integer> o1, Pair<Double, Integer> o2) -> o1.getLeft()
							.compareTo(o2.getLeft());
					
					Collections.sort(speedsSectionPeriod, compareByPk);
				}
				else {
					
					pair.setValue(speed);				
				}
			}
		}
		
		public Double getPk(int sectionIdx) {
			
			Double pk = null;
			
			List<Pair<Double, Integer>> speedsSectionPeriod = speeds.get(0);
			
			if (travelTime != null && sectionIdx < speedsSectionPeriod.size()) {
				
				Pair<Double, Integer> pair = speedsSectionPeriod.get(sectionIdx);
				
				if (pair != null) {
					
					pk = pair.getLeft();
				}
			}
			
			return pk;
		}
		
		public int getSectionsCount() {
			
			int sectionsCount = 0;
			List<Pair<Double, Integer>> speedsSectionPeriod = speeds.get(0);
			
			if (travelTime != null) {
				
				sectionsCount = speedsSectionPeriod.size();
			}
			
			return sectionsCount;
		}
		
		public Integer getSpeed(int period, int sectionIdx) {
			
			Integer speed = null;
			
			if (travelTime != null && period < speeds.size()) {
				
				List<Pair<Double, Integer>> speedsSectionPeriod = speeds.get(period);
				
				Pair<Double, Integer> pair = speedsSectionPeriod.get(sectionIdx);
				
				if (pair != null) {
					
					speed = pair.getRight();
				}
			}
			
			return speed;
		}
		
		public boolean validate() {
			
			boolean bOk = true;
			
			if (travelTime != null) {
				
				bOk &= (sourceX > getPk(0));
				bOk &= (targetX < getPk(getSectionsCount() - 1));
				bOk &= (getSectionsCount() > 2);
			}
			
			return bOk;
		}
	}
	
	public TravelTimeService() {
		
	}

	public Boolean process(Long currentPeriod) {

		Boolean bOk = true;

		if (travelTime != null) {
			
			travelTime.setTimeMin(null);
			
			TravelTimeData ttData = getTravelTimeData(currentPeriod);
			
			if (ttData != null) {
				
				if (ttData.validate()) {
					
					if (calculateWeightedAvgSpeeds(ttData)) {
						
						Double areas = getAreas(ttData);
						Integer minutes = getMinutesFromArea(areas);
						
						log.debug("Travel time #" + travelTime.getId() + " = " + areas + " hours");
						log.info("Travel time #" + travelTime.getId() + " from " + travelTime.getPkIni() + " to "
								+ travelTime.getPkEnd() + " is " + minutes + " minutes");
						
						travelTime.setTimeMin(minutes);
					}
					else {
						
						log.error("Period: " + currentPeriod + " travelTime: " + (travelTime != null ? travelTime.getId() : null)
								+ " cannot calculate Weighted average speeds");
						
						bOk = false;
					}
				}
				else {
					
					log.error("Period: " + currentPeriod + " travelTime: " + (travelTime != null ? travelTime.getId() : null)
							+ " validation error");
					
					bOk = false;
				}
			}
			else {
							
				bOk = false;
			}
			

			if (bOk.booleanValue()) {

				travelTime.setDate(currentPeriod);
			}

		} else {
			bOk = false;
		}

		return bOk;
	}
	
	private TravelTimeData getTravelTimeData(Long currentPeriod) {
		
		boolean bOk = true;
		TravelTimeData ttData = new TravelTimeData(travelTime);
		
		List<Pair<Long, Map<Long, Section>>> sectionsHistory = TrafficDataEntitiesManager.getInstance().getSectionsHistory();
		
		if (travelTime != null && sectionsHistory.size() == EntitiesManager.getInstance().getTravelTimePeriods()) {
			
			if (sectionsHistory.get(sectionsHistory.size() - 1).getLeft().equals(currentPeriod)) {
				
				for (int idxPeriod = 0; bOk && idxPeriod < EntitiesManager.getInstance().getTravelTimePeriods() ; idxPeriod++) {
					
					Pair<Long, Map<Long, Section>> pair = sectionsHistory.get(idxPeriod);
					
					if (pair != null) {
					
						Long period = pair.getLeft();
						Map<Long, Section> mapSectionsHistory = pair.getRight(); 					
						
						Long periodCompare = currentPeriod - ((EntitiesManager.getInstance().getTravelTimePeriods() - idxPeriod - 1) * 60000);
						
						String strCurrentPeriod = Instant.ofEpochMilli(currentPeriod).toString();
						String strSectionHistoryPeriod = Instant.ofEpochMilli(period).toString();
						String strPeriodToCompare = Instant.ofEpochMilli(periodCompare).toString();
						
						if (period.equals(periodCompare)) {
			
							for (Section section : travelTime.getSections()) {
								
								Section sectionHistory = mapSectionsHistory.get(section.getId());
								
								if (sectionHistory != null) {
									
									if (sectionHistory.getSpeed() != null && sectionHistory.getSpeed() > 0) {
									
										ttData.add(section.getPk(), idxPeriod, sectionHistory.getSpeed());
									}
									else {
										
										bOk = false;
									}
								}
								else {
									
									bOk = false;
								}
								
								if (!bOk) {
									
									break;
								}
							}
						}
						else {
							
							bOk = false;
						}
					}
				}
			}
			else {
			
				bOk = false;
			}
		}
		else {
			
			bOk = false;
		}
		
		if (!bOk) {
			
			ttData = null;
		}
		
		return ttData;
	}
	
	private boolean calculateWeightedAvgSpeeds(TravelTimeData ttData) {
		
		boolean isValidSpeed = true;
		
		for (int idxSection = 0; isValidSpeed && idxSection < ttData.getSectionsCount(); idxSection++) {
			
			double weightedSpeed = 0;
			
			for (int period = 0; isValidSpeed && period < EntitiesManager.getInstance().getTravelTimePeriods(); period++) {
				
				double weightingPercentage = (100
						* (period + 1))
						/ (double) ttData.getSumPeriods();
				
				Integer speedPeriod = ttData.getSpeed(period, idxSection);
				
				if (speedPeriod != null && speedPeriod > 0) {
					
					weightedSpeed += (speedPeriod * weightingPercentage / 100);
				}
				else {
					
					log.error("Period: " + period + " travelTime: " + (travelTime != null ? travelTime.getId() : null)
							+ " speed is null or 0");
					
					isValidSpeed = false;
				}
			}
			
			if (isValidSpeed && weightedSpeed > 0) {
				
				ttData.getWeightedAvgSpeedSections().add(weightedSpeed);
				ttData.getReverseWeightedAvgSpeedSections().add(1 / weightedSpeed);
				
				log.debug("Section: " + ttData.getPk(idxSection) + "; V = " + weightedSpeed + "; 1/V = " + (1 / weightedSpeed));
			}
		}
		
		if (!isValidSpeed) {
			
			ttData.getWeightedAvgSpeedSections().clear();
			ttData.getReverseWeightedAvgSpeedSections().clear();
		}
		
		return isValidSpeed;
	}
	
	private double getAreas(TravelTimeData ttData) {
		
		double areas = 0.0;
		Double sourceY = getY(ttData, ttData.getSourceX(), 0, 1);
		Double targetY = getY(ttData, ttData.getTargetX(), ttData.getSectionsCount() - 2, ttData.getSectionsCount() - 1);
		
		log.debug("SourceX = " + ttData.getSourceX() + "; SourceY = " +  sourceY);
		log.debug("TargetX = " + ttData.getTargetX() + "; TargetY = " +  targetY);
		
		List<Pair<Double, Double>> matrixAreaData = new ArrayList<>();
		
		// Prepare matrix of data
		matrixAreaData.add(new ImmutablePair<>(ttData.getSourceX(), sourceY));
		
		for (int i = 1; i < ttData.getReverseWeightedAvgSpeedSections().size() - 1; i++) {
		
			double x = ttData.getPk(i);
			double y = ttData.getReverseWeightedAvgSpeedSections().get(i);
			
			matrixAreaData.add(new ImmutablePair<>(x, y));
		}
		
		matrixAreaData.add(new ImmutablePair<>(ttData.getTargetX(), targetY));
		
		// Calculate area from matrix data
		
		int numArea = 0;
		
		for (int i = 0; i <= matrixAreaData.size() - 2; i++) {
			
			areas += getArea(++numArea, matrixAreaData.get(i), matrixAreaData.get(i + 1));
		}
		
		return areas;
	}
	
	private Integer getMinutesFromArea(double area) {
		
		Integer minutes = null;
		
		if (area > 0) {
		
			minutes = (int) Math.ceil(area * 60);
		}
		
		return minutes;
	}
	
	private double getArea(int numArea, Pair<Double, Double> pointIni, Pair<Double, Double> pointEnd) {
		
		Double area = 0.0;
		
		if (pointIni != null && pointEnd != null) {
			
			double x1 = pointIni.getLeft();
			double y1 = pointIni.getRight();
			
			double x2 = pointEnd.getLeft();
			double y2 = pointEnd.getRight();
			
			if (x2 > x1) {
			
				double areaRectangle = (x2 - x1) * (y1 < y2 ? y1 : y2);
				double areaTriangle = ((x2 - x1) * Math.abs(y2 - y1)) / 2;
				
				area = areaRectangle + areaTriangle;
				
				log.debug("Area #" + numArea + " = " + areaRectangle + " + " + areaTriangle + " = " + area);
			}
		}
		
		return area;
	}
	
	private Double getY(TravelTimeData ttData, double x, int idxIni, int idxEnd) {
		
		Double y = null;
		
		if (ttData.getReverseWeightedAvgSpeedSections().size() == ttData.getSectionsCount()
				&& ttData.getSectionsCount() > 2) {

			double y2 = ttData.getReverseWeightedAvgSpeedSections().get(idxEnd);
			double y1 = ttData.getReverseWeightedAvgSpeedSections().get(idxIni);
			
			double x2 = ttData.getPk(idxEnd);
			double x1 = ttData.getPk(idxIni);
			
			y = ((y2 - y1) / (x2 - x1)) * (x - x1) + y1; 
		}
		
		return y;
	}

	public void init(TravelTime travelTime) {

		this.travelTime = travelTime;
	}
}

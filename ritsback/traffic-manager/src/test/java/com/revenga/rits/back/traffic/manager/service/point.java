package com.revenga.rits.back.traffic.manager.service;

import java.util.List;

import com.revenga.rits.back.traffic.manager.model.Detector;
import com.revenga.rits.back.traffic.manager.model.Section;
import com.revenga.rits.back.traffic.manager.model.TravelTime;

import lombok.Data;

@Data
public class point {
	Integer n;
	List<Detector> detectors;
	List<Section> sections;
	List<TravelTime> segment;
}

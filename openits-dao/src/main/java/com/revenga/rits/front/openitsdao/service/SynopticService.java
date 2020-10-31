package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.Synoptic;

public interface SynopticService {

	public List<Synoptic> getAll();
	public Synoptic getById(Integer id);
}

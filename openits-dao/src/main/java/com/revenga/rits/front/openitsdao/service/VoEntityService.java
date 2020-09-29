package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VoEntity;

public interface VoEntityService {

	public List<VoEntity> getAll();
	public VoEntity getById(String id);
}

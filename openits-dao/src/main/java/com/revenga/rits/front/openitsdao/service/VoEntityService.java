package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityType;

public interface VoEntityService {

	public List<VoEntity> getAll();
	public VoEntity getById(String id);
	public List<VoEntity> getByVoEntityType(VoEntityType voEntityType);
}

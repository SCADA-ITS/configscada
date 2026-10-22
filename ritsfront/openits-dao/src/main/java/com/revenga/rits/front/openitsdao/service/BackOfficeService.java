package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.BackOffice;

public interface BackOfficeService {

	public List<BackOffice> getAll();
	public BackOffice getById(Integer id);
}

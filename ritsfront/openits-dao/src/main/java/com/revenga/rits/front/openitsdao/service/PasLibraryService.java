package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibrary;

public interface PasLibraryService {

	public List<PasLibrary> getAll();

	public PasLibrary getById(Integer id);
}

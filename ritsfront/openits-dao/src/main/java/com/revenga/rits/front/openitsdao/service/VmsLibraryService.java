package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VmsLibrary;

public interface VmsLibraryService {

	public List<VmsLibrary> getAll();

	public VmsLibrary getById(Integer id);
}

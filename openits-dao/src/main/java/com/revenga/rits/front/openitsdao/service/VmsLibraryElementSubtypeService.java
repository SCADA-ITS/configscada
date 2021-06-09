package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VmsLibraryElementSubtype;

public interface VmsLibraryElementSubtypeService {

	public List<VmsLibraryElementSubtype> getAll();
	public VmsLibraryElementSubtype getByElementSubtypeId(String elementSubtypeId);
}

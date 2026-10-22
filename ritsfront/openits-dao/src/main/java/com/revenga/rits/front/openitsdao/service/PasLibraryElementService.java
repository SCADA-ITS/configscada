package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryElement;

public interface PasLibraryElementService {

	public List<PasLibraryElement> getAll();
	public PasLibraryElement getByElementId(String elementId);
}

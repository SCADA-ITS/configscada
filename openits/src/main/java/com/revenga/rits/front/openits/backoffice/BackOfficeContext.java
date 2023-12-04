package com.revenga.rits.front.openits.backoffice;

import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.front.openitsdao.service.GridService;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BackOfficeContext {
	
	GroovyManager groovyManager;
	BackOfficeService backOfficeService;
	BackOfficeManager backOfficeManager;
	GridService gridService;	
}

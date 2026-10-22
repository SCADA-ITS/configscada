package com.revenga.rits.front.openits.backoffice;

import org.springframework.core.env.Environment;

import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.front.openitsdao.service.GridService;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BackOfficeContext {
	
	Environment env;
	GroovyManager groovyManager;
	BackOfficeService backOfficeService;
	BackOfficeManager backOfficeManager;
	GridService gridService;	
}

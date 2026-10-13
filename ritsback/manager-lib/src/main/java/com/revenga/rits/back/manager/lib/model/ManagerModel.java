package com.revenga.rits.back.manager.lib.model;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ManagerModel {
	
	private String xmlAdapter;
	private ManagerAdapterV1 managerAdapterV1;
}

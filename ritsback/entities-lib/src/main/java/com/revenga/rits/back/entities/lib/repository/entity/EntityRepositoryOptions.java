package com.revenga.rits.back.entities.lib.repository.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityRepositoryOptions {
	
	boolean buffered;
	Long bufferRetentionTime;
	List<EntityRepository> repositories;
	boolean publishChanges;
	boolean subscribeChanges;
	EntityOnChange entityOnChange; 
}

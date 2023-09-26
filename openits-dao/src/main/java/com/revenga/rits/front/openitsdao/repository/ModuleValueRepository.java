package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.ModuleValue;

@Repository
public interface ModuleValueRepository extends JpaRepository<ModuleValue, Long> {

	List<ModuleValue> findAll();

	<S extends ModuleValue> S save(S moduleValue);
}

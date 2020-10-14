package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revenga.rits.front.openitsdao.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

	Module findById(Integer id);
	List<Module> findAll();
}

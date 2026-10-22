package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibrary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasLibraryRepository extends JpaRepository<PasLibrary, Long> {

	PasLibrary findById(Integer id);
	List<PasLibrary> findAll();
}

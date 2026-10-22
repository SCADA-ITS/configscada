package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryElement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasLibraryElementRepository extends JpaRepository<PasLibraryElement, Long> {

	PasLibraryElement findByElementId(String elementId);
	List<PasLibraryElement> findAll();
}

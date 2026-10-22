package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.VmsLibraryElementSubtype;

@Repository
public interface VmsLibraryElementSubtypeRepository extends JpaRepository<VmsLibraryElementSubtype, Long> {

	VmsLibraryElementSubtype findByElementSubtypeId(String elementSubtypeId);
	List<VmsLibraryElementSubtype> findAll();
}

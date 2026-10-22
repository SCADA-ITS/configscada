package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.VoEntityType;

@Repository
public interface VoEntityTypeRepository extends JpaRepository<VoEntityType, Long> {

	VoEntityType findById(String id);
	List<VoEntityType> findAll();
}

package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.VoEntity;

@Repository
public interface VoEntityRepository extends JpaRepository<VoEntity, Long> {

	VoEntity findById(String id);
	List<VoEntity> findAll();
}

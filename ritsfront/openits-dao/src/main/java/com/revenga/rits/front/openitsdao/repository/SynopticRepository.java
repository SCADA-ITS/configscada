package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.Synoptic;

@Repository
public interface SynopticRepository extends JpaRepository<Synoptic, Long> {

	Synoptic findById(Integer id);
	List<Synoptic> findAll();
}

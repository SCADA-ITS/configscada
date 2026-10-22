package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.EquipViewer;

@Repository
public interface EquipViewerRepository extends JpaRepository<EquipViewer, Long> {

	EquipViewer findById(String id);
	List<EquipViewer> findAll();
}
